package cn.edu.dgut.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TDiagnosis;
import cn.edu.dgut.pojo.TPrescription;


public interface PrescriptionService {
	List<TPrescription> getAllPrescription(Page page);
	List<TPrescription> pageByCondition(String prescriptionId,String patientId,String name, Page page);
	TPrescription getPrescriptionById(Integer id);
	int deletePrescriptionById(Integer id);
	int deletePrescriptionByIds(String[] ids);
	int addPrescription(TPrescription prescription);
	int updatePrescription(TPrescription prescription);
}
