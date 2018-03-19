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
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.pojo.TbAdmin;
import cn.edu.dgut.pojo.TbSales;
import cn.edu.dgut.pojo.TbSalesItem;
import cn.edu.dgut.service.PatientService;
import cn.edu.dgut.service.SalesItemService;
import cn.edu.dgut.service.SalesService;

/**
 * @author TanWaiKim
 * @time 2018年2月24日 下午9:16:44
 * @version 1.0
 */
@Controller
@RequestMapping("/sales")
public class SalesController {
	@Autowired
	private PatientService patientService;
	@Autowired
	private SalesService salesService;
	@Autowired
	private SalesItemService salesItemService;
	
	
	/**
	 * 返回添加销药单页面
	 * @return
	 */
	@RequestMapping("/skipToAdd")
	public String skipToAdd(HttpSession session, Model model) {
        TbAdmin admin = (TbAdmin)session.getAttribute(Const.CURRENT_USER);
        if(admin ==null){
            return "login";
        }
        
		List<TPatient> patientList = patientService.selectAllPatient();
		model.addAttribute("patientList", patientList);        
		
		model.addAttribute("operator", admin.getUsername());
			
		return "sale-add";
	}
	
	
	/**
	 * 处理添加销药单请求
	 * @param session
	 * @param model
	 * @param salesDto
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addSalesByTbSales(HttpSession session, Model model, SalesDto salesDto) {
		try {
			
			if (salesDto.getDrugName() == null) {
				return HmsResult.build(505, "医药名称不能为空！");
			}	        
	        
			if (salesDto.getPatientId() == null) {
				return HmsResult.build(505, "病人名称不能为空！");
			}
			
			if (salesDto.getQuantity() == null) {
				return HmsResult.build(505, "数量不能为空！");
			}
			
			
			if (salesService.addSalesByTbSales(salesDto) > 0) {
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
	 * 销药单列表展示，首次进入
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String getAllSales(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+1);
			page.setCurrentPage(currentPage);
			model.addAttribute("patientList", patientService.selectAllPatient());   
			model.addAttribute("salesList", salesService.getAllSale(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sale-list";
	}

	/**
	 * 分页条件查询
	 * @param patientId
	 * @param salesNo
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition")
	public String getSalesByPage(
			@RequestParam(value = "salesNo", defaultValue = "") String salesNo,
			@RequestParam(value = "patientId", defaultValue = "") String patientId,
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
			
			List<TbSales> salesList = salesService.pageByCondition(salesNo, patientId, page);
			
			
			TPatient patientCondition =patientService.getPatientById(patientId);
			model.addAttribute("patientCondition", patientCondition);
			
			model.addAttribute("salesList", salesList);
			model.addAttribute("page", page);
			model.addAttribute("salesNo", salesNo);
			model.addAttribute("patientList", patientService.selectAllPatient());  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sale-list";
	}
	
	@RequestMapping("/findBySalesNo")
	public String getBySalesNo(
			@RequestParam(value = "salesNo") String salesNo, 
			@RequestParam(value = "page", defaultValue = "1") Integer currentPage, 
			Model model) {
		Page page = new Page();
		page.setCurrentPage(currentPage);
		TbSales sales = salesService.getSalesBySalesNo(salesNo);
		model.addAttribute("sales", sales);
		List<TbSalesItem> salesItemList = salesItemService.getAllSalesItem(salesNo, page);
		model.addAttribute("salesItemList", salesItemList);
		model.addAttribute("page", page);
		return "sale-detail";
	}
	
	
	
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteSalesById(Integer id) {
		try {
			TbSales sales = salesService.getSalesById(id);
			String salesNo = sales.getSalesNo();
			if ((salesService.deleteSalesById(id) > 0 )) {
				// 删除采药详细单
				salesItemService.deleteSalesItemBySalesItem(salesNo);
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除销药单失败！");
		}
		
		return HmsResult.build(500, "删除销药单失败！");
		
	}
	
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult deleteSalesByIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (salesService.deleteSalesByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除销药单失败！");
		}
		return HmsResult.build(500, "删除销药单失败！");
		
	}
}
