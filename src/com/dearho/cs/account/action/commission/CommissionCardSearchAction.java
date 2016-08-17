package com.dearho.cs.account.action.commission;

import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.account.service.RechargeCardService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-17
 *
 */
public class CommissionCardSearchAction extends AbstractAction {

	private static final long serialVersionUID = -267403983716467437L;
	private RechargeCardService rechargeCardService;
	private SubscriberService subscriberService;
	private RechargeCard rechargeCard ;
	private List<RechargeCard> rechargeCardList ;
	private Subscriber subscriber;
	private String msg;
	
	public CommissionCardSearchAction (){
		super();
		rechargeCard=new RechargeCard();
	}
	@Override
	public String process() {
		rechargeCardList=rechargeCardService.queryEnabledRechargeCardForChannel(RechargeCard.CHANNEL_CONSOLE);
		
		return SUCCESS;
	}
	
	public String searchSubscriberInfo(){
		if(StringUtils.isEmpty(subscriber.getPhoneNo())){
			return SUCCESS;
		}
		subscriber=subscriberService.querySubscriberByPhoneNo(subscriber.getPhoneNo());
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
	public List<RechargeCard> getRechargeCardList() {
		return rechargeCardList;
	}
	public void setRechargeCardList(List<RechargeCard> rechargeCardList) {
		this.rechargeCardList = rechargeCardList;
	}
	public SubscriberService getSubscriberService() {
		return subscriberService;
	}
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}
	public Subscriber getSubscriber() {
		return subscriber;
	}
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	

	
}
