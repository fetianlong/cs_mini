package com.dearho.cs.sys.action.menu;



import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Menu;
import com.dearho.cs.sys.service.MenuService;
import com.dearho.cs.util.StringHelper;

public class MenuGetAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	private MenuService menuService;
	
	private Menu menu;
	private String state;
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	
	

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public MenuGetAction() {
		super();
		menu=new Menu();
	}

	public List<Menu> getMenusByPid(Integer pid){
		return menuService.getMenusByPid(pid);
	}
	public List<Menu> getAllParentMenu(){
		return menuService.getAllParentMenu();
	}
	@Override
	public String process() {
		if(menu.getMenuId()!=null){
			menu=menuService.queryMenuById(menu.getMenuId());
			if(null==menu){
				menu=new Menu();
			}
		}
		return SUCCESS;
	}
	
}
