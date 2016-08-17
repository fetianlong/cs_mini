package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.sys.pojo.SMSCode;

/**   
 * @Description: 
 * @author liusong 
 * @date 2015-7-20
 * @version 1.0   
 */

public interface SMSCodeDao {
	
	public List<SMSCode> searchSMSCode(String hql);
	
	void addSMSCode(SMSCode smsRecord);
	
	
}
