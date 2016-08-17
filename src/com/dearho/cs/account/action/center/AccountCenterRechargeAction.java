/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountGetAction.java creation date:[2015-6-1 下午03:02:26] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.action.center;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.account.service.PayService;
import com.dearho.cs.account.service.RechargeCardService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.service.WechatUserInfoService;
import com.dearho.cs.wechat.util.WeixinUtil;
import com.dearho.cs.wxpay.WxPayUtil;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public class AccountCenterRechargeAction extends AbstractAction {

	public static final Log logger = LogFactory.getLog(AccountCenterRechargeAction.class);
	
	private static final long serialVersionUID = -4365773690582053808L;
	private AccountService accountService;
	private RechargeCardService  rechargeCardService;
	private PayService payService;
	private WechatUserInfoService wechatUserInfoService;
	
	private Account account;
	List<RechargeCard> rechargeCardList;
	
	
	
	private String retMsg;
	public AccountCenterRechargeAction(){
		super();
		account= new Account();
	}

	@Override
	public String process() {
		
		Subscriber subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		
		
		//参数校验
		String customAmountStr=getRequest().getParameter("customAmount");
		double customAmount=0;
		String metadata=getRequest().getParameter("isMetaData");
		String rechargeCardId=	getRequest().getParameter("rechargeCardId");
				
		if("Y".equals(metadata)){
			RechargeCard rechargeCard=rechargeCardService.getRechargeCardById(rechargeCardId);
			if(rechargeCard==null){
				retMsg="根据充值卡ID未找到充值卡";
				return "fail";
			}else if(!rechargeCard.getChannel().contains(RechargeCard.CHANNEL_WECHAT)){
				retMsg="充值卡使用渠道有误";
				return "fail";
			}
			
			
			
		}else{
			try{
				customAmount=Double.parseDouble(customAmountStr);
				rechargeCardId=null;
			}catch(Exception e){
				retMsg="充值金额有误请重新输入";
				return "fail";
			}
			if(customAmount<1 || customAmount>10000){
				retMsg="请输入1-10000以内的整数";
				return "fail";
			}
		}
		
		String payType=	getRequest().getParameter("payType");
		
		//微信支付
		if(Account.PAY_TYPE_WECHAT.toString().equals(payType)){
			
				String openId=	getRequest().getParameter("openId");
				
				if(StringUtils.isEmpty(openId)){
					String rechargeReferrer="/rechargePrepareServlet?payType="+payType+"&customAmount="+customAmount+"&isMetaData="+metadata+"&rechargeCardId="+rechargeCardId+"&url=/mobile/online/payRecharge.action";
					WeixinUtil.requestOauth2Code(getResponse(), "snsapi_userinfo",rechargeReferrer);
					return null;
				}
				
				WechatUserInfo wechatUserInfo = wechatUserInfoService.getUserInfoBySubscriberId(subscriber.getId());
				if(wechatUserInfo!=null){
					if(!wechatUserInfo.getOpenId().equals(openId)){
						retMsg="支付账号与绑定账号不一致,请先解绑或者使用绑定账号支付";
						return "fail";
					}else{
						openId=wechatUserInfo.getOpenId();
					}
				}
			
			
				//先创建订单
				AccountTradeRecord accountTradeRecord=null;
				try {
					accountTradeRecord=payService.createRechargeRecord(subscriber.getId(), rechargeCardId,customAmount, Account.PAY_CHANNEL_WECHAT, Account.PAY_TYPE_WECHAT);
				} catch (Exception e) {
					logger.error(e);
					retMsg="创建充值订单失败";
					return "fail";
				}

				String body=accountTradeRecord.getDescription();
				String orderNo=accountTradeRecord.getTradeOrderNo();
				try {
					WxPayUtil.recharge(openId, body, orderNo, accountTradeRecord.getAmount(),accountTradeRecord.getTradeTime(),getRequest(), getResponse());
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e);
					retMsg="微信统一下单失败："+e.getMessage();
					return "fail";
				}
				
				return "wx_pay_success";
			

			
		}else{
			retMsg="未知的支付渠道!";
			return "fail";
		}
		

	}
	
	
	public String gotoRecharge(){
		
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		
		Subscriber s=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		/*WechatUserInfo wechatUserInfo = wechatUserInfoService.getUserInfoBySubscriberId(s.getId());
		
		if(wechatUserInfo==null){
			WeixinUtil.requestOauth2Code(getResponse(), "snsapi_userinfo");
			return null;
		}*/
		rechargeCardList=rechargeCardService.queryEnabledRechargeCardForChannel(RechargeCard.CHANNEL_WECHAT);
		
		
		account=accountService.getAccoutDetail(s.getId());
		return SUCCESS;
	}
	
	public String gotoRechargeCheck(){
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		
		Subscriber s=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
	
		
		/** 状态校验 start**/
		if(Subscriber.EVENT_STATE_HALF.equals(s.getEventState())){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "此账号已冻结，不能进行充值操作。<br/> 如有疑问，请联系客服。");
			return SUCCESS;
		}
		if(Subscriber.STATE_WAIT_CONFIRMED.equals(s.getState()) || Subscriber.STATE_NO_CONFIRMED.equals(s.getState()) || Subscriber.STATE_UNCONFIRMED.equals(s.getState())){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "帐户资料未审核通过，不能进行充值操作!");
			return SUCCESS;
		}
		Account account=accountService.getAccoutDetail(s.getId());
		if (Account.IS_REFUND_TRUE.equals(account.getIsRefund())) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "退款流程处理中，不能充值!");
			return SUCCESS;
		}
		/** 状态校验end**/
		
		
		result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "成功");
		
		return SUCCESS;
	}
	
	public String continueRecharge(){
		
		String rechargeAmount=	getRequest().getParameter("rechargeAmount");
		if(StringUtils.isEmpty(rechargeAmount)){
			retMsg="充值金额不能为空!";
			return "fail";
		}
		getRequest().setAttribute("rechargeAmount", rechargeAmount);
		Map<String,String> map=DictUtil.getDictMapByGroupCode("11");
		if(!map.containsKey(rechargeAmount)){
			retMsg="金额非元数据!";
			return "fail";
		}
		
		return SUCCESS;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}


	public RechargeCardService getRechargeCardService() {
		return rechargeCardService;
	}

	public void setRechargeCardService(RechargeCardService rechargeCardService) {
		this.rechargeCardService = rechargeCardService;
	}

	public List<RechargeCard> getRechargeCardList() {
		return rechargeCardList;
	}

	public void setRechargeCardList(List<RechargeCard> rechargeCardList) {
		this.rechargeCardList = rechargeCardList;
	}
	
	public PayService getPayService() {
		return payService;
	}

	public void setPayService(PayService payService) {
		this.payService = payService;
	}

	public WechatUserInfoService getWechatUserInfoService() {
		return wechatUserInfoService;
	}

	public void setWechatUserInfoService(WechatUserInfoService wechatUserInfoService) {
		this.wechatUserInfoService = wechatUserInfoService;
	}

	
    
	
	
	
}
