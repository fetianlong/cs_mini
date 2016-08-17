package com.dearho.cs.sys.service.impl;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.ConfigDAO;
import com.dearho.cs.sys.pojo.Config;
import com.dearho.cs.sys.service.ConfigService;
import com.dearho.cs.util.StringHelper;

/**
 * @author GaoYunpeng
 * @Description 角色业务实现类
 * @version 1.0 2015年4月22日 上午9:57:01
 */
public class ConfigServiceImpl implements ConfigService {

	private ConfigDAO configDAO;
	
	public void setConfigDAO(ConfigDAO configDAO) {
		this.configDAO = configDAO;
	}
	
	@Override
	public List<Config> searchConfig(String id, String name) {
		return configDAO.searchConfig(id, name);
	}

	@Override
	public void addConfig(Config config) {
		configDAO.addConfig(config);
	}

	@Override
	public void updateConfig(Config config) {
		configDAO.updateConfig(config);
	}

	@Override
	public void deleteConfig(String[] checkdel) {
		configDAO.deleteConfig(checkdel);
	}

	@Override
	public Page<Config> searchConfig(Page<Config> page, Config roleEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.id from Config a where 1 = 1 ");
		if (roleEntity != null){
			if (StringHelper.isNotEmpty(roleEntity.getName())){
				sb.append("and a.name like '%"+roleEntity.getName()+"%'");
			}
			if (StringHelper.isNotEmpty(roleEntity.getCode())){
				sb.append("and a.code like '%"+roleEntity.getCode()+"%'");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		page = configDAO.searchConfig(page, sb.toString());
		return page;
	}

	@Override
	public Config getConfigByCode(String code) {
		return configDAO.getConfigByCode(code);
	}

}
