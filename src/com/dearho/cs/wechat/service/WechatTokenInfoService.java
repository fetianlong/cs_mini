package com.dearho.cs.wechat.service;

import com.dearho.cs.wechat.pojo.WechatTokenInfo;

public interface WechatTokenInfoService {

	/**
	 * 增
	 * @param accessToken
	 */
	void addAccessToken(WechatTokenInfo accessToken);

	/**
	 * 改
	 * @param accessToken
	 */
	void updateAccessToken(WechatTokenInfo accessToken);

	/**
	 * 查
	 * @param key 
	 * @param accessToken
	 */
	WechatTokenInfo getAccessToken(String key);
}
