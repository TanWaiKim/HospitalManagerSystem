package cn.edu.dgut.service;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TPrescription;


public interface PrescriptionService {
	List<TPrescription> getAllPrescription(Page page);
	List<TPrescription> pageByCondition(String prescriptionId,String patientId,String name, Page page);
	TPrescription getPrescriptionById(Integer id);
	int deletePrescriptionById(Integer id);
	int deletePrescriptionByIds(String[] ids);
	int addPrescription(TPrescription prescription);
	int updatePrescription(TPrescription prescription);
	String autoDrugName(String term, HttpServletResponse response) throws Exception;
	boolean isEnoughDrug(String drugName, String drugNum);
}
