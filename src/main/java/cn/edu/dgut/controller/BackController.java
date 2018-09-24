package cn.edu.dgut.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.common.dto.BackDto;
import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.BigDecimalUtil;
import cn.edu.dgut.common.util.Const;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.pojo.TbDrugAdmin;
import cn.edu.dgut.pojo.TbBack;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbProvider;
import cn.edu.dgut.service.BackService;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.ProviderService;

/**
 * @author TanWaiKim
 * @time 2018年2月2日 下午7:46:45
 * @version 1.0
 */
@Controller
@RequestMapping("/back")
public class BackController {
	@Autowired
	private ProviderService providerService;
	@Autowired
	private DrugService drugService;
	@Autowired
	private BackService backService;
	
	
	/**
	 * 返回添加退药单页面
	 * @return
	 */
	@RequestMapping("/skipToAdd")
	public String skipToAdd(HttpSession session, Model model) {
		
		List<TbDrug> drugList = drugService.selectAllDrug();
		model.addAttribute("drugList", drugList);  
        
	    List<TbProvider> providerList = providerService.selectAllProvider();
	    model.addAttribute("providerList", providerList);
		
		return "back-add";
	}
	
	/**
	 * 添加新的退货记录
	 * @param session
	 * @param model
	 * @param back
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addBackByTbBack(HttpSession session, Model model, TbBack back) {
		try {
			
			if (back.getProviderId() == null) {
				return HmsResult.build(505, "供药商不能为空！");
			}	        
			
			if (back.getDrugName() == null || back.getDrugName().equals("")) {
				return HmsResult.build(505, "医药名称不能为空！");
			}
			
			
			if (back.getQuantity() == null || back.getQuantity() == 0) {
				return HmsResult.build(505, "退药数量不能为空！");
			}
			
			if (back.getReason() == null || back.getReason().equals("")) {
				return HmsResult.build(505, "退药原因不能为空！");
			}
			
			if (back.getReason() != null && back.getReason().length() > 30) {
				return HmsResult.build(505, "退药原因长度不能大于20个字！");
			}
			
			if (backService.addBackByTbBack(back) > 0) {
				return HmsResult.ok();
			} else {
				return HmsResult.build(505, "添加退药信息失败！请检查供药商或药品信息是否正确!");
			}

		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "数据库异常，添加退药信息失败！");
		}
	}
	
	/**
	 * 退药单列表展示，首次进入
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String getAllBack(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+2);
			page.setCurrentPage(currentPage);
			model.addAttribute("providerList", providerService.selectAllProvider());
			model.addAttribute("backList", backService.getAllBack(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "back-list";
	}

	/**
	 * 分页条件查询
	 * @param backType
	 * @param backObject
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition")
	public String getPurchaseByPage(
			@RequestParam(value = "backNo", defaultValue = "") String backNo,
			@RequestParam(value = "providerId", defaultValue = "") Integer providerId,
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
			
			List<TbBack> backList = backService.pageByCondition(backNo,providerId, page);
			
			TbProvider providerCondition = providerService.getProviderById(providerId);
			
			model.addAttribute("providerCondition", providerCondition);
			model.addAttribute("backNo", backNo);
			
			model.addAttribute("providerList", providerService.selectAllProvider());
			
			model.addAttribute("backList", backList);
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "back-list";
	}	
	
	// 根据退药单编号查看详情
	@RequestMapping("/findByBackNo")
	public String getByBackNo(
			@RequestParam(value = "backNo") String backNo, 
			@RequestParam(value = "page", defaultValue = "1") Integer currentPage, 
			Model model) {
		Page page = new Page();
		page.setCurrentPage(currentPage);
		
		// 分页查询
		List<TbBack> backList = backService.getBackByBackNo(backNo,page);
		
		// 不分页
		List<TbBack> backList1 = backService.getBackByBackNo1(backNo);
		
		TbBack back = new TbBack();
		Integer quantity = 0;
		BigDecimal price = new BigDecimal("0");
		
		// 计算总的金额
		for(TbBack back1:backList1) {
			quantity += back1.getQuantity();
			price = BigDecimalUtil.add(price.doubleValue(), back1.getTotalPrice().doubleValue());
		}
		
		back.setCreateTime(backList1.get(0).getCreateTime());
		back.setQuantity(quantity);
		back.setTotalPrice(price);
		back.setBackNo(backList1.get(0).getBackNo());
		
		TbProvider provider = providerService.getProviderById(backList1.get(0).getProviderId());
		back.setProvider(provider);
		
		TbDrug drug = new TbDrug();
		
		for (TbBack back2:backList) {
			drug = drugService.getDrugById(back2.getDrugId());
			back2.setDrug(drug);
		}
		
		model.addAttribute("back", back);
		model.addAttribute("backList", backList);
		model.addAttribute("page", page);
		return "back-detail";
	}
	
	/**
	 * 分页条件查询（详细分页）
	 * @param salesNo
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition1")
	public String getSalesByPage(
			@RequestParam(value = "backNo", defaultValue = "") String backNo,
			@RequestParam(value = "currentPage", defaultValue = "") String currentPage, Model model) {
		try {
			// 创建分页对象
			Page page = new Page();
			Pattern pattern = Pattern.compile("[0-9]{1,9}");
			if (currentPage == null || !pattern.matcher(currentPage).matches()) {
				page.setCurrentPage(1);
			} else {
				page.setCurrentPage(Integer.valueOf(currentPage));
			}
		
			// 分页
			List<TbBack> backList = backService.getBackByBackNo(backNo,page);
			
			// 不分页
			List<TbBack> backList1 = backService.getBackByBackNo1(backNo);
			
			TbBack back = new TbBack();
			Integer quantity = 0;
			BigDecimal price = new BigDecimal("0");
			
			// 计算总的金额
			for(TbBack back1:backList1) {
				quantity += back1.getQuantity();
				price = BigDecimalUtil.add(price.doubleValue(), back1.getTotalPrice().doubleValue());
			}
			
			back.setCreateTime(backList1.get(0).getCreateTime());
			back.setQuantity(quantity);
			back.setTotalPrice(price);
			back.setBackNo(backList1.get(0).getBackNo());
			
			TbProvider provider = providerService.getProviderById(backList1.get(0).getProviderId());
			back.setProvider(provider);
			
			TbDrug drug = new TbDrug();
			
			for (TbBack back2:backList) {
				drug = drugService.getDrugById(back2.getDrugId());
				back2.setDrug(drug);
			}
			
			model.addAttribute("back", back);
			model.addAttribute("backList", backList);
			model.addAttribute("page", page);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "back-detail";
	}
	
	/**
	 * 删除单条退药记录
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteBackById(Integer id) {
		try {
			if ((backService.deleteBackById(id) > 0 )) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除退药记录失败！");
		}
		
		return HmsResult.build(500, "删除退药记录失败！");
		
	}

	/**
	 * 批量删除退药记录
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult deleteBackByIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (backService.deleteBackByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除退药记录失败！");
		}
		return HmsResult.build(500, "删除退药记录失败！");
		
	}
	
	/**
	 * 根据退药单编号和药品ID删除销售记录
	 */
	@RequestMapping("/deleteOneByBackNoAndDrugId")
	@ResponseBody
	public HmsResult deleteOneByBackNoAndDrugId(Integer drugId,String backNo) {
		try {
			
			if ((backService.deleteOneByBackNoAndDrugId(drugId,backNo) > 0 )) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "药品退回删除失败！");
		}
		
		return HmsResult.build(500, "药品退回记录删除失败！");
		
	}
	
	/**
	 * 返回修改退药单详细单页面
	 * @param backNo
	 * @param id
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateByBackNoAndDrugId")
	public String updateByBackNoAndDrugId(
			@RequestParam(value = "drugId") Integer drugId,
			@RequestParam(value = "backNo") String backNo,
			HttpSession session, 
			Model model) {
        
        // 根据退药单编号和药品ID获取销售信息
		TbBack back = backService.getBackByBackNoAndDrugId(drugId,backNo);
		TbDrug drug = drugService.getDrugById(back.getDrugId());
		
		BackDto backDto = new BackDto();
		backDto.setId(back.getId());             // 销售单ID
		backDto.setBackNo(back.getBackNo());
		backDto.setProviderId(back.getProviderId());
		backDto.setDrugId(drug.getId());
		backDto.setOldBackItemQuantity(back.getQuantity()); // 上次退药量
		backDto.setQuantity(back.getQuantity());
		backDto.setDrugName(drug.getDrugName());
		backDto.setBatchNo(drug.getDrugNo());
		backDto.setReason(back.getReason());
		
		model.addAttribute("backDto", backDto); 
		return "backItem-update";
	}	
	
	/**
	 * 根据药品ID和退药单编号修改
	 * @param session
	 * @param model
	 * @param backDto
	 * @return
	 */
	@RequestMapping("/updateItem")
	@ResponseBody()
	public HmsResult updateBackByBackNoAndDrugId(
			HttpSession session, 
			Model model, 
			BackDto backDto) {
		try {
			if (backDto.getQuantity() == null || backDto.getQuantity() == 0) {
				return HmsResult.build(505, "退回药品数量不能为空！");
			}
			
			if (backDto.getReason() == null || backDto.getReason().equals("")) {
				return HmsResult.build(505, "退药原因不能为空！");
			}
			
			if (backService.updateBackByBackItemId(backDto) > 0) {
				return HmsResult.ok();
			} else {
				return HmsResult.build(505, "该批次药品库存不足，请检查信息是否正确！");
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "修改药品退回记录失败！");
		}
	}	
	
	/**
	 * 返回添加药品页面
	 * @return
	 */
	@RequestMapping("/skipToAddDrug")
	public String skipToAddDrug(HttpSession session,
			Model model,
			@RequestParam(value = "backNo") String backNo,
			@RequestParam(value = "providerId") Integer providerId) {  
		List<TbDrug> drugList = drugService.selectAllDrug();
		model.addAttribute("drugList", drugList);
		model.addAttribute("backNo", backNo);
		model.addAttribute("providerId", providerId);
		return "backItem-add";
	}
	
	/**
	 * 退回药品
	 * @param session
	 * @param model
	 * @param salesDto
	 * @return
	 */
	@RequestMapping("/addDrug")
	@ResponseBody()
	public HmsResult addSalesByDrug(HttpSession session, BackDto backDto) {
		try {
			
			if (backDto.getQuantity() == null || backDto.getQuantity() == 0) {
				return HmsResult.build(505, "数量不能为空！");
			}
			
			if (backDto.getBatchNo() == null || backDto.getBatchNo().equals("")) {
				return HmsResult.build(505, "生产批号不能为空！");
			}
			
			if (backDto.getReason() == null || backDto.getReason().equals("")) {
				return HmsResult.build(505, "退药原因不能为空！");
			}
			
			if (backService.addBackByDrug(backDto) > 0) {
				return HmsResult.ok();
			} else {
				return HmsResult.build(505, "该药品不存在或库存不足，请补充库存！");
			}

		} catch (Exception e) {
			e.getStackTrace();
			
			return HmsResult.build(500, "数据库异常，添加医药信息失败！");
		}
	}
}
