package cn.edu.dgut.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TDiagnosis;


public interface DiagnosisService {
	List<TDiagnosis> getAllDiagnosis(Page page);
	List<TDiagnosis> pageByCondition(String name, String symptom, Page page);
	TDiagnosis getDiagnosisByDId(long diagnosisId, String patientName);
	int updateDiagnosisByTDiagnosis(TDiagnosis diagnosis);
	int addDiagnosisByTDiagnosis(TDiagnosis diagnosis);
	int deleteDiagnosisByDId(long diagnosisId);
	int deleteDiagnosisByDIds(String[] dIds);
	boolean isSimpleLoginName(String loginName);
	void export(String[] idArray);
	void importExcelInfo(MultipartFile file) throws Exception;
}
