package cn.edu.dgut.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.dgut.common.util.IDUtils;
import cn.edu.dgut.mapper.TDiagnosisMapper;
import cn.edu.dgut.mapper.TPatientMapper;
import cn.edu.dgut.mapper.TPrescriptionMapper;
import cn.edu.dgut.mapper.TSickbedMapper;
import cn.edu.dgut.mapper.TStayHospitalMapper;
import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TDiagnosis;
import cn.edu.dgut.pojo.TDiagnosisExample;
import cn.edu.dgut.pojo.TPatient;
import cn.edu.dgut.pojo.TPatientExample;
import cn.edu.dgut.pojo.TPrescriptionExample;
import cn.edu.dgut.pojo.TSickbed;
import cn.edu.dgut.pojo.TSickbedExample;
import cn.edu.dgut.pojo.TStayHospital;
import cn.edu.dgut.pojo.TStayHospitalExample;
import cn.edu.dgut.pojo.TbDrug;
import cn.edu.dgut.service.PatientService;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private TPatientMapper patientMapper;

	@Autowired
	private TDiagnosisMapper diagnosisMapper;

	@Autowired
	private TStayHospitalMapper stayHospitalMapper;

	@Autowired
	private TPrescriptionMapper prescriptionMapper;

	// 分页查询
	public List<TPatient> getAllPatient(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mcName", null);
		// 根据条件查询总数
		int totalNum = patientMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return patientMapper.pageByCondition(map);
	}

	// 条件分页查询
	public List<TPatient> pageByCondition(String patientId, String name, String mcName, String keywords, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("patientId", patientId);
		map.put("name", name);
		map.put("mcName", mcName);
		map.put("keywords", keywords);
		// 根据条件查询总数
		int totalNum = patientMapper.countByCondition(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return patientMapper.pageByCondition(map);
	}

	// 通过id标识查询病人
	public TPatient getPById(Long id) {
		TPatient patient = patientMapper.selectByPrimaryKey(id);
		if (patient != null) {
			return patient;
		}
		return null;
	}

	// 通过病人id查询病人
	public TPatient getPatientById(String patientId) {
		TPatientExample example = new TPatientExample();
		example.createCriteria().andPatientIdEqualTo(patientId);
		List<TPatient> patientList = patientMapper.selectByExample(example);
		if (patientList.size() > 0) {
			return patientList.get(0);
		}
		return null;
	}

	// 通过手机号码查询
	public TPatient getPatientByPhone(String phone) {
		TPatientExample example = new TPatientExample();
		example.createCriteria().andPhoneEqualTo(phone);
		List<TPatient> patientList = patientMapper.selectByExample(example);
		if (patientList.size() > 0) {
			return patientList.get(0);
		}
		return null;
	}

	// 获取t_patient中最后一条记录
	public TPatient getLastRecord() {
		TPatient patient = patientMapper.selectLastRecord();
		if (patient != null) {
			return patient;
		}
		return null;
	}

	// 修改病人信息
	public int updatePatientByTPatient(TPatient patient) {
		patient.setUpdated(new Date());
		int count = patientMapper.updateByPrimaryKey(patient);
		// System.out.println("count="+count);
		return count;
	}

	// 添加病人
	public int addPatientByTPatient(TPatient patient) {
		TPatient lastPatient = this.getLastRecord();
		if (lastPatient != null) {
			patient.setId(lastPatient.getId() + 1);
		} else {
			patient.setId(1l);
		}
		String patientId = "P" + IDUtils.getId() + "";
		patient.setPatientId(patientId);
		patient.setCreated(new Date());
		patient.setUpdated(new Date());
		int count = patientMapper.insert(patient);
		return count;
	}

	// 删除单个记录
	public int deletePatientById(long id) {
		// 通过id查询patient获取patientId
		String patientId = patientMapper.selectByPrimaryKey(id).getPatientId();
		deleteRelaByPatientId(patientId);
		return patientMapper.deleteByPrimaryKey(id);
	}

	public void deleteRelaByPatientId(String patientId) {
		// 删除病人的诊断信息
		TDiagnosisExample example = new TDiagnosisExample();
		example.createCriteria().andPatientIdEqualTo(patientId);
		diagnosisMapper.deleteByExample(example);

		// 删除病人的处方信息
		TPrescriptionExample pExample = new TPrescriptionExample();
		pExample.createCriteria().andPatientIdEqualTo(patientId);
		prescriptionMapper.deleteByExample(pExample);

		// 删除病人的住院信息
		TStayHospitalExample dExample = new TStayHospitalExample();
		dExample.createCriteria().andPatientIdEqualTo(patientId);
		stayHospitalMapper.deleteByExample(dExample);

	}

	// 批量删除记录
	public int deletePatientByIds(String[] ids) {
		List<Long> list = new ArrayList<Long>();
		for (String id : ids) {
			// 通过id查询patient获取patientId
			String patientId = patientMapper.selectByPrimaryKey(Long.valueOf(id).longValue()).getPatientId();
			// 该方法删除与病人相关的诊断，处方，住院等信息
			deleteRelaByPatientId(patientId);
			list.add(Long.valueOf(id).longValue());
		}
		return patientMapper.deleteBatch(list);
	}

	/*
	 * 判断数据库中是否存在该登录账号所对应的记录 如果找到该记录，则返回true 否则返回false
	 */

	public boolean isSimpleLoginName(String loginName) {
		// 根据loginName条件查询
		TPatientExample example = new TPatientExample();
		example.createCriteria().andLoginNameEqualTo(loginName);
		List<TPatient> patientList = patientMapper.selectByExample(example);
		if (patientList.size() > 0) {
			return true;
		}
		return false;
	}

	// 批量导出数据
	public void export(String[] idArray) {
		String filename = "D:/patients.xls";
		File file = new File(filename);
		if (!file.exists()) {
			try {
				file.createNewFile();
				// 第一步，创建一个workbook，对应一个Excel文件
				HSSFWorkbook wb = new HSSFWorkbook();
				// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
				HSSFSheet sheet = wb.createSheet("病人信息表一");
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				HSSFRow row = sheet.createRow((int) 0);
				// 第四步，创建单元格，并设置值表头 设置表头居中
				HSSFCellStyle style = wb.createCellStyle();
				// 创建一个居中格式
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				HSSFCell cell = row.createCell((short) 0);
				cell.setCellValue("ID");
				cell.setCellStyle(style);
				cell = row.createCell((short) 1);
				cell.setCellValue("病人ID");
				cell.setCellStyle(style);
				cell = row.createCell((short) 2);
				cell.setCellValue("姓名");
				cell.setCellStyle(style);
				cell = row.createCell((short) 3);
				cell.setCellValue("性别");
				cell.setCellStyle(style);
				cell = row.createCell((short) 4);
				cell.setCellValue("地址");
				cell.setCellStyle(style);
				cell = row.createCell((short) 5);
				cell.setCellValue("年龄");
				cell.setCellStyle(style);
				cell = row.createCell((short) 6);
				cell.setCellValue("是否处理");
				cell.setCellStyle(style);
				cell = row.createCell((short) 7);
				cell.setCellValue("科别名称");
				cell.setCellStyle(style);
				cell = row.createCell((short) 8);
				cell.setCellValue("人群类型");
				cell.setCellStyle(style);
				cell = row.createCell((short) 9);
				cell.setCellValue("联系电话");
				cell.setCellStyle(style);
				cell = row.createCell((short) 10);
				cell.setCellValue("登录账号");
				cell.setCellStyle(style);
				cell = row.createCell((short) 11);
				cell.setCellValue("登录密码");
				cell.setCellStyle(style);
				cell = row.createCell((short) 12);
				cell.setCellValue("创建时间");
				cell.setCellStyle(style);
				cell = row.createCell((short) 13);
				cell.setCellValue("更新时间");
				cell.setCellStyle(style);

				// 第五步，写入从数据库中获取的数据
				List<TPatient> list = new ArrayList<TPatient>();
				for (String id : idArray) {
					TPatient patient = getPById(Long.valueOf(id));
					list.add(patient);
				}
				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						row = sheet.createRow((int) i + 1);
						TPatient patient = list.get(i);
						// 创建单元格，并设置值
						row.createCell((short) 0).setCellValue((long) patient.getId());
						row.createCell((short) 1).setCellValue(patient.getPatientId());
						row.createCell((short) 2).setCellValue(patient.getName());
						row.createCell((short) 3).setCellValue(patient.getSex());
						row.createCell((short) 4).setCellValue(patient.getAddress());
						row.createCell((short) 5).setCellValue(patient.getAge());
						row.createCell((short) 6).setCellValue(patient.getIsFinished());
						row.createCell((short) 7).setCellValue(patient.getMcName());
						row.createCell((short) 8).setCellValue(patient.getPersonType());
						row.createCell((short) 9).setCellValue(patient.getPhone());
						row.createCell((short) 10).setCellValue(patient.getLoginName());
						row.createCell((short) 11).setCellValue(patient.getLoginPassword());
						row.createCell((short) 12)
								.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(patient.getCreated()));
						row.createCell((short) 13)
								.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(patient.getUpdated()));
					}
				}

				// 第六步，将文件存到指定位置
				FileOutputStream fout = new FileOutputStream(filename);
				wb.write(fout);
				fout.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else {
			FileInputStream fileIn = null;
			try {
				fileIn = new FileInputStream(file);
				// 根据指定的文件输入流导入Excel从而产生Workbook对象
				HSSFWorkbook wb0 = new HSSFWorkbook(fileIn);
				// 获取Excel文档中的第一个表单
				HSSFSheet sht0 = wb0.getSheetAt(0);
				// 获取Excel文件中的行数
				int rowNum = sht0.getPhysicalNumberOfRows();
				// System.out.println(rowNum);

				List<TPatient> list = new ArrayList<TPatient>();
				for (String id : idArray) {
					TPatient patient = getPById(Long.valueOf(id));
					list.add(patient);
				}

				if (list != null && list.size() > 0) {
					for (int i = 0; i < list.size(); i++) {
						HSSFRow row = sht0.createRow((int) i + rowNum);
						TPatient patient = list.get(i);
						// 创建单元格，并设置值
						row.createCell((short) 0).setCellValue((long) patient.getId());
						row.createCell((short) 1).setCellValue(patient.getPatientId());
						row.createCell((short) 2).setCellValue(patient.getName());
						row.createCell((short) 3).setCellValue(patient.getSex());
						row.createCell((short) 4).setCellValue(patient.getAddress());
						row.createCell((short) 5).setCellValue(patient.getAge());
						row.createCell((short) 6).setCellValue(patient.getIsFinished());
						row.createCell((short) 7).setCellValue(patient.getMcName());
						row.createCell((short) 8).setCellValue(patient.getPersonType());
						row.createCell((short) 9).setCellValue(patient.getPhone());
						row.createCell((short) 10).setCellValue(patient.getLoginName());
						row.createCell((short) 11).setCellValue(patient.getLoginPassword());
						row.createCell((short) 12)
								.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(patient.getCreated()));
						row.createCell((short) 13)
								.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(patient.getUpdated()));
					}
				}
				FileOutputStream fout = new FileOutputStream(filename);
				wb0.write(fout);
				fout.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	// 导入excel文件
	public void importExcelInfo(MultipartFile file) throws Exception {
		List<TPatient> patientList = new ArrayList<TPatient>();
		// 打开Excel，读取文件内容
		HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
		// 获取第一个工作表
		HSSFSheet sheet = workbook.getSheetAt(0);

		// 获取sheet中最后一行行号
		int lastRowNum = sheet.getLastRowNum();

		// count用于记录id
		Long count = 0l;
		for (int i = 1; i <= lastRowNum; i++) {
			// 创建Patient对象，用户存储每一行数据的值
			TPatient patient = new TPatient();
			HSSFRow row = sheet.getRow(i);
			// 获取当前行最后单元格列号
			int lastCellNum = row.getLastCellNum();
			for (int j = 0; j < lastCellNum; j++) {
				HSSFCell cell = row.getCell(j);
				switch (j) {
				case 0:
					// patient.setId(Math.round(cell.getNumericCellValue()));
					TPatient lastPatient = this.getLastRecord();
					if (lastPatient != null) {
						if (count == 0) {
							count = lastPatient.getId() + 1;
						} else {
							count++;
						}
						patient.setId(count);
					} else {
						patient.setId(1l);
					}
					break;
				case 1:
					patient.setPatientId(cell.getStringCellValue());
					break;
				case 2:
					patient.setName(cell.getStringCellValue());
					break;
				case 3:
					patient.setSex(cell.getStringCellValue());
					break;
				case 4:
					patient.setAddress(cell.getStringCellValue());
					break;
				case 5:
					patient.setAge(new Double(cell.getNumericCellValue()).intValue());
					break;
				case 6:
					patient.setIsFinished(cell.getStringCellValue());
					break;
				case 7:
					patient.setMcName(cell.getStringCellValue());
					break;
				case 8:
					patient.setPersonType(cell.getStringCellValue());
					break;
				case 9:
					patient.setPhone(cell.getStringCellValue());
					break;
				case 10:
					patient.setLoginName(cell.getStringCellValue());
					break;
				case 11:
					patient.setLoginPassword(cell.getStringCellValue());
					break;
				case 12:
					patient.setCreated(new SimpleDateFormat("yyyyMMdd").parse(cell.getStringCellValue()));
					break;
				case 13:
					patient.setUpdated(new SimpleDateFormat("yyyyMMdd").parse(cell.getStringCellValue()));
					break;
				}
			}
			patientList.add(patient);
		}

		// 批量插入
		patientMapper.insertInfoBatch(patientList);

	}

	@Override
	public List<String> selectAllPatientIds() {
		List<TPatient> patientList = patientMapper.selectAllPatient();
		if (patientList.size() > 0) {
			List<String> ids = new ArrayList<String>();
			for (TPatient patient : patientList) {
				ids.add(patient.getPatientId());
			}
			return ids;

		}
		return null;
	}

	// 查询健康档案
	@Override
	public TPatient getSpecialHealthRecordByPId(String patientId) {
		// 通过patientId查询病人记录
		TPatientExample example0 = new TPatientExample();
		example0.createCriteria().andPatientIdEqualTo(patientId);
		List<TPatient> patientList = patientMapper.selectByExample(example0);
		if (patientList.size() > 0) {
			TPatient patient = patientList.get(0);
			// 通过patientId查询病人诊断记录
			TDiagnosisExample example = new TDiagnosisExample();
			example.createCriteria().andPatientIdEqualTo(patientId);
			List<TDiagnosis> diagnosisList = diagnosisMapper.selectByExample(example);
			if (diagnosisList.size() > 0) {
				// 通过patientId查询病人住院记录
				TDiagnosis diagnosis = diagnosisList.get(diagnosisList.size() - 1);
				patient.setDiagnosis(diagnosis);
				TStayHospitalExample sHExample = new TStayHospitalExample();
				sHExample.createCriteria().andPatientIdEqualTo(patientId);
				List<TStayHospital> stayHospitalList = stayHospitalMapper.selectByExample(sHExample);
				if (stayHospitalList.size() > 0) {
					TStayHospital stayHospital = stayHospitalList.get(stayHospitalList.size() - 1);
					patient.setStayHospital(stayHospital);
				}
			}
			System.out.println("patient=" + patient.toString());
			return patient;
		}
		return null;
	}

	/**
	 * 查询所有病人记录
	 */
	@Override
	public List<TPatient> selectAllPatient() {
		List<TPatient> patients = patientMapper.selectAllPatient();

		if (patients != null) {
			return patients;
		}

		return null;
	}

	/**
	 * 条件统计，根据开始时间，结束时间等查询病人的就诊情况
	 */
	@Override
	public List<TPatient> selectAllPatientByCondition(String beginTime, String endTime) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("beginTime", beginTime);
		map.put("endTime", endTime);

		List<TPatient> patientList = patientMapper.selectAllPatientByCondition(map);

		if (patientList.size() > 0) {
			return patientList;
		}
		return null;
	}

	// 通过病人人群类型分页查询病人及诊断信息
	@Override
	public List<TPatient> getPatientByPersonType(Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("personType", "正常");
		// 根据条件查询总数
		int totalNum = patientMapper.countByPersonType(map);
		System.out.println("totalNum=" + totalNum);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());
		return patientMapper.pageByPersonType(map);
	}

	// 通过病人姓名和人群类型分页查询病人及诊断信息
	@Override
	public List<TPatient> pageByPatientNameAndPersonType(String patientName, Page page) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", patientName);
		map.put("personType", "正常");
		// 根据条件查询总数
		int totalNum = patientMapper.countByPersonType(map);
		page.setTotalNumber(totalNum);
		// 组织分页查询总数数
		map.put("pageIndex", page.getDbIndex());
		map.put("pageSize", page.getDbNumber());

		return patientMapper.pageByPersonType(map);

	}

	@Override
	public List<String> getAllPatientId() {
		List<TPatient> patientList = patientMapper.selectAllPatient();
		List<String> patientIdList = new ArrayList<String>();
		if(patientList.size()>0){
			for (TPatient patient : patientList) {
				patientIdList.add(patient.getPatientId());
			}
		}
		return patientIdList;
	}

	//自动搜索匹配patientId
	@Override
	public String autoPatientId(String term, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");//解决乱码  
		StringBuffer sb = new StringBuffer("[");
		//通过输入的字符查询病人记录
		List<TPatient> pIsStrList = patientMapper.selectPatientByAutoComplete(term);
		 for(int i=0;i<pIsStrList.size();i++){  
             if(i==pIsStrList.size()-1){  
                 //注意在拼接的时候，要用双引号，单引号我试过，不起作用好像是,至少key必须是双引号，最好都写成双引号  
                 /*  
                  * 其中label属性用于显示在autocomplete弹出菜单，而value属性则是选中后给文本框赋的值。如果没有指定其中一个属性  
                  * 则用另一个属性替代(即value和label值一样)，如果label和value都没有指定，则无法用于autocomplete的提示。    
                  */  
                 sb.append("{\"lable\":\""+pIsStrList.get(i).getPatientId()+"\",\"value\":\""+pIsStrList.get(i).getPatientId()+"\"}]");  
             }else{  
                 sb.append("{\"lable\":\""+pIsStrList.get(i).getPatientId()+"\",\"value\":\""+pIsStrList.get(i).getPatientId()+"\"},");  
             }  
         }  
         return sb.toString();
	}

}
