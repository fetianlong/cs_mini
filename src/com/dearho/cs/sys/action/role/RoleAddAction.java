package com.dearho.cs.sys.action.role;

import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Role;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.RoleService;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:14:43
 */
public class RoleAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RoleService roleService;
	
	private Role rEntity;
	
	public RoleAddAction(){
		super();
		rEntity = new Role();
	}
	
	public String process(){
		try {
			Role role = roleService.getRoleByName(rEntity.getName());
			if (role != null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "角色已存在！");
				return Action.ERROR;
			}
			roleService.addRole(rEntity);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, rEntity.getId());
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "角色保存失败！");
			return Action.ERROR;
		}
	}
	

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public Role getrEntity() {
		return rEntity;
	}

	public void setrEntity(Role rEntity) {
		this.rEntity = rEntity;
	}


}
