package com.dearho.cs.sys.service.impl;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.RoleDAO;
import com.dearho.cs.sys.pojo.Role;
import com.dearho.cs.sys.service.RoleService;
import com.dearho.cs.util.StringHelper;

/**
 * @author GaoYunpeng
 * @Description 角色业务实现类
 * @version 1.0 2015年4月22日 上午9:57:01
 */
public class RoleServiceImpl implements RoleService {

	private RoleDAO roleDAO;
	
	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
	
	@Override
	public List<Role> searchRole(String roleId, String roleName) {
		return roleDAO.searchRole(roleId, roleName);
	}

	@Override
	public void addRole(Role role) {
		roleDAO.addRole(role);
	}

	@Override
	public void updateRole(Role role) {
		roleDAO.updateRole(role);
	}

	@Override
	public void deleteRole(String[] checkdel) {
		roleDAO.deleteRole(checkdel);
	}

	@Override
	public Page<Role> searchRole(Page<Role> page, Role roleEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.id from Role a where 1 = 1 ");
		if (roleEntity != null){
			if (StringHelper.isNotEmpty(roleEntity.getName())){
				sb.append("and a.name like '%"+roleEntity.getName()+"%'");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		page = roleDAO.searchRole(page, sb.toString());
		return page;
	}

	@Override
	public Role getRoleByName(String name) {
		return roleDAO.getRoleByName(name);
	}

}
