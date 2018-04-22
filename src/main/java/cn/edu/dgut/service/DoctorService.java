package cn.edu.dgut.service;

import java.util.List;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TDoctor;

public interface DoctorService {

	TDoctor selectByDoctorName(String loginName, String mpass);

	int updatePassword(Long id, String newpass);

	List<TDoctor> getAllDoctor(Page page);

	List<TDoctor> pageByCondition(String doctorId, String name, String mcName, String keywords, Page page);

	int deleteDoctorById(long id);

	int deleteDoctorByIds(String[] idArray);

	TDoctor getDoctorById(String doctorId);

	int updateDoctorByTDoctor(TDoctor doctor);

	TDoctor getDoctorByPhone(String phone);

	int addDoctorByTDoctor(TDoctor doctor);

	TDoctor getDoctorByLoginName(String loginName);
	
}
