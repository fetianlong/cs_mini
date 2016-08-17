package com.dearho.cs.sys.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.DictGroup;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年5月19日 下午3:19:44
 */
public interface DictGroupService {
	List<DictGroup> searchDictGroupByIdCode(String id,String code);
	
	void addDictGroup(DictGroup dictGroup);
	
	void updateDictGroup(DictGroup dictGroup);
	void deleteDictGroup(final String[] checkdel);
	
	Page<DictGroup> searchDictGroup(Page<DictGroup> page,DictGroup dictGroup);

	DictGroup getDictGroupByCode(String code);
	
	DictGroup getDictGroupById(String id);

	void deleteDictGroupById(String id);
	
}
