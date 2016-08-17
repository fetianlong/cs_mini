package com.dearho.cs.sys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.DictGroupDAO;
import com.dearho.cs.sys.pojo.DictGroup;
import com.dearho.cs.util.StringHelper;

public class DictGroupDAOImpl extends AbstractDaoSupport implements DictGroupDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<DictGroup> searchDictGroupByIdCode(String id, String code) {
		String hql = "from DictGroup r where 1=1 ";
		List<Object> paramObjs = new ArrayList<Object>();
		
		if(id != null){
			hql += " and r.id = ? ";
			paramObjs.add(id);
		}
		if(code != null){
			hql += "and r.code like '%"+code+"%'";
		}
		hql += " order by r.createTime desc ";
		return getHibernateTemplate().find(hql,paramObjs.toArray());
	}

	@Override
	public void addDictGroup(DictGroup dictGroup) {
		addEntity(dictGroup);;
	}

	@Override
	public void updateDictGroup(DictGroup dictGroup) {
		updateEntity(dictGroup);
	}

	@Override
	public void deleteDictGroup(final String[] checkdel) {
		final String queryString="delete DictGroup where id in (:ids)";
		deleteEntity(DictGroup.class, queryString, checkdel);
	}

	@Override
	public Page<DictGroup> searchDictGroup(Page<DictGroup> page, String hql) {
		Page<DictGroup> resultPage = pageCache(DictGroup.class ,page, hql);
		resultPage.setResults(idToObj(DictGroup.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public DictGroup getDictGroupByCode(String code) {
		if(StringHelper.isEmpty(code)){
			return null;
		}
		return get(DictGroup.class, (String)getQuery("select id from DictGroup where code = '"+code.trim()+"'").uniqueResult());
	}

	@Override
	public DictGroup getDictGroupById(String id) {
		if(StringHelper.isEmpty(id)){
			return null;
		}
		return get(DictGroup.class, (String)getQuery("select id from DictGroup where id = '"+id.trim()+"'").uniqueResult());
	}


}
