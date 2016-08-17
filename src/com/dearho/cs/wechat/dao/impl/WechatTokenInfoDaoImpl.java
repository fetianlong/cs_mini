package com.dearho.cs.wechat.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.wechat.dao.WechatTokenInfoDao;
import com.dearho.cs.wechat.pojo.WechatTokenInfo;

public class WechatTokenInfoDaoImpl extends AbstractDaoSupport implements WechatTokenInfoDao{

	@Override
	public void addAccessToken(WechatTokenInfo accessToken) {
		addEntity(accessToken);
		
	}

	@Override
	public void updateAccessToken(WechatTokenInfo accessToken) {
		updateEntity(accessToken);	
		
	}

	@Override
	public List<WechatTokenInfo> queryTokenByHql(String hql) {
		return getList(WechatTokenInfo.class, queryFList(hql));
	}

}
