package cn.edu.dgut.controller;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.common.util.JsonUtils;
import cn.edu.dgut.pojo.DrugData;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TDiagnosis;
import cn.edu.dgut.pojo.TDoctor;
import cn.edu.dgut.pojo.TPrescription;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.service.PatientService;
import cn.edu.dgut.service.PrescriptionService;

@Controller
@RequestMapping("/prescription")
public class PrescriptionController {

	@Autowired
	private PrescriptionService prescriptionService;
	@Autowired 
	private PatientService patientService;

	@RequestMapping("/list")
	public String getAllPrescription(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model,HttpServletRequest request) {
		try {
			Page page = new Page();
			page.setCurrentPage(currentPage);
			List<TPrescription> prescriptionList = prescriptionService.getAllPrescription(page);
			//用于存放自定义处方数据返回到jsp中
			if(prescriptionList.size()>0){
				for (TPrescription prescription : prescriptionList) {
					List<DrugData> drugDataList = JsonUtils.jsonToList(prescription.getDrugData(), DrugData.class);
					StringBuilder sb = new StringBuilder();
					for (DrugData drugData : drugDataList) {
						sb.append("'"+drugData.getDrugName()+"'×"+drugData.getDrugNum()+",'"+drugData.getDrugUsage()+"';");
					}
					prescription.setDrugData(new String(sb));
				}
			}
			model.addAttribute("prescriptionList", prescriptionList);
			model.addAttribute("page", page);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return "error";
		}
		return "prescription-list";
	}
	
	@RequestMapping("/pageByCondition")
	public String getPrescriptionByPage(
			@RequestParam(value = "prescriptionId", defaultValue = "") String prescriptionId,
			@RequestParam(value = "patientName", defaultValue = "") String patientName,
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
			List<TPrescription> prescriptionList = prescriptionService.pageByCondition(prescriptionId,null,patientName, page);
			//用于存放自定义处方数据返回到jsp中
			if(prescriptionList.size()>0){
				for (TPrescription prescription : prescriptionList) {
					List<DrugData> drugDataList = JsonUtils.jsonToList(prescription.getDrugData(), DrugData.class);
					StringBuilder sb = new StringBuilder();
					for (DrugData drugData : drugDataList) {
						sb.append("'"+drugData.getDrugName()+"'×"+drugData.getDrugNum()+",'"+drugData.getDrugUsage()+"';");
					}
					prescription.setDrugData(new String(sb));
				}
			}
			model.addAttribute("prescriptionList", prescriptionList);
			model.addAttribute("page", page);
			model.addAttribute("prescriptionId", prescriptionId);
			model.addAttribute("patientName", patientName);
		
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return "error";
		}
		return "prescription-list";
	}
	
	@RequestMapping("/skipToAdd")
	public String skipToAdd(Model model) {
		//跳转到添加处方信息界面并查询所有记录的病人编号
		List<String> patientIds = patientService.selectAllPatientIds();
		model.addAttribute("patientIds", patientIds);
		return "prescription-add";
	}
	//添加诊断信息成功后直接跳转到处方信息增加界面
	@RequestMapping("/skipToAdd1/{patientId}")
	public String skipToAdd1(@PathVariable("patientId")String patientId,Model model) {
		model.addAttribute("pId", patientId);
		return "prescription-add";
	}
	
	//添加处方接口
	@RequestMapping("/add/{patientId}")
	@ResponseBody
	public HmsResult addPrescription(@PathVariable("patientId")String patientId, String paramData, HttpServletRequest request){
		try {
			
			
			//查询药品所在库存记录，并判断库存是否满足处方药品数量
			List<DrugData> drugDataList = JsonUtils.jsonToList(paramData, DrugData.class);
			for (DrugData drugData : drugDataList) {
				String drugName = drugData.getDrugName();
				String drugNum = drugData.getDrugNum();
				List<TbDrug> prescriptionList = prescriptionService.findByDrugName(drugName);
				if(prescriptionList == null){
					return HmsResult.build(505, drugName+"在库存中不存在！");
				}
				if(prescriptionService.isEnoughDrug(drugName,drugNum.substring(0, drugNum.length()-1)) != true){
					return HmsResult.build(505, drugName+"在库存中不足！");
				}
			}
			TPrescription prescription = new TPrescription();
			TDoctor doctor = (TDoctor) request.getSession().getAttribute("doctorInfo");
			prescription.setPatientId(patientId);
			prescription.setDoctorId(doctor.getDoctorId());
			prescription.setDrugData(paramData);
			prescription.setIsDeal("未处理");
			if (prescriptionService.addPrescription(prescription) > 0) {
				return HmsResult.ok();
			}

		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "系统错误，添加失败！");
		}
		return HmsResult.build(500, "添加失败！");
	}
	
	@RequestMapping("/findById/{id}")
	public String getDiagnosisById(@PathVariable("id") Integer id, Model model) {
		TPrescription prescription = prescriptionService.getPrescriptionById(id);
		String drugData = prescription.getDrugData();
		List<DrugData> drugDataList = JsonUtils.jsonToList(drugData, DrugData.class);
		for(int i =0; i < drugDataList.size(); i++){
			DrugData drugData2 = drugDataList.get(i);
			String dN = drugData2.getDrugNum();
			drugData2.setDrugNum(dN.substring(0, dN.length()-1));
			drugData2.setUnit(dN.substring(dN.length()-1));
		}
		model.addAttribute("prescription", prescription);
		model.addAttribute("drugDataList", drugDataList);
		return "prescription-update";
	}
	
	@RequestMapping("/update/{id}/{prescriptionId}/{patientId}/{doctorId}")
	@ResponseBody
	public HmsResult updatePrescription(@PathVariable("id")String id, 
			@PathVariable("prescriptionId")String prescriptionId, @PathVariable("patientId")String patientId, 
			@PathVariable("doctorId")String doctorId, String paramData, Model model){
		try {
			//查询药品所在库存记录，并判断库存是否满足处方药品数量
			List<DrugData> drugDataList = JsonUtils.jsonToList(paramData, DrugData.class);
			for (DrugData drugData : drugDataList) {
				String drugName = drugData.getDrugName();
				String drugNum = drugData.getDrugNum();
				if(prescriptionService.isEnoughDrug(drugName,drugNum.substring(0, drugNum.length()-1)) != true){
					return HmsResult.build(505, drugName+"在库存中不足！");
				}
			}
			TPrescription prescription = new TPrescription();
			prescription.setId(Integer.valueOf(id));
			prescription.setPrescriptionId(prescriptionId);
			prescription.setPatientId(patientId);
			prescription.setDoctorId(doctorId);
			prescription.setDrugData(paramData);
			
			prescription.setUpdated(new Date());
			
			System.out.println("prescription="+prescription.toString());
			if (prescriptionService.updatePrescription(prescription) > 0) {
				model.addAttribute("pId", "");
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return HmsResult.build(500, "系统错误，修改失败！");
		}
		return HmsResult.build(500, "修改失败！");
	}
	
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deletePrescriptionById(String id) {
		try {
			if (prescriptionService.deletePrescriptionById(Integer.valueOf(id)) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "系统错误，删除失败！");
		}
		
		return HmsResult.build(500, "删除失败！");
		
	}
	
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult deletPrescriptionByIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (prescriptionService.deletePrescriptionByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "系统错误，删除失败！");
		}
		return HmsResult.build(500, "删除失败！");
	}
	
	
	@RequestMapping(value = "/auto")
	@ResponseBody
	public String autoDrugName(@RequestParam("term") String term, HttpServletResponse response){
		String dNs = null;
		try {
			dNs = prescriptionService.autoDrugName(term,response);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return dNs;
	}
	
	/**
	 * 处方信息（用于销售）
	 * @param currentPage
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list-data")
	public String getAllPrescriptionData(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model,HttpServletRequest request) {
		try {
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+1);
			page.setCurrentPage(currentPage);
			List<TPrescription> prescriptionList = prescriptionService.getAllPrescription(page);
			//用于存放自定义处方数据返回到jsp中
			if(prescriptionList.size()>0){
				for (TPrescription prescription : prescriptionList) {
					List<DrugData> drugDataList = JsonUtils.jsonToList(prescription.getDrugData(), DrugData.class);
					StringBuilder sb = new StringBuilder();
					for (DrugData drugData : drugDataList) {
						sb.append("药品名称："+drugData.getDrugName()+"，数量："+drugData.getDrugNum()+"; ");
					}
					prescription.setDrugData(new String(sb));
				}
			}
			model.addAttribute("prescriptionList", prescriptionList);
			model.addAttribute("page", page);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return "error";
		}
		return "prescription-data";
	}
	
	/**
	 * 处分信息（用于销售）
	 * @param prescriptionId
	 * @param patientName
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/dataPageByCondition")
	public String getPrescriptionDataByPage(
			@RequestParam(value = "prescriptionId", defaultValue = "") String prescriptionId,
			@RequestParam(value = "patientId", defaultValue = "") String patientId,
			@RequestParam(value = "patientName", defaultValue = "") String patientName,
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
			List<TPrescription> prescriptionList = prescriptionService.pageByCondition(prescriptionId,patientId,patientName, page);
			//用于存放自定义处方数据返回到jsp中
			if(prescriptionList.size()>0){
				for (TPrescription prescription : prescriptionList) {
					List<DrugData> drugDataList = JsonUtils.jsonToList(prescription.getDrugData(), DrugData.class);
					StringBuilder sb = new StringBuilder();
					for (DrugData drugData : drugDataList) {
						sb.append("药品名称："+drugData.getDrugName()+"，数量："+drugData.getDrugNum()+";");
					}
					prescription.setDrugData(new String(sb));
				}
			}
			model.addAttribute("prescriptionList", prescriptionList);
			model.addAttribute("page", page);
			model.addAttribute("prescriptionId", prescriptionId);
			model.addAttribute("patientId", patientId);
			model.addAttribute("patientName", patientName);
		
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			return "error";
		}
		return "prescription-data";
	}

}
