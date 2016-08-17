package com.dearho.cs.wechat.dao;

import java.util.List;

import com.dearho.cs.wechat.pojo.WechatTokenInfo;

public interface WechatTokenInfoDao {

	void addAccessToken(WechatTokenInfo accessToken);
	
	void updateAccessToken(WechatTokenInfo accessToken);
	
	List<WechatTokenInfo> queryTokenByHql(String string);
}
