package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.util.IDUtils;
import cn.edu.dgut.mapper.TDoctorMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TDoctor;
import cn.edu.dgut.pojo.TDoctorExample;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.service.DoctorService;
@Service
public class DoctorServiceImpl implements DoctorService {
	@Autowired
	private TDoctorMapper doctorMapper;

	//通过doctorName查询医生
	@Override
	public TDoctor selectByDoctorName(String loginName, String mpass) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("loginName", loginName);
		map.put("mpass", mpass);
		List<TDoctor> doctorList = doctorMapper.selectByloginNameAndloginPassword(map);
		return doctorList.get(0);
	}

	//通过id修改密码
	@Override
	public int updatePassword(Long id, String newpass) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("newpass", newpass);
		return doctorMapper.updateById(map);
	}

	//分页查询所有医生记录
	@Override
	public List<TDoctor> getAllDoctor(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mcName", null);
		// 根据条件查询总数
		int totalNum = doctorMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return doctorMapper.pageByCondition(map);
	}

	//分页(带条件)查询所有医生记录
	@Override
	public List<TDoctor> pageByCondition(String doctorId, String name, String mcName, String keywords, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("doctorId", doctorId);
		map.put("name", name);
		map.put("mcName", mcName);
		map.put("keywords", keywords);
		// 根据条件查询总数
		int totalNum = doctorMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return doctorMapper.pageByCondition(map);
	}

	//根据id删除记录
	@Override
	public int deleteDoctorById(long id) {
		return doctorMapper.deleteByPrimaryKey(id);
	}

	// 批量删除记录
	@Override
	public int deleteDoctorByIds(String[] idArray) {
		List<Long> list = new ArrayList<Long>();
		for (String id : idArray) {
			list.add(Long.valueOf(id).longValue());
		}
		return doctorMapper.deleteBatch(list);
	}

	//根据doctorId查询Doctor
	@Override
	public TDoctor getDoctorById(String doctorId) {
		TDoctorExample example = new TDoctorExample();
		example.createCriteria().andDoctorIdEqualTo(doctorId);
		return doctorMapper.selectByExample(example).get(0);
	}

	//更新doctor
	@Override
	public int updateDoctorByTDoctor(TDoctor doctor) {
		doctor.setUpdated(new Date());
		return doctorMapper.updateByPrimaryKey(doctor);
	}

	//通过电话查询Doctor
	@Override
	public TDoctor getDoctorByPhone(String phone) {
		TDoctorExample example = new TDoctorExample();
		example.createCriteria().andPhoneEqualTo(phone);
		List<TDoctor> doctorList = doctorMapper.selectByExample(example);
		if(doctorList.size()>0){
			return doctorList.get(0);
		}
		return null;
	}

	//通过登录名查询Doctor
	@Override
	public TDoctor getDoctorByLoginName(String loginName) {
		Map<String, Object> map = new HashMap<>();
		map.put("loginName", loginName);
		TDoctor doctor = doctorMapper.selectByLoginName(map);
		if(doctor != null ){
			System.out.println("getDoctorByLoginName="+doctor.toString());
			return doctor;
		}
		return null;
	}
	
	//插入医生数据
	@Override
	public int addDoctorByTDoctor(TDoctor doctor) {
		TDoctor lastDoctor = new TDoctor();
		lastDoctor = this.getLastRecord();
		if (lastDoctor != null) {
			doctor.setId(lastDoctor.getId() + 1);
		} else {
			lastDoctor.setId(1l);
		}
		String doctorId = "D" + IDUtils.getId() + "";
		doctor.setDoctorId(doctorId);
		Date date = new Date();
		doctor.setCreated(date);
		doctor.setUpdated(date);
		return doctorMapper.insert(doctor);
	}

	// 获取t_doctor中最后一条记录
	private TDoctor getLastRecord() {
		TDoctor doctor = doctorMapper.selectLastRecord();
		if (doctor != null) {
			return doctor;
		}
		return null;
	}

	

}
