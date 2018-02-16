package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.mapper.TbAdminMapper;
import cn.edu.dgut.mapper.TbDrugtypeMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbAdmin;
import cn.edu.dgut.pojo.TbAdminExample;
import cn.edu.dgut.pojo.TbDrugtype;
import cn.edu.dgut.service.DrugtypeService;

/**
 * @author TanWaiKim
 * @time 2018年1月25日 下午1:21:29
 * @version 1.0
 */
@Service
public class DrugtypeServiceImpl implements DrugtypeService {

	@Autowired
	private TbDrugtypeMapper drugtypeMapper;
	
	@Autowired
	private TbAdminMapper adminMapper;
	
	
	/**
	 * 分页查询所有医药种类信息，刚进来的页面
	 */
	@Override
	public List<TbDrugtype> getAllDrugtype(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据条件查询总数
		int totalNum = drugtypeMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return drugtypeMapper.pageByCondition(map);
	}

	/**
	 * 分页条件查询医药种类信息，
	 */
	@Override
	public List<TbDrugtype> pageByCondition(String drugtypeName, String keywords, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugtypeName", drugtypeName);
		map.put("keywords", keywords);
		// 根据条件查询总数
		int totalNum = drugtypeMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return drugtypeMapper.pageByCondition(map);
	}

	/**
	 * 根据id查询医药种类信息
	 */
	@Override
	public TbDrugtype getDrugtypeById(Integer id) {
		TbDrugtype drugtype = drugtypeMapper.selectByPrimaryKey(id);
		if (drugtype!=null) {
			return drugtype;
		}
		return null;
	}

	/**
	 * 修改医药种类信息
	 */
	@Override
	public int updateDrugtypeByTbDrugtype(TbDrugtype drugtype) {
		int count = drugtypeMapper.updateByPrimaryKey(drugtype);
		return count;
	}

	/**
	 * 获取tb_drugtype中最后一条记录
	 * @return
	 */
	public TbDrugtype getLastRecord(){
		TbDrugtype drugtype = drugtypeMapper.selectLastRecord();
		if(drugtype!=null){
			return drugtype;
		}
		return null;
	}
	
	/**
	 * 添加一条信息医药种类信息
	 */
	@Override
	public int addDrugtypeByTbDrugtype(TbDrugtype drugtype) {
		
		TbDrugtype lastDrugtype = this.getLastRecord();
		if(lastDrugtype!=null){
			drugtype.setId(lastDrugtype.getId()+1);
		}else{
			drugtype.setId(1);
		}
		int count = drugtypeMapper.insert(drugtype);
		return count;
	}

	/**
	 * 根据id删除单个医药种类信息
	 */
	@Override
	public int deleteDrugtypeById(Integer id) {
		
		
		
		return drugtypeMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除医药种类信息
	 */
	@Override
	public int deleteDrugtypeByIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			list.add(Long.valueOf(id).longValue());
		}
		return drugtypeMapper.deleteBatch(list);
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
	 * 查询所有的医药种类信息，不分页显示
	 */
	@Override
	public List<TbDrugtype> selectAllDrugtype() {
		List<TbDrugtype> drugtypeList = drugtypeMapper.selectAllDrugtype();
		if (drugtypeList.size() > 0) {
			return drugtypeList;
		}
		return null;
	}



}
