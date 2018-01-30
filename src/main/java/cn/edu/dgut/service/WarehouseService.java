package cn.edu.dgut.service;

import java.util.List;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbWarehouse;

/**
 * @author TanWaiKim
 * @time 2018年1月29日 上午10:34:14
 * @version 1.0
 */
public interface WarehouseService {
	List<TbWarehouse> getAllWarehouse(Page page);
	List<TbWarehouse> pageByCondition(String warehouseNo, String warehouseName, String manager, String keywords, Page page);
	TbWarehouse getWarehouseById(Integer id);
	TbWarehouse getWarehouseByPhone(String phone);
	int updateWarehouseByTbWarehouse(TbWarehouse warehouse);
	int addWarehouseByTbWarehouse(TbWarehouse warehouse);
	int deleteWarehouseById(Integer id);
	int deleteWarehouseByIds(String[] ids);
	boolean isSimpleLoginName(String loginName);
	List<TbWarehouse> selectAllWarehouse();
}
