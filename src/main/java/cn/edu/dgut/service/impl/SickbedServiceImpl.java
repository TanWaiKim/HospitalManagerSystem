package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.mapper.TSickbedMapper;
import cn.edu.dgut.mapper.TStayHospitalMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.pojo.TSickbed;
import cn.edu.dgut.pojo.TSickbedExample;
import cn.edu.dgut.pojo.TStayHospitalExample;
import cn.edu.dgut.service.SickbedService;
@Service
public class SickbedServiceImpl implements SickbedService {
	
	@Autowired
	private TSickbedMapper sickbedMapper;
	
	@Autowired
	private TStayHospitalMapper stayHospitalMapper;
	
	@Override
	public List<TSickbed> getAllSickbed(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据条件查询总数
		int totalNum = sickbedMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return sickbedMapper.pageByCondition(map);
	}

	@Override
	public List<TSickbed> pageByCondition(String id, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		// 根据条件查询总数
		int totalNum = sickbedMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return sickbedMapper.pageByCondition(map);
	}

	@Override
	public int deleteSickbedById(long id) {
		//删除病床记录之前先删除病床对应的病人住院信息
		TStayHospitalExample example = new TStayHospitalExample();
		example.createCriteria().andSickbedIdEqualTo(id);
		stayHospitalMapper.deleteByExample(example );
		
		int count = sickbedMapper.deleteByPrimaryKey(id);
		return count;
	}

	@Override
	public int deleteSickbedByIds(String[] idArray) {
		List<Long> list = new ArrayList<Long>();
		for (String id : idArray) {
			list.add(Long.valueOf(id).longValue());
		}
		
		stayHospitalMapper.deleteBatchBySickIds(list);
		return sickbedMapper.deleteBatch(list);
	}

	@Override
	public TSickbed getSickbedById(long id) {
		TSickbedExample example = new TSickbedExample();
		example.createCriteria().andIdEqualTo(id);
		return sickbedMapper.selectByExample(example).get(0);
	}

	@Override
	public int updateSickbedByTSickbed(TSickbed sickbed) {
		sickbed.setUpdated(new Date());
		return sickbedMapper.updateByPrimaryKey(sickbed);
	}

	@Override
	public int addSickbedByTSickbed(TSickbed sickbed) {
		TSickbed lastSickbed = new TSickbed();
		lastSickbed = this.getLastRecord();
		if (lastSickbed != null) {
			sickbed.setId(lastSickbed.getId() + 1);
		} else {
			sickbed.setId(1l);
		}

		Date date = new Date();
		sickbed.setCreated(date);
		sickbed.setUpdated(date);
		return sickbedMapper.insert(sickbed);
	}

	private TSickbed getLastRecord() {
		TSickbed sicked = sickbedMapper.selectLastRecord();
		if (sicked != null) {
			return sicked;
		}
		return null;
	}

	@Override
	public List<Long> getAllSickbedId() {
		List<TSickbed> sickbedList = sickbedMapper.selectAllSickbed();
		List<Long> sickbedIdList =new ArrayList<>();
		if(sickbedList.size()>0){
			for(TSickbed sickbed:sickbedList){
				sickbedIdList.add(sickbed.getId());
			}
		}
		return sickbedIdList;
	}

	//自动搜索匹配sickbedId
	@Override
	public String autoSickbedId(String term, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");//解决乱码  
		StringBuffer sb = new StringBuffer("[");
		List<TSickbed> sIsStrList = sickbedMapper.selectSickbedIdByAutoComplete(term);
		 for(int i=0;i<sIsStrList.size();i++){  
             if(i==sIsStrList.size()-1){  
                 //注意在拼接的时候，要用双引号，单引号我试过，不起作用好像是,至少key必须是双引号，最好都写成双引号  
                 /*  
                  * 其中label属性用于显示在autocomplete弹出菜单，而value属性则是选中后给文本框赋的值。如果没有指定其中一个属性  
                  * 则用另一个属性替代(即value和label值一样)，如果label和value都没有指定，则无法用于autocomplete的提示。    
                  */  
                 sb.append("{\"lable\":\""+sIsStrList.get(i).getId()+"\",\"value\":\""+sIsStrList.get(i).getId()+"\"}]");  
             }else{  
                 sb.append("{\"lable\":\""+sIsStrList.get(i).getId()+"\",\"value\":\""+sIsStrList.get(i).getId()+"\"},");  
             }  
         }  
         return sb.toString();
	}

}
