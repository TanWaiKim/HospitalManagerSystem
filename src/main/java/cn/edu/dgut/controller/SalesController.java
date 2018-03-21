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
import cn.edu.dgut.common.util.JsonUtils;
import cn.edu.dgut.pojo.DrugData;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.pojo.TPrescription;
import cn.edu.dgut.pojo.TbAdmin;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbSales;
import cn.edu.dgut.pojo.TbSalesItem;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.PatientService;
import cn.edu.dgut.service.PrescriptionService;
import cn.edu.dgut.service.PurchaseItemService;
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
	@Autowired
	private PrescriptionService prescriptionService;
	@Autowired
	private DrugService drugService;
	@Autowired
	private PurchaseItemService purchaseItemService;
	
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
	public HmsResult addSalesByTbSales(HttpSession session, Integer id, String isDeal) {
		try {
			
			if (isDeal != null && isDeal.equals("已处理")) {
				return HmsResult.build(505, "该处方已被处理，不能重复提交！");
			}
			
			SalesDto salesDto = new SalesDto();
			TPrescription prescription = prescriptionService.getPrescriptionById(id);
			String drugData = prescription.getDrugData();
			List<DrugData> drugDataList = JsonUtils.jsonToList(drugData, DrugData.class);
			
			TbAdmin admin = (TbAdmin)session.getAttribute(Const.CURRENT_USER);
			salesDto.setOperator(admin.getUsername());
			salesDto.setPatientId(prescription.getPatientId());
			salesDto.setDrugDataList(drugDataList);
			
			if (salesService.addSalesByTbSales(salesDto) > 0) {
				prescription.setIsDeal("已处理");
				prescriptionService.updatePrescription(prescription);
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
			System.out.println("异常信息："+ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除销药单失败！");
		}
		return HmsResult.build(500, "删除销药单失败！");
		
	}
}
