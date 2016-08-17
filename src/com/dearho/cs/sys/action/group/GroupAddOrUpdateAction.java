package com.dearho.cs.sys.action.group;

import java.util.Date;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.GroupService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

/**   
 * @Description: 
 * @author liusong 
 * @date 2015-7-20
 * @version 1.0   
 */

public class GroupAddOrUpdateAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GroupService groupService;
	
	private Group group;
	public GroupAddOrUpdateAction(){
		super();
		group=new Group();
	}

	@Override
	public String process() {
		
		Object obj=getSession().getAttribute(Constants.SESSION_USER);
		if(obj==null){
			return "login";
		}
		
		if(StringUtils.isEmpty(group.getGroupId())){
			group.setTs(new Date());
			group.setGroupCreateTime(new Date());
			group.setGroupCreatorId(((User)obj).getId());
			groupService.addGroup(group);
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"添加成功!");
		}else{
			Group g=groupService.searchGroupByID(group.getGroupId());
			g.setGroupName(group.getGroupName());
			g.setGroupRemark(group.getGroupRemark());
			g.setTs(new Date());
			groupService.updateGroup(g);
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功!");
		}
	
		return SUCCESS;
	
	}

	public GroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	

}
