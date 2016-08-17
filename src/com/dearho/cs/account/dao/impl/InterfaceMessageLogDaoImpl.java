/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountTradeRecordDaoImpl.java creation date:[2015-6-1 下午02:38:41] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.dao.impl;

import com.dearho.cs.account.dao.InterfaceMessageLogDao;
import com.dearho.cs.account.pojo.InterfaceMessageLog;
import com.dearho.cs.core.db.AbstractDaoSupport;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-23
 *
 */
public class InterfaceMessageLogDaoImpl extends AbstractDaoSupport implements InterfaceMessageLogDao {

	@Override
	public void addInterfaceMessageLog(InterfaceMessageLog interfaceMessageLog) {
		addEntity(interfaceMessageLog);
	}


	


}
