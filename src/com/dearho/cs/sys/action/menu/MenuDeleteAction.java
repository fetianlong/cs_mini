package com.dearho.cs.sys.action.menu;



import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.service.MenuService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

public class MenuDeleteAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private MenuService menuService;
	
	private String[] checkdel;
	



	public MenuService getMenuService() {
		return menuService;
	}

	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}


	public String[] getCheckdel() {
		return checkdel;
	}
	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
	}
	@Override
	public String process() {
		try{
			
			menuService.deleteMenuByIds(checkdel);

			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功");
		}catch(Exception e){
			e.printStackTrace();
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败");
			return ERROR;
		}
		return SUCCESS;
	}

}
