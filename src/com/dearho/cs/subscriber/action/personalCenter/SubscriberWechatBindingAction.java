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
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.service.WechatUserInfoService;
import com.dearho.cs.wechat.util.WeixinUtil;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-27
 *
 */
public class SubscriberWechatBindingAction extends AbstractAction{

	private static final long serialVersionUID = 8565951619735489060L;
	private WechatUserInfoService  wechatUserInfoService;
	private SubscriberService subscriberService;
	

	public SubscriberWechatBindingAction(){
		super();
	
	}
	@Override
	public String process() {
		
	
			String subscriberId=getRequest().getParameter("subscriberId");
			
			if(StringUtils.isEmpty(subscriberId)){
				Subscriber subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
				subscriberId=subscriber.getId();
			}
			
			Subscriber subscriber=subscriberService.querySubscriberById(subscriberId);
			if(StringUtils.isEmpty(subscriber.getWechatUnionId())){
				
				String unionId=	getRequest().getParameter("unionId");
				
				if(StringUtils.isEmpty(unionId)){
					String rechargeReferrer="/rechargePrepareServlet?subscriberId="+subscriberId+"&url=/mobile/subscriber/wechatBinding.action";
					WeixinUtil.requestOauth2Code(getResponse(), "snsapi_userinfo",rechargeReferrer);
					return null;
				}else{
					
					subscriber.setWechatUnionId(unionId);
					subscriberService.updateSubscriber(subscriber);

					try {
						Map<String, String> contentMap = new HashMap<String, String>();
						contentMap.put("微信号绑定", unionId);
						SystemOperateLogUtil.sysAddOperateLog(subscriberId, null, SystemOperateLogUtil.MODEL_SUBSCRIBER_INFO,contentMap);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "绑定成功！");
					return SUCCESS;
					
				}
				
			}else{
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "已绑定微信号，不能重复绑定！");
				return SUCCESS;
			}
	
		
		

		

	}
	
	
	

	public void setWechatUserInfoService(WechatUserInfoService wechatUserInfoService) {
		this.wechatUserInfoService = wechatUserInfoService;
	}
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}
	
	
	
}
