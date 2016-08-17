package com.dearho.cs.appinterface.sys.action;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.service.SMSCodeService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.JsonTools;
/**
* @ClassName: AppShortMessage 
* @Description: APP 短信 接口
* @author LH
* @date 2016年3月9日 下午5:58:58 
*
*/
public class AppShortMessageAction  extends AbstractAction{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(AppShortMessageAction.class);
	private SMSCodeService smsCodeService;
	
	@Override
	public String process() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	* @Title: appLoginMessage 
	* @Description: 发送登录验证码
	* @return String
	*/
	public String appLoginMessage(){
		String data = getRequest().getParameter("data");
		Map<String, String> map = JsonTools.jsonForMap(data);
		String phoneNo = map.get("phone_no");
		try {
			if(StringUtils.isEmpty(phoneNo)){
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "手机号码不能为空");
				return SUCCESS;
			}
			//在SMSCodeService重写短信调用方法,用于短信登录
			result=smsCodeService.sendSMSCode(Constants.SUBSCRIBER_LOGIN_CODE,phoneNo , Account.PAY_CHANNEL_APP);
			if("ok".equals(result)){
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, "下发成功");
			}else{
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, result);
			}			
		} catch (Exception e) {
			logger.error(e);
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "发送短信失败！");
		}
		return SUCCESS;
	}
	/**
	@Title: appEditMessage 
	* @Description: 获取 修改手机号码 验证码
	* @return String
	*/
	public String appEditMessage(){
		String data = getRequest().getParameter("data");
		Map<String, String> map = JsonTools.jsonForMap(data);
		String phoneNo = map.get("phone_No");
		String type = map.get("phone_type");
		try {
		if(!StringUtils.isEmpty(type)){
			if("new".equals(type)){
				type=Constants.SUBSCIRBER_CHANGE_NEW_PHONE_CODE;
			}else if("old".equals(type)){
				type=Constants.SUBSCIRBER_CHANGE_OLD_PHONE_CODE;
			}else{
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "下发类型错误");
				return SUCCESS;
			}
		}else{
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "下发类型为空");
			return SUCCESS;
		}
		//在SMSCodeService重写短信调用方法，
		 result=smsCodeService.sendSMSCode(type,phoneNo , Account.PAY_CHANNEL_APP);
		if("ok".equals(result)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, "下发成功");
		}else{
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, result);
		}			
		
		} catch (Exception e) {
			logger.error(e);//
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "发送短信失败");
		}
		return SUCCESS;
	}
	
	public SMSCodeService getSmsCodeService() {
		return smsCodeService;
	}
	public void setSmsCodeService(SMSCodeService smsCodeService) {
		this.smsCodeService = smsCodeService;
	}
	
}
