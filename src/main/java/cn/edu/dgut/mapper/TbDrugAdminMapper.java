package cn.edu.dgut.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.edu.dgut.pojo.TbDrugAdmin;
import cn.edu.dgut.pojo.TbDrugAdminExample;

public interface TbDrugAdminMapper {
    int countByExample(TbDrugAdminExample example);

    int deleteByExample(TbDrugAdminExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbDrugAdmin record);

    int insertSelective(TbDrugAdmin record);

    List<TbDrugAdmin> selectByExample(TbDrugAdminExample example);

    TbDrugAdmin selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbDrugAdmin record, @Param("example") TbDrugAdminExample example);

    int updateByExample(@Param("record") TbDrugAdmin record, @Param("example") TbDrugAdminExample example);

    int updateByPrimaryKeySelective(TbDrugAdmin record);

    int updateByPrimaryKey(TbDrugAdmin record);
    
    TbDrugAdmin selectLogin(@Param("username") String username, @Param("password") String password);

	int countByCondition(Map<String, Object> map);

	List<TbDrugAdmin> pageByCondition(Map<String, Object> map);

	int deleteBatch(List<Long> list);
	
	List<TbDrugAdmin> selectAllDrugAdmin();
}