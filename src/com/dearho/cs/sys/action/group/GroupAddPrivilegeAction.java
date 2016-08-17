package com.dearho.cs.sys.action.group;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.GroupService;
import com.dearho.cs.sys.service.MenuService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

/**   
 * @Description: 
 * @author liusong 
 * @date 2015-7-20
 * @version 1.0   
 */

public class GroupAddPrivilegeAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private GroupService groupService;
	private MenuService menuService;
	
	
	private Group group;
	
	public GroupAddPrivilegeAction(){
		super();
		group=new Group();
	}
	@Override
	public String process() {
		
		Object userObj =getSession().getAttribute(Constants.SESSION_USER);
		if(userObj==null){
			return "login";
		}
		User user=(User)userObj;
		
		String  menuIds=getRequest().getParameter("privilegeIdS");
		if(menuIds==null){
			menuIds="";
		}
		String[] menuIdAtt = menuIds.split(",");
		groupService.addGroupMenu(group.getGroupId(),menuIdAtt,user.getId());
		
		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS," 授权成功!");
		
		
		return SUCCESS;
	}
	
	public GroupService getGroupService() {
		return groupService;
	}
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	public MenuService getMenuService() {
		return menuService;
	}
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	
	

}
