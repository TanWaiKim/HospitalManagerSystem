package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TSickbed;
import cn.edu.dgut.pojo.TSickbedExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TSickbedMapper {
    int countByExample(TSickbedExample example);

    int deleteByExample(TSickbedExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TSickbed record);

    int insertSelective(TSickbed record);

    List<TSickbed> selectByExample(TSickbedExample example);

    TSickbed selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TSickbed record, @Param("example") TSickbedExample example);

    int updateByExample(@Param("record") TSickbed record, @Param("example") TSickbedExample example);

    int updateByPrimaryKeySelective(TSickbed record);

    int updateByPrimaryKey(TSickbed record);

    TSickbed selectLastRecord();

	int countByCondition(Map<String, Object> map);

	List<TSickbed> pageByCondition(Map<String, Object> map);

	int deleteBatch(List<Long> list);

	List<TSickbed> selectAllSickbed();

	List<TSickbed> selectSickbedIdByAutoComplete(String term);
}