package com.dearho.cs.sys.action.config;

import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 系统参数 更新
 * @version 1.0 2015年4月22日 上午10:30:49
 */
public class ConfigUpdateAction extends ConfigAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String process(){
		try {
			getConfigService().updateConfig(getrEntity());
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, getrEntity().getId());
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "参数更新失败！");
			return Action.ERROR;
		}
	}
}
