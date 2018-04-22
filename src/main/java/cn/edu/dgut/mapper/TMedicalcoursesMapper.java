package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TMedicalcourses;
import cn.edu.dgut.pojo.TMedicalcoursesExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TMedicalcoursesMapper {
    int countByExample(TMedicalcoursesExample example);

    int deleteByExample(TMedicalcoursesExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TMedicalcourses record);

    int insertSelective(TMedicalcourses record);

    List<TMedicalcourses> selectByExample(TMedicalcoursesExample example);

    TMedicalcourses selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TMedicalcourses record, @Param("example") TMedicalcoursesExample example);

    int updateByExample(@Param("record") TMedicalcourses record, @Param("example") TMedicalcoursesExample example);

    int updateByPrimaryKeySelective(TMedicalcourses record);

    int updateByPrimaryKey(TMedicalcourses record);

	int countByCondition(Map<String, Object> map);

	List<TMedicalcourses> pageByCondition(Map<String, Object> map);

	int deleteBatch(List<Long> idList);

	TMedicalcourses selectLastRecord();

	List<TMedicalcourses> selectAllMedicalcourses();
}