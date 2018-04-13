package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.util.IDUtils;
import cn.edu.dgut.mapper.TbDrugAdminMapper;
import cn.edu.dgut.mapper.TbWarehouseMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrugAdmin;
import cn.edu.dgut.pojo.TbDrugAdminExample;
import cn.edu.dgut.pojo.TbWarehouse;
import cn.edu.dgut.pojo.TbWarehouseExample;
import cn.edu.dgut.service.WarehouseService;

/**
 * @author TanWaiKim
 * @time 2018年1月29日 上午10:44:00
 * @version 1.0
 */
@Service
public class WarehouseServiceImpl implements WarehouseService {
	
	@Autowired
	TbWarehouseMapper warehouseMapper;
	
	@Autowired
	TbDrugAdminMapper adminMapper;
	
	/**
	 * 首次进入列表页面
	 */
	@Override
	public List<TbWarehouse> getAllWarehouse(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据条件查询总数
		int totalNum = warehouseMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return warehouseMapper.pageByCondition(map);
	}

	/**
	 * 条件查询
	 */
	@Override
	public List<TbWarehouse> pageByCondition(String warehouseNo, String warehouseName, String manager, String keywords,
			Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("warehouseNo", warehouseNo);
		map.put("warehouseName", warehouseName);
		map.put("manager", manager);
		map.put("keywords", keywords);
		// 根据条件查询总数
		int totalNum = warehouseMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return warehouseMapper.pageByCondition(map);
	}

	/**
	 * 根据仓库id查找仓库信息
	 */
	@Override
	public TbWarehouse getWarehouseById(Integer id) {
		TbWarehouse warehouse =warehouseMapper.selectByPrimaryKey(id);
		if (warehouse!=null) {
			return warehouse;
		}
		return null;
	}
	
	/**
	 * 根据仓库no查找仓库信息
	 */
	@Override
	public TbWarehouse getWarehouseByNo(String warehouseNo) {
		TbWarehouse warehouse =warehouseMapper.selectByWarehouseNo(warehouseNo);
		if (warehouse!=null) {
			return warehouse;
		}
		return null;
	}

	/**
	 * 根据手机号码查询
	 */
	@Override
	public TbWarehouse getWarehouseByPhone(String phone) {
		TbWarehouseExample example = new TbWarehouseExample();
		example.createCriteria().andPhoneEqualTo(phone);
		List<TbWarehouse> warehouseList = warehouseMapper.selectByExample(example);
		if (warehouseList.size() > 0) {
			return warehouseList.get(0);
		}
		return null;
	}
	
	/**
	 * 获取tb_warehouse中最后一条记录
	 * @return
	 */
	public TbWarehouse getLastRecord(){
		TbWarehouse warehouse = warehouseMapper.selectLastRecord();
		if(warehouse!=null){
			return warehouse;
		}
		return null;
	}

	/**
	 * 更新仓库信息
	 */
	@Override
	public int updateWarehouseByTbWarehouse(TbWarehouse warehouse) {
		int count = warehouseMapper.updateByPrimaryKey(warehouse);
		return count;
	}

	/**
	 * 添加仓库信息
	 */
	@Override
	public int addWarehouseByTbWarehouse(TbWarehouse warehouse) {
		TbWarehouse lastWarehouse = this.getLastRecord();
		if(lastWarehouse!=null){
			warehouse.setId(lastWarehouse.getId()+1);
		}else{
			warehouse.setId(1);
		}
		String warehouseNo = "CK"+IDUtils.getId() + "";
		warehouse.setWarehouseNo(warehouseNo);
		int count = warehouseMapper.insert(warehouse);
		return count;
	}

	/**
	 * 删除单条仓库记录
	 */
	@Override
	public int deleteWarehouseById(Integer id) {
		return warehouseMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除仓库记录
	 */
	@Override
	public int deleteWarehouseByIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			list.add(Long.valueOf(id).longValue());
		}
		return warehouseMapper.deleteBatch(list);
	}

	/**
	 * 
	 * 判断数据库中是否存在该登录账号所对应的记录
	 * 如果找到该记录，则返回true
	 * 否则返回false
	 */
	@Override
	public boolean isSimpleLoginName(String username) {
		//根据loginName条件查询
		TbDrugAdminExample example = new TbDrugAdminExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<TbDrugAdmin> adminList = adminMapper.selectByExample(example);
		if(adminList != null){
			return true;
		}
		return false;
	}

	/**
	 * 获取所有的仓库信息，不分页
	 */
	@Override
	public List<TbWarehouse> selectAllWarehouse() {
		List<TbWarehouse> warehouseList = warehouseMapper.selectAllWarehouse();
		if (warehouseList != null) {
			return warehouseList;
		}
		return null;
	}

}
