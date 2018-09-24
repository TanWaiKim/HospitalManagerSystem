package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.mapper.TbDrugAdminMapper;
import cn.edu.dgut.mapper.TbDrugMapper;
import cn.edu.dgut.mapper.TbDrugtypeMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrugAdmin;
import cn.edu.dgut.pojo.TbDrugAdminExample;
import cn.edu.dgut.pojo.TbDrugtype;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.service.DrugService;

/**
 * @author TanWaiKim
 * @time 2018年1月26日 上午8:39:53
 * @version 1.0
 */
@Service
public class DrugServiceImpl implements DrugService {
	@Autowired
	private TbDrugMapper drugMapper;
	@Autowired
	private TbDrugtypeMapper drugtypeMapper;

	@Override
	public List<TbDrug> getAllDrug(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		//防止刷新该字段不为空
		map.put("drugtypeId", null);
		// 根据条件查询总数
		int totalNum = drugMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		
		List<TbDrug> drugs = drugMapper.pageByCondition(map);
		
		for(TbDrug drug:drugs) {
			TbDrugtype drugtype = drugtypeMapper.selectByPrimaryKey(drug.getDrugtypeId());
			if (drug.getProduceTime() != null && drug.getValidTime() != null) {
				drug.setProduceTime(drug.getProduceTime().substring(0, 10));
				drug.setValidTime(drug.getValidTime().substring(0, 10));
			}
			drug.setDrugtype(drugtype);
		}
		
		return drugs;
	}

	@Override
	public TbDrug getDrugById(Integer id) {
		TbDrug drug = drugMapper.selectByPrimaryKey(id);
		if (drug != null) {
			TbDrugtype drugtype = drugtypeMapper.selectByPrimaryKey(drug.getDrugtypeId());
			drug.setDrugtype(drugtype);
		}
		return drug;
	}

	@Override
	public int updateDrugByTbDrug(TbDrug drug) {
		return drugMapper.updateByPrimaryKeySelective(drug);
	}

	/**
	 * 获取tb_drug中最后一条记录
	 * @return
	 */
	public TbDrug getLastRecord(){
		TbDrug drug = drugMapper.selectLastRecord();
		if(drug!=null){
			return drug;
		}
		return null;
	}
	
	@Override
	public int addDrugByTbDrug(TbDrug drug) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugName", drug.getDrugName());
		if (drug.getDrugNo() == null) {
			map.put("drugNo", null);
		} else {
			map.put("drugNo", drug.getDrugNo());
		}
		
		List<TbDrug> drugs = drugMapper.selectByDrugNameAndDrugNo(map);
		
		TbDrug lastDrug = this.getLastRecord();
		if(lastDrug!=null){
			drug.setId(lastDrug.getId()+1);
		}else{
			drug.setId(1);
		}
		
		// 非空，证明该药品已经存在
		if (drugs != null && drugs.size() > 0) {
			return 0;
		} else {
			return drugMapper.insertSelective(drug);
		}
	}

	@Override
	public int deleteDrugById(Integer id) {
		return drugMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteDrugByIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			list.add(Long.valueOf(id).longValue());
		}
		return drugMapper.deleteBatch(list);
	}

	@Override
	public boolean isSimpleLoginName(String loginName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<TbDrug> selectAllDrug() {
		return drugMapper.selectAllDrug();
	}

	@Override
	public TbDrug getDrugByNo(String drugNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TbDrug> getDrugByName(String drugName) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("drugName", drugName);
		return drugMapper.selectByDrugNameAndDrugNo(map);
	}

	@Override
	public TbDrug getDrugBySelective(TbDrug drug) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugName", drug.getDrugName());
		map.put("drugNo", drug.getDrugNo());
		List<TbDrug> drugs = drugMapper.selectByDrugNameAndDrugNo(map);
		
		if (drugs == null || drugs.size() == 0) {
			return null;
		} else {
			return drugs.get(0);
		}
	}

	@Override
	public TbDrug getDrugBySelective1(TbDrug drug) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugName", drug.getDrugName());
		map.put("drugNo", drug.getDrugNo());
		List<TbDrug> drugs = drugMapper.selectByDrugNameAndDrugNo1(map);
		
		if (drugs == null || drugs.size() == 0) {
			return null;
		} else {
			return drugs.get(0);
		}
	}
	
	@Override
	public List<TbDrug> pageByCondition(Integer drugtypeId, String drugName, String drugNo, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugName", drugName);
		map.put("drugNo", drugNo);
		map.put("drugtypeId", drugtypeId);
		// 根据条件查询总数
		int totalNum = drugMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());

		List<TbDrug> drugs = drugMapper.pageByCondition(map);
		
		for(TbDrug drug:drugs) {
			TbDrugtype drugtype = drugtypeMapper.selectByPrimaryKey(drug.getDrugtypeId());
			if (drug.getProduceTime() != null && drug.getValidTime() != null) {
				drug.setProduceTime(drug.getProduceTime().substring(0, 10));
				drug.setValidTime(drug.getValidTime().substring(0, 10));
			}
			drug.setDrugtype(drugtype);
		}
		
		return drugs;
	}

	@Override
	public int updateSalePrice(TbDrug drug) {
		return drugMapper.updateSalePrice(drug);
	}

	/**
	 * 根据药品名称和产品批号查询
	 */
	@Override
	public TbDrug getDrugByNameAndDrugNo(String drugName, String drugNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugName", drugName);
		map.put("drugNo", drugNo);
		
		List<TbDrug> drugs = drugMapper.selectByDrugNameAndDrugNo(map);
		
		if (drugs == null || drugs.size() == 0) {
			return null;
		}
		
		return drugs.get(0);
	}

	/**
	 * 根据输入的字符自动模糊匹配数据库中已有的药品名
	 */
	@Override
	public String autoDrugName(String term, HttpServletResponse response) {
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
	
}
