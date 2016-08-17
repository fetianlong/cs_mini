package com.dearho.cs.appinterface.subscriber;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.appinterface.subscriber.pojo.AppSubscriber;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.SMSCode;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.service.SMSCodeService;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.ImageHelper;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.service.WechatUserInfoService;

/**
 * 
* @ClassName: AppSubscriberAction 
* @Description: APP 个人资料接口
* @author LH
* @date 2016-3-3 上午11:40:42 
*
 */
public class AppSubscriberAction extends AbstractAction{
	

	
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(AppSubscriberAction.class);
	
	private SubscriberService subscriberService;
	private Subscriber subscriber ;
	private SMSCodeService smsCodeService;
	private WechatUserInfoService wechatUserInfoService;
	private WechatUserInfo  wechatUserInfo;
	
	private static final Integer maxUploadSize=1024*1024*5;
	
	private File  upload;  //文件
	private String uploadContentType;  //文件类型
	private String uploadFileName;   //文件名
	
	private File drivingUpload;  //文件
	private String drivingUploadContentType;  //文件类型
	private String drivingUploadFileName;   //文件名
	
	private static final String uploadPath="upload/authImage/";
	
	/**
	 * @Title: appSubsDetail 
	* @Description: 获取个人资料信息
	* @param @return   
	* @return String
	* @throws
	 */
	public String appSubsDetail(){
		String id=getRequest().getParameter("subscriber_id");
		if(StringUtils.isEmpty(id)){
			result=Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL,"会员ID为空");
			return SUCCESS;
		}
		//个人资料封装类
		AppSubscriber  appSubscriber =new AppSubscriber();
		subscriber=subscriberService.querySubscriberById(id);
		String wechatUnionId = subscriber.getWechatUnionId();
		if(!StringUtils.isEmpty(wechatUnionId)){
			wechatUserInfo = wechatUserInfoService.getUserInfoByUnionId(wechatUnionId);
			appSubscriber.setWebchatName(wechatUserInfo.getNickname());
		}
		//赋值
		appSubscriber.setId(id);
		appSubscriber.setName(subscriber.getName());
		appSubscriber.setPhoneNo(subscriber.getPhoneNo());
		appSubscriber.setState(subscriber.getState());
		appSubscriber.setDrivingLicenseNo(subscriber.getDrivingLicenseNo());
		appSubscriber.setEmail(subscriber.getEmail());
		
		appSubscriber.setProvince(subscriber.getProvince());
		appSubscriber.setCity(subscriber.getCity());
		appSubscriber.setCounty(subscriber.getCounty());
		appSubscriber.setAddress(subscriber.getAddress());
		appSubscriber.setEmergencyContact(subscriber.getEmergencyContact());
		appSubscriber.setEmergencyContactPhone(subscriber.getEmergencyContactPhone());
		appSubscriber.setEmergencyContactAddress(subscriber.getEmergencyContactAddress());
		result=Ajax.AppJsonResult(0,appSubscriber);
		return SUCCESS;
	}

	/**
	 * @Title: subscriberInfo 
	* @Description: 根据ID获取个人基本资料    
	* @return String 
	* @throws
	 */
	public String subsDetail(){
		
		String id=getRequest().getParameter("subscriber_id");
		if(StringUtils.isEmpty(id)){
			result=Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL,"会员ID为空");
			return SUCCESS;
		}
		subscriber=subscriberService.querySubscriberById(id);
		result=Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS,subscriber);
		return SUCCESS;
	}
	
	/**
	 * @Title: appSubscriberInfo 
	* @Description:  根据手机号获取个人基本资料
	* @return String 
	* @throws
	 */
	public String subsInfo(){
		String phoneNo=getRequest().getParameter("subscriber_phoneNo");
		if(StringUtils.isEmpty(phoneNo)){
			result=Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL,"会员手机号为空");
			return SUCCESS;
		}
		subscriber=subscriberService.querySubscriberById(phoneNo);
		result=Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS,subscriber);
		return SUCCESS;
	}
	/**
	 * @Title: subscriberUpdate 
	* @Description: 个人资料修改 
	* @param @return     
	* @return String 
	* @throws
	 */
	public String subsUpdate(){
		String id=getRequest().getParameter("subscriber_id");
		
		String email=getRequest().getParameter("subscriber_email");
		
		if(StringUtils.isEmpty(id)){
			result=Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "会员ID为空");
			return SUCCESS;
		}
		
		Subscriber subs=subscriberService.querySubscriberById(id);
		
		if(Subscriber.EVENT_STATE_HALF.equals(subs.getEventState())){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "此账号已冻结，不能进行会员相关操作。如有疑问，请联系客服。");
			return SUCCESS;
		}
		if(!StringUtils.isEmpty(email)){
	        String check = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+";    
	        Pattern regex = Pattern.compile(check);    
            Matcher m = regex.matcher(email); 
            if(!m.matches()){
            	result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "邮箱格式有误");
    			return SUCCESS;
            }
		}
	
		Object beforeObject=subscriberService.querySubscriberById(subs.getId());
		
		String city=getRequest().getParameter("subscriber_city");
		String province=getRequest().getParameter("subscriber_province");
		
		String county=getRequest().getParameter("subscriber_county");
		String address=getRequest().getParameter("subscriber_address");
		String emergencyContact=getRequest().getParameter("subscriber_emergencyContact");
		String emergencyContactAddress=getRequest().getParameter("subscriber_emergencyContactAddress");
		String emergencyContactPhone=getRequest().getParameter("subscriber_emergencyContactPhone");
		

		subs.setCity(city);
		subs.setProvince(province);
		subs.setCounty(county);
		subs.setAddress(address);
		
		subs.setEmail(email);
		subs.setEmergencyContact(emergencyContact);
		subs.setEmergencyContactAddress(emergencyContactAddress);
		subs.setEmergencyContactPhone(emergencyContactPhone);
		
		
		subscriberService.updateSubscriber(subs);
		
		
		Object afterObject=subs;

		result=Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS,"修改成功");
		
		try {
			SystemOperateLogUtil.sysUpdateOperateLog(beforeObject, afterObject,null, SystemOperateLogUtil.MODEL_SUBSCRIBER_INFO, subs.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	/**
	 * @Title: sendChangePhoneCode 
	* @Description: 更改手机号   发送短信验证码 
	* @param @return     
	* @return String 
	* @throws
	 */
	public String sendPhoneCode(){
			try {
			String type=getRequest().getParameter("subscriber_type");
			String phoneNo=getRequest().getParameter("subscriber_phoneNo");
			if(!StringUtils.isEmpty(type)){
				if("new".equals(type)){
					type=Constants.SUBSCIRBER_CHANGE_NEW_PHONE_CODE;
				}else{
					type=Constants.SUBSCIRBER_CHANGE_OLD_PHONE_CODE;
				}
				
			}else{
				result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "下发类型为空");
				return SUCCESS;
			}
			
			 result=subscriberService.sendSMSCode(type,phoneNo , Account.PAY_CHANNEL_PORTAL);
			if("ok".equals(result)){
				result = Ajax.AppJsonResult(Constants.RESULT_CODE_SUCCESS, "下发成功");
			}else{
				result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, result);
			}			
			
		} catch (Exception e) {
			logger.error(e);
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "发送短信失败！");
		}
		return SUCCESS;
	}
	/**
	 * @Title: changePhoneNo 
	* @Description: 修改绑定手机号 
	* @param @return     
	* @return String 
	* @throws
	 */
	public String revisePhoneNo(){
		
		String id=getRequest().getParameter("subscriber_id");
		if(StringUtils.isEmpty(id)){
			result=Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL,"会员ID为空");
			return SUCCESS;
		}
		Subscriber subscribers=subscriberService.querySubscriberById(id);
		SMSCode oldsmsCode=new SMSCode();
		oldsmsCode.setPhoneNo(subscribers.getPhoneNo());
		oldsmsCode.setType(Constants.SUBSCIRBER_CHANGE_OLD_PHONE_CODE);
		oldsmsCode.setChannel(Account.PAY_CHANNEL_PORTAL);
		oldsmsCode=smsCodeService.getLatestSMSCode(oldsmsCode,Constants.REGISTER_SMS_VALID_MINUTE );
		if(oldsmsCode==null){
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "原手机号验证码已失效请重新获取!");
			return SUCCESS;
		}
		//短信验证码校验
		String oldCpyzm=getRequest().getParameter("subscriber_oldyanzheng");
		if(StringUtils.isEmpty(oldCpyzm)){
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "原手机号验证码不能为空！");
			return SUCCESS;
		}
		
		if(!oldCpyzm.equals(oldsmsCode.getCode())){
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "原手机号验证码有误！");
			return SUCCESS;
		}
		
		String newphone=getRequest().getParameter("subscriber_newphone");
		if(StringUtils.isEmpty(newphone)){
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "请输入新手机号！");
			return SUCCESS;
		}
		
		SMSCode newSmsCode=new SMSCode();
		newSmsCode.setPhoneNo(newphone);
		newSmsCode.setType(Constants.SUBSCIRBER_CHANGE_NEW_PHONE_CODE);
		newSmsCode.setChannel(Account.PAY_CHANNEL_PORTAL);
		newSmsCode=smsCodeService.getLatestSMSCode(newSmsCode,Constants.REGISTER_SMS_VALID_MINUTE );
		if(newSmsCode==null){
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "新手机号验证码已失效请重新获取!");
			return SUCCESS;
		}
		//短信验证码校验
		String newCpyzm=getRequest().getParameter("subscriber_newyanzheng");
		
		if(StringUtils.isEmpty(newCpyzm)){
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "新手机号验证码不能为空！");
			return SUCCESS;
		}
		
		if(!newCpyzm.equals(newSmsCode.getCode())){
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "新手机验证码有误！");
			return SUCCESS;
		}
		
		//手机号码已有校验
		Subscriber s=subscriberService.querySubscriberByPhoneNo(newphone);
		if(s != null){
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "此手机号已经注册，请更换其他手机号！");
			return SUCCESS;
		}
		
		Subscriber accSubscriber=(Subscriber) getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		String oldPhone = accSubscriber.getPhoneNo();
		accSubscriber.setPhoneNo(newphone);
		//校验完成，保存会员信息
		try{
			subscriberService.updateSubscriber(accSubscriber);
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_SUCCESS, "手机号码修改成功！");
		
			
			try {
				Map<String, String> contentMap = new HashMap<String, String>();
				contentMap.put("手机号码变更", "前"+oldPhone+"，后"+newphone);
				SystemOperateLogUtil.sysAddOperateLog(accSubscriber.getId(), null, SystemOperateLogUtil.MODEL_SUBSCRIBER_INFO,contentMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			getSession().setAttribute(Constants.SESSION_SUBSCRIBER,accSubscriber);
			String content = "尊敬的用户，您的手机号码修改成功，如非本人操作，请关注账号安全。";
			SMSUtil.sendSMS(oldPhone, content,SMSRecord.TYPE_PWD);
		}catch(Exception e){
			logger.error(e);
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_FAILED, "系统异常！");
			
		}
		return SUCCESS;
	}


	/**
	 * @Title: regFinish 
	* @Description: 实名认证 LH
	* @param @return   
	* @return String
	* @throws
	 */
	public String regFinish(){
		String id=getRequest().getParameter("subscriber_id");
		if(StringUtils.isEmpty(id)){
			result=Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL,"会员ID为空");
			return SUCCESS;
		}
		subscriber.setId(id);
		Subscriber sub = subscriberService.querySubscriberById(id);
		
		String name=getRequest().getParameter("subscriber_name");
		String drivingLicenseNo=getRequest().getParameter("subscriber_drivingLicenseNo");
		String province=getRequest().getParameter("subscriber_province");
		String city=getRequest().getParameter("subscriber_city");
		String county=getRequest().getParameter("subscriber_county");
		String address=getRequest().getParameter("subscriber_address");
		String state=getRequest().getParameter("subscriber_state");
		
		
		if(StringUtils.isEmpty(name)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "真实姓名不能为空！");
			return SUCCESS;
		}
		if(StringUtils.isEmpty(drivingLicenseNo)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "驾驶证号不能为空！");
			return SUCCESS;
		}
		
		if(StringUtils.isEmpty(province)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "省份不能为空!");
			return SUCCESS;
		}
		if(StringUtils.isEmpty(city)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "地级市不能为空!");
			return SUCCESS;
		}
		if(StringUtils.isEmpty(county)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "县级市不能为空!");
			return SUCCESS;
		}
		if(StringUtils.isEmpty(address)){
			result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "家庭住址不能为空!");
			return SUCCESS;
		}
		if(StringUtils.isEmpty(upload.getName())){
			if(upload==null){
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "身份证照片不能为空!");
				return SUCCESS;
			}
		}
		if(StringUtils.isEmpty(drivingUpload.getName())){
			if(drivingUpload==null){
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "驾驶证照片不能为空!");
				return SUCCESS;
			}
		}
		
	    if(Subscriber.STATE_WAIT_CONFIRMED.equals(state) ){
	    	result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "资料正在审核,无需再次上传，请耐心等待!");
			return SUCCESS;
	    }
	    if(Subscriber.STATE_NORMAL.equals(state) || Subscriber.STATE_CARD_ISSUED.equals(state) ){
	    	result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, "资料已审核通过，不能再次上传!");
			return SUCCESS;
	    }
	

		String idCardImgName=null;
		String drivingImgName=null;
		
		if(upload!=null&& uploadFileName!=null){
			try {
				 idCardImgName=ImageHelper.uploadPic(upload,uploadFileName,uploadContentType,maxUploadSize,uploadPath);
			} catch (Exception e) {
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, e.getMessage());
				return SUCCESS;
			} 
		}
		
		
		if(drivingUpload!=null && drivingUploadFileName!=null ){
			try {
				drivingImgName=ImageHelper.uploadPic(drivingUpload,drivingUploadFileName,drivingUploadContentType,maxUploadSize,uploadPath);
			} catch (Exception e) {
				result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, e.getMessage());
				return SUCCESS;
			} 
		}
		sub.setName(name);
		sub.setDrivingLicenseNo(drivingLicenseNo);
		sub.setProvince(province);
		sub.setCity(city);
		sub.setCounty(county);
		sub.setAddress(address);
		sub.setState(Integer.valueOf(state));
		
		//subscriber.setId(sub.getId());
		if(!StringUtils.isEmpty(idCardImgName)){
			sub.setIdCardImg(uploadPath+idCardImgName);
		}
		if(!StringUtils.isEmpty(drivingImgName)){
			sub.setDrivingLicenseImg(uploadPath+drivingImgName);
		}
	    subscriberService.completeRegister(sub,true);
	   	
	    result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_SUCCESS, sub);
		return SUCCESS;
		
	}
	@Override
	public String process() {
		// TODO Auto-generated method stub
		return SUCCESS;
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

	public WechatUserInfoService getWechatUserInfoService() {
		return wechatUserInfoService;
	}

	public void setWechatUserInfoService(WechatUserInfoService wechatUserInfoService) {
		this.wechatUserInfoService = wechatUserInfoService;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public File getDrivingUpload() {
		return drivingUpload;
	}

	public void setDrivingUpload(File drivingUpload) {
		this.drivingUpload = drivingUpload;
	}

	public String getDrivingUploadContentType() {
		return drivingUploadContentType;
	}

	public void setDrivingUploadContentType(String drivingUploadContentType) {
		this.drivingUploadContentType = drivingUploadContentType;
	}

	public String getDrivingUploadFileName() {
		return drivingUploadFileName;
	}

	public void setDrivingUploadFileName(String drivingUploadFileName) {
		this.drivingUploadFileName = drivingUploadFileName;
	}

	
	
	
	

	
}
