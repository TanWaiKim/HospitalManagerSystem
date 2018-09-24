package cn.edu.dgut.controller;

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

import cn.edu.dgut.common.dto.PurchaseDto;
import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.Const;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TbDrugAdmin;
import cn.edu.dgut.pojo.TbDrugtype;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbProvider;
import cn.edu.dgut.pojo.TbPurchase;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.pojo.TbWarehouse;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.DrugtypeService;
import cn.edu.dgut.service.ProviderService;
import cn.edu.dgut.service.PurchaseItemService;
import cn.edu.dgut.service.PurchaseService;
import cn.edu.dgut.service.WarehouseService;

/**
 * @author TanWaiKim
 * @time 2018年2月2日 下午7:46:45
 * @version 1.0
 */
@Controller
@RequestMapping("/purchase")
public class PurchaseController {
	@Autowired
	private ProviderService providerService;
	@Autowired
	private WarehouseService warehouseService;
	@Autowired
	private DrugService drugService;
	@Autowired
	private DrugtypeService drugtypeService;
	@Autowired
	private PurchaseService purchaseService;
	@Autowired
	private PurchaseItemService purchaseItemService;
	
	
	/**
	 * 返回添加采药单页面
	 * @return
	 */
	@RequestMapping("/skipToAdd")
	public String skipToAdd(HttpSession session, Model model) {
        TbDrugAdmin drugAdmin = (TbDrugAdmin)session.getAttribute(Const.CURRENT_USER);
        if(drugAdmin ==null){
            return "login";
        }
        
        List<TbProvider> providerList = providerService.selectAllProvider();
        model.addAttribute("providerList", providerList); 
   
		List<TbDrug> drugList = drugService.selectAllDrug();
		model.addAttribute("drugList", drugList);     
		
		model.addAttribute("drugAdmin", drugAdmin);
			
		return "purchase-add";
	}
	
	/**
	 * 处理添加采药单请求
	 * @param session
	 * @param model
	 * @param purchaseDto
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addPurchaseByTbPurchase(HttpSession session, Model model, PurchaseDto purchaseDto) {
		try {
			
			if (purchaseDto.getProviderId() == null || purchaseDto.getProviderId() == 0) {
				return HmsResult.build(505, "供药商不能为空！");
			}	        
			
			if (purchaseDto.getDrugName() == null || purchaseDto.getDrugName().equals("")) {
				return HmsResult.build(505, "医药名称不能为空！");
			}
			
			if (purchaseDto.getPurchasePrice() == null) {
				return HmsResult.build(505, "采购单价不能为空！");
			}		
			
			if (purchaseDto.getSalePrice() == null) {
				return HmsResult.build(505, "销售单价不能为空！");
			}	
			
			if (purchaseDto.getProduceTime() == null) {
				return HmsResult.build(505, "生产日期不能为空！");
			}
			
			if (purchaseDto.getValidTime() == null) {
				return HmsResult.build(505, "有效期至不能为空！");
			}
			
			if (purchaseDto.getDrugNo() == null || purchaseDto.getDrugNo().equals("")) {
				return HmsResult.build(505, "产品批号不能为空！");
			}		
			
			if (purchaseDto.getQuantity() == null || purchaseDto.getQuantity() == 0) {
				return HmsResult.build(505, "进药数量不能为空！");
			}
			
			if (purchaseDto.getValidTime().compareTo(purchaseDto.getProduceTime()) < 0) {
				return HmsResult.build(505, "有效期至不能小于生产日期！");
			}
			
			if (purchaseService.addPurchaseByTbPurchase(purchaseDto) > 0) {
				return HmsResult.ok();
			}

		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "数据库异常，添加医药信息失败！");
		}
		return HmsResult.build(500, "添加医药信息失败！");
	}	
	
	
	/**
	 * 采药单列表展示，首次进入
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String getAllPurchase(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setPageNumber(page.getPageNumber()+2);
			page.setCurrentPage(currentPage);
			model.addAttribute("providerList", providerService.selectAllProvider());
			model.addAttribute("purchaseList", purchaseService.getAllPurchase(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "purchase-list";
	}

	/**
	 * 分页条件查询
	 * @param providerId
	 * @param purchaseNo
	 * @param isStock
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition")
	public String getPurchaseByPage(
			@RequestParam(value = "providerId", defaultValue = "") Integer providerId,
			@RequestParam(value = "purchaseNo", defaultValue = "") String purchaseNo,
			@RequestParam(value = "isStock", defaultValue = "") Integer isStock,
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
			
			
			List<TbPurchase> purchaseList = purchaseService.pageByCondition(purchaseNo, providerId, isStock, page);
			
			
			TbProvider providerCondition = providerService.getProviderById(providerId);
			model.addAttribute("providerCondition", providerCondition);
			
			model.addAttribute("purchaseList", purchaseList);
			model.addAttribute("page", page);
			model.addAttribute("purchaseNo", purchaseNo);
			model.addAttribute("isStock", isStock);
			
			
			model.addAttribute("providerList", providerService.selectAllProvider());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "purchase-list";
	}
	
	/**
	 * 根据id查询采药单信息，然后返回到采药详细单页面
	 * @param purchaseNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/findByPurchaseNo")
	public String getPurchaseByNo(@RequestParam(value = "purchaseNo") String purchaseNo, Model model) {
		TbPurchase purchase = purchaseService.getPurchaseByPurchaseNo(purchaseNo);
		TbDrug drug = new TbDrug();
		drug = drugService.getDrugById(purchase.getDrugId());
		TbDrugtype drugtype = new TbDrugtype();
		
		if (drug != null) {
			drugtype = drugtypeService.getDrugtypeById(drug.getDrugtypeId());
			drug.setDrugtype(drugtype);
		}
		
		purchase.setDrug(drug);
		model.addAttribute("purchase", purchase);
		return "purchase-detail";
	}
	
	/**
	 * 根据id查询采药单信息，然后返回到修改采药单页面
	 * @param purchaseNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateByPurchaseNo")
	public String getPurchaseByPurchaseNo(@RequestParam(value = "purchaseNo") String purchaseNo, Model model) {
		TbPurchase purchase = purchaseService.getPurchaseByPurchaseNo(purchaseNo);
		TbDrug drug = drugService.getDrugById(purchase.getDrugId());
		
		PurchaseDto purchaseDto = new PurchaseDto();
		purchaseDto.setDrugAdminId(purchase.getDrugAdminId());
		purchaseDto.setDrugId(purchase.getDrugId());
		purchaseDto.setDrugName(drug.getDrugName());
		purchaseDto.setDrugNo(drug.getDrugNo());
		purchaseDto.setId(purchase.getId());
		purchaseDto.setProduceTime(drug.getProduceTime());
		purchaseDto.setValidTime(drug.getValidTime());
		purchaseDto.setPurchaseNo(purchase.getPurchaseNo());
		purchaseDto.setQuantity(purchase.getQuantity());
		purchaseDto.setRemark(purchase.getRemark());
		purchaseDto.setPurchasePrice(drug.getPurchasePrice());
		purchaseDto.setSalePrice(drug.getSalePrice());
		purchaseDto.setIsStock(purchase.getIsStock());
		
		model.addAttribute("purchaseDto", purchaseDto);
		
		return "purchase-update";
	}
	
	/**
	 * 修改采药单请求
	 * @param session
	 * @param model
	 * @param purchaseDto
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updatePurchaseByTbPurchase(HttpSession session, Model model, PurchaseDto purchaseDto) {
		try {
			
			if (purchaseDto.getIsStock() == 1) {
				return HmsResult.build(505, "该采购单已入库，不可修改！");
			}
			
			if (purchaseDto.getDrugId() == null) {
				return HmsResult.build(505, "医药名称不能为空！");
			}
			
			if (purchaseDto.getPurchasePrice() == null) {
				return HmsResult.build(505, "采购单价不能为空！");
			}		
			
			if (purchaseDto.getSalePrice() == null) {
				return HmsResult.build(505, "销售单价不能为空！");
			}	
			
			if (purchaseDto.getProduceTime() == null) {
				return HmsResult.build(505, "生产日期不能为空！");
			}
			
			if (purchaseDto.getValidTime() == null) {
				return HmsResult.build(505, "有效期至不能为空！");
			}
			
			if (purchaseDto.getValidTime().compareTo(purchaseDto.getProduceTime()) < 0) {
				return HmsResult.build(505, "有效期至不能小于生产日期！");
			}
			
			if (purchaseDto.getDrugNo() == null || purchaseDto.getDrugNo().equals("")) {
				return HmsResult.build(505, "产品批号不能为空！");
			}		
			
			if (purchaseDto.getQuantity() == null || purchaseDto.getQuantity() == 0) {
				return HmsResult.build(505, "进药数量不能为空！");
			}
			
			if (purchaseService.updatePurchaseByTbPurchase(purchaseDto) > 0) {
				return HmsResult.ok();
			}

		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "数据库异常，添加医药信息失败！");
		}
		return HmsResult.build(500, "添加医药信息失败！");
	}	
	
	/**
	 * 返回选择入库页面
	 * @param purchaseNo
	 * @param model
	 * @return
	 */
	@RequestMapping("/stockByPurchaseNo")
	public String gotoStock(@RequestParam(value = "purchaseNo") String purchaseNo, Model model) {

		TbPurchase purchase = purchaseService.getPurchaseByPurchaseNo(purchaseNo);
		TbDrug drug = drugService.getDrugById(purchase.getDrugId());
		purchase.setDrug(drug);

		List<TbWarehouse> warehouses = warehouseService.selectAllWarehouse();
		
		model.addAttribute("purchase", purchase);
		model.addAttribute("warehouses", warehouses);

		return "search-stock";
	}
	
	/**
	 * 删除单条采药单
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deletePurchaseById(Integer id) {
		try {
			if ((purchaseService.deletePurchaseById(id) > 0 )) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除采药单失败！");
		}
		
		return HmsResult.build(500, "删除采药单失败！");
		
	}

	/**
	 * 批量删除采药单
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult deletePurchaseByIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (purchaseService.deletePurchaseByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除采药单失败！");
		}
		return HmsResult.build(500, "删除采药单失败！");
		
	}
	
}
