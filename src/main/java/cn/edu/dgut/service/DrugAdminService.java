package cn.edu.dgut.service;

import java.util.List;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrugAdmin;

/**
 * @author TanWaiKim
 * @time 2018年2月2日 下午10:27:10
 * @version 1.0
 */
public interface DrugAdminService {
	TbDrugAdmin login(String username, String password);
	List<TbDrugAdmin> getAllDrugAdmin(Page page);
	List<TbDrugAdmin> pageByCondition(String username, Page page);
	TbDrugAdmin getDrugAdminById(Integer id);
	int updateDrugAdminByTbDrugAdmin(TbDrugAdmin drugAdmin);
	int addDrugAdminByTbDrugAdmin(TbDrugAdmin drugAdmin);
	int deleteDrugAdminById(Integer id);
	int deleteDrugAdminByIds(String[] ids);
	boolean isSimpleLoginName(String username);
	List<TbDrugAdmin> selectAllDrugAdmin();
	TbDrugAdmin getDrugAdminByPhone(String phone);
	TbDrugAdmin getDrugAdminByEmail(String email);
	TbDrugAdmin getDrugAdminByUsername(String username);
	
}
