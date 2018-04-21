package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.dgut.common.util.IDUtils;
import cn.edu.dgut.mapper.TDiagnosisMapper;
import cn.edu.dgut.mapper.TPatientMapper;
import cn.edu.dgut.mapper.TStayHospitalMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TDiagnosis;
import cn.edu.dgut.pojo.TDiagnosisExample;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.pojo.TPatientExample;
import cn.edu.dgut.pojo.TStayHospital;
import cn.edu.dgut.pojo.TStayHospitalExample;
import cn.edu.dgut.service.DiagnosisService;
@Service
public class DiagnosisServiceImpl implements DiagnosisService {

	@Autowired
	private TDiagnosisMapper diagnosisMapper;
	
	@Autowired
	private TPatientMapper patientMapper;
	
	@Autowired
	private TStayHospitalMapper stayHospitalMapper;
	
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
	public TDiagnosis getDiagnosisByDId(long diagnosisId) {
		return diagnosisMapper.selectByDid(diagnosisId);
	}

	//更新诊断信息
	public int updateDiagnosisByTDiagnosis(TDiagnosis diagnosis) {
		diagnosis.setUpdated(new Date());
		return diagnosisMapper.updateByPrimaryKey(diagnosis);
	}

	//增加诊断信息
	public int addDiagnosisByTDiagnosis(TDiagnosis diagnosis) {
		diagnosis.setPatientId(diagnosis.getPatientId());
		Date date = new Date();
		diagnosis.setCreated(date);
		diagnosis.setUpdated(date);
		return diagnosisMapper.insert(diagnosis);
	}

	//通过诊断id删除诊断信息
	public int deleteDiagnosisByDId(long diagnosisId) {
		return diagnosisMapper.deleteByPrimaryKey(diagnosisId);
	}

	//通过多诊断id批量删除诊断信息
	public int deleteDiagnosisByDIds(String[] dIds) {
		List<Long> list = new ArrayList<Long>();
		for (String id : dIds) {
			list.add(Long.valueOf(id).longValue());
		}
		return diagnosisMapper.deleteBatch(list);
	}

	//通过病人姓名分页查询诊断信息
	public List<TDiagnosis> pageByPatientName(String patientName, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", patientName);
		// 根据条件查询总数
		int totalNum = diagnosisMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return diagnosisMapper.pageByCondition(map);
	}

	// 通过diagnosisId获取健康档案
	@Override
	public TDiagnosis getHealthByDId(long diagnosisId) {
		// 通过patientId查询病人诊断记录
		TDiagnosisExample example = new TDiagnosisExample();
		example.createCriteria().andDiagnosisIdEqualTo(diagnosisId);
		List<TDiagnosis> diagnosisList = diagnosisMapper.selectByExample(example);
		if (diagnosisList.size() > 0) {
			TPatientExample pExample = new TPatientExample();
			pExample.createCriteria().andPatientIdEqualTo(diagnosisList.get(0).getPatientId());
			List<TPatient> patientList = patientMapper.selectByExample(pExample);
			if(patientList.size()>0){
				diagnosisList.get(0).setPatient(patientList.get(0));
				TStayHospitalExample sHExample = new TStayHospitalExample();
				sHExample.createCriteria().andPatientIdEqualTo(patientList.get(0).getPatientId());
				List<TStayHospital> stayHospitalList = stayHospitalMapper.selectByExample(sHExample);
				if (stayHospitalList.size() > 0) {
					TStayHospital stayHospital = stayHospitalList.get(stayHospitalList.size() - 1);
					diagnosisList.get(0).getPatient().setStayHospital(stayHospital);
				}
			}
			
		}
		System.out.println("diagnosisList.get(0)="+diagnosisList.get(0).toString());
		return diagnosisList.get(0);
	}
}
