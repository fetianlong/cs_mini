package com.dearho.cs.wechat.dao;

import java.util.List;

import com.dearho.cs.wechat.pojo.WechatUserInfo;

public interface WechatUserInfoDao {

	void addUserInfo(WechatUserInfo wechatUserInfo);
	
	void updateUserInfo(WechatUserInfo wechatUserInfo);
	
	List<WechatUserInfo> getUserInfoByHql(String hql);
}
