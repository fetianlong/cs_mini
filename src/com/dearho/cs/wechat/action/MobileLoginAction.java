package com.dearho.cs.wechat.action;



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

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.pojo.SubscriberLoginRecord;
import com.dearho.cs.subscriber.service.SubscriberLoginRecordService;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.SMSCode;
import com.dearho.cs.sys.service.SMSCodeService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.IpUtil;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.service.WechatUserInfoService;

import net.sf.json.JSONObject;

/**
 * @Author liusong
 * @Description 会员登录
 * @Version 1.0,2015-5-18
 *
 */
public class MobileLoginAction extends AbstractAction {


	private static final long serialVersionUID = 9052312888517878632L;
	private SubscriberService subscriberService;
	private SubscriberLoginRecordService subscriberLoginRecordService;
	private Subscriber subscriber ;
	private String retMsg;
	
	private WechatUserInfoService wechatUserInfoService;
//	private OrdersService ordersService;
	private SMSCodeService smsCodeService;
	
	private static final Log logger = LogFactory.getLog(MobileLoginAction.class);
	
	
	public MobileLoginAction() {
		super();
		subscriber=new Subscriber();
	}

	
	@Override
	public String process() {
		try{
			
		
			
			if(StringUtils.isEmpty(subscriber.getPhoneNo())||StringUtils.isEmpty(subscriber.getSmsCode())){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请输入手机号或验证码!");
				return SUCCESS;
			}
			Subscriber loginSubscriber=subscriberService.querySubscriberByPhoneNo(subscriber.getPhoneNo());
			if(loginSubscriber==null){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "用户未注册!");
				return SUCCESS;
			}
			if(Subscriber.EVENT_STATE_FUll.equals(loginSubscriber.getEventState())){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "此账号已被锁定，如有疑问，请联系客服!");
				return SUCCESS;
			}
			
			SMSCode smsCode=new SMSCode();
			smsCode.setPhoneNo(subscriber.getPhoneNo());
			smsCode.setType(Constants.SUBSCRIBER_LOGIN_CODE);
			smsCode.setChannel(Account.PAY_CHANNEL_WECHAT);
			smsCode=smsCodeService.getLatestSMSCode(smsCode,Constants.REGISTER_SMS_VALID_MINUTE );
			if(smsCode==null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "验证码已失效请重新获取!");
				return SUCCESS;
			}
			
			if(!subscriber.getSmsCode().equals(smsCode.getCode())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "验证码不正确!");
				return SUCCESS;
			}
			
			
			
			//微信号和会员账号做关联
			if(getSession().getAttribute("wechatUserInfo")!=null && getRequest().getParameter("WeChat")!=null && "1".equals(getRequest().getParameter("WeChat"))){
				WechatUserInfo wechatUserInfo =(WechatUserInfo)getSession().getAttribute("wechatUserInfo");
				if(StringUtils.isEmpty(wechatUserInfo.getSubscriberId())){
					wechatUserInfo = wechatUserInfoService.getUserInfoByOpenId(wechatUserInfo.getOpenId());
					loginSubscriber.setWechatUnionId(wechatUserInfo.getUnionId());
						
				}
			}
			
			
		
			// loginSubscriber.setLoginMacAddress(IpUtil.getMACAddress(getRequest()));
			
	         subscriberService.updateSubscriber(loginSubscriber);
			
			getSession().setAttribute(Constants.SESSION_SUBSCRIBER, loginSubscriber);
			
			SubscriberLoginRecord loginRecord =new SubscriberLoginRecord();
			loginRecord.setLoginTime(new Date());
			loginRecord.setSubscriberId(loginSubscriber.getId());
			loginRecord.setTs(new Date());
			loginRecord.setIp(getIpAddr(getRequest()));
			subscriberLoginRecordService.addSubscriberLoginRecord(loginRecord);
			
			
			Object referrer=getRequest().getSession().getAttribute("login_referrer");
			String login_referrer="";
			if(referrer!=null){
				login_referrer=(String)referrer;
			}
			
			//是否有当前订单，有则替换refer
		/*	Orders orders=ordersService.queryCurrentOrder(loginSubscriber.getId());
			if(orders!=null){
				login_referrer="";
			}*/
			getRequest().getSession().setAttribute("login_referrer", null);
			result=new JSONObject().element("result", Constants.RESULT_CODE_SUCCESS).element("login_referrer", login_referrer).toString();

		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, " 登录异常!");
			logger.error(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	
	 
	
	
	
	/**
	 *  发送登录短信验证码
	 * @return
	 */
	public String sendLoginCode(){
		
		try {
			
			 result=subscriberService.sendSMSCode(Constants.SUBSCRIBER_LOGIN_CODE,subscriber.getPhoneNo() , Account.PAY_CHANNEL_WECHAT);
			if("ok".equals(result)){
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "下发成功");
			}else{
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, result);
			}			
			
		} catch (Exception e) {
			logger.error(e);
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "发送短信失败！");
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


	public void setWechatUserInfoService(WechatUserInfoService wechatUserInfoService) {
		this.wechatUserInfoService = wechatUserInfoService;
	}


	public SMSCodeService getSmsCodeService() {
		return smsCodeService;
	}


	public void setSmsCodeService(SMSCodeService smsCodeService) {
		this.smsCodeService = smsCodeService;
	}


	
	

}
