package cn.edu.dgut.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.dgut.mapper.TbDrugtypeMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.pojo.TbDrugtype;
import cn.edu.dgut.service.DrugtypeService;

/**
 * @author TanWaiKim
 * @time 2018年1月25日 下午1:21:29
 * @version 1.0
 */
@Service
public class DrugtypeServiceImpl implements DrugtypeService {

	@Autowired
	private TbDrugtypeMapper drugtypeMapper;
	
	@Override
	public List<TbDrugtype> getAllDrugtype(Page page) {
		
		return null;
	}

	@Override
	public List<TbDrugtype> pageByCondition(String drugtypeId, String name, String mcName, String keywords, Page page) {
		
		return null;
	}


	@Override
	public TbDrugtype getDrugtypeById(String drugtypeId) {
		
		return null;
	}


	@Override
	public TbDrugtype getDrugtypeByPhone(String phone) {
		
		return null;
	}


	@Override
	public int updateDrugtypeByTbDrugtype(TbDrugtype drugtype) {
		
		return 0;
	}

	/**
	 * 获取tb_drugtype中最后一条记录
	 * @return
	 */
	public TbDrugtype getLastRecord(){
		TbDrugtype drugtype = drugtypeMapper.selectLastRecord();
		if(drugtype!=null){
			return drugtype;
		}
		return null;
	}
	
	/**
	 * 添加一条信息医药种类信息
	 */
	@Override
	public int addDrugtypeByTbDrugtype(TbDrugtype drugtype) {
		
		TbDrugtype lastDrugtype = this.getLastRecord();
		if(lastDrugtype!=null){
			drugtype.setId(lastDrugtype.getId()+1);
		}else{
			drugtype.setId(1);
		}
		int count = drugtypeMapper.insert(drugtype);
		return count;
	}


	@Override
	public int deleteDrugtypeById(long id) {
		
		return 0;
	}


	@Override
	public int deleteDrugtypeByIds(String[] ids) {
		
		return 0;
	}


	@Override
	public boolean isSimpleLoginName(String loginName) {
		
		return false;
	}

	@Override
	public void export(String[] idArray) {
		

	}


	@Override
	public void importExcelInfo(MultipartFile file) throws Exception {
		

	}

}
