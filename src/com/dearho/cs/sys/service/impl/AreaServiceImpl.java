package com.dearho.cs.sys.service.impl;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.AreaDAO;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.service.AreaService;
import com.dearho.cs.util.StringHelper;

public class AreaServiceImpl implements AreaService {
	
	private AreaDAO areaDAO;

	@Override
	public List<AdministrativeArea> searchAreaByCode(String code) {
		return areaDAO.searchAreaByCode(code);
	}

	@Override
	public List<AdministrativeArea> searchAreaByName(String name) {
		return areaDAO.searchAreaByName(name);
	}

	@Override
	public void addArea(AdministrativeArea area) {
		areaDAO.addArea(area);
	}

	@Override
	public void updateArea(AdministrativeArea area) {
		areaDAO.updateArea(area);
	}

	@Override
	public void deleteArea(String[] checkdel) {
		areaDAO.deleteArea(checkdel);
	}

	@Override
	public Page<AdministrativeArea> searchArea(Page<AdministrativeArea> page,
			AdministrativeArea area) {
		String hql = "select a.id from AdministrativeArea a where 1=1 ";
		if(area != null){
			if(StringHelper.isNotEmpty(area.getCode())){
				hql += " and a.code = '"+area.getCode()+"'";
			}
			if(StringHelper.isNotEmpty(area.getName())){
				hql += " and a.name like '%"+area.getName()+"%'";
			}
			if(StringHelper.isNotEmpty(area.getParentCode())){
				hql += " and a.parentCode = '"+area.getParentCode()+"'";
			}
		}
		hql += (StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		return areaDAO.searchArea(page, hql);
	}

	@Override
	public List<AdministrativeArea> getAreasByParentCode(String parentCode) {
		return areaDAO.getAreasByParentCode(parentCode);
	}

	@Override
	public AdministrativeArea searchAreaById(String id) {
		return areaDAO.searchAreaById(id);
	}

	public AreaDAO getAreaDAO() {
		return areaDAO;
	}

	public void setAreaDAO(AreaDAO areaDAO) {
		this.areaDAO = areaDAO;
	}

}
