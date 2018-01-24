package cn.edu.dgut.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TDiagnosis;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.service.DiagnosisService;
import cn.edu.dgut.service.PatientService;

@Controller
@RequestMapping("/diagnosis")
public class DiagnosisController {

	@Autowired
	private DiagnosisService diagnosisService;
	@Autowired 
	private PatientService patientService;
	

	@RequestMapping("/list")
	public String getAllDiagnosis(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model,HttpServletRequest request) {
		try {
			Page page = new Page();
			page.setCurrentPage(currentPage);
			model.addAttribute("diagnosisList", diagnosisService.getAllDiagnosis(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "diagnosis-list";
	}

	@RequestMapping("/pageByCondition")
	public String getDiagnosisByPage(
			@RequestParam(value = "patientName", defaultValue = "") String patientName,
			@RequestParam(value = "symptom", defaultValue = "") String symptom,
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
			List<TDiagnosis> diagnosisList = diagnosisService.pageByCondition(patientName, symptom, page);
			model.addAttribute("diagnosisList", diagnosisList);
			model.addAttribute("page", page);
			model.addAttribute("patientName", patientName);
			model.addAttribute("symptom", symptom);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "diagnosis-list";
	}

	@RequestMapping("/findById/{diagnosisId}/{patientName}")
	public String getDiagnosisById(@PathVariable("diagnosisId") long diagnosisId, @PathVariable(value = "patientName") String patientName, Model model) {
		TDiagnosis diagnosis = diagnosisService.getDiagnosisByDId(diagnosisId,patientName);
		model.addAttribute("diagnosis", diagnosis);
		return "diagnosis-update";
	}

	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updateDiagnosisByTDiagnosis(TDiagnosis diagnosis, Model model) {
		try {
			if(diagnosisService.updateDiagnosisByTDiagnosis(diagnosis)>0){
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "修改失败！");
		}
		return HmsResult.build(500, "修改失败！");
	}

	@RequestMapping("/skipToAdd")
	public String skipToAdd() {
		return "diagnosis-add";
	}

	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addPatientByTPatient(TPatient patient, Model model) {
		try {
			// System.out.println("Patient=" + patient);
			if (patient.getPhone() == null) {
				return HmsResult.build(505, "手机号码不能为空！");
			}
			if (patient.getPhone() != null && patient.getPhone().length() != 11) {
				return HmsResult.build(505, "手机号码格式错误！(11位数字)");
			}
			if (patientService.getPatientByPhone(patient.getPhone()) != null) {
				return HmsResult.build(505, "手机号码已存在！");
			}

			if (patientService.isSimpleLoginName(patient.getLoginName())) {
				return HmsResult.build(505, "病人登录账号已存在,请重新输入!");
			}
			if (patientService.addPatientByTPatient(patient) > 0) {
				return HmsResult.ok();
			}

		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "添加失败！");
		}
		return HmsResult.build(500, "添加失败！");
	}

	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deletePatientById(String id) {
		System.out.println("id=" + id);
		try {
			if (patientService.deletePatientById(Long.valueOf(id).longValue()) > 0) {

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
	public HmsResult deletePatientByIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (patientService.deletePatientByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除病人记录失败！");
		}
		return HmsResult.build(500, "删除病人记录失败！");
		

	}

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult exportBatch(String ids) {
		String[] idArray = ids.split(",");
		try {
			patientService.export(idArray);
			return HmsResult.ok();
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "导出失败！");
		}
	}

	@RequestMapping("/import")
	@ResponseBody
	public HmsResult importBatch(MultipartFile uploadFile,HttpServletRequest request) throws Exception {
		try {
			// 数据导入
			patientService.importExcelInfo(uploadFile);
			return HmsResult.ok();
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "导入失败！");
		}
	}
}


