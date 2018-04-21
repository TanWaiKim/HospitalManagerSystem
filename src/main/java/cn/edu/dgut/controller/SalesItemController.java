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

import cn.edu.dgut.common.dto.SalesDto;
import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.Const;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbAdmin;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbSales;
import cn.edu.dgut.pojo.TbSalesItem;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.PurchaseItemService;
import cn.edu.dgut.service.SalesItemService;
import cn.edu.dgut.service.SalesService;

/**
 * @author TanWaiKim
 * @time 2018年2月5日 下午3:41:54
 * @version 1.0
 */
@Controller
@RequestMapping("/salesItem")
public class SalesItemController {
	@Autowired
	private SalesService salesService;
	@Autowired
	private SalesItemService salesItemService;
	@Autowired
	private DrugService drugService;
	@Autowired
	private PurchaseItemService purchaseItemService;
	
	/**
	 * 返回添加销药单项目页面
	 * @param salesNo
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/skipToAdd")
	public String skipToAdd(
			@RequestParam(value = "salesNo", defaultValue = "") String salesNo,
			HttpSession session, 
			Model model) {
        TbAdmin admin = (TbAdmin)session.getAttribute(Const.CURRENT_USER);
        if(admin ==null){
            return "login";
        }
		
		TbSales sales = salesService.getSalesBySalesNo(salesNo);
		SalesDto salesDto = new SalesDto();
		salesDto.setSalesNo(sales.getSalesNo());
		salesDto.setPatientId(sales.getPatientId());
		salesDto.setTotalQuantity(sales.getTotalQuantity());
		salesDto.setTotalPrice(sales.getTotalPrice());
		salesDto.setOperator(sales.getOperator());
		
		model.addAttribute("salesDto", salesDto); 
		
		return "salesItem-add";
	}
	
	/**
	 * 处理添加销药单项目请求
	 * @param session
	 * @param model
	 * @param salesDto
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addSalesBySalesNo(HttpSession session, Model model, SalesDto salesDto) {
		try {
			
			if (salesDto.getDrugName() == null) {
				return HmsResult.build(505, "医药名称不能为空！");
			}
			
			if (salesDto.getQuantity() == null) {
				return HmsResult.build(505, "销药数量不能为空！");
			}
			
			if (salesService.updateSalesBySalesNo(salesDto) > 0) {
				return HmsResult.ok();
			} else {
				return HmsResult.build(505, "该医药不存在或库存不足，请补充库存！");
			}

		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "数据库异常，添加医药信息失败！");
		}
	}		
	
	
	/**
	 * 分页条件查询
	 * @param salesNo
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition")
	public String getSalesByPage(
			@RequestParam(value = "salesNo", defaultValue = "") String salesNo,
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
		
			TbSales sales = salesService.getSalesBySalesNo(salesNo);
			model.addAttribute("sales", sales);
			List<TbSalesItem> salesItemList = salesItemService.getAllSalesItem(salesNo, page);
			
			TbDrug drug = new TbDrug();
			TbPurchaseItem purchaseItem = new TbPurchaseItem();
			
			for (int i = 0; i < salesItemList.size(); i++) {
				drug = drugService.getDrugById(salesItemList.get(i).getDrugId());
				purchaseItem.setBatchNo(salesItemList.get(i).getBatchNo());
				purchaseItem.setDrugId(salesItemList.get(i).getDrugId());
				purchaseItem = purchaseItemService.selectByDrugIdAndBatchNo(purchaseItem);
				drug.setPurpose(purchaseItem.getProduceTime());
				drug.setSpec(purchaseItem.getValidTime());
				salesItemList.get(i).setDrug(drug);
			}
			
			model.addAttribute("salesItemList", salesItemList);
			model.addAttribute("page", page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sale-detail";
	}
	
	/**
	 * 返回修改销药单详细单页面
	 * @param salesNo
	 * @param id
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateById")
	public String updateSalesBySalesItemId(
			@RequestParam(value = "salesNo") String salesNo,
			@RequestParam(value = "id") Integer id,
			HttpSession session, 
			Model model) {
        TbAdmin admin = (TbAdmin)session.getAttribute(Const.CURRENT_USER);
        if(admin ==null){
            return "login";
        }
        
        // 根据销售单编号获取销售信息
		TbSales sales = salesService.getSalesBySalesNo(salesNo);
		// 封装销售信息到工具类中
		SalesDto salesDto = new SalesDto();
		salesDto.setSalesNo(sales.getSalesNo());
		salesDto.setPatientId(sales.getPatientId());
		salesDto.setTotalQuantity(sales.getTotalQuantity());
		salesDto.setTotalPrice(sales.getTotalPrice());
		salesDto.setOperator(sales.getOperator());
		
		
		// 根据销售详细单id获取销售详细信息
		TbSalesItem salesItem = salesItemService.getSalesItemById(id);
		
		// 封装销售详细单信息到工具类中
		salesDto.setId(id);
		salesDto.setDrugId(salesItem.getDrugId());
		salesDto.setDrugName(salesItem.getDrugName());
		salesDto.setQuantity(salesItem.getQuantity());
		salesDto.setSalePrice(salesItem.getSalePrice());
		// 该项目上次的数量
		salesDto.setOldSalesItemQuantity(salesItem.getQuantity());
		salesDto.setBatchNo(salesItem.getBatchNo());
		
		model.addAttribute("salesDto", salesDto); 
		
		return "salesItem-update";
	}	
	
	/**
	 * 根据销药单详细单id，处理修改请求
	 * @param session
	 * @param model
	 * @param salesDto
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updateSalesItemBySalesItemId(
			HttpSession session, 
			Model model, 
			SalesDto salesDto) {
		try {
			
			if (salesDto.getDrugId() == null) {
				return HmsResult.build(505, "医药名称不能为空！");
			}
			
			
			if (salesDto.getQuantity() == null) {
				return HmsResult.build(505, "销药数量不能为空！");
			}
			
			if (salesDto.getPatientId() == null) {
				return HmsResult.build(505, "病人名称不能为空！");
			}	
			
			if (salesService.updateSalesBySalesItemId(salesDto) > 0) {
				return HmsResult.ok();
			} else {
				return HmsResult.build(505, "该医药不存在或库存不足，请补充库存！");
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "修改销药单失败！");
		}
	}
	
	/**
	 * 删除单条销药详细单
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteSalesItemById(Integer id) {
		try {
			if (salesItemService.deleteSalesItemById(id) > 0) {

				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除销药详细单失败！");
		}
		
		return HmsResult.build(500, "删除销药详细单失败！");
		
	}

	/**
	 * 批量删除销药详细单
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult deleteSalesItemByIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (salesItemService.deleteSalesItemByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除销药详细单失败！");
		}
		return HmsResult.build(500, "删除销药详细单失败！");
		
	}
	
}
