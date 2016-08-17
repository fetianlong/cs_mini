package com.dearho.cs.sys.service.impl;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.UserDAO;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.util.Sha1Util;
import com.dearho.cs.util.StringHelper;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class UserServiceImpl implements UserService{
	
	private UserDAO userDAO;
	public User login(User user){
		return userDAO.login(user);
	}
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	@Override
	public List<User> searchUser() {
		String hql="select a.id from User a";
		return userDAO.searchUser(hql);
	}
	@Override
	public List<User> searchUser(User user) {
		return userDAO.searchUser(user);
	}
	@Override
	public void addUser(User user) {
		userDAO.addUser(user);
	}
	@Override
	public User searchUserByID(String id) {
		return userDAO.searchUserByID(id);
	}
	@Override
	public User searchUserByName(String name) {
		return userDAO.searchUserByName(name);
	}
	public User searchUserByLoginName(String name) {
		return userDAO.searchUserByName(name);
	}
	@Override
	public void deleteUser(String[] checkdel) {
		userDAO.deleteUser(checkdel);
	}
	@Override
	public void updateUser(User user) {
		userDAO.updateUser(user);
	}
	@Override
	public Page<User> searchUser(Page<User> page, User sEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.id from User a where 1 = 1 ");
		if (sEntity != null){
			if (StringHelper.isNotEmpty(sEntity.getName())){
				sb.append("and a.name like '%"+sEntity.getName()+"%'");
			}
			if (StringHelper.isNotEmpty(sEntity.getLoginName())){
				sb.append("and a.loginName like '%"+sEntity.getLoginName()+"%'");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		page = userDAO.searchUser(page, sb.toString());
		return page;
	}
	
	public void resetPassword(String userId){
		User user=userDAO.searchUserByID(userId);
		if(user!=null){
			user.setPassword(Sha1Util.SHA1Encode(User.defaultPassword));
			userDAO.updateUser(user);
		}
	}
	
	public List<User> searchUserByGroupId(String groupId){
		String hql="select a.id from User a where 1 = 1  and groupId ='"+groupId+"'";
		return userDAO.searchUser(hql);
	}
}
