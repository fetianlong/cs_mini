package com.dearho.cs.appinterface.subscriber;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.action.center.AccountCenterRechargeAction;
import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.AccountPaymentAccount;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.account.service.PayService;
import com.dearho.cs.account.service.RechargeCardService;
import com.dearho.cs.account.service.RefundService;
import com.dearho.cs.appinterface.subscriber.vo.RechargeCardVo;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.pojo.SubscriberLoginRecord;
import com.dearho.cs.subscriber.service.SubscriberLoginRecordService;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.SMSCode;
import com.dearho.cs.sys.service.SMSCodeService;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.CookieTool;
import com.dearho.cs.util.IpUtil;
import com.dearho.cs.wechat.service.WechatUserInfoService;
import com.dearho.cs.wxpay.WxPayUtil;
/**
 * @Author liusong
 * @Description 会员注册第一步 
 * @Version 1.0,2015-5-18
 *
 */
public class AppAccountAction extends AbstractAction {

	public static final Log logger = LogFactory.getLog(AccountCenterRechargeAction.class);
	
	private static final long serialVersionUID = -4365773690582053808L;
	private AccountService accountService;
	private RechargeCardService  rechargeCardService;
	private PayService payService;
	private WechatUserInfoService wechatUserInfoService;
	private SubscriberService subscriberService;
	private SMSCodeService smsCodeService;
	private SubscriberLoginRecordService subscriberLoginRecordService;
	
	private RefundService refundService;

	public AppAccountAction(){
		super();
	}

	public String process() {
		return SUCCESS;
	}
	
	
	/**
	 * 申请退款校验
	 * @return
	 */
	public String applyRefundCheck() {
		/**
		 * 会员判空
		 */
		String  subscriberId=CookieTool.getAppSubscriberToken(getRequest());
		
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
		Subscriber subscriber=subscriberService.querySubscriberById(subscriberId);
		
		if(subscriber==null){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
		try {
			refundService.checkApplyRefund(subscriber.getId());
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, "成功");
		} catch (Exception e) {
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL,e.getMessage());
		}
		return SUCCESS;
	}
	
	
	
	public String applyRefund(){

		/**会员判空 */
		String  subscriberId=CookieTool.getAppSubscriberToken(getRequest());
		
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
		Subscriber subscriber=subscriberService.querySubscriberById(subscriberId);
		
		if(subscriber==null){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
		
		/**申请退款校验*/
		try {
			refundService.checkApplyRefund(subscriber.getId());
		} catch (Exception e) {
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL,e.getMessage());
		}
		
		
		String paymentAccountType=getRequest().getParameter("type");
		
		String openId=getRequest().getParameter("openId");
		String alipayNo=getRequest().getParameter("alipayNo");
		
		
		AccountPaymentAccount 	paymentAccount=new AccountPaymentAccount();
		try {
			Boolean isNeedRefundAccount=refundService.checkIsNeedRefundAccount(subscriber.getId());
			String paymentAccountId=null;
			if(isNeedRefundAccount){
				if(StringUtils.isEmpty(paymentAccountType)){
					result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "请选择退款渠道");
					return SUCCESS;
				}
				
				if(Account.PAY_TYPE_ALIPAY.equals(paymentAccountType)){
					if(StringUtils.isEmpty(alipayNo)){
						result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "支付宝账号不能为空");
						return SUCCESS;
					}
					
					 paymentAccount.setAccountNo(alipayNo);
				}else if(Account.PAY_TYPE_WECHAT.equals(paymentAccountType)){

					if(StringUtils.isEmpty(openId)){
						result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "未绑定微信账号不能选择微信渠道退款");
						return SUCCESS;
					}
					
					 paymentAccount.setAccountNo(openId);
				}
				
				accountService.addAccountPaymentAccount(paymentAccount);
				paymentAccountId=paymentAccount.getId();
			}

			refundService.checkApplyRefund(subscriber.getId());
			refundService.applyRefund(subscriber.getId(), paymentAccountId);
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, "成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, e.getMessage());
		}
		
		return SUCCESS;
	
	}
	
	
	
	/**
	 * 支付宝
	 * service="mobile.securitypay.pay"
	 * &partner="2088801273866834"
	 * &_input_charset="UTF-8"
	 * &out_trade_no="20160304145301"
	 * &subject="DCloud项目捐赠"
	 * &payment_type="1"
	 * &seller_id="payservice@dcloud.io"
	 * &total_fee="1"
	 * &body="DCloud致力于打造HTML5最好的移动开发工具，包括终端的Runtime、云端的服务和IDE，同时提供各项配套的开发者服务。"
	 * &it_b_pay="1d"
	 * &notify_url="http%3A%2F%2Fdemo.dcloud.net.cn%2Fpayment%2Falipay%2Fnotify.php"
	 * &show_url="http%3A%2F%2Fwww.dcloud.io%2Fhelloh5%2F"
	 * &sign="ldqk4xXZuY3CEPZ5Js0wT19d%2BvvgFW%2FRws%2F7%2F5oYBHgeeImfMh5%2F7KNVjn4n7SbHfnH31SjM6s371W6HbFMUNFyXtIZdfcIKTJxlS2evw6AfIC4dnQUddpdLbH1SJNG5lDXLDcClR1S9EwGpydAxoeeg2xlxStNifP%2FHCDJVQp4%3D"
	 * &sign_type="RSA"
	 */
	
	
	/**
	 * 微信支付返回参数
	 * 
	 * {	
	 * 		"appid":"wx0411fa6a39d61297",
	 * 		"noncestr":"XXEldK6keoiMzkfu",
	 * 		"package":"Sign=WXPay",
	 * 		"partnerid":"1230636401",
	 * 		"prepayid":"wx201603041458146781d769790248981444",
	 * 		"timestamp":1457074694,
	 * 		"sign":"7AA62DD9E86C24C6F671208F29F75BB1"
	 * }
	 */
	
	
	/**
	 * 充值
	 * @return
	 */
	public String recharge() {
		/**
		 * 会员判空
		 */	
		
		String  subscriberId=CookieTool.getAppSubscriberToken(getRequest());
		
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
		Subscriber subscriber=subscriberService.querySubscriberById(subscriberId);
		
	//	Subscriber subscriber=subscriberService.querySubscriberByPhoneNo("15210660866");
		
		if(subscriber==null){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
		

		/** 会员状态校验 start**/
		if(Subscriber.EVENT_STATE_HALF.equals(subscriber.getEventState())){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "此账号已冻结，不能进行充值操作。<br/> 如有疑问，请联系客服。");
			return SUCCESS;
		}
		if(Subscriber.STATE_WAIT_CONFIRMED.equals(subscriber.getState()) || Subscriber.STATE_NO_CONFIRMED.equals(subscriber.getState()) || Subscriber.STATE_UNCONFIRMED.equals(subscriber.getState())){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "帐户资料未审核通过，不能进行充值操作!");
			return SUCCESS;
		}
		Account account=accountService.getAccoutDetail(subscriber.getId());
		if (Account.IS_REFUND_TRUE.equals(account.getIsRefund())) {
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "退款流程处理中，不能充值!");
			return SUCCESS;
		}
		
		
		
		/**参数校验**/
		String amountStr=getRequest().getParameter("total");
		String payType=	getRequest().getParameter("payType");
		//微信支付
		Double	amount=null;
	

		try{
				amount=Double.parseDouble(amountStr);			
		}catch(Exception e){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "充值金额有误请重新输入");
			return SUCCESS;
		}
		
		//微信支付
		if("wxpay".equals(payType)){
				//先创建订单
				AccountTradeRecord accountTradeRecord=null;
				try {
					accountTradeRecord=payService.createRechargeRecord(subscriber.getId(), null,amount, Account.PAY_CHANNEL_WECHAT, Account.PAY_TYPE_WECHAT);
				} catch (Exception e) {
					logger.error(e);
					result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "创建充值订单失败");
					return SUCCESS;
				}
				
				String body=accountTradeRecord.getDescription();
				String orderNo=accountTradeRecord.getTradeOrderNo();
				try {
					WxPayUtil.rechargeForApp(body, orderNo, accountTradeRecord.getAmount(),accountTradeRecord.getTradeTime(),getRequest(), getResponse());
					
					result=getRequest().getParameter("jsonResult");
				} catch (Exception e) {
					e.printStackTrace();
					result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "微信统一下单失败");
					return "fail";
				}
				
				return SUCCESS;
			

		}else if("alipay".equals(payType)){

			//先创建订单
			AccountTradeRecord accountTradeRecord=null;
			try {
				accountTradeRecord=payService.createRechargeRecord(subscriber.getId(), null, amount,Account.PAY_CHANNEL_APP, Account.PAY_TYPE_ALIPAY);
			} catch (Exception e) {
				logger.error(e);
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "创建充值订单失败");
				return SUCCESS;
			}
			
			//如无异常则跳转至银行付款页面
			/*try {
				String orderId=accountTradeRecord.getTradeOrderNo();
				String subject=accountTradeRecord.getDescription();
				String body=null;
				result=AlipayUtil.rechargeApp(orderId, subject, formatAmount(amount), body,accountTradeRecord.getTradeTime(), getRequest(), getResponse());
				return SUCCESS;
			} catch (IOException e) {
				logger.error(e);
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "支付宝跳转失败");
				return SUCCESS;
			}*/
			return SUCCESS;
		
			
		}else{
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "未知的支付渠道");
			return SUCCESS;
		}
				


	}
	
	
	
	
	

	
	
	
	//==================
	
	
	
	/**
	 * 充值金额默认选项查询
	 * @return
	 */
	public String searchRechargeList(){
		List<RechargeCard> rechargeCardList=rechargeCardService.queryEnabledRechargeCardForChannel(RechargeCard.CHANNEL_WECHAT);
		
		List<RechargeCardVo>rechargeCardVoList=new ArrayList<RechargeCardVo>();
		
		if(rechargeCardList!=null && rechargeCardList.size()>0){
			for(RechargeCard rechargeCard:rechargeCardList){		
				rechargeCardVoList.add(new RechargeCardVo(rechargeCard));
			}
		}
		result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, rechargeCardVoList);
		return SUCCESS;
		
	}
	
	
	/**
	 * 充值校验
	 * @return
	 */
	public String rechargeCheck(){
		String  subscriberId=CookieTool.getAppSubscriberToken(getRequest());
		
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
		Subscriber subscriber=subscriberService.querySubscriberById(subscriberId);
		
		if(subscriber==null){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
	
		
		/** 状态校验 start**/
		if(Subscriber.EVENT_STATE_HALF.equals(subscriber.getEventState())){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "此账号已冻结，不能进行充值操作。<br/> 如有疑问，请联系客服。");
			return SUCCESS;
		}
		if(Subscriber.STATE_WAIT_CONFIRMED.equals(subscriber.getState()) || Subscriber.STATE_NO_CONFIRMED.equals(subscriber.getState()) || Subscriber.STATE_UNCONFIRMED.equals(subscriber.getState())){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "帐户资料未审核通过，不能进行充值操作!");
			return SUCCESS;
		}
		Account account=accountService.getAccoutDetail(subscriber.getId());
		if (Account.IS_REFUND_TRUE.equals(account.getIsRefund())) {
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "退款流程处理中，不能充值!");
			return SUCCESS;
		}
		/** 状态校验end**/
		result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, "成功");
		
		return SUCCESS;
	}
	
	
	
	

	
	public String getSubscriber(){
		String  subscriberId=CookieTool.getAppSubscriberToken(getRequest());
		
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
		
		
		Subscriber subscriber=subscriberService.querySubscriberById(subscriberId);
		
		if(subscriber==null){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
	
		
		result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, subscriber);
			
		
		return 	SUCCESS;
		
	}
	
	/**
	 * app登录
	 * @return
	 */
	public String appLogin(){
		try{
			
			/** 请求参数校验**/
			String  phoneNoStr=getRequest().getParameter("phoneNo");
			String  smsCodeStr=getRequest().getParameter("smsCode");
			if(StringUtils.isEmpty(phoneNoStr)||StringUtils.isEmpty(smsCodeStr)){
				result=Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "请输入手机号或验证码!");
				return SUCCESS;
			}
			if(!isPhone(phoneNoStr)){
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "手机号格式不正确！");
				return SUCCESS;
			}
			Subscriber loginSubscriber=subscriberService.querySubscriberByPhoneNo(phoneNoStr);
			
			/**
			 * 验证码校验
			 */
			SMSCode smsCode=new SMSCode();
			smsCode.setPhoneNo(phoneNoStr);
			smsCode.setType(Constants.SUBSCRIBER_LOGIN_CODE);
			smsCode.setChannel(Account.PAY_CHANNEL_APP);
			smsCode=smsCodeService.getLatestSMSCode(smsCode,Constants.REGISTER_SMS_VALID_MINUTE );
			if(smsCode==null){
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "验证码已失效请重新获取!");
				return SUCCESS;
			}
			if(!smsCodeStr.equals(smsCode.getCode())){
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "验证码有误！");
				return SUCCESS;
			}

			//注册流程
			if(loginSubscriber==null){
				//短信验证码校验

				//校验完成，保存会员信息

				//微信号和会员账号做关联
				/*if( getRequest().getParameter("WeChat")!=null && "1".equals(getRequest().getParameter("WeChat")) && !StringUtils.isEmpty(getRequest().getParameter("unionId")) ){
					subscriber.setWechatUnionId(getRequest().getParameter("unionId"));
				}*/
				
				Subscriber subscriber = new Subscriber();
				subscriber.setPhoneNo(phoneNoStr);
				loginSubscriber=subscriberService.addSubscriber(subscriber);
				
				CookieTool.setAppSubscriberToken(getRequest(), getResponse(), loginSubscriber);
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, "登录成功！");
				
			}else{
			//登录流程	
				
				if(Subscriber.EVENT_STATE_FUll.equals(loginSubscriber.getEventState())){
					result=Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "此账号已被锁定，如有疑问，请联系客服!");
					return SUCCESS;
				}

				
				//微信号和会员账号做关联
				/*if(getSession().getAttribute("wechatUserInfo")!=null && getRequest().getParameter("WeChat")!=null && "1".equals(getRequest().getParameter("WeChat"))){
					WechatUserInfo wechatUserInfo =(WechatUserInfo)getSession().getAttribute("wechatUserInfo");
					if(StringUtils.isEmpty(wechatUserInfo.getSubscriberId())){
						wechatUserInfo = wechatUserInfoService.getUserInfoByOpenId(wechatUserInfo.getOpenId());
						loginSubscriber.setWechatUnionId(wechatUserInfo.getUnionId());
					}
				}*/
		         subscriberService.updateSubscriber(loginSubscriber);
		         CookieTool.setAppSubscriberToken(getRequest(), getResponse(), loginSubscriber);
				 result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, "登录成功！");
			}
			
			
			SubscriberLoginRecord loginRecord =new SubscriberLoginRecord();
			loginRecord.setLoginTime(new Date());
			loginRecord.setSubscriberId(loginSubscriber.getId());
			loginRecord.setTs(new Date());
			loginRecord.setIp(IpUtil.getIpAddr(getRequest()));
			subscriberLoginRecordService.addSubscriberLoginRecord(loginRecord);
			
			

		}catch(Exception e){
			result=Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, " 登录异常!");
			logger.error(e);
			e.printStackTrace();
			return SUCCESS;
		}
		return SUCCESS;
	
	}
	

	/**
	 *  发送登录短信验证码
	 * @return
	 */
	public String sendLoginCode(){
		try {
			/** 请求参数校验**/
			String  phoneNoStr=getRequest().getParameter("phoneNo");
			if(StringUtils.isEmpty(phoneNoStr)){
				result=Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "请输入手机号");
				return SUCCESS;
			}
			if(!isPhone(phoneNoStr)){
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "请输入正确格式手机号");
				return SUCCESS;
			}
			 result=subscriberService.sendSMSCode(Constants.SUBSCRIBER_LOGIN_CODE,phoneNoStr , Account.PAY_CHANNEL_APP);
			if("ok".equals(result)){
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, "下发成功");
			}else{
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, result);
			}			
		} catch (Exception e) {
			logger.error(e);
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "发送短信失败");
		}
		return SUCCESS;
	}
	
	
	

	/**
	 * 验证手机号是否已注册
	 * @Title: validatePhoneNo
	 * @Description:
	 * @return
	 * @throws
	 */
	public String isNewPhoneNo(){
		/** 请求参数校验**/
		String  phoneNoStr=getRequest().getParameter("phoneNo");
		if(StringUtils.isEmpty(phoneNoStr) ){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "手机号为空");
			return 	SUCCESS;
		}
		if(!isPhone(phoneNoStr)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "手机号格式不正确");
			return SUCCESS;
		}
		Subscriber s=subscriberService.querySubscriberByPhoneNo(phoneNoStr);
		if(s!=null){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, false);
		}else{
			
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, true);
		}
		return SUCCESS;
	}
	
	
	
	
	public String wechatBinding(){
		/** 会员判空 */
		String  subscriberId=CookieTool.getAppSubscriberToken(getRequest());
		
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
		Subscriber subscriber=subscriberService.querySubscriberById(subscriberId);
		
		if(subscriber==null){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
		
		String unionId=	getRequest().getParameter("unionId");
		
		if(StringUtils.isEmpty(unionId)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "unionId不能为空");
			return SUCCESS;
		}
		
		
		if(StringUtils.isEmpty(subscriber.getWechatUnionId())){

			subscriber.setWechatUnionId(unionId);
			subscriberService.updateSubscriber(subscriber);

			try {
				Map<String, String> contentMap = new HashMap<String, String>();
				contentMap.put("微信号绑定", unionId);
				SystemOperateLogUtil.sysAddOperateLog(subscriberId, null, SystemOperateLogUtil.MODEL_SUBSCRIBER_INFO,contentMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, "绑定成功！");
			return SUCCESS;

		}else{
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "已绑定微信号，不能重复绑定！");
			return SUCCESS;
		}

		
	}
	
	
	
	
	public String wechatUnbinding(){
		/** 会员判空 */
		String  subscriberId=CookieTool.getAppSubscriberToken(getRequest());
		
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
		Subscriber subscriber=subscriberService.querySubscriberById(subscriberId);
		
		if(subscriber==null){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_LOGIN, "登录超时，请重新登录");
			return SUCCESS;
		}
		
		if(!StringUtils.isEmpty(subscriber.getWechatUnionId())){
			try {
				Map<String, String> contentMap = new HashMap<String, String>();
				contentMap.put("微信号解绑", subscriber.getWechatUnionId());
				SystemOperateLogUtil.sysAddOperateLog(subscriber.getId(), null, SystemOperateLogUtil.MODEL_SUBSCRIBER_INFO,contentMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			subscriber.setWechatUnionId(null);
			subscriberService.updateSubscriber(subscriber);
			
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, "解绑成功！");
			return SUCCESS;
		}else{
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "未绑定微信账号！");
			return SUCCESS;
		}
	}
	
	
	
	
	


	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	

	public RechargeCardService getRechargeCardService() {
		return rechargeCardService;
	}

	public void setRechargeCardService(RechargeCardService rechargeCardService) {
		this.rechargeCardService = rechargeCardService;
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

	

	public AccountService getAccountService() {
		return accountService;
	}

	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public SMSCodeService getSmsCodeService() {
		return smsCodeService;
	}

	public void setSmsCodeService(SMSCodeService smsCodeService) {
		this.smsCodeService = smsCodeService;
	}

	public SubscriberLoginRecordService getSubscriberLoginRecordService() {
		return subscriberLoginRecordService;
	}

	public void setSubscriberLoginRecordService(SubscriberLoginRecordService subscriberLoginRecordService) {
		this.subscriberLoginRecordService = subscriberLoginRecordService;
	}
	

	
    
	public void setRefundService(RefundService refundService) {
		this.refundService = refundService;
	}

	private  Boolean isPhone(String phoneNo){
		Pattern pattern = Pattern.compile("^0?(1)[0-9]{10}$");
		Matcher matcher = pattern.matcher(phoneNo);
		return matcher.matches();
	}
	
	
	
}
