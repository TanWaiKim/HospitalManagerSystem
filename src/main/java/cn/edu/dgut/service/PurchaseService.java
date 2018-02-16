package cn.edu.dgut.service;

import java.util.List;

import cn.edu.dgut.common.dto.PurchaseDto;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbPurchase;

/**
 * @author TanWaiKim
 * @time 2018年2月3日 下午9:33:13
 * @version 1.0
 */
public interface PurchaseService {
	List<TbPurchase> getAllPurchase(Page page);
	List<TbPurchase> pageByCondition(String purchaseNo, Integer providerId,String warehouseNo, Page page);
	TbPurchase getPurchaseById(Integer id);
	TbPurchase getPurchaseByPurchaseNo(String purchaseNo);
	int addPurchaseByTbPurchase(PurchaseDto purchaseDto);
	int deletePurchaseById(Integer id);
	int deletePurchaseByIds(String[] ids);
	List<TbPurchase> selectAllPurchase();
	int updatePurchaseByPurchaseNo(PurchaseDto purchaseDto);
	int updatePurchaseByPurchaseItemId(PurchaseDto purchaseDto);
	int updatePurchase(TbPurchase purchase);
}
