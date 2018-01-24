package cn.edu.dgut.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.mapper.TDoctorMapper;


import cn.edu.dgut.pojo.TDoctor;
import cn.edu.dgut.pojo.TDoctorExample;
import cn.edu.dgut.service.UserService;
/*
 * service层实现业务逻辑
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private TDoctorMapper doctorMapper;
	
	
	public HmsResult login(String username, String password, String postOffice, HttpServletRequest request) {
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
					request.getSession().setAttribute("doctorLoginName", username);
					return HmsResult.build(200, "医生登录成功");
				}else{
					//密码不相等
					return HmsResult.build(500, "密码错误！");
				}
						
			}
			//账号不一致
			return HmsResult.build(500, "账号错误！");
		}
	
		
		return HmsResult.build(500, "登录类型错误！");
	}

}