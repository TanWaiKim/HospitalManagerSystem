package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TbDrugtype;
import cn.edu.dgut.pojo.TbDrugtypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbDrugtypeMapper {
    int countByExample(TbDrugtypeExample example);

    int deleteByExample(TbDrugtypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbDrugtype record);

    int insertSelective(TbDrugtype record);

    List<TbDrugtype> selectByExample(TbDrugtypeExample example);

    TbDrugtype selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbDrugtype record, @Param("example") TbDrugtypeExample example);

    int updateByExample(@Param("record") TbDrugtype record, @Param("example") TbDrugtypeExample example);

    int updateByPrimaryKeySelective(TbDrugtype record);

    int updateByPrimaryKey(TbDrugtype record);
}