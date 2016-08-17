package com.dearho.cs.sys.action.config;


import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Config;
import com.dearho.cs.sys.service.ConfigService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:14:43
 */
public class ConfigAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConfigService configService;
	
	private Config rEntity;
	
	public ConfigAddAction(){
		super();
		rEntity = new Config();
	}
	
	public String process(){
		try {
			Config config = configService.getConfigByCode(rEntity.getCode());
			if (config != null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "参数已存在！");
				return Action.ERROR;
			}
			rEntity.setIsSystem(0);
			configService.addConfig(rEntity);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, rEntity.getId());
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "参数保存失败！");
			return Action.ERROR;
		}
	}
	

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public Config getrEntity() {
		return rEntity;
	}

	public void setrEntity(Config rEntity) {
		this.rEntity = rEntity;
	}


}
