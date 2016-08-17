package com.dearho.cs.account.action.rechargecard;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.account.service.RechargeCardService;
import com.dearho.cs.sys.action.AbstractAction;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-17
 *
 */
public class RechargeCardGetAction extends AbstractAction  {

	
	private static final long serialVersionUID = 3530792839096296655L;


	private RechargeCardService rechargeCardService;
	private RechargeCard rechargeCard ;
	
	
	public RechargeCardGetAction(){
		super();
		rechargeCard=new RechargeCard();
	}
	
	@Override
	public String process() {
		if(!StringUtils.isEmpty(rechargeCard.getId())){
			rechargeCard=rechargeCardService.getRechargeCardById(rechargeCard.getId());
		}
		
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

	

	
	
}
