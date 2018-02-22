package cn.edu.dgut.controller;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

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
			model.addAttribute("prescriptionList", prescriptionService.getAllPrescription(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
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
			List<TPrescription> prescriptionList = prescriptionService.pageByCondition(prescriptionId,patientName, page);
			model.addAttribute("prescriptionList", prescriptionList);
			model.addAttribute("page", page);
			model.addAttribute("prescriptionId", prescriptionId);
			model.addAttribute("patientName", patientName);
		
		} catch (Exception e) {
			e.printStackTrace();
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
	
	@RequestMapping("/add/{patientId}")
	@ResponseBody
	public HmsResult addPrescription(@PathVariable("patientId")String patientId, String paramData, HttpServletRequest request){
		try {
			TPrescription prescription = new TPrescription();
			TDoctor doctor = (TDoctor) request.getSession().getAttribute("doctorInfo");
			prescription.setPatientId(patientId);
			prescription.setDoctorId(doctor.getDoctorId());
			prescription.setDrugData(paramData);
			if (prescriptionService.addPrescription(prescription) > 0) {
				return HmsResult.ok();
			}

		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "添加失败！");
		}
		return HmsResult.build(500, "添加失败！");
	}
	
	@RequestMapping("/findById/{id}")
	public String getDiagnosisById(@PathVariable("id") Integer id, Model model) {
		TPrescription prescription = prescriptionService.getPrescriptionById(id);
		String drugData = prescription.getDrugData();
		List<DrugData> drugDataList = JsonUtils.jsonToList(drugData, DrugData.class);
		model.addAttribute("prescription", prescription);
		model.addAttribute("drugDataList", drugDataList);
		return "prescription-update";
	}
	
	@RequestMapping("/update/{id}/{prescriptionId}/{patientId}/{doctorId}/{created}")
	@ResponseBody
	public HmsResult updatePrescription(@PathVariable("id")String id, 
			@PathVariable("prescriptionId")String prescriptionId, @PathVariable("patientId")String patientId, 
			@PathVariable("doctorId")String doctorId, @PathVariable("created")Date created, String paramData){
		try {
			TPrescription prescription = new TPrescription();
			//TDoctor doctor = (TDoctor) request.getSession().getAttribute("doctorInfo");
			//prescription.setPatientId(patientId);
			prescription.setId(Integer.valueOf(id));
			prescription.setPrescriptionId(prescriptionId);
			prescription.setPatientId(patientId);
			prescription.setDoctorId(doctorId);
			prescription.setDrugData(paramData);
			prescription.setCreated(created);
			prescription.setUpdated(new Date());
			
			System.out.println("prescription="+prescription.toString());
			if (prescriptionService.updatePrescription(prescription) > 0) {
				return HmsResult.ok();
			}

		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "修改失败！");
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
			return HmsResult.build(500, "删除失败！");
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
			return HmsResult.build(500, "删除失败！");
		}
		return HmsResult.build(500, "删除失败！");
		

	}

}
