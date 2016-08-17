package com.dearho.cs.sys.action.role;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Role;
import com.dearho.cs.sys.service.RoleService;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class RoleSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RoleService roleService;
	
	private Page<Role> page = new Page<Role>();
	
	private Role rEntity;
	
	private List<Role> recordList;
	
	public RoleSearchAction(){
		super();
		rEntity = new Role();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			page = roleService.searchRole(page, rEntity);
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public Page<Role> getPage() {
		return page;
	}

	public void setPage(Page<Role> page) {
		this.page = page;
	}

	public Role getrEntity() {
		return rEntity;
	}

	public void setrEntity(Role rEntity) {
		this.rEntity = rEntity;
	}

	public List<Role> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<Role> recordList) {
		this.recordList = recordList;
	}
	
	
	
}
