package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.pojo.TbStockExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TbStockMapper {
    int countByExample(TbStockExample example);

    int deleteByExample(TbStockExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbStock record);

    int insertSelective(TbStock record);

    List<TbStock> selectByExample(TbStockExample example);

    TbStock selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbStock record, @Param("example") TbStockExample example);

    int updateByExample(@Param("record") TbStock record, @Param("example") TbStockExample example);

    int updateByPrimaryKeySelective(TbStock record);
    
    int updateByDrugSelective(TbStock record);

    int updateByPrimaryKey(TbStock record);
    
    TbStock selectLastRecord();
    
	int countByCondition(Map<String, Object> map);

	List<TbStock> pageByCondition(Map<String, Object> map);
	
	int countByListCondition(Map<String, Object> map);

	List<TbStock> pageByListCondition(Map<String, Object> map);
	
	TbStock selectByDrugIdAndBatchNo(Map<String, Object> map);
	
	int countByQuantityWaring(Map<String, Object> map);

	List<TbStock> pageByQuantityWaring(Map<String, Object> map);
	
	int countByValidWaring(Map<String, Object> map);

	List<TbStock> pageByValidWaring(Map<String, Object> map);
	
	Integer countValidTime(TbStock stock);
	
	List<TbStock> selectAllStock();
	
	List<TbStock> getStockByDrugId(Integer drugId);
	
	int updateByDrugId (TbStock record);
	
	int updateWaring (TbStock record);
	
	int updateValidWaring (TbStock record);
	
	int countStockQuantityByDrugId(Integer drugId);
	
	int updateByStockSelective(TbStock record);
}