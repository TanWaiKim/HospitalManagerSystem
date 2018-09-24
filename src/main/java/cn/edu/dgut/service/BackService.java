package cn.edu.dgut.service;

import java.util.List;

import cn.edu.dgut.common.dto.BackDto;
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
	List<TbBack> pageByCondition(String backNo, Integer providerId, Page page);
	TbPurchase getBackById(Integer id);
	int addBackByTbBack(TbBack back);
	int deleteBackById(Integer id);
	int deleteBackByIds(String[] ids);
	List<TbBack> selectAllBack();
	int updateBackByBackId(TbBack back);
	int updateBack(TbBack back);
	List<TbBack> getBackByBackNo(String backNo,Page page);
	List<TbBack> getBackByBackNo1(String backNo);
	TbBack getBackByBackNoAndDrugId(Integer drugId, String backNo);
	int updateBackByBackItemId(BackDto backDto);
	int addBackByDrug(BackDto backDto);
	int deleteOneByBackNoAndDrugId(Integer drugId, String backNo);
	
}
