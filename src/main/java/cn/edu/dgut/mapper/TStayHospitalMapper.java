package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TStayHospital;
import cn.edu.dgut.pojo.TStayHospitalExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TStayHospitalMapper {
    int countByExample(TStayHospitalExample example);

    int deleteByExample(TStayHospitalExample example);

    int deleteByPrimaryKey(Long id);
    
    int insert(TStayHospital record);

    int insertSelective(TStayHospital record);

    List<TStayHospital> selectByExample(TStayHospitalExample example);
    
    TStayHospital selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TStayHospital record, @Param("example") TStayHospitalExample example);

    int updateByExample(@Param("record") TStayHospital record, @Param("example") TStayHospitalExample example);

	int countByCondition(Map<String, Object> map);

	List<TStayHospital> pageByCondition(Map<String, Object> map);

	int deleteBatch(List<Long> list);

	void deleteBatchBySickIds(List<Long> list);

}