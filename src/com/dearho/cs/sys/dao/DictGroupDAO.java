package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.DictGroup;
/**
 * @author GaoYunpeng
 * @Description 数据字典组
 * @version 1.0 2015年5月19日 下午3:22:01
 */
public interface DictGroupDAO {
	List<DictGroup> searchDictGroupByIdCode(String id,String code);
	void addDictGroup(DictGroup dictGroup);
	void updateDictGroup(DictGroup dictGroup);
	void deleteDictGroup(final String[] checkdel);
	Page<DictGroup> searchDictGroup(Page<DictGroup> page,String hql);
	DictGroup getDictGroupByCode(String code);
	DictGroup getDictGroupById(String id);
}
