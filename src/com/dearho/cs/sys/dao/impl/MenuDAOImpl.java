package com.dearho.cs.sys.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.MenuDAO;
import com.dearho.cs.sys.pojo.Menu;

/**   
 * @Title: MenuDAOImpl.java 
 * @Description: 
 * @author liusong 
 * @date 2015-7-17 上午10:18:29 
 * @version V1.0   
 */

public class MenuDAOImpl extends AbstractDaoSupport implements MenuDAO {

	@Override
	public void addMenu(Menu menu) {
		addEntity(menu);
	}

	@Override
	public void deleteMenu(String[] ids) {
		final String queryString="delete Menu where menuId in (:ids)";
		Integer[] idints = new Integer[ids.length];
		for (int i = 0; i < ids.length; i++ ) {
			idints[i] = Integer.parseInt(ids[i]);
		}
		
		deleteEntity(Menu.class, queryString, idints);
		
	}

	@Override
	public Menu getRootMenu() {
		return get(Menu.class, (Integer)getQuery("select menuId from Menu where menuPid = -1 ").uniqueResult());
	}

	@Override
	public List<Menu> searchChildMenuBypid(Integer pid) {
		return getList(Menu.class, queryFList("select menuId from Menu where menuPid = "+pid+""));
	}

	@Override
	public void updateMenu(Menu menu) {
		updateEntity(menu);
		
	}

	@Override
	public Menu getMenu(Integer menuId) {
		return get(Menu.class, menuId);
	}
	
	public List<Menu> searchMenu(String hql){
		return getList(Menu.class, queryFList( hql));
	}
	
	public List<Menu> searchMenuAll(String hql){
		List<Menu> list = this.getHibernateTemplate().find(hql);
		if(!Hibernate.isInitialized(list)){
			Hibernate.initialize(list);
		}
		return list;
	}

	@Override
	public Page<Menu> queryMenuByPage(Page<Menu> page, String hql) {
		Page<Menu> resultPage=pageCache(Menu.class, page, hql);
		resultPage.setResults(idToObj(Menu.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public List<Menu> queryMenusByHql(String hql) {
		return getHibernateTemplate().find(hql);
	}

}
