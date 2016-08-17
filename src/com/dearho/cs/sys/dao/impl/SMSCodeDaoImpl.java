package com.dearho.cs.sys.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.sys.dao.SMSCodeDao;
import com.dearho.cs.sys.pojo.SMSCode;

/**   
 * @Description: 
 * @author liusong 
 * @date 2015-7-20
 * @version 1.0   
 */

public class SMSCodeDaoImpl  extends AbstractDaoSupport implements SMSCodeDao {





	@Override
	public void addSMSCode(SMSCode smsCord) {
		addEntity(smsCord);	
		
	}
	
	@Override
	public List<SMSCode> searchSMSCode(String hql) {
		return getList(SMSCode.class, queryFList( hql));
	}

	

}
