package cn.edu.dgut.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.edu.dgut.common.result.HmsResult;

public interface UserService {
	HmsResult login(String username, String password, String postOffice,HttpServletRequest request, HttpSession session);
}
