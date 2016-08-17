package com.dearho.cs.sys.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.User;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public interface UserService {
	public User login(User user);
	
	public List<User> searchUser();
	
	public List<User> searchUser(User user);

	public void addUser(User user);

	public User searchUserByID(String id);

	public User searchUserByName(String name);

	public void deleteUser(String[] checkdel);

	public void updateUser(User user);

	public Page<User> searchUser(Page<User> page, User sEntity);
	
	public List<User> searchUserByGroupId(String groupId);
	
	public void resetPassword(String userId);
	
	public User searchUserByLoginName(String name) ;
}
