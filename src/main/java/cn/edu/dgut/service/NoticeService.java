package cn.edu.dgut.service;

import java.util.List;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TNotice;

public interface NoticeService {
	int addNotice(TNotice notice);
	List<TNotice> getAllNotices(Page page);
	List<TNotice> pageByCondition(String title, Page page);
	int deleteNoticeByDId(Integer id);
	int deleteNoticesByDIds(String[] idArray);
	TNotice getNoticeById(Integer id);
	int updateNotice(TNotice notice);
}
