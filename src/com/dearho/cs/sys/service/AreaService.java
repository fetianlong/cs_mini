package com.dearho.cs.sys.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.AdministrativeArea;

/**
 * @author GaoYunpeng
 * @Description 行政区划
 * @version 1.0 2015年5月19日 下午3:19:44
 */
public interface AreaService {
	
	/**
	 * @Title:searchAreaByCode
	 * @Description:根据编码查询行政区域，参数可以为空，则查询所有。
	 * @param code
	 * @return
	 * @throws
	 */
	List<AdministrativeArea> searchAreaByCode(String code);
	
	/**
	 * @Title:searchAreaByName
	 * @Description:根据名称模糊查询行政区域，参数可以为空，则查询所有。
	 * @param name
	 * @return
	 * @throws
	 */
	List<AdministrativeArea> searchAreaByName(String name);
	
	void addArea(AdministrativeArea area);
	void updateArea(AdministrativeArea area);
	void deleteArea(final String[] checkdel);
	
	Page<AdministrativeArea> searchArea(Page<AdministrativeArea> page,AdministrativeArea area);

	/**
	 * @Title:getAreasByParentCode
	 * @Description:根据父级编码查询行政区域，参数可以为空，则查询所有
	 * @param parentCode
	 * @return
	 * @throws
	 */
	List<AdministrativeArea> getAreasByParentCode(String parentCode);
	
	AdministrativeArea searchAreaById(String id);
}
