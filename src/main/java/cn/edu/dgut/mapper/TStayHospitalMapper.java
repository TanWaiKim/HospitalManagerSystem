package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TStayHospital;
import cn.edu.dgut.pojo.TStayHospitalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TStayHospitalMapper {
    int countByExample(TStayHospitalExample example);

    int deleteByExample(TStayHospitalExample example);

    int insert(TStayHospital record);

    int insertSelective(TStayHospital record);

    List<TStayHospital> selectByExample(TStayHospitalExample example);

    int updateByExampleSelective(@Param("record") TStayHospital record, @Param("example") TStayHospitalExample example);

    int updateByExample(@Param("record") TStayHospital record, @Param("example") TStayHospitalExample example);
}