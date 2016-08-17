package com.dearho.cs.sys.action.menu;

import java.util.Date;

import com.dearho.cs.sys.pojo.Menu;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.xwork.Action;

public class MenuUpdateAction extends MenuAddAction {

	private static final long serialVersionUID = 1L;

	@Override
	public String process() {
		try {
			Menu c = getMenu();
			Menu oldC = getMenuService().queryMenuById(c.getMenuId());
			c.setMenuOrder(Integer.valueOf(oldC.getMenuOrder()));
			c.setTs(new Date());
			getMenuService().updateMenu(c);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return Action.ERROR;
		}
	}
	
}
