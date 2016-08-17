package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Config;
/**
 * @author GaoYunpeng
 * @Description 数据字典组
 * @version 1.0 2015年5月19日 下午3:22:01
 */
public interface ConfigDAO {
	List<Config> searchConfig(String id,String name);
	void addConfig(Config config);
	void updateConfig(Config config);
	void deleteConfig(final String[] checkdel);
	Page<Config> searchConfig(Page<Config> page,String hql);
	Config getConfigByCode(String code);
}
