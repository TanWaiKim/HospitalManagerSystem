package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TbSales;
import cn.edu.dgut.pojo.TbSalesExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TbSalesMapper {
    int countByExample(TbSalesExample example);

    int deleteByExample(TbSalesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbSales record);

    int insertSelective(TbSales record);

    List<TbSales> selectByExample(TbSalesExample example);

    TbSales selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbSales record, @Param("example") TbSalesExample example);

    int updateByExample(@Param("record") TbSales record, @Param("example") TbSalesExample example);

    int updateByPrimaryKeySelective(TbSales record);

    int updateByPrimaryKey(TbSales record);
    
    TbSales selectLastRecord();
    
    int countByCondition(Map<String, Object> map);
    
    int countBySalesNo(Map<String, Object> map);
    
    List<TbSales> pageByCondition(Map<String, Object> map);
    
    List<TbSales> selectBySalesNoCondition(Map<String, Object> map);
    
    List<TbSales> selectBySalesNo(String salesNo);
    
    int deleteBatch(List<Long> list);
    
	int deleteBySalesNo(String salesNo);
	
	int deleteBySalesNoBatch(List<String> list);
	
	TbSales getSalesBySalesNoAndDrugId(Map<String, Object> map);
	
	List<TbSales> selectAllSales(Map<String, Object> map);
	
	List<TbSales> selectAllSales1(Map<String, Object> map);
    
}