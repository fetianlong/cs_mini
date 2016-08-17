package com.dearho.cs.account.action.rechargecard;

import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.account.service.RechargeCardService;

/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberInfoSearchAction.java creation date:[2015-5-20 上午09:27:25] by liusong
 *http://www.dearho.com
 */


import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-17
 *
 */
public class RechargeCardSearchAction extends AbstractAction {


	private RechargeCardService rechargeCardService;
	private RechargeCard rechargeCard ;
	private Page<RechargeCard> page=new Page<RechargeCard>();
	public RechargeCardSearchAction (){
		super();
		rechargeCard=new RechargeCard();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	@Override
	public String process() {
		page=rechargeCardService.queryRechargeCardByPage(page, rechargeCard);
		
		return SUCCESS;
	}
	public RechargeCardService getRechargeCardService() {
		return rechargeCardService;
	}
	public void setRechargeCardService(RechargeCardService rechargeCardService) {
		this.rechargeCardService = rechargeCardService;
	}
	public RechargeCard getRechargeCard() {
		return rechargeCard;
	}
	public void setRechargeCard(RechargeCard rechargeCard) {
		this.rechargeCard = rechargeCard;
	}
	public Page<RechargeCard> getPage() {
		return page;
	}
	public void setPage(Page<RechargeCard> page) {
		this.page = page;
	}
	
	
	

	
}
