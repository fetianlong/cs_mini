package com.dearho.cs.sys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.DictDAO;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.util.StringHelper;

public class DictDAOImpl extends AbstractDaoSupport implements DictDAO {

	@SuppressWarnings("unchecked")
	@Override
	public Dict searchDictById(String id) {
		if(StringHelper.isEmpty(id)){
			return null;
		}
		String hql = "from Dict r where r.id = '"+ id +"'";
		List<Dict> dicts = getHibernateTemplate().find(hql);
		if(dicts != null && dicts.size() > 0){
			return dicts.get(0);
		}
		return null;
	}

	@Override
	public void addDict(Dict dict) {
		addEntity(dict);;
	}

	@Override
	public void updateDict(Dict dict) {
		updateEntity(dict);
	}

	@Override
	public void deleteDict(final String[] checkdel) {
		final String queryString="delete Dict where id in (:ids)";
		deleteEntity(Dict.class, queryString, checkdel);
	}

	@Override
	public Page<Dict> searchDict(Page<Dict> page, String hql) {
		Page<Dict> resultPage = pageCache(Dict.class ,page, hql);
		resultPage.setResults(idToObj(Dict.class, resultPage.getmResults()));
		return resultPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dict> getDictsByGroupId(String groupId) {
		String hql = "from Dict r ";
		if(StringHelper.isNotEmpty(groupId)){
			hql += "where r.groupId = '"+groupId+"'";
		}
		return getHibernateTemplate().find(hql);
	}

	@Override
	public Dict getDictByGroupIdAndDictCode(String gourpId, String dictCode) {
		 return get(Dict.class, (String)getQuery("select id from Dict where groupId = '"+gourpId+"' and code = '"+dictCode+"'").uniqueResult());
	}

}
