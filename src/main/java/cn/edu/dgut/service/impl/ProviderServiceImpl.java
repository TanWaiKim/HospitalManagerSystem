package cn.edu.dgut.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.dgut.mapper.TbDrugAdminMapper;
import cn.edu.dgut.mapper.TbProviderMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrugAdmin;
import cn.edu.dgut.pojo.TbDrugAdminExample;
import cn.edu.dgut.pojo.TbProvider;
import cn.edu.dgut.pojo.TbProviderExample;
import cn.edu.dgut.service.ProviderService;

/**
 * @author TanWaiKim
 * @time 2018年1月28日 下午3:41:07
 * @version 1.0
 */
@Service
public class ProviderServiceImpl implements ProviderService {
	@Autowired
	private TbProviderMapper providerMapper;
	
	@Autowired
	private TbDrugAdminMapper adminMapper;
	
	/**
	 * 首次进入到供药商列表页面
	 */
	@Override
	public List<TbProvider> getAllProvider(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据条件查询总数
		int totalNum = providerMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return providerMapper.pageByCondition(map);
	}

	/**
	 * 条件查询
	 */
	@Override
	public List<TbProvider> pageByCondition(String providerName, String contact,String keywords, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("providerName", providerName);
		map.put("contact", contact);
		map.put("keywords", keywords);
		// 根据条件查询总数
		int totalNum = providerMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return providerMapper.pageByCondition(map);
	}

	/**
	 * 根据id查询供药商信息
	 */
	@Override
	public TbProvider getProviderById(Integer id) {
		TbProvider provider = providerMapper.selectByPrimaryKey(id);
		if (provider!=null) {
			return provider;
		}
		return null;
	}

	/**
	 * 修改供药商信息
	 */
	@Override
	public int updateProviderByTbProvider(TbProvider provider) {
		int count = providerMapper.updateByPrimaryKey(provider);
		return count;
	}

	/**
	 * 获取tb_provider中最后一条记录
	 * @return
	 */
	public TbProvider getLastRecord(){
		TbProvider provider = providerMapper.selectLastRecord();
		if(provider!=null){
			return provider;
		}
		return null;
	}
	
	/**
	 * 添加供药商信息
	 */
	@Override
	public int addProviderByTbProvider(TbProvider provider) {
		TbProvider lastProvider = this.getLastRecord();
		if(lastProvider!=null){
			provider.setId(lastProvider.getId()+1);
		}else{
			provider.setId(1);
		}
		int count = providerMapper.insert(provider);
		return count;
	}

	/**
	 * 删除单条记录
	 */
	@Override
	public int deleteProviderById(Integer id) {
		return providerMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public int deleteProviderByIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			list.add(Long.valueOf(id).longValue());
		}
		return providerMapper.deleteBatch(list);
	}

	/**
	 * 判断是否登录
	 */
	@Override
	public boolean isSimpleLoginName(String username) {
		//根据loginName条件查询
		TbDrugAdminExample example = new TbDrugAdminExample();
		example.createCriteria().andUsernameEqualTo(username);
		List<TbDrugAdmin> adminList = adminMapper.selectByExample(example);
		if(adminList != null){
			return true;
		}
		return false;
	}

	/**
	 * 获取所有的供药商信息，不分页
	 */
	@Override
	public List<TbProvider> selectAllProvider() {
		List<TbProvider> providerList = providerMapper.selectAllProvider();
		if (providerList != null) {
			return providerList;
		}
		return null;
	}

	/**
	 * 通过手机号码查询
	 */
	@Override
	public TbProvider getProviderByPhone(String phone) {
		TbProviderExample example = new TbProviderExample();
		example.createCriteria().andPhoneEqualTo(phone);
		List<TbProvider> providerList = providerMapper.selectByExample(example);
		if (providerList.size() > 0) {
			return providerList.get(0);
		}
		return null;
	}

}
