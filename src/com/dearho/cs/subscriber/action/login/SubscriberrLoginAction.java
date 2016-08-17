package com.dearho.cs.subscriber.action.login;



/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberLoginAction.java creation date:[2015-5-18 上午10:04:40] by liusong
 *http://www.dearho.com
 */
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.pojo.SubscriberLoginRecord;
import com.dearho.cs.subscriber.service.SubscriberLoginRecordService;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.CookieTool;
import com.dearho.cs.util.PropertiesHelper;
import com.dearho.cs.util.Sha1Util;
import com.dearho.cs.util.ToolDateTime;

import net.sf.json.JSONObject;

/**
 * @Author liusong
 * @Description 会员登录
 * @Version 1.0,2015-5-18
 *
 */
public class SubscriberrLoginAction extends AbstractAction {


	private static final long serialVersionUID = 9052312888517878632L;
	private SubscriberService subscriberService;
	private SubscriberLoginRecordService subscriberLoginRecordService;
	private Subscriber subscriber ;
	private String retMsg;
	
	private static final Log logger = LogFactory.getLog(SubscriberrLoginAction.class);
	
	
	public SubscriberrLoginAction() {
		super();
		subscriber=new Subscriber();
	}

	
	@Override
	public String process() {
		try{
			
			String phoneNo=subscriber.getPhoneNo();
			String password=subscriber.getPassword();
			String autoLoginStr =getRequest().getParameter("autoLogin");
			boolean autoLogin = false;
			if(null != autoLoginStr && autoLoginStr.equals("1")){
				autoLogin = true;
			}
			if(StringUtils.isEmpty(phoneNo)||StringUtils.isEmpty(password)){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请输入手机号或密码!");
				return SUCCESS;
			}
			
			//===========

			Subscriber loginSubscriber=subscriberService.querySubscriberByPhoneNo(phoneNo);

			//用户名校验
			if (loginSubscriber == null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "用户名或密码错误！"); 
				return SUCCESS;
			}
			
			// 密码错误次数超限，几分钟内不能登录
			int errorCount = loginSubscriber.getErrorCount()==null ? 0:loginSubscriber.getErrorCount();
			int passErrorCount = Integer.parseInt(PropertiesHelper.getValue("password_error_account"));
			if(errorCount >= passErrorCount){
				Date stopDate = loginSubscriber.getStopDate();
				int minuteSpace = ToolDateTime.getDateMinuteSpace( stopDate,ToolDateTime.getDate());
				int passErrorMinute = Integer.parseInt(PropertiesHelper.getValue("password_error_minute"));
				if(minuteSpace < passErrorMinute){ 
					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "密码错误次数超限，"+(passErrorMinute-minuteSpace )+"分钟内不能登录！"); 
					return SUCCESS;
				}
			}
			
			//密码对比校验	
			if(!loginSubscriber.getPassword().equals(Sha1Util.SHA1Encode(password))){
				loginSubscriber.setStopDate(new Date());
				loginSubscriber.setErrorCount(errorCount+1);
				subscriberService.updateSubscriber(loginSubscriber);
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "用户名或密码错误！"); 
				
				return SUCCESS;
			}
			//=============
			
			
			/*Subscriber loginSubscriber=subscriberService.login(subscriber);
			if(loginSubscriber==null){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "手机号或密码错误!");
				return SUCCESS;
			}*/
			
			if(Subscriber.EVENT_STATE_FUll.equals(loginSubscriber.getEventState())){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "此账号已被锁定，如有疑问，请联系客服!");
				return SUCCESS;
			}
			
			getSession().setAttribute(Constants.SESSION_SUBSCRIBER, loginSubscriber);
			//更新用户登录失败记录
			loginSubscriber.setErrorCount(0);
			subscriberService.updateSubscriber(loginSubscriber);
			
			SubscriberLoginRecord loginRecord =new SubscriberLoginRecord();
			loginRecord.setLoginTime(new Date());
			loginRecord.setSubscriberId(loginSubscriber.getId());
			loginRecord.setTs(new Date());
			loginRecord.setIp(getIpAddr(getRequest()));
			subscriberLoginRecordService.addSubscriberLoginRecord(loginRecord);
			
			
			
			try {
				CookieTool.setCurrentUser(getRequest(), getResponse(), loginSubscriber, autoLogin);
			} catch (Exception e) {
				logger.error(e);
			}
			Object referrer=getRequest().getSession().getAttribute("login_referrer");
			String login_referrer="";
			if(referrer!=null){
				login_referrer=(String)referrer;
			}
			 getRequest().getSession().setAttribute("login_referrer", null);
			result=new JSONObject().element("result", Constants.RESULT_CODE_SUCCESS).element("login_referrer", login_referrer).toString();
			//result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "登录成功!");

		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, " 登录异常!");
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	
	private String getIpAddr(HttpServletRequest request) { 
	       String ip = request.getHeader("x-forwarded-for"); 
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	           ip = request.getHeader("Proxy-Client-IP"); 
	       } 
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	           ip = request.getHeader("WL-Proxy-Client-IP"); 
	       } 
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	           ip = request.getRemoteAddr(); 
	       } 
	       return ip; 
	   } 
	
	
	
	public String gotoLogin(){
		
		
		/*try {
			getRequest().getRequestDispatcher("/").forward(getRequest(),getResponse());
			
		} catch (ServletException e1) {
			
			e1.printStackTrace();
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}*/
		
		/*String subscriberId = CookieTool.getCurrentUser(getRequest());
		if(!StringUtils.isEmpty(subscriberId)){
			
			
			try {
				 Subscriber s=	subscriberService.querySubscriberById(subscriberId);
				 if(s!=null){
					 getSession().setAttribute(Constants.SESSION_SUBSCRIBER, s);
				 }
				 
				String referer= getRequest().getHeader("referer");
				if(referer==null || !referer.contains(getRequest().getServerName())){
					
					
				}
				 
			} catch (Exception e) {
				
			}
		}*/
		return 	SUCCESS;
	}
	


	public SubscriberService getSubscriberService() {
		return subscriberService;
	}


	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}


	public Subscriber getSubscriber() {
		return subscriber;
	}


	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}


	public String getRetMsg() {
		return retMsg;
	}


	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}


	public void setSubscriberLoginRecordService(
			SubscriberLoginRecordService subscriberLoginRecordService) {
		this.subscriberLoginRecordService = subscriberLoginRecordService;
	}


}
