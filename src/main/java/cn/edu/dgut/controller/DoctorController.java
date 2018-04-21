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
import cn.edu.dgut.pojo.Password;
import cn.edu.dgut.pojo.TDoctor;
import cn.edu.dgut.service.DoctorService;
import cn.edu.dgut.service.MedicalcoursesService;

@Controller
public class DoctorController {
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private MedicalcoursesService medicalcoursesService;
	
	@RequestMapping("/mainDoctor")
	public String mainDoctor(){
		return "mainDoctor";
	}
	
	@RequestMapping("/mainDrug")
	public String mainDrug(){
		return "mainDrug";
	}
	
	@RequestMapping("/dPassReset")
	public String toPassReset(){
		return "doctor-pass-reset";
	}
	
	@RequestMapping(value="/resetPass", method = RequestMethod.POST)
	@ResponseBody()
	public HmsResult resetPass(Password password,HttpServletRequest request){
		try{
			TDoctor doctorInfo = (TDoctor) request.getSession().getAttribute("doctorInfo");
			if(password.getMpass().equals("") ||password.getMpass() == null){
				return HmsResult.build(505, "原始密码不能为空");
			}
			TDoctor doctor = doctorService.selectByDoctorName(doctorInfo.getLoginName(),password.getMpass());
			if(doctor != null){

				if(password.getNewpass().equals("") ||password.getNewpass() == null){
					return HmsResult.build(505, "新密码不能为空");
				}
				if(password.getRenewpass().equals("") || password.getRenewpass() ==null){
					return HmsResult.build(505, "确认密码不能为空");
				}
				if(password.getNewpass().length()<5){
					return HmsResult.build(505, "新密码长度必须5位以上");
				}
				if(!password.getNewpass().equals(password.getRenewpass())){
					return HmsResult.build(505, "两次密码不一致");
				}
				if(doctorService.updatePassword(doctorInfo.getId(),password.getNewpass())>0){
					return HmsResult.ok();
				}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			return HmsResult.build(500, "系统错误！");
		}
		return HmsResult.build(505, "原始密码错误");
	}
	
	@RequestMapping(value="/personalInfo", method = RequestMethod.GET)
	public String personalInfo(HttpServletRequest request,Model model){
		TDoctor doctor = (TDoctor) request.getSession().getAttribute("doctorInfo");
		model.addAttribute("doctor",doctor);
		return "doctor-detail";
	}
	
	
	@RequestMapping("/doctor/list")
	public String getAllDoctor(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setCurrentPage(currentPage);
			model.addAttribute("doctorList", doctorService.getAllDoctor(page));
			model.addAttribute("medicalcoursesNameList", medicalcoursesService.getAllMedicalcoursesName());
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "doctor-list";
	}

	@RequestMapping("/doctor/pageByCondition")
	public String getDoctorByPage(@RequestParam(value = "doctorId", defaultValue = "") String doctorId,
			@RequestParam(value = "name", defaultValue = "") String name,
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
			List<TDoctor> doctorList = doctorService.pageByCondition(doctorId, name, mcName, keywords, page);
			model.addAttribute("doctorList", doctorList);
			model.addAttribute("medicalcoursesNameList", medicalcoursesService.getAllMedicalcoursesName());
			model.addAttribute("page", page);
			model.addAttribute("keywords", keywords);
			model.addAttribute("doctorId", doctorId);
			model.addAttribute("name", name);
			model.addAttribute("mcName", mcName);
			model.addAttribute("keywords", keywords);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "doctor-list";
	}
	
	@RequestMapping("/doctor/deleteOne")
	@ResponseBody
	public HmsResult deleteDoctorById(String id) {
		try {
			if (doctorService.deleteDoctorById(Long.valueOf(id).longValue()) > 0) {

				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "系统错误,删除失败！");
		}
		
		return HmsResult.build(500, "删除失败！");
		
	}
	
	@RequestMapping("/doctor/deleteBatch")
	@ResponseBody
	public HmsResult deleteDoctorByIds(String ids) {
		String[] idArray = ids.split(",");
		try {
			if (doctorService.deleteDoctorByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "系统错误,删除失败！");
		}
		
		return HmsResult.build(500, "删除失败！");
		
	}
	
	@RequestMapping("/doctor/findById")
	public String getDoctorById(@RequestParam(value = "doctorId") String doctorId, Model model) {
		TDoctor doctor = doctorService.getDoctorById(doctorId);
		model.addAttribute("medicalcoursesNameList", medicalcoursesService.getAllMedicalcoursesName());
		model.addAttribute("doctor", doctor);
		return "doctor-update";
	}

	@RequestMapping("/doctor/update")
	@ResponseBody()
	public HmsResult updateDoctorByTDoctor(TDoctor doctor, Model model) {
		try {
			// 先通过doctor_id获取更新之前的数据，为了与后面的phone对比
			// 更新前后doctor_id都不会变
			TDoctor doctor1 = doctorService.getDoctorByPhone(doctor.getPhone());
			if(doctor1 != null){
				if(doctor1.getId() != doctor.getId()){
					return HmsResult.build(505, "手机号码已存在！");
				}
			}
			if(doctor.getLoginPassword().length()<5){
				return HmsResult.build(505, "登录密码必须为5位以上！");
			}
			if (doctorService.updateDoctorByTDoctor(doctor) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "系统错误：修改失败！");
		}
		return HmsResult.build(500, "修改失败！");
	}

	@RequestMapping("/doctor/skipToAdd")
	public String skipToAdd(Model model) {
		model.addAttribute("medicalcoursesNameList", medicalcoursesService.getAllMedicalcoursesName());
		return "doctor-add";
	}

	@RequestMapping("/doctor/add")
	@ResponseBody()
	public HmsResult addDoctorByTDoctor(TDoctor doctor, Model model) {
		try {
			System.out.println("doctor="+doctor.getLoginName());
			if (doctorService.getDoctorByPhone(doctor.getPhone()) != null) {
				return HmsResult.build(505, "手机号码已存在！");
			}

			if (doctorService.getDoctorByLoginName(doctor.getLoginName()) != null) {
				return HmsResult.build(505, "登录账号已存在,请重新输入!");
			}
			if(doctor.getLoginPassword().length()<5){
				return HmsResult.build(505, "登录密码必须为5位以上！");
			}
			if (doctorService.addDoctorByTDoctor(doctor) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "系统错误，添加失败！");
		}
		return HmsResult.build(500, "添加失败！");
	}
	
	@RequestMapping("/dLogout")
	public String logout(HttpServletRequest request) {
		request.getSession().setAttribute("doctorInfo", "");
		return "login";
	}

}
