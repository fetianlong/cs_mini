package com.dearho.cs.sys.action.config;

import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Config;
import com.dearho.cs.sys.service.ConfigService;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:31
 */
public class ConfigGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private ConfigService configService;
	
	private Config rEntity;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			rEntity = new Config();
		}else{
			List<Config> eEntitys = configService.searchConfig(id, null);
			if (eEntitys == null || eEntitys.size() <= 0 ){
				rEntity = new Config();
			}
			else {
				rEntity = eEntitys.get(0);
			}
		}
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
