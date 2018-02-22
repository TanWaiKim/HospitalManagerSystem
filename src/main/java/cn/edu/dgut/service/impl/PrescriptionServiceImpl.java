package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.util.IDUtils;
import cn.edu.dgut.mapper.TPrescriptionMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TPrescription;
import cn.edu.dgut.service.PrescriptionService;
@Service
public class PrescriptionServiceImpl implements PrescriptionService {

	@Autowired
	private TPrescriptionMapper prescriptionMapper;
	
	//分页查询
	@Override
	public List<TPrescription> getAllPrescription(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据条件查询总数
		int totalNum = prescriptionMapper.countByCondition(map);
		
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		return prescriptionMapper.pageByCondition(map);
	}

	//按条件分页查询
	@Override
	public List<TPrescription> pageByCondition(String prescriptionId,String name, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prescriptionId", prescriptionId);
		map.put("name", name);
		// 根据条件查询总数
		int totalNum = prescriptionMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return prescriptionMapper.pageByCondition(map);
	}

	//通过id查询处方记录
	@Override
	public TPrescription getPrescriptionById(Integer id) {
		return prescriptionMapper.selectById(id);
	}

	//通过id删除处方记录
	@Override
	public int deletePrescriptionById(Integer id) {
		return prescriptionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deletePrescriptionByIds(String[] ids) {
		List<Integer> list = new ArrayList<Integer>();
		for (String id : ids) {
			list.add(Integer.valueOf(id));
		}
		return prescriptionMapper.deleteBatch(list);
	}

	//添加处方记录
	@Override
	public int addPrescription(TPrescription prescription) {
		String prescriptionId = "Pr"+IDUtils.getId();
		prescription.setPrescriptionId(prescriptionId);
		Date date = new Date();
		prescription.setCreated(date);
		prescription.setUpdated(date);
		return prescriptionMapper.insert(prescription);
	}

	//修改处方记录
	@Override
	public int updatePrescription(TPrescription prescription) {
		return prescriptionMapper.updateByPrimaryKey(prescription);
	}

}
