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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.common.dto.StockDto;
import cn.edu.dgut.common.dto.ValidWarningDto;
import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.BigDecimalUtil;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.pojo.TbWarehouse;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.PurchaseService;
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
	private StockService stockService;
	@Autowired
	private DrugService drugService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private WarehouseService warehouseService;
	
	/**
	 * 添加库存
	 * @param id
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public HmsResult addStockByTbStock(HttpSession session, Model model,StockDto stockDto) {
		try 
		{
			if (stockDto.getWarehouseNo() != null && stockDto.getWarehouseNo().equals("") ) {
				return HmsResult.build(505, "请选择存储仓库！");
			}
			
			if (stockDto.getIsStock() == 1) {
				return HmsResult.build(500, "该记录处于已入库状态，不可再次入库！");
			}
	        
	        // 填充库存信息
			TbStock stock = new TbStock();
			stock.setWarehouseNo(stockDto.getWarehouseNo());
			stock.setDrugId(stockDto.getDrugId());
			stock.setStockQuantity(stockDto.getQuantity());
			
			// 找到医药的信息
			TbDrug drug = drugService.getDrugById(stockDto.getDrugId());
			
			stock.setDrugname(drug.getDrugName());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("drugId", drug.getId());
			
			// 根据药品id查找药品，判断库存中是否有该药品
			List<TbStock> stocks = stockService.getStockByDrug(map);
			
			// 如果有，则增加相应的药品数量即可
			if (stocks != null && stocks.size() > 0) {
				BigDecimal stockQuantity = BigDecimalUtil.add(stockDto.getQuantity().doubleValue(),
						stocks.get(0).getStockQuantity().doubleValue());
				stock.setStockQuantity(stockQuantity.intValue());
				if (stockService.updateStockBySelective(stock) > 0) {
			        TbPurchase purchase = new TbPurchase();
			        purchase.setPurchaseNo(stockDto.getPurchaseNo());
			        purchase.setIsStock(1);
			        purchaseService.updatePurchase(purchase);
					return HmsResult.ok();
				}
			} else {
				// 添加库存
				if (stockService.addStockByTbStock(stock) > 0) {
			        TbPurchase purchase = new TbPurchase();
			        purchase.setPurchaseNo(stockDto.getPurchaseNo());
			        purchase.setIsStock(1);
			        purchaseService.updatePurchase(purchase);
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
			page.setPageNumber(page.getPageNumber()+2);
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
	 * @param drugName
	 * @param drugNo
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition")
	public String getStockByPage(
			@RequestParam(value = "warehouseNo", defaultValue = "") String warehouseNo,
			@RequestParam(value = "drugname", defaultValue = "") String drugname,
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
			
			List<TbStock> stockList = stockService.pageByCondition(warehouseNo,  drugname, page);
			
			TbWarehouse warehouseCondition = warehouseService.getWarehouseByNo(warehouseNo);
			model.addAttribute("warehouseCondition", warehouseCondition);
			
			
			model.addAttribute("stockList", stockList);
			model.addAttribute("page", page);
			model.addAttribute("warehouseNo", warehouseNo);
			model.addAttribute("drugname", drugname);
			
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
			
			List<TbStock> stockList = stockService.pageByListCondition(drugName,page);
		
			
			model.addAttribute("stockList", stockList);
			model.addAttribute("page", page);
			model.addAttribute("drugName", drugName);
			
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
			page.setPageNumber(page.getPageNumber()+3);
			page.setCurrentPage(currentPage);
			model.addAttribute("validWaringList", stockService.getAllStockByValidWaring(page));
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
			page.setPageNumber(page.getPageNumber()+3);
			Pattern pattern = Pattern.compile("[0-9]{1,9}");
			if (currentPage == null || !pattern.matcher(currentPage).matches()) {
				page.setCurrentPage(1);
			} else {
				page.setCurrentPage(Integer.valueOf(currentPage));
			}
			
			List<ValidWarningDto> validList = stockService.pageByValidWaring(page);
			
			
			model.addAttribute("validWaringList", validList);
			model.addAttribute("page", page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "valid-waring";
	}
	
	
	
	/**
	 * 根据药品名称查询库存信息，返回设置库存上下限页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/findByDrugName")
	public String getStockByDrugName(@RequestParam(value = "drugname") String drugname, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugname", drugname);
		List<TbStock> stockList = stockService.getStockByDrug(map);
		
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
			if (stock.getMinQuantity() == null || stock.getMinQuantity() == 0) {
				return HmsResult.build(505, "最小库存不能为空！");
			}
			
			if (stock.getMaxQuantity() == null || stock.getMaxQuantity() == 0) {
				return HmsResult.build(505, "最大库存不能为空！");
			}
			
			if (stock.getMaxQuantity() < stock.getMinQuantity()) {
				return HmsResult.build(505, "最小库存不能大于最大库存！");
			}
			
			if (stockService.updateStockByDrugName(stock) > 0) {
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
	@RequestMapping("/findByDrugName1")
	public String getStockByDrugId1(@RequestParam(value = "drugname") String drugname, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugname", drugname);
		List<TbStock> stockList = stockService.getStockByDrug(map);
		
		Integer totalQuantity = 0;
		
		for(TbStock stock:stockList) {
			totalQuantity += stock.getStockQuantity();
		}
		
		TbStock stock = stockList.get(0);
		
		BigDecimal purchasePrice =  new BigDecimal("0");
		BigDecimal salePrice =  new BigDecimal("0");
		List<TbDrug> drugs = drugService.getDrugByName(stock.getDrugname());
		
		for(TbDrug drug : drugs) {
			purchasePrice = BigDecimalUtil.add(purchasePrice.doubleValue(), drug.getPurchasePrice().doubleValue());
		}
		
		purchasePrice = BigDecimalUtil.div(purchasePrice.doubleValue(), drugs.size());
		
		for(TbDrug drug : drugs) {
			salePrice = BigDecimalUtil.add(salePrice.doubleValue(), drug.getSalePrice().doubleValue());
		}
		
		salePrice = BigDecimalUtil.div(salePrice.doubleValue(), drugs.size());
		
		TbDrug drug = new TbDrug();
		drug.setId(stock.getDrugId());
		drug.setDrugName(stock.getDrugname());
		drug.setPurchasePrice(purchasePrice);
		drug.setSalePrice(salePrice);
		
		stock.setDrug(drug);
		stock.setStockQuantity(totalQuantity);
		model.addAttribute("stock", stock);
		return "price-reset";
	}	
	
	/**
	 * 删除单条库存药品信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteStockByDrugId(Integer id) {
		try {
			if (stockService.deleteStockByDrugId(id) > 0) {

				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "销毁药品失败！");
		}
		
		return HmsResult.build(500, "销毁药品失败！");
		
	}

	/**
	 * 批量删除库存药品信息
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult deleteStockByDrugIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (stockService.deleteStockByDrugIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "销毁药品失败！");
		}
		return HmsResult.build(500, "销毁药品失败！");
		
	}	
}
