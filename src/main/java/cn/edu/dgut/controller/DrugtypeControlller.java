package cn.edu.dgut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author TanWaiKim
 * @time 2018年1月25日 上午8:20:00
 * @version 1.0
 */
@Controller
@RequestMapping("/drugtype")
public class DrugtypeControlller {
	
	
	@RequestMapping("/skipToAdd")
	public String skipToAdd() {
		return "drugtype-add";
	}

	
}
