package com.dearho.cs.sys.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.RoleDAO;
import com.dearho.cs.sys.pojo.Role;
import com.dearho.cs.sys.pojo.User;

public class RoleDAOImpl extends AbstractDaoSupport implements RoleDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> searchRole(String roleId, String roleName) {
		String hql = "from Role r where 1=1 ";
		List<Object> paramObjs = new ArrayList<Object>();
		
		if(roleId != null){
			hql += " and r.id = ? ";
			paramObjs.add(roleId);
		}
		if(roleName != null){
			hql += "and r.name like '%"+roleName+"%'";
		}
		return getHibernateTemplate().find(hql,paramObjs.toArray());
	}

	@Override
	public void addRole(Role role) {
		addEntity(role);;
	}

	@Override
	public void updateRole(Role role) {
		updateEntity(role);
	}

	@Override
	public void deleteRole(final String[] checkdel) {
		final String queryString="delete Role where id in (:ids)";
		deleteEntity(Role.class, queryString, checkdel);
	}

	@Override
	public Page<Role> searchRole(Page<Role> page, String hql) {
		Page<Role> resultPage = pageCache(Role.class ,page, hql);
		resultPage.setResults(idToObj(Role.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public Role getRoleByName(String name) {
		return get(Role.class, (String)getQuery("select id from Role where name like '"+name+"'").uniqueResult());
	}

}
