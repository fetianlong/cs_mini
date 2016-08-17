package com.dearho.cs.sys.action.function;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.service.FunctionService;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

public class FunctionDeleteAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	private String[] checkdel;
	
	private FunctionService functionService;
	

	public String[] getCheckdel() {
		return checkdel;
	}

	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
	}

	public FunctionService getFunctionService() {
		return functionService;
	}

	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}

	@Override
	public String process() {
			try{
				functionService.deleteArrays(checkdel);
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "用户删除成功！");
			}catch(Exception e){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "用户删除失败！");
			}
		return SUCCESS;
	}

}
