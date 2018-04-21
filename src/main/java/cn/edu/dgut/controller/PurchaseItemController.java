package cn.edu.dgut.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.common.dto.PurchaseDto;
import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.Const;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbAdmin;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbWarehouse;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.PurchaseItemService;
import cn.edu.dgut.service.PurchaseService;
import cn.edu.dgut.service.WarehouseService;

/**
 * @author TanWaiKim
 * @time 2018年2月5日 下午3:41:54
 * @version 1.0
 */
@Controller
@RequestMapping("/purchaseItem")
public class PurchaseItemController {
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private PurchaseItemService purchaseItemService;
	@Autowired
	private DrugService drugService;
	@Autowired
	private WarehouseService warehouseService;
	
	
	/**
	 * 返回添加采药单项目页面
	 * @return
	 */
	@RequestMapping("/skipToAdd")
	public String skipToAdd(
			@RequestParam(value = "purchaseNo", defaultValue = "") String purchaseNo,
			HttpSession session, 
			Model model) {
        TbAdmin admin = (TbAdmin)session.getAttribute(Const.CURRENT_USER);
        if(admin ==null){
            return "login";
        }
        
        
		List<TbDrug> drugList = drugService.selectAllDrug();
		model.addAttribute("drugList", drugList);     
		
		TbPurchase purchase = purchaseService.getPurchaseByPurchaseNo(purchaseNo);
		PurchaseDto purchaseDto = new PurchaseDto();
		purchaseDto.setPurchaseNo(purchase.getPurchaseNo());
		purchaseDto.setProviderId(purchase.getProviderId());
		purchaseDto.setTotalQuantity(purchase.getTotalQuantity());
		purchaseDto.setTotalPrice(purchase.getTotalPrice());
		purchaseDto.setOperator(purchase.getOperator());
		purchaseDto.setRemarks(purchase.getRemarks());
		List<TbWarehouse> warehouseList = warehouseService.selectAllWarehouse();
		model.addAttribute("warehouseList", warehouseList); 
		
		
		model.addAttribute("purchaseDto", purchaseDto); 
		
		return "purchaseItem-add";
	}
	
	/**
	 * 处理添加采药单项目请求
	 * @param session
	 * @param model
	 * @param purchaseDto
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addPurchaseByPurchaseNo(HttpSession session, Model model, PurchaseDto purchaseDto) {
		try {
			
			if (purchaseDto.getWarehouseNo() == null) {
				return HmsResult.build(505, "仓库不能为空！");
			}
			
			if (purchaseDto.getDrugId() == null) {
				return HmsResult.build(505, "医药名称不能为空！");
			}
			
			if (purchaseDto.getPurchasePrice() == null) {
				return HmsResult.build(505, "进药价格不能为空！");
			}
			
			if (purchaseDto.getSalePrice() == null) {
				return HmsResult.build(505, "销药价格不能为空！");
			}
			
			
			if (purchaseDto.getQuantity() == null) {
				return HmsResult.build(505, "进药数量不能为空！");
			}
			
			if (purchaseDto.getProduceTime() == null) {
				return HmsResult.build(505, "生产日期不能为空！");
			}
			
			if (purchaseDto.getValidTime() == null) {
				return HmsResult.build(505, "有效日期至不能为空！");
			}
			
			if (purchaseDto.getBatchNo() == null) {
				return HmsResult.build(505, "产品批号不能为空！");
			}	
			
			if (purchaseService.updatePurchaseByPurchaseNo(purchaseDto) > 0) {
				return HmsResult.ok();
			}

		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "数据库异常，添加医药信息失败！");
		}
		return HmsResult.build(500, "添加医药信息失败！");
	}		
	
	
	/**
	 *  分页条件查询
	 * @param purchaseNo
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition")
	public String getPurchaseByPage(
			@RequestParam(value = "purchaseNo", defaultValue = "") String purchaseNo,
			@RequestParam(value = "currentPage", defaultValue = "") String currentPage, Model model) {
		try {
			// 创建分页对象
			Page page = new Page();
			Pattern pattern = Pattern.compile("[0-9]{1,9}");
			if (currentPage == null || !pattern.matcher(currentPage).matches()) {
				page.setCurrentPage(1);
			} else {
				page.setCurrentPage(Integer.valueOf(currentPage));
			}
		
			TbPurchase purchase = purchaseService.getPurchaseByPurchaseNo(purchaseNo);
			model.addAttribute("purchase", purchase);
			List<TbPurchaseItem> purchaseItemList = purchaseItemService.getAllPurchaseItem(purchaseNo, page);
			
			TbWarehouse warehouse = new TbWarehouse();
			
			for (int i = 0; i < purchaseItemList.size(); i++) {
				warehouse.setWarehouseNo(purchaseItemList.get(i).getWarehouseNo());
				warehouse = warehouseService.getWarehouseByNo(warehouse.getWarehouseNo());
				purchaseItemList.get(i).setWarehouseNo(warehouse.getWarehouseName());
			}
						
			model.addAttribute("purchaseItemList", purchaseItemList);
			model.addAttribute("page", page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "purchase-detail";
	}
	
	
	/**
	 * 返回修改采药单详细单页面
	 * @return
	 */
	@RequestMapping("/updateById")
	public String updatePurchaseByPurchaseItemId(
			@RequestParam(value = "purchaseNo") String purchaseNo,
			@RequestParam(value = "id") Integer id,
			HttpSession session, 
			Model model) {
        TbAdmin admin = (TbAdmin)session.getAttribute(Const.CURRENT_USER);
        if(admin ==null){
            return "login";
        }
        
		TbPurchase purchase = purchaseService.getPurchaseByPurchaseNo(purchaseNo);
		PurchaseDto purchaseDto = new PurchaseDto();
		purchaseDto.setPurchaseNo(purchase.getPurchaseNo());
		purchaseDto.setProviderId(purchase.getProviderId());
		purchaseDto.setTotalQuantity(purchase.getTotalQuantity());
		purchaseDto.setTotalPrice(purchase.getTotalPrice());
		purchaseDto.setOperator(purchase.getOperator());
		purchaseDto.setRemarks(purchase.getRemarks());
		purchaseDto.setId(id);
		
		TbPurchaseItem purchaseItem = purchaseItemService.getPurchaseItemById(id);
		
		purchaseDto.setDrugId(purchaseItem.getDrugId());
		purchaseDto.setDrugName(purchaseItem.getDrugName());
		purchaseDto.setQuantity(purchaseItem.getQuantity());
		purchaseDto.setPurchasePrice(purchaseItem.getPurchasePrice());
		purchaseDto.setSalePrice(purchaseItem.getSalePrice());
		purchaseDto.setOldPurchaseItemQuantity(purchaseItem.getQuantity());
		purchaseDto.setBatchNo(purchaseItem.getBatchNo());
		purchaseDto.setProduceTime(purchaseItem.getProduceTime());
		purchaseDto.setValidTime(purchaseItem.getValidTime());
		
		model.addAttribute("purchaseDto", purchaseDto); 
		
		return "purchaseItem-update";
	}	
	
	/**
	 * 根据采药单详细单id，处理修改请求
	 * @param session
	 * @param model
	 * @param purchaseDto
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updatePurchaseItemByPurchaseItemId(HttpSession session, Model model, PurchaseDto purchaseDto) {
		try {
			
			if (purchaseDto.getDrugId() == null) {
				return HmsResult.build(505, "医药名称不能为空！");
			}
			
			if (purchaseDto.getPurchasePrice() == null) {
				return HmsResult.build(505, "进药价格不能为空！");
			}
			
			if (purchaseDto.getSalePrice() == null) {
				return HmsResult.build(505, "销药价格不能为空！");
			}
			
			
			if (purchaseDto.getQuantity() == null) {
				return HmsResult.build(505, "进药数量不能为空！");
			}
			
			if (purchaseDto.getProduceTime() == null) {
				return HmsResult.build(505, "生产日期不能为空！");
			}
			
			if (purchaseDto.getValidTime() == null) {
				return HmsResult.build(505, "有效日期至不能为空！");
			}
			
			if (purchaseDto.getBatchNo() == null) {
				return HmsResult.build(505, "产品批号不能为空！");
			}	
			
			if (purchaseService.updatePurchaseByPurchaseItemId(purchaseDto) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "修改采药单失败！");
		}
		return HmsResult.build(500, "修改采药单失败！");
	}
	
	/**
	 * 删除单条采药详细单
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deletePurchaseItemById(Integer id) {
		try {
			if (purchaseItemService.deletePurchaseItemById(id) > 0) {

				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除采药详细单失败！");
		}
		
		return HmsResult.build(500, "删除采药详细单失败！");
		
	}

	/**
	 * 批量删除采药详细单
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult deletePurchaseItemByIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (purchaseItemService.deletePurchaseItemByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除采药详细单失败！");
		}
		return HmsResult.build(500, "删除采药详细单失败！");
		
	}
	
	/**
	 * 调整价格
	 * @param stock
	 * @param model
	 * @return
	 */
	@RequestMapping("/updatePrice")
	@ResponseBody()
	public HmsResult updatePrice(TbPurchaseItem purchaseItem, Model model) {
		
		try {
			if (purchaseItemService.updateByDrugIdSelective(purchaseItem) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "数据库异常，修改库存信息失败！");
		}
		return HmsResult.build(500, "修改库存信息失败！");
	}	
	
}
