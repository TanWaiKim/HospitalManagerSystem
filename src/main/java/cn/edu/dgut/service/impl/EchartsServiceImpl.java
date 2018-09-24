package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.edu.dgut.pojo.EchartsEntity;
import cn.edu.dgut.service.EchartsService;

/**
 * @author TanWaiKim
 * @time 2018年5月9日 上午8:03:45
 * @version 1.0
 */
@Service
public class EchartsServiceImpl implements EchartsService {
	 private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public String getLineImage() {
		List<EchartsEntity> echarts = new ArrayList<EchartsEntity>();
		//自定义横坐标
		String[] xAxis = {"周一","周二","周三","周四","周五","周六","周日"};
		//自定义三条线
		EchartsEntity entity1 = new EchartsEntity("邮件营销","bar",Arrays.asList(120, 132, 101, 134, 90, 230, 210));
		EchartsEntity entity2 = new EchartsEntity("联盟广告","bar",Arrays.asList(220, 182, 191, 234, 290, 330, 310));
		EchartsEntity entity3 = new EchartsEntity("视频广告","bar",Arrays.asList(150, 232, 201, 154, 190, 330, 410));
		echarts.add(entity1);
		echarts.add(entity2);
		echarts.add(entity3);
		      
		String[] legend = {"邮件营销","联盟广告","视频广告"};
	    Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("xAxis", xAxis);
		resultMap.put("series", echarts);
		resultMap.put("legend", legend);
		
		try {
		       return mapper.writeValueAsString(resultMap);
		  	} catch (JsonProcessingException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		  return "";
		}
}
