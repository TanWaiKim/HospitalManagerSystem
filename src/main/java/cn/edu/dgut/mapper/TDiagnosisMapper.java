package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TDiagnosis;
import cn.edu.dgut.pojo.TDiagnosisExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TDiagnosisMapper {
    int countByExample(TDiagnosisExample example);

    int deleteByExample(TDiagnosisExample example);

    int deleteByPrimaryKey(Long diagnosisId);

    int insert(TDiagnosis record);

    int insertSelective(TDiagnosis record);

    List<TDiagnosis> selectByExample(TDiagnosisExample example);

    TDiagnosis selectByPrimaryKey(Long diagnosisId);

    int updateByExampleSelective(@Param("record") TDiagnosis record, @Param("example") TDiagnosisExample example);

    int updateByExample(@Param("record") TDiagnosis record, @Param("example") TDiagnosisExample example);

    int updateByPrimaryKeySelective(TDiagnosis record);

    int updateByPrimaryKey(TDiagnosis record);
    
    int countByCondition(Map<String, Object> map);

	List<TDiagnosis> pageByCondition(Map<String, Object> map);

	TDiagnosis selectByDidAndPName(long diagnosisId, String patientName);

	TDiagnosis selectByDidAndPName(Map<String, Object> map);

	int deleteBatch(List<Long> list);

	TDiagnosis selectByDid(long diagnosisId);

	int countByPersonType(Map<String, Object> map);

	List<TDiagnosis> pageByPersonType(Map<String, Object> map);
}