package cn.edu.dgut.service;

import java.util.List;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbBack;
import cn.edu.dgut.pojo.TbPurchase;

/**
 * @author TanWaiKim
 * @time 2018年2月3日 下午9:33:13
 * @version 1.0
 */
public interface BackService {
	List<TbBack> getAllBack(Page page);
	List<TbBack> pageByCondition(String backType, String backObject, Page page);
	TbPurchase getBackById(Integer id);
	int addBackByTbBack(TbBack back);
	int deleteBackById(Integer id);
	int deleteBackByIds(String[] ids);
	List<TbBack> selectAllBack();
	int updateBackByBackId(TbBack back);
	int updateBack(TbBack back);
}
