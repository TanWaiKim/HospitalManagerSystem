package cn.edu.dgut.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.service.PatientService;

@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@RequestMapping("/list")
	public String getAllPatient(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setCurrentPage(currentPage);
			model.addAttribute("patientList", patientService.getAllPatient(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "patient-list";
	}

	@RequestMapping("/pageByCondition")
	public String getPatientByPage(@RequestParam(value = "patientId", defaultValue = "") String patientId,
			@RequestParam(value = "patientName", defaultValue = "") String patientName,
			@RequestParam(value = "mcName", defaultValue = "") String mcName,
			@RequestParam(value = "keywords", defaultValue = "") String keywords,
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
			List<TPatient> patientList = patientService.pageByCondition(patientId, patientName, mcName, keywords, page);
			model.addAttribute("patientList", patientList);
			model.addAttribute("page", page);
			model.addAttribute("keywords", keywords);
			model.addAttribute("patientId", patientId);
			model.addAttribute("patientName", patientName);
			model.addAttribute("mcName", mcName);
			model.addAttribute("keywords", keywords);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "patient-list";
	}

	@RequestMapping("/findById")
	public String getPatientById(@RequestParam(value = "patientId") String patientId, Model model) {
		TPatient patient = patientService.getPatientById(patientId);
		model.addAttribute("patient", patient);
		return "patient-update";
	}

	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updatePatientByTPatient(TPatient patient, Model model) {
		try {
			// 先通过patient_id获取更新之前的数据，为了与后面的phone对比
			// 更新前后patient_id都不会变
			TPatient patient1 = patientService.getPatientById(patient.getPatientId());
			TPatient patient2 = patientService.getPatientByPhone(patient.getPhone());
			
			if (patient2 != null) {
				if (!patient2.getPhone().equals(patient1.getPhone())) {
					return HmsResult.build(505, "手机号码已存在！");
				}
			}
			if (patientService.updatePatientByTPatient(patient) > 0) {
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
		return "patient-add";
	}

	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addPatientByTPatient(TPatient patient, Model model) {
		try {
			
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
