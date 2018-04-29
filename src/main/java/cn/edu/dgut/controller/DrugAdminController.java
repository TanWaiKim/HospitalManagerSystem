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
import cn.edu.dgut.pojo.TbDrugAdmin;
import cn.edu.dgut.service.DrugAdminService;

/**
 * @author TanWaiKim
 * @time 2018年4月13日 下午10:06:35
 * @version 1.0
 */
@Controller
@RequestMapping("drugAdmin")
public class DrugAdminController {
	@Autowired
	private DrugAdminService drugAdminService;
	
	/**
	 * 返回添加药品员页面
	 * @return
	 */
	@RequestMapping("/skipToAdd")
	public String skipToAdd() {
		return "drugAdmin-add";
	}

	/**
	 * 添加一条药品员记录
	 * @param drugAdmin
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addDrugAdminByTbDrugAdmin(TbDrugAdmin drugAdmin, Model model) {
		try {
			if (drugAdmin.getUsername() == null || drugAdmin.getUsername().equals("")) {
				return HmsResult.build(505, "用户名不能为空！");
			}
			
			if (drugAdmin.getPassword() == null || drugAdmin.getPassword().equals("")) {
				return HmsResult.build(505, "密码不能为空！");
			}
			
			if (drugAdmin.getSex() == null || drugAdmin.getSex().equals("")) {
				return HmsResult.build(505, "性别不能为空！");
			}
			
			if (drugAdmin.getEmail() == null || drugAdmin.getEmail().equals("")) {
				return HmsResult.build(505, "邮箱不能为空！");
			}
			
			if (drugAdmin.getPhone() == null || drugAdmin.getPhone().equals("")) {
				return HmsResult.build(505, "手机号码不能为空！");
			}
			
			if (drugAdmin.getAddress() == null || drugAdmin.getAddress().equals("")) {
				return HmsResult.build(505, "住址不能为空！");
			}
			
			if (drugAdmin.getIntro() == null || drugAdmin.getIntro().equals("")) {
				return HmsResult.build(505, "自我简介不能为空！");
			}
			
			if (drugAdminService.addDrugAdminByTbDrugAdmin(drugAdmin) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "出现异常，添加药品员信息失败！");
		}
		return HmsResult.build(500, "不明原因，添加药品员信息失败！");
	}
	
	/**
	 * 药品员列表展示
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String getAllDrugAdmin(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+2);
			page.setCurrentPage(currentPage);
			model.addAttribute("drugAdminList", drugAdminService.getAllDrugAdmin(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "drugAdmin-list";
	}
	
	/**
	 * 条件查询药品员信息
	 * @param username
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition")
	public String getDrugtypeByPage(
			@RequestParam(value = "username", defaultValue = "") String username,
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
			List<TbDrugAdmin> drugAdminList = drugAdminService.pageByCondition(username, page);
			model.addAttribute("drugAdminList", drugAdminList);
			model.addAttribute("page", page);
			model.addAttribute("username", username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "drugAdmin-list";
	}
	
	/**
	 * 根据id查询药品员信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/findById")
	public String getDrugAdminById(@RequestParam(value = "id") Integer id, Model model) {
		TbDrugAdmin drugAdmin = drugAdminService.getDrugAdminById(id);
		model.addAttribute("drugAdmin", drugAdmin);
		return "drugAdmin-update";
	}
	
	/**
	 * 修改药品员信息
	 * @param drugAdmin
	 * @param model
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updateDrugAdminByTbDrugAdmin(TbDrugAdmin drugAdmin, Model model) {
		try {
			if (drugAdmin.getUsername() == null || drugAdmin.getUsername().equals("")) {
				return HmsResult.build(505, "用户名不能为空！");
			}
			
			if (drugAdmin.getPassword() == null || drugAdmin.getPassword().equals("")) {
				return HmsResult.build(505, "密码不能为空！");
			}
			
			if (drugAdmin.getSex() == null || drugAdmin.getSex().equals("")) {
				return HmsResult.build(505, "性别不能为空！");
			}
			
			if (drugAdmin.getEmail() == null || drugAdmin.getEmail().equals("")) {
				return HmsResult.build(505, "邮箱不能为空！");
			}
			
			if (drugAdmin.getPhone() == null || drugAdmin.getPhone().equals("")) {
				return HmsResult.build(505, "手机号码不能为空！");
			}
			
			if (drugAdmin.getAddress() == null || drugAdmin.getAddress().equals("")) {
				return HmsResult.build(505, "住址不能为空！");
			}
			
			if (drugAdmin.getIntro() == null || drugAdmin.getIntro().equals("")) {
				return HmsResult.build(505, "自我简介不能为空！");
			}
			if (drugAdminService.updateDrugAdminByTbDrugAdmin(drugAdmin) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "修改药品员信息失败！");
		}
		return HmsResult.build(500, "修改药品员信息失败！");
	}	
	
	/**
	 * 返回修改密码页面
	 * @return
	 */
	@RequestMapping("/resetDrugPass")
	public String toPassReset(){ 
		return "drugAdmin-pass-reset";
	}
	
	/**
	 * 修改密码
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @param rwPassword
	 * @param model
	 * @return
	 */
	@RequestMapping("/updatePassword")
	@ResponseBody()
	public HmsResult updatePassword(Integer id, String oldPassword,
			String newPassword,String rePassword,Model model) {
		try {
			
			if (oldPassword == null || oldPassword.equals("")) {
				return HmsResult.build(505, "请输入原始密码！");
			}
			
			if (newPassword == null || newPassword.equals("")) {
				return HmsResult.build(505, "请输入新密码！");
			}
			
			if (rePassword == null || rePassword.equals("")) {
				return HmsResult.build(505, "请输入确认密码！");
			}
			
			if (newPassword.length() < 6) {
				return HmsResult.build(505, "新密码长度不能小于6！");
			}
			
			if (!oldPassword.equals(drugAdminService.getDrugAdminById(id).getPassword())) {
				return HmsResult.build(505, "原始密码不正确，请重新输入！");
			}
			
			if (!newPassword.equals(rePassword)) {
				return HmsResult.build(505, "新密码和确认密码不一致，请重新输入！");
			}			
			
			TbDrugAdmin drugAdmin = new TbDrugAdmin();
			drugAdmin.setId(id);
			drugAdmin.setPassword(newPassword);
			if (drugAdminService.updateDrugAdminByTbDrugAdmin(drugAdmin) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "修改密码失败！");
		}
		return HmsResult.build(500, "修改密码失败！");
	}
	
	/**
	 * 返回个人信息页面
	 * @return
	 */
	@RequestMapping("/detail")
	public String DrugAdminDetail(){ 
		return "drugAdmin-detail";
	}
	
	
	
	/**
	 * 删除单条药品员信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteDrugAdminById(Integer id) {
		try {
			if (drugAdminService.deleteDrugAdminById(id) > 0) {

				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除药品员信息失败！");
		}
		
		return HmsResult.build(500, "删除药品员信息失败！");
		
	}

	/**
	 * 批删除药品员信息
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult deleteDrugAdminByIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (drugAdminService.deleteDrugAdminByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除药品员信息失败！");
		}
		return HmsResult.build(500, "删除药品员信息失败！");
		
	}
}
