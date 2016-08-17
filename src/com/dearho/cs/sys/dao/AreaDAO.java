package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.AdministrativeArea;
/**
 * @author GaoYunpeng
 * @Description 行政区域
 * @version 1.0 2015年5月19日 下午3:22:01
 */
public interface AreaDAO {
	
	List<AdministrativeArea> searchAreaByCode(String code);
	
	List<AdministrativeArea> searchAreaByName(String name);
	
	void addArea(AdministrativeArea area);
	void updateArea(AdministrativeArea area);
	void deleteArea(final String[] checkdel);
	
	Page<AdministrativeArea> searchArea(Page<AdministrativeArea> page,String hql);
	
	List<AdministrativeArea> getAreasByParentCode(String parentCode);

	AdministrativeArea searchAreaById(String id);
}
