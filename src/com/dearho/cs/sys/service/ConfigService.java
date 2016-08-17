package com.dearho.cs.sys.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Config;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年5月19日 下午3:19:44
 */
public interface ConfigService {
	List<Config> searchConfig(String id,String name);
	
	void addConfig(Config config);
	
	void updateConfig(Config config);
	void deleteConfig(final String[] checkdel);
	
	Page<Config> searchConfig(Page<Config> page,Config config);

	Config getConfigByCode(String code);
	
}
