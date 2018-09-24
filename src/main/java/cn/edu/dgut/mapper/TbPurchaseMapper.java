package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbPurchaseExample;
import cn.edu.dgut.pojo.TbPurchaseItem;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TbPurchaseMapper {
    int countByExample(TbPurchaseExample example);

    int deleteByExample(TbPurchaseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbPurchase record);

    int insertSelective(TbPurchase record);

    List<TbPurchase> selectByExample(TbPurchaseExample example);

    TbPurchase selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbPurchase record, @Param("example") TbPurchaseExample example);

    int updateByExample(@Param("record") TbPurchase record, @Param("example") TbPurchaseExample example);

    int updateByPrimaryKeySelective(TbPurchase record);

    int updateByPrimaryKey(TbPurchase record);
    
    int updateByPurchaseNo(TbPurchase record);
    
    TbPurchase selectLastRecord();
	
	int countByCondition(Map<String, Object> map);

	List<TbPurchase> pageByCondition(Map<String, Object> map);
	
	int deleteBatch(List<Long> list);
	
	TbPurchase getPurchaseByPurchaseNo(String purchaseNo);
	
	TbPurchase getPurchaseByDrugId(Integer drugId);
	
	List<TbPurchase> getPurchaseByProviderIdAndDrugId(Map<String, Object> map);
	
	List<TbPurchase> selectAllPurchase(Map<String, Object> map);
	List<TbPurchase> selectAllPurchase1(Map<String, Object> map);
	
}