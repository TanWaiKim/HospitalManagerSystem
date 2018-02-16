package cn.edu.dgut.controller;

import java.util.List;
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
import cn.edu.dgut.pojo.TbProvider;
import cn.edu.dgut.service.ProviderService;

/**
 * @author TanWaiKim
 * @time 2018年1月28日 下午4:30:15
 * @version 1.0
 */
@Controller
@RequestMapping("/provider")
public class ProviderController {
	@Autowired
	private ProviderService providerService;
	
	
	/**
	 * 返回添加供药商页面
	 * @return
	 */
	@RequestMapping("/skipToAdd")
	public String skipToAdd() {
		return "provider-add";
	}

	/**
	 * 添加一条供药商记录
	 * @param provider
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addProviderByTbProvider(TbProvider provider, Model model) {
		try {
			if (provider.getProviderName() == null) {
				return HmsResult.build(505, "供药商名称不能为空！");
			}
			if (provider.getAddress() == null) {
				return HmsResult.build(505, "供药商地址不能为空！");
			}
			if (provider.getContact() == null) {
				return HmsResult.build(505, "联系人不能为空！");
			}
			if (provider.getPhone() != null && provider.getPhone().length() != 11) {
				return HmsResult.build(505, "手机号码格式错误！(11位数字)");
			}
			if (providerService.getProviderByPhone(provider.getPhone()) != null) {
				return HmsResult.build(505, "手机号码已存在！");
			}
			
			if (providerService.addProviderByTbProvider(provider) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "出现异常，添加供药商失败！");
		}
		return HmsResult.build(500, "不明原因，添加供药商失败！");
	}
	
	/**
	 * 供药商列表展示，首次进入
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String getAllProvider(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model) {
		try {
			Page page = new Page();
			page.setCurrentPage(currentPage);
			model.addAttribute("providerList", providerService.getAllProvider(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "provider-list";
	}
	
	/**
	 * 条件查询供药商信息
	 * @param providerName
	 * @param keywords
	 * @param currentPage
	 * @param model
	 * @return
	 */
	@RequestMapping("/pageByCondition")
	public String getDrugtypeByPage(
			@RequestParam(value = "providerName", defaultValue = "") String providerName,
			@RequestParam(value = "contact", defaultValue = "") String contact,
			@RequestParam(value = "keywords", defaultValue = "") String keywords,
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
			List<TbProvider> providerList = providerService.pageByCondition(providerName, contact, keywords, page);
			model.addAttribute("providerList", providerList);
			model.addAttribute("page", page);
			model.addAttribute("contact", contact);
			model.addAttribute("providerName", providerName);
			model.addAttribute("keywords", keywords);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "provider-list";
	}
	
	/**
	 * 根据id查询供药商信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/findById")
	public String getDrugtypeById(@RequestParam(value = "id") Integer id, Model model) {
		TbProvider provider = providerService.getProviderById(id);
		model.addAttribute("provider", provider);
		return "provider-update";
	}
	
	/**
	 * 修改供药商信息
	 * @param provider
	 * @param model
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updateProviderByTbProvider(TbProvider provider, Model model) {
		try {
			
			TbProvider provider1 = providerService.getProviderById(provider.getId());
			TbProvider provider2 = providerService.getProviderByPhone(provider.getPhone());
			
			if (provider.getProviderName() == null) {
				return HmsResult.build(505, "供药商名称不能为空！");
			}
			if (provider.getAddress() == null) {
				return HmsResult.build(505, "供药商地址不能为空！");
			}
			if (provider.getContact() == null) {
				return HmsResult.build(505, "联系人不能为空！");
			}
			if (provider.getPhone() != null && provider.getPhone().length() != 11) {
				return HmsResult.build(505, "手机号码格式错误！(11位数字)");
			}
			if (provider2 != null) {
				if (!provider2.getPhone().equals(provider1.getPhone())) {
					return HmsResult.build(505, "手机号码已存在！");
				}
			}
			if (providerService.updateProviderByTbProvider(provider) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "修改供药商失败！");
		}
		return HmsResult.build(500, "修改供药商失败！");
	}	
	
	/**
	 * 删除单条供药商信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteProviderById(Integer id) {
		try {
			if (providerService.deleteProviderById(id) > 0) {

				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除供药商失败！");
		}
		
		return HmsResult.build(500, "删除供药商失败！");
		
	}

	/**
	 * 批量删除医药种类信息
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult deleteProviderByIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (providerService.deleteProviderByIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除供药商失败！");
		}
		return HmsResult.build(500, "删除供药商失败！");
		
	}
	
	
	
	
}
