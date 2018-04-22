package cn.edu.dgut.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import cn.edu.dgut.pojo.Page;
import cn.edu.dgut.pojo.TSickbed;

public interface SickbedService {

	List<TSickbed> getAllSickbed(Page page);

	List<TSickbed> pageByCondition(String id, Page page);

	int deleteSickbedById(long id);

	int deleteSickbedByIds(String[] idArray);

	TSickbed getSickbedById(long id);

	int updateSickbedByTSickbed(TSickbed sickbed);

	int addSickbedByTSickbed(TSickbed sickbed);

	List<Long> getAllSickbedId();

	String autoSickbedId(String term, HttpServletResponse response);

}
