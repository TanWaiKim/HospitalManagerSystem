package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.pojo.TPatientExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TPatientMapper {
    int countByExample(TPatientExample example);

    int deleteByExample(TPatientExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TPatient record);

    int insertSelective(TPatient record);

    List<TPatient> selectByExample(TPatientExample example);

    TPatient selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TPatient record, @Param("example") TPatientExample example);

    int updateByExample(@Param("record") TPatient record, @Param("example") TPatientExample example);

    int updateByPrimaryKeySelective(TPatient record);

    int updateByPrimaryKey(TPatient record);

	int countByCondition(Map<String, Object> map);

	List<TPatient> pageByCondition(Map<String, Object> map);

	int deleteBatch(List<Long> list);

	void insertInfoBatch(List<TPatient> patientList);

	TPatient selectLastRecord();

	List<TPatient> selectAllPatient();
}