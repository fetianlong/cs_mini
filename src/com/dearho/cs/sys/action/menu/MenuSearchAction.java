package com.dearho.cs.sys.action.menu;


import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Menu;
import com.dearho.cs.sys.service.MenuService;


public class MenuSearchAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MenuService menuService;
	
	private Page<Menu> page = new Page<Menu>();
	
	private Menu menu;
	
	
	public String process() {
//		if(getPageNum() != null){
//			page.setCurrentPage(getPageNum());
//		}
//		if(getNumPerPage() != null){
//			page.setPageSize(getNumPerPage());
//		}
		page = menuService.queryMenuByPage(page, menu);
		return SUCCESS;
	}
	
	
	public MenuSearchAction() {
		super();
		menu=new Menu();
		
		page.setCountField("a.id");
//		if(getPageNum() != null){
//			page.setCurrentPage(getPageNum());
//		}
//		else{
//			page.setCurrentPage(1);
//		}
//		if(getNumPerPage() != null){
//			page.setPageSize(getNumPerPage());
//		}
	}
	
	public String getMenuNameById(Integer id){
		Menu m = menuService.queryMenuById(id);
		if(m == null){
			return "";
		}
		else{
			return m.getMenuName();
		}
	}

	public List<Menu> getAllParentMenu(){
		return menuService.getAllParentMenu();
	}

	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}

	public Page<Menu> getPage() {
		return page;
	}

	public void setPage(Page<Menu> page) {
		this.page = page;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}


}
