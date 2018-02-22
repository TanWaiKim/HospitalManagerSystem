package cn.edu.dgut.service;

import java.util.List;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbProvider;

/**
 * @author TanWaiKim
 * @time 2018年1月28日 下午3:19:00
 * @version 1.0
 */
public interface ProviderService {
	List<TbProvider> getAllProvider(Page page);
	List<TbProvider> pageByCondition(String providerName, String contact, String keywords, Page page);
	TbProvider getProviderById(Integer id);
	TbProvider getProviderByPhone(String phone);
	int updateProviderByTbProvider(TbProvider provider);
	int addProviderByTbProvider(TbProvider provider);
	int deleteProviderById(Integer id);
	int deleteProviderByIds(String[] ids);
	boolean isSimpleLoginName(String username);
	List<TbProvider> selectAllProvider();
}
