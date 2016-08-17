package com.dearho.cs.sys.action.menu;

import java.util.Date;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Menu;
import com.dearho.cs.sys.service.MenuService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

public class MenuAddAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private MenuService menuService;
	private Menu menu;
	
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
	
	public MenuAddAction() {
		super();
		menu=new Menu();
	}

	@Override
	public String process() {
		try{
			int orderNo = menuService.getMaxOrderNo(menu.getMenuPid());
			menu.setMenuOrder(orderNo + 1);
			menu.setTs(new Date());
			menuService.addMenu(menu);
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "添加成功！");
		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "添加失败!");
			return ERROR;
		}
		return SUCCESS;
	}

}
