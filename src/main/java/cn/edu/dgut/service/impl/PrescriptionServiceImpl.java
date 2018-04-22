package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.common.util.IDUtils;
import cn.edu.dgut.mapper.TPrescriptionMapper;
import cn.edu.dgut.mapper.TbDrugMapper;
import cn.edu.dgut.mapper.TbStockMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TPrescription;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbDrugExample;
import cn.edu.dgut.pojo.TbStock;
import cn.edu.dgut.pojo.TbStockExample;
import cn.edu.dgut.service.PrescriptionService;
@Service
public class PrescriptionServiceImpl implements PrescriptionService {

	@Autowired
	private TPrescriptionMapper prescriptionMapper;
	
	@Autowired
	private TbDrugMapper drugMapper;
	
	@Autowired
	private TbStockMapper stockMapper;
	
	//分页查询
	@Override
	public List<TPrescription> getAllPrescription(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据条件查询总数
		int totalNum = prescriptionMapper.countByCondition(map);
		
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		return prescriptionMapper.pageByCondition(map);
	}

	//按条件分页查询
	@Override
	public List<TPrescription> pageByCondition(String prescriptionId,String patientId,String name, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("prescriptionId", prescriptionId);
		map.put("patientId", patientId);
		map.put("name", name);
		// 根据条件查询总数
		int totalNum = prescriptionMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return prescriptionMapper.pageByCondition(map);
	}

	//通过id查询处方记录
	@Override
	public TPrescription getPrescriptionById(Integer id) {
		return prescriptionMapper.selectById(id);
	}

	//通过id删除处方记录
	@Override
	public int deletePrescriptionById(Integer id) {
		return prescriptionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deletePrescriptionByIds(String[] ids) {
		List<Integer> list = new ArrayList<Integer>();
		for (String id : ids) {
			list.add(Integer.valueOf(id));
		}
		return prescriptionMapper.deleteBatch(list);
	}

	//添加处方记录
	@Override
	public int addPrescription(TPrescription prescription) {
		String prescriptionId = "Pr"+IDUtils.getId();
		prescription.setPrescriptionId(prescriptionId);
		Date date = new Date();
		prescription.setCreated(date);
		prescription.setUpdated(date);
		return prescriptionMapper.insert(prescription);
	}

	//修改处方记录
	@Override
	public int updatePrescription(TPrescription prescription) {
		// 改为有选择地修改
		return prescriptionMapper.updateByPrimaryKeySelective(prescription);
	}

	//根据输入的字符自动模糊匹配数据库中已有的药品名
	@Override
	public String autoDrugName(String term, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");//解决乱码  
		StringBuffer sb = new StringBuffer("[");
		List<TbDrug> dNStrList = drugMapper.selectDrugNameByAutoComplete(term);
		 for(int i=0;i<dNStrList.size();i++){  
             if(i==dNStrList.size()-1){  
                 //注意在拼接的时候，要用双引号，单引号我试过，不起作用好像是,至少key必须是双引号，最好都写成双引号  
                 /*  
                  * 其中label属性用于显示在autocomplete弹出菜单，而value属性则是选中后给文本框赋的值。如果没有指定其中一个属性  
                  * 则用另一个属性替代(即value和label值一样)，如果label和value都没有指定，则无法用于autocomplete的提示。    
                  */  
                 sb.append("{\"lable\":\""+dNStrList.get(i).getDrugName()+"\",\"value\":\""+dNStrList.get(i).getDrugName()+"\"}]");  
             }else{  
                 sb.append("{\"lable\":\""+dNStrList.get(i).getDrugName()+"\",\"value\":\""+dNStrList.get(i).getDrugName()+"\"},");  
             }  
         }  
         return sb.toString();
    
	}

	//判断库存是否有足够数量的药品用于销售
	@Override
	public boolean isEnoughDrug(String drugName, String drugNum) {
		//根据药品名条件查询药品数据
		TbDrugExample example = new TbDrugExample();
		example.createCriteria().andDrugNameEqualTo(drugName);
		List<TbDrug> drugList = drugMapper.selectByExample(example );
		
		//通过药品id查询总库存
		TbStockExample stockexample = new TbStockExample();
		stockexample.createCriteria().andDrugIdEqualTo(drugList.get(0).getId());
		List<TbStock> stockList = stockMapper.selectByExample(stockexample);
		int stockNum = 0;
		for (TbStock stock : stockList) {
			stockNum+=stock.getStockQuantity();
		}
		
		//比较药品库存与所开处方药品数量的大小
		//药品库存小于所开处方药品数量，说明库存量不足，返回false
		if(stockNum < Integer.valueOf(drugNum)){
			return false;
		}
		
		return true;
	}

	//通过药品名查询药品记录
	@Override
	public List<TbDrug> findByDrugName(String drugName) {
		TbDrugExample example = new TbDrugExample();
		example.createCriteria().andDrugNameEqualTo(drugName);
		List<TbDrug> drugList = drugMapper.selectByExample(example);
		if(drugList.size()>0){
			return drugList;
		}
		return null;
	}

}
