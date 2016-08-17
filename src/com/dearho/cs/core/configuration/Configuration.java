package com.dearho.cs.core.configuration;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class Configuration {
	private static Log log = LogFactory.getLog(Configuration.class);

	private static Configuration instance = new Configuration();

	private static boolean reLoad = true;

	private String errorinfoShowType;
	
	private int sysPageSize, timeOut = 60 * 30;
	
	private ConcurrentMap<String, Object> paramMap;
	

	public synchronized static Configuration getInstance() {
		if (reLoad)
			instance.loadParam();
		return instance;
	}

	protected Configuration() {

	}

	public void setReload() {
		reLoad = true;
	}

	public int getTimeOut() {
		return timeOut;
	}



	private void loadParam() {
		try {
			paramMap = new ConcurrentHashMap<String, Object>();
			paramMap.put("errorinfoShowType", "detail");
			errorinfoShowType = "detail";
			sysPageSize = 10;
			reLoad = false;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public int getSysPageSize() {
		return sysPageSize;
	}

	public String getErrorinfoShowType() {
		return errorinfoShowType;
	}
}
