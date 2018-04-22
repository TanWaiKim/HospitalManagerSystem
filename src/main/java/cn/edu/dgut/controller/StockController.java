package cn.edu.dgut.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.BigDecimalUtil;
import cn.edu.dgut.common.util.Const;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrugAdmin;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.pojo.TbWarehouse;
import cn.edu.dgut.service.PurchaseItemService;
import cn.edu.dgut.service.StockService;
import cn.edu.dgut.service.WarehouseService;

/**
 * @author TanWaiKim
 * @time 2018年2月4日 下午8:15:42
 * @version 1.0
 */
@Controller
@RequestMapping("/stock")
public class StockController {
	@Autowired
	private PurchaseItemService purchaseItemService;
	@Autowired
	private StockService stockService;
	@Autowired
	private WarehouseService warehouseService;
	
	/**
	 * 添加库存
	 * @param id
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public HmsResult addStockByTbStock(HttpSession session, Model model,Integer id) {
		try {
			TbPurchaseItem purchaseItem = purchaseItemService.getPurchaseItemById(id);
			
			if (purchaseItem.getStatus().equals("已入库")) {
				return HmsResult.build(500, "该记录处于已入库状态，不可再次入库！");
			}
			
	        TbDrugAdmin admin = (TbDrugAdmin)session.getAttribute(Const.CURRENT_USER);
	        
			TbStock stock = new TbStock();
			stock.setWarehouseNo(purchaseItem.getWarehouseNo());
			stock.setDrugId(purchaseItem.getDrugId());
			stock.setStockQuantity(purchaseItem.getQuantity());
			stock.setOperator(admin.getUsername());
			stock.setBatchNo(purchaseItem.getBatchNo());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("drugId", purchaseItem.getDrugId());
			map.put("batchNo", purchaseItem.getBatchNo());
			
			TbStock stock2 = stockService.getStockByDrug(map);
			
			if (stock2 != null) {
				BigDecimal stockQuantity = BigDecimalUtil.add(purchaseItem.getQuantity().doubleValue(),
						stock2.getStockQuantity().doubleValue());
				stock.setStockQuantity(stockQuantity.intValue());
				if (stockService.updateStockBySelective(stock) > 0) {
			        purchaseItem.setStatus("已入库");
			        purchaseItemService.updatePurchaseItemByTbPurchaseItem(purchaseItem);
					return HmsResult.ok();
				}
			} else {
				// 添加库存
				if (stockService.addStockByTbStock(stock) > 0) {
			        purchaseItem.setStatus("已入库");
			        purchaseItemService.updatePurchaseItemByTbPurchaseItem(purchaseItem);
					return HmsResult.ok();
				}
			}
			
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "入库失败！");
		}
		
		return HmsResult.build(500, "入库失败！");
		
	}
	
	/**
	 * 库存单列表展示，首次进入
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String getAllStock(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+1);
			page.setCurrentPage(currentPage);
			model.addAttribute("stockList", stockService.getAllStock(page));
			model.addAttribute("warehouseList", warehouseService.selectAllWarehouse());
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "stock-list";
	}
	
	/**
	 * 分页条件查询
	 * @param warehouseNo
	 * @param operator
	 * @param drugName
	 * @param drugNo
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition")
	public String getStockByPage(
			@RequestParam(value = "warehouseNo", defaultValue = "") String warehouseNo,
			@RequestParam(value = "operator", defaultValue = "") String operator,
			@RequestParam(value = "drugName", defaultValue = "") String drugName,
			@RequestParam(value = "drugNo", defaultValue = "") String drugNo,
			@RequestParam(value = "currentPage", defaultValue = "") String currentPage, Model model) {
		try {
			// 创建分页对象
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+1);
			Pattern pattern = Pattern.compile("[0-9]{1,9}");
			if (currentPage == null || !pattern.matcher(currentPage).matches()) {
				page.setCurrentPage(1);
			} else {
				page.setCurrentPage(Integer.valueOf(currentPage));
			}
			
			List<TbStock> stockList = stockService.pageByCondition(warehouseNo, operator, drugName, drugNo, page);
			
			TbWarehouse warehouseCondition = warehouseService.getWarehouseByNo(warehouseNo);
			model.addAttribute("warehouseCondition", warehouseCondition);
			
			
			model.addAttribute("stockList", stockList);
			model.addAttribute("page", page);
			model.addAttribute("drugName", drugName);
			model.addAttribute("drugNo", drugNo);
			model.addAttribute("operator", operator);
			
			model.addAttribute("warehouseList", warehouseService.selectAllWarehouse());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "stock-list";
	}	
	
	/**
	 * 库存限制列表展示，首次进入
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/limtList")
	public String getAllStocklist(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+2);
			page.setCurrentPage(currentPage);
			
			List<TbStock> stockList = stockService.getAllListStock(page);
			
			for(TbStock stock:stockList) {
				TbPurchaseItem purchaseItem = new TbPurchaseItem();
				purchaseItem.setBatchNo(stock.getBatchNo());
				purchaseItem.setDrugId(stock.getDrugId());
				purchaseItem = purchaseItemService.selectByDrugIdAndBatchNo(purchaseItem);
				
				stock.setPurchaseItem(purchaseItem);
			}
			
			model.addAttribute("stockList", stockList);
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "stock-limit";
	}
	
	/**
	 * 库存限制列表展示，条件查询
	 * @param drugName
	 * @param drugNo
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/limitPageByCondition")
	public String getStocklistByPage(
			@RequestParam(value = "drugName", defaultValue = "") String drugName,
			@RequestParam(value = "drugNo", defaultValue = "") String drugNo,
			@RequestParam(value = "currentPage", defaultValue = "") String currentPage, Model model) {
		try {
			// 创建分页对象
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+2);
			Pattern pattern = Pattern.compile("[0-9]{1,9}");
			if (currentPage == null || !pattern.matcher(currentPage).matches()) {
				page.setCurrentPage(1);
			} else {
				page.setCurrentPage(Integer.valueOf(currentPage));
			}
			
			List<TbStock> stockList = stockService.pageByListCondition(drugName,drugNo,page);
			
			for(TbStock stock:stockList) {
				TbPurchaseItem purchaseItem = new TbPurchaseItem();
				purchaseItem.setBatchNo(stock.getBatchNo());
				purchaseItem.setDrugId(stock.getDrugId());
				purchaseItem = purchaseItemService.selectByDrugIdAndBatchNo(purchaseItem);
				
				stock.setPurchaseItem(purchaseItem);
			}
			
			model.addAttribute("stockList", stockList);
			model.addAttribute("page", page);
			model.addAttribute("drugName", drugName);
			model.addAttribute("drugNo", drugNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "stock-limit";
	}
	
	

	/**
	 * 库存上下限预警列表展示，首次进入
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/quantityWaring")
	public String getQuantityWaring(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+3);
			page.setCurrentPage(currentPage);
			model.addAttribute("stockList", stockService.getAllStockByQuantityWaring(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "quantity-waring";
	}
	
	/**
	 * 库存上下限预警列表展示，分页查询
	 * @param model
	 * @return
	 */
	@RequestMapping("/quantityWaringPageByCondition")
	public String getQuantityWaringByPage(
			@RequestParam(value = "currentPage", defaultValue = "") String currentPage, Model model) {
		try {
			// 创建分页对象
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+3);
			Pattern pattern = Pattern.compile("[0-9]{1,9}");
			if (currentPage == null || !pattern.matcher(currentPage).matches()) {
				page.setCurrentPage(1);
			} else {
				page.setCurrentPage(Integer.valueOf(currentPage));
			}
			
			List<TbStock> stockList = stockService.pageByQuantityWaring(page);
			
			model.addAttribute("stockList", stockList);
			model.addAttribute("page", page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "quantity-waring";
	}
	

	/**
	 * 有效期预警列表展示，首次进入
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/validWaring")
	public String getValidWaring(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+4);
			page.setCurrentPage(currentPage);
			model.addAttribute("stockList", stockService.getAllStockByValidWaring(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "valid-waring";
	}
	
	/**
	 * 有效期预警列表展示，分页查询
	 * @param model
	 * @return
	 */
	@RequestMapping("/validWaringPageByCondition")
	public String getValidWaringByPage(
			@RequestParam(value = "currentPage", defaultValue = "") String currentPage, Model model) {
		try {
			// 创建分页对象
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+4);
			Pattern pattern = Pattern.compile("[0-9]{1,9}");
			if (currentPage == null || !pattern.matcher(currentPage).matches()) {
				page.setCurrentPage(1);
			} else {
				page.setCurrentPage(Integer.valueOf(currentPage));
			}
			
			List<TbStock> stockList = stockService.pageByValidWaring(page);
			
			
			model.addAttribute("stockList", stockList);
			model.addAttribute("page", page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "valid-waring";
	}
	
	
	
	/**
	 * 根据id查询库存信息，返回设置库存上下限页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/findByDrugId")
	public String getStockByDrugId(@RequestParam(value = "drugId") Integer drugId, Model model) {
		List<TbStock> stockList = stockService.getStockByDrugId(drugId);
		
		Integer totalQuantity = 0;
		
		for(TbStock stock:stockList) {
			totalQuantity += stock.getStockQuantity();
		}
		
		TbStock stock = stockList.get(0);
		
		stock.setStockQuantity(totalQuantity);
		model.addAttribute("stock", stock);
		return "stock-update";
	}
	
	/**
	 * 修改库存上下限信息
	 * @param stock
	 * @param model
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updateStockByTbStock(TbStock stock, Model model) {
		
		try {
			if (stockService.updateStockByDrugId(stock) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "数据库异常，修改库存信息失败！");
		}
		return HmsResult.build(500, "修改库存信息失败！");
	}
	
	/**
	 * 根据id查询库存信息，返回调整价格页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/findByDrugId1")
	public String getStockByDrugId1(@RequestParam(value = "drugId") Integer drugId, Model model) {
		List<TbStock> stockList = stockService.getStockByDrugId(drugId);
		
		Integer totalQuantity = 0;
		
		for(TbStock stock:stockList) {
			totalQuantity += stock.getStockQuantity();
		}
		
		TbStock stock = stockList.get(0);
		
		List<TbPurchaseItem> purchaseItemList = purchaseItemService.selectAllPurchaseItemByDrugId(stock.getDrugId());
		BigDecimal purchasePrice = new BigDecimal("0");
		
		// 计算平均进价
		for(TbPurchaseItem tbPurchaseItem1 : purchaseItemList) {
			purchasePrice = BigDecimalUtil.add(purchasePrice.doubleValue(), 
					tbPurchaseItem1.getPurchasePrice().doubleValue());
		}
		purchasePrice = BigDecimalUtil.div(purchasePrice.doubleValue(), purchaseItemList.size());
		
		TbPurchaseItem purchaseItem = new TbPurchaseItem();
		purchaseItem.setBatchNo(stock.getBatchNo());
		purchaseItem.setDrugId(stock.getDrugId());
		purchaseItem = purchaseItemService.selectByDrugIdAndBatchNo(purchaseItem);
		purchaseItem.setPurchasePrice(purchasePrice);
		
		stock.setPurchaseItem(purchaseItem);
		stock.setStockQuantity(totalQuantity);
		model.addAttribute("stock", stock);
		return "price-reset";
	}	
}
