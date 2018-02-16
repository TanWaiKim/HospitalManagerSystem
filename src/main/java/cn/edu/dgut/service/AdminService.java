package cn.edu.dgut.service;

import cn.edu.dgut.pojo.TbAdmin;

/**
 * @author TanWaiKim
 * @time 2018年2月2日 下午10:27:10
 * @version 1.0
 */
public interface AdminService {
	TbAdmin login(String username, String password);
}
