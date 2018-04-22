package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.mapper.TStayHospitalMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TStayHospital;
import cn.edu.dgut.service.StayHospitalService;
@Service
public class StayHospitalServiceImpl implements StayHospitalService {

	@Autowired
	private TStayHospitalMapper stayHospitalMapper;
	
	@Override
	public List<TStayHospital> getAllStayHospital(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据条件查询总数
		int totalNum = stayHospitalMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		List<TStayHospital> stayHospitalList = stayHospitalMapper.pageByCondition(map);
		if(stayHospitalList.size()>0){
			for (TStayHospital tStayHospital : stayHospitalList) {
				System.out.println("tStayHospital="+tStayHospital.toString());
			}
		}
		return stayHospitalList;
	}

	@Override
	public List<TStayHospital> pageByCondition(String sickbedId, String patientName, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sickbedId", sickbedId);
		map.put("patientName", patientName);
		// 根据条件查询总数
		int totalNum = stayHospitalMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return stayHospitalMapper.pageByCondition(map);
	}

	@Override
	public int deleteStayHospitalById(long id) {
		return stayHospitalMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteStayHospitalByIds(String[] idArray) {
		List<Long> idList = new ArrayList<Long>();
		for (String id : idArray) {
			idList.add(Long.valueOf(id));
		}
		return stayHospitalMapper.deleteBatch(idList);
	}

	@Override
	public int addStayHospitalByTStayHospital(TStayHospital stayHospital) {
		Date date = new Date();
		stayHospital.setCreated(date);
		stayHospital.setUpdated(date);
		return stayHospitalMapper.insert(stayHospital);
	}

}
