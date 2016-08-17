package com.dearho.cs.sys.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.GroupDAO;
import com.dearho.cs.sys.pojo.Group;

/**   
 * @Description: 
 * @author liusong 
 * @date 2015-7-20
 * @version 1.0   
 */

public class GroupDAOImpl  extends AbstractDaoSupport implements GroupDAO {

	@Override
	public void addGroup(Group group) {
		addEntity(group);

	}

	@Override
	public void deleteGroup(String[] checkdel) {
		final String queryString="delete Group where groupId in (:ids)";
		deleteEntity(Group.class, queryString, checkdel);

	}

	@Override
	public Group getGroupById(String groupId) {
		return get(Group.class, groupId);
	}

	@Override
	public Page<Group> searchGroup(Page<Group> page, String hql) {
		Page<Group> resultPage=pageCache(Group.class, page, hql);
		
		resultPage.setResults(idToObj(Group.class, resultPage.getmResults()));
		/*if(resultPage.getResults()!=null){
			for(int i=0;i<resultPage.getResults().size();i++){
				
			}
		}*/
		return resultPage;
	}

	@Override
	public void updateGroup(Group group) {
		updateEntity(group);
	}
	
	public List<Group> searchGroup(String hql){
		return getList(Group.class, queryFList( hql));
	}

}
