package cn.edu.dgut.service;

import java.util.List;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TMedicalcourses;

public interface MedicalcoursesService {

	List<TMedicalcourses> getAllMedicalcourses(Page page);
	List<TMedicalcourses> pageByCondition(String name, Page page);
	int deleteMedicalcoursesById(long id);
	int deleteMedicalcoursesByIds(String[] idArray);
	TMedicalcourses getMedicalcoursesById(String id);
	int updateMedicalcoursesByTMedicalcourses(TMedicalcourses medicalcourses);
	int addMedicalcoursesByTMedicalcourses(TMedicalcourses medicalcourses);
	List<String> getAllMedicalcoursesName();
}
