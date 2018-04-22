package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.util.IDUtils;
import cn.edu.dgut.mapper.TMedicalcoursesMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TDoctor;
import cn.edu.dgut.pojo.TMedicalcourses;
import cn.edu.dgut.service.MedicalcoursesService;

@Service
public class MedicalcoursesServiceImpl implements MedicalcoursesService {

	@Autowired
	private TMedicalcoursesMapper medicalcoursesMapper;

	// 分页查询所有医生记录
	@Override
	public List<TMedicalcourses> getAllMedicalcourses(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", null);
		// 根据条件查询总数
		int totalNum = medicalcoursesMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return medicalcoursesMapper.pageByCondition(map);
	}

	// 分页(带条件)查询所有医生记录
	@Override
	public List<TMedicalcourses> pageByCondition(String name, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		// 根据条件查询总数
		int totalNum = medicalcoursesMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return medicalcoursesMapper.pageByCondition(map);
	}

	//根据id删除记录
	@Override
	public int deleteMedicalcoursesById(long id) {
		return medicalcoursesMapper.deleteByPrimaryKey(id);
	}

	//批量删除记录
	@Override
	public int deleteMedicalcoursesByIds(String[] idArray) {
		List<Long> idList = new ArrayList<Long>();
		for (String id : idArray) {
			idList.add(Long.valueOf(id));
		}
		return medicalcoursesMapper.deleteBatch(idList);
	}

	//根据id查询记录
	@Override
	public TMedicalcourses getMedicalcoursesById(String id) {
		return medicalcoursesMapper.selectByPrimaryKey(Long.valueOf(id));
	}

	//更新科别
	@Override
	public int updateMedicalcoursesByTMedicalcourses(TMedicalcourses medicalcourses) {
		medicalcourses.setUpdated(new Date());
		return medicalcoursesMapper.updateByPrimaryKey(medicalcourses);
	}

	//新增科别
	@Override
	public int addMedicalcoursesByTMedicalcourses(TMedicalcourses medicalcourses) {
		TMedicalcourses lastMedicalcourses = new TMedicalcourses();
		lastMedicalcourses = this.getLastRecord();
		if (lastMedicalcourses != null) {
			medicalcourses.setId(lastMedicalcourses.getId() + 1);
		} else {
			medicalcourses.setId(1l);
		}

		Date date = new Date();
		medicalcourses.setCreated(date);
		medicalcourses.setUpdated(date);
		return medicalcoursesMapper.insert(medicalcourses);
	}

	
	//获取最后一个记录
	private TMedicalcourses getLastRecord() {
		TMedicalcourses medicalcourses = medicalcoursesMapper.selectLastRecord();
		if (medicalcourses != null) { 
			return medicalcourses;
		}
		return null;
	}

	//获取所有科别名
	@Override
	public List<String> getAllMedicalcoursesName() {
		List<String> medicalcoursesNameList = new ArrayList<String>();
		List<TMedicalcourses> medicalcoursesList = medicalcoursesMapper.selectAllMedicalcourses();
		if(medicalcoursesList.size()>0){
			for (TMedicalcourses medicalcourses : medicalcoursesList) {
				medicalcoursesNameList.add(medicalcourses.getName());
			}
			return medicalcoursesNameList;
		}
		return null;
	}
	
	
}
