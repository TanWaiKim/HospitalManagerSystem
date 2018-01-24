package cn.edu.dgut.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.dgut.mapper.TDiagnosisMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TDiagnosis;
import cn.edu.dgut.service.DiagnosisService;
@Service
public class DiagnosisServiceImpl implements DiagnosisService {

	@Autowired
	private TDiagnosisMapper diagnosisMapper;
	
	//分页查询
	public List<TDiagnosis> getAllDiagnosis(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据条件查询总数
		int totalNum = diagnosisMapper.countByCondition(map);
		
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		return diagnosisMapper.pageByCondition(map);
	}

	//条件分页查询
	public List<TDiagnosis> pageByCondition(String name, String symptom, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("symptom", symptom);
		// 根据条件查询总数
		int totalNum = diagnosisMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return diagnosisMapper.pageByCondition(map);
	}

	//多表条件查询单条记录
	public TDiagnosis getDiagnosisByDId(long diagnosisId, String patientName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("diagnosisId", diagnosisId);
		map.put("patientName", patientName);
		return diagnosisMapper.selectByDidAndPName(map);
	}

	//更新诊断信息
	public int updateDiagnosisByTDiagnosis(TDiagnosis diagnosis) {
		diagnosis.setUpdated(new Date());
		return diagnosisMapper.updateByPrimaryKey(diagnosis);
	}

	public int addDiagnosisByTDiagnosis(TDiagnosis diagnosis) {
		
		return 0;
	}

	public int deleteDiagnosisByDId(long diagnosisId) {
		
		return 0;
	}

	public int deleteDiagnosisByDIds(String[] dIds) {
		
		return 0;
	}

	public boolean isSimpleLoginName(String loginName) {
		
		return false;
	}

	public void export(String[] idArray) {
		

	}

	public void importExcelInfo(MultipartFile file) throws Exception {
		

	}

}
