package cn.edu.dgut.service;

import java.util.List;
import java.util.Map;

import cn.edu.dgut.common.dto.ValidWarningDto;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbStock;

/**
 * @author TanWaiKim
 * @time 2018年2月4日 下午4:08:47
 * @version 1.0
 */
public interface StockService {
	List<TbStock> getAllStock(Page page);
	List<TbStock> pageByCondition(String warehouseNo, String drugName, Page page);
	TbStock getStockById(Integer id);
	int addStockByTbStock(TbStock TbStock);
	int deleteStockByDrugId(Integer id);
	int deleteStockByDrugIds(String[] ids);
	List<TbStock> selectAllStock();
	List<TbStock> getStockByDrug(Map<String, Object> map);
	int updateStockBySelective(TbStock stock);
	List<TbStock> getAllStockByQuantityWaring(Page page);
	List<TbStock> pageByQuantityWaring(Page page);
	List<ValidWarningDto> getAllStockByValidWaring(Page page);
	List<ValidWarningDto> pageByValidWaring(Page page);
	List<TbStock> getStockByDrugId(Integer drugId);
	
	List<TbStock> getAllListStock(Page page);
	
	List<TbStock> pageByListCondition(String drugName, Page page);
	
	int updateStockByDrugName(TbStock stock);
	
	int updateByStockSelective(TbStock stock);
	
	int countStockQuantityByDrugName(String drugname);
	
	List<TbStock> getStockByDrugName(String drugname);
	
	int countStockQuantityByDrugId(Integer drugId);
	
	int countStockQuantityByDrugNameAndDrugNo(String drugName,String drugNo);
}
