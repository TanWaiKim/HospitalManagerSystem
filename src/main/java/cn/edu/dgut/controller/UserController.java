package cn.edu.dgut.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.common.result.HmsResult;
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
	public HmsResult login(String username, String password, String postOffice,HttpServletRequest request){
		//System.out.println("username:"+username+"\n password:"+password+"\n postOffice:"+postOffice);
		return userService.login(username, password, postOffice, request);
	}
}
