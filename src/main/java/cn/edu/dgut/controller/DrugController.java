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

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbDrugtype;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.DrugtypeService;

/**
 * @author TanWaiKim
 * @time 2018年1月26日 上午6:23:27
 * @version 1.0
 */
@Controller
@RequestMapping("/drug")
public class DrugController {
	@Autowired
	private DrugtypeService drugtypeService;
	
	@Autowired
	private DrugService drugService;
	
	/**
	 * 返回添加医药信息页面
	 * @return
	 */
	@RequestMapping("/skipToAdd")
	public String skipToAdd(Model model) {
		List<TbDrugtype> drugtypeList = drugtypeService.selectAllDrugtype();
		model.addAttribute("drugtypeList", drugtypeList);
		return "drug-add";
	}
	
	/**
	 * 处理添加医药信息请求
	 * @param drug
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addDrugByTbDrug(TbDrug drug, Model model) {
		try {
			if (drug.getDrugtypeId() == null || drug.getDrugtypeId() == 0) {
				return HmsResult.build(505, "医药种类不能为空！");
			}
			
			if (drug.getDrugName() == null || drug.getDrugName().equals("")) {
				return HmsResult.build(505, "医药名称不能为空！");
			}
			
			if (drug.getPurpose() == null || drug.getPurpose().equals("")) {
				return HmsResult.build(505, "功能主治不能为空！");
			}
			
			if (drug.getUnit() == null || drug.getUnit().equals("")) {
				return HmsResult.build(505, "单位不能为空！");
			}
			
			if (drug.getSpec() == null || drug.getSpec().equals("")) {
				return HmsResult.build(505, "规格不能为空！");
			}
			
			if (drug.getHowuse() == null || drug.getHowuse().equals("")) {
				return HmsResult.build(505, "用法用量不能为空！");
			}
			
			if (drug.getDrugNo() == null || drug.getDrugNo().equals("")) {
				return HmsResult.build(505, "批准文号不能为空！");
			}
			
			if (drug.getUneffect() == null || drug.getUneffect().equals("")) {
				return HmsResult.build(505, "不良反应不能为空！");
			}
			
			if (drugService.addDrugByTbDrug(drug) > 0) {
				return HmsResult.ok();
			}

		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "数据库异常，添加医药信息失败！");
		}
		return HmsResult.build(500, "添加医药信息失败！");
	}
	
	/**
	 * 医药信息列表页面
	 * @param currentPage
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String getAllDrug(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model,HttpServletRequest request) {
		try {
			Page page = new Page();
			page.setCurrentPage(currentPage);
			List<TbDrug> drugList = drugService.getAllDrug(page);
			model.addAttribute("drugList", drugList);
			model.addAttribute("page", page);
			List<TbDrugtype> drugtypeList = drugtypeService.selectAllDrugtype();
			model.addAttribute("drugtypeList", drugtypeList);
			model.addAttribute("drugtypeName1", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "drug-list";
	}
	

	/**
	 * 条件查询医药信息
	 * @param drugtypeId
	 * @param drugName
	 * @param drugNo
	 * @param keywords
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition")
	public String getDrugByPage(
			@RequestParam(value = "drugtypeId", defaultValue = "") Integer drugtypeId,
			@RequestParam(value = "drugName", defaultValue = "") String drugName,
			@RequestParam(value = "drugNo", defaultValue = "") String drugNo,
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
			List<TbDrug> drugList = drugService.pageByCondition(drugtypeId, drugName, drugNo, keywords, page);
			
			TbDrugtype drugTypeCondition = drugtypeService.getDrugtypeById(drugtypeId);
			model.addAttribute("drugTypeCondition", drugTypeCondition);
			
			TbDrugtype drugtype = drugtypeService.getDrugtypeById(drugtypeId);
			model.addAttribute("drugType", drugtype);	
			
			model.addAttribute("drugList", drugList);
			model.addAttribute("page", page);
			model.addAttribute("keywords", keywords);
			model.addAttribute("drugtypeId", drugtypeId);
			model.addAttribute("drugName", drugName);
			model.addAttribute("drugNo", drugNo);
			List<TbDrugtype> drugtypeList = drugtypeService.selectAllDrugtype();
			model.addAttribute("drugtypeList", drugtypeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "drug-list";
	}
	
	/**
	 * 根据id查询医药信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/findById")
	public String getDrugById(@RequestParam(value = "id") Integer id, Model model) {
		TbDrug drug = drugService.getDrugById(id);
//		drug.setProducedTime(drug.getProducedTime().substring(0, drug.getProducedTime().indexOf('.')-9));
//		drug.setValidTime(drug.getValidTime().substring(0, drug.getValidTime().indexOf('.')-9));
		model.addAttribute("drug", drug);
		List<TbDrugtype> drugtypeList = drugtypeService.selectAllDrugtype();
		model.addAttribute("drugtypeList", drugtypeList);
		return "drug-update";
	}
	
	/**
	 * 修改医药信息
	 * @param drug
	 * @param model
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updateDrugByTbDrug(TbDrug drug, Model model) {
		
		try {
			if (drugService.updateDrugByTbDrug(drug) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "数据库异常，修改医药信息失败！");
		}
		return HmsResult.build(500, "修改医药信息失败！");
	}	
	
	/**
	 * 删除单条医药信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteDrugById(Integer id) {
		try {
			if (drugService.deleteDrugById(id) > 0) {

				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "数据库异常，删除医药信息失败！");
		}
		
		return HmsResult.build(500, "删除医药信息失败！");
		
	}

	/**
	 * 批删除医药信息
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult deleteDrugByIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (drugService.deleteDrugByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "数据库异常，删除医药信息失败！");
		}
		return HmsResult.build(500, "删除医药信息失败！");
		
	}	
}
