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
public class CommissionCutPaymentSearchAction extends AbstractAction {


	private String msg;
	
	public CommissionCutPaymentSearchAction (){
		super();
		
	}
	@Override
	public String process() {
	
		return SUCCESS;
	}
	
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	

	
}
