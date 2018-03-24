package cn.edu.dgut.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.dgut.common.util.DateTimeUtil;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.pojo.TbPurchaseItem;
import cn.edu.dgut.service.PatientService;

/**
 * @author Routa
 * @time 2018年3月21日 上午9:46:27
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/patientStatistics")
public class PatientStatisticsController {

	@Autowired
	private PatientService patientService;

	/**
	 * 统计各种医药包含的药品数量
	 * 
	 * @return
	 */
	public Map<String, Integer> countPatientCategory() {
		Map<String, Integer> patientCategory = new HashMap<String, Integer>();

		// 获取所有病人记录
		List<TPatient> patientList = patientService.selectAllPatient();

		// 计算数量
		for (TPatient patient : patientList) {
			if (patientCategory.containsKey(patient.getPersonType())) {
				patientCategory.put(patient.getPersonType(), patientCategory.get(patient.getPersonType()) + 1);
			} else {
				patientCategory.put(patient.getPersonType(), 1);
			}
		}

		return patientCategory;
	}

	/**
	 * 组合病人类型数量的数据集对象
	 * 
	 * @return
	 */
	public DefaultPieDataset getDataSet() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Map.Entry<String, Integer> entry : this.countPatientCategory().entrySet()) {
			dataset.setValue(entry.getKey(), new Double(entry.getValue()));
		}
		return dataset;
	}

	/**
	 * 显示不同类型的病人所占的数量
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/category")
	public ModelAndView getCategory(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		// 1. 获得数据集合
		DefaultPieDataset dataset = this.getDataSet();

		// 2.创建饼图
		JFreeChart chart = ChartFactory.createPieChart("各人群类型就诊记录", // chart
																	// title
				dataset, // data
				true, // include legend
				true, false);

		// 3. 将图形转换为图片，传到前台
		String fileName = ServletUtilities.saveChartAsJPEG(chart, 1200, 500, null, request.getSession());
		String chartURL = request.getContextPath() + "/chart?filename=" + fileName;
		modelMap.put("chartURL", chartURL);
		return new ModelAndView("category", modelMap);
	}

	/**
	 * 按月份统计就诊数量
	 * 
	 * @return
	 */
	public Map<String, Integer> countPatient() {
		Map<String, Integer> patientQuantity = new LinkedHashMap<String, Integer>();

		// 获取所有病人记录
		List<TPatient> patientList = patientService.selectAllPatient();

		// 计算数量
		for (TPatient patient : patientList) {
			String date = DateTimeUtil.dateToStr(patient.getCreated(), "yyyy-MM");
			if (patientQuantity.containsKey(date)) {
				patientQuantity.put(date, patientQuantity.get(date) + 1);
			} else {
				patientQuantity.put(date, 1);
			}
		}
		return patientQuantity;
	}

	/**
	 * 组合按月份统计就诊数量的数据集对象
	 * 
	 * @return
	 */
	public CategoryDataset getPatientQuantityDataSet(Map<String, Integer> map) {
		DefaultCategoryDataset defaultdataset = new DefaultCategoryDataset();

		if (map == null) {
			return null;
		}

		// 组合就诊数量
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			defaultdataset.addValue(new Double(entry.getValue()), "就诊数量", entry.getKey());
		}

		return defaultdataset;
	}

	/**
	 * 按月份统计就诊人数
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping(value = "/month")
	public ModelAndView getPatientByMonth(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws IOException {
		// 获取数据集对象
		CategoryDataset dataset = this.getPatientQuantityDataSet(this.countPatient());

		JFreeChart chart = ChartFactory.createLineChart("病人就诊人数统计分析", "月份", "就诊数量", dataset, PlotOrientation.VERTICAL,
				true, // 底部是否显示 GuangZhou、ShangHai 的theme
				false, false);

		// 将图形转换为图片，传到前台
		String fileName = ServletUtilities.saveChartAsJPEG(chart, 1200, 500, null, request.getSession());
		String chartURL = request.getContextPath() + "/chart?filename=" + fileName;
		modelMap.put("chartURL", chartURL);
		return new ModelAndView("month", modelMap);
	}

	/**
	 * 按条件统计就诊数量
	 * 
	 * @return
	 */
	public Map<String, Integer> countPatientQuantityCondition(String beginTime, String endTime) {
		Map<String, Integer> patientQuantity = new LinkedHashMap<String, Integer>();
		// 获取所有病人记录
		List<TPatient> patientList = patientService.selectAllPatientByCondition(beginTime, endTime);

		if (patientList.size() == 0) {
			return null;
		}

		// 计算数量
		for (TPatient patient : patientList) {
			String date = DateTimeUtil.dateToStr(patient.getCreated(), "yyyy-MM");
			if (patientQuantity.containsKey(date)) {
				patientQuantity.put(date, patientQuantity.get(date) + 1);
			} else {
				patientQuantity.put(date, 1);
			}
		}
		return patientQuantity;
	}

	/**
	 * 按照分类条件查询
	 * 
	 * @param drugName
	 * @param drugNo
	 * @param beginTime
	 * @param endTime
	 * @param model
	 * @return
	 */
	@RequestMapping("/monthByCondition")
	public ModelAndView getPurchaseByCondition(@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
			@RequestParam(value = "endTime", defaultValue = "") String endTime, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		try {
			if (beginTime.equals("") && endTime.equals("")) {
				this.getPatientByMonth(request, response, modelMap);
			} else {
				// 获取数据集对象
				CategoryDataset dataset = this.getPatientQuantityDataSet(this.countPatientQuantityCondition(beginTime, endTime));

				JFreeChart chart = ChartFactory.createLineChart("病人就诊人数统计分析", "月份", "就诊数量", dataset, PlotOrientation.VERTICAL,
						true, // 底部是否显示 GuangZhou、ShangHai 的theme
						false, false);
				

				// 将图形转换为图片，传到前台
				String fileName = ServletUtilities.saveChartAsJPEG(chart, 1200, 500, null, request.getSession());
				String chartURL = request.getContextPath() + "/chart?filename=" + fileName;
				modelMap.put("chartURL", chartURL);
				modelMap.addAttribute("beginTime", beginTime);
				modelMap.addAttribute("endTime", endTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("month", modelMap);
	}
}
