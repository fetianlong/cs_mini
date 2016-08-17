package com.dearho.cs.appinterface.sys.action;

import java.util.Date;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.appinterface.sys.pojo.AppToken;
import com.dearho.cs.core.db.memcached.MemCachedFactory;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.SMSCode;
import com.dearho.cs.sys.service.SMSCodeService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.JsonTools;
import com.dearho.cs.util.Md5Util;
import com.dearho.cs.util.TokenUtils;

/**
 * 
* @ClassName: AppLoginAction 
* @Description: App登录
* @author LH
* @date 2016年3月9日 下午5:18:05 
*
 */
public class AppLoginAction extends AbstractAction{
	
	

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	
	private SMSCodeService smsCodeService;
	private SubscriberService subscriberService;
	private Subscriber subscriber ;
	
	//private AppTokenService appTokenService;
	private AppToken appToken;

	@Override
	public String process() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String appLogin(){
		String data = getRequest().getParameter("data");
		Map<String, String> map = JsonTools.jsonForMap(data);
		String phoneNo = map.get("phone_no"); //手机号
		String code = map.get("verify_code"); //短信验证码校验
		SMSCode newSmsCode=new SMSCode();
		newSmsCode.setPhoneNo(phoneNo);
		newSmsCode.setType(Constants.SUBSCRIBER_LOGIN_CODE);
		newSmsCode.setChannel(Account.PAY_CHANNEL_APP);
		newSmsCode=smsCodeService.getLatestSMSCode(newSmsCode,Constants.REGISTER_SMS_VALID_RETRY_MINUTE );
		if(newSmsCode==null){
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "验证码已失效请重新获取");
			return SUCCESS;
		}
		if(StringUtils.isEmpty(code)){
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "验证码不能为空");
			return SUCCESS;
		}
		if(!code.equals(newSmsCode.getCode())){
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "验证码有误");
			return SUCCESS;
		}
		//获取用户信息
		subscriber = subscriberService.querySubscriberByPhoneNo(phoneNo);
		if(subscriber==null){
			// 如果新用户,则创建用户信息
			subscriber= new Subscriber();
			subscriber.setPhoneNo(phoneNo);
			subscriber = subscriberService.addSubscriber(subscriber);
		}
		//生成Token存入MemCached
		String id = subscriber.getId();
		Date date= new Date();
	
		String token = TokenUtils.getTokenEncode(String.valueOf(date.getTime()),id);
		
		appToken= new AppToken();//封装Token
		appToken.setCreationTime(date);
		appToken.setTokenCode(token);
		appToken.setObjectId(id);
		//缓存
		boolean memcached = MemCachedFactory.set(Md5Util.MD5Encode(id), appToken);
		if(!memcached){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "登录失败,服务异常");
			return SUCCESS;
		}
		result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, "登录成功",token);
		return SUCCESS;
	}
	/**
	* @Title: invalidToken 
	* @Description: token失效时返回方法
	* @return String
	*/
	public String invalidToken(){
		result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "验证身份失败");
		return SUCCESS;
	}
	public SMSCodeService getSmsCodeService() {
		return smsCodeService;
	}

	public void setSmsCodeService(SMSCodeService smsCodeService) {
		this.smsCodeService = smsCodeService;
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
/*
	public AppTokenService getAppTokenService() {
		return appTokenService;
	}

	public void setAppTokenService(AppTokenService appTokenService) {
		this.appTokenService = appTokenService;
	}
*/
	public AppToken getAppToken() {
		return appToken;
	}

	public void setAppToken(AppToken appToken) {
		this.appToken = appToken;
	}

	
}
