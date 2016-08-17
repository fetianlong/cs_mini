package com.dearho.cs.wechat.service.impl;

import java.util.List;

import com.dearho.cs.wechat.dao.WechatTokenInfoDao;
import com.dearho.cs.wechat.pojo.WechatTokenInfo;
import com.dearho.cs.wechat.service.WechatTokenInfoService;

public class WechatTokenInfoServiceImpl implements WechatTokenInfoService{

	private WechatTokenInfoDao wechatTokenInfoDao;
	

	@Override
	public void addAccessToken(WechatTokenInfo accessToken) {
		wechatTokenInfoDao.addAccessToken(accessToken);
	}

	@Override
	public void updateAccessToken(WechatTokenInfo accessToken) {
		wechatTokenInfoDao.updateAccessToken(accessToken);
		
	}

	@Override
	public WechatTokenInfo getAccessToken(String key) {
		WechatTokenInfo token = null;
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from WechatTokenInfo a where 1=1 and wx_key = '"+key+"' order by createTime DESC");
		List<WechatTokenInfo> list = wechatTokenInfoDao.queryTokenByHql(sb.toString());
		if(list != null && list.size()>0){
			token = list.get(0);
		}
		return token;
	}

	public WechatTokenInfoDao getWechatTokenInfoDao() {
		return wechatTokenInfoDao;
	}

	public void setWechatTokenInfoDao(WechatTokenInfoDao wechatTokenInfoDao) {
		this.wechatTokenInfoDao = wechatTokenInfoDao;
	}

}
