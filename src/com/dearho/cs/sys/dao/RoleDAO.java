package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Role;

/**
 * @author GaoYunpeng
 * 角色数据持久类
 * 
 */
public interface RoleDAO {

	/**
	 * 根据roleId或roleName来查询角色，或者查询全部
	 * @param roleId
	 * @param roleName
	 * @return
	 * 
	 */
	List<Role> searchRole(String roleId,String roleName);
	
	/**
	 * 新增角色
	 * @param role
	 * @return
	 */
	void addRole(Role role);
	
	/**
	 * 更新角色，带ID
	 * @param role
	 * @return
	 */
	void updateRole(Role role);
	
	/**
	 * 删除角色，带ID
	 * @param checkdel
	 * @return
	 */
	void deleteRole(final String[] checkdel);
	
	/**
	 * 分页查询
	 * @param page
	 * @param hql
	 * @return
	 */
	Page<Role> searchRole(Page<Role> page,String hql);

	Role getRoleByName(String name);
}
