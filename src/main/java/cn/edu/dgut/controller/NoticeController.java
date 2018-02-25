package cn.edu.dgut.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.dgut.common.result.HmsResult;
import cn.edu.dgut.common.util.ExceptionUtil;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TDiagnosis;
import cn.edu.dgut.pojo.TNotice;
import cn.edu.dgut.service.NoticeService;

/**
 * @author Routa
 * @time 2018年2月24日 下午2:24:27
 * @version 1.0
 */
@Controller
@RequestMapping("/notice")
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("/list")
	public String getAllNotices(@RequestParam(value = "page", defaultValue = "1") Integer currentPage, Model model,HttpServletRequest request) {
		try {
			Page page = new Page();
			page.setCurrentPage(currentPage);
			model.addAttribute("noticeList", noticeService.getAllNotices(page));
			model.addAttribute("page", page);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "notice-list";
	}
	
	@RequestMapping("/pageByCondition")
	public String getNoticesByPage(
			@RequestParam(value = "title", defaultValue = "") String title,
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
			List<TNotice> noticeList = noticeService.pageByCondition(title, page);
			model.addAttribute("noticeList", noticeList);
			model.addAttribute("page", page);
			model.addAttribute("title", title);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "notice-list";
	}
	
	//跳转到公告添加界面
	@RequestMapping("/skipToAdd")
	public String skipToAdd(){
		return "notice-add";
	}
	
	//添加公告
	@RequestMapping("/add")
	@ResponseBody()
	public HmsResult addNotice(TNotice notice){
		try{
			if(noticeService.addNotice(notice)>0){
				return HmsResult.ok();	
			}
			
		}catch(Exception e){
			e.getStackTrace();
			return HmsResult.build(500, "添加失败！");
		}
		return HmsResult.build(500, "添加失败！");
	}
	
	@RequestMapping("/deleteOne")
	@ResponseBody
	public HmsResult deleteNoticeById(String id) {
		try {
			if (noticeService.deleteNoticeByDId(Integer.valueOf(id)) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除失败！");
		}
		
		return HmsResult.build(500, "删除失败！");
		
	}
	
	@RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
	@ResponseBody
	public HmsResult deletNoticesByIds(String ids) {
		String[] idArray = ids.split(",");

		try {
			if (noticeService.deleteNoticesByDIds(idArray) > 0) {
				return HmsResult.ok();
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtil.getStackTrace(e));
			return HmsResult.build(500, "删除失败！");
		}
		return HmsResult.build(500, "删除失败！");
	}
	
	@RequestMapping("/findById/{id}")
	public String getNoticeById(@PathVariable("id") Integer id, Model model) {
		TNotice notice = noticeService.getNoticeById(id);
		model.addAttribute("notice", notice);
		return "notice-update";
	}
	
	@RequestMapping("/update")
	@ResponseBody()
	public HmsResult updateNotice(TNotice notice, Model model) {
		try {
			if(noticeService.updateNotice(notice)>0){
				return HmsResult.ok();
			}
		} catch (Exception e) {
			e.getStackTrace();
			return HmsResult.build(500, "修改失败！");
		}
		return HmsResult.build(500, "修改失败！");
	}
}