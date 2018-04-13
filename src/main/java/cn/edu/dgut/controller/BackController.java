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

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.Const;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.pojo.TbAdmin;
import cn.edu.dgut.pojo.TbBack;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.pojo.TbProvider;
import cn.edu.dgut.service.BackService;
import cn.edu.dgut.service.DrugService;
import cn.edu.dgut.service.PatientService;
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
	private PatientService patientService;
	@Autowired
	private BackService backService;
	
	
	/**
	 * 返回添加退药单页面
	 * @return
	 */
	@RequestMapping("/skipToAdd")
	public String skipToAdd(HttpSession session, Model model, int type) {
        TbAdmin admin = (TbAdmin)session.getAttribute(Const.CURRENT_USER);
        if(admin ==null){
            return "login";
        }
		
		List<TbDrug> drugList = drugService.selectAllDrug();
		model.addAttribute("drugList", drugList);  
        
		if (type == 1) {
	        List<TbProvider> providerList = providerService.selectAllProvider();
	        model.addAttribute("providerList", providerList);
		}
		
		if (type == 2) {
	        List<TPatient> patientList = patientService.selectAllPatient();
	        model.addAttribute("patientList", patientList);
		}

        model.addAttribute("type", type);
		model.addAttribute("operator", admin.getUsername());
		
			
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
			
			if (back.getBackObject() == null || back.getBackObject().equals("")) {
				return HmsResult.build(505, "退药对象不能为空！");
			}	        
	        
			if (back.getBackType() == null || back.getBackType().equals("")) {
				return HmsResult.build(505, "退药类型不能为空！");
			}
			
			if (back.getDrugId() == null || back.getDrugId().equals("")) {
				return HmsResult.build(505, "医药名称不能为空！");
			}
			
			if (back.getBatchNo() == null || back.getBatchNo().equals("")) {
				return HmsResult.build(505, "药品批次不能为空！");
			}
			
			if (back.getBackSum() == null || back.getBackSum() == 0) {
				return HmsResult.build(505, "退药数量不能为空！");
			}
			
			if (back.getBackReason() == null || back.getBackReason().equals("")) {
				return HmsResult.build(505, "退药原因不能为空！");
			}		
			
			if (backService.addBackByTbBack(back) > 0) {
				return HmsResult.ok();
			}

		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "数据库异常，添加退药信息失败！");
		}
		return HmsResult.build(500, "添加退药信息失败！");
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
			model.addAttribute("patientList", patientService.selectAllPatient());
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
			@RequestParam(value = "backType", defaultValue = "") String backType,
			@RequestParam(value = "backObject", defaultValue = "") String backObject,
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
			
			List<TbBack> backList = backService.pageByCondition(backType, backObject, page);
			
			model.addAttribute("typeCondition", backType);
			model.addAttribute("backObject", backObject);
			
			model.addAttribute("backList", backList);
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "back-list";
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
	
}
