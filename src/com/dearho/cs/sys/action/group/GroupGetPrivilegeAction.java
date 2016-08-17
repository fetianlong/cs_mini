package com.dearho.cs.sys.action.group;

import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Group;
import com.dearho.cs.sys.pojo.GroupMenu;
import com.dearho.cs.sys.pojo.Menu;
import com.dearho.cs.sys.service.GroupService;
import com.dearho.cs.sys.service.MenuService;
import com.dearho.cs.sys.vo.MenuVO;
import com.dearho.cs.util.Ajax;

/**   
 * @Description: 
 * @author liusong 
 * @date 2015-7-20
 * @version 1.0   
 */

public class GroupGetPrivilegeAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	private GroupService groupService;
	private MenuService menuService;
	
	private Group group;
	public GroupGetPrivilegeAction(){
		super();
		group = new Group();
	}
	@Override
	public String process() {
		
		
	
		
		List<GroupMenu> list=groupService.getGroupMenuByGroupId(group.getGroupId());
		List<String> hasMenuList=new ArrayList<String>();
		if(list !=null && list.size()>0){
			for(GroupMenu groupMenu:list ){
				hasMenuList.add(groupMenu.getMenuId());
			}
		}
		
		
		
		StringBuffer json= new StringBuffer();
		json.append("[ ");
		
		List<Menu> allMenuList=menuService.getAllMenus();
		for(Menu menu:allMenuList){
			json.append("{id:"+menu.getMenuId()+", pId:"+menu.getMenuPid()+", name:\""+menu.getMenuName()+"\", open:true,"+(hasMenuList.contains(menu.getMenuId().toString())?"checked:true":"checked:false")+"},");
		}
		
		String jsonStr=json.substring(0, json.length()-1)+"] ";
		
		
		getRequest().setAttribute("menuListStr",jsonStr);
		return SUCCESS;
	}
	public String getGroupMenu(){
		
		//result="[{\"id\": 1,\"pId\": 0,\"name\": \"随意勾选 1\",\"open\": true},{\"id\": 11,\"pId\": 1,\"name\": \"随意勾选 11\",\"checked\": true}]";
		List<MenuVO>  menuList= new ArrayList<MenuVO>();
		menuList.add(new MenuVO(1,0,"随意勾选1"));
		menuList.add(new MenuVO(11,1,"随意勾选1"));
		menuList.add(new MenuVO(12,1,"随意勾选1"));
		menuList.add(new MenuVO(13,1,"随意勾选1"));
		menuList.add(new MenuVO(111,11,"随意勾选1"));
		menuList.add(new MenuVO(112,11,"随意勾选1"));
		result=Ajax.toJsonFromObject(menuList);
		return SUCCESS;
	}
	
	
	
	public GroupService getGroupService() {
		return groupService;
	}
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
	public MenuService getMenuService() {
		return menuService;
	}
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	public Group getGroup() {
		return group;
	}
	public void setGroup(Group group) {
		this.group = group;
	}
	
	

}
