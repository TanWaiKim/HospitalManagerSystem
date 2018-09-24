package cn.edu.dgut.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;

import cn.edu.dgut.common.vo.SaleCount;
import cn.edu.dgut.pojo.Product;
import cn.edu.dgut.service.EchartsService;

/**
 * @author TanWaiKim
 * @time 2018年5月9日 上午8:08:20
 * @version 1.0
 */
@RestController
@RequestMapping(value="/echarts",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
public class EchartsTestController {

@Autowired
public EchartsService echartsService;
@Autowired
public StatisticsController statisticsController;
 
 @RequestMapping("/showImage")
   public String showImage() {
       String value = echartsService.getLineImage();
       System.out.println(value);
       return value;
    }
 
 @RequestMapping("/show")
 public String show() throws JsonProcessingException {
	 List<SaleCount> list = new ArrayList<SaleCount>();
	 
	 
     //这里把“类别名称”和“销量”作为两个属性封装在一个Product类里，每个Product类的对象都可以看作是一个类别（X轴坐标值）与销量（Y轴坐标值）的集合
//     list.add(new Product("衬衣1", 10,125.25));
//     list.add(new Product("短袖2", 20,111.25));
//     list.add(new Product("大衣3", 30,128.30));
//     list.add(new Product("皮鞋4", 50,130.30));
//     list.add(new Product("毛织5", 20,90.30));
//     list.add(new Product("拖鞋6", 40,59.30));
//     list.add(new Product("凉鞋7", 60,63.30));
//
//     list.add(new Product("球鞋8", 90,89.30));
     
//     Map<String, Integer> countSaleQuantity = statisticsController.countSaleQuantity(null, null);
//     Map<String, BigDecimal> countSalePrice = statisticsController.countPurchasePrice(null, null);
//     
//     for (Map.Entry<String, Integer> entry : countSaleQuantity.entrySet()) {
//    	 SaleCount saleCount = new SaleCount();
//    	 saleCount.setTime(entry.getKey());
//    	 saleCount.setNum(entry.getValue());
//    	 list.add(saleCount);
//     }

	 Map<String, SaleCount> countSale = statisticsController.countSale(null, null);
	 
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
}