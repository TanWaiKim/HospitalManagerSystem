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
import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbSales;
import cn.edu.dgut.pojo.TbSalesItem;
import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.PatientService;
import cn.edu.dgut.service.PrescriptionService;
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
	private StockService stockService;
	@Autowired
	private PrescriptionService prescriptionService;
	@Autowired
	private DrugService drugService;
	@Autowired
	private PatientService patientService;
	
	/**
	 * 第一次进入
	 */
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

	/**
	 * 条件查询
	 */
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

	/* (non-Javadoc)
	 * @see cn.edu.dgut.service.SalesService#getSalesById(java.lang.Integer)
	 */
	@Override
	public TbSales getSalesById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据销售单号查询
	 */
	@Override
	public List<TbSales> getSalesBySalesNo(String salesNo,Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("salesNo", salesNo);
		// 根据条件查询总数
		int totalNum = salesMapper.countBySalesNo(map);
		
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbSales> sales = salesMapper.selectBySalesNoCondition(map);
		
		if (sales == null || sales.size() == 0) {
			return null;
		}
		
		for(TbSales sales2: sales){
			TPatient patient = patientService.getPatientById(sales2.getPatientId());
			sales2.setPatient(patient);
		}
		return sales;
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
	
	// 添加销药单
	@Override
	public int addSalesByTbSales(SalesDto salesDto) {
		TbSales sales = new TbSales();
		int count = 0;
	
		// 同一处方中的药品属于同一销药单
		String salesNo = "XSYY"+IDUtils.getId() + "";
		sales.setSalesNo(salesNo);
		sales.setPatientId(salesDto.getPatientId());
		
		// 依次处理药品
		for (int i = 0; i < salesDto.getDrugDataList().size(); i++) {
			DrugData drugData2 = salesDto.getDrugDataList().get(i);
			String drugName = drugData2.getDrugName();
			String dN = drugData2.getDrugNum();
			Integer drugNum = Integer.parseInt(dN.substring(0, dN.length()-1));
			
			salesDto.setDrugName(drugName);
			salesDto.setQuantity(drugNum);
		
			List<TbDrug> drugs = drugService.getDrugByName(salesDto.getDrugName());
			
			// 判断该药品是否存在，不存在就不处理
			if (drugs == null || drugs.size() == 0) {
				return 0;
			}
			
			count = stockService.countStockQuantityByDrugName(drugs.get(0).getDrugName());
			
			// 判断库存数量是否足够
			if (salesDto.getQuantity() > count) {
				return 0;
			}
			
			// 获得到将待销售的药品，key为药品ID，value为药品信息
			Map<Integer, TbStock> map = this.getSalesStock(salesDto,drugs.get(0).getDrugName());
			
			// 设置该批次的信息，添加销售单记录，同时减少库存
			for (Map.Entry<Integer, TbStock> entry : map.entrySet()) {
				TbDrug drug1 = new TbDrug();
				drug1 = drugService.getDrugById(entry.getKey());
				sales.setDrugId(entry.getKey());
				sales.setQuantity(entry.getValue().getStockQuantity());
				sales.setTotalPrice(this.getOrderItemTotalPrice(sales.getQuantity(),drug1.getSalePrice()));
				
				// 因为每条记录的ID是不一样的
				TbSales lastSales = this.getLastRecord();
				
				if(lastSales!=null){
					sales.setId(lastSales.getId()+1);
				}else{
					sales.setId(1);
				}
				
				TbStock stock = new TbStock();
				stock.setDrugId(drug1.getId());
				count = salesMapper.insert(sales);
				if (count > 0) {
					stock.setStockQuantity(sales.getQuantity());
					stockService.updateByStockSelective(stock);
					}
			}
		}
		
		return count;
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
	 * 根据药品名称获取需要的信息
	 * @param salesDto
	 * @param drugId
	 * @return
	 */
	public Map<Integer, TbStock> getSalesStock(SalesDto salesDto,String drugName) {
		// 根据药品名称找到相关的药品
		List<TbStock> stockList = stockService.getStockByDrugName(drugName);
		// 用来保存待销售的药品
		Map<Integer, TbStock> map = new LinkedHashMap<Integer, TbStock>();
		
		Integer countQuantity = 0;
		
		for(TbStock stock : stockList) {
			if (stock.getStockQuantity() <= 0) {
				continue;
			}
			
			TbStock stock2 = new TbStock();
			countQuantity += stock.getStockQuantity();
			if (countQuantity <=  salesDto.getQuantity()) {
				stock2.setDrugId(stock.getDrugId());
				stock2.setStockQuantity(stock.getStockQuantity());
				map.put(stock.getDrugId(), stock2);
			} else {
				stock2.setDrugId(stock.getDrugId());
				stock2.setStockQuantity(salesDto.getQuantity()-countQuantity+stock.getStockQuantity());
				map.put(stock.getDrugId(), stock2);
				break;
			}
		}
		return map;
	}
	

	/**
	 * 单条删除
	 */
	@Override
	public int deleteSalesById(Integer id) {
		TbSales sales = salesMapper.selectByPrimaryKey(id);
		return salesMapper.deleteBySalesNo(sales.getSalesNo());
	}


	/**
	 * 批量删除
	 */
	@Override
	public int deleteSalesByIds(String[] ids) {
		int count = 0;
		List<Integer> list = new ArrayList<Integer>();
		for (String id : ids) {
			list.add(Integer.valueOf(id).intValue());
		}
		
		for (Integer id:list) {
			TbSales sales = salesMapper.selectByPrimaryKey(id);
			count = salesMapper.deleteBySalesNo(sales.getSalesNo());
		}
		return count;
	}


	//根据时间查询销售记录
	@Override
	public List<TbSales> selectAllSales(String beginTime,String endTime) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		List<TbSales> salesList = salesMapper.selectAllSales(map);
		if (salesList != null ) {
			return salesList;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.dgut.service.SalesService#updateSalesBySalesNo(cn.edu.dgut.common.dto.SalesDto)
	 */
	@Override
	public int updateSalesBySalesNo(SalesDto salesDto) {
		// TODO Auto-generated method stub
		return 0;
	}


	/**
	 * 根据药品ID和销售单编号修改
	 */
	@Override
	public int updateSalesBySalesItemId(SalesDto salesDto) {
		
		TbSales sales = new TbSales();  // 作为销售单修改对象
		TbDrug drug = new TbDrug();
		
		drug = drugService.getDrugById(salesDto.getDrugId());
		sales.setId(salesDto.getId());
		sales.setSalesNo(salesDto.getSalesNo());	
		sales.setPatientId(salesDto.getPatientId());
		sales.setQuantity(salesDto.getQuantity());
		sales.setTotalPrice(this.getOrderItemTotalPrice(sales.getQuantity(),drug.getSalePrice()));
		
		int count = 0;
		// 当前库存中该批次的药品数量
		int oldStock = stockService.countStockQuantityByDrugId(drug.getId());
		
		
		// 判断库存数量是否足够
		if (oldStock+salesDto.getOldSalesItemQuantity() < salesDto.getQuantity()) {
			return 0;
		}
		
		// 修改前，先恢复之前的库存数量，否则会出错
		TbStock stock1 = new TbStock();
		stock1.setDrugId(salesDto.getDrugId());
		stock1.setStockQuantity(-salesDto.getOldSalesItemQuantity());
		stockService.updateByStockSelective(stock1);
		
		TbStock stock = new TbStock();
		stock.setDrugId(drug.getId());
		stock.setStockQuantity(sales.getQuantity());
		// 更新库存
		count = stockService.updateByStockSelective(stock);
		
		if (count > 0) {
			count = salesMapper.updateByPrimaryKeySelective(sales);
		}
		
		return count;
	}

	/* (non-Javadoc)
	 * @see cn.edu.dgut.service.SalesService#updateSales(cn.edu.dgut.pojo.TbSales)
	 */
	@Override
	public int updateSales(TbSales sales) {
		// TODO Auto-generated method stub
		return 0;
	}

	// 条件统计
	@Override
	public List<TbSales> saleByCondition(String drugName, String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<TbDrug> drugs = drugService.getDrugByName(drugName);
		List<Integer> ids = new ArrayList<Integer>();
		
		if (drugs != null && drugs.size() > 0) {
			for (TbDrug drug : drugs) {
				ids.add(drug.getId());
			}
		} else {
			return null;
		}
		
		map.put("ids", ids);
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		
		List<TbSales> salesList = salesMapper.selectAllSales1(map);
		
		if (salesList != null && salesList.size() > 0) {
			
			for (TbSales sales : salesList) {
				TbDrug drug = drugService.getDrugById(sales.getDrugId());
				sales.setDrug(drug);
			}
			
			return salesList;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.edu.dgut.service.SalesService#getSalesByPatientId(java.lang.String)
	 */
	@Override
	public List<TbSales> getSalesByPatientId(String patientId) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * 根据销售单编号和药品ID删除销售记录
	 */
	@Override
	public int deleteOneBySalesNoAndDrugId(Integer drugId, String salesNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugId", drugId);
		map.put("salesNo", salesNo);
		
		TbSales sales = salesMapper.getSalesBySalesNoAndDrugId(map);
		
		// 在删除销售记录之前，需要增加相应的库存数量
		TbStock stock = new TbStock();
		stock.setDrugId(sales.getDrugId());
		stock.setStockQuantity(-sales.getQuantity());
		stockService.updateByStockSelective(stock);
		
		return salesMapper.deleteByPrimaryKey(sales.getId());
	}

	// 根据药品ID和销售单编号查找
	@Override
	public TbSales getSalesBySalesNoAndDrugId(Integer id, String salesNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugId", id);
		map.put("salesNo", salesNo);
		return salesMapper.getSalesBySalesNoAndDrugId(map);
	}

	// 销售药品
	@Override
	public int addSalesByDrug(SalesDto salesDto) {
		TbSales sales = new TbSales();
		int count = 0;
		int quantity = 0;
		sales.setSalesNo(salesDto.getSalesNo());
		sales.setPatientId(salesDto.getPatientId());
		
		// 根据药品名称查看库存中药品的数量
		quantity = stockService.countStockQuantityByDrugName(salesDto.getDrugName());
		
		// 判断库存数量是否足够
		if (salesDto.getQuantity() > quantity) {
			return 0;
		}
		
		// 获得到将待销售的药品，key为药品ID，value为药品信息
		Map<Integer, TbStock> map = this.getSalesStock(salesDto,salesDto.getDrugName());
		
		// 设置该批次的信息，添加销售单记录，同时减少库存
		for (Map.Entry<Integer, TbStock> entry : map.entrySet()) {
			TbDrug drug1 = new TbDrug();
			drug1 = drugService.getDrugById(entry.getKey());
			sales.setDrugId(entry.getKey());
			sales.setQuantity(entry.getValue().getStockQuantity());
			sales.setTotalPrice(this.getOrderItemTotalPrice(sales.getQuantity(),drug1.getSalePrice()));
			
			// 因为每条记录的ID是不一样的
			TbSales lastSales = this.getLastRecord();
			
			if(lastSales!=null){
				sales.setId(lastSales.getId()+1);
			}else{
				sales.setId(1);
			}
	
			TbStock stock = new TbStock();
			stock.setDrugId(drug1.getId());
			stock.setStockQuantity(sales.getQuantity());
			
			// 更新库存
			count = stockService.updateByStockSelective(stock);
			
			if (count > 0) {
				Map<String, Object> map2 = new HashMap<String,Object>();
				map2.put("salesNo", salesDto.getSalesNo());
				map2.put("drugId", drug1.getId());
				TbSales sales2 = salesMapper.getSalesBySalesNoAndDrugId(map2);
				
				if (sales2 != null) {
					sales2.setQuantity(sales2.getQuantity()+sales.getQuantity());
					sales2.setTotalPrice(BigDecimalUtil.add(sales2.getTotalPrice().doubleValue(), 
							sales.getTotalPrice().doubleValue()));
					count = salesMapper.updateByPrimaryKeySelective(sales2);
				} else {
					count = salesMapper.insert(sales);
				}
			}
			
		}
		
		return count;
	}

	// 根据销售单编号查询
	@Override
	public List<TbSales> getSalesBySalesNo1(String salesNo) {
		return salesMapper.selectBySalesNo(salesNo);
	}


}
