package cn.edu.dgut.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.util.BigDecimalUtil;
import cn.edu.dgut.mapper.TbPurchaseItemMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.PurchaseItemService;
import cn.edu.dgut.service.PurchaseService;

/**
 * @author TanWaiKim
 * @time 2018年2月3日 下午10:09:11
 * @version 1.0
 */
@Service
public class PurchaseItemServiceImpl implements PurchaseItemService {
	@Autowired
	private TbPurchaseItemMapper purchaseItemMapper;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private DrugService drugService;
	
	/**
	 * 分页查询所有的采药单项目信息，刚进来的页面信息
	 */
	@Override
	public List<TbPurchaseItem> getAllPurchaseItem(String purchaseNo, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		//防止刷新该字段不为空
		map.put("purchaseNo", purchaseNo);
		
		// 根据条件查询总数
		int totalNum = purchaseItemMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbPurchaseItem> purchaseItemList = purchaseItemMapper.pageByCondition(map);	
		return purchaseItemList;
	}

	@Override
	public List<TbPurchaseItem> pageByCondition(String purchaseNo, Page page) {
		
		return null;
	}

	@Override
	public TbPurchaseItem getPurchaseItemById(Integer id) {
		TbPurchaseItem purchaseItem = purchaseItemMapper.selectByPrimaryKey(id);
		if (purchaseItem != null) {
			return purchaseItem;
		}
		return null;
	}

	@Override
	public List<TbPurchaseItem> getPurchaseItemByPurchaseNo(String purchaseNo) {
		
		return null;
	}

	/**
	 * 获取tb_purchase_item中最后一条记录
	 * @return
	 */
	public TbPurchaseItem getLastRecord(){
		TbPurchaseItem purchaseItem = purchaseItemMapper.selectLastRecord();
		if(purchaseItem!=null){
			return purchaseItem;
		}
		return null;
	}
	
	/**
	 * 添加一条记录
	 */
	@Override
	public int addPurchaseItemByTbPurchaseItem(TbPurchaseItem purchaseItem) {
		TbPurchaseItem lastPurchaseItem = this.getLastRecord();
		if(lastPurchaseItem!=null){
			purchaseItem.setId(lastPurchaseItem.getId()+1);
		}else{
			purchaseItem.setId(1);
		}
		
		int count = purchaseItemMapper.insert(purchaseItem);		
		return count;
	}


	@Override
	public List<TbPurchaseItem> selectAllPurchaseItem(String purchaseNo) {
		List<TbPurchaseItem> purchaseItemList = purchaseItemMapper.selectAllPurchaseItem(purchaseNo);
		return purchaseItemList;
	}

	/**
	 * 根据采药单项目id修改
	 */
	@Override
	public int updatePurchaseItemByTbPurchaseItem(TbPurchaseItem purchaseItem) {
		int count = purchaseItemMapper.updateByPrimaryKeySelective(purchaseItem);
		return count;
	}

	/**
	 * 删除单条采药详细单
	 */
	@Override
	public int deletePurchaseItemById(Integer id) {
		// 根据id获取采药详细单
		TbPurchaseItem purchaseItem = purchaseItemMapper.selectByPrimaryKey(id);
		// 根据采药号获取采药单
		TbPurchase purchase = purchaseService.getPurchaseByPurchaseNo(purchaseItem.getPurchaseNo());
		// 删除后的采药单总数量
		purchase.setTotalQuantity(purchase.getTotalQuantity() - purchaseItem.getQuantity());
		BigDecimal payment = BigDecimalUtil.mul(purchaseItem.getQuantity().doubleValue(),purchaseItem.getPurchasePrice().doubleValue());
		// 删除后的采药单总价格
		purchase.setTotalPrice(BigDecimalUtil.sub(purchase.getTotalPrice().doubleValue(), payment.doubleValue()));
		purchaseService.updatePurchase(purchase);
		return purchaseItemMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 批量删除
	 */
	@Override
	public int deletePurchaseItemByIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			list.add(Long.valueOf(id).longValue());
			// 根据id获取采药详细单
			TbPurchaseItem purchaseItem = purchaseItemMapper.selectByPrimaryKey(Integer.valueOf(id).intValue());
			// 根据采药号获取采药单
			TbPurchase purchase = purchaseService.getPurchaseByPurchaseNo(purchaseItem.getPurchaseNo());
			// 删除后的采药单总数量
			purchase.setTotalQuantity(purchase.getTotalQuantity() - purchaseItem.getQuantity());
			BigDecimal payment = BigDecimalUtil.mul(purchaseItem.getQuantity().doubleValue(),purchaseItem.getPurchasePrice().doubleValue());
			// 删除后的采药单总价格
			purchase.setTotalPrice(BigDecimalUtil.sub(purchase.getTotalPrice().doubleValue(), payment.doubleValue()));
			purchaseService.updatePurchase(purchase);
		}
		return purchaseItemMapper.deleteBatch(list);
	}

	/**
	 * 根据purchaseNo删除
	 */
	@Override
	public int deletePurchaseItemByPurchase(String purchaseNo) {
		return purchaseItemMapper.deleteByPurchaseNo(purchaseNo);
	}

	/**
	 * 根据条件统计采购情况
	 */
	@Override
	public List<TbPurchaseItem> selectAllPurchase(Map<String, Object> map) {
		List<TbPurchaseItem> purchaseItem = purchaseItemMapper.analysisByCondition(map);
		if (purchaseItem != null) {
			return purchaseItem;
		}
		
		return null;
	}


	@Override
	public TbPurchaseItem selectByDrugIdAndBatchNo(TbPurchaseItem purchaseItem) {
		TbPurchaseItem purchaseItem2 = purchaseItemMapper.selectByDrugIdAndBatchNo(purchaseItem);
		
		if (purchaseItem2 != null) {
			return purchaseItem2;
		}
		
		return null;
	}

	
	@Override
	public List<TbPurchaseItem> selectAllPurchaseItem() {
		List<TbPurchaseItem> purchaseItems = purchaseItemMapper.selectAllPurchaseItem1();
		
		if (purchaseItems != null) {
			return purchaseItems;
		}
		
		return null;
	}

	
	@Override
	public List<TbPurchaseItem> purchaseItemByCondition(String drugName, String drugNo, String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		TbDrug drug = new TbDrug();
		if (!drugName.equals("") || !drugNo.equals("")) {
			drug.setDrugName(drugName);
			drug.setDrugNo(drugNo);
			drug.setId(0);
			drug = drugService.getDrugBySelective(drug);
		} else if (drugName.equals("") && drugNo.equals("")) {
			drug = null;
		} 
		
		if (drug == null) {
			return null;
		}
		
		map.put("drugId", drug.getId());
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		
		List<TbPurchaseItem> purchaseItemList = purchaseItemMapper.analysisByCondition(map);
		
		if (purchaseItemList != null) {
			return purchaseItemList;
		}
		return null;
	}
}
