package cn.edu.dgut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.mapper.TbAdminMapper;
import cn.edu.dgut.pojo.TbAdmin;
import cn.edu.dgut.service.AdminService;

/**
 * @author TanWaiKim
 * @time 2018年2月2日 下午10:28:33
 * @version 1.0
 */
@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
	private TbAdminMapper adminMapper;
	
	@Override
	public TbAdmin login(String username, String password) {
		TbAdmin admin  = adminMapper.selectLogin(username,password);
		return admin;
	}

}
