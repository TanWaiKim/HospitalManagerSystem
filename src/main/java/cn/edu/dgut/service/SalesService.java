package cn.edu.dgut.service;

import java.util.List;

import cn.edu.dgut.common.dto.SalesDto;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbSales;
import cn.edu.dgut.pojo.TbSalesItem;

/**
 * @author TanWaiKim
 * @time 2018年2月24日 下午10:21:04
 * @version 1.0
 */
public interface SalesService {
	List<TbSales> getAllSale(Page page);
	List<TbSales> pageByCondition(String salesNo, String patientId, Page page);
	TbSales getSalesById(Integer id);
	List<TbSales> getSalesBySalesNo(String salesNo,Page page);
	List<TbSales> getSalesBySalesNo1(String salesNo);
	int addSalesByTbSales(SalesDto salesDto);
	int deleteSalesById(Integer id);
	int deleteOneBySalesNoAndDrugId(Integer id,String salesNo);
	int deleteSalesByIds(String[] ids);
	List<TbSales> selectAllSales(String beginTime,String endTime);
	int updateSalesBySalesNo(SalesDto salesDto);
	int updateSalesBySalesItemId(SalesDto SalesDto);
	int updateSales(TbSales sales);
	List<TbSales> saleByCondition(String drugName,String beginTime,String endTime);
	List<TbSales> getSalesByPatientId(String patientId);
	TbSales getSalesBySalesNoAndDrugId(Integer drugId,String salesNo);
	int addSalesByDrug(SalesDto salesDto);
}
