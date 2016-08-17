package com.dearho.cs.sys.action.config;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Config;
import com.dearho.cs.sys.service.ConfigService;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class ConfigSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ConfigService configService;
	
	private Page<Config> page = new Page<Config>();
	
	private Config rEntity;
	
	private List<Config> recordList;
	
	public ConfigSearchAction(){
		super();
		rEntity = new Config();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			page = configService.searchConfig(page, rEntity);
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}


	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public Page<Config> getPage() {
		return page;
	}

	public void setPage(Page<Config> page) {
		this.page = page;
	}

	public Config getrEntity() {
		return rEntity;
	}

	public void setrEntity(Config rEntity) {
		this.rEntity = rEntity;
	}

	public List<Config> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<Config> recordList) {
		this.recordList = recordList;
	}
	
	
	
}
