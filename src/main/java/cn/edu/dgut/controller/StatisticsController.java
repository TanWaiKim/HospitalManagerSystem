package cn.edu.dgut.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.dgut.common.util.BigDecimalUtil;
import cn.edu.dgut.common.util.DateTimeUtil;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbDrugtype;
import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbSales;
import cn.edu.dgut.pojo.TbSalesItem;
import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.DrugtypeService;
import cn.edu.dgut.service.PurchaseItemService;
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
	private PurchaseItemService purchaseItemService;
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
		return drugCategory;
	}
	
    /**
     * 组合医药种类数量的数据集对象  
     * @return
     */
    public DefaultPieDataset getDataSet() {  
    	DefaultPieDataset dataset = new DefaultPieDataset();  
		 for(Map.Entry<String, Integer> entry : this.countDrugCategory().entrySet()){
		  dataset.setValue(entry.getKey(),new Double(entry.getValue())); 
		 }
        return dataset;  
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
        //1. 获得数据集合  
    	DefaultPieDataset dataset = this.getDataSet();  
    	
    	//2.创建饼图
        JFreeChart chart = ChartFactory.createPieChart(
           "各医药种类所占库存量", // chart title
           dataset, // data
           true, // include legend
           true,
           false);
             
        //3. 将图形转换为图片，传到前台  
        String fileName = ServletUtilities.saveChartAsJPEG(chart, 1200, 500, null, request.getSession());  
        String chartURL=request.getContextPath() + "/chart?filename="+fileName;  
        modelMap.put("chartURL", chartURL);  
        return new ModelAndView("category",modelMap);  
    }  
     
  
 	/**
 	 * 按月份统计采药数量
 	 * @return
 	 */
	public Map<String, Integer> countPurchaseQuantity() {
		Map<String, Integer> purchaseQuantity = new LinkedHashMap<String, Integer>();
		// 获取所有库存
		List<TbPurchase> purchaseList = purchaseService.selectAllPurchase();
		
		// 计算数量
		for(TbPurchase purchase: purchaseList) {
			String date = DateTimeUtil.dateToStr(purchase.getCreateTime(),"yyyy-MM");
			if (purchaseQuantity.containsKey(date)) {
				purchaseQuantity.put(date, purchaseQuantity.get(date)+purchase.getTotalQuantity());
			} else {
				purchaseQuantity.put(date, purchase.getTotalQuantity());
			}
		}
		return purchaseQuantity;
	}  
    
 	/**
 	 * 按月份统计采药金额
 	 * @return
 	 */
	public Map<String, BigDecimal> countPurchasePrice() {
		Map<String, BigDecimal> purchasePrice = new LinkedHashMap<String, BigDecimal>();
		// 获取所有库存
		List<TbPurchase> purchaseList = purchaseService.selectAllPurchase();
		
		// 计算数量
		for(TbPurchase purchase: purchaseList) {
			String date = DateTimeUtil.dateToStr(purchase.getCreateTime(),"yyyy-MM");
			if (purchasePrice.containsKey(date)) {
				purchasePrice.put(date, BigDecimalUtil.add(purchasePrice.get(date).doubleValue(), purchase.getTotalPrice().doubleValue()));
			} else {
				Integer init = 0;
				purchasePrice.put(date, BigDecimalUtil.add(init.doubleValue(), purchase.getTotalPrice().doubleValue()));
			}
			
		}
		return purchasePrice;
	} 
    
    /**
     * 组合按月份统计采药金额的数据集对象  
     * @return
     */
    public CategoryDataset getPurchasePriceDataSet(Map<String, BigDecimal> map) {  
        DefaultCategoryDataset defaultdataset = new DefaultCategoryDataset();  
		
        if (map == null) {
			return null;
		}
        
        //组合采药数量
        for(Map.Entry<String, BigDecimal> entry : map.entrySet()){
		  defaultdataset.addValue(new Double(entry.getValue().doubleValue()), "采药金额", entry.getKey());
		}
		
        return defaultdataset;  
    }  
    
    /**
     * 组合按月份统计采药数量的数据集对象  
     * @return
     */
    public CategoryDataset getPurchaseQuantityDataSet(Map<String, Integer> map) {  
        DefaultCategoryDataset defaultdataset = new DefaultCategoryDataset();  
		
        if (map == null) {
			return null;
		}
        
        //组合采药数量
        for(Map.Entry<String, Integer> entry : map.entrySet()){
		  defaultdataset.addValue(new Double(entry.getValue()), "采药数量", entry.getKey());
		}
		
        return defaultdataset;  
    } 
    
    /**
     * 按月份统计采药数量和金额
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/purchase")  
    public ModelAndView getPurchase(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{  
        //获取数据集对象  
        CategoryDataset dataset1 = this.getPurchaseQuantityDataSet(this.countPurchaseQuantity());  
        CategoryDataset dataset2 = this.getPurchasePriceDataSet(this.countPurchasePrice());   
    	
        JFreeChart chart = ChartFactory.createLineChart("采药数量以及金额统计分析", 
        	    "月份",
        	    "采药量",
        	    dataset1,
        	    PlotOrientation.VERTICAL,
        	    true,//底部是否显示 GuangZhou、ShangHai 的theme
        	    false,
        	    false);
        	CategoryPlot plot=chart.getCategoryPlot();
        	//Y2轴的设置
        	NumberAxis numberaxis2 = new NumberAxis("采药额");
        	plot.setRangeAxis(1, numberaxis2);
        	plot.setDataset(1, dataset2);
        	plot.mapDatasetToRangeAxis(1, 1);
        	
        	CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
        	plot.setRenderer(1, renderer2);
             
        //3. 将图形转换为图片，传到前台  
        String fileName = ServletUtilities.saveChartAsJPEG(chart, 1200, 500, null, request.getSession());  
        String chartURL=request.getContextPath() + "/chart?filename="+fileName;  
        modelMap.put("chartURL", chartURL);  
        return new ModelAndView("purchase",modelMap);  
    }   
	
    
 	/**
 	 * 按条件统计采药数量
 	 * @return
 	 */
	public Map<String, Integer> countPurchaseQuantityCondition(String drugName,String drugNo,String beginTime,String endTime) {
		Map<String, Integer> purchaseQuantity = new LinkedHashMap<String, Integer>();
		// 获取所有库存
		List<TbPurchaseItem> purchaseItemList = purchaseService.purchaseByCondition(drugName, drugNo, beginTime, endTime);
		
		if (purchaseItemList == null) {
			return null;
		}
		
		
		// 计算数量
		for(TbPurchaseItem purchaseItem: purchaseItemList) {
			String date = DateTimeUtil.dateToStr(purchaseItem.getCreateTime(),"yyyy-MM-dd");
			if (purchaseQuantity.containsKey(date)) {
				purchaseQuantity.put(date, purchaseQuantity.get(date)+purchaseItem.getQuantity());
			} else {
				purchaseQuantity.put(date, purchaseItem.getQuantity());
			}
		}
		return purchaseQuantity;
	}  
    
 	/**
 	 * 按条件统计采药金额
 	 * @return
 	 */
	public Map<String, BigDecimal> countPurchasePriceCondition(String drugName,String drugNo,String beginTime,String endTime) {
		Map<String, BigDecimal> purchasePrice = new LinkedHashMap<String, BigDecimal>();
		// 获取所有库存
		List<TbPurchaseItem> purchaseItemList = purchaseService.purchaseByCondition(drugName, drugNo, beginTime, endTime);
		
		if (purchaseItemList == null) {
			return null;
		}
		
		// 计算数量
		for(TbPurchaseItem purchaseItem: purchaseItemList) {
			String date = DateTimeUtil.dateToStr(purchaseItem.getCreateTime(),"yyyy-MM-dd");
			if (purchasePrice.containsKey(date)) {
				purchasePrice.put(date, BigDecimalUtil.add(purchasePrice.get(date).doubleValue(),purchaseItem.getPurchaseTotalPrice().doubleValue()));
			} else {
				Integer init = 0;
				purchasePrice.put(date, BigDecimalUtil.add(init.doubleValue(), purchaseItem.getPurchaseTotalPrice().doubleValue()));
			}
			
		}
		return purchasePrice;
	} 
    
    
    /**
     * 按照分类条件查询
     * @param drugName
     * @param drugNo
     * @param beginTime
     * @param endTime
     * @param model
     * @return
     */
	@RequestMapping("/purchaseByCondition")
	public ModelAndView getPurchaseByCondition(
			@RequestParam(value = "drugName", defaultValue = "") String drugName,
			@RequestParam(value = "drugNo", defaultValue = "") String drugNo,
			@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
			@RequestParam(value = "endTime", defaultValue = "") String endTime,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) {
		try {
			if (drugName.equals("") && drugNo.equals("") && beginTime.equals("") && endTime.equals("")) {
				this.getPurchase(request, response, modelMap);
			} else {
		        //获取数据集对象  
		        CategoryDataset dataset1 = this.getPurchaseQuantityDataSet(this.countPurchaseQuantityCondition(drugName,drugNo,beginTime,endTime));  
		        CategoryDataset dataset2 = this.getPurchasePriceDataSet(this.countPurchasePriceCondition(drugName,drugNo,beginTime,endTime));   
		        JFreeChart chart = ChartFactory.createLineChart("采药数量以及金额统计分析", 
		        	    "日期",
		        	    "采药量",
		        	    dataset1,
		        	    PlotOrientation.VERTICAL,
		        	    true,//底部是否显示 GuangZhou、ShangHai 的theme
		        	    false,
		        	    false);
		        	CategoryPlot plot=chart.getCategoryPlot();
		        	//Y2轴的设置
		        	NumberAxis numberaxis2 = new NumberAxis("采药额");
		        	plot.setRangeAxis(1, numberaxis2);
		        	plot.setDataset(1, dataset2);
		        	plot.mapDatasetToRangeAxis(1, 1);
		        	
		        	CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
		        	plot.setRenderer(1, renderer2);
		             
		        //3. 将图形转换为图片，传到前台  
		        String fileName = ServletUtilities.saveChartAsJPEG(chart, 1200, 500, null, request.getSession());  
		        String chartURL=request.getContextPath() + "/chart?filename="+fileName;  
		        modelMap.put("chartURL", chartURL);  
				modelMap.addAttribute("drugName", drugName);
				modelMap.addAttribute("drugNo", drugNo);
				modelMap.addAttribute("beginTime", beginTime);
				modelMap.addAttribute("endTime", endTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("purchase",modelMap);
	}
	

 	/**
 	 * 首次统计价格变化
 	 * @return
 	 */
	public Map<String, BigDecimal> countPriceChange(String drugName,String drugNo,String beginTime,String endTime) {
		Map<String, BigDecimal> priceChange = new LinkedHashMap<String, BigDecimal>();
		List<TbPurchaseItem> purchaseItemList = null;
		
		if (drugName.equals("") && drugNo.equals("") && beginTime.equals("") && endTime.equals("")) {
			// 获取所有库存
			purchaseItemList = purchaseItemService.selectAllPurchaseItem();
		} else {
			purchaseItemList = purchaseItemService.purchaseItemByCondition(drugName,drugNo,beginTime,endTime);
		}
		
		
		if (purchaseItemList == null) {
			return null;
		}
		
		TbPurchaseItem purchaseItem = purchaseItemList.get(0);
		TbDrug drug = drugService.getDrugById(purchaseItem.getDrugId());
		
		List<TbPurchaseItem> purchaseItems = purchaseService.purchaseByCondition(drug.getDrugName(), drugNo, beginTime, endTime);
		
		// 计算数量
		for(TbPurchaseItem purchaseItem1: purchaseItems) {
			String date = DateTimeUtil.dateToStr(purchaseItem1.getCreateTime(),"yyyy-MM-dd");
			priceChange.put(date, purchaseItem1.getPurchasePrice());
		}
		return priceChange;
	} 
    
    /**
     * 组合按月份统计采药金额的数据集对象  
     * @return
     */
    public DefaultCategoryDataset  getPriceChangeDataSet(Map<String, BigDecimal> map, String drugName) {  
		
        if (map == null) {
			return null;
		}
        
        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
        
        //组合价格
        for(Map.Entry<String, BigDecimal> entry : map.entrySet()){
        	dataset.addValue(entry.getValue().doubleValue(), drugName, entry.getKey());
		}
		
        return dataset; 
    } 
	
	
    @RequestMapping(value = {"/priceChange","/priceChangeByCondition"})  
    public ModelAndView getPriceChange(
			@RequestParam(value = "drugName", defaultValue = "") String drugName,
			@RequestParam(value = "drugNo", defaultValue = "") String drugNo,
			@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
			@RequestParam(value = "endTime", defaultValue = "") String endTime,
    		HttpServletRequest request,
    		HttpServletResponse response, 
    		ModelMap modelMap) throws Exception{  
    	
    	List<TbPurchaseItem> purchaseItemList = null;
    	
    	if (drugName.equals("") && drugNo.equals("") && beginTime.equals("") && endTime.equals("")) {
    		purchaseItemList = purchaseItemService.selectAllPurchaseItem();
		} else {
			purchaseItemList = purchaseItemService.purchaseItemByCondition(drugName,drugNo,beginTime,endTime);
		}
    	
		if (purchaseItemList == null) {
		    JFreeChart lineChart = ChartFactory.createLineChart(
		    		"",
		    	    "采购时间",
		    	    "采药单价（元/单位）",
		    	    this.getPriceChangeDataSet(this.countPriceChange(drugName,drugNo,beginTime,endTime),""),
		    	    PlotOrientation.VERTICAL,
		    	    true,
		    	    true,
		    	    false
		    	    );

	        //3. 将图形转换为图片，传到前台  
	        String fileName1 = ServletUtilities.saveChartAsJPEG(lineChart, 1200, 500, null, request.getSession());  
	        String chartURL1 = request.getContextPath() + "/chart?filename="+fileName1;  
	        modelMap.put("chartURL", chartURL1);  
			modelMap.addAttribute("drugName", drugName);
			modelMap.addAttribute("drugNo", drugNo);
			modelMap.addAttribute("beginTime", beginTime);
			modelMap.addAttribute("endTime", endTime);
	        return new ModelAndView("price-change",modelMap);  
		}
		
		TbPurchaseItem purchaseItem = purchaseItemList.get(0);
		TbDrug drug = drugService.getDrugById(purchaseItem.getDrugId());
    	
		
	    JFreeChart lineChart = ChartFactory.createLineChart(
	    		drug.getDrugName()+"采购价格异动",
	    	    "采购时间",
	    	    "采药单价（元/单位）",
	    	    this.getPriceChangeDataSet(this.countPriceChange(drugName,drugNo,beginTime,endTime),drug.getDrugName()),
	    	    PlotOrientation.VERTICAL,
	    	    true,
	    	    true,
	    	    false
	    	    );

        //3. 将图形转换为图片，传到前台  
        String fileName = ServletUtilities.saveChartAsJPEG(lineChart, 1200, 500, null, request.getSession());  
        String chartURL=request.getContextPath() + "/chart?filename="+fileName;  
        modelMap.put("chartURL", chartURL);  
		modelMap.addAttribute("drugName", drugName);
		modelMap.addAttribute("drugNo", drugNo);
		modelMap.addAttribute("beginTime", beginTime);
		modelMap.addAttribute("endTime", endTime);
        return new ModelAndView("price-change",modelMap);  
    } 
    
    
    
	/**
 	 * 按月份统计销药数量
 	 * @return
 	 */
	public Map<String, Integer> countSaleQuantity() {
		Map<String, Integer> saleQuantity = new LinkedHashMap<String, Integer>();
		// 获取所有库存
		List<TbSales> salesList = salesService.selectAllSales();
		
		// 计算数量
		for(TbSales sales: salesList) {
			String date = DateTimeUtil.dateToStr(sales.getCreateTime(),"yyyy-MM");
			if (saleQuantity.containsKey(date)) {
				saleQuantity.put(date, saleQuantity.get(date)+sales.getTotalQuantity());
			} else {
				saleQuantity.put(date, sales.getTotalQuantity());
			}
		}
		return saleQuantity;
	}  
    
 	/**
 	 * 按月份统计销药金额
 	 * @return
 	 */
	public Map<String, BigDecimal> countSalePrice() {
		Map<String, BigDecimal> salePrice = new LinkedHashMap<String, BigDecimal>();
		// 获取所有库存
		List<TbSales > salesList = salesService.selectAllSales();
		
		// 计算数量
		for(TbSales sales: salesList) {
			String date = DateTimeUtil.dateToStr(sales.getCreateTime(),"yyyy-MM");
			if (salePrice.containsKey(date)) {
				salePrice.put(date, BigDecimalUtil.add(salePrice.get(date).doubleValue(), sales.getTotalPrice().doubleValue()));
			} else {
				Integer init = 0;
				salePrice.put(date, BigDecimalUtil.add(init.doubleValue(), sales.getTotalPrice().doubleValue()));
			}
			
		}
		return salePrice;
	} 
    
    /**
     * 组合按月份统计销药金额的数据集对象  
     * @return
     */
    public CategoryDataset getSalePriceDataSet(Map<String, BigDecimal> map) {  
        DefaultCategoryDataset defaultdataset = new DefaultCategoryDataset();  
		
        if (map == null) {
			return null;
		}
        
        //组合销药数量
        for(Map.Entry<String, BigDecimal> entry : map.entrySet()){
		  defaultdataset.addValue(new Double(entry.getValue().doubleValue()), "销药金额", entry.getKey());
		}
		
        return defaultdataset;  
    }  
    
    /**
     * 组合按月份统计销药数量的数据集对象  
     * @return
     */
    public CategoryDataset getSaleQuantityDataSet(Map<String, Integer> map) {  
        DefaultCategoryDataset defaultdataset = new DefaultCategoryDataset();  
		
        if (map == null) {
			return null;
		}
        
        //组合销药数量
        for(Map.Entry<String, Integer> entry : map.entrySet()){
		  defaultdataset.addValue(new Double(entry.getValue()), "销药数量", entry.getKey());
		}
		
        return defaultdataset;  
    } 
    
    /**
     * 按月份统计销药数量和金额
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sale")  
    public ModelAndView getSale(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap) throws Exception{  
        //获取数据集对象  
        CategoryDataset dataset1 = this.getSaleQuantityDataSet(this.countSaleQuantity());  
        CategoryDataset dataset2 = this.getSalePriceDataSet(this.countSalePrice());   
    	
        JFreeChart chart = ChartFactory.createLineChart("销药数量以及金额统计分析", 
        	    "月份",
        	    "销药量",
        	    dataset1,
        	    PlotOrientation.VERTICAL,
        	    true,//底部是否显示 GuangZhou、ShangHai 的theme
        	    false,
        	    false);
        	CategoryPlot plot=chart.getCategoryPlot();
        	//Y2轴的设置
        	NumberAxis numberaxis2 = new NumberAxis("销药额");
        	plot.setRangeAxis(1, numberaxis2);
        	plot.setDataset(1, dataset2);
        	plot.mapDatasetToRangeAxis(1, 1);
        	
        	CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
        	plot.setRenderer(1, renderer2);
             
        //3. 将图形转换为图片，传到前台  
        String fileName = ServletUtilities.saveChartAsJPEG(chart, 1200, 500, null, request.getSession());  
        String chartURL=request.getContextPath() + "/chart?filename="+fileName;  
        modelMap.put("chartURL", chartURL);  
        return new ModelAndView("sale",modelMap);  
    }   
	
    
 	/**
 	 * 按条件统计销药数量
 	 * @return
 	 */
	public Map<String, Integer> countSaleQuantityCondition(String drugName,String drugNo,String beginTime,String endTime) {
		Map<String, Integer> saleQuantity = new LinkedHashMap<String, Integer>();
		// 获取所有库存
		List<TbSalesItem> salesItemList = salesService.saleByCondition(drugName, drugNo, beginTime, endTime);
		
		if (salesItemList == null) {
			return null;
		}
		
		
		// 计算数量
		for(TbSalesItem salesItem: salesItemList) {
			String date = DateTimeUtil.dateToStr(salesItem.getCreateTime(),"yyyy-MM-dd");
			if (saleQuantity.containsKey(date)) {
				saleQuantity.put(date, saleQuantity.get(date)+salesItem.getQuantity());
			} else {
				saleQuantity.put(date, salesItem.getQuantity());
			}
		}
		return saleQuantity;
	}  
    
 	/**
 	 * 按条件统计销药金额
 	 * @return
 	 */
	public Map<String, BigDecimal> countSalePriceCondition(String drugName,String drugNo,String beginTime,String endTime) {
		Map<String, BigDecimal> salePrice = new LinkedHashMap<String, BigDecimal>();
		// 获取所有库存
		List<TbSalesItem> salesItemList = salesService.saleByCondition(drugName, drugNo, beginTime, endTime);
		
		if (salesItemList == null) {
			return null;
		}
		
		// 计算数量
		for(TbSalesItem salesItem: salesItemList) {
			String date = DateTimeUtil.dateToStr(salesItem.getCreateTime(),"yyyy-MM-dd");
			if (salePrice.containsKey(date)) {
				salePrice.put(date, BigDecimalUtil.add(salePrice.get(date).doubleValue(),salesItem.getSaleTotalPrice().doubleValue()));
			} else {
				Integer init = 0;
				salePrice.put(date, BigDecimalUtil.add(init.doubleValue(), salesItem.getSaleTotalPrice().doubleValue()));
			}
			
		}
		return salePrice;
	} 
    
    
    /**
     * 按照分类条件查询
     * @param drugName
     * @param drugNo
     * @param beginTime
     * @param endTime
     * @param model
     * @return
     */
	@RequestMapping("/saleByCondition")
	public ModelAndView getSaleByCondition(
			@RequestParam(value = "drugName", defaultValue = "") String drugName,
			@RequestParam(value = "drugNo", defaultValue = "") String drugNo,
			@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
			@RequestParam(value = "endTime", defaultValue = "") String endTime,
			HttpServletRequest request,
			HttpServletResponse response, 
			ModelMap modelMap) {
		try {
			if (drugName.equals("") && drugNo.equals("") && beginTime.equals("") && endTime.equals("")) {
				this.getPurchase(request, response, modelMap);
			} else {
		        //获取数据集对象  
		        CategoryDataset dataset1 = this.getSaleQuantityDataSet(this.countSaleQuantityCondition(drugName,drugNo,beginTime,endTime));  
		        CategoryDataset dataset2 = this.getSalePriceDataSet(this.countSalePriceCondition(drugName,drugNo,beginTime,endTime));   
		        JFreeChart chart = ChartFactory.createLineChart("销药数量以及金额统计分析", 
		        	    "日期",
		        	    "销药量",
		        	    dataset1,
		        	    PlotOrientation.VERTICAL,
		        	    true,//底部是否显示 GuangZhou、ShangHai 的theme
		        	    false,
		        	    false);
		        	CategoryPlot plot=chart.getCategoryPlot();
		        	//Y2轴的设置
		        	NumberAxis numberaxis2 = new NumberAxis("销药额");
		        	plot.setRangeAxis(1, numberaxis2);
		        	plot.setDataset(1, dataset2);
		        	plot.mapDatasetToRangeAxis(1, 1);
		        	
		        	CategoryItemRenderer renderer2 = new LineAndShapeRenderer();
		        	plot.setRenderer(1, renderer2);
		             
		        //3. 将图形转换为图片，传到前台  
		        String fileName = ServletUtilities.saveChartAsJPEG(chart, 1200, 500, null, request.getSession());  
		        String chartURL=request.getContextPath() + "/chart?filename="+fileName;  
		        modelMap.put("chartURL", chartURL);  
				modelMap.addAttribute("drugName", drugName);
				modelMap.addAttribute("drugNo", drugNo);
				modelMap.addAttribute("beginTime", beginTime);
				modelMap.addAttribute("endTime", endTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ModelAndView("sale",modelMap);
	}  

    
}
