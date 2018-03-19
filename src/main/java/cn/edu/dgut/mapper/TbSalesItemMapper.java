package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TbSalesItem;
import cn.edu.dgut.pojo.TbSalesItemExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TbSalesItemMapper {
    int countByExample(TbSalesItemExample example);

    int deleteByExample(TbSalesItemExample example);

    int deleteByPrimaryKey(Integer id);
    
    int deleteBySalesNo(String salesNo);

    int insert(TbSalesItem record);

    int insertSelective(TbSalesItem record);

    List<TbSalesItem> selectByExample(TbSalesItemExample example);

    TbSalesItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbSalesItem record, @Param("example") TbSalesItemExample example);

    int updateByExample(@Param("record") TbSalesItem record, @Param("example") TbSalesItemExample example);

    int updateByPrimaryKeySelective(TbSalesItem record);

    int updateByPrimaryKey(TbSalesItem record);
    
    
    TbSalesItem selectLastRecord();
    
    TbSalesItem selectByDrugIdAndBatchNo(TbSalesItem salesItem);
    
    List<TbSalesItem> selectAllSalesItem(String salesNo);
    
	int countByCondition(Map<String, Object> map);

	List<TbSalesItem> pageByCondition(Map<String, Object> map);
	
	int deleteBatch(List<Long> list);
	
	List<TbSalesItem> selectAllSalesItem1();
    
}