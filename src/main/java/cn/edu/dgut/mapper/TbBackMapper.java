package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TbBack;
import cn.edu.dgut.pojo.TbBackExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TbBackMapper {
    int countByExample(TbBackExample example);

    int deleteByExample(TbBackExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbBack record);

    int insertSelective(TbBack record);

    List<TbBack> selectByExample(TbBackExample example);

    TbBack selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbBack record, @Param("example") TbBackExample example);

    int updateByExample(@Param("record") TbBack record, @Param("example") TbBackExample example);

    int updateByPrimaryKeySelective(TbBack record);

    int updateByPrimaryKey(TbBack record);
    
    TbBack selectLastRecord();
    
	int countByCondition(Map<String, Object> map);

	List<TbBack> pageByCondition(Map<String, Object> map);
	
	int deleteBatch(List<Long> list);
	
	List<TbBack> selectAllBack();
}