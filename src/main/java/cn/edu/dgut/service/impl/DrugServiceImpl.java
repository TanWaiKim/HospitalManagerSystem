package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.util.IDUtils;
import cn.edu.dgut.mapper.TbAdminMapper;
import cn.edu.dgut.mapper.TbDrugMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbAdmin;
import cn.edu.dgut.pojo.TbAdminExample;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.service.DrugService;

/**
 * @author TanWaiKim
 * @time 2018年1月26日 上午8:39:53
 * @version 1.0
 */
@Service
public class DrugServiceImpl implements DrugService {
	@Autowired
	private TbDrugMapper drugMapper;
	
	@Autowired
	private TbAdminMapper adminMapper;
	
	/**
	 * 获取tb_drug中最后一条记录
	 * @return
	 */
	public TbDrug getLastRecord(){
		TbDrug drug = drugMapper.selectLastRecord();
		if(drug!=null){
			return drug;
		}
		return null;
	}
	
	/**
	 * 添加医药信息
	 */
	public int addDrugByTbDrug(TbDrug drug) {
		TbDrug lastDrug = this.getLastRecord();
		if(lastDrug!=null){
			drug.setId(lastDrug.getId()+1);
		}else{
			drug.setId(1);
		}
		String drugNo = "YY"+IDUtils.getId() + "";
		drug.setDrugNo(drugNo);
		int count = drugMapper.insert(drug);
		return count;
	}
	
	/**
	 * 分页查询所有的医药信息，刚进来的页面信息
	 */
	@Override
	public List<TbDrug> getAllDrug(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		//防止刷新该字段不为空
		map.put("drugtypeId", null);
		// 根据条件查询总数
		int totalNum = drugMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return drugMapper.pageByCondition(map);
	}

	/**
	 * 分页条件查询医药信息
	 */
	@Override
	public List<TbDrug> pageByCondition(Integer drugtypeId, String drugName, String drugNo, String keywords,
			Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugtypeId", drugtypeId);
		map.put("drugName", drugName);
		map.put("drugNo", drugNo);
		map.put("keywords", keywords);
		// 根据条件查询总数
		int totalNum = drugMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return drugMapper.pageByCondition(map);
	}
	
	/**
	 * 根据id查询医药信息
	 */
	@Override
	public TbDrug getDrugById(Integer id) {
		TbDrug drug = drugMapper.selectByPrimaryKey(id);
		if (drug!=null) {
			return drug;
		}
		return null;
	}

	/**
	 * 修改医药医药信息
	 */
	@Override
	public int updateDrugByTbDrug(TbDrug drug) {
		int count = drugMapper.updateByPrimaryKey(drug);
		return count;
	}

	/**
	 * 根据id单独删除医药信息
	 */
	@Override
	public int deleteDrugById(Integer id) {
		return drugMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除医药信息
	 */
	@Override
	public int deleteDrugByIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			list.add(Long.valueOf(id).longValue());
		}
		return drugMapper.deleteBatch(list);
	}

	/*
	 * 判断数据库中是否存在该登录账号所对应的记录
	 * 如果找到该记录，则返回true
	 * 否则返回false
	 */
	@Override
	public boolean isSimpleLoginName(String username) {
		//根据loginName条件查询
		TbAdminExample example = new TbAdminExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<TbAdmin> adminList = adminMapper.selectByExample(example);
		if(adminList.size()>0){
			return true;
		}
		return false;
	}

	/**
	 * 不分页查询所有的医药信息
	 */
	@Override
	public List<TbDrug> selectAllDrug() {
		List<TbDrug> drugList = drugMapper.selectAllDrug();
		if (drugList.size() > 0) {
			return drugList;
		}
		return null;
	}

	@Override
	public TbDrug getDrugByNo(String drugNo) {
		TbDrug drug = drugMapper.selectByDrugNo(drugNo);
		if (drug!=null) {
			return drug;
		}
		return null;
	}

	/**
	 * 根据名称查询
	 */
	@Override
	public TbDrug getDrugByName(String drugName) {
		TbDrug drug = drugMapper.selectByDrugName(drugName);
		if (drug!=null) {
			return drug;
		}
		return null;
	}



}
