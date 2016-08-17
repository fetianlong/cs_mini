package com.dearho.cs.sys.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.FunctionDao;
import com.dearho.cs.sys.pojo.Function;
import com.dearho.cs.sys.pojo.User;

public class FunctionDaoImpl extends AbstractDaoSupport implements FunctionDao {

	@Override
	public void add(Function function) {
		addEntity(function);

	}

	@Override
	public void update(Function function) {
		update(function);
	}

	@Override
	public void delete(Function function) {
		delete(function);
	}


	@Override
	public Function searchFunctionById(String id) {
		return get(Function.class, id);
	}

	@Override
	public Page<Function> searchFunction(Page<Function> page, String hql) {
		Page<Function> resultPage = pageCache(Function.class ,page, hql);
		resultPage.setResults(idToObj(Function.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public void deleteArray(String[] ids) {
		final String queryString="delete Function where id in (:ids)";
		deleteEntity(Function.class, queryString, ids);
		
	}

}
