package cn.edu.dgut.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.common.result.HmsResult;
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

	
	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addDrugtypeByTbDrugtype(TbDrugtype drugtype, Model model) {
		try {
			if (drugtype.getDrugtypeName() == null) {
				return HmsResult.build(505, "医药种类名称不能为空！");
			}
			if (drugtype.getRemarks() == null) {
				return HmsResult.build(505, "医药种类简介不能为空！");
			}
			
			System.out.println("种类名字："+drugtype.getDrugtypeName());
			System.out.println("种类简介："+drugtype.getRemarks());
			
			if (drugtypeService.addDrugtypeByTbDrugtype(drugtype) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "出现异常，添加失败！");
		}
		return HmsResult.build(500, "不明原因，添加失败！");
	}
	
}
