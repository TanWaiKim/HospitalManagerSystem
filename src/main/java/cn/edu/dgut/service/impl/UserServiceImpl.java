package cn.edu.dgut.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.Const;
import cn.edu.dgut.mapper.TDoctorMapper;


import cn.edu.dgut.pojo.TDoctor;
import cn.edu.dgut.pojo.TDoctorExample;
import cn.edu.dgut.pojo.TbAdmin;
import cn.edu.dgut.service.AdminService;
import cn.edu.dgut.service.UserService;
/*
 * service层实现业务逻辑
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TDoctorMapper doctorMapper;
	
	@Autowired
	private AdminService adminService;
	
	
	public HmsResult login(String username, String password, String postOffice, HttpServletRequest request, HttpSession session) {
		if(postOffice.equals("医生")){
			//条件查询  通过用户名查询记录 
			TDoctorExample example = new TDoctorExample();
			example.createCriteria().andLoginNameEqualTo(username);
			List<TDoctor> doctorList = doctorMapper.selectByExample(example);
			//验证密码
			if(doctorList.size()>0){
				//对输入的密码进行验证
				if(doctorList.get(0).getLoginPassword().equals(password)){
					//密码相同
					TbAdmin admin = new TbAdmin();
					admin.setUsername(doctorList.get(0).getName());
					session.setAttribute(Const.CURRENT_USER, admin);
					request.getSession().setAttribute("doctorInfo", doctorList.get(0));
					return HmsResult.build(200, "医生登录成功");
				}else{
					//密码不相等
					return HmsResult.build(500, "密码错误！");
				}
						
			}
			//账号不一致
			return HmsResult.build(500, "账号错误！");
		} else if(postOffice.equals("医药管理员")){
			//条件查询  通过用户名查询记录 
			TbAdmin admin = adminService.login(username, password);
			//验证密码
			if(admin != null){
				session.setAttribute(Const.CURRENT_USER, admin);
				return HmsResult.build(200, "医药管理员登录成功");		
			}
			//账号不一致
			return HmsResult.build(500, "账号或密码错误！");
		}
		
		return HmsResult.build(500, "登录类型错误！");
	}

}
