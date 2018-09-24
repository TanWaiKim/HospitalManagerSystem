package cn.edu.dgut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author TanWaiKim
 * @time 2018年5月9日 上午8:10:35
 * @version 1.0
 */
@Controller
public class ShowPageController {

  @RequestMapping("/{page}")
  public String showPage(@PathVariable String page) {
       return page;
   }
 }