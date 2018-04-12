package cn.edu.dgut.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.util.BigDecimalUtil;
import cn.edu.dgut.mapper.TbSalesItemMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbSales;
import cn.edu.dgut.pojo.TbSalesItem;
import cn.edu.dgut.service.SalesItemService;
import cn.edu.dgut.service.SalesService;

/**
 * @author TanWaiKim
 * @time 2018年2月24日 下午10:54:01
 * @version 1.0
 */
@Service
public class SalesItemServiceImpl implements SalesItemService {
	@Autowired
	private TbSalesItemMapper salesItemMapper;
	@Autowired
	private SalesService salesService;
	
	@Override
	public List<TbSalesItem> getAllSalesItem(String salesNo, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		//防止刷新该字段不为空
		map.put("salesNo", salesNo);
		
		// 根据条件查询总数
		int totalNum = salesItemMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbSalesItem> salesItemList = salesItemMapper.pageByCondition(map);	
		return salesItemList;
	}

	@Override
	public List<TbSalesItem> pageByCondition(String salesNo, Page page) {
		
		return null;
	}

	@Override
	public TbSalesItem getSalesItemById(Integer id) {
		TbSalesItem salesItem = salesItemMapper.selectByPrimaryKey(id);
		if (salesItem != null) {
			return salesItem;
		}

		return null;
	}

	@Override
	public List<TbSalesItem> getSalesItemBySalesNo(String salesNo) {
		
		return null;
	}

	/**
	 * 获取tb_sales_item中最后一条记录
	 * @return
	 */
	public TbSalesItem getLastRecord(){
		TbSalesItem salesItem = salesItemMapper.selectLastRecord();
		if(salesItem!=null){
			return salesItem;
		}
		return null;
	}
	
	/**
	 * 添加新的记录
	 */
	@Override
	public int addSalesItemByTbSalesItem(TbSalesItem sales) {
		TbSalesItem lastSalesItem = this.getLastRecord();
		if(lastSalesItem!=null){
			sales.setId(lastSalesItem.getId()+1);
		}else{
			sales.setId(1);
		}
		
		int count = salesItemMapper.insert(sales);		
		return count;
	}

	@Override
	public int updateSalesItemByTbSalesItem(TbSalesItem sales) {
		return salesItemMapper.updateByPrimaryKeySelective(sales);
	}

	@Override
	public int deleteSalesItemById(Integer id) {
		// 根据id获取销药详细单
		TbSalesItem salesItem = salesItemMapper.selectByPrimaryKey(id);
		// 根据销药号获取销药单
		TbSales sales = salesService.getSalesBySalesNo(salesItem.getSalesNo());
		// 删除后的销药单总数量
		sales.setTotalQuantity(sales.getTotalQuantity() - salesItem.getQuantity());
		BigDecimal payment = BigDecimalUtil.mul(salesItem.getQuantity().doubleValue(),salesItem.getSalePrice().doubleValue());
		// 删除后的销药单总价格
		sales.setTotalPrice(BigDecimalUtil.sub(sales.getTotalPrice().doubleValue(), payment.doubleValue()));
		salesService.updateSales(sales);
		return salesItemMapper.deleteByPrimaryKey(id);		
	}

	@Override
	public int deleteSalesItemByIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			list.add(Long.valueOf(id).longValue());
			// 根据id获取销药详细单
			TbSalesItem salesItem = salesItemMapper.selectByPrimaryKey(Integer.valueOf(id).intValue());
			// 根据采药号获取销药单
			TbSales sales = salesService.getSalesBySalesNo(salesItem.getSalesNo());
			// 删除后的销药单总数量
			sales.setTotalQuantity(sales.getTotalQuantity() - salesItem.getQuantity());
			BigDecimal payment = BigDecimalUtil.mul(salesItem.getQuantity().doubleValue(),salesItem.getSalePrice().doubleValue());
			// 删除后的销药单总价格
			sales.setTotalPrice(BigDecimalUtil.sub(sales.getTotalPrice().doubleValue(), payment.doubleValue()));
			salesService.updateSales(sales);
		}
		return salesItemMapper.deleteBatch(list);
	}

	@Override
	public int deleteSalesItemBySalesItem(String salesNo) {
		return salesItemMapper.deleteBySalesNo(salesNo);
	}

	@Override
	public List<TbSalesItem> selectAllSalesItem() {
		List<TbSalesItem> salesItems = salesItemMapper.selectAllSalesItem1();
		
		if (salesItems != null) {
			return salesItems;
		}
		
		return null;
	}

	@Override
	public List<TbSalesItem> selectAllSalesItem(Map<String, Object> map) {
		
		return null;
	}

	@Override
	public TbSalesItem selectByDrugIdAndBatchNo(TbSalesItem salesItem) {
		TbSalesItem salesItem1 = salesItemMapper.selectByDrugIdAndBatchNo(salesItem);
		
		if (salesItem1 != null) {
			return salesItem1;
		}
	
		return null;
	}

	@Override
	public List<TbSalesItem> selectAllSalesItem(String salesNo) {
		List<TbSalesItem> salesItems = salesItemMapper.selectAllSalesItem(salesNo);
		
		if (salesItems != null) {
			return salesItems;
		}
		
		return null;
	}

	/**
	 * 根据条件统计销售情况
	 */
	@Override
	public List<TbSalesItem> selectAllSale(Map<String, Object> map) {
		List<TbSalesItem> salesItem = salesItemMapper.analysisByCondition(map);
		if (salesItem != null) {
			return salesItem;
		}
		
		return null;
	}

}
