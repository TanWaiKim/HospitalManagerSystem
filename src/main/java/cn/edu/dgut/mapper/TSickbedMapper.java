package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TSickbed;
import cn.edu.dgut.pojo.TSickbedExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSickbedMapper {
    int countByExample(TSickbedExample example);

    int deleteByExample(TSickbedExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSickbed record);

    int insertSelective(TSickbed record);

    List<TSickbed> selectByExample(TSickbedExample example);

    TSickbed selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSickbed record, @Param("example") TSickbedExample example);

    int updateByExample(@Param("record") TSickbed record, @Param("example") TSickbedExample example);

    int updateByPrimaryKeySelective(TSickbed record);

    int updateByPrimaryKey(TSickbed record);
}