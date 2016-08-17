/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountGetAction.java creation date:[2015-6-1 下午03:02:26] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.action.center;

import java.util.List;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.service.AccountTradeRecordService;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Constants;

import net.sf.json.JSONObject;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public class AccountTradingRecordGetAction extends AbstractAction {

	
	
	private static final long serialVersionUID = -4365773690582053808L;

	private AccountTradeRecordService accountTradeRecordService;

	private OrdersService ordersService;

	private AccountTradeRecord accountTradeRecord;

	
	
	public AccountTradingRecordGetAction(){
		super();
		accountTradeRecord=new AccountTradeRecord();
	}
	
	

	@Override
	public String process() {
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		Subscriber subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		
		JSONObject jsonObject=new JSONObject();
		accountTradeRecord=accountTradeRecordService.getAccountTradeRecordById(accountTradeRecord.getId());
//		jsonObject.element("amount", formatAmount(accountTradeRecord.getAmount()));
		jsonObject.element("type", Account.getTradeType(accountTradeRecord.getType()));
		jsonObject.element("tradeTime", transDateString(accountTradeRecord.getTradeTime()));
		
		
//		AccountCurrentAccount accountCurrentAccount=null;
		if(accountTradeRecord!=null){
			if(Account.TYPE_REFUND.equals(accountTradeRecord.getType()) || Account.TYPE_RECHARGE.equals(accountTradeRecord.getType()) || 
					Account.TYPE_CARE_BINDING.equals(accountTradeRecord.getType()) || Account.TYPE_CARD_UNBINDING.equals(accountTradeRecord.getType()) ){
				/*accountCurrentAccount=accountCurrentAccountService.getAccountCurrentAccountById(accountTradeRecord.getBizId());
				
				jsonObject.element("alipayAccount", accountCurrentAccount.getAccount());
				if(Account.PAY_TYPE_ALIPAY.equals(accountCurrentAccount.getPayType())){
					jsonObject.element("payType", "支付宝");
				}else if(Account.PAY_TYPE_CREDIT.equals(accountCurrentAccount.getPayType())){
					jsonObject.element("payType", "信用卡");
				}*/
			}else if(Account.TYPE_ORDER.equals(accountTradeRecord.getType())){
				//订单消费
				
				jsonObject.element("orderId", accountTradeRecord.getBizId());
				
				Orders order= new Orders();
				order.setId( accountTradeRecord.getBizId());
				List<Orders> list=ordersService.queryOrdersByCon(order);
				if(list!=null && list.size()>0){
					order=list.get(0);
					jsonObject.element("payType", new Integer(1).equals(order.getPayStyle())?"账户":"信用卡");
				}
				
				jsonObject.element("orderAutoClear", new Integer(1).equals(accountTradeRecord.getIsAutoClear())?"是":"否");
			}
			
		}
		jsonObject.element("result",Constants.RESULT_CODE_SUCCESS );
		result=jsonObject.toString();
		
		
		
		return SUCCESS;
	}
	
	
	

public String gotoTradingRecord(){
	if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
		return LOGIN;
	}
	
	accountTradeRecord=accountTradeRecordService.getAccountTradeRecordById(accountTradeRecord.getId());

//	getRequest().setAttribute("amount", formatAmount(accountTradeRecord.getAmount()));
	getRequest().setAttribute("type", Account.getTradeType(accountTradeRecord.getType()));
	getRequest().setAttribute("tradeTime", transDateString(accountTradeRecord.getTradeTime()));
	
	
//	AccountCurrentAccount accountCurrentAccount=null;
	if(accountTradeRecord!=null){
		if(Account.TYPE_REFUND.equals(accountTradeRecord.getType()) || Account.TYPE_RECHARGE.equals(accountTradeRecord.getType()) || 
				Account.TYPE_CARE_BINDING.equals(accountTradeRecord.getType()) || Account.TYPE_CARD_UNBINDING.equals(accountTradeRecord.getType()) ){
			/*accountCurrentAccount=accountCurrentAccountService.getAccountCurrentAccountById(accountTradeRecord.getBizId());
			
			getRequest().setAttribute("alipayAccount", accountCurrentAccount.getAccount());
			if(Account.PAY_TYPE_ALIPAY.equals(accountCurrentAccount.getPayType())){
				getRequest().setAttribute("payType", "支付宝");
			}else if(Account.PAY_TYPE_CREDIT.equals(accountCurrentAccount.getPayType())){
				getRequest().setAttribute("payType", "信用卡");
			}*/
		}else if(Account.TYPE_ORDER.equals(accountTradeRecord.getType())){
			//订单消费
			
//			getRequest().setAttribute("orderId", accountTradeRecord.getOrderId());
			
			Orders order= new Orders();
//			order.setId( accountTradeRecord.getOrderId());
			List<Orders> list=ordersService.queryOrdersByCon(order);
			if(list!=null && list.size()>0){
				order=list.get(0);
				getRequest().setAttribute("payType", "1".equals(order.getPayStyle())?"账户":"信用卡");
				
				//信用卡支付账号
				if("2".equals(order.getPayStyle())){
/*					accountCurrentAccount=accountCurrentAccountService.getAccountCurrentAccountById(accountTradeRecord.getBizId());
					getRequest().setAttribute("alipayAccount", accountCurrentAccount.getAccount());
*/				}
			}
			
			getRequest().setAttribute("orderAutoClear", new Integer(1).equals(accountTradeRecord.getIsAutoClear())?"是":"否");
		}
		
	}
	
	
	return SUCCESS;
}



	public AccountTradeRecordService getAccountTradeRecordService() {
		return accountTradeRecordService;
	}



	public void setAccountTradeRecordService(
			AccountTradeRecordService accountTradeRecordService) {
		this.accountTradeRecordService = accountTradeRecordService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	
	
}
