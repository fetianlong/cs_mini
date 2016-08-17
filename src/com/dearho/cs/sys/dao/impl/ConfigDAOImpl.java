package com.dearho.cs.sys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.ConfigDAO;
import com.dearho.cs.sys.pojo.Config;
import com.dearho.cs.util.StringHelper;

public class ConfigDAOImpl extends AbstractDaoSupport implements ConfigDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Config> searchConfig(String id, String name) {
		String hql = "from Config r where 1=1 ";
		List<Object> paramObjs = new ArrayList<Object>();
		
		if(id != null){
			hql += " and r.id = ? ";
			paramObjs.add(id);
		}
		if(name != null){
			hql += "and r.name like '%"+name+"%'";
		}
		return getHibernateTemplate().find(hql,paramObjs.toArray());
	}

	@Override
	public void addConfig(Config config) {
		addEntity(config);;
	}

	@Override
	public void updateConfig(Config config) {
		updateEntity(config);
	}

	@Override
	public void deleteConfig(final String[] checkdel) {
		final String queryString="delete Config where id in (:ids)";
		deleteEntity(Config.class, queryString, checkdel);
	}

	@Override
	public Page<Config> searchConfig(Page<Config> page, String hql) {
		Page<Config> resultPage = pageCache(Config.class ,page, hql);
		resultPage.setResults(idToObj(Config.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public Config getConfigByCode(String code) {
		if(StringHelper.isEmpty(code)){
			return null;
		}
		return get(Config.class, (String)getQuery("select id from Config where code = '"+code.trim()+"'").uniqueResult());
	}


}
