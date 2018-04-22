package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.util.BigDecimalUtil;
import cn.edu.dgut.mapper.TbBackMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbBack;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbSalesItem;
import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.service.BackService;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.PurchaseItemService;
import cn.edu.dgut.service.SalesItemService;
import cn.edu.dgut.service.StockService;

/**
 * @author TanWaiKim
 * @time 2018年4月11日 下午7:41:19
 * @version 1.0
 */
@Service
public class BackServiceImpl implements BackService {
	@Autowired
	private TbBackMapper backMapper;
	@Autowired
	private StockService stockService;
	@Autowired
	private SalesItemService salesItemService;
	@Autowired
	private PurchaseItemService purchaseItemService;
	@Autowired
	private DrugService drugService;
	
	
	/**
	 * 分页查询所有的退药单信息，刚进来的页面信息
	 */
	@Override
	public List<TbBack> getAllBack(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		//防止刷新该字段不为空
		map.put("backObject", null);
		map.put("backType", null);
		
		// 根据条件查询总数
		int totalNum = backMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbBack> backList = backMapper.pageByCondition(map);
		
		for (TbBack tbBack : backList) {
			TbDrug drug = drugService.getDrugById(tbBack.getDrugId());
			tbBack.setDrug(drug);
		}
		
		return backList;
	}

	/**
	 * 分页条件查询
	 */
	@Override
	public List<TbBack> pageByCondition(String backType, String backObject, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("backType", backType);
		map.put("backObject", backObject);
		// 根据条件查询总数
		int totalNum = backMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbBack> backList = backMapper.pageByCondition(map);
		
		for (TbBack tbBack : backList) {
			TbDrug drug = drugService.getDrugById(tbBack.getDrugId());
			tbBack.setDrug(drug);
		}
		
		return backList;
	}

	@Override
	public TbPurchase getBackById(Integer id) {
	
		return null;
	}

	
	
	/**
	 * 添加新的退药记录
	 */
	@Override
	public int addBackByTbBack(TbBack back) {
		TbStock stock = new TbStock();
		stock.setDrugId(back.getDrugId());
		stock.setBatchNo(back.getBatchNo());
		int count = 0;
		
		// 对于病人退药，先增加相应库存数量，再增加退药记录
		if (back.getBackType().equals("病人退药")) {
			stock.setStockQuantity(-back.getBackSum());
			stockService.updateByStockSelective(stock);
			
			// 获得销售单价
			TbSalesItem salesItem = new TbSalesItem();
			salesItem.setDrugId(back.getDrugId());
			salesItem.setBatchNo(back.getBatchNo());
			salesItem = salesItemService.selectByDrugIdAndBatchNo(salesItem);
			
			// 设置退货总价
			back.setBackTotalPrice(BigDecimalUtil.mul(back.getBackSum().doubleValue(), 
					salesItem.getSalePrice().doubleValue()));
			
			count = backMapper.insert(back);
			
		}
		
		
		// 对于药商退药，先减少相应库存数量，再增加退药记录
		if (back.getBackType().equals("药商退药")) {
			Map<String, Object> map = new HashMap<>();
			map.put("drugId", back.getDrugId());
			map.put("batchNo", back.getBatchNo());
			stock = stockService.getStockByDrug(map);
			
			// 先判断退货的数量是否合理
			if (stock.getStockQuantity() < back.getBackSum()) {
				return 0;
			}
			
			stock.setStockQuantity(back.getBackSum());
			stockService.updateByStockSelective(stock);
			
			// 获得采购单价
			TbPurchaseItem purchaseItem = new TbPurchaseItem();
			purchaseItem.setDrugId(back.getDrugId());
			purchaseItem.setBatchNo(back.getBatchNo());
			purchaseItem = purchaseItemService.selectByDrugIdAndBatchNo(purchaseItem);
			
			// 设置退货总价
			back.setBackTotalPrice(BigDecimalUtil.mul(back.getBackSum().doubleValue(), 
					purchaseItem.getPurchasePrice().doubleValue()));
			
			count = backMapper.insert(back);
			
		}
		
		return count;
	}

	/**
	 * 删除单条记录
	 */
	@Override
	public int deleteBackById(Integer id) {
		return backMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public int deleteBackByIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			list.add(Long.valueOf(id).longValue());
		}
		return backMapper.deleteBatch(list);
	}

	@Override
	public List<TbBack> selectAllBack() {
		
		return null;
	}

	@Override
	public int updateBackByBackId(TbBack back) {
		
		return 0;
	}

	@Override
	public int updateBack(TbBack back) {
		
		return 0;
	}

}
