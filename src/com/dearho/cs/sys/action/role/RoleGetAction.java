package com.dearho.cs.sys.action.role;

import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Role;
import com.dearho.cs.sys.service.RoleService;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:31
 */
public class RoleGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private RoleService roleService;
	
	private Role rEntity;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			rEntity = new Role();
		}else{
			List<Role> eEntitys = roleService.searchRole(id, null);
			if (eEntitys == null || eEntitys.size() <= 0 ){
				rEntity = new Role();
			}
			else {
				rEntity = eEntitys.get(0);
			}
		}
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Role getrEntity() {
		return rEntity;
	}

	public void setrEntity(Role rEntity) {
		this.rEntity = rEntity;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
}
