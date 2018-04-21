package cn.edu.dgut.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.dto.SalesDto;
import cn.edu.dgut.common.util.BigDecimalUtil;
import cn.edu.dgut.common.util.IDUtils;
import cn.edu.dgut.mapper.TbSalesMapper;
import cn.edu.dgut.pojo.DrugData;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbSales;
import cn.edu.dgut.pojo.TbSalesItem;
import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.PatientService;
import cn.edu.dgut.service.PurchaseItemService;
import cn.edu.dgut.service.SalesItemService;
import cn.edu.dgut.service.SalesService;
import cn.edu.dgut.service.StockService;

/**
 * @author TanWaiKim
 * @time 2018年2月24日 下午10:28:44
 * @version 1.0
 */
@Service
public class SalesServiceImpl implements SalesService {
	@Autowired
	private TbSalesMapper salesMapper;
	@Autowired
	private SalesItemService salesItemService;
	@Autowired
	private DrugService drugService;
	@Autowired
	private StockService stockService;
	@Autowired
	private PurchaseItemService purchaseItemService;
	@Autowired
	private PatientService patientService;
	
	
	@Override
	public List<TbSales> getAllSale(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		//防止刷新该字段不为空
		map.put("patientId", null);
		
		// 根据条件查询总数
		int totalNum = salesMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbSales> salesList = salesMapper.pageByCondition(map);
		
		for (TbSales tbSales : salesList) {
			TPatient patient = patientService.getPatientById(tbSales.getPatientId());
			tbSales.setPatient(patient);
		}
		
		return salesList;
	}

	@Override
	public List<TbSales> pageByCondition(String salesNo, String patientId, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("salesNo", salesNo);
		map.put("patientId", patientId);
		
		// 根据条件查询总数
		int totalNum = salesMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbSales> salesList = salesMapper.pageByCondition(map);
		
		for (TbSales tbSales : salesList) {
			TPatient patient = patientService.getPatientById(tbSales.getPatientId());
			tbSales.setPatient(patient);
		}
		
		return salesList;
	}

	@Override
	public TbSales getSalesById(Integer id) {
		TbSales sales = salesMapper.selectByPrimaryKey(id);
		if (sales != null) {
			return sales;
		}
		return null;
	}

	@Override
	public TbSales getSalesBySalesNo(String salesNo) {
		TbSales sales = salesMapper.selectBySalesNo(salesNo);
		if (sales != null) {
			TPatient patient = patientService.getPatientById(sales.getPatientId());
			sales.setPatient(patient);
			return sales;
		}
		
		return null;
	}

	/**
	 * 获取tb_sales中最后一条记录
	 * @return
	 */
	public TbSales getLastRecord(){
		TbSales sales = salesMapper.selectLastRecord();
		if(sales!=null){
			return sales;
		}
		return null;
	}
	

	/**
	 * 计算销药单详细总价
	 * @param quantity
	 * @param salePrice
	 * @return
	 */
    private BigDecimal getOrderItemTotalPrice(Integer quantity, BigDecimal salePrice){
        BigDecimal payment = new BigDecimal("0");
        payment = BigDecimalUtil.mul(quantity.doubleValue(), salePrice.doubleValue());
        return payment;
    }
    
	/**
	 * 计算一条销药单的总价
	 * @param salesItemList
	 * @return
	 */
    private BigDecimal getOrderTotalPrice(List<TbSalesItem> salesItemList){
        BigDecimal payment = new BigDecimal("0");
        for (TbSalesItem salesItem:salesItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(),salesItem.getSaleTotalPrice().doubleValue());
        }
        return payment;
    }
    
	/**
	 * 计算一条销药单项目的总数量
	 * @param salesItemList
	 * @return
	 */
    private BigDecimal getOrderTotalCount(List<TbSalesItem> salesItemList){
        BigDecimal count = new BigDecimal("0");
        for (TbSalesItem salesItem:salesItemList){
        	count = BigDecimalUtil.add(count.doubleValue(),salesItem.getQuantity().doubleValue());
        }
        return count;
    }
	
	/**
	 * 根据药品id获取需要的信息
	 * @param salesDto
	 * @param drugId
	 * @return
	 */
	public Map<String, TbStock> getSalesStock(SalesDto salesDto,Integer drugId) {
		List<TbStock> stockList = stockService.getStockByDrugId(drugId);
		Map<String, TbStock> map = new LinkedHashMap<String, TbStock>();
		
		Integer countQuantity = 0;
		
		for(TbStock stock : stockList) {
			if (stock.getStockQuantity() <= 0) {
				continue;
			}
			
			TbStock stock2 = new TbStock();
			countQuantity += stock.getStockQuantity();
			if (countQuantity <=  salesDto.getQuantity()) {
				stock2.setDrugId(stock.getDrugId());
				stock2.setBatchNo(stock.getBatchNo());
				stock2.setStockQuantity(stock.getStockQuantity());
				map.put(stock.getBatchNo(), stock2);
			} else {
				stock2.setDrugId(stock.getDrugId());
				stock2.setBatchNo(stock.getBatchNo());
				stock2.setStockQuantity(salesDto.getQuantity()-countQuantity+stock.getStockQuantity());
				map.put(stock.getBatchNo(), stock2);
				break;
			}
		}
		return map;
	}
	
	/**
	 * 添加新的销药单、销药详细单
	 */
	@Override
	public int addSalesByTbSales(SalesDto salesDto) {
		TbSales lastSales = this.getLastRecord();
		TbSales sales = new TbSales();
		int count = 0;
		
		if(lastSales!=null){
			sales.setId(lastSales.getId()+1);
		}else{
			sales.setId(1);
		}
	
		String salesNo = "XSYY"+IDUtils.getId() + "";
		sales.setSalesNo(salesNo);
		sales.setOperator(salesDto.getOperator());
		sales.setPatientId(salesDto.getPatientId());
		
		for (int i = 0; i < salesDto.getDrugDataList().size(); i++) {
			DrugData drugData2 = salesDto.getDrugDataList().get(i);
			String drugName = drugData2.getDrugName();
			String dN = drugData2.getDrugNum();
			Integer drugNum = Integer.parseInt(dN.substring(0, dN.length()-1));
			
			salesDto.setDrugName(drugName);
			salesDto.setQuantity(drugNum);
			
			TbDrug drug = drugService.getDrugByName(salesDto.getDrugName());
			
			if (drug == null) {
				return 0;
			}
			
			count = stockService.countStockQuantityByDrugId(drug.getId());
			// 判断库存数量是否足够
			if (salesDto.getQuantity() > count) {
				return 0;
			}
			
			Map<String, TbStock> map = this.getSalesStock(salesDto,drug.getId());
			
			TbSalesItem salesItem = new TbSalesItem();
			salesItem.setSalesNo(sales.getSalesNo());
			salesItem.setDrugId(drug.getId());
			salesItem.setDrugName(drug.getDrugName());
			
			// 设置该批次的信息
			for (Map.Entry<String, TbStock> entry : map.entrySet()) {
				TbPurchaseItem purchaseItem = new TbPurchaseItem();
				purchaseItem.setBatchNo(entry.getKey());
				purchaseItem.setDrugId(drug.getId());
				purchaseItem = purchaseItemService.selectByDrugIdAndBatchNo(purchaseItem);
				salesItem.setBatchNo(entry.getKey());
				salesItem.setQuantity(entry.getValue().getStockQuantity());
				salesItem.setSalePrice(purchaseItem.getSalePrice());
				salesItem.setSaleTotalPrice(this.getOrderItemTotalPrice(salesItem.getQuantity(),salesItem.getSalePrice()));
				
				TbStock stock = new TbStock();
				stock.setBatchNo(entry.getKey());
				stock.setDrugId(drug.getId());
				
				if (salesItemService.addSalesItemByTbSalesItem(salesItem) > 0) {
					stock.setStockQuantity(salesItem.getQuantity());
					stockService.updateByStockSelective(stock);
					}
			}
		}
		
		
		List<TbSalesItem> salesItems = salesItemService.selectAllSalesItem(sales.getSalesNo());
		
		sales.setTotalQuantity(this.getOrderTotalCount(salesItems).intValue());
		sales.setTotalPrice(this.getOrderTotalPrice(salesItems));
		
		count = salesMapper.insert(sales);
		
		return count;
	}

	@Override
	public int deleteSalesById(Integer id) {
		return salesMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteSalesByIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			list.add(Long.valueOf(id).longValue());
			// 先删除销药详细单
			TbSales sales = salesMapper.selectByPrimaryKey(Integer.valueOf(id).intValue());
			String salesNo = sales.getSalesNo();
			salesItemService.deleteSalesItemBySalesItem(salesNo);
		}
		return salesMapper.deleteBatch(list);
	}

	@Override
	public List<TbSales> selectAllSales() {
		List<TbSales> salesList = salesMapper.selectAllSales();
		if (salesList != null ) {
			return salesList;
		}
		return null;
	}
	
	// 这里有问题，回头再看看。
	@Override
	public int updateSalesBySalesNo(SalesDto salesDto) {
		
		TbSales sales = new TbSales();
		sales.setSalesNo(salesDto.getSalesNo());
		
		// 根据药品名称获得药品信息
		TbDrug drug = drugService.getDrugByName(salesDto.getDrugName());
		
		if (drug == null) {
			return 0;
		}
		
		int count = stockService.countStockQuantityByDrugId(drug.getId());
		// 判断库存数量是否足够
		if (salesDto.getQuantity() > count) {
			return 0;
		}
		
		// 封装需求数据
		Map<String, TbStock> map = this.getSalesStock(salesDto,drug.getId());
		
		TbSalesItem salesItem = new TbSalesItem();
		salesItem.setSalesNo(sales.getSalesNo());
		salesItem.setDrugId(drug.getId());
		salesItem.setDrugName(drug.getDrugName());
		
		// 设置该批次的信息
		for (Map.Entry<String, TbStock> entry : map.entrySet()) {
			TbPurchaseItem purchaseItem = new TbPurchaseItem();
			purchaseItem.setBatchNo(entry.getKey());
			purchaseItem.setDrugId(drug.getId());
			purchaseItem = purchaseItemService.selectByDrugIdAndBatchNo(purchaseItem);
			salesItem.setBatchNo(entry.getKey());
			salesItem.setQuantity(entry.getValue().getStockQuantity());
			salesItem.setSalePrice(purchaseItem.getSalePrice());
			salesItem.setSaleTotalPrice(this.getOrderItemTotalPrice(salesItem.getQuantity(),salesItem.getSalePrice()));
			
			TbStock stock = new TbStock();
			stock.setBatchNo(entry.getKey());
			stock.setDrugId(drug.getId());
			
			if (salesItemService.addSalesItemByTbSalesItem(salesItem) > 0) {
				stock.setStockQuantity(salesItem.getQuantity());
				stockService.updateByStockSelective(stock);
				}
		}
		
		List<TbSalesItem> salesItems = salesItemService.selectAllSalesItem(sales.getSalesNo());
		
		sales.setTotalQuantity(this.getOrderTotalCount(salesItems).intValue());
		sales.setTotalPrice(this.getOrderTotalPrice(salesItems));
		
		// 更新销药单
		count = salesMapper.updateBySalesNoSelective(sales);
		
		return count;
	}

	@Override
	public int updateSalesBySalesItemId(SalesDto salesDto) {
		TbSales sales = new TbSales();
		sales.setSalesNo(salesDto.getSalesNo());
		 
		TbDrug drug = drugService.getDrugById(salesDto.getDrugId());
		
		if (drug == null) {
			return 0;
		}
		
		// 修改的时候，先恢复之前的库存数量，否则会出错
		TbStock stock1 = new TbStock();
		stock1.setBatchNo(salesDto.getBatchNo());
		stock1.setDrugId(drug.getId());
		stock1.setStockQuantity(-salesDto.getOldSalesItemQuantity());
		stockService.updateByStockSelective(stock1);
		
		int count = stockService.countStockQuantityByDrugId(drug.getId());
		// 判断库存数量是否足够
		if (salesDto.getQuantity() > count) {
			return 0;
		}
		
		Map<String, TbStock> map = this.getSalesStock(salesDto,drug.getId());
		TbSalesItem salesItem = new TbSalesItem();
		salesItem.setId(salesDto.getId());
		salesItem.setSalesNo(sales.getSalesNo());
		salesItem.setDrugId(drug.getId());
		salesItem.setDrugName(drug.getDrugName());
		
		// 设置该批次的信息
		for (Map.Entry<String, TbStock> entry : map.entrySet()) {
			TbPurchaseItem purchaseItem = new TbPurchaseItem();
			purchaseItem.setBatchNo(entry.getKey());
			purchaseItem.setDrugId(drug.getId());
			purchaseItem = purchaseItemService.selectByDrugIdAndBatchNo(purchaseItem);
			salesItem.setBatchNo(entry.getKey());
			salesItem.setQuantity(entry.getValue().getStockQuantity());
			salesItem.setSalePrice(purchaseItem.getSalePrice());
			salesItem.setSaleTotalPrice(this.getOrderItemTotalPrice(salesItem.getQuantity(),salesItem.getSalePrice()));
			
			TbStock stock = new TbStock();
			stock.setBatchNo(entry.getKey());
			stock.setDrugId(drug.getId());
			
			if (salesItemService.getSalesItemById(salesDto.getId()) != null) {
				if (salesItemService.updateSalesItemByTbSalesItem(salesItem) > 0) {
					stock.setStockQuantity(salesItem.getQuantity());
					stockService.updateByStockSelective(stock);
					}
			} else {
				if (salesItemService.addSalesItemByTbSalesItem(salesItem) > 0) {
					stock.setStockQuantity(salesItem.getQuantity());
					stockService.updateByStockSelective(stock);
					}
			}
		}
		
		List<TbSalesItem> salesItems = salesItemService.selectAllSalesItem(sales.getSalesNo());
		
		sales.setTotalQuantity(this.getOrderTotalCount(salesItems).intValue());
		sales.setTotalPrice(this.getOrderTotalPrice(salesItems));
		// 更新销药单
		count = salesMapper.updateBySalesNoSelective(sales);
		
		return count;
	}

	
	@Override
	public int updateSales(TbSales sales) {
		return salesMapper.updateBySalesNoSelective(sales);
	}

}
