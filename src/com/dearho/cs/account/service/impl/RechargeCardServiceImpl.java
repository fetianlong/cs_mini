/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountServiceImpl.java creation date:[2015-6-1 下午02:48:52] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.dao.RechargeCardDao;
import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.account.service.RechargeCardService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.util.DateUtil;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-16
 *
 */
public class RechargeCardServiceImpl implements RechargeCardService {
	
	private RechargeCardDao rechargeCardDao;

	@Override
	public Page<RechargeCard> queryRechargeCardByPage(Page<RechargeCard> page, RechargeCard rechargeCard) {
		StringBuffer hql = new StringBuffer();
		hql.append(" from RechargeCard a where 1=1 ");
		
		if(rechargeCard!=null){
			if(!StringUtils.isEmpty(rechargeCard.getChannel())){
				hql.append("and channel like '%"+rechargeCard.getChannel()+"%'");
			}
			if(!StringUtils.isEmpty(rechargeCard.getName())){
				hql.append("and name like '%"+rechargeCard.getName()+"%'");
			}
			if(rechargeCard.getIsValid()!=null){
				hql.append("and isValid = "+rechargeCard.getIsValid()+"");
			}
		}
		hql.append(" order by sortField asc");
		return rechargeCardDao.queryRechargeCardByPage(page, hql.toString());
	}


	@Override
	public RechargeCard getRechargeCardById(String id) {
		return rechargeCardDao.getRechargeCardById(id);
	}


	@Override
	public void deleteRechargeCard(String[] checkdel) {
		
		//校验卡是否使用
		for(int i=0;i<checkdel.length;i++){
		/*	SubscriberCard subscriberCard=subscriberCardDao.getSubscriberCardById(checkdel[i]);
			if(!SubscriberCard.STATE_NORMAL.equals(subscriberCard.getState())){
				throw new RuntimeException(checkdel[i]+"非待绑定状态");
			}*/
		}
		String queryString="delete RechargeCard a where a.id in (:ids)";
		rechargeCardDao.deleteRechargeCard(queryString, checkdel);
		
	
		
	}
	@Override
	public List<RechargeCard> queryRechargeCard(RechargeCard rechargeCard) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select a.id from RechargeCard a where 1=1 ");
		
		if(rechargeCard!=null){
			if(!StringUtils.isEmpty(rechargeCard.getChannel())){
				hql.append("and channel like '%"+rechargeCard.getChannel()+"%'");
			}
			if(!StringUtils.isEmpty(rechargeCard.getName())){
				hql.append("and name like '%"+rechargeCard.getName()+"%'");
			}
			if(rechargeCard.getIsValid()!=null){
				hql.append("and isValid = "+rechargeCard.getIsValid()+"");
			}
		}
		hql.append(" order by sortField asc");
		return rechargeCardDao.queryRechargeCardList(hql.toString());
	}
	


	@Override
	public void addRechargeCard(RechargeCard rechargeCard) {
		rechargeCardDao.addRechargeCard(rechargeCard);
		
	}


	@Override
	public void updateRechargeCard(RechargeCard rechargeCard) {
		rechargeCardDao.updateRechargeCard(rechargeCard);
		
	}


	public RechargeCardDao getRechargeCardDao() {
		return rechargeCardDao;
	}


	public void setRechargeCardDao(RechargeCardDao rechargeCardDao) {
		this.rechargeCardDao = rechargeCardDao;
	}


	@Override
	public List<RechargeCard> queryEnabledRechargeCardForChannel(String channel) {
//		if(org.apache.commons.lang.StringUtils.isEmpty(channel)){
//			return null;
//		}
		StringBuffer hql = new StringBuffer();
		hql.append(" select a.id from RechargeCard a where 1=1 ");
		hql.append(" and '"+DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")+"' >= startValidTime ");
		hql.append(" and '"+DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")+"' <= endValidTime ");
		hql.append(" and isValid="+RechargeCard.IS_VALID_TRUE);
		if(!StringUtils.isEmpty(channel)){
			hql.append("and ( ");
			Boolean isFirst=true;
			if(channel.contains(RechargeCard.CHANNEL_CONSOLE)){
				isFirst=false;
				hql.append(" channel like '%"+RechargeCard.CHANNEL_CONSOLE+"%' ");
			}
			if(channel.contains(RechargeCard.CHANNEL_PORTAL)){
				if(!isFirst){
					hql.append(" or ");
				}
				isFirst=false;
				hql.append(" channel like '%"+RechargeCard.CHANNEL_PORTAL+"%' ");
			}
			if(channel.contains(RechargeCard.CHANNEL_WECHAT)){
				if(!isFirst){
					hql.append(" or ");
				}
				isFirst=false;
				hql.append(" channel like '%"+RechargeCard.CHANNEL_WECHAT+"%' ");
			}
			
			hql.append("  )");
		}
		hql.append(" order by sortField asc");
		return rechargeCardDao.queryRechargeCardList(hql.toString());
	}


	@Override
	public RechargeCard validateRechargeCard(String rechargeCardId) throws Exception {
		if(StringUtils.isEmpty(rechargeCardId)){
			throw new Exception("充值卡ID不能为空!");
		}
		
		RechargeCard rechargeCard=rechargeCardDao.getRechargeCardById(rechargeCardId);
		if(StringUtils.isEmpty(rechargeCardId)){
			throw new Exception("未查询到有效的充值卡");
		}
		if(RechargeCard.IS_VALID_FALSE.equals(rechargeCard.getIsValid())){
			throw new Exception("卡已失效!");
		}
		if(new Date().after(rechargeCard.getStartValidTime()) && new Date().before(rechargeCard.getEndValidTime())){
			
		}else{
			throw new Exception("卡已过期!");
		}
		return rechargeCard;
	}




	

	
	
}
