package cn.edu.dgut.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrugtype;
import cn.edu.dgut.service.DrugtypeService;

/**
 * @author TanWaiKim
 * @time 2018年1月25日 上午8:20:00
 * @version 1.0
 */
@Controller
@RequestMapping("/drugtype")
public class DrugtypeControlller {
	
	@Autowired
	private DrugtypeService drugtypeService;
	
	/**
	 * 返回添加医药种类页面
	 * @return
	 */
	@RequestMapping("/skipToAdd")
	public String skipToAdd() {
		return "drugtype-add";
	}

	/**
	 * 添加一条医药种类记录
	 * @param drugtype
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addDrugtypeByTbDrugtype(TbDrugtype drugtype, Model model) {
		try {
			if (drugtype.getDrugtypeName() == null || drugtype.getDrugtypeName().equals("")) {
				return HmsResult.build(505, "医药种类名称不能为空！");
			}
			
			if (drugtype.getRemarks() == null || drugtype.getRemarks().equals("")) {
				return HmsResult.build(505, "医药种类简介不能为空！");
			}
			
			if (drugtype.getRemarks() != null && drugtype.getRemarks().length() > 80) {
				return HmsResult.build(505, "种类简介不能超过80个字！");
			}
			
			if (drugtypeService.addDrugtypeByTbDrugtype(drugtype) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "出现异常，添加失败！");
		}
		return HmsResult.build(500, "不明原因，添加失败！");
	}
	
	/**
	 * 医药种类列表展示
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String getAllDrugtype(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+2);
			page.setCurrentPage(currentPage);
			model.addAttribute("drugtypeList", drugtypeService.getAllDrugtype(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "drugtype-list";
	}
	
	/**
	 * 条件查询医药种类信息
	 * @param drugtypeName
	 * @param keywords
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition")
	public String getDrugtypeByPage(
			@RequestParam(value = "drugtypeName", defaultValue = "") String drugtypeName,
			@RequestParam(value = "keywords", defaultValue = "") String keywords,
			@RequestParam(value = "currentPage", defaultValue = "") String currentPage, Model model) {
		try {
			// 创建分页对象
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+2);
			Pattern pattern = Pattern.compile("[0-9]{1,9}");
			if (currentPage == null || !pattern.matcher(currentPage).matches()) {
				page.setCurrentPage(1);
			} else {
				page.setCurrentPage(Integer.valueOf(currentPage));
			}
			List<TbDrugtype> drugtypeList = drugtypeService.pageByCondition(drugtypeName, keywords, page);
			model.addAttribute("drugtypeList", drugtypeList);
			model.addAttribute("page", page);
			model.addAttribute("keywords", keywords);
			model.addAttribute("drugtypeName", drugtypeName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "drugtype-list";
	}
	
	/**
	 * 根据id查询医药种类信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/findById")
	public String getDrugtypeById(@RequestParam(value = "id") Integer id, Model model) {
		TbDrugtype drugtype = drugtypeService.getDrugtypeById(id);
		model.addAttribute("drugtype", drugtype);
		return "drugtype-update";
	}
	
	/**
	 * 修改医药种类信息
	 * @param drugtype
	 * @param model
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updateDrugtypeByTbDrugtype(TbDrugtype drugtype, Model model) {
		try {
			if (drugtype.getDrugtypeName() == null || drugtype.getDrugtypeName().equals("")) {
				return HmsResult.build(505, "医药种类名称不能为空！");
			}
			if (drugtype.getRemarks() == null || drugtype.getRemarks().equals("")) {
				return HmsResult.build(505, "医药种类简介不能为空！");
			}
			
			if (drugtype.getRemarks() != null && drugtype.getRemarks().length() > 80) {
				return HmsResult.build(505, "种类简介不能超过80个字！");
			}
			
			if (drugtypeService.updateDrugtypeByTbDrugtype(drugtype) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "修改失败！");
		}
		return HmsResult.build(500, "修改失败！");
	}	
	
	/**
	 * 删除单条医药种类信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteDrugtypeById(Integer id) {
		try {
			if (drugtypeService.deleteDrugtypeById(id) > 0) {

				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除失败！");
		}
		
		return HmsResult.build(500, "删除失败！");
		
	}

	/**
	 * 批删除医药种类信息
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult deleteDrugtypeByIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (drugtypeService.deleteDrugtypeByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除医药种类失败！");
		}
		return HmsResult.build(500, "删除医药种类失败！");
		
	}
	
}
