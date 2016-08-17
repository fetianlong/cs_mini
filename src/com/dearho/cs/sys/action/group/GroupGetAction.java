package com.dearho.cs.sys.action.group;



import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.service.GroupService;

/**
 * @Author liusogn
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class GroupGetAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GroupService groupService;
	
	private Group group;
	
	public GroupGetAction(){
		super();
		group=new Group();
	}
	
	public String process() {
		//修改
		if(!StringUtils.isEmpty(group.getGroupId())){
			group=groupService.searchGroupByID(group.getGroupId());
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
