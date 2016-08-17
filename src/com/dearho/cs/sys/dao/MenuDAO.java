package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Menu;

/**
 * @Author liusgon
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public interface MenuDAO {

	Menu getMenu(Integer menuId);
	
	void addMenu(Menu menu);
	
	void deleteMenu(String[] checkdel);

	void updateMenu(Menu menu);

	Menu getRootMenu();

	List<Menu> searchChildMenuBypid(Integer pid);
	
	public List<Menu> searchMenu(String hql);

	Page<Menu> queryMenuByPage(Page<Menu> page, String string);

	List<Menu> queryMenusByHql(String hql);
	
	List<Menu> searchMenuAll(String hql);

}
