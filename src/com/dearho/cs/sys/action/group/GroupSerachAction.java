package com.dearho.cs.sys.action.group;


import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.service.GroupService;

/**
 * @Author liusogn
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class GroupSerachAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GroupService groupService;
	
	private Group group;
	private Page<Group> page=new Page<Group>();
	public GroupSerachAction (){
		super();
		group=new Group();
		page.setCurrentPage(1);
		page.setCountField("a.groupId");
	}
	
	
	public String process() {
//		if(getPageNum() != null){
//			page.setCurrentPage(getPageNum());
//		}
//		if(getNumPerPage() != null){
//			page.setPageSize(getNumPerPage());
//		}
		page=groupService.searchGroup(page, group);
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


	public Page<Group> getPage() {
		return page;
	}


	public void setPage(Page<Group> page) {
		this.page = page;
	}

	

}
