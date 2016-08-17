/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountService.java creation date:[2015-6-1 下午02:47:34] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.service;

import java.util.List;

import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.core.db.page.Page;


/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-16
 *
 */
public interface RechargeCardService {
	public Page<RechargeCard> queryRechargeCardByPage(Page<RechargeCard> page,RechargeCard rechargeCard) ;
	public RechargeCard       getRechargeCardById(String id);
	public void deleteRechargeCard(String[] checkdel);
	public void addRechargeCard(RechargeCard rechargeCard);
	public void updateRechargeCard(RechargeCard card);
	public List<RechargeCard> queryRechargeCard(RechargeCard rechargeCard);
	public List<RechargeCard> queryEnabledRechargeCardForChannel(String channel);
	
	/**
	 * 验证充值卡有效性
	 * @param rechargeCardId
	 * @return
	 * @throws Exception
	 */
	public RechargeCard validateRechargeCard(String rechargeCardId) throws Exception;
	
}
