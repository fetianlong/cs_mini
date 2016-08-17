package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.GroupMenu;

/**   
 * @Description: 
 * @author liusong 
 * @date 2015-7-20
 * @version 1.0   
 */

public interface GroupMenuDAO {
	
	GroupMenu getGroupMenu(Integer groupMenuId);
	
	void addGroupMenu(GroupMenu groupMenu);
	
	void deleteGroupMenu(String[] checkdel);

	void updateGroupMenu(GroupMenu groupMenu);
	
	Page<GroupMenu> searchGroupMenu(Page<GroupMenu> page, String string);
	
	List<GroupMenu> searchGroupMenu(String hql);
	
	List<GroupMenu> searchGroupMenuAll(String hql);
	
	
}
