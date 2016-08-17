package com.dearho.cs.sys.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.MenuDAO;
import com.dearho.cs.sys.pojo.Menu;
import com.dearho.cs.sys.service.MenuService;

/**
 * @Author liusong
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-7-17
 */
public class MenuServiceImpl implements MenuService{
	
	private MenuDAO menuDAO;

	@Override
	public List<Menu> searchChildMenuBypid(Integer pid,String userId) {
		if(pid==null){
			return null;
		}else{
			return menuDAO.searchChildMenuBypid(pid);
		}
		
		
	}

	public Menu getRootMenu(){
		return menuDAO.getRootMenu();
	}
	public MenuDAO getMenuDAO() {
		return menuDAO;
	}

	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	@Override
	public void addMenu(Menu menu) {
		menuDAO.addMenu(menu);
	}

	@Override
	public void updateMenu(Menu menu) {
		menuDAO.updateMenu(menu);
	}

	@Override
	public Page<Menu> queryMenuByPage(Page<Menu> page, Menu menu) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Menu a where 1=1 ");
		if(menu != null){
			if(menu.getMenuPid() != null){
				sb.append(" and a.menuPid = " ).append(menu.getMenuPid());
			}
			if(!StringUtils.isEmpty(menu.getMenuName())){
				sb.append(" and a.menuName like '%" ).append(menu.getMenuName()).append("%' ");
			}
			
		}
		sb.append("  order by a.menuPid asc, menuOrder asc");
		page = menuDAO.queryMenuByPage(page, sb.toString());
		return page;
	}

	@Override
	public Menu queryMenuById(Integer id) {
		return menuDAO.getMenu(id);
	}

	@Override
	public void deleteMenuByIds(String[] ids) {
		menuDAO.deleteMenu(ids);
	}

	@Override
	public List<Menu> getMenusByPid(Integer pid) {
		String hql = " from Menu where menuPid = "+ pid;
		return menuDAO.queryMenusByHql(hql);
	}
	
	public List<Menu> getAllMenus() {
		String hql = " from Menu order by menuPid asc ,menuOrder asc";
		return menuDAO.queryMenusByHql(hql);
	}

	@Override
	public List<Menu> getAllParentMenu() {
		String hql = " from Menu where menuType='M'";
		return menuDAO.queryMenusByHql(hql);
	}

	@Override
	public Integer getMaxOrderNo(Integer pid) {
		String hql = " from Menu a where a.menuPid = "+pid+" order by a.menuOrder desc";
		List<Menu> vbs = menuDAO.queryMenusByHql(hql);
		if(vbs != null && vbs.size() > 0){
			return vbs.get(0).getMenuOrder();
		}
		return 0;
	}
	
	public Map<Integer,List<Menu>> queryMenuPidListByGroupId(String groupid) {
		String hql = " from Menu where menuType='M' and menuId in (select menuId from GroupMenu where groupId = '"+groupid+"')";
		List<Menu> menuList=menuDAO.queryMenusByHql(hql);
		Map<Integer,List<Menu>> resultMap=new HashMap<Integer,List<Menu>>();
		if(menuList==null){
			return null;
		}
		for(Menu menu:menuList){
			if(resultMap.get(menu.getMenuPid())==null){
				List<Menu> list=new ArrayList<Menu>();
				list.add(menu);
				resultMap.put(menu.getMenuPid(), list);
			}else{
				List<Menu> list=resultMap.get(menu.getMenuPid());
				list.add(menu);
				resultMap.put(menu.getMenuPid(), list);
			}
		}
		return resultMap;
	}
	
	
}
