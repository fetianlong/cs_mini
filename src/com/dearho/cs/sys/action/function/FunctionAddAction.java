package com.dearho.cs.sys.action.function;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Function;
import com.dearho.cs.sys.service.FunctionService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

public class FunctionAddAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private Function function;
	private FunctionService functionService;
	
	public FunctionAddAction() {
		super();
		function=new Function();
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
		functionService.add(function);
		result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, function.getId());
		return SUCCESS;
	}

}
