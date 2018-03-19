package cn.edu.dgut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DoctorController {
	@RequestMapping("/mainDoctor")
	public String mainDoctor(){
		return "mainDoctor";
	}
	
	@RequestMapping("/mainDrug")
	public String mainDrug(){
		return "mainDrug";
	}
}
