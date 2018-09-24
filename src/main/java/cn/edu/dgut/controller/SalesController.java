package cn.edu.dgut.controller;

import java.math.BigDecimal;
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
import cn.edu.dgut.common.util.BigDecimalUtil;
import cn.edu.dgut.common.util.Const;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.common.util.JsonUtils;
import cn.edu.dgut.pojo.DrugData;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.pojo.TPrescription;
import cn.edu.dgut.pojo.TbDrugAdmin;
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
        TbDrugAdmin admin = (TbDrugAdmin)session.getAttribute(Const.CURRENT_USER);
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
			// 得到处方单信息
			TPrescription prescription = prescriptionService.getPrescriptionById(id);
			// 得到处方单中的药品信息
			String drugData = prescription.getDrugData();
			// 解析JSON字符串，得到具体的药品信息
			List<DrugData> drugDataList = JsonUtils.jsonToList(drugData, DrugData.class);
			
			TbDrugAdmin admin = (TbDrugAdmin)session.getAttribute(Const.CURRENT_USER);
			salesDto.setOperator(admin.getUsername());
			salesDto.setPatientId(prescription.getPatientId());
			salesDto.setDrugDataList(drugDataList);
			
			if (salesService.addSalesByTbSales(salesDto) > 0) {
				prescription.setIsDeal("已处理");
				prescriptionService.updatePrescription(prescription);
				return HmsResult.ok();
			} else {
				return HmsResult.build(505, "该处方的医药不存在或库存不足，请补充库存！");
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
	
	// 根据销售单编号查看详情
	@RequestMapping("/findBySalesNo")
	public String getBySalesNo(
			@RequestParam(value = "salesNo") String salesNo, 
			@RequestParam(value = "page", defaultValue = "1") Integer currentPage, 
			Model model) {
		Page page = new Page();
		page.setCurrentPage(currentPage);
		
		// 分页查询
		List<TbSales> salesList = salesService.getSalesBySalesNo(salesNo,page);
		
		// 不分页
		List<TbSales> salesList1 = salesService.getSalesBySalesNo1(salesNo);
		
		TbSales sales = new TbSales();
		Integer quantity = 0;
		BigDecimal price = new BigDecimal("0");
		for(TbSales sales1:salesList1) {
			quantity += sales1.getQuantity();
			price = BigDecimalUtil.add(price.doubleValue(), sales1.getTotalPrice().doubleValue());
		}
		
		sales.setCreateTime(salesList1.get(0).getCreateTime());
		sales.setQuantity(quantity);
		sales.setTotalPrice(price);
		sales.setSalesNo(salesList1.get(0).getSalesNo());
		
		TPatient patient =patientService.getPatientById(salesList1.get(0).getPatientId());
		sales.setPatient(patient);
		
		TbDrug drug = new TbDrug();
		
		for (TbSales sales2:salesList) {
			drug = drugService.getDrugById(sales2.getDrugId());
			sales2.setDrug(drug);
		}
		
		model.addAttribute("sales", sales);
		model.addAttribute("salesList", salesList);
		model.addAttribute("page", page);
		return "sale-detail";
	}
	
	
	/**
	 * 分页条件查询
	 * @param salesNo
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition1")
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
		
			// 分页
			List<TbSales> salesList = salesService.getSalesBySalesNo(salesNo,page);
			
			// 不分页
			List<TbSales> salesList1 = salesService.getSalesBySalesNo1(salesNo);
			
			TbSales sales = new TbSales();
			Integer quantity = 0;
			BigDecimal price = new BigDecimal("0");
			for(TbSales sales1:salesList1) {
				quantity += sales1.getQuantity();
				price = BigDecimalUtil.add(price.doubleValue(), sales1.getTotalPrice().doubleValue());
			}
			
			sales.setCreateTime(salesList1.get(0).getCreateTime());
			sales.setQuantity(quantity);
			sales.setTotalPrice(price);
			sales.setSalesNo(salesList1.get(0).getSalesNo());
			
			TPatient patient =patientService.getPatientById(salesList1.get(0).getPatientId());
			sales.setPatient(patient);
			
			TbDrug drug = new TbDrug();
			
			for (TbSales sales2:salesList) {
				drug = drugService.getDrugById(sales2.getDrugId());
				sales2.setDrug(drug);
			}
			
			model.addAttribute("sales", sales);
			model.addAttribute("salesList", salesList);
			model.addAttribute("page", page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sale-detail";
	}
	
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteSalesById(Integer id) {
		try {
			
			if ((salesService.deleteSalesById(id) > 0 )) {
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
	
	/**
	 * 根据销售单编号和药品ID删除销售记录
	 */
	@RequestMapping("/deleteOneBySalesNoAndDrugId")
	@ResponseBody
	public HmsResult deleteOneBySalesNoAndDrugId(Integer drugId,String salesNo) {
		try {
			
			if ((salesService.deleteOneBySalesNoAndDrugId(drugId,salesNo) > 0 )) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "药品销售记录删除失败！");
		}
		
		return HmsResult.build(500, "药品销售记录删除失败！");
		
	}
	
	
	/**
	 * 返回修改销药单详细单页面
	 * @param salesNo
	 * @param id
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateBySalesNoAndDrugId")
	public String updateBySalesNoAndDrugId(
			@RequestParam(value = "drugId") Integer drugId,
			@RequestParam(value = "salesNo") String salesNo,
			HttpSession session, 
			Model model) {
        
        // 根据销售单编号和药品ID获取销售信息
		TbSales sales = salesService.getSalesBySalesNoAndDrugId(drugId,salesNo);
		TbDrug drug = drugService.getDrugById(sales.getDrugId());
		
		SalesDto salesDto = new SalesDto();
		salesDto.setId(sales.getId());             // 销售单ID
		salesDto.setSalesNo(sales.getSalesNo());
		salesDto.setPatientId(sales.getPatientId());
		salesDto.setDrugId(drug.getId());
		salesDto.setOldSalesItemQuantity(sales.getQuantity());
		salesDto.setQuantity(sales.getQuantity());
		salesDto.setDrugName(drug.getDrugName());
		salesDto.setBatchNo(drug.getDrugNo());
		
		model.addAttribute("salesDto", salesDto); 
		return "salesItem-update";
	}	
	
	/**
	 * 根据药品ID和销售单编号修改
	 * @param session
	 * @param model
	 * @param salesDto
	 * @return
	 */
	@RequestMapping("/updateItem")
	@ResponseBody()
	public HmsResult updateSalesBySalesNoAndDrugId(
			HttpSession session, 
			Model model, 
			SalesDto salesDto) {
		try {
			if (salesDto.getQuantity() == null || salesDto.getQuantity() == 0) {
				return HmsResult.build(505, "销售药品数量不能为空！");
			}
			
			if (salesService.updateSalesBySalesItemId(salesDto) > 0) {
				return HmsResult.ok();
			} else {
				return HmsResult.build(505, "该批次药品库存不足，请补充库存！");
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "修改药品销售记录失败！");
		}
	}	
	
	/**
	 * 返回添加药品页面
	 * @return
	 */
	@RequestMapping("/skipToAddDrug")
	public String skipToAddDrug(HttpSession session,
			Model model,
			@RequestParam(value = "salesNo") String salesNo,
			@RequestParam(value = "patientId") String patientId) {
        System.out.println("什么鬼："+patientId);     
		List<TbDrug> drugList = drugService.selectAllDrug();
		model.addAttribute("drugList", drugList);
		model.addAttribute("salesNo", salesNo);
		model.addAttribute("patientId", patientId);
		return "salesItem-add";
	}
	
	/**
	 * 销售药品
	 * @param session
	 * @param model
	 * @param salesDto
	 * @return
	 */
	@RequestMapping("/addDrug")
	@ResponseBody()
	public HmsResult addSalesByDrug(HttpSession session, SalesDto salesDto) {
		try {
			
			if (salesDto.getQuantity() == null || salesDto.getQuantity() == 0) {
				return HmsResult.build(505, "数量不能为空！");
			}
			
			if (salesService.addSalesByDrug(salesDto) > 0) {
				return HmsResult.ok();
			} else {
				return HmsResult.build(505, "该药品不存在或库存不足，请补充库存！");
			}

		} catch (Exception e) {
			e.getStackTrace();
			
			return HmsResult.build(500, "数据库异常，添加医药信息失败！");
		}
	}
}
