package cn.edu.dgut.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TMedicalcourses;
import cn.edu.dgut.service.MedicalcoursesService;

@Controller
@RequestMapping("/medicalcourses")
public class MedicalcoursesController {
	
	@Autowired
	private MedicalcoursesService medicalcoursesService;
	
	@RequestMapping("/list")
	public String getAllMedicalcourses(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setCurrentPage(currentPage);
			model.addAttribute("medicalcoursesList", medicalcoursesService.getAllMedicalcourses(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "medicalcourses-list";
	}
	

	@RequestMapping("/pageByCondition")
	public String getMedicalcoursesByPage(@RequestParam(value = "name", defaultValue = "") String name,
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
			List<TMedicalcourses> medicalcoursesList = medicalcoursesService.pageByCondition(name, page);
			model.addAttribute("medicalcoursesList", medicalcoursesList);
			model.addAttribute("page", page);	
			model.addAttribute("name", name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "medicalcourses-list";
	}
	
	
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteMedicalcoursesById(String id) {
		try {
			if (medicalcoursesService.deleteMedicalcoursesById(Long.valueOf(id).longValue()) > 0) {
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
	public HmsResult deleteMedicalcoursesByIds(String ids) {
		String[] idArray = ids.split(",");
		try {
			if (medicalcoursesService.deleteMedicalcoursesByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "系统错误,删除失败！");
		}
		
		return HmsResult.build(500, "删除失败！");
		
	}
	
	@RequestMapping("/findById")
	public String getMedicalcoursesById(@RequestParam(value = "id") String id, Model model) {
		TMedicalcourses medicalcourses = medicalcoursesService.getMedicalcoursesById(id);
		model.addAttribute("medicalcourses", medicalcourses);
		return "medicalcourses-update";
	}

	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updateMedicalcoursesByTMedicalcourses(TMedicalcourses medicalcourses, Model model) {
		try {
			
			if (medicalcoursesService.updateMedicalcoursesByTMedicalcourses(medicalcourses) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "系统错误：修改失败！");
		}
		return HmsResult.build(500, "修改失败！");
	}

	@RequestMapping("skipToAdd")
	public String skipToAdd() {
		return "medicalcourses-add";
	}

	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addMedicalcoursesByTMedicalcourses(TMedicalcourses medicalcourses, Model model) {
		try {
			if (medicalcoursesService.addMedicalcoursesByTMedicalcourses(medicalcourses) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "系统错误，添加失败！");
		}
		return HmsResult.build(500, "添加失败！");
	}
	
			
}
