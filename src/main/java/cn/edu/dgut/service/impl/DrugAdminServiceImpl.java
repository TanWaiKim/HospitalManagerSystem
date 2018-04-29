package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.mapper.TbDrugAdminMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrugAdmin;
import cn.edu.dgut.pojo.TbDrugAdminExample;
import cn.edu.dgut.service.DrugAdminService;

/**
 * @author TanWaiKim
 * @time 2018年2月2日 下午10:28:33
 * @version 1.0
 */
@Service
public class DrugAdminServiceImpl implements DrugAdminService {
	@Autowired
	private TbDrugAdminMapper drugAdminMapper;
	
	/**
	 * 判断药品员是否已登录
	 */
	@Override
	public TbDrugAdmin login(String username, String password) {
		TbDrugAdmin drugAdmin  = drugAdminMapper.selectLogin(username,password);
		return drugAdmin;
	}

	/**
	 * 获取药品员列表
	 */
	@Override
	public List<TbDrugAdmin> getAllDrugAdmin(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据条件查询总数
		int totalNum = drugAdminMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return drugAdminMapper.pageByCondition(map);
	}

	/**
	 * 条件查询药品员
	 */
	@Override
	public List<TbDrugAdmin> pageByCondition(String username, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", username);
		// 根据条件查询总数
		int totalNum = drugAdminMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return drugAdminMapper.pageByCondition(map);
	}

	/**
	 * 根据id查找药品员
	 */
	@Override
	public TbDrugAdmin getDrugAdminById(Integer id) {
		TbDrugAdmin drugAdmin = drugAdminMapper.selectByPrimaryKey(id);
		if (drugAdmin != null) {
			return drugAdmin;
		}
		return null;
	}

	/**
	 * 更新药品员信息
	 */
	@Override
	public int updateDrugAdminByTbDrugAdmin(TbDrugAdmin drugAdmin) {
		int count = drugAdminMapper.updateByPrimaryKeySelective(drugAdmin);
		return count;
	}

	/**
	 * 添加药品员信息
	 */
	@Override
	public int addDrugAdminByTbDrugAdmin(TbDrugAdmin drugAdmin) {
		int count = drugAdminMapper.insert(drugAdmin);
		return count;
	}

	/**
	 * 单条删除药品员信息
	 */
	@Override
	public int deleteDrugAdminById(Integer id) {
		return drugAdminMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除药品员信息
	 */
	@Override
	public int deleteDrugAdminByIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			list.add(Long.valueOf(id).longValue());
		}
		return drugAdminMapper.deleteBatch(list);
	}

	/*
	 * 判断数据库中是否存在该登录账号所对应的记录
	 * 如果找到该记录，则返回true
	 * 否则返回false
	 */
	@Override
	public boolean isSimpleLoginName(String username) {
		//根据loginName条件查询
		TbDrugAdminExample example = new TbDrugAdminExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<TbDrugAdmin> drugAdminList = drugAdminMapper.selectByExample(example);
		if(drugAdminList != null){
			return true;
		}
		return false;
	}

	/**
	 * 查询所有药品员信息
	 */
	@Override
	public List<TbDrugAdmin> selectAllDrugAdmin() {
		List<TbDrugAdmin> drugAdminList = drugAdminMapper.selectAllDrugAdmin();
		if (drugAdminList != null) {
			return drugAdminList;
		}
		return null;
	}

}
