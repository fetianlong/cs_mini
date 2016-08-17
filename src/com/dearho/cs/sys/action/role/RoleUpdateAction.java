package com.dearho.cs.sys.action.role;

import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:30:49
 */
public class RoleUpdateAction extends RoleAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String process(){
		try {
			getRoleService().updateRole(getrEntity());
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, getrEntity().getId());
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "角色更新失败！");
			return Action.ERROR;
		}
	}
}
