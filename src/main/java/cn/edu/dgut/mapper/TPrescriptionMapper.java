package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TPrescription;
import cn.edu.dgut.pojo.TPrescriptionExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TPrescriptionMapper {
    int countByExample(TPrescriptionExample example);

    int deleteByExample(TPrescriptionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPrescription record);

    int insertSelective(TPrescription record);

    List<TPrescription> selectByExample(TPrescriptionExample example);

    TPrescription selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPrescription record, @Param("example") TPrescriptionExample example);

    int updateByExample(@Param("record") TPrescription record, @Param("example") TPrescriptionExample example);

    int updateByPrimaryKeySelective(TPrescription record);

    int updateByPrimaryKey(TPrescription record);

	int countByCondition(Map<String, Object> map);

	List<TPrescription> pageByCondition(Map<String, Object> map);

	TPrescription selectById(Integer id);

	int deleteBatch(List<Integer> list);
}