package cn.edu.dgut.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TStayHospital;
import cn.edu.dgut.service.PatientService;
import cn.edu.dgut.service.SickbedService;
import cn.edu.dgut.service.StayHospitalService;

@Controller
@RequestMapping("/stayHospital")
public class StayHospitalController {

	@Autowired
	private StayHospitalService stayHospitalService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private SickbedService sickbedService;
	
	@RequestMapping("/list")
	public String getAllStayHospital(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setCurrentPage(currentPage);
			model.addAttribute("stayHospitalList", stayHospitalService.getAllStayHospital(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "stayHospital-list";
	}

	@RequestMapping("/pageByCondition")
	public String getStayHospitalByPage(@RequestParam(value = "sickbedId", defaultValue = "") String sickbedId,
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
			List<TStayHospital> stayHospitalList = stayHospitalService.pageByCondition(sickbedId,patientName, page);
			model.addAttribute("stayHospitalList", stayHospitalList);
			model.addAttribute("page", page);
			model.addAttribute("sickbedId", sickbedId);
			model.addAttribute("patientName", patientName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "stayHospital-list";
	}
	
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteStayHospitalById(String id) {
		try {
			if (stayHospitalService.deleteStayHospitalById(Long.valueOf(id).longValue()) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "系统错误,删除失败！");
		}
		
		return HmsResult.build(500, "删除失败！");
		
	}
	
	@RequestMapping("/deleteBatch")
	@ResponseBody
	public HmsResult deleteStayHospitalByIds(String ids) {
		String[] idArray = ids.split(",");
		try {
			if (stayHospitalService.deleteStayHospitalByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "系统错误,删除失败！");
		}
		
		return HmsResult.build(500, "删除失败！");
		
	}
	
	@RequestMapping("/skipToAdd")
	public String skipToAdd(Model model) {
		return "stayHospital-add";
	}

	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addStayHospitalByTStayHospital(TStayHospital StayHospital, Model model) {
		try {
			
			if (stayHospitalService.addStayHospitalByTStayHospital(StayHospital) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "系统错误，添加失败！");
		}
		return HmsResult.build(500, "添加失败！");
	}
	
	@RequestMapping(value = "/patientId/auto")
	@ResponseBody
	public String autoPatientId(@RequestParam("term") String term, HttpServletResponse response){
		String pIs = null;
		try {
			pIs = patientService.autoPatientId(term,response);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return pIs;
	}
	
	@RequestMapping(value = "/sickbedId/auto")
	@ResponseBody
	public String autoSickbedId(@RequestParam("term") String term, HttpServletResponse response){
		String sIs = null;
		try {
			sIs = sickbedService.autoSickbedId(term,response);
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		return sIs;
	}
}
