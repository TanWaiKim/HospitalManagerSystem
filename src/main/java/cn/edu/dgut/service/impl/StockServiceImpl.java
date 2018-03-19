package cn.edu.dgut.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.mapper.TbStockMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbDrugtype;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.pojo.TbWarehouse;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.DrugtypeService;
import cn.edu.dgut.service.PurchaseItemService;
import cn.edu.dgut.service.StockService;
import cn.edu.dgut.service.WarehouseService;

/**
 * @author TanWaiKim
 * @time 2018年2月4日 下午4:26:48
 * @version 1.0
 */
@Service
public class StockServiceImpl implements StockService {

	@Autowired
	private TbStockMapper stockMapper;
	@Autowired
	private DrugtypeService drugtypeService;
	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private DrugService drugService;
	@Autowired
	private PurchaseItemService purchaseItemService;
	
	/**
	 * 分页查询所有的库存单信息，刚进来的页面信息
	 */
	@Override
	public List<TbStock> getAllStock(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		//防止刷新该字段不为空
		map.put("warehouseNo", null);
		map.put("drugtypeId", null);
		
		// 根据条件查询总数
		int totalNum = stockMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbStock> stockList = stockMapper.pageByCondition(map);
		
		for (TbStock tbStock : stockList) {
			TbDrug drug = drugService.getDrugById(tbStock.getDrugId());
			TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug.getDrugtypeId());
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(tbStock.getWarehouseNo());
			
			drug.setDrugtype(drugtype);
			tbStock.setDrug(drug);
			tbStock.setWarehouse(warehouse);
		}
		
		return stockList;
	}

	/**
	 * 分页条件查询
	 */
	@Override
	public List<TbStock> pageByCondition(String warehouseNo, String operator, String drugName, String drugNo, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("warehouseNo", warehouseNo);
		map.put("operator", operator);
		
		// 根据药名获得医药
		TbDrug drug = drugService.getDrugByName(drugName);
		
		if (drug != null) {
			map.put("drugId", drug.getId());
		} else {
			map.put("drugId", null);
		}
		
		if (!drugNo.equals("")) {
			// 根据药品编号获得药品的id
			TbDrug drug1 = drugService.getDrugByNo(drugNo);
			if (drug1 != null) {
				map.put("drugId", drug1.getId());
			} else {
				map.put("drugId", null);
			}
		}
		
		// 根据条件查询总数
		int totalNum = stockMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		
		
		List<TbStock> stockList = stockMapper.pageByCondition(map);
		
		for (TbStock tbStock : stockList) {
			TbDrug drug2 = drugService.getDrugById(tbStock.getDrugId());
			TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug2.getDrugtypeId());
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(tbStock.getWarehouseNo());
			
			drug2.setDrugtype(drugtype);
			tbStock.setDrug(drug2);
			tbStock.setWarehouse(warehouse);
		}
		
		return stockList;
	}

	/**
	 * 根据id查询
	 */
	@Override
	public TbStock getStockById(Integer id) {
		TbStock stock = stockMapper.selectByPrimaryKey(id);
		if (stock != null) {
			TbDrug drug = drugService.getDrugById(stock.getDrugId());
			stock.setDrug(drug);
			return stock;
		}
		return null;
	}
	
	/**
	 * 根据医药id查询
	 */
	@Override
	public TbStock getStockByDrug(Map<String, Object> map) {
		TbStock stock = stockMapper.selectByDrugIdAndBatchNo(map);
		if (stock != null) {
			return stock;
		}
		return null;
	}
	
	/**
	 * 获取tb_stock中最后一条记录
	 * @return
	 */
	public TbStock getLastRecord(){
		TbStock stock = stockMapper.selectLastRecord();
		if(stock!=null){
			return stock;
		}
		return null;
	}

	/**
	 * 添加库存
	 */
	@Override
	public int addStockByTbStock(TbStock stock) {
		TbStock lastStock = this.getLastRecord();
		if(lastStock!=null){
			stock.setId(lastStock.getId()+1);
		}else{
			stock.setId(1);
		}
		int count = stockMapper.insert(stock);
		return count;
	}

	@Override
	public int deleteStockById(Integer id) {
		
		return 0;
	}

	@Override
	public int deleteStockByIds(String[] ids) {
		
		return 0;
	}

	/**
	 * 获取所有库存
	 * @return
	 */
	@Override
	public List<TbStock> selectAllStock() {
		List<TbStock> stockList = stockMapper.selectAllStock();
		
		if (stockList != null) {
			return stockList;
		}
		return null;
	}

	/**
	 * 根据药品id和批次更新库存
	 */
	@Override
	public int updateStockBySelective(TbStock stock) {
		int count = stockMapper.updateByDrugSelective(stock);
		return count;
	}
	
	/**
	 * 根据药品id更新库存
	 */
	@Override
	public int updateStockByDrugId(TbStock stock) {
		int count = stockMapper.updateByDrugId(stock);
		return count;
	}

	/**
	 * 库存上下限预警，首页
	 * @param page
	 * @return
	 */
	@Override
	public List<TbStock> getAllStockByQuantityWaring(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 根据条件查询总数
		int totalNum = stockMapper.countByQuantityWaring(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbStock> stockList = stockMapper.pageByQuantityWaring(map);
		
		// 库存上下限预警
		for (TbStock tbStock : stockList) {
			if (tbStock.getStockQuantity() < tbStock.getMinQuantity()) {
				tbStock.setWaring("当前库存量低于库存下限，请及时采药入库！");
			} else if (tbStock.getStockQuantity() > tbStock.getMaxQuantity()) {
				tbStock.setWaring("当前库存量高于库存上线，请减少库存数量！");
			} 
			stockMapper.updateWaring(tbStock);
		}
		
		for (TbStock tbStock : stockList) {
			TbDrug drug = drugService.getDrugById(tbStock.getDrugId());
			TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug.getDrugtypeId());
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(tbStock.getWarehouseNo());
			
			drug.setDrugtype(drugtype);
			tbStock.setDrug(drug);
			tbStock.setWarehouse(warehouse);
		}
		
		return stockList;
	}

	/**
	 * 库存上下限预警，分页显示
	 * @param page
	 * @return
	 */
	@Override
	public List<TbStock> pageByQuantityWaring(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		// 根据条件查询总数
		int totalNum = stockMapper.countByQuantityWaring(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		
		
		List<TbStock> stockList = stockMapper.pageByQuantityWaring(map);
		
		// 库存上下限预警
		for (TbStock tbStock : stockList) {
			if (tbStock.getStockQuantity() < tbStock.getMinQuantity()) {
				tbStock.setWaring("当前库存量低于库存下限，请及时采药入库！");
			} else if (tbStock.getStockQuantity() > tbStock.getMaxQuantity()) {
				tbStock.setWaring("当前库存量高于库存上线，请减少库存数量！");
			} 
			stockMapper.updateWaring(tbStock);
		}
		
		
		for (TbStock tbStock : stockList) {
			TbDrug drug2 = drugService.getDrugById(tbStock.getDrugId());
			TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug2.getDrugtypeId());
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(tbStock.getWarehouseNo());
			
			drug2.setDrugtype(drugtype);
			tbStock.setDrug(drug2);
			tbStock.setWarehouse(warehouse);
		}
		
		return stockList;
	}

	/**
	 * 有效期预警，首次进入
	 * @param page
	 * @return
	 */
	@Override
	public List<TbStock> getAllStockByValidWaring(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 根据条件查询总数
		int totalNum = stockMapper.countByValidWaring(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbStock> stockList = stockMapper.pageByValidWaring(map);
		
		// 有效期预警
		for (TbStock tbStock : stockList) {
			String remark = null;
			if (stockMapper.countValidTime(tbStock) < 0) {
				System.out.println("我是答案1："+stockMapper.countValidTime(tbStock));
				remark = "该药品已经过期"+(-stockMapper.countValidTime(tbStock))+"天，请进行退货或销毁处理！";
			} else {
				System.out.println("我是答案2："+stockMapper.countValidTime(tbStock));
				remark = "该药品还有"+stockMapper.countValidTime(tbStock)+"天将失效，请尽快销售！";
			}
			
			tbStock.setValidWaring(remark);
			stockMapper.updateValidWaring(tbStock);
		}
		
		for (TbStock tbStock : stockList) {
			TbDrug drug = drugService.getDrugById(tbStock.getDrugId());
			TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug.getDrugtypeId());
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(tbStock.getWarehouseNo());
			
			TbPurchaseItem purchaseItem = new TbPurchaseItem();
			purchaseItem.setBatchNo(tbStock.getBatchNo());
			purchaseItem.setDrugId(tbStock.getDrugId());
			purchaseItem = purchaseItemService.selectByDrugIdAndBatchNo(purchaseItem);
			
			
			drug.setDrugtype(drugtype);
			tbStock.setDrug(drug);
			tbStock.setWarehouse(warehouse);
			tbStock.setPurchaseItem(purchaseItem);
		}
		
		return stockList;
	}
	
	/**
	 * 有效期预警，分页显示
	 * @param page
	 * @return
	 */
	@Override
	public List<TbStock> pageByValidWaring(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		// 根据条件查询总数
		int totalNum = stockMapper.countByValidWaring(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		
		
		List<TbStock> stockList = stockMapper.pageByValidWaring(map);
		
		// 有效期预警
		for (TbStock tbStock : stockList) {
			String remark = null;
			if (stockMapper.countValidTime(tbStock) < 0) {
				System.out.println("我是答案3："+stockMapper.countValidTime(tbStock));
				remark = "该药品已经过期"+(-stockMapper.countValidTime(tbStock))+"天，请进行退货或销毁处理！";
			} else {
				System.out.println("我是答案4："+stockMapper.countValidTime(tbStock));
				remark = "该药品还有"+stockMapper.countValidTime(tbStock)+"天将失效，请尽快销售！";
			}
			tbStock.setValidWaring(remark);
			stockMapper.updateValidWaring(tbStock);
		}
		
		
		for (TbStock tbStock : stockList) {
			TbDrug drug2 = drugService.getDrugById(tbStock.getDrugId());
			TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug2.getDrugtypeId());
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(tbStock.getWarehouseNo());
			TbPurchaseItem purchaseItem = new TbPurchaseItem();
			purchaseItem.setBatchNo(tbStock.getBatchNo());
			purchaseItem.setDrugId(tbStock.getDrugId());
			purchaseItem = purchaseItemService.selectByDrugIdAndBatchNo(purchaseItem);
			
			drug2.setDrugtype(drugtype);
			tbStock.setDrug(drug2);
			tbStock.setWarehouse(warehouse);
			tbStock.setPurchaseItem(purchaseItem);
		}
		
		return stockList;
	}

	
	@Override
	public List<TbStock> getStockByDrugId(Integer drugId) {
		List<TbStock> stocks = stockMapper.getStockByDrugId(drugId);
		
		if (stocks != null) {
			for (TbStock tbStock : stocks) {
				TbDrug drug = drugService.getDrugById(tbStock.getDrugId());
				TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug.getDrugtypeId());
				TbWarehouse warehouse = warehouseService.getWarehouseByNo(tbStock.getWarehouseNo());
				
				drug.setDrugtype(drugtype);
				tbStock.setDrug(drug);
				tbStock.setWarehouse(warehouse);
			}
			
			return stocks;
		}
		return null;
	}

	
	@Override
	public List<TbStock> getAllListStock(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据条件查询总数
		int totalNum = stockMapper.countByListCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbStock> stockList = stockMapper.pageByListCondition(map);
		
		for (TbStock tbStock : stockList) {
			TbDrug drug = drugService.getDrugById(tbStock.getDrugId());
			TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug.getDrugtypeId());
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(tbStock.getWarehouseNo());
			
			drug.setDrugtype(drugtype);
			tbStock.setDrug(drug);
			tbStock.setWarehouse(warehouse);
		}
		
		return stockList;
	}

	
	@Override
	public List<TbStock> pageByListCondition(String drugName, String drugNo, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 根据药名获得医药
		TbDrug drug = drugService.getDrugByName(drugName);
		
		if (drug != null) {
			map.put("drugId", drug.getId());
		} else {
			map.put("drugId", null);
		}
		
		if (!drugNo.equals("")) {
			// 根据药品编号获得药品的id
			TbDrug drug1 = drugService.getDrugByNo(drugNo);
			if (drug1 != null) {
				map.put("drugId", drug1.getId());
			} else {
				map.put("drugId", null);
			}
		}
		
		// 根据条件查询总数
		int totalNum = stockMapper.countByListCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		
		
		List<TbStock> stockList = stockMapper.pageByListCondition(map);
		
		for (TbStock tbStock : stockList) {
			TbDrug drug2 = drugService.getDrugById(tbStock.getDrugId());
			TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug2.getDrugtypeId());
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(tbStock.getWarehouseNo());
			
			drug2.setDrugtype(drugtype);
			tbStock.setDrug(drug2);
			tbStock.setWarehouse(warehouse);
		}
		
		return stockList;
	}

	
	@Override
	public int countStockQuantityByDrugId(Integer drugId) {
		return stockMapper.countStockQuantityByDrugId(drugId);
	}

	
	@Override
	public int updateByStockSelective(TbStock stock) {
		return stockMapper.updateByStockSelective(stock);
	}

}
