/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountTradeRecordDaoImpl.java creation date:[2015-6-1 下午02:38:41] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.dao.impl;

import java.util.List;

import com.dearho.cs.account.dao.RechargeCardDao;
import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public class RechargeCardDaoImpl extends AbstractDaoSupport implements RechargeCardDao {


	@Override
	public void addRechargeCard(RechargeCard rechargeCard) {
		addEntity(rechargeCard);
	}


	@Override
	public void updateRechargeCard(RechargeCard rechargeCard) {
		updateEntity(rechargeCard);
	}

	@Override
	public Page<RechargeCard> queryRechargeCardByPage(Page<RechargeCard> page, String hql) {
		Page<RechargeCard> resultPage=pageCache(RechargeCard.class, page, hql);
		
		return resultPage;
	}


	@Override
	public RechargeCard getRechargeCardById(String id) {
		return get(RechargeCard.class, id);
	}


	@Override
	public List<RechargeCard> queryRechargeCardList(String hql) {
		return getList(RechargeCard.class, queryFList(hql));
	}
	
	@Override
	public void deleteRechargeCard(String queryString,String[] checkdel) {
		deleteEntity(RechargeCard.class, queryString, checkdel);
	}



}
