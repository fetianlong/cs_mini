package com.dearho.cs.sys.action.user;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.Sha1Util;

/**
 * @Author liusong
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class UserChangePasswordAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String newPassword;
	private String oldPassword;
	private UserService userService;
	
	
	
	@Override
	public String process() {
		User user=(User) getSession().getAttribute(Constants.SESSION_USER);
		user=userService.searchUserByID(user.getId());
		if(StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(oldPassword)){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "参数为空");
			return SUCCESS;
		}
		if (user.getPassword().equals(Sha1Util.SHA1Encode(oldPassword))) {
			user.setPassword(Sha1Util.SHA1Encode(newPassword));
			userService.updateUser(user);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "修改成功");
		}else{
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "旧密码不正确");
		}
		
		return SUCCESS;
	}
	
	public String resetPassword(){
		String id=getRequest().getParameter("id");
		if(StringUtils.isEmpty(id)){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "参数为空");
			return SUCCESS;
		}
		User  user=userService.searchUserByID(id);
		user.setPassword(Sha1Util.SHA1Encode(Constants.DEFAULT_PASSWORDS));
		userService.updateUser(user);
		result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "密码重置成功");
		return SUCCESS;
	}
	public String gotoChangePassword(){
		return SUCCESS;
	}
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	

	
	
}
