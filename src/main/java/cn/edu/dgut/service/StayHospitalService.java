package cn.edu.dgut.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TStayHospital;

public interface StayHospitalService {

	List<TStayHospital> getAllStayHospital(Page page);

	List<TStayHospital> pageByCondition(String sickbedId, String patientName, Page page);

	int deleteStayHospitalById(long longValue);

	int deleteStayHospitalByIds(String[] idArray);

	int addStayHospitalByTStayHospital(TStayHospital stayHospital);

}
