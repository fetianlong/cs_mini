package com.dearho.cs.sys.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.GroupDAO;
import com.dearho.cs.sys.dao.GroupMenuDAO;
import com.dearho.cs.sys.dao.MenuDAO;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.pojo.GroupMenu;
import com.dearho.cs.sys.pojo.Menu;
import com.dearho.cs.sys.service.GroupService;
import com.dearho.cs.util.StringHelper;

/**
 * @Author liusong
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class GroupServiceImpl implements GroupService{

	private GroupDAO groupDAO;
	private MenuDAO menuDAO;
	private GroupMenuDAO groupMenuDAO;
	@Override
	public void addGroup(Group group) {
		
		groupDAO.addGroup(group);
		
	}

	@Override
	public void deleteGroup(String[] checkdel) {
		groupDAO.deleteGroup(checkdel);
		
	}

	@Override
	public List<Group> searchGroup(Group Group) {
		StringBuilder hql = new StringBuilder();
		hql.append("select a.groupId from Group a where 1 = 1 order by a.groupCreateTime asc ");
		return groupDAO.searchGroup(hql.toString());
	}
	

	@Override
	public Page<Group> searchGroup(Page<Group> page, Group group) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.groupId from Group a where 1 = 1 ");
		if (group != null){
			if(!StringUtils.isEmpty(group.getGroupName())){
				sb.append(" and groupName like '%"+group.getGroupName()+"%' ");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "order by groupCreateTime asc" : page.getOrderByString());
		page = groupDAO.searchGroup(page, sb.toString());
		return page;
	}

	@Override
	public Group searchGroupByID(String groupId) {
		return groupDAO.getGroupById(groupId);
	}

	@Override
	public void updateGroup(Group group) {
		groupDAO.updateGroup(group);
		
	}

	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

	@Override
	public void addGroupMenu(String groupId, String[] menuIdAtt,String creatorId) {
		String hql="select id  from GroupMenu a where 1 = 1 and groupId= '"+groupId+"'";
		List<GroupMenu> groupMenuList=groupMenuDAO.searchGroupMenu(hql);
		if(groupMenuList!=null && groupMenuList.size()>0){
			String[] checkdel=new String[groupMenuList.size()];
			for(int i=0;i<groupMenuList.size();i++){
				checkdel[i]=groupMenuList.get(i).getId();
			}
			groupMenuDAO.deleteGroupMenu(checkdel);
			
		}
		
		if(menuIdAtt!=null && menuIdAtt.length>0){
			for(int j=0;j<menuIdAtt.length;j++){
				GroupMenu groupMenu=new GroupMenu();
				groupMenu.setGroupId(groupId);
				groupMenu.setCreatorId(creatorId);
				groupMenu.setMenuId(menuIdAtt[j]);
				groupMenu.setTs(new Date());
				groupMenuDAO.addGroupMenu(groupMenu);
			}
		}
		
	}
	
	
	public List<Menu> getAllMenu(){
		String hql=" from Menu a ";
		return menuDAO.searchMenuAll(hql);
	}

	public MenuDAO getMenuDAO() {
		return menuDAO;
	}

	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	public GroupMenuDAO getGroupMenuDAO() {
		return groupMenuDAO;
	}

	public void setGroupMenuDAO(GroupMenuDAO groupMenuDAO) {
		this.groupMenuDAO = groupMenuDAO;
	}

	@Override
	public List<GroupMenu> getGroupMenuByGroupId(String groupId) {
		String hql="select id from GroupMenu a where 1 = 1 and groupId= '"+groupId+"'";
		return groupMenuDAO.searchGroupMenu(hql);
	}

	@Override
	public Boolean hasPermission(String url, String groupId) {
		String hql="select id from GroupMenu a where 1 = 1 and groupId= '"+groupId+"'  order by menuId asc";
		List<GroupMenu> groupMenuList= groupMenuDAO.searchGroupMenu(hql);
		if(groupMenuList==null || groupMenuList.size()==0){
			return false;
		}
		StringBuilder menuIds = new StringBuilder();
		for(GroupMenu groupMenu:groupMenuList){
			menuIds.append(groupMenu.getMenuId()).append(",");
		}
		String queryMenuIds="-100";
		if(menuIds.length()>0){
			queryMenuIds=menuIds.substring(0, menuIds.length()-1);
		}
		
		String menuHql="select menuId from Menu a where 1 = 1 and menuId in ("+queryMenuIds+") and menuUrl='"+url+"'";
		List<Menu> menuList=menuDAO.searchMenu(menuHql);
		if(menuList==null || menuList.size()==0){
			return false;
		}else{
			return true;
		}
	}
	
	
	public List<String>  getUserPermissionByGroupId(String groupId){
		List<String> permissionList = new ArrayList<String>();
		if(StringUtils.isEmpty(groupId)){
			return permissionList;
		}

		String hql=" from GroupMenu a where 1 = 1 and groupId= '"+groupId+"'  order by menuId asc";
		List<GroupMenu> groupMenuList= groupMenuDAO.searchGroupMenuAll(hql);
		if(groupMenuList==null || groupMenuList.size()==0){
			return permissionList;
		}
		StringBuilder menuIds = new StringBuilder();
		for(GroupMenu groupMenu:groupMenuList){
			menuIds.append(groupMenu.getMenuId()).append(",");
		}
		String queryMenuIds="-100";
		if(menuIds.length()>0){
			queryMenuIds=menuIds.substring(0, menuIds.length()-1);
		}
		
		String menuHql=" from Menu a where 1 = 1 and menuId in ("+queryMenuIds+")  ";
		List<Menu> menuList=menuDAO.searchMenuAll(menuHql);
		
		if(menuList==null || menuList.size()==0){
			return permissionList;
		}
		
		for(Menu menu:menuList){
			permissionList.add(menu.getMenuUrl());
		}
		return permissionList;
	}
	
	
	
	
}
