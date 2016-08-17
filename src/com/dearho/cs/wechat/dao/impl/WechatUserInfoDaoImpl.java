package com.dearho.cs.wechat.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.wechat.dao.WechatUserInfoDao;
import com.dearho.cs.wechat.pojo.WechatUserInfo;

public class WechatUserInfoDaoImpl extends AbstractDaoSupport implements WechatUserInfoDao{

	@Override
	public void addUserInfo(WechatUserInfo wechatUserInfo) {
		addEntity(wechatUserInfo);
		
	}

	@Override
	public void updateUserInfo(WechatUserInfo wechatUserInfo) {
		updateEntity(wechatUserInfo);
		
	}

	@Override
	public List<WechatUserInfo> getUserInfoByHql(String hql) {
		return getList(WechatUserInfo.class, queryFList(hql));
	}

}
