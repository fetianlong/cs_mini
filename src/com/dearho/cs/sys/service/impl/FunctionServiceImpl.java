package com.dearho.cs.sys.service.impl;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.FunctionDao;
import com.dearho.cs.sys.pojo.Function;
import com.dearho.cs.sys.service.FunctionService;
import com.dearho.cs.util.StringHelper;

public class FunctionServiceImpl implements FunctionService {
	private FunctionDao functionDao;
	
	public void setFunctionDao(FunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	@Override
	public void add(Function function) {
		functionDao.add(function);

	}

	@Override
	public void update(Function function) {
		functionDao.update(function);

	}

	@Override
	public void delete(Function function) {
		functionDao.delete(function);

	}

	@Override
	public Function searchFunctionById(String id) {
		
		return functionDao.searchFunctionById(id);
	}

	@Override
	public Page<Function> searchFunction(Page<Function> page, Function fun) {
		
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Function a where 1=1");
		if(fun!=null){
			if (StringHelper.isNotEmpty(fun.getName())){
				sb.append("and a.name like '%"+fun.getName()+"%'");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		page = functionDao.searchFunction(page, sb.toString());
		return page;
	}

	@Override
	public void deleteArrays(String[] ids) {
		functionDao.deleteArray(ids);
		
	}

}
