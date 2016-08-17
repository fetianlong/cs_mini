package com.dearho.cs.sys.dao;


import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Function;

public interface FunctionDao {
	
	void  add(Function function);
	
	void update(Function function);
	
	void delete(Function function);
	
	void deleteArray(String[] ids);
	Function searchFunctionById(String id);
	
	Page<Function> searchFunction(Page<Function> page, String hql);
}
