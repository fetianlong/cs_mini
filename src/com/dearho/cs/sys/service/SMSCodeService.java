package com.dearho.cs.sys.service;

import com.dearho.cs.sys.pojo.SMSCode;


public interface SMSCodeService {
	
	public SMSCode getLatestSMSCode(SMSCode smsCode,Integer validMinute) ;
	
	void addSMSCode(SMSCode smsRecord);
	public  String sendSMSCode(String type,String phoneNo,Integer channel) throws Exception;
	
	
}
