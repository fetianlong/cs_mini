package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Group;

/**   
 * @Description: 
 * @author liusong 
 * @date 2015-7-20
 * @version 1.0   
 */

public interface GroupDAO {
	Group getGroupById(String groupId);
	
	void addGroup(Group group);
	
	void deleteGroup(String[] checkdel);

	void updateGroup(Group group);
	
	Page<Group> searchGroup(Page<Group> page, String string);
	
	public List<Group> searchGroup(String hql);
	
	
}
