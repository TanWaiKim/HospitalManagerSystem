package cn.edu.dgut.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AdminController {
	
	@RequestMapping("/mainAdmin")
	public String toMainAdmin(){
		return "mainAdmin";
	}
	
	@RequestMapping("alogout")
	public String logout(HttpSession session) {
		session.removeAttribute("adminInfo");
		return "login";
	}

}
