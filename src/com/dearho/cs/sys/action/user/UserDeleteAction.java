package com.dearho.cs.sys.action.user;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.xwork.Action;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class UserDeleteAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] checkdel;
	
	private UserService userService;

	@Override
	public String process() {
		try {
			userService.deleteUser(checkdel);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "用户删除成功！");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！");
			return Action.ERROR;
		}
	}

	public String[] getCheckdel() {
		return checkdel;
	}

	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
