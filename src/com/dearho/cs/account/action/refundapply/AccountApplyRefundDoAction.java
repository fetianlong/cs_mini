package com.dearho.cs.account.action.refundapply;


import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.service.RefundService;
import com.dearho.cs.subscriber.pojo.SubscriberConfirm;
import com.dearho.cs.subscriber.service.SubscriberConfirmService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.BusinessFlow;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.BusinessFlowService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

/**
 * @Author liusong
 * @Description 用户信息审核
 * @Version 1.0,2015-5-18
 *
 */
public class AccountApplyRefundDoAction extends AbstractAction {
	
	private static final long serialVersionUID = -8695368288549126666L;
	private SubscriberConfirmService subscriberConfirmService;
	
	private BusinessFlowService  businessFlowService;
//	private AccountService accountService;
	private SubscriberConfirm subscriberConfirm ;
	private RefundService refundService;
	
	public AccountApplyRefundDoAction() {
		super();
		subscriberConfirm=new SubscriberConfirm();
		
	}
		

	@Override
	public String process()  {
	
		
		
		User operator=(User)getSession().getAttribute(Constants.SESSION_USER);
		
		Integer checkResult=subscriberConfirm.getResult();
		SubscriberConfirm confirm=subscriberConfirmService.querySubscriberConfirmById(subscriberConfirm.getId());
		
		try {
			
			if(SubscriberConfirm.IS_COMPLETE_FALSE.equals(confirm.getIsComplete())){
				
				if(SubscriberConfirm.RESULT_SUCCESS.equals(checkResult)){
					AccountTradeRecord mainTradeRecord=refundService.applyRefundSuccess(subscriberConfirm, operator.getId());
					refundService.handleRefundRecord(mainTradeRecord.getTradeOrderNo(),getRequest(),getResponse());
				}else{
					refundService.applyRefundFail(subscriberConfirm, operator);
				}
				
				result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"操作成功");
			}else if(SubscriberConfirm.IS_COMPLETE_EXCEPTION.equals(confirm.getIsComplete())){
				//退款出现异常后
				BusinessFlow businessFlow=businessFlowService.getBusinessFlowByConfirmId(confirm.getId());
				refundService.handleRefundRecord(businessFlow.getBusinessId(),getRequest(),getResponse());
			}else if(SubscriberConfirm.IS_COMPLETE_TRUE.equals(confirm.getIsComplete())){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"已审核完结无需处理");
			}else{
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"未知的审核状态");
			}
			
			
		} catch (Exception e) {
			subscriberConfirm=subscriberConfirmService.querySubscriberConfirmById(subscriberConfirm.getId());
			subscriberConfirm.setIsComplete(SubscriberConfirm.IS_COMPLETE_EXCEPTION);
			subscriberConfirmService.update(subscriberConfirm);
			e.printStackTrace();
			//TODO 建立异常单
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"退款失败");
		}
		
	
		/*
		User operator=(User)getSession().getAttribute(Constants.SESSION_USER);
		try {
			subscriberConfirmService.confirm(subscriberConfirm, operator);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		*/
		
		return SUCCESS;
		
	}


	public SubscriberConfirm getSubscriberConfirm() {
		return subscriberConfirm;
	}


	public void setSubscriberConfirm(SubscriberConfirm subscriberConfirm) {
		this.subscriberConfirm = subscriberConfirm;
	}


	public void setSubscriberConfirmService(
			SubscriberConfirmService subscriberConfirmService) {
		this.subscriberConfirmService = subscriberConfirmService;
	}


	public void setRefundService(RefundService refundService) {
		this.refundService = refundService;
	}


	public void setBusinessFlowService(BusinessFlowService businessFlowService) {
		this.businessFlowService = businessFlowService;
	}


	
	

	

	

	
}
