package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TbWarehouse;
import cn.edu.dgut.pojo.TbWarehouseExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TbWarehouseMapper {
    int countByExample(TbWarehouseExample example);

    int deleteByExample(TbWarehouseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbWarehouse record);

    int insertSelective(TbWarehouse record);

    List<TbWarehouse> selectByExample(TbWarehouseExample example);

    TbWarehouse selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbWarehouse record, @Param("example") TbWarehouseExample example);

    int updateByExample(@Param("record") TbWarehouse record, @Param("example") TbWarehouseExample example);

    int updateByPrimaryKeySelective(TbWarehouse record);

    int updateByPrimaryKey(TbWarehouse record);
    
	int countByCondition(Map<String, Object> map);

	List<TbWarehouse> pageByCondition(Map<String, Object> map);

	int deleteBatch(List<Long> list);

	void insertInfoBatch(List<TbWarehouse> warehouseList);

	TbWarehouse selectLastRecord();

	List<TbWarehouse> selectAllWarehouse();
	
	TbWarehouse selectByWarehouseNo(String warehouseNo);
    
}