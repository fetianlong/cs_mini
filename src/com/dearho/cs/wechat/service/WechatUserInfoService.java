package com.dearho.cs.wechat.service;

import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.wechat.pojo.WechatUserInfo;

public interface WechatUserInfoService {

	/**
	 * 增
	 * @param accessToken
	 */
	void addUserInfo(WechatUserInfo wechatUserInfo);

	/**
	 * 改
	 * @param accessToken
	 */
	void updateUserInfo(WechatUserInfo wechatUserInfo);

	/**
	 * 查
	 * @param key 
	 * @param accessToken
	 */
	WechatUserInfo getUserInfoByOpenId(String key);
	
	//Subscriber getSubscriberByOpenId(String openid);
	
	WechatUserInfo getUserInfoBySubscriberId(String key);
	

	WechatUserInfo getUserInfoByUnionId(String unionid);
}
