package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbDrugExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TbDrugMapper {
    int countByExample(TbDrugExample example);

    int deleteByExample(TbDrugExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbDrug record);

    int insertSelective(TbDrug record);

    List<TbDrug> selectByExample(TbDrugExample example);

    TbDrug selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbDrug record, @Param("example") TbDrugExample example);

    int updateByExample(@Param("record") TbDrug record, @Param("example") TbDrugExample example);

    int updateByPrimaryKeySelective(TbDrug record);

    int updateByPrimaryKey(TbDrug record);
    
    int updateSalePrice(TbDrug drug);
    
	List<TbDrug> selectDrugNameByAutoComplete(String parameter);
	
	TbDrug selectLastRecord();
    
	int countByCondition(Map<String, Object> map);

	List<TbDrug> pageByCondition(Map<String, Object> map);

	int deleteBatch(List<Long> list);
	
	List<TbDrug> selectAllDrug();
	
	List<TbDrug> selectByDrugNameAndDrugNo(Map<String, Object> map);
	
	List<TbDrug> selectByDrugNameAndDrugNo1(Map<String, Object> map);
}