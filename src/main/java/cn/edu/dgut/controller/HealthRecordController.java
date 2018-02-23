package cn.edu.dgut.controller;

import java.util.List;
import java.util.regex.Pattern;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TDiagnosis;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.service.DiagnosisService;
import cn.edu.dgut.service.PatientService;

@Controller
@RequestMapping("/healthRecord")
public class HealthRecordController {
	
	@Autowired
	private DiagnosisService diagnosisService;

	@Autowired
	private PatientService patientService;
	@RequestMapping("/list")
	public String getDiagnosis(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setCurrentPage(currentPage);
			model.addAttribute("diagnosisList", diagnosisService.getAllDiagnosis(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "healthRecord-list";
	}

	@RequestMapping("/pageByCondition")
	public String getDiagnosisByPage(
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
			List<TDiagnosis> diagnosisList = diagnosisService.pageByPatientName(patientName, page);
			model.addAttribute("diagnosisList", diagnosisList);
			model.addAttribute("page", page);
			model.addAttribute("patientName", patientName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "healthRecord-list";
	}
	
	
	@RequestMapping("/findById/{patientId}")
	public String getHealthRecordByPatientId(@PathVariable("patientId") String patientId, Model model) {
		TPatient patient = patientService.getHealthRecordByPId(patientId);
		model.addAttribute("patient", patient);
		return "healthRecord-detail";
	}
}
