package cn.edu.dgut.mapper;

import cn.edu.dgut.pojo.TNotice;
import cn.edu.dgut.pojo.TNoticeExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TNoticeMapper {
    int countByExample(TNoticeExample example);

    int deleteByExample(TNoticeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TNotice record);

    int insertSelective(TNotice record);

    List<TNotice> selectByExample(TNoticeExample example);

    TNotice selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TNotice record, @Param("example") TNoticeExample example);

    int updateByExample(@Param("record") TNotice record, @Param("example") TNoticeExample example);

    int updateByPrimaryKeySelective(TNotice record);

    int updateByPrimaryKey(TNotice record);

	int countByCondition(Map<String, Object> map);

	List<TNotice> pageByCondition(Map<String, Object> map);

	int deleteBatch(List<Integer> list);

	int updateNotice(TNotice notice);
}