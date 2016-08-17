package com.dearho.cs.sys.action.user;

import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.xwork.Action;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class UserUpdateAction extends UserAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String process(){
		try {
			User u=getUserService().searchUserByID(getEEntity().getId());
			u.setName(getEEntity().getName());
			u.setPhoneNo(getEEntity().getPhoneNo());
			u.setGroupId(getEEntity().getGroupId());
			getUserService().updateUser(u);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, getEEntity().getId());
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "用户更新失败！");
			return Action.ERROR;
		}
	}
}
