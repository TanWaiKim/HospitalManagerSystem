package cn.edu.dgut.service.impl;

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
import cn.edu.dgut.pojo.TbDrugAdmin;
import cn.edu.dgut.pojo.TbProvider;
import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.service.DrugAdminService;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.ProviderService;
import cn.edu.dgut.service.PurchaseItemService;
import cn.edu.dgut.service.PurchaseService;
import cn.edu.dgut.service.StockService;

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
	private DrugService drugService;
	@Autowired
	private ProviderService providerService;
	@Autowired
	private DrugAdminService drugAdminService;
	
	@Override
	public List<TbPurchase> getAllPurchase(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("providerId", null);
		map.put("isStock", null);
		// 根据条件查询总数
		int totalNum = purchaseMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbPurchase> tbPurchases = purchaseMapper.pageByCondition(map);
		
		for(TbPurchase purchase : tbPurchases) {
			TbProvider provider = providerService.getProviderById(purchase.getProviderId());
			TbDrugAdmin drugAdmin = drugAdminService.getDrugAdminById(purchase.getDrugAdminId());
			purchase.setProvider(provider);
			purchase.setDrugAdmin(drugAdmin);
		}
		
		return tbPurchases;
	}


	@Override
	public List<TbPurchase> pageByCondition(String purchaseNo, Integer providerId, Integer isStock, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purchaseNo", purchaseNo);
		map.put("providerId", providerId);
		map.put("isStock", isStock);
		// 根据条件查询总数
		int totalNum = purchaseMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());

		List<TbPurchase> tbPurchases = purchaseMapper.pageByCondition(map);
		
		for(TbPurchase purchase : tbPurchases) {
			TbProvider provider = providerService.getProviderById(purchase.getProviderId());
			TbDrugAdmin drugAdmin = drugAdminService.getDrugAdminById(purchase.getDrugAdminId());
			purchase.setProvider(provider);
			purchase.setDrugAdmin(drugAdmin);
		}
		
		return tbPurchases;
	}


	@Override
	public TbPurchase getPurchaseById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public TbPurchase getPurchaseByPurchaseNo(String purchaseNo) {
		return purchaseMapper.getPurchaseByPurchaseNo(purchaseNo);
	}


	@Override
	public int addPurchaseByTbPurchase(PurchaseDto purchaseDto) {
		TbPurchase purchase = new TbPurchase();
		purchase.setIsStock(0);
		purchase.setDrugAdminId(purchaseDto.getDrugAdminId());
		purchase.setProviderId(purchaseDto.getProviderId());
		purchase.setQuantity(purchaseDto.getQuantity());
		purchase.setPurchaseNo("CYD"+IDUtils.getId() + "");
		purchase.setRemark(purchaseDto.getRemark());
		purchase.setTotalPrice(BigDecimalUtil.mul(
				purchaseDto.getQuantity().doubleValue(),purchaseDto.getPurchasePrice().doubleValue()));
		
		
		TbDrug drug = new TbDrug();
		drug.setDrugNo(purchaseDto.getDrugNo());
		drug.setDrugName(purchaseDto.getDrugName());
		
		// 根据药品名称和批次查找
		drug = drugService.getDrugBySelective(drug);
		
		if (drug == null) { //同名不同批次
			drug = new TbDrug();
			drug.setDrugName(purchaseDto.getDrugName());
			// 根据药品名称和空的批次查找
			drug = drugService.getDrugBySelective1(drug);
			// 找出有名无号的药品
			if (drug != null) {
				drug.setProduceTime(purchaseDto.getProduceTime());
				drug.setValidTime(purchaseDto.getValidTime());
				drug.setPurchasePrice(purchaseDto.getPurchasePrice());
				drug.setSalePrice(purchaseDto.getSalePrice());
				drug.setDrugNo(purchaseDto.getDrugNo());
				drugService.updateDrugByTbDrug(drug);
			} else { // 否则根据药品名查找药品的信息
				drug = new TbDrug();
				drug.setDrugName(purchaseDto.getDrugName());
				drug = drugService.getDrugBySelective(drug);
				drug.setProduceTime(purchaseDto.getProduceTime());
				drug.setValidTime(purchaseDto.getValidTime());
				drug.setPurchasePrice(purchaseDto.getPurchasePrice());
				drug.setSalePrice(purchaseDto.getSalePrice());
				drug.setDrugNo(purchaseDto.getDrugNo());
				drug.setCreateTime(null);
				drug.setUpdateTime(null);
				//然后添加新的药品信息
				drugService.addDrugByTbDrug(drug);
			}
			
			purchase.setDrugId(drug.getId());
			
			return  purchaseMapper.insert(purchase);
		} else { // 同名同批次，不修改药品表的信息，直接增加采购单记录即可
			purchase.setDrugId(drug.getId());
			return purchaseMapper.insert(purchase);
		}
	}


	@Override
	public int deletePurchaseById(Integer id) {
		return purchaseMapper.deleteByPrimaryKey(id);
	}


	@Override
	public int deletePurchaseByIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			list.add(Long.valueOf(id).longValue());
		}
		return purchaseMapper.deleteBatch(list);
	}


	/**
	 * 获取所有的订单
	 * @return
	 */
	@Override
	public List<TbPurchase> selectAllPurchase(String beginTime,String endTime) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		List<TbPurchase> purchaseList = purchaseMapper.selectAllPurchase(map);
		if (purchaseList != null ) {
			return purchaseList;
		}
		return null;
	}


	@Override
	public int updatePurchaseByPurchaseNo(PurchaseDto purchaseDto) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int updatePurchaseByPurchaseItemId(PurchaseDto purchaseDto) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int updatePurchase(TbPurchase purchase) {
		return purchaseMapper.updateByPurchaseNo(purchase);
	}


	@Override
	public List<TbPurchase> purchaseByCondition(String drugName, String beginTime, String endTime) {
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
		
		List<TbPurchase> purchaseList = purchaseMapper.selectAllPurchase1(map);
		
		if (purchaseList != null && purchaseList.size() > 0) {
			
			for (TbPurchase purchase : purchaseList) {
				TbDrug drug = drugService.getDrugById(purchase.getDrugId());
				purchase.setDrug(drug);
			}
			
			return purchaseList;
		}
		return null;
	}


	@Override
	public int updatePurchaseByTbPurchase(PurchaseDto purchaseDto) {
		TbDrug drug = new TbDrug();
		drug.setId(purchaseDto.getDrugId());
		drug.setDrugNo(purchaseDto.getDrugNo());
		drug.setDrugName(purchaseDto.getDrugName());
		drug.setProduceTime(purchaseDto.getProduceTime());
		drug.setValidTime(purchaseDto.getValidTime());
		drug.setSalePrice(purchaseDto.getSalePrice());
		drug.setPurchasePrice(purchaseDto.getPurchasePrice());
		drugService.updateDrugByTbDrug(drug);
		
		TbPurchase purchase = new TbPurchase();
		purchase.setDrugAdminId(purchaseDto.getDrugAdminId());
		purchase.setId(purchaseDto.getId());
		purchase.setQuantity(purchaseDto.getQuantity());
		purchase.setRemark(purchaseDto.getRemark());
		purchase.setTotalPrice(BigDecimalUtil.mul(
				purchaseDto.getQuantity().doubleValue(), purchaseDto.getPurchasePrice().doubleValue()));
		purchase.setPurchaseNo(purchaseDto.getPurchaseNo());
		return purchaseMapper.updateByPrimaryKeySelective(purchase);
	}
	
	@Override
	public TbPurchase getPurchaseByDrugId(Integer drugId) {
		return purchaseMapper.getPurchaseByDrugId(drugId);
	}


	// 根据供药商ID和药品ID查询
	@Override
	public List<TbPurchase> getPurchaseByProviderIdAndDrugId(Integer providerId, Integer drugId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("providerId", providerId);
		map.put("drugId", drugId);
		return purchaseMapper.getPurchaseByProviderIdAndDrugId(map);
	}

}
