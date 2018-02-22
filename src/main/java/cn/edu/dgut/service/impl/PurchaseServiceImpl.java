package cn.edu.dgut.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.edu.dgut.common.dto.PurchaseDto;
import cn.edu.dgut.common.util.BigDecimalUtil;
import cn.edu.dgut.common.util.IDUtils;
import cn.edu.dgut.mapper.TbPurchaseMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbProvider;
import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.pojo.TbWarehouse;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.ProviderService;
import cn.edu.dgut.service.PurchaseItemService;
import cn.edu.dgut.service.PurchaseService;
import cn.edu.dgut.service.StockService;
import cn.edu.dgut.service.WarehouseService;

/**
 * @author TanWaiKim
 * @time 2018年2月3日 下午9:58:29
 * @version 1.0
 */
@Service
public class PurchaseServiceImpl implements PurchaseService {
	@Autowired
	private TbPurchaseMapper purchaseMapper;
	@Autowired
	private PurchaseItemService purchaseItemService;
	@Autowired
	private DrugService drugService;
	@Autowired
	private ProviderService providerService;
	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private StockService stockService;
	
	/**
	 * 分页查询所有的采药单信息，刚进来的页面信息
	 */
	@Override
	public List<TbPurchase> getAllPurchase(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		//防止刷新该字段不为空
		map.put("providerId", null);
		map.put("warehouseNo", null);
		
		// 根据条件查询总数
		int totalNum = purchaseMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbPurchase> purchaseList = purchaseMapper.pageByCondition(map);
		
		for (TbPurchase tbPurchase : purchaseList) {
			TbProvider provider = providerService.getProviderById(tbPurchase.getProviderId());
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(tbPurchase.getWarehouseNo());
			tbPurchase.setProvider(provider);
			tbPurchase.setWarehouse(warehouse);
		}
		
		return purchaseList;
	}

	/**
	 * 分页条件查询
	 */
	@Override
	public List<TbPurchase> pageByCondition(String purchaseNo, Integer providerId, String warehouseNo, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("providerId", providerId);
		map.put("purchaseNo", purchaseNo);
		map.put("warehouseNo", warehouseNo);
		// 根据条件查询总数
		int totalNum = purchaseMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbPurchase> purchaseList = purchaseMapper.pageByCondition(map);
		
		for (TbPurchase tbPurchase : purchaseList) {
			TbProvider provider = providerService.getProviderById(tbPurchase.getProviderId());
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(tbPurchase.getWarehouseNo());
			tbPurchase.setProvider(provider);
			tbPurchase.setWarehouse(warehouse);
		}
		
		return purchaseList;
	}

	/**
	 * 根据id获取purchase
	 */
	@Override
	public TbPurchase getPurchaseById(Integer id) {
		TbPurchase purchase = purchaseMapper.selectByPrimaryKey(id);
		if (purchase != null) {
			return purchase;
		}
		return null;
	}

	/**
	 * 根据采药单号查询
	 * @param purchaseNo
	 * @return
	 */
	@Override
	public TbPurchase getPurchaseByPurchaseNo(String purchaseNo) {
		TbPurchase purchase = purchaseMapper.selectByPurchaseNo(purchaseNo);
		if (purchase != null) {
			TbProvider provider = providerService.getProviderById(purchase.getProviderId());
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(purchase.getWarehouseNo());
			purchase.setProvider(provider);
			purchase.setWarehouse(warehouse);		
			return purchase;
		}
		return null;
	}

	/**
	 * 获取tb_purchase中最后一条记录
	 * @return
	 */
	public TbPurchase getLastRecord(){
		TbPurchase purchase = purchaseMapper.selectLastRecord();
		if(purchase!=null){
			return purchase;
		}
		return null;
	}
	
	/**
	 * 计算采药详细总价
	 * @param purchaseItemList
	 * @return
	 */
    private BigDecimal getOrderItemTotalPrice(Integer quantity, BigDecimal purchasePrice){
        BigDecimal payment = new BigDecimal("0");
        payment = BigDecimalUtil.mul(quantity.doubleValue(), purchasePrice.doubleValue());
        return payment;
    }
    
	/**
	 * 计算一条采药单的总价
	 * @param purchaseItemList
	 * @return
	 */
    private BigDecimal getOrderTotalPrice(List<TbPurchaseItem> purchaseItemList){
        BigDecimal payment = new BigDecimal("0");
        for (TbPurchaseItem purchaseItem:purchaseItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(),purchaseItem.getPurchaseTotalPrice().doubleValue());
        }
        return payment;
    }
    
	/**
	 * 计算一条采药单项目的总数量
	 * @param purchaseItemList
	 * @return
	 */
    private BigDecimal getOrderTotalCount(List<TbPurchaseItem> purchaseItemList){
        BigDecimal count = new BigDecimal("0");
        for (TbPurchaseItem purchaseItem:purchaseItemList){
        	count = BigDecimalUtil.add(count.doubleValue(),purchaseItem.getQuantity().doubleValue());
        }
        return count;
    }
	
    /**
     * 添加新的采药单、采药详细单
     * @param purchaseDto
     * @return
     */
	@Override
	public int addPurchaseByTbPurchase(PurchaseDto purchaseDto) {
		TbPurchase lastPurchase = this.getLastRecord();
		TbPurchase purchase = new TbPurchase();
		if(lastPurchase!=null){
			purchase.setId(lastPurchase.getId()+1);
		}else{
			purchase.setId(1);
		}
	
		String purchaseNo = "CGYY"+IDUtils.getId() + "";
		purchase.setPurchaseNo(purchaseNo);	
		purchase.setWarehouseNo(purchaseDto.getWarehouseNo());
		purchase.setProviderId(purchaseDto.getProviderId());
		purchase.setOperator(purchaseDto.getOperator());
		purchase.setRemarks(purchaseDto.getRemarks());
		
		
		
		TbDrug drug = drugService.getDrugById(purchaseDto.getDrugId());
		
		// 组装采药单项目
		TbPurchaseItem purchaseItem = new TbPurchaseItem();
		purchaseItem.setPurchaseNo(purchase.getPurchaseNo());
		purchaseItem.setDrugId(purchaseDto.getDrugId());
		purchaseItem.setDrugName(drug.getDrugName());
		purchaseItem.setPurchasePrice(purchaseDto.getPurchasePrice());
		purchaseItem.setSalePrice(purchaseDto.getSalePrice());
		purchaseItem.setQuantity(purchaseDto.getQuantity());
		purchaseItem.setPurchaseTotalPrice(this.getOrderItemTotalPrice(purchaseDto.getQuantity(), purchaseDto.getPurchasePrice()));
		purchaseItem.setStatus("待入库");
		purchaseItem.setBatchNo(purchaseDto.getBatchNo());
		purchaseItem.setProduceTime(purchaseDto.getProduceTime());
		purchaseItem.setValidTime(purchaseDto.getValidTime());
		
		TbPurchaseItem purchaseItem2 = purchaseItemService.selectByDrugIdAndBatchNo(purchaseItem);
		
		if (purchaseItem2 != null) {
			purchaseItem.setId(purchaseItem2.getId());
			purchaseItem.setQuantity(purchaseItem2.getQuantity()+purchaseItem.getQuantity());
			purchaseItemService.updatePurchaseItemByTbPurchaseItem(purchaseItem);
		} else {
			// 添加采药单子项目
			purchaseItemService.addPurchaseItemByTbPurchaseItem(purchaseItem);
		}
		
		// 设置药品的进药单价、销药单价
//		drug.setPurchasePrice(purchaseDto.getPurchasePrice());
		drug.setSalePrice(purchaseDto.getSalePrice());
		drugService.updateDrugByTbDrug(drug);
		
		List<TbPurchaseItem> purchaseItemList = purchaseItemService.selectAllPurchaseItem(purchase.getPurchaseNo());
		purchase.setTotalQuantity(this.getOrderTotalCount(purchaseItemList).intValue());
		purchase.setTotalPrice(this.getOrderTotalPrice(purchaseItemList));
		
		// 添加采药单
		int count = purchaseMapper.insert(purchase);
		
		
//		TbStock stock = new TbStock();
//		stock.setWarehouseNo(purchaseDto.getWarehouseNo());
//		stock.setDrugId(purchaseItem.getDrugId());
//		stock.setDrugName(purchaseItem.getDrugName());
//		stock.setStockQuantity(purchaseItem.getQuantity());
//		stock.setPurchasePrice(purchaseItem.getPurchasePrice());
//		stock.setSalePrice(purchaseItem.getSalePrice());
//		stock.setProducedTime(new DateTimeUtil().strToDate(drug.getProducedTime(), "yyyy-MM-dd"));
//		stock.setValidTime(new DateTimeUtil().strToDate(drug.getValidTime(), "yyyy-MM-dd"));
//		stock.setOperator(purchaseDto.getOperator());
//		
//		
//		TbStock stock2 = stockService.getStockByDrug(purchaseDto.getDrugId());
//		
//		if (stock2 != null) {
//			BigDecimal stockQuantity = BigDecimalUtil.add(purchaseItem.getQuantity().doubleValue(),
//					stock2.getStockQuantity().doubleValue());
//			stock.setStockQuantity(stockQuantity.intValue());
//			stockService.updateStockBySelective(stock);
//		} else {
//			// 添加库存
//			stockService.addStockByTbStock(stock);	
//		}
		
		return count;
	}


	/**
	 * 根据采药单号，添加新的采药详细单
	 */
	@Override
	public int updatePurchaseByPurchaseNo(PurchaseDto purchaseDto) {
		TbPurchase purchase = new TbPurchase();
		purchase.setPurchaseNo(purchaseDto.getPurchaseNo());
		purchase.setTotalQuantity(purchaseDto.getTotalQuantity()+purchaseDto.getQuantity());
		BigDecimal totalPrice = BigDecimalUtil.add(purchaseDto.getTotalPrice().doubleValue(),
				BigDecimalUtil.mul(purchaseDto.getQuantity().doubleValue(), purchaseDto.getPurchasePrice().doubleValue()).doubleValue());
		purchase.setTotalPrice(totalPrice);
		
		// 更新采药单
		int count = purchaseMapper.updateByPurchaseNoSelective(purchase);
		
		TbDrug drug = drugService.getDrugById(purchaseDto.getDrugId());
		
		// 组装采药单项目
		TbPurchaseItem purchaseItem = new TbPurchaseItem();
		purchaseItem.setPurchaseNo(purchase.getPurchaseNo());
		purchaseItem.setDrugId(purchaseDto.getDrugId());
		purchaseItem.setDrugName(drug.getDrugName());
		purchaseItem.setPurchasePrice(purchaseDto.getPurchasePrice());
		purchaseItem.setSalePrice(purchaseDto.getSalePrice());
		purchaseItem.setQuantity(purchaseDto.getQuantity());
		purchaseItem.setPurchaseTotalPrice(this.getOrderItemTotalPrice(purchaseDto.getQuantity(), purchaseDto.getPurchasePrice()));
		purchaseItem.setStatus("待入库");
		purchaseItem.setBatchNo(purchaseDto.getBatchNo());
		purchaseItem.setProduceTime(purchaseDto.getProduceTime());
		purchaseItem.setValidTime(purchaseDto.getValidTime());
		
		TbPurchaseItem purchaseItem2 = purchaseItemService.selectByDrugIdAndBatchNo(purchaseItem);
		
		if (purchaseItem2 != null) {
			purchaseItem.setId(purchaseItem2.getId());
			purchaseItem.setQuantity(purchaseItem2.getQuantity()+purchaseItem.getQuantity());
			purchaseItemService.updatePurchaseItemByTbPurchaseItem(purchaseItem);
		} else {
			// 添加采药单子项目
			purchaseItemService.addPurchaseItemByTbPurchaseItem(purchaseItem);
		}
		
		// 设置药品的进药单价、销药单价
//		drug.setPurchasePrice(purchaseDto.getPurchasePrice());
		drug.setSalePrice(purchaseDto.getSalePrice());
		drugService.updateDrugByTbDrug(drug);
		
//		
//		TbStock stock = new TbStock();
//		stock.setWarehouseNo(purchaseDto.getWarehouseNo());
//		stock.setDrugId(purchaseItem.getDrugId());
//		stock.setDrugName(purchaseItem.getDrugName());
//		stock.setStockQuantity(purchaseItem.getQuantity());
//		stock.setPurchasePrice(purchaseItem.getPurchasePrice());
//		stock.setSalePrice(purchaseItem.getSalePrice());
//		stock.setProducedTime(new DateTimeUtil().strToDate(drug.getProducedTime(), "yyyy-MM-dd"));
//		stock.setValidTime(new DateTimeUtil().strToDate(drug.getValidTime(), "yyyy-MM-dd"));
//		stock.setOperator(purchaseDto.getOperator());
//		
//		TbStock stock2 = stockService.getStockByDrug(purchaseDto.getDrugId());
//		
//		if (stock2 != null) {
//			BigDecimal stockQuantity = BigDecimalUtil.add(purchaseItem.getQuantity().doubleValue(),
//					stock2.getStockQuantity().doubleValue());
//			stock.setStockQuantity(stockQuantity.intValue());
//			stockService.updateStockBySelective(stock);
//		} else {
//			// 添加库存
//			stockService.addStockByTbStock(stock);	
//		}
		
		
		return count;
	}
	
	/**
	 * 根据采药单号，添加新的采药详细单
	 */
	@Override
	public int updatePurchase(TbPurchase purchase) {
		return purchaseMapper.updateByPurchaseNoSelective(purchase);
	}
	
	/**
	 * 删除单条采药详细单
	 */
	@Override
	public int deletePurchaseById(Integer id) {
		
		return purchaseMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 批量删除
	 */
	@Override
	public int deletePurchaseByIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			list.add(Long.valueOf(id).longValue());
			// 先删除采药详细单
			TbPurchase purchase = purchaseMapper.selectByPrimaryKey(Integer.valueOf(id).intValue());
			String purchaseNo = purchase.getPurchaseNo();
			purchaseItemService.deletePurchaseItemByPurchase(purchaseNo);
		}
		return purchaseMapper.deleteBatch(list);
	}

	/**
	 * 获取所有的订单
	 * @return
	 */
	@Override
	public List<TbPurchase> selectAllPurchase() {
		List<TbPurchase> purchaseList = purchaseMapper.selectAllPurchase();
		if (purchaseList != null ) {
			return purchaseList;
		}
		return null;
	}

	/**
	 * 根据采药详细单id更新采药单、采药详细单、库存单
	 */
	@Override
	public int updatePurchaseByPurchaseItemId(PurchaseDto purchaseDto) {
		TbPurchase purchase = new TbPurchase();
		purchase.setPurchaseNo(purchaseDto.getPurchaseNo());
		
		TbDrug drug = drugService.getDrugById(purchaseDto.getDrugId());
		
		// 组装采药单项目
		TbPurchaseItem purchaseItem = new TbPurchaseItem();
		purchaseItem.setId(purchaseDto.getId());
		purchaseItem.setPurchaseNo(purchase.getPurchaseNo());
		purchaseItem.setDrugId(purchaseDto.getDrugId());
		purchaseItem.setDrugName(drug.getDrugName());
		purchaseItem.setPurchasePrice(purchaseDto.getPurchasePrice());
		purchaseItem.setSalePrice(purchaseDto.getSalePrice());
		purchaseItem.setQuantity(purchaseDto.getQuantity());
		purchaseItem.setPurchaseTotalPrice(this.getOrderItemTotalPrice(purchaseDto.getQuantity(), purchaseDto.getPurchasePrice()));
		purchaseItem.setStatus("待入库");
		purchaseItem.setBatchNo(purchaseDto.getBatchNo());
		purchaseItem.setProduceTime(purchaseDto.getProduceTime());
		purchaseItem.setValidTime(purchaseDto.getValidTime());
		
		// 更新采药详细单前的记录，用于比较
		TbPurchaseItem purchaseItem1 = purchaseItemService.getPurchaseItemById(purchaseDto.getId());
		
		// 修改采药详细单
		purchaseItemService.updatePurchaseItemByTbPurchaseItem(purchaseItem);
		
		// 设置药品的进药单价、销药单价
//		drug.setPurchasePrice(purchaseDto.getPurchasePrice());
		drug.setSalePrice(purchaseDto.getSalePrice());
		drugService.updateDrugByTbDrug(drug);
		
		TbStock stock = new TbStock();
//		stock.setWarehouseNo(purchaseDto.getWarehouseNo());
		stock.setDrugId(purchaseItem.getDrugId());
//		stock.setProducedTime(new DateTimeUtil().strToDate(drug.getProducedTime(), "yyyy-MM-dd"));
//		stock.setValidTime(new DateTimeUtil().strToDate(drug.getValidTime(), "yyyy-MM-dd"));
		stock.setOperator(purchaseDto.getOperator());
		stock.setBatchNo(purchaseDto.getBatchNo());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugId", purchaseItem.getDrugId());
		map.put("batchNo", purchaseItem.getBatchNo());
		
		// 获取当前该药品的库存信息
		TbStock stock2 = stockService.getStockByDrug(map);
		Integer diffQuantity  = purchaseItem.getQuantity() - purchaseItem1.getQuantity();
		
		// 如果仓库中存在，则减少相应的库存量
		if (stock2 != null) {
			// 用新的药品数量减去旧的采购详细单的数量，得出数量差，然后将结果与库存的数量相加
			// diffQuantity = purchaseItem.getQuantity() - purchaseItem1.getQuantity();
			// 最后的库存数量
			// Integer stockQuantity = diffQuantity + stock2.getStockQuantity();
			// 减去修改前的采购量，因为在修改后还要继续入库，否则库存量重复
			
			Integer stockQuantity = 0;
			
			if (stock2.getStockQuantity() != 0) {
				stockQuantity = stock2.getStockQuantity()-purchaseItem1.getQuantity();
			} else{
				stockQuantity = 0;
			}
			stock.setId(stock2.getId());
			stock.setStockQuantity(stockQuantity);
			stockService.updateStockBySelective(stock);
		}
		
		// 最后的采购单数量
		purchase.setTotalQuantity(purchaseDto.getTotalQuantity()+diffQuantity);
		
		// 最后的采购单总进价
//		BigDecimal totalPrice = BigDecimalUtil.add(purchaseDto.getTotalPrice().doubleValue(),
//				BigDecimalUtil.mul(diffQuantity.doubleValue(), purchaseDto.getPurchasePrice().doubleValue()).doubleValue());
		
		BigDecimal totalPrice = BigDecimalUtil.add(
				BigDecimalUtil.sub(purchaseDto.getTotalPrice().doubleValue(), 
						BigDecimalUtil.mul(purchaseItem1.getQuantity().doubleValue(), purchaseItem1.getPurchasePrice().doubleValue()).doubleValue()).doubleValue(),
				BigDecimalUtil.mul(purchaseDto.getQuantity().doubleValue(), purchaseDto.getPurchasePrice().doubleValue()).doubleValue()
					);
		
		purchase.setTotalPrice(totalPrice);
		
		// 更新采药单
		int count = purchaseMapper.updateByPurchaseNoSelective(purchase);
		
		return count;
	}

	/**
	 * 采购条件统计，根据名称、编号等查询某一种药品的采购情况
	 */
	@Override
	public List<TbPurchaseItem> purchaseByCondition(String drugName, String drugNo, String beginTime, String endTime) {
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
		
		List<TbPurchaseItem> purchaseItemList = purchaseItemService.selectAllPurchase(map);
		
		if (purchaseItemList != null) {
			return purchaseItemList;
		}
		return null;
	}

}
