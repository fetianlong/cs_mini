package com.dearho.cs.sys.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.UserDAO;
import com.dearho.cs.sys.pojo.User;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class UserDAOImpl extends AbstractDaoSupport implements UserDAO{
	public User login(User user){
		try{
			@SuppressWarnings("rawtypes")
			List list = getHibernateTemplate().find("from User u where name=? and password=?",new Object[]{user.getName(),user.getPassword()});
			return ((list == null || list.size() == 0) ? null : (User) list.get(0));
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> searchUser(String hql) {
		return getList(User.class, queryFList(hql));
	}
	
	@Override
	public List<User> searchUser(User user) {
		return getList(User.class, queryFList("select id from User where name like '%"+user.getName()+"%'"));
	}

	@Override
	public void addUser(User user) {
		addEntity(user);
	}


	@Override
	public User searchUserByID(String id) {
		return get(User.class, id);
	}

	@Override
	public User searchUserByName(String name) {
		return get(User.class, (String)getQuery("select id from User where loginName like '"+name+"'").uniqueResult());
	}

	@Override
	public void deleteUser(final String[] checkdel) {
		final String queryString="delete User where id in (:ids)";
		deleteEntity(User.class, queryString, checkdel);
	}

	@Override
	public void updateUser(User user) {
		updateEntity(user);
	}

	@Override
	public Page<User> searchUser(Page<User> page, String hql) {
		Page<User> resultPage = pageCache(User.class ,page, hql);
		resultPage.setResults(idToObj(User.class, resultPage.getmResults()));
		return resultPage;
	}
}
