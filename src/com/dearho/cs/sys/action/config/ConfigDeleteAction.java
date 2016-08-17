package com.dearho.cs.sys.action.config;

import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Config;
import com.dearho.cs.sys.service.ConfigService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:22:43
 */
public class ConfigDeleteAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] checkdel;
	
	private ConfigService configService;
	
	private String id;

	@Override
	public String process() {
		try {
			boolean hasSystem = false;
			if(!StringHelper.isRealNull(id)){
				checkdel = new String[]{id};
			}
			for (String id : checkdel) {
				List<Config> configs = configService.searchConfig(id, null);
				if(configs != null && configs.size() > 0){
					if(configs.get(0).getIsSystem() == 1){
						hasSystem = true;
					}
				}
			}
			if(hasSystem){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "系统预制参数不允许删除！");
				return Action.ERROR;
			}
			configService.deleteConfig(checkdel);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "参数删除成功！");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！");
			return Action.ERROR;
		}
	}

	public String[] getCheckdel() {
		return checkdel;
	}

	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
