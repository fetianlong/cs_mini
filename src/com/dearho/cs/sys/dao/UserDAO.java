package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.User;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public interface UserDAO {
	User login(User user);
	
	List<User> searchUser(String params);
	
	List<User> searchUser(User user);

	void addUser(User user);

	User searchUserByID(String id);

	User searchUserByName(String name);

	void deleteUser(String[] checkdel);

	void updateUser(User user);

	Page<User> searchUser(Page<User> page, String string);
}
