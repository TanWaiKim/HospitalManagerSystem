package cn.edu.dgut.service;

import javax.servlet.http.HttpServletRequest;

import cn.edu.dgut.common.result.HmsResult;

public interface UserService {
	HmsResult login(String username, String password, String postOffice,HttpServletRequest request);
}
