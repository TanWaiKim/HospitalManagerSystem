package cn.edu.dgut.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.dto.ValidWarningDto;
import cn.edu.dgut.common.util.BigDecimalUtil;
import cn.edu.dgut.mapper.TbStockMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbDrugAdmin;
import cn.edu.dgut.pojo.TbDrugtype;
import cn.edu.dgut.pojo.TbProvider;
import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.pojo.TbWarehouse;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.DrugtypeService;
import cn.edu.dgut.service.PurchaseItemService;
import cn.edu.dgut.service.PurchaseService;
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
	private DrugService drugService;
	@Autowired
	private DrugtypeService drugtypeService;
	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private PurchaseService purchaseService;
	
	@Override
	public List<TbStock> getAllStock(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("warehouseNo", null);
		// 根据条件查询总数
		int totalNum = stockMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbStock> stocks = stockMapper.pageByCondition(map);
		
		for(TbStock stock : stocks) {
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(stock.getWarehouseNo());
			TbDrug drug = drugService.getDrugById(stock.getDrugId());
			TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug.getDrugtypeId());
			drug.setDrugtype(drugtype);
			stock.setWarehouse(warehouse);
			stock.setDrug(drug);
		}

		return stocks;
	}

	@Override
	public List<TbStock> pageByCondition(String warehouseNo,  String drugname, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("warehouseNo", warehouseNo);
		map.put("drugname", drugname);
		
		// 根据条件查询总数
		int totalNum = stockMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbStock> stocks = stockMapper.pageByCondition(map);
		
		for(TbStock stock : stocks) {
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(stock.getWarehouseNo());
			TbDrug drug = drugService.getDrugById(stock.getDrugId());
			TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug.getDrugtypeId());
			drug.setDrugtype(drugtype);
			stock.setWarehouse(warehouse);
			stock.setDrug(drug);
			System.out.println(stock.getCreateTime().toString());
		}

		return stocks;
	}

	@Override
	public TbStock getStockById(Integer id) {
		// TODO Auto-generated method stub
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
	public int deleteStockByDrugId(Integer id) {
		return stockMapper.deleteByDrugId(id);
	}

	@Override
	public int deleteStockByDrugIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			list.add(Long.valueOf(id).longValue());
		}
		return stockMapper.deleteBatch(list);
	}

	@Override
	public List<TbStock> selectAllStock() {
		List<TbStock> stockList = stockMapper.selectAllStock();
		
		if (stockList != null) {
			return stockList;
		}
		return null;
	}


	// 根据医药信息查询库存
	@Override
	public List<TbStock> getStockByDrug(Map<String, Object> map) {
		List<TbStock> stocks = stockMapper.getStockByDrug(map);
		
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
	public int updateStockBySelective(TbStock stock) {
		int count = stockMapper.updateByDrugSelective(stock);
		return count;
	}

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
		
		if (stockList == null) {
			return null;
		}
		
		// 库存上下限预警
		for (TbStock tbStock : stockList) {
			if (tbStock.getStockQuantity() < tbStock.getMinQuantity()) {
				tbStock.setQuantityWaring("当前库存量低于库存下限，请及时采药入库！");
			} else if (tbStock.getStockQuantity() > tbStock.getMaxQuantity()) {
				tbStock.setQuantityWaring("当前库存量高于库存上线，请减少库存数量！");
			} 
		}
		
		// 填充数据
		for (TbStock tbStock : stockList) {
			TbStock stock = stockMapper.selectByPrimaryKey(tbStock.getId());
			TbDrug drug = drugService.getDrugById(stock.getDrugId());
			TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug.getDrugtypeId());
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(tbStock.getWarehouseNo());
			
			drug.setDrugtype(drugtype);
			tbStock.setDrug(drug);
			tbStock.setWarehouse(warehouse);
		}
		
		return stockList;
	}

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
		
		if (stockList == null) {
			return null;
		}
		
		// 库存上下限预警
		for (TbStock tbStock : stockList) {
			if (tbStock.getStockQuantity() < tbStock.getMinQuantity()) {
				tbStock.setQuantityWaring("当前库存量低于库存下限，请及时采药入库！");
			} else if (tbStock.getStockQuantity() > tbStock.getMaxQuantity()) {
				tbStock.setQuantityWaring("当前库存量高于库存上线，请减少库存数量！");
			} 
		}
		
		// 填充数据
		for (TbStock tbStock : stockList) {
			TbStock stock = stockMapper.selectByPrimaryKey(tbStock.getId());
			TbDrug drug = drugService.getDrugById(stock.getDrugId());
			TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug.getDrugtypeId());
			TbWarehouse warehouse = warehouseService.getWarehouseByNo(tbStock.getWarehouseNo());
			
			drug.setDrugtype(drugtype);
			tbStock.setDrug(drug);
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
	public List<ValidWarningDto> getAllStockByValidWaring(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 根据条件查询总数
		int totalNum = stockMapper.countByValidWaring(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		// 查到符合要求的库存数量
		List<TbStock> stockList = stockMapper.pageByValidWaring(map);
		
		if (stockList == null) {
			return null;
		}
		
		// 用来存储显示的信息
		List<ValidWarningDto> validWarningDtos = new ArrayList<ValidWarningDto>();
		
		// 填充数据
		for (TbStock tbStock : stockList) {
			ValidWarningDto validWarningDto = new ValidWarningDto();
			TbDrug drug = new TbDrug();
			TbDrugtype drugtype = new TbDrugtype();
			TbWarehouse warehouse = new TbWarehouse();
			
			String remark = null;
			if (stockMapper.countValidTime(tbStock) < 0) {
				remark = "该药品已经过期"+(-stockMapper.countValidTime(tbStock))+"天，请进行销毁处理！";
			} else {
				remark = "该药品还有"+stockMapper.countValidTime(tbStock)+"天将失效！";
			}
			
			drug = drugService.getDrugById(tbStock.getDrugId());
			drugtype = drugtypeService.getDrugtypeById(drug.getDrugtypeId());
			warehouse = warehouseService.getWarehouseByNo(tbStock.getWarehouseNo());
		
			validWarningDto.setDrugId(tbStock.getDrugId());
			validWarningDto.setDrugName(drug.getDrugName());
			validWarningDto.setDrugNo(drug.getDrugNo());
			validWarningDto.setDrugtypeName(drugtype.getDrugtypeName());
			validWarningDto.setProduceTime(drug.getProduceTime());
			validWarningDto.setValidTime(drug.getValidTime());
			validWarningDto.setWarehouseName(warehouse.getWarehouseName());
			validWarningDto.setWaringMsg(remark);
			// 将数据添加到数组中
			validWarningDtos.add(validWarningDto);
		}
		
		return validWarningDtos;
	}

	/**
	 * 有效期预警，分页显示
	 * @param page
	 * @return
	 */
	@Override
	public List<ValidWarningDto> pageByValidWaring(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 根据条件查询总数
		int totalNum = stockMapper.countByValidWaring(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		// 查到符合要求的库存数量
		List<TbStock> stockList = stockMapper.pageByValidWaring(map);
		
		if (stockList == null) {
			return null;
		}
		
		// 用来存储显示的信息
		List<ValidWarningDto> validWarningDtos = new ArrayList<ValidWarningDto>();
		
		// 填充数据
		for (TbStock tbStock : stockList) {
			ValidWarningDto validWarningDto = new ValidWarningDto();
			TbDrug drug = new TbDrug();
			TbDrugtype drugtype = new TbDrugtype();
			TbWarehouse warehouse = new TbWarehouse();
			
			String remark = null;
			if (stockMapper.countValidTime(tbStock) < 0) {
				remark = "该药品已经过期"+(-stockMapper.countValidTime(tbStock))+"天，请进行销毁处理！";
			} else {
				remark = "该药品还有"+stockMapper.countValidTime(tbStock)+"天将失效！";
			}
			
			drug = drugService.getDrugById(tbStock.getDrugId());
			drugtype = drugtypeService.getDrugtypeById(drug.getDrugtypeId());
			warehouse = warehouseService.getWarehouseByNo(tbStock.getWarehouseNo());
		
			validWarningDto.setDrugId(tbStock.getDrugId());
			validWarningDto.setDrugName(drug.getDrugName());
			validWarningDto.setDrugNo(drug.getDrugNo());
			validWarningDto.setDrugtypeName(drugtype.getDrugtypeName());
			validWarningDto.setProduceTime(drug.getProduceTime());
			validWarningDto.setValidTime(drug.getValidTime());
			validWarningDto.setWarehouseName(warehouse.getWarehouseName());
			validWarningDto.setWaringMsg(remark);
			// 将数据添加到数组中
			validWarningDtos.add(validWarningDto);
		}
		
		return validWarningDtos;
	}

	@Override
	public List<TbStock> getStockByDrugId(Integer drugId) {
		// TODO Auto-generated method stub
		return null;
	}

	// 计算某一种药品的预售均价
	BigDecimal getAveSalePrice(TbStock stock) {
		BigDecimal salePrice =  new BigDecimal("0");
		List<TbDrug> drugs = drugService.getDrugByName(stock.getDrugname());
		for(TbDrug drug : drugs) {
			salePrice = BigDecimalUtil.add(salePrice.doubleValue(), drug.getSalePrice().doubleValue());
		}
		
		return BigDecimalUtil.div(salePrice.doubleValue(), drugs.size());
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
			
			drug.setSalePrice(this.getAveSalePrice(tbStock));
			drug.setDrugtype(drugtype);
			tbStock.setDrug(drug);
			tbStock.setWarehouse(warehouse);
		}
		
		return stockList;
	}

	@Override
	public List<TbStock> pageByListCondition(String drugName, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugname", drugName);
		
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

	/**
	 * 根据药品名称修改
	 */
	@Override
	public int updateStockByDrugName(TbStock stock) {
		return stockMapper.updateStockByDrugName(stock);
	}

	@Override
	public int updateByStockSelective(TbStock stock) {
		return stockMapper.updateByStockSelective(stock);
	}

	@Override
	public int countStockQuantityByDrugName(String drugname) {
		return stockMapper.countStockQuantityByDrugName(drugname);
	}

	
	@Override
	public List<TbStock> getStockByDrugName(String drugname) {
		List<TbStock> stocks = stockMapper.getStockByDrugName(drugname);
		
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

	// 根据药品ID计算药品的数量
	@Override
	public int countStockQuantityByDrugId(Integer drugId) {
		return stockMapper.countStockQuantityByDrugId(drugId);
	}

	/**
	 * 根据药品名称和批次查询数量
	 */
	@Override
	public int countStockQuantityByDrugNameAndDrugNo(String drugName, String drugNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugName", drugName);
		map.put("drugNo", drugNo);
		return stockMapper.countStockQuantityByDrugNameAndDrugNo(map);
	}

	

}
