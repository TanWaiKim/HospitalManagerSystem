package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbPurchaseExample;
import java.util.List;
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
}