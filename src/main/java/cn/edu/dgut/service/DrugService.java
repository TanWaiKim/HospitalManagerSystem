package cn.edu.dgut.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrug;

/**
 * @author TanWaiKim
 * @time 2018年1月26日 上午8:38:45
 * @version 1.0
 */
public interface DrugService {
	List<TbDrug> getAllDrug(Page page);
	List<TbDrug> pageByCondition(Integer drugtypeId, String drugName,String drugNo, Page page);
	TbDrug getDrugById(Integer id);
	int updateDrugByTbDrug(TbDrug drug);
	int addDrugByTbDrug(TbDrug drug);
	int deleteDrugById(Integer id);
	int deleteDrugByIds(String[] ids);
	boolean isSimpleLoginName(String loginName);
	List<TbDrug> selectAllDrug();
	TbDrug getDrugByNo(String drugNo);
	List<TbDrug> getDrugByName(String drugName);
	TbDrug getDrugBySelective(TbDrug drug);
	TbDrug getDrugBySelective1(TbDrug drug);
	int updateSalePrice(TbDrug drug);
	TbDrug getDrugByNameAndDrugNo(String drugName,String drugNo);
	String autoDrugName(String term, HttpServletResponse response);
}
