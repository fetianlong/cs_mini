package com.dearho.cs.subscriber.action.register;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberPhoneChangeService;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.SMSCode;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.service.SMSCodeService;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.IpUtil;
import com.dearho.cs.util.Sha1Util;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.service.WechatUserInfoService;
import com.dearho.cs.wechat.util.WeixinUtil;
/**
 * @Author liusong
 * @Description 会员注册第一步 
 * @Version 1.0,2015-5-18
 *
 */
public class SubscriberRegisterOneAction extends AbstractAction {

	private static final long serialVersionUID = -3312264055146365391L;
	
	private static final Log logger = LogFactory.getLog(SubscriberRegisterOneAction.class);
	private SubscriberService subscriberService;
	private Subscriber subscriber ;
	
	private SubscriberPhoneChangeService subscriberPhoneChangeService;
	
	private WechatUserInfoService wechatUserInfoService;
	private SMSCodeService smsCodeService;
	
	
	
	public SubscriberRegisterOneAction() {
		super();
		subscriber=new Subscriber();
	}

	
	@Override
	public String process() {
		
		try{
			//手机号校验
			if(StringUtils.isEmpty(subscriber.getPhoneNo())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请输入手机号！");
				return SUCCESS;
			}
			if(!isPhone(subscriber.getPhoneNo())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "手机号格式不正确！");
				return SUCCESS;
			}
			//密码校验
			/*if(subscriber.getPhoneNo().length()<8 ){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "密码过短！");
				return SUCCESS;
			}*/
			//短信验证码校验
			String code=getRequest().getParameter("code");
			if(StringUtils.isEmpty(code)){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请输入验证码！");
				return SUCCESS;
			}
			SMSCode smsCode=new SMSCode();
			smsCode.setPhoneNo(subscriber.getPhoneNo());
			smsCode.setType(Constants.SUBSCIRBER_REGISTER_PHONE_CODE);
			smsCode.setChannel(Account.PAY_CHANNEL_PORTAL);
			smsCode=smsCodeService.getLatestSMSCode(smsCode,Constants.REGISTER_SMS_VALID_MINUTE );
			if(smsCode==null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "验证码已失效请重新获取!");
				return SUCCESS;
			}
			
			if(!code.equals(smsCode.getCode())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "验证码有误！");
				return SUCCESS;
			}
			//手机号码已有校验
			Subscriber s=subscriberService.querySubscriberByPhoneNo(subscriber.getPhoneNo());
			if(s!=null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "此手机号已注册！");
				return SUCCESS;
			}
			//校验完成，保存会员信息

		//	subscriber.setLoginMacAddress(IpUtil.getMACAddress(getRequest()));
			
			System.out.println("unionid="+subscriber.getWechatUnionId());

			//微信号和会员账号做关联
			if( getRequest().getParameter("WeChat")!=null && "1".equals(getRequest().getParameter("WeChat")) && !StringUtils.isEmpty(getRequest().getParameter("unionId")) ){
				subscriber.setWechatUnionId(getRequest().getParameter("unionId"));
			}
			
			subscriber=subscriberService.addSubscriber(subscriber);
			getSession().setAttribute(Constants.SESSION_SUBSCRIBER,subscriber );
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "登录成功！");
			
		}catch(Exception e){
			logger.error(e);
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "系统异常！");
			
		}
		return SUCCESS;
 }
	
	
	/**
	 * 第一步会员注册成功
	 * @return
	 */
	public String registerNextStep(){
		Object sub=getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		if(sub==null){
			return "login";
		}
		subscriber=subscriberService.querySubscriberByPhoneNo(((Subscriber)sub).getPhoneNo());
		return SUCCESS;
	}
	
	public String gotoRegister(){
		
		
		String unionId=getRequest().getParameter("unionId");
		if(StringUtils.isEmpty(unionId)){
			String rechargeReferrer="/rechargePrepareServlet?&url=/mobile/subscriber/register.action";
			WeixinUtil.requestOauth2Code(getResponse(), "snsapi_userinfo",rechargeReferrer);
			return null;
		}
		getRequest().setAttribute("unionId", unionId);
		
		return SUCCESS;
	}
	/**
	 * 会员注册--下发手机验证码
	 * @return
	 */
	public String sendPhoneVerificationCode(){

		
		try {
			String result=subscriberService.sendSMSCode(Constants.SUBSCIRBER_REGISTER_PHONE_CODE,subscriber.getPhoneNo() , Account.PAY_CHANNEL_PORTAL);
			if("ok".equals(result)){
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "下发成功");
			}else{
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, result);
			}
		
		} catch (Exception e) {
			logger.error(e);
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "发送短信失败！");
		}
		return SUCCESS;
		
	
	}
	
	/**
	 * 找回密码    发送短信验证码
	 * @return
	 */
	public String sendPhoneFindPwdCode(){
		
		String phoneNo=getRequest().getParameter("phoneNo");
		try {
			String result=subscriberService.sendSMSCode(Constants.SUBSCRIBER_PHONE_FIND_PWD_CODE,phoneNo , Account.PAY_CHANNEL_PORTAL);
			if("ok".equals(result)){
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "下发成功");
			}else{
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, result);
			}
			
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "发送短信失败！");
		} catch (Exception e) {
			logger.error(e);
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "发送短信失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 更改手机号   发送短信验证码
	 * @return
	 */
	public String sendChangePhoneCode(){
		
		try {
			
			String type=getRequest().getParameter("type");
			if(!StringUtils.isEmpty(type)){
				if("new".equals(type)){
					type=Constants.SUBSCIRBER_CHANGE_NEW_PHONE_CODE;
				}else{
					type=Constants.SUBSCIRBER_CHANGE_OLD_PHONE_CODE;
				}
				
			}else{
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "下发类型为空");
				return SUCCESS;
			}
			
			 result=subscriberService.sendSMSCode(type,subscriber.getPhoneNo() , Account.PAY_CHANNEL_PORTAL);
			if("ok".equals(result)){
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "下发成功");
			}else{
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, result);
			}			
			
		} catch (Exception e) {
			logger.error(e);
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "发送短信失败！");
		}
		return SUCCESS;
	}
	
	
	
	
	
	
	/**
	 * TODO 参数修改，手机号 重置用户没有phoneno
	 * @return
	 */
	public String resetPwd(){
		
		//短信验证码校验
		String cpyzm=getRequest().getParameter("cpyzm");
		if(StringUtils.isEmpty(cpyzm)){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请输入验证码！");
			return SUCCESS;
		}
		
		SMSCode smsCode=new SMSCode();
		smsCode.setPhoneNo(subscriber.getPhoneNo());
		smsCode.setType(Constants.SUBSCRIBER_PHONE_FIND_PWD_CODE);
		smsCode.setChannel(Account.PAY_CHANNEL_APP);
		smsCode=smsCodeService.getLatestSMSCode(smsCode,Constants.REGISTER_SMS_VALID_MINUTE );
		if(smsCode==null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "验证码已失效请重新获取!");
			return SUCCESS;
		}
		
		
		if(!cpyzm.equals(smsCode.getCode())){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "验证码有误！");
			return SUCCESS;
		}
		//手机号码已有校验
		Subscriber s=subscriberService.querySubscriberByPhoneNo(subscriber.getPhoneNo());
		if(s == null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "此手机号没有注册！");
			return SUCCESS;
		}
		s.setPassword(Sha1Util.SHA1Encode(subscriber.getPassword()));
		//校验完成，保存会员信息
		try{
			subscriberService.updateSubscriber(s);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "密码重置成功！");
			StringBuffer content = new StringBuffer();
			content.append("尊敬的乐途出行用户，您的密码重置成功，如非本人操作，请关注账号安全。");
			SMSUtil.sendSMS(s.getPhoneNo(), content.toString(),SMSRecord.TYPE_PWD);
		}catch(Exception e){
			logger.error(e);
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "系统异常！");
			
		}
		return SUCCESS;
	}
	
	public String changePhoneNo(){
		
		subscriber=(Subscriber) getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		
		
		SMSCode oldsmsCode=new SMSCode();
		oldsmsCode.setPhoneNo(subscriber.getPhoneNo());
		oldsmsCode.setType(Constants.SUBSCIRBER_CHANGE_OLD_PHONE_CODE);
		oldsmsCode.setChannel(Account.PAY_CHANNEL_PORTAL);
		oldsmsCode=smsCodeService.getLatestSMSCode(oldsmsCode,Constants.REGISTER_SMS_VALID_MINUTE );
		if(oldsmsCode==null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "原手机号验证码已失效请重新获取!");
			return SUCCESS;
		}
		
		
		//短信验证码校验
		String oldCpyzm=getRequest().getParameter("oldyanzheng");
		if(StringUtils.isEmpty(oldCpyzm)){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "原手机号验证码不能为空！");
			return SUCCESS;
		}
		
		if(!oldCpyzm.equals(oldsmsCode.getCode())){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "原手机号验证码有误！");
			return SUCCESS;
		}
		
		
		
		
		String newphone=getRequest().getParameter("newphone");
		if(StringUtils.isEmpty(newphone)){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请输入新手机号！");
			return SUCCESS;
		}
		
		SMSCode newSmsCode=new SMSCode();
		newSmsCode.setPhoneNo(newphone);
		newSmsCode.setType(Constants.SUBSCIRBER_CHANGE_NEW_PHONE_CODE);
		newSmsCode.setChannel(Account.PAY_CHANNEL_PORTAL);
		newSmsCode=smsCodeService.getLatestSMSCode(newSmsCode,Constants.REGISTER_SMS_VALID_MINUTE );
		if(newSmsCode==null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "新手机号验证码已失效请重新获取!");
			return SUCCESS;
		}
		
		//短信验证码校验
		String newCpyzm=getRequest().getParameter("newyanzheng");
		
		if(StringUtils.isEmpty(newCpyzm)){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "新手机号验证码不能为空！");
			return SUCCESS;
		}
		
		if(!newCpyzm.equals(newSmsCode.getCode())){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "新手机验证码有误！");
			return SUCCESS;
		}
		
		
		
		//手机号码已有校验
		Subscriber s=subscriberService.querySubscriberByPhoneNo(newphone);
		if(s != null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "此手机号已经注册，请更换其他手机号！");
			return SUCCESS;
		}
		
		Subscriber accSubscriber=(Subscriber) getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		String oldPhone = accSubscriber.getPhoneNo();
		accSubscriber.setPhoneNo(getRequest().getParameter("newphone"));
		//校验完成，保存会员信息
		try{
			subscriberService.updateSubscriber(accSubscriber);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "手机号码修改成功！");
		
			
			try {
				Map<String, String> contentMap = new HashMap<String, String>();
				contentMap.put("手机号码变更", "前"+oldPhone+"，后"+newphone);
				SystemOperateLogUtil.sysAddOperateLog(accSubscriber.getId(), null, SystemOperateLogUtil.MODEL_SUBSCRIBER_INFO,contentMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			getSession().setAttribute(Constants.SESSION_SUBSCRIBER,accSubscriber);
			String content = "尊敬的乐途出行用户，您的手机号码修改成功，如非本人操作，请关注账号安全。";
			SMSUtil.sendSMS(oldPhone, content,SMSRecord.TYPE_PWD);
		}catch(Exception e){
			logger.error(e);
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "系统异常！");
			
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
	public String isPhoneEngaged(){
		if(StringUtils.isEmpty(subscriber.getPhoneNo())){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "手机号不能为空!");
			return 	SUCCESS;
		}
		Subscriber s=subscriberService.querySubscriberByPhoneNo(subscriber.getPhoneNo());
		if(s!=null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "此手机号已注册！");
		}else{
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "未注册");
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
	public String isPhoneEngagedNew(){
		if(StringUtils.isEmpty(subscriber.getPhoneNo())){
			result="false";
			return 	SUCCESS;
		}
		Subscriber s=subscriberService.querySubscriberByPhoneNo(subscriber.getPhoneNo());
		if(s!=null){
			result = "false";
		}else{
			result = "true";
		}
		return SUCCESS;
	}
	
	private  Boolean isPhone(String phoneNo){
		Pattern pattern = Pattern.compile("^0?(1)[0-9]{10}$");
		Matcher matcher = pattern.matcher(phoneNo);
		return matcher.matches();
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


	public SubscriberPhoneChangeService getSubscriberPhoneChangeService() {
		return subscriberPhoneChangeService;
	}
	public void setSubscriberPhoneChangeService(
			SubscriberPhoneChangeService subscriberPhoneChangeService) {
		this.subscriberPhoneChangeService = subscriberPhoneChangeService;
	}


	public WechatUserInfoService getWechatUserInfoService() {
		return wechatUserInfoService;
	}


	public void setWechatUserInfoService(WechatUserInfoService wechatUserInfoService) {
		this.wechatUserInfoService = wechatUserInfoService;
	}


	public SMSCodeService getSmsCodeService() {
		return smsCodeService;
	}


	public void setSmsCodeService(SMSCodeService smsCodeService) {
		this.smsCodeService = smsCodeService;
	}
	
	
	

}
