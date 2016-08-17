package com.dearho.cs.sys.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.AreaDAO;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.util.StringHelper;

public class AreaDAOImpl extends AbstractDaoSupport implements AreaDAO{

	@Override
	public List<AdministrativeArea> searchAreaByCode(String code) {
		String hql = "from AdministrativeArea r where 1=1 ";
		
		if(code != null){
			hql += "and r.code = '"+code+"'";
		}
		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<AdministrativeArea> searchAreaByName(String name) {
		String hql = "from AdministrativeArea r where 1=1 ";
		
		if(name != null){
			hql += "and r.name like '%"+name+"%'";
		}
		return getHibernateTemplate().find(hql);
	}
	
	@Override
	public void addArea(AdministrativeArea area) {
		addEntity(area);
	}

	@Override
	public void updateArea(AdministrativeArea area) {
		updateEntity(area);
	}

	@Override
	public void deleteArea(String[] checkdel) {
		final String queryString="delete AdministrativeArea where id in (:ids)";
		deleteEntity(AdministrativeArea.class, queryString, checkdel);
	}

	@Override
	public Page<AdministrativeArea> searchArea(Page<AdministrativeArea> page,
			String hql) {
		Page<AdministrativeArea> resultPage = pageCache(AdministrativeArea.class ,page, hql);
		resultPage.setResults(idToObj(AdministrativeArea.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public List<AdministrativeArea> getAreasByParentCode(String parentCode) {
		String hql = "from AdministrativeArea r ";
		if(StringHelper.isNotEmpty(parentCode)){
			hql += "where r.parentCode = '"+parentCode+"'";
		}
		else{
			hql += "where r.parentCode is null or r.parentCode = ''";
		}
		return getHibernateTemplate().find(hql);
	}

	@Override
	public AdministrativeArea searchAreaById(String id) {
		return get(AdministrativeArea.class, id);
	}

}
