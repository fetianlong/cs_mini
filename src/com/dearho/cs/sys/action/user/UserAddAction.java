package com.dearho.cs.sys.action.user;

import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.Sha1Util;
import com.opensymphony.xwork.Action;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class UserAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	
	private User eEntity;
	
	private List<User> recordList;
	
	public UserAddAction(){
		super();
		eEntity = new User();
	}
	
	public String process(){
		try {
			User user = userService.searchUserByName(eEntity.getLoginName());
			if (user != null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "用户已存在！");
				return Action.ERROR;
			}
			eEntity.setPassword(Sha1Util.SHA1Encode(Constants.DEFAULT_PASSWORDS));
			userService.addUser(eEntity);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, eEntity.getId());
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "用户保存失败！");
			return Action.ERROR;
		}
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<User> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<User> recordList) {
		this.recordList = recordList;
	}

	public User getEEntity() {
		return eEntity;
	}

	public void setEEntity(User eEntity) {
		this.eEntity = eEntity;
	}

}
