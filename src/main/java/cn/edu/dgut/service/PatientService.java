package cn.edu.dgut.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TPatient;

public interface PatientService {
	List<TPatient> getAllPatient(Page page);
	List<TPatient> pageByCondition(String patientId, String name, String mcName, String keywords, Page page);
	TPatient getPatientById(String patientId);
	TPatient getPatientByPhone(String phone);
	int updatePatientByTPatient(TPatient patient);
	int addPatientByTPatient(TPatient patient);
	int deletePatientById(long id);
	int deletePatientByIds(String[] ids);
	boolean isSimpleLoginName(String loginName);
	void export(String[] idArray);
	void importExcelInfo(MultipartFile file) throws Exception;
}
