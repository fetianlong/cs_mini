package com.dearho.cs.sys.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.GroupMenuDAO;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.pojo.GroupMenu;

/**   
 * @Description: 
 * @author liusong 
 * @date 2015-7-20
 * @version 1.0   
 */

public class GroupMenuDAOImpl extends AbstractDaoSupport implements
		GroupMenuDAO {

	@Override
	public void addGroupMenu(GroupMenu groupMenu) {
		addEntity(groupMenu);

	}

	@Override
	public void deleteGroupMenu(String[] checkdel) {
		final String queryString="delete GroupMenu where id in (:ids)";
		deleteEntity(GroupMenu.class, queryString, checkdel);

	}

	@Override
	public GroupMenu getGroupMenu(Integer groupMenuId) {
		
		return get(GroupMenu.class, groupMenuId);
	}

	@Override
	public Page<GroupMenu> searchGroupMenu(Page<GroupMenu> page, String hql) {
		Page<GroupMenu> resultPage=pageCache(GroupMenu.class, page, hql);
		
		resultPage.setResults(idToObj(GroupMenu.class, resultPage.getmResults()));
		/*if(resultPage.getResults()!=null){
			for(int i=0;i<resultPage.getResults().size();i++){
				
			}
		}*/
		return resultPage;
	}

	@Override
	public void updateGroupMenu(GroupMenu groupMenu) {
		updateEntity(groupMenu);
	}
	
	public List<GroupMenu> searchGroupMenu(String hql){
		
		return getList(GroupMenu.class, queryFList( hql));
				
	}
	
	
	public List<GroupMenu> searchGroupMenuAll(String hql){
		
		List<GroupMenu> list = this.getHibernateTemplate().find(hql);
		
		if(!Hibernate.isInitialized(list)){
			
			Hibernate.initialize(list);
		}
		return list;
				
				//return getList(GroupMenu.class, queryFList( hql));
	}
	
	
	public List<GroupMenu> queryGroupMenu(String hql){
		return getHibernateTemplate().find(hql);
	}

}
