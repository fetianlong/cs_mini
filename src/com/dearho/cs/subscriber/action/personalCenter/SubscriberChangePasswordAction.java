/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file SubscriberChangePassword.java creation date:[2015-5-27 上午11:14:06] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.subscriber.action.personalCenter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dearho.cs.subscriber.action.register.SubscriberRegisterTwoAction;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.Sha1Util;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-27
 *
 */
public class SubscriberChangePasswordAction extends AbstractAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5955127175067934274L;
	private SubscriberService subscriberService;
	private Subscriber subscriber ;
	private static final Log logger = LogFactory.getLog(SubscriberChangePasswordAction.class);

	public SubscriberChangePasswordAction(){
		super();
		subscriber=new Subscriber();
	}
	@Override
	public String process() {
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		Subscriber s=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		s=subscriberService.querySubscriberById(s.getId());
		
		if(Subscriber.EVENT_STATE_HALF.equals(s.getEventState())){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "此账号已冻结，不能进行会员相关操作。<br/> 如有疑问，请联系客服。");
			return SUCCESS;
		}
		
		String oldPassword=getRequest().getParameter("oldPassword");
		
		if(oldPassword!=null && s.getPassword().equals(Sha1Util.SHA1Encode(oldPassword))){
			if(oldPassword.equals(subscriber.getPassword())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "新旧密码一致无须更改!");
				return SUCCESS;
			}
			
			s.setPassword(Sha1Util.SHA1Encode(subscriber.getPassword()));
			subscriberService.updateSubscriber(s);
			try {
				StringBuffer content = new StringBuffer();
				content.append("尊敬的乐途出行用户，您的密码修改成功，如非本人操作，请关注账号安全。");
				SMSUtil.sendSMS(s.getPhoneNo(), content.toString(),SMSRecord.TYPE_PWD);
			} catch (Exception e) {
				logger.error("修改密码发短信异常:", e);
			}
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "修改成功!");
			//更新session 用户状态可能变更
			getSession().setAttribute(Constants.SESSION_SUBSCRIBER,s);
		}else{
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "旧密码有误!");
		}
		
		return SUCCESS;
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

	/**
	 * 前台页面修改系统登录密码
	 * @return
	 */
	public String portalChangePassword(){
//		String result = this.process();
//		return result;
		return SUCCESS;
	}
	
	
}
