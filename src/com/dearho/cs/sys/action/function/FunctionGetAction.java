package com.dearho.cs.sys.action.function;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Function;
import com.dearho.cs.sys.service.FunctionService;

public class FunctionGetAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	
	private FunctionService functionService;
	
	private String id;
	
	private Function function;
	
	public FunctionService getFunctionService() {
		return functionService;
	}

	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	@Override
	public String process() {
		if(id==null || id.trim().equals("")){
			function=new Function();
		}else{
			function=functionService.searchFunctionById(id);
			if(function==null){
				function=new Function();
			}
		}
		return SUCCESS;
	}

}
