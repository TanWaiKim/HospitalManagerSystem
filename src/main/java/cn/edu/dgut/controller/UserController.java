package cn.edu.dgut.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.common.util.Const;
import cn.edu.dgut.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public String index(){
		return "login";
	}
	
	@RequestMapping("/login")
	@ResponseBody()
	public HmsResult login(String username, String password, String postOffice,HttpServletRequest request, HttpSession session){
		try{
			return userService.login(username, password, postOffice, request, session);
		}catch(Exception e){
			ExceptionUtil.getStackTrace(e);
			return HmsResult.build(505, "系统错误");
		}
	}
	

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute(Const.CURRENT_USER);
		return "login";
	}
	
}
