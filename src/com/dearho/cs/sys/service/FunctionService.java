package com.dearho.cs.sys.service;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Function;

public interface FunctionService {
	void  add(Function function);
	
	void update(Function function);
	
	void delete(Function function);
	
	Function searchFunctionById(String id);
	
	Page<Function> searchFunction(Page<Function> page, Function fun);
	
	void deleteArrays(String[] ids);
}
