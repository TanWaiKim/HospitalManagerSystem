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
	TbSales getSalesBySalesNo(String salesNo);
	int addSalesByTbSales(SalesDto salesDto);
	int deleteSalesById(Integer id);
	int deleteSalesByIds(String[] ids);
	List<TbSales> selectAllSales();
	int updateSalesBySalesNo(SalesDto salesDto);
	int updateSalesBySalesItemId(SalesDto SalesDto);
	int updateSales(TbSales sales);
	List<TbSalesItem> saleByCondition(String drugName,String drugNo,String beginTime,String endTime);
}
