package cn.edu.dgut.service;

import java.util.List;
import java.util.Map;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbStock;

/**
 * @author TanWaiKim
 * @time 2018年2月4日 下午4:08:47
 * @version 1.0
 */
public interface StockService {
	List<TbStock> getAllStock(Page page);
	List<TbStock> pageByCondition(String warehouseNo, String operator, String drugName, String drugNo, Page page);
	TbStock getStockById(Integer id);
	int addStockByTbStock(TbStock TbStock);
	int deleteStockById(Integer id);
	int deleteStockByIds(String[] ids);
	List<TbStock> selectAllStock();
	TbStock getStockByDrug(Map<String, Object> map);
	int updateStockBySelective(TbStock stock);
	List<TbStock> getAllStockByQuantityWaring(Page page);
	List<TbStock> pageByQuantityWaring(Page page);
	List<TbStock> getAllStockByValidWaring(Page page);
	List<TbStock> pageByValidWaring(Page page);
	List<TbStock> getStockByDrugId(Integer drugId);
	
	List<TbStock> getAllListStock(Page page);
	List<TbStock> pageByListCondition(String drugName, String drugNo, Page page);
	
	int updateStockByDrugId(TbStock stock);
}
