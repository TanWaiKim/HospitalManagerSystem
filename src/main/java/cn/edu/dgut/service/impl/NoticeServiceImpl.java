package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.util.IDUtils;
import cn.edu.dgut.mapper.TNoticeMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TNotice;

import cn.edu.dgut.service.NoticeService;
@Service
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private TNoticeMapper noticeMapper;
	
	//添加公告
	@Override
	public int addNotice(TNotice notice) {
		long noticeId = IDUtils.getId();
		notice.setNoticeId(noticeId);
		Date date = new Date();
		notice.setCreated(date);
		notice.setUpdated(date);
		return noticeMapper.insert(notice);
	}

	//分页查询所有公告
	@Override
	public List<TNotice> getAllNotices(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据条件查询总数
		int totalNum = noticeMapper.countByCondition(map);
		
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		return noticeMapper.pageByCondition(map);
	}

	//根据标题查询所有公告并分页
	@Override
	public List<TNotice> pageByCondition(String title, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", title);
		// 根据条件查询总数
		int totalNum = noticeMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return noticeMapper.pageByCondition(map);
	}

	//通过id删除公告
	@Override
	public int deleteNoticeByDId(Integer id) {
		return noticeMapper.deleteByPrimaryKey(id);
	}

	//批量删除公告
	@Override
	public int deleteNoticesByDIds(String[] idArray) {
		List<Integer> list = new ArrayList<Integer>();
		for (String id : idArray) {
			list.add(Integer.valueOf(id));
		}
		return noticeMapper.deleteBatch(list);
	}

	//通过id查询公告
	@Override
	public TNotice getNoticeById(Integer id) {
		return noticeMapper.selectByPrimaryKey(id);
	}

	//更新公告
	@Override
	public int updateNotice(TNotice notice) {
		//notice.setUpdated(new Date());
		return noticeMapper.updateNotice(notice);
	}

}
