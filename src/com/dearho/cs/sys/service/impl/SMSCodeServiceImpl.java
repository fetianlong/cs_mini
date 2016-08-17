package com.dearho.cs.sys.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.SMSCodeDao;
import com.dearho.cs.sys.dao.SMSRecordDao;
import com.dearho.cs.sys.pojo.SMSCode;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.service.SMSCodeService;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

public class SMSCodeServiceImpl implements SMSCodeService{

	private SMSCodeDao smsCodeDao;
	






	@Override
	public void addSMSCode(SMSCode smsCord) {
		smsCodeDao.addSMSCode(smsCord);	
		
	}

	@Override
	public SMSCode getLatestSMSCode(SMSCode smsCode,Integer validMinute) {
		StringBuffer hql=new StringBuffer();
		
	
		hql.append("select id from SMSCode a where 1 = 1 ");
		if (smsCode != null){
			if(!StringUtils.isEmpty(smsCode.getPhoneNo())){
				hql.append(" and phoneNo = '"+smsCode.getPhoneNo()+"' ");
			}
			if(smsCode.getChannel()!=null){
				hql.append(" and channel ="+smsCode.getChannel()+" ");
			}
			if(!StringUtils.isEmpty(smsCode.getType())){
				hql.append(" and type ='"+smsCode.getType()+"' ");
			}
		}
		
		if(validMinute!=null){
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MINUTE, -validMinute);
			hql.append(" and ts > '").append(DateUtil.getChar19DateString(calendar.getTime())).append("'");

		}
		
		hql.append(" order by ts desc");
	
		List<SMSCode> list=smsCodeDao.searchSMSCode(hql.toString());
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public SMSCodeDao getSmsCodeDao() {
		return smsCodeDao;
	}

	public void setSmsCodeDao(SMSCodeDao smsCodeDao) {
		this.smsCodeDao = smsCodeDao;
	}



	@Override
	public String sendSMSCode(String type, String phoneNo, Integer channel) throws Exception {
		String result=null;
		
		if(StringUtils.isEmpty(phoneNo)){
			result = "手机号不能为空";
			return result;
		}
		String message=null;
		if(Constants.SUBSCIRBER_REGISTER_PHONE_CODE.equals(type)){
			 message=" 用户注册验证码:code，此验证码5分钟内有效，如非本人操作请忽略。";
		}else if(Constants.SUBSCIRBER_CHANGE_NEW_PHONE_CODE.equals(type)){
			 message=" 用户修改手机号验证码:code，此验证码5分钟内有效，如非本人操作请忽略。";
			
		}else if(Constants.SUBSCIRBER_CHANGE_OLD_PHONE_CODE.equals(type)){
			 message="用户修改手机号验证码:code，此验证码5分钟内有效，如非本人操作请忽略。";
			
		}else if(Constants.SUBSCRIBER_LOGIN_CODE.equals(type)){
			 message="用户登录验证码:code，此验证码5分钟内有效。";
		}
		
		SMSCode smsCode=new SMSCode();
		smsCode.setPhoneNo(phoneNo);
		if(channel!=null){
			smsCode.setChannel(channel);
		}
		smsCode.setType(type);
		smsCode=this.getLatestSMSCode(smsCode, Constants.REGISTER_SMS_VALID_RETRY_MINUTE);
		if(smsCode!=null){
			result = "2分钟内只能获取一次验证码，请稍后重试";
			return result;
		}

		
		StringBuffer code=new StringBuffer(); 
		Random r=new Random(); 
		for(int i=0;i<4;i++){ 
			code.append(r.nextInt(10));
		}; 
		
		SMSCode smsCode2=new SMSCode();
		smsCode2.setType(type);
		smsCode2.setPhoneNo(phoneNo);
		smsCode2.setChannel(channel);
		smsCode2.setMessage(message);;
		smsCode2.setCode(code.toString());
		smsCode2.setTs(new Date());
		this.addSMSCode(smsCode2);
		SMSUtil.sendSMS(phoneNo, message.replace("code", code),SMSRecord.TYPE_REGISTER);
		
		result="ok";
		return result;
	}


	
	
	
}
