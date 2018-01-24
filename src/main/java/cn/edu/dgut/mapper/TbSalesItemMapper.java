package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TbSalesItem;
import cn.edu.dgut.pojo.TbSalesItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbSalesItemMapper {
    int countByExample(TbSalesItemExample example);

    int deleteByExample(TbSalesItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbSalesItem record);

    int insertSelective(TbSalesItem record);

    List<TbSalesItem> selectByExample(TbSalesItemExample example);

    TbSalesItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbSalesItem record, @Param("example") TbSalesItemExample example);

    int updateByExample(@Param("record") TbSalesItem record, @Param("example") TbSalesItemExample example);

    int updateByPrimaryKeySelective(TbSalesItem record);

    int updateByPrimaryKey(TbSalesItem record);
}