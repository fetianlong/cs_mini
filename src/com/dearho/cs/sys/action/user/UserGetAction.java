package com.dearho.cs.sys.action.user;

import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.GroupService;
import com.dearho.cs.sys.service.UserService;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class UserGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private UserService userService;
	private GroupService groupService;
	
	private User eEntity;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			eEntity = new User();
		}else{
			eEntity = userService.searchUserByID(id);
			if (eEntity == null) eEntity = new User();
		}
		
		List<Group> groupList=groupService.searchGroup(null);
		getRequest().setAttribute("groupList", groupList);
		
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User geteEntity() {
		return eEntity;
	}

	public void seteEntity(User eEntity) {
		this.eEntity = eEntity;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	
	
	
}
