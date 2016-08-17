/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountServiceImpl.java creation date:[2015-6-1 下午02:48:52] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.service.impl;

import com.dearho.cs.account.dao.InterfaceMessageLogDao;
import com.dearho.cs.account.pojo.InterfaceMessageLog;
import com.dearho.cs.account.service.InterfaceMessageLogService;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-16
 *
 */
public class InterfaceMessageLogServiceImpl implements InterfaceMessageLogService {
	
	private InterfaceMessageLogDao interfaceMessageLogDao;

	@Override
	public void addInterfaceMessageLog(InterfaceMessageLog interfaceMessageLog) {
		
		interfaceMessageLogDao.addInterfaceMessageLog(interfaceMessageLog);
	}

	public InterfaceMessageLogDao getInterfaceMessageLogDao() {
		return interfaceMessageLogDao;
	}

	public void setInterfaceMessageLogDao(InterfaceMessageLogDao interfaceMessageLogDao) {
		this.interfaceMessageLogDao = interfaceMessageLogDao;
	}

	

	

	
	
}
