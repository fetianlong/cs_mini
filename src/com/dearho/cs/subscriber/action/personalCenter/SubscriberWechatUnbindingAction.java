/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file SubscriberChangePassword.java creation date:[2015-5-27 上午11:14:06] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.subscriber.action.personalCenter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.service.WechatUserInfoService;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-27
 *
 */
public class SubscriberWechatUnbindingAction extends AbstractAction{

	private static final long serialVersionUID = 8565951619735489060L;
	private WechatUserInfoService  wechatUserInfoService;
	private Subscriber subscriber ;
	private WechatUserInfo wechatUserInfo;
	private SubscriberService subscriberService;
	

	public SubscriberWechatUnbindingAction(){
		super();
		subscriber=new Subscriber();
		wechatUserInfo=new WechatUserInfo();
	}
	@Override
	public String process() {
		
		subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		subscriber=subscriberService.querySubscriberById(subscriber.getId());
		
		if(StringUtils.isNotEmpty(subscriber.getWechatUnionId())){
			try {
				Map<String, String> contentMap = new HashMap<String, String>();
				contentMap.put("微信号解绑", subscriber.getWechatUnionId());
				SystemOperateLogUtil.sysAddOperateLog(subscriber.getId(), null, SystemOperateLogUtil.MODEL_SUBSCRIBER_INFO,contentMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			subscriber.setWechatUnionId(null);
			subscriberService.updateSubscriber(subscriber);
			
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "解绑成功！");
			return SUCCESS;
		}else{
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "未绑定微信账号！");
			return SUCCESS;
		}
		
		
		

	}
		public Subscriber getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
	
	public WechatUserInfo getWechatUserInfo() {
		return wechatUserInfo;
	}
	public void setWechatUserInfo(WechatUserInfo wechatUserInfo) {
		this.wechatUserInfo = wechatUserInfo;
	}
	public void setWechatUserInfoService(WechatUserInfoService wechatUserInfoService) {
		this.wechatUserInfoService = wechatUserInfoService;
	}
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}
	
	
	
}
