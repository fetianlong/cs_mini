package com.dearho.cs.report.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.report.dao.IndexDataShowDAO;

public class IndexDataShowDAOImpl extends AbstractDaoSupport implements IndexDataShowDAO {

	@Override
	public Object executeQuery(String hql) {
		List<Object> objs = getHibernateTemplate().find(hql);
		if(objs != null && objs.size() > 0){
			return objs.get(0);
		}
		return 0;
	}

}
