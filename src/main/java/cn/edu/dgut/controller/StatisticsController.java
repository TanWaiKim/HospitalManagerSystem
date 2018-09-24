package cn.edu.dgut.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.dgut.common.util.BigDecimalUtil;
import cn.edu.dgut.common.util.DateTimeUtil;
import cn.edu.dgut.common.vo.PriceCount;
import cn.edu.dgut.common.vo.PurchaseCount;
import cn.edu.dgut.common.vo.SaleCount;
import cn.edu.dgut.common.vo.TypeCount;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbDrugtype;
import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbSales;
import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.DrugtypeService;
import cn.edu.dgut.service.PurchaseService;
import cn.edu.dgut.service.SalesService;
import cn.edu.dgut.service.StockService;

/**
 * @author TanWaiKim
 * @time 2018年2月16日 下午9:46:27
 * @version 1.0
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsController {
	@Autowired
	private StockService stockService;
	@Autowired
	private DrugService drugService;
	@Autowired 
	private DrugtypeService drugtypeService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private SalesService salesService;
	
	
	/**
	 * 统计各种医药包含的药品数量
	 * @return
	 */
	public Map<String, Integer> countDrugCategory() {
		Map<String, Integer> drugCategory = new HashMap<String, Integer>();
		// 获取所有库存
		List<TbStock> stockList = stockService.selectAllStock();
		
		if (stockList != null && stockList.size() > 0) {
			// 计算数量
			for(TbStock stock: stockList) {
				TbDrug drug = drugService.getDrugById(stock.getDrugId());
				TbDrugtype drugtype = drugtypeService.getDrugtypeById(drug.getDrugtypeId());
				if (drugCategory.containsKey(drugtype.getDrugtypeName())) {
					drugCategory.put(drugtype.getDrugtypeName(), drugCategory.get(drugtype.getDrugtypeName())+stock.getStockQuantity());
				} else {
					drugCategory.put(drugtype.getDrugtypeName(), stock.getStockQuantity());
				}
			}
		}
		
		return drugCategory;
	}
	
	/**
	 * 显示不同种类的医药所占的数量
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/category")  
    public ModelAndView getCategory(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{  
        return new ModelAndView("category",modelMap);  
    }  
     
    
    /**
     * 显示不同种类的医药所占的数量，返回数据
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/category1") 
    @ResponseBody
    public String getCategory1(
    		HttpServletRequest request,
    		HttpServletResponse response, 
    		ModelMap modelMap) throws Exception{  
    	List<TypeCount> list = new ArrayList<TypeCount>();
    	Map<String, Integer> typeCount = new LinkedHashMap<String, Integer>();
    	typeCount = this.countDrugCategory();
    	
    	if (typeCount == null || typeCount.size() == 0) {
			return null;
		}
    	
   	 	for (Map.Entry<String, Integer> entry : typeCount.entrySet()) {
   	 		TypeCount typeCount2 = new TypeCount();
   	 		typeCount2.setValue(entry.getValue());
   	 		typeCount2.setName(entry.getKey());
   	 		list.add(typeCount2);
   	 		}
   	 	
   	 	ObjectMapper mapper = new ObjectMapper();    //提供java-json相互转换功能的类
   	 	
   	 	String json = mapper.writeValueAsString(list);    //将list中的对象转换为Json格式的数组
        
   	 	return json;
    } 
	

 	/**
 	 * 统计价格变化
 	 * @return
 	 */
	public Map<String, BigDecimal> countPriceChange(String drugName,String beginTime,String endTime) {
		Map<String, BigDecimal> priceChange = new LinkedHashMap<String, BigDecimal>();
		List<TbPurchase> purchaseList = null;
		
		purchaseList = purchaseService.purchaseByCondition(drugName,beginTime,endTime);
		
		if (purchaseList == null || purchaseList.size() == 0) {
			return null;
		}
		
		// 计算数量
		for(TbPurchase purchaseItem1: purchaseList) {
			String date = DateTimeUtil.dateToStr(purchaseItem1.getCreateTime(),"yyyy-MM-dd");
			priceChange.put(date, purchaseItem1.getDrug().getPurchasePrice());
		}
		return priceChange;
	} 
    
	
	/**
	 * 价格异动
	 * @param drugName
	 * @param beginTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = {"/priceChange","/priceChangeByCondition"})  
    public ModelAndView getPriceChange(
			@RequestParam(value = "drugName", defaultValue = "") String drugName,
			@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
			@RequestParam(value = "endTime", defaultValue = "") String endTime,
    		HttpServletRequest request,
    		HttpServletResponse response, 
    		ModelMap modelMap) throws Exception{  
		modelMap.addAttribute("drugName", drugName);
		modelMap.addAttribute("beginTime", beginTime);
		modelMap.addAttribute("endTime", endTime);
        return new ModelAndView("price-change",modelMap);  
    } 
    
    /**
     * 统计价格异动，返回JSON数据
     * @param drugName
     * @param beginTime
     * @param endTime
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/priceChange1") 
    @ResponseBody
    public String getPriceChange1(
    		@RequestParam(value = "drugName", defaultValue = "") String drugName,
    		@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
    		@RequestParam(value = "endTime", defaultValue = "") String endTime,
    		HttpServletRequest request,
    		HttpServletResponse response, 
    		ModelMap modelMap) throws Exception{  
    	List<PriceCount> list = new ArrayList<PriceCount>();
    	Map<String, BigDecimal> countPrice = new LinkedHashMap<String, BigDecimal>();
    	
    	if (drugName == null || drugName.equals("")) {
    		countPrice = this.countPriceChange("null",beginTime,endTime);
		} else {
			countPrice = this.countPriceChange(drugName,beginTime,endTime);
		}
   	 	
    	if (countPrice == null || countPrice.size() == 0) {
			return null;
		}
    	
   	 	for (Map.Entry<String, BigDecimal> entry : countPrice.entrySet()) {
   	 		PriceCount priceCount = new PriceCount();
   	 		priceCount.setTime(entry.getKey());
   	 		priceCount.setPrice(entry.getValue());
   	 		list.add(priceCount);
   	 		}
   	 	
   	 	ObjectMapper mapper = new ObjectMapper();    //提供java-json相互转换功能的类
   	 	
   	 	String json = mapper.writeValueAsString(list);    //将list中的对象转换为Json格式的数组
        
   	 	return json;
    } 
    
  
	/**
	 * 按月份统计采购的情况，这只是组织数据而已（升级版）
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Map<String, PurchaseCount> countPurchase(String beginTime,String endTime) {
		Map<String, PurchaseCount> purchaseCount = new LinkedHashMap<String, PurchaseCount>();
		// 获取所有库存
		List<TbPurchase > purchaseList = purchaseService.selectAllPurchase(beginTime,endTime);
		
		// 如果库存为空，返回空
		if (purchaseList == null || purchaseList.size() == 0) {
			return null;
		}
		
		// 计算数量
		for(TbPurchase purchase: purchaseList) {
			String date = DateTimeUtil.dateToStr(purchase.getCreateTime(),"yyyy-MM");
			if (purchaseCount.containsKey(date)) {
				PurchaseCount purchaseCount2 = new PurchaseCount();
				purchaseCount2.setTime(date);
				purchaseCount2.setPrice(BigDecimalUtil.add(purchaseCount.get(date).getPrice().doubleValue(), purchase.getTotalPrice().doubleValue()));
				purchaseCount2.setNum(purchaseCount.get(date).getNum()+purchase.getQuantity());
				purchaseCount.put(date,purchaseCount2);
			} else {
				PurchaseCount purchaseCount2 = new PurchaseCount();
				purchaseCount2.setTime(date);
				Integer init = 0;
				purchaseCount2.setPrice(BigDecimalUtil.add(init.doubleValue(), purchase.getTotalPrice().doubleValue()));
				purchaseCount2.setNum(purchase.getQuantity());
				purchaseCount.put(date, purchaseCount2);
			}
			
		}
		return purchaseCount;
	}
	
	/**
	 * 按药品名称、月份条件统计采购的情况，这只是组织数据而已（升级版）
	 * @param drugName
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Map<String, PurchaseCount> countPurchaseCondition(String drugName,String beginTime,String endTime) {
		Map<String, PurchaseCount> purchaseCount = new LinkedHashMap<String, PurchaseCount>();
		// 获取所有库存
		List<TbPurchase> purchaseList = purchaseService.purchaseByCondition(drugName,beginTime, endTime);
		
		if (purchaseList == null || purchaseList.size() == 0) {
			return null;
		}
		
		// 计算数量
		for(TbPurchase purchase: purchaseList) {
			String date = DateTimeUtil.dateToStr(purchase.getCreateTime(),"yyyy-MM");
			if (purchaseCount.containsKey(date)) {
				PurchaseCount purchaseCount2 = new PurchaseCount();
				purchaseCount2.setTime(date);
				purchaseCount2.setPrice(BigDecimalUtil.add(purchaseCount.get(date).getPrice().doubleValue(), purchase.getTotalPrice().doubleValue()));
				purchaseCount2.setNum(purchaseCount.get(date).getNum()+purchase.getQuantity());
				purchaseCount.put(date,purchaseCount2);
			} else {
				PurchaseCount purchaseCount2 = new PurchaseCount();
				purchaseCount2.setTime(date);
				Integer init = 0;
				purchaseCount2.setPrice(BigDecimalUtil.add(init.doubleValue(), purchase.getTotalPrice().doubleValue()));
				purchaseCount2.setNum(purchase.getQuantity());
				purchaseCount.put(date, purchaseCount2);
			}
			
		}
		return purchaseCount;
	}  
	
	
    /**
     * 按月份统计采药数量和金额（升级版），这里返回展示的页面（首次进入）
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/purchase1")  
    public ModelAndView getPurchase1(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{  
        return new ModelAndView("purchase",modelMap);  
    }  
	
    /**
     * 按月份统计采药数量和金额（升级版），返回数据，渲染前端页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/purchase2") 
    @ResponseBody
    public String getPurchase2(
    		@RequestParam(value = "drugName", defaultValue = "") String drugName,
    		@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
    		@RequestParam(value = "endTime", defaultValue = "") String endTime,
    		HttpServletRequest request,
    		HttpServletResponse response, 
    		ModelMap modelMap) throws Exception{  
    	List<PurchaseCount> list = new ArrayList<PurchaseCount>();
    	Map<String, PurchaseCount> countPurchase = new LinkedHashMap<String, PurchaseCount>();
    	
    	if (drugName == null  || drugName.equals("")) { // 如果药品名称为空，则不用处理名称
    		countPurchase = this.countPurchase(beginTime, endTime);
		}else {
			countPurchase = this.countPurchaseCondition(drugName, beginTime, endTime);
		}
   	 	
    	if (countPurchase == null || countPurchase.size() == 0) {
			return null;
		}
    	
   	 	for (Map.Entry<String, PurchaseCount> entry : countPurchase.entrySet()) {
   	 		PurchaseCount purchaseCount = new PurchaseCount();
   	 		purchaseCount.setTime(entry.getKey());
   	 		purchaseCount.setNum(entry.getValue().getNum());
   	 		purchaseCount.setPrice(entry.getValue().getPrice());
   	 		list.add(purchaseCount);
   	 		}
   	 	
   	 	ObjectMapper mapper = new ObjectMapper();    //提供java-json相互转换功能的类
   	 	
   	 	String json = mapper.writeValueAsString(list);    //将list中的对象转换为Json格式的数组
        
   	 	return json;
    } 
    
    
    /**
     * 按照分类条件查询，返回前端页面
     * @param beginTime
     * @param endTime
     * @param model
     * @return
     */
	@RequestMapping("/purchaseByCondition1")
	public ModelAndView getPurchaseByCondition1(
			@RequestParam(value = "drugName", defaultValue = "") String drugName,
			@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
			@RequestParam(value = "endTime", defaultValue = "") String endTime,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) {
		modelMap.addAttribute("drugName", drugName);
		modelMap.addAttribute("beginTime", beginTime);
		modelMap.addAttribute("endTime", endTime);
		return new ModelAndView("purchase",modelMap);
	}  
    
    
	
	// 按月份统计销售的情况，这只是组织数据而已（升级版）
	public Map<String, SaleCount> countSale(String beginTime,String endTime) {
		Map<String, SaleCount> saleCount = new LinkedHashMap<String, SaleCount>();
		// 获取所有库存
		List<TbSales > salesList = salesService.selectAllSales(beginTime,endTime);
		
		// 如果库存为空，返回空
		if (salesList == null || salesList.size() == 0) {
			return null;
		}
		
		// 计算数量
		for(TbSales sales: salesList) {
			String date = DateTimeUtil.dateToStr(sales.getCreateTime(),"yyyy-MM");
			if (saleCount.containsKey(date)) {
				SaleCount saleCount2 = new SaleCount();
				saleCount2.setTime(date);
				saleCount2.setPrice(BigDecimalUtil.add(saleCount.get(date).getPrice().doubleValue(), sales.getTotalPrice().doubleValue()));
				saleCount2.setNum(saleCount.get(date).getNum()+sales.getQuantity());
				saleCount.put(date,saleCount2);
			} else {
				SaleCount saleCount2 = new SaleCount();
				saleCount2.setTime(date);
				Integer init = 0;
				saleCount2.setPrice(BigDecimalUtil.add(init.doubleValue(), sales.getTotalPrice().doubleValue()));
				saleCount2.setNum(sales.getQuantity());
				saleCount.put(date, saleCount2);
			}
			
		}
		return saleCount;
	}
	
	// 按药品名称、月份条件统计销售的情况，这只是组织数据而已（升级版）
	public Map<String, SaleCount> countSaleCondition(String drugName,String beginTime,String endTime) {
		Map<String, SaleCount> saleCount = new LinkedHashMap<String, SaleCount>();
		// 获取所有库存
		List<TbSales> salesList = salesService.saleByCondition(drugName,beginTime, endTime);
		
		if (salesList == null || salesList.size() == 0) {
			return null;
		}
		
		// 计算数量
		for(TbSales sales: salesList) {
			String date = DateTimeUtil.dateToStr(sales.getCreateTime(),"yyyy-MM");
			if (saleCount.containsKey(date)) {
				SaleCount saleCount2 = new SaleCount();
				saleCount2.setTime(date);
				saleCount2.setPrice(BigDecimalUtil.add(saleCount.get(date).getPrice().doubleValue(), sales.getTotalPrice().doubleValue()));
				saleCount2.setNum(saleCount.get(date).getNum()+sales.getQuantity());
				saleCount.put(date,saleCount2);
			} else {
				SaleCount saleCount2 = new SaleCount();
				saleCount2.setTime(date);
				Integer init = 0;
				saleCount2.setPrice(BigDecimalUtil.add(init.doubleValue(), sales.getTotalPrice().doubleValue()));
				saleCount2.setNum(sales.getQuantity());
				saleCount.put(date, saleCount2);
			}
			
		}
		return saleCount;
	}  
	
	
    /**
     * 按月份统计销药数量和金额（升级版），这里返回展示的页面（首次进入）
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sale1")  
    public ModelAndView getSale1(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{  
        return new ModelAndView("sale",modelMap);  
    }  
	
    /**
     * 按月份统计销药数量和金额（升级版），返回数据，渲染前端页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sale2") 
    @ResponseBody
    public String getSale2(
    		@RequestParam(value = "drugName", defaultValue = "") String drugName,
    		@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
    		@RequestParam(value = "endTime", defaultValue = "") String endTime,
    		HttpServletRequest request,
    		HttpServletResponse response, 
    		ModelMap modelMap) throws Exception{  
    	List<SaleCount> list = new ArrayList<SaleCount>();
    	Map<String, SaleCount> countSale = new LinkedHashMap<String, SaleCount>();
    	
    	if (drugName == null  || drugName.equals("")) { // 如果药品名称为空，则不用处理名称
    		countSale = this.countSale(beginTime, endTime);
		}else {
			countSale = this.countSaleCondition(drugName, beginTime, endTime);
		}
   	 	
    	if (countSale == null || countSale.size() == 0) {
			return null;
		}
    	
   	 	for (Map.Entry<String, SaleCount> entry : countSale.entrySet()) {
   	 		SaleCount saleCount = new SaleCount();
   	 		saleCount.setTime(entry.getKey());
   	 		saleCount.setNum(entry.getValue().getNum());
   	 		saleCount.setPrice(entry.getValue().getPrice());
   	 		list.add(saleCount);
   	 		}
   	 	
   	 	ObjectMapper mapper = new ObjectMapper();    //提供java-json相互转换功能的类
   	 	
   	 	String json = mapper.writeValueAsString(list);    //将list中的对象转换为Json格式的数组
        
   	 	return json;
    } 
    
    
    /**
     * 按照分类条件查询，返回前端页面
     * @param beginTime
     * @param endTime
     * @param model
     * @return
     */
	@RequestMapping("/saleByCondition1")
	public ModelAndView getSaleByCondition1(
			@RequestParam(value = "drugName", defaultValue = "") String drugName,
			@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
			@RequestParam(value = "endTime", defaultValue = "") String endTime,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) {
		modelMap.addAttribute("drugName", drugName);
		modelMap.addAttribute("beginTime", beginTime);
		modelMap.addAttribute("endTime", endTime);
		return new ModelAndView("sale",modelMap);
	}  
}
