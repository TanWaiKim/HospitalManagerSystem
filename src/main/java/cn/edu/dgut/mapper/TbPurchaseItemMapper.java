package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbPurchaseItemExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TbPurchaseItemMapper {
    int countByExample(TbPurchaseItemExample example);

    int deleteByExample(TbPurchaseItemExample example);

    int deleteByPrimaryKey(Integer id);
    
    int deleteByPurchaseNo(String purchaseNo);

    int insert(TbPurchaseItem record);

    int insertSelective(TbPurchaseItem record);

    List<TbPurchaseItem> selectByExample(TbPurchaseItemExample example);

    TbPurchaseItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbPurchaseItem record, @Param("example") TbPurchaseItemExample example);

    int updateByExample(@Param("record") TbPurchaseItem record, @Param("example") TbPurchaseItemExample example);

    int updateByPrimaryKeySelective(TbPurchaseItem record);
    
    int updateByDrugIdSelective(TbPurchaseItem record);

    int updateByPrimaryKey(TbPurchaseItem record);
    
    TbPurchaseItem selectLastRecord();
    
	List<TbPurchaseItem> selectAllPurchaseItem(String purchaseNo);
	
	int countByCondition(Map<String, Object> map);

	List<TbPurchaseItem> pageByCondition(Map<String, Object> map);
	
	int deleteBatch(List<Long> list);
	
	List<TbPurchaseItem> analysisByCondition(Map<String, Object> map);
	
	TbPurchaseItem selectByDrugIdAndBatchNo(TbPurchaseItem purchaseItem);
	List<TbPurchaseItem> selectAllPurchaseItem1();
	
}