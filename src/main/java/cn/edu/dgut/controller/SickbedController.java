package cn.edu.dgut.controller;

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
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.Password;
import cn.edu.dgut.pojo.TSickbed;
import cn.edu.dgut.service.MedicalcoursesService;
import cn.edu.dgut.service.SickbedService;

@Controller
@RequestMapping("/sickbed")
public class SickbedController {

	@Autowired
	private SickbedService sickbedService;
	
	@RequestMapping("/list")
	public String getAllSickbed(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setCurrentPage(currentPage);
			model.addAttribute("sickbedList", sickbedService.getAllSickbed(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sickbed-list";
	}

	@RequestMapping("/pageByCondition")
	public String getSickbedByPage(@RequestParam(value = "id", defaultValue = "") String id,
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
			List<TSickbed> sickbedList = sickbedService.pageByCondition(id, page);
			model.addAttribute("sickbedList", sickbedList);
			model.addAttribute("page", page);
			model.addAttribute("id", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sickbed-list";
	}
	
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteSickbedById(String id) {
		try {
			if (sickbedService.deleteSickbedById(Long.valueOf(id).longValue()) > 0) {
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
	public HmsResult deleteSickbedByIds(String ids) {
		String[] idArray = ids.split(",");
		try {
			if (sickbedService.deleteSickbedByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "系统错误,删除失败！");
		}
		
		return HmsResult.build(500, "删除失败！");
		
	}
	
	@RequestMapping("/findById/{id}")
	public String getsickbedById(@PathVariable("id") long id, Model model) {
		TSickbed sickbed = sickbedService.getSickbedById(id);
		model.addAttribute("sickbed", sickbed);
		return "sickbed-update";
	}

	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updateSickbedByTSickbed(TSickbed sickbed, Model model) {
		try {
			if (sickbedService.updateSickbedByTSickbed(sickbed) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "系统错误：修改失败！");
		}
		return HmsResult.build(500, "修改失败！");
	}

	@RequestMapping("/skipToAdd")
	public String skipToAdd(Model model) {
		return "sickbed-add";
	}

	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addSickbedByTsickbed(TSickbed sickbed, Model model) {
		try {
			if (sickbedService.addSickbedByTSickbed(sickbed) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "系统错误，添加失败！");
		}
		return HmsResult.build(500, "添加失败！");
	}
	
	
}
