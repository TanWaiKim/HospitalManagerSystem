package cn.edu.dgut.service;

import java.util.List;

import cn.edu.dgut.common.dto.PurchaseDto;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbPurchaseItem;

/**
 * @author TanWaiKim
 * @time 2018年2月3日 下午9:33:13
 * @version 1.0
 */
public interface PurchaseService {
	List<TbPurchase> getAllPurchase(Page page);
	List<TbPurchase> pageByCondition(String purchaseNo, Integer providerId, Integer isStock, Page page);
	TbPurchase getPurchaseById(Integer id);
	TbPurchase getPurchaseByPurchaseNo(String purchaseNo);
	int addPurchaseByTbPurchase(PurchaseDto purchaseDto);
	int updatePurchaseByTbPurchase(PurchaseDto purchaseDto);
	int deletePurchaseById(Integer id);
	int deletePurchaseByIds(String[] ids);
	List<TbPurchase> selectAllPurchase(String beginTime,String endTime);
	int updatePurchaseByPurchaseNo(PurchaseDto purchaseDto);
	int updatePurchaseByPurchaseItemId(PurchaseDto purchaseDto);
	int updatePurchase(TbPurchase purchase);
	List<TbPurchase> purchaseByCondition(String drugName,String beginTime,String endTime);
	TbPurchase getPurchaseByDrugId(Integer drugId);
	
	List<TbPurchase> getPurchaseByProviderIdAndDrugId(Integer providerId,Integer drugId);
}
