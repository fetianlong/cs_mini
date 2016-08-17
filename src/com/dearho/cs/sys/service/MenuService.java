package com.dearho.cs.sys.service;

import java.util.List;
import java.util.Map;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Menu;

/**   
 * @Description: 
 * @author liusong 
 * @date 2015-7-17
 * @version 1.0   
 */

public interface MenuService {

	
	public List<Menu> searchChildMenuBypid(Integer pid,String userId);
	
	public Menu getRootMenu();
	

	void addMenu(Menu menu);
	void updateMenu(Menu menu);
	
	Page<Menu> queryMenuByPage(Page<Menu> page,Menu menu);
	
	Menu queryMenuById(Integer id);
	
	void deleteMenuByIds(String[] ids);

	public List<Menu> getMenusByPid(Integer pid);

	public List<Menu> getAllParentMenu();

	public Integer getMaxOrderNo(Integer pid);
	
	public List<Menu> getAllMenus() ;
	
	public Map<Integer,List<Menu>>  queryMenuPidListByGroupId(String groupid);
	

}
