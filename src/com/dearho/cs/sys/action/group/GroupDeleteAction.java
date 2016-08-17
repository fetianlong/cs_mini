package com.dearho.cs.sys.action.group;

import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.GroupService;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

/**
 * @Author liusogn
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class GroupDeleteAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GroupService groupService;
	private UserService userService;
	
	private String[] checkdel;


	public GroupDeleteAction (){
		super();
	}
	
	public String process() {
		
		
		
		if(checkdel!=null&&checkdel.length>0){
			
			
			for (int i = 0; i < checkdel.length; i++) {
				List<User> list=userService.searchUserByGroupId(checkdel[i]);
				if(list!=null&&list.size()>0){
					Group group=groupService.searchGroupByID(checkdel[i]);
					result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败,"+group.getGroupName()+"有绑定用户，不能删除");
					return SUCCESS;
				}
			}
			groupService.deleteGroup(checkdel);
			
		}
		
		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功");
		
		return SUCCESS;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String[] getCheckdel() {
		return checkdel;
	}

	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
	}

	
	
	
	
}
