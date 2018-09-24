package cn.edu.dgut.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbWarehouse;
import cn.edu.dgut.service.WarehouseService;

/**
 * @author TanWaiKim
 * @time 2018年1月29日 上午10:32:47
 * @version 1.0
 */
@Controller
@RequestMapping("/warehouse")
public class WarehouseController {
	@Autowired
	WarehouseService warehouseService;
	
	/**
	 * 首次进入到仓库列表页面
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String getAllWarehouse(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+2);
			page.setCurrentPage(currentPage);
			model.addAttribute("warehouseList", warehouseService.getAllWarehouse(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "warehouse-list";
	}

	/**
	 * 条件查询
	 * @param warehouseNo
	 * @param warehouseName
	 * @param manager
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition")
	public String getWarehouseyPage(@RequestParam(value = "warehouseNo", defaultValue = "") String warehouseNo,
			@RequestParam(value = "warehouseName", defaultValue = "") String warehouseName,
			@RequestParam(value = "manager", defaultValue = "") String manager,
			@RequestParam(value = "currentPage", defaultValue = "") String currentPage, Model model) {
		try {

			// 创建分页对象
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+2);
			Pattern pattern = Pattern.compile("[0-9]{1,9}");
			if (currentPage == null || !pattern.matcher(currentPage).matches()) {
				page.setCurrentPage(1);
			} else {
				page.setCurrentPage(Integer.valueOf(currentPage));
			}
			List<TbWarehouse> warehouseList = warehouseService.pageByCondition(warehouseNo, warehouseName, manager, page);
			model.addAttribute("warehouseList", warehouseList);
			model.addAttribute("page", page);
			model.addAttribute("warehouseNo", warehouseNo);
			model.addAttribute("warehouseName", warehouseName);
			model.addAttribute("manager", manager);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "warehouse-list";
	}

	/**
	 * 根据id查询
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/findById")
	public String getWarehouseById(@RequestParam(value = "id") Integer id, Model model) {
		TbWarehouse warehouse = warehouseService.getWarehouseById(id);
		model.addAttribute("warehouse", warehouse);
		return "warehouse-update";
	}

	/**
	 * 更新仓库信息
	 * @param warehouse
	 * @param model
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updateWarehouseByTbWarehouse(TbWarehouse warehouse, Model model) {
		try {
			// 先通过warehouse_id获取更新之前的数据，为了与后面的phone对比
			// 更新前后warehouse_id都不会变
			TbWarehouse warehouse1 = warehouseService.getWarehouseById(warehouse.getId());
			TbWarehouse warehouse2 = warehouseService.getWarehouseByPhone(warehouse.getPhone());
			if (warehouse.getWarehouseName() == null || warehouse.getWarehouseName().equals("")) {
				return HmsResult.build(505, "仓库名称不能为空！");
			}
			if (warehouse.getLocation() == null || warehouse.getLocation().equals("")) {
				return HmsResult.build(505, "仓库所在位置不能为空！");
			}	
			if (warehouse.getManager() == null || warehouse.getManager().equals("")) {
				return HmsResult.build(505, "管理员不能为空！");
			}
			if (warehouse.getPhone() == null || warehouse.getPhone().equals("")) {
				return HmsResult.build(505, "手机号码不能为空！");
			}
			if (warehouse.getPhone() != null && warehouse.getPhone().length() != 11) {
				return HmsResult.build(505, "手机号码格式错误！(只能为11位数字)");
			}
			
			String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
			Pattern p = Pattern.compile(regExp);  
			Matcher m = p.matcher(warehouse.getPhone()); 
			
			if (!m.find()) {
				return HmsResult.build(505, "手机号码格式错误！(11位数字)");
			}
			
			if (warehouse2 != null) {
				if (!warehouse2.getPhone().equals(warehouse1.getPhone())) {
					return HmsResult.build(505, "手机号码已存在！");
				}
			}
			if (warehouseService.updateWarehouseByTbWarehouse(warehouse) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "数据库修改仓库信息异常，修改失败！");
		}
		return HmsResult.build(500, "修改仓库信息失败！");
	}

	/**
	 * 跳转到添加页面
	 * @return
	 */
	@RequestMapping("/skipToAdd")
	public String skipToAdd() {
		return "warehouse-add";
	}

	/**
	 * 处理添加请求
	 * @param warehouse
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addWarehouseByTbWarehouse(TbWarehouse warehouse, Model model) {
		try {
			if (warehouse.getWarehouseName() == null || warehouse.getWarehouseName().equals("")) {
				return HmsResult.build(505, "仓库名称不能为空！");
			}
			if (warehouse.getLocation() == null || warehouse.getLocation().equals("")) {
				return HmsResult.build(505, "仓库所在位置不能为空！");
			}	
			if (warehouse.getManager() == null || warehouse.getManager().equals("")) {
				return HmsResult.build(505, "管理员不能为空！");
			}
			if (warehouse.getPhone() == null || warehouse.getPhone().equals("")) {
				return HmsResult.build(505, "手机号码不能为空！");
			}
			if (warehouse.getPhone() != null && warehouse.getPhone().length() != 11) {
				return HmsResult.build(505, "手机号码格式错误！(11位数字)");
			}
			
			String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
			Pattern p = Pattern.compile(regExp);  
			Matcher m = p.matcher(warehouse.getPhone()); 
			
			if (!m.find()) {
				return HmsResult.build(505, "手机号码格式错误！(11位数字)");
			}
			
			if (warehouseService.getWarehouseByPhone(warehouse.getPhone()) != null) {
				return HmsResult.build(505, "手机号码已存在！");
			}
			if (warehouseService.addWarehouseByTbWarehouse(warehouse) > 0) {
				return HmsResult.ok();
			}

		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "数据库添加仓库信息异常，添加失败！");
		}
		return HmsResult.build(500, "仓库信息添加失败！");
	}

	/**
	 * 删除单条仓库信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteWarehouseById(Integer id) {
		try {
			if (warehouseService.deleteWarehouseById(id) > 0) {

				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "数据库删除仓库信息异常，删除失败！");
		}
		
		return HmsResult.build(500, "仓库信息删除失败！");
		
	}

	/**
	 * 批量删除仓库信息
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult deleteWarehouseByIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (warehouseService.deleteWarehouseByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "数据库删除病人记录失败！");
		}
		return HmsResult.build(500, "删除病人记录失败！");
	}
	
	

}
