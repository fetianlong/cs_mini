package com.dearho.cs.sys.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.pojo.GroupMenu;
import com.dearho.cs.sys.pojo.Menu;

/**
 * @Author liusong
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public interface GroupService {
	
	

	
	public List<Group> searchGroup(Group Group);

	public void addGroup(Group Group);

	public Group searchGroupByID(String id);

	public void deleteGroup(String[] checkdel);

	public void updateGroup(Group Group);

	public Page<Group> searchGroup(Page<Group> page, Group group);

	public void addGroupMenu(String groupId, String[] menuIdAtt,String creatorId);

	public List<GroupMenu> getGroupMenuByGroupId(String groupId);
	
	public List<Menu> getAllMenu();

	public Boolean hasPermission(String url, String groupId);
	
	public List<String>  getUserPermissionByGroupId(String groupId);
	
}
