package com.dearho.cs.sys.action.user;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Constants;
import com.opensymphony.xwork.Action;

/**
 * @Author liusong
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class LogoutAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public String process() {
		getSession().setAttribute(Constants.SESSION_USER, null);
		getSession().setAttribute(Constants.SESSION_USER_PERMISSION,null);
		return Action.SUCCESS;
	}
	
	
}
