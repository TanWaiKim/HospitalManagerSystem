package cn.edu.dgut.service;

import java.util.List;
import java.util.Map;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbPurchaseItem;

/**
 * @author TanWaiKim
 * @time 2018年2月3日 下午10:00:35
 * @version 1.0
 */
public interface PurchaseItemService {
	List<TbPurchaseItem> getAllPurchaseItem(String purchaseNo, Page page);
	List<TbPurchaseItem> pageByCondition(String purchaseNo,Page page);
	TbPurchaseItem getPurchaseItemById(Integer id);
	List<TbPurchaseItem> getPurchaseItemByPurchaseNo(String purchaseNo);
	int addPurchaseItemByTbPurchaseItem(TbPurchaseItem purchaseItem);
	int updatePurchaseItemByTbPurchaseItem(TbPurchaseItem purchaseItem);
	int updateByDrugIdSelective(TbPurchaseItem purchaseItem);
	int deletePurchaseItemById(Integer id);
	int deletePurchaseItemByIds(String[] ids);
	int deletePurchaseItemByPurchase(String purchaseNo);
	List<TbPurchaseItem> selectAllPurchaseItem(String purchaseNo);
	List<TbPurchaseItem> selectAllPurchaseItemByDrugId(Integer drugId);
	List<TbPurchaseItem> selectAllPurchase(Map<String, Object> map);
	TbPurchaseItem selectByDrugIdAndBatchNo(TbPurchaseItem purchaseItem);
	List<TbPurchaseItem> selectAllPurchaseItem();
	List<TbPurchaseItem> purchaseItemByCondition(String drugName,String drugNo,String beginTime,String endTime);
}
