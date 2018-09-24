package cn.edu.dgut.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.dto.BackDto;
import cn.edu.dgut.common.util.BigDecimalUtil;
import cn.edu.dgut.common.util.IDUtils;
import cn.edu.dgut.mapper.TbBackMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbBack;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbDrugAdmin;
import cn.edu.dgut.pojo.TbProvider;
import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbSales;
import cn.edu.dgut.pojo.TbSalesItem;
import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.service.BackService;
import cn.edu.dgut.service.DrugAdminService;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.ProviderService;
import cn.edu.dgut.service.PurchaseItemService;
import cn.edu.dgut.service.PurchaseService;
import cn.edu.dgut.service.SalesItemService;
import cn.edu.dgut.service.SalesService;
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
	private PurchaseService purchaseService;
	@Autowired
	private DrugService drugService;
	@Autowired
	private DrugAdminService drugAdminService;
	@Autowired
	private SalesService salesService;
	@Autowired
	private ProviderService providerService;

	/**
	 * 分页查询所有的退药单信息，刚进来的页面信息
	 */
	@Override
	public List<TbBack> getAllBack(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		//防止刷新该字段不为空
		map.put("providerId", null);
		
		// 根据条件查询总数
		int totalNum = backMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbBack> backList = backMapper.pageByCondition(map);
		
		for (TbBack tbBack : backList) {
			TbDrug drug = drugService.getDrugById(tbBack.getDrugId());
			TbProvider provider = providerService.getProviderById(tbBack.getProviderId());
			tbBack.setDrug(drug);
			tbBack.setProvider(provider);
		}
		
		return backList;
	}

	
	@Override
	public List<TbBack> pageByCondition(String backNo,Integer providerId,  Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("providerId", providerId);
		map.put("backNo", backNo);
		
		// 根据条件查询总数
		int totalNum = backMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbBack> backList = backMapper.pageByCondition(map);
		
		for (TbBack tbBack : backList) {
			TbDrug drug = drugService.getDrugById(tbBack.getDrugId());
			TbProvider provider = providerService.getProviderById(tbBack.getProviderId());
			tbBack.setDrug(drug);
			tbBack.setProvider(provider);
		}
		
		return backList;
	}

	
	@Override
	public TbPurchase getBackById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 添加新的退药记录
	 */
	@Override
	public int addBackByTbBack(TbBack back) {
		TbStock stock = new TbStock();
		
		
		String backNo = "YPTH"+IDUtils.getId() + "";
		back.setBackNo(backNo);
		
		// 根据药品名称和产品批次获得药品信息
		TbDrug drug1 = drugService.getDrugByNameAndDrugNo(back.getDrugName(),back.getDrugNo());
		
		// 代表该批次药品不存在
		if (drug1 == null) {
			return 0;
		}
		
		back.setDrugId(drug1.getId());
		
		List<TbPurchase> purchases = purchaseService.getPurchaseByProviderIdAndDrugId(back.getProviderId(), back.getDrugId());
		
		// 代表该供药商没有提供过此批次的药品
		if (purchases == null || purchases.size() == 0) {
			return 0;
		}
		
		stock.setDrugId(drug1.getId());
		int count = 0;

		// 对于药商退药，先减少相应库存数量，再增加退药记录
		Map<String, Object> map = new HashMap<>();
		map.put("drugId", back.getDrugId());
		
		List<TbStock> stocks = stockService.getStockByDrug(map);
		// 库存中不存在该药品
		if (stocks == null || stocks.size() == 0) {
			return 0;
		}
		
		stock = stocks.get(0);
		
		// 先判断退货的数量是否合理
		if (stock.getStockQuantity() < back.getQuantity()) {
			return 0;
		}
		
		stock.setStockQuantity(back.getQuantity());
		stockService.updateByStockSelective(stock);
		
		
		// 设置退货总价
		back.setTotalPrice(BigDecimalUtil.mul(back.getQuantity().doubleValue(), 
				drug1.getPurchasePrice().doubleValue()));
		
		count = backMapper.insert(back);
			
		return count;
	}

	/**
	 * 删除单条记录
	 */
	@Override
	public int deleteBackById(Integer id) {
		TbBack back = backMapper.selectByPrimaryKey(id);
		return backMapper.deleteByBackNo(back.getBackNo());
	}

	/**
	 * 批量删除
	 */
	@Override
	public int deleteBackByIds(String[] ids) {
		int count = 0;
		List<Integer> list = new ArrayList<Integer>();
		for (String id : ids) {
			list.add(Integer.valueOf(id).intValue());
		}
		
		for (Integer id:list) {
			TbBack back = backMapper.selectByPrimaryKey(id);
			count = backMapper.deleteByBackNo(back.getBackNo());
		}
		return count;
	}

	
	@Override
	public List<TbBack> selectAllBack() {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see cn.edu.dgut.service.BackService#updateBackByBackId(cn.edu.dgut.pojo.TbBack)
	 */
	@Override
	public int updateBackByBackId(TbBack back) {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see cn.edu.dgut.service.BackService#updateBack(cn.edu.dgut.pojo.TbBack)
	 */
	@Override
	public int updateBack(TbBack back) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 根据退药单编号查询
	 */
	@Override
	public List<TbBack> getBackByBackNo(String backNo, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("backNo", backNo);
		// 根据条件查询总数
		int totalNum = backMapper.countByBackNo(map);
		
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbBack> backs = backMapper.selectByBackNoCondition(map);
		
		if (backs == null || backs.size() == 0) {
			return null;
		}
		
		for(TbBack back2: backs){
			TbProvider provider = providerService.getProviderById(back2.getProviderId());
			back2.setProvider(provider);
		}
		return backs;
	}


	/**
	 * 不分页
	 */
	@Override
	public List<TbBack> getBackByBackNo1(String backNo) {
		return backMapper.selectByBackNo(backNo);
	}

	/**
	 * 根据退药单编号和药品ID查询
	 */
	@Override
	public TbBack getBackByBackNoAndDrugId(Integer drugId, String backNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugId", drugId);
		map.put("backNo", backNo);
		return backMapper.getBackByBackNoAndDrugId(map);
	}

	/**
	 * 计算退药单详细总价
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
	 * 根据图药单编号和药品ID修改
	 */
	@Override
	public int updateBackByBackItemId(BackDto backDto) {
		TbBack back = new TbBack();  // 作为退药单修改对象
		TbDrug drug = new TbDrug();
		
		drug = drugService.getDrugById(backDto.getDrugId());
		back.setId(backDto.getId());
		back.setBackNo(backDto.getBackNo());	
		back.setProviderId(backDto.getProviderId());
		back.setQuantity(backDto.getQuantity());
		back.setTotalPrice(this.getOrderItemTotalPrice(back.getQuantity(),drug.getPurchasePrice()));
		back.setDrugId(backDto.getDrugId());
		
		System.out.println("我是："+back.getDrugId());
		
		List<TbPurchase> purchases = purchaseService.getPurchaseByProviderIdAndDrugId(back.getProviderId(), back.getDrugId());
		
		if (purchases == null || purchases.size() == 0) {
			return 0;
		}
		
		int count = 0;
		// 当前库存中该批次的药品数量
		int oldStock = stockService.countStockQuantityByDrugId(drug.getId());
		
		
		// 判断库存数量是否足够
		if (oldStock+backDto.getOldBackItemQuantity() < backDto.getQuantity()) {
			return 0;
		}
		
		// 修改前，先恢复之前的库存数量，否则会出错
		TbStock stock1 = new TbStock();
		stock1.setDrugId(backDto.getDrugId());
		stock1.setStockQuantity(-backDto.getOldBackItemQuantity());
		stockService.updateByStockSelective(stock1);
		
		TbStock stock = new TbStock();
		stock.setDrugId(drug.getId());
		stock.setStockQuantity(back.getQuantity());
		// 更新库存
		count = stockService.updateByStockSelective(stock);
		
		if (count > 0) {
			count = backMapper.updateByPrimaryKeySelective(back);
		}
		
		return count;
	}


	/**
	 * 添加新的退药信息
	 */
	@Override
	public int addBackByDrug(BackDto backDto) {
		TbBack back = new TbBack();
		int count = 0;
		int quantity = 0;
		
		back.setBackNo(backDto.getBackNo());
		back.setProviderId(backDto.getProviderId());
		back.setQuantity(backDto.getQuantity());
		back.setReason(backDto.getReason());
		back.setDrugNo(backDto.getBatchNo());
		
		// 根据药品名称、生产批号查看库存中药品的数量
		quantity = stockService.countStockQuantityByDrugNameAndDrugNo(backDto.getDrugName(),backDto.getBatchNo());
		
		// 判断库存数量是否足够
		if (backDto.getQuantity() > quantity) {
			return 0;
		}
		
		TbDrug drug = drugService.getDrugByNameAndDrugNo(backDto.getDrugName(), back.getDrugNo());
		
		if (drug == null) {
			return 0;
		}
		
		back.setDrugId(drug.getId());
		back.setTotalPrice(BigDecimalUtil.mul(back.getQuantity().doubleValue(), drug.getPurchasePrice().doubleValue()));
		
		List<TbPurchase> purchases = purchaseService.getPurchaseByProviderIdAndDrugId(back.getProviderId(), back.getDrugId());
		
		if (purchases == null || purchases.size() == 0) {
			return 0;
		}
		
		TbStock stock = new TbStock();
		stock.setDrugId(drug.getId());
		stock.setStockQuantity(backDto.getQuantity());
		
		// 更新库存
		count = stockService.updateByStockSelective(stock);
		
		
		if (count > 0) {
			Map<String, Object> map2 = new HashMap<String,Object>();
			map2.put("backNo", backDto.getBackNo());
			map2.put("drugId", drug.getId());
			TbBack back2 = backMapper.getBackByBackNoAndDrugId(map2);
			
			if (back2 != null) {
				back2.setQuantity(back2.getQuantity()+back.getQuantity());
				back2.setTotalPrice(BigDecimalUtil.add(back2.getTotalPrice().doubleValue(), 
						back.getTotalPrice().doubleValue()));
				count = backMapper.updateByPrimaryKeySelective(back2);
			} else {
				count = backMapper.insert(back);
			}
		}
		
		return count;
	}

	
	/**
	 * 删除退回药品记录
	 */
	@Override
	public int deleteOneByBackNoAndDrugId(Integer drugId, String backNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugId", drugId);
		map.put("backNo", backNo);
		
		TbBack back = backMapper.getBackByBackNoAndDrugId(map);
		
		// 在删除销售记录之前，需要增加相应的库存数量
		TbStock stock = new TbStock();
		stock.setDrugId(back.getDrugId());
		stock.setStockQuantity(-back.getQuantity());
		stockService.updateByStockSelective(stock);
		
		return backMapper.deleteByPrimaryKey(back.getId());
	}
	

}
