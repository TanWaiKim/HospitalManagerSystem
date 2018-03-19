package cn.edu.dgut.service;

import java.util.List;
import java.util.Map;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbSalesItem;

/**
 * @author TanWaiKim
 * @time 2018年2月24日 下午10:48:26
 * @version 1.0
 */
public interface SalesItemService {
	List<TbSalesItem> getAllSalesItem(String salesNo, Page page);
	List<TbSalesItem> pageByCondition(String salesNo,Page page);
	TbSalesItem getSalesItemById(Integer id);
	List<TbSalesItem> getSalesItemBySalesNo(String salesNo);
	int addSalesItemByTbSalesItem(TbSalesItem salesItem);
	int updateSalesItemByTbSalesItem(TbSalesItem sales);
	int deleteSalesItemById(Integer id);
	int deleteSalesItemByIds(String[] ids);
	int deleteSalesItemBySalesItem(String salesNo);
	List<TbSalesItem> selectAllSalesItem();
	List<TbSalesItem> selectAllSalesItem(Map<String, Object> map);
	TbSalesItem selectByDrugIdAndBatchNo(TbSalesItem salesItem);
	List<TbSalesItem> selectAllSalesItem(String salesNo);
}
