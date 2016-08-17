package com.dearho.cs.sys.action.function;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Function;
import com.dearho.cs.sys.service.FunctionService;
import com.opensymphony.xwork.Action;

public class FunctionSearchAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private FunctionService functionService;
	
	private Page<Function> page=new Page<Function>();
	
	private Function function;
	
	public FunctionSearchAction() {
		super();
		function=new Function();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	public Page<Function> getPage() {
		return page;
	}
	public void setPage(Page<Function> page) {
		this.page = page;
	}
	public Function getFunction() {
		return function;
	}
	public void setFunction(Function function) {
		this.function = function;
	}
	public FunctionService getFunctionService() {
		return functionService;
	}
	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}
	@Override
	public String process() {
		try {
			page = functionService.searchFunction(page, function);
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}

}
