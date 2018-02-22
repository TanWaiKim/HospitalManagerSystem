package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TbProvider;
import cn.edu.dgut.pojo.TbProviderExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TbProviderMapper {
    int countByExample(TbProviderExample example);

    int deleteByExample(TbProviderExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbProvider record);

    int insertSelective(TbProvider record);

    List<TbProvider> selectByExample(TbProviderExample example);

    TbProvider selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbProvider record, @Param("example") TbProviderExample example);

    int updateByExample(@Param("record") TbProvider record, @Param("example") TbProviderExample example);

    int updateByPrimaryKeySelective(TbProvider record);

    int updateByPrimaryKey(TbProvider record);
   
    TbProvider selectLastRecord();
    
	int countByCondition(Map<String, Object> map);

	List<TbProvider> pageByCondition(Map<String, Object> map);

	int deleteBatch(List<Long> list);
	
	List<TbProvider> selectAllProvider();
}