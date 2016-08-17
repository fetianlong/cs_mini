package com.dearho.cs.wechat.service.impl;

import java.util.List;

import com.dearho.cs.subscriber.dao.SubscriberDao;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.wechat.dao.WechatUserInfoDao;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.service.WechatUserInfoService;

public class WechatUserInfoServiceImpl implements WechatUserInfoService{

	private WechatUserInfoDao wechatUserInfoDao;
	
	private SubscriberDao subscriberDao;

	public SubscriberDao getSubscriberDao() {
		return subscriberDao;
	}

	public void setSubscriberDao(SubscriberDao subscriberDao) {
		this.subscriberDao = subscriberDao;
	}

	public WechatUserInfoDao getWechatUserInfoDao() {
		return wechatUserInfoDao;
	}

	public void setWechatUserInfoDao(WechatUserInfoDao wechatUserInfoDao) {
		this.wechatUserInfoDao = wechatUserInfoDao;
	}

	@Override
	public void addUserInfo(WechatUserInfo wechatUserInfo) {
		wechatUserInfoDao.addUserInfo(wechatUserInfo);
		
	}

	@Override
	public void updateUserInfo(WechatUserInfo wechatUserInfo) {
		wechatUserInfoDao.updateUserInfo(wechatUserInfo);
		
	}

	@Override
	public WechatUserInfo getUserInfoByOpenId(String openid) {
		WechatUserInfo userInfo = null;
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from WechatUserInfo a where 1=1 and a.openId = '"+openid+"' order by createTime DESC");
		List<WechatUserInfo> list = wechatUserInfoDao.getUserInfoByHql(sb.toString());
		if(list != null && list.size()>0){
			userInfo = list.get(0);
		}
		return userInfo;
	}
	
/*	*//**
	 * 根据openId获取会员信息
	 * @param openid
	 * @return
	 *//*
	public Subscriber getSubscriberByOpenId(String openid) {
		Subscriber subscriber = null;
		StringBuffer sb=new StringBuffer();
		sb.append("select s.id from Subscriber s , WechatUserInfo w where  w.openId = '"+openid+"' and w.unionId = s.wechatUnionId ");
		List<Subscriber> list = subscriberDao.getSubscriberByHql(sb.toString());
		if(list != null && list.size()>0){
			subscriber = list.get(0);
		}
		return subscriber;
	}*/
	
	@Override
	public WechatUserInfo getUserInfoBySubscriberId(String subscriberId) {
		WechatUserInfo userInfo = null;
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from WechatUserInfo a where 1=1 and a.subscriberId = '"+subscriberId+"'");
		List<WechatUserInfo> list = wechatUserInfoDao.getUserInfoByHql(sb.toString());
		if(list != null && list.size()>0){
			userInfo = list.get(0);
		}
		return userInfo;
	}

@Override
public WechatUserInfo getUserInfoByUnionId(String unionid) {
	WechatUserInfo userInfo = null;
	StringBuffer sb=new StringBuffer();
	sb.append("select a.id from WechatUserInfo a where 1=1 and a.unionId = '"+unionid+"' order by createTime DESC");
	List<WechatUserInfo> list = wechatUserInfoDao.getUserInfoByHql(sb.toString());
	if(list != null && list.size()>0){
		userInfo = list.get(0);
	}
	return userInfo;
}
}
