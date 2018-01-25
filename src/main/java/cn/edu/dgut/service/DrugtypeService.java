package cn.edu.dgut.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrugtype;

/**
 * @author TanWaiKim
 * @time 2018年1月25日 下午1:17:20
 * @version 1.0
 */
public interface DrugtypeService {
	List<TbDrugtype> getAllDrugtype(Page page);
	List<TbDrugtype> pageByCondition(String drugtypeName, String keywords, Page page);
	TbDrugtype getDrugtypeById(Integer id);
	int updateDrugtypeByTbDrugtype(TbDrugtype drugtype);
	int addDrugtypeByTbDrugtype(TbDrugtype drugtype);
	int deleteDrugtypeById(Integer id);
	int deleteDrugtypeByIds(String[] ids);
	boolean isSimpleLoginName(String loginName);
}
