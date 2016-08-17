package com.dearho.cs.appinterface.subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.binding.corba.wsdl.Array;
import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.action.center.AccountCenterRechargeAction;
import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.appinterface.sys.pojo.AppToken;
import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.core.db.memcached.MemCachedFactory;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.pojo.SubscriberLoginRecord;
import com.dearho.cs.subscriber.service.SubscriberLoginRecordService;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.SMSCode;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.service.SMSCodeService;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.CookieTool;
import com.dearho.cs.util.ImageHelper;
import com.dearho.cs.util.IpUtil;
import com.dearho.cs.util.JsonTools;
import com.dearho.cs.util.MapDistance;
import com.dearho.cs.util.Md5Util;
import com.dearho.cs.util.Quick;
import com.dearho.cs.util.Sha1Util;
import com.dearho.cs.util.TokenUtils;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.service.WechatUserInfoService;

/**
 * @Description app用户
 * @author user
 * @date 2016.6.15
 */
public class AppUserAction  extends AbstractAction {



	public static final Log logger = LogFactory.getLog(AccountCenterRechargeAction.class);
	private static final long serialVersionUID = -4365773690582053808L;
	private SubscriberService subscriberService;
	private SMSCodeService smsCodeService;
	private SubscriberLoginRecordService subscriberLoginRecordService;
	private AppToken appToken;
	private Subscriber subscriber ;
	private WechatUserInfoService wechatUserInfoService;
	private WechatUserInfo  wechatUserInfo;
	private BranchDotService branchDotService;
	
	
	private static final String uploadPath="upload/authImage/";
	private static final Integer maxUploadSize=1024*1024*5;
	private File  upload;  //文件
	private String uploadContentType;  //文件类型
	private String uploadFileName;   //文件名
	
	private File drivingUpload;  //文件
	private String drivingUploadContentType;  //文件类型
	private String drivingUploadFileName;   //文件名
	
	
	@Override
	public String process() {
		 
		return null;
	}
	/**
	 * token失效
	 */
	public String isnotoken(){
		result = Ajax.AppJsonResult(Constants.APP_LOGIN_TOKEN, "token失效！");
		return SUCCESS;
	}
	
	/**
	 * @Title: sendChangePhoneCode 
	* @Description: 发送短信验证码 
	* @param @return     
	* @return String 
	* @throws
	 */
	public String sendCode(){
			try {
				String data = getRequest().getParameter("data");
				Map<String, String> map = JsonTools.desjsonForMap(data);
				String type = map.get("type");
				String phoneNo = map.get("phoneNo");
				if(!StringUtils.isEmpty(type)){
					if("new".equals(type)){
						type=Constants.SUBSCIRBER_CHANGE_NEW_PHONE_CODE;
					}else if("old".equals(type)){
						type=Constants.SUBSCIRBER_CHANGE_OLD_PHONE_CODE;
					}else if("login".equals(type)){
						Subscriber s=subscriberService.querySubscriberByPhoneNo(phoneNo);
						if(s!=null){
							type=Constants.SUBSCRIBER_LOGIN_CODE;
						}else{
							type=Constants.SUBSCIRBER_REGISTER_PHONE_CODE;
						}
					}
				}else{
					result = Ajax.AppJsonResult(Constants.APP_TYPE_NULL, "短信类型为空");
				}
				if(!isPhone(phoneNo)){
					result = Ajax.AppJsonResult(Constants.APP_LOGIN_MOBILE_STYLE, "手机号格式不正确！");
				}
				result=subscriberService.sendSMSCode(type,phoneNo , Account.PAY_CHANNEL_APP);
				if("ok".equals(result)){
					result = Ajax.AppJsonResult(Constants.APP_LOGIN_SUCCESS, "发送成功");
				}else{
					result = Ajax.AppJsonResult(Constants.APP_SERVER_EXCEPTION, result);
				}			
			
		    } catch (Exception e) {
				logger.error(e);
				result = Ajax.AppJsonResult(Constants.APP_SERVER_EXCEPTION, "发送短信失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * app(账号)登录
	 * @return
	 */
	public String acountLogin(){
		try{
			String data = getRequest().getParameter("data");
			Map<String, String> map = JsonTools.desjsonForMap(data);
			/** 请求参数校验**/
			String  phoneNoStr=map.get("phoneNo");
			String  password=map.get("password");
			if(StringUtils.isEmpty(phoneNoStr)||StringUtils.isEmpty(password)){
				result=Ajax.AppJsonResult(Constants.APP_LOGIN_NULL, "请输入手机号或密码!");
				return SUCCESS;
			}
			if(!isPhone(phoneNoStr)){
				result = Ajax.AppJsonResult(Constants.APP_LOGIN_MOBILE_STYLE, "手机号格式不正确！");
				return SUCCESS;
			}
			Subscriber loginSubscriber=subscriberService.querySubscriberByPhoneNo(phoneNoStr);
			if(loginSubscriber==null){
				result = Ajax.AppJsonResult(Constants.APP_LOGIN_NO_MOBILE, "该手机号没有注册！");
				return SUCCESS;
			}else{
				String pwd = loginSubscriber.getPassword();
				password = Sha1Util.SHA1Encode(password);
				if(password.equals(pwd)){
					//生成Token存入MemCached
					String id = loginSubscriber.getId();
					Date date= new Date();
					String token = TokenUtils.getTokenEncode(String.valueOf(date.getTime()),id);
					String md5id = Md5Util.MD5Encode(id);
					//缓存
					boolean m = MemCachedFactory.set(md5id+"token", token);
					boolean memcached = MemCachedFactory.set(md5id, loginSubscriber);
					if(!memcached&&!m){
						result = Ajax.AppJsonResult(Constants.APP_LOGIN_FAIL, "登录失败,服务异常");
						return SUCCESS;
					}
					Map<String,Object> rm = new HashMap<String,Object>();
					rm.put("token", token);
					rm.put("phoneNoStr", phoneNoStr);
					result = Ajax.AppResult(Constants.APP_LOGIN_SUCCESS, "登录成功",rm);
				    //登录记录
					SubscriberLoginRecord loginRecord =new SubscriberLoginRecord();
					loginRecord.setLoginTime(new Date());
					loginRecord.setSubscriberId(loginSubscriber.getId());
					loginRecord.setTs(new Date());
					loginRecord.setIp(IpUtil.getIpAddr(getRequest()));
					subscriberLoginRecordService.addSubscriberLoginRecord(loginRecord);
				}else{
					result=Ajax.AppJsonResult(Constants.APP_LOGIN_ERROR_PWD, " 登录密码错误!");
					return SUCCESS;
				}
			}
			
		}catch(Exception e){
			result=Ajax.AppJsonResult(Constants.APP_SERVER_EXCEPTION, " 登录异常!");
			logger.error(e);
			e.printStackTrace();
			return SUCCESS;
		}
		return SUCCESS;
	}

	/**
	 * app手机短信登录
	 * @return
	 */
	public String mobileCodeLogin(){
		try{
			String data = getRequest().getParameter("data");
			Map<String, String> map = JsonTools.desjsonForMap(data);
			/** 请求参数校验**/
			String  phoneNoStr=map.get("phoneNo");
			String  smsCodeStr=map.get("smsCode");
			if(StringUtils.isEmpty(phoneNoStr)||StringUtils.isEmpty(smsCodeStr)){
				result=Ajax.AppJsonResult(Constants.APP_LOGIN_NULL, "请输入手机号或验证码!");
				return SUCCESS;
			}
			if(!isPhone(phoneNoStr)){
				result = Ajax.AppJsonResult(Constants.APP_LOGIN_MOBILE_STYLE, "手机号格式不正确！");
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
				result = Ajax.AppJsonResult(Constants.APP_MOBILE_CODE_NO, "验证码已失效请重新获取!");
				return SUCCESS;
			}
			if(!smsCodeStr.equals(smsCode.getCode())){
				result = Ajax.AppJsonResult(Constants.APP_MOBILE_CODE_ERROR, "验证码有误！");
				return SUCCESS;
			}

			//注册流程
			if(loginSubscriber==null){
				Subscriber subscriber = new Subscriber();
				subscriber.setPhoneNo(phoneNoStr);
				loginSubscriber=subscriberService.addSubscriber(subscriber);
				loginSubscriber=subscriberService.querySubscriberByPhoneNo(phoneNoStr);
				//生成Token存入MemCached
				String id = loginSubscriber.getId();
				Date date= new Date();
				String token = TokenUtils.getTokenEncode(String.valueOf(date.getTime()),id);
				String md5id = Md5Util.MD5Encode(id);
				//缓存
				boolean m = MemCachedFactory.set(md5id+"token", token);
				boolean memcached = MemCachedFactory.set(md5id, loginSubscriber);
				if(!memcached&&!m){
					result = Ajax.AppJsonResult(Constants.APP_LOGIN_FAIL, "登录失败,服务异常");
					return SUCCESS;
				}
				Map<String,Object> rm = new HashMap<String,Object>();
				rm.put("token", token);
				rm.put("phoneNoStr", phoneNoStr);
				result = Ajax.AppResult(Constants.APP_LOGIN_SUCCESS, "登录成功",rm);
				
			}else{
				//登录流程	
				if(Subscriber.EVENT_STATE_FUll.equals(loginSubscriber.getEventState())){
					result=Ajax.AppJsonResult(Constants.APP_EVENT_STATE_FUll, "此账号已被锁定，如有疑问，请联系客服!");
					return SUCCESS;
				}
		         subscriberService.updateSubscriber(loginSubscriber);
		         //生成Token存入MemCached
				 String id = loginSubscriber.getId();
				 Date date= new Date();
				 String token = TokenUtils.getTokenEncode(String.valueOf(date.getTime()),id);
				 String md5id = Md5Util.MD5Encode(id);
				 //缓存
				 boolean m = MemCachedFactory.set(md5id+"token", token);
				 boolean memcached = MemCachedFactory.set(md5id, loginSubscriber);
				 if(!memcached&&!m){
					result = Ajax.AppJsonResult(Constants.APP_LOGIN_FAIL, "登录失败,服务异常");
					return SUCCESS;
				 }
				 Map<String,Object> rm = new HashMap<String,Object>();
				 rm.put("token", token);
				 rm.put("phoneNoStr", phoneNoStr);
				 result = Ajax.AppResult(Constants.APP_LOGIN_SUCCESS, "登录成功",rm);
			}
			
			SubscriberLoginRecord loginRecord =new SubscriberLoginRecord();
			loginRecord.setLoginTime(new Date());
			loginRecord.setSubscriberId(loginSubscriber.getId());
			loginRecord.setTs(new Date());
			loginRecord.setIp(IpUtil.getIpAddr(getRequest()));
			subscriberLoginRecordService.addSubscriberLoginRecord(loginRecord);

		}catch(Exception e){
			result=Ajax.AppJsonResult(Constants.APP_SERVER_EXCEPTION, " 登录异常!");
			logger.error(e);
			e.printStackTrace();
			return SUCCESS;
		}
		return SUCCESS;
	
	}
	/**
	* @Description: 获取个人资料信息1.1
	* @param @return   
	* @return String
	 */
	public String appSubsinfo11(){
		try{
			String data = getRequest().getParameter("data");
			subscriber = TokenUtils.getSubscriber(data);
			//个人资料集合
			Map<String,Object> map = new HashMap<String,Object>();
			//赋值
			map.put("name", subscriber.getName());
			map.put("phoneNo", subscriber.getPhoneNo());
			map.put("state", subscriber.getState());
			map.put("nickname", subscriber.getNickname());
			map.put("nickstatus", subscriber.getNickstatus());
			result=Ajax.AppResult(Constants.APP_RESULT_CODE_SUCCESS,"ok",map);
		}catch(Exception e){
			result=Ajax.AppResult(Constants.APP_SERVER_EXCEPTION,"服务器异常",null);
		}
		return SUCCESS;
	}
	
	/**
	* @Description: 获取个人资料信息1.2
	* @param @return   
	* @return String
	 */
	public String appSubsinfo(){
		try{
			String data = getRequest().getParameter("data");
			Map<String, String> pmap = JsonTools.desjsonForMap(data);
			/** 请求参数校验**/
			Double  lat= Double.parseDouble(pmap.get("lat"));
			Double  lng= Double.parseDouble(pmap.get("lng"));
			List<BranchDot> branchDots = branchDotService.searchBranchDot(1);
			int[] distance= new int[branchDots.size()] ;
			int i = 0;
			Map<String,String> disdot = new HashMap<String, String>();
			for (BranchDot branchDot : branchDots) {
				Double dlat = branchDot.getLat();
				Double dlng = branchDot.getLng();
				distance[i]=MapDistance.getDistance(lat, lng, dlat, dlng);
				disdot.put(distance[i]+"", branchDot.getName());
				i++;
			} 
			Quick q = new Quick();
			int [] arrays = q.quick_sort(distance,distance.length);
			System.out.println(disdot.get(arrays[0]+""));
			subscriber = TokenUtils.getSubscriber(data);
			//个人资料集合
			Map<String,Object> map = new HashMap<String,Object>();
			//赋值
			map.put("name", subscriber.getName());
			map.put("phoneNo", subscriber.getPhoneNo());
			map.put("state", subscriber.getState());
			map.put("drivingLicenseNo", subscriber.getDrivingLicenseNo());
			map.put("email", subscriber.getEmail());
			map.put("psubscriber", subscriber.getProvince());
			map.put("emergencyContact", subscriber.getEmergencyContact());
			map.put("emergencyContactPhone", subscriber.getEmergencyContactPhone());
			map.put("sex", subscriber.getSex());
			map.put("distance", disdot.get(arrays[0]+""));
			result=Ajax.AppResult(Constants.APP_RESULT_CODE_SUCCESS,"ok",map);
		}catch(Exception e){
			result=Ajax.AppResult(Constants.APP_SERVER_EXCEPTION,"服务器异常",null);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	public String upSubspwd(){
		try{
			String data = getRequest().getParameter("data");
			Map<String, String> map = JsonTools.desjsonForMap(data);
			String token = map.get("token");
			String oldpwd = map.get("oldpwd");
			String newpwd = Sha1Util.SHA1Encode(map.get("newpwd"));
			
			Map<String, String> toktenmap = TokenUtils.getTokenDecoder(token);
			if(toktenmap!=null){
				subscriber = (Subscriber) MemCachedFactory.get(toktenmap.get("md5Code"));
			}
			String id = subscriber.getId();
			String password = subscriber.getPassword();
			oldpwd = Sha1Util.SHA1Encode(oldpwd);
			if(password.equals(oldpwd)){
				subscriber.setPassword(newpwd);
				subscriberService.updateSubscriber(subscriber);
				MemCachedFactory.set(Md5Util.MD5Encode(id), subscriber);
				result = Ajax.AppJsonResult(Constants.RESULT_CODE_SUCCESS, "密码修改成功！");
			}else{
				result=Ajax.AppJsonResult(Constants.APP_LOGIN_ERROR_PWD, " 输入密码有误!");
			}
			
		}catch(Exception e){
			result=Ajax.AppJsonResult(Constants.APP_SERVER_EXCEPTION,"服务器异常");
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
	public String upphoneno(){
		try{
			//获取参数
			String data = getRequest().getParameter("data");
			Map<String, String> map = JsonTools.desjsonForMap(data);
			String token = map.get("token");
			String newCpyzm = map.get("newCpyzm");
			String oldPhone = map.get("oldphone");
			String newphone = map.get("newphone");
			Map<String, String> toktenmap = TokenUtils.getTokenDecoder(token);
			if(toktenmap!=null){
				subscriber = (Subscriber) MemCachedFactory.get(toktenmap.get("md5Code"));
			}
			String dboldPhone = subscriber.getPhoneNo();
			
			if(StringUtils.isEmpty(oldPhone)){
				result = Ajax.AppJsonResult(Constants.APP_MOBILE_NULL, "请输入旧手机号！");
				return SUCCESS;
			}
			if(!dboldPhone.equals(oldPhone)){
				result = Ajax.AppJsonResult(Constants.APP_MOBILE_OLD_ERROR, "旧手机号输入有误！");
				return SUCCESS;
			}
			if(StringUtils.isEmpty(newphone)){
				result = Ajax.AppJsonResult(Constants.APP_MOBILE_NULL, "请输入新手机号！");
				return SUCCESS;
			}
			
			Subscriber s=subscriberService.querySubscriberByPhoneNo(newphone);
			if(s != null){
				result = Ajax.AppJsonResult(Constants.APP_MOBILE_EXIST, "此手机号已经注册，请更换其他手机号！");
				return SUCCESS;
			}
			//检验 验证码
			SMSCode newSmsCode=new SMSCode();
			newSmsCode.setPhoneNo(newphone);
			newSmsCode.setType(Constants.SUBSCIRBER_CHANGE_NEW_PHONE_CODE);
			newSmsCode.setChannel(Account.PAY_CHANNEL_APP);
			newSmsCode=smsCodeService.getLatestSMSCode(newSmsCode,Constants.REGISTER_SMS_VALID_MINUTE );
			if(newSmsCode==null){
				result = Ajax.AppJsonResult(Constants.APP_MOBILE_CODE_NO, "手机号验证码已失效请重新获取!");
				return SUCCESS;
			}
			
			if(StringUtils.isEmpty(newCpyzm)){
				result = Ajax.AppJsonResult(Constants.APP_MOBILE_CODE_NULL, "手机号验证码不能为空！");
				return SUCCESS;
			}
			
			if(!newCpyzm.equals(newSmsCode.getCode())){
				result = Ajax.AppJsonResult(Constants.APP_MOBILE_CODE_ERROR, "手机验证码有误！");
				return SUCCESS;
			}
			
			Subscriber accSubscriber=subscriber;
			accSubscriber.setPhoneNo(newphone);
			//校验完成，保存会员信息
			subscriberService.updateSubscriber(accSubscriber);
			result = Ajax.AppJsonResult(Constants.RESULT_CODE_SUCCESS, "手机号码修改成功！");
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("手机号码变更", "前"+oldPhone+"，后"+newphone);
			SystemOperateLogUtil.sysAddOperateLog(accSubscriber.getId(), null, SystemOperateLogUtil.MODEL_SUBSCRIBER_INFO,contentMap);
			
			getSession().setAttribute(Constants.SESSION_SUBSCRIBER,accSubscriber);
			String content = "尊敬的用户，您的手机号码修改成功，如非本人操作，请关注账号安全。";
			SMSUtil.sendSMS(oldPhone, content,SMSRecord.TYPE_PWD);
		}catch(Exception e){
			logger.error(e);
			result = Ajax.AppJsonResult(Constants.APP_SERVER_EXCEPTION, "系统异常！");
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
	public String appregLH(){
		try {
			String data = getRequest().getParameter("data");
			subscriber = TokenUtils.getSubscriber(data);
			Map<String, String> map = JsonTools.desjsonForMap(data);
			String name = map.get("name");
			String state = subscriber.getState().toString();
			
			if(StringUtils.isEmpty(name)){
				result = Ajax.AppJsonResult(Constants.APP_NAME_NULL, "真实姓名不能为空！");
				return SUCCESS;
			}
		    if(Subscriber.STATE_WAIT_CONFIRMED.equals(state) ){
		    	result = Ajax.AppJsonResult(Constants.APP_STATE_WAIT_CONFIRMED, "资料正在审核,无需再次上传，请耐心等待!");
				return SUCCESS;
		    }
		    if(Subscriber.STATE_NORMAL.equals(state) || Subscriber.STATE_CARD_ISSUED.equals(state) ){
		    	result = Ajax.AppJsonResult(Constants.APP_STATE_NORMAL, "资料已审核通过，不能再次上传!");
				return SUCCESS;
		    }

			String idCardImgName=null;//身份证
			String drivingImgName=null;//驾驶证
			if(upload!=null&& uploadFileName!=null){
				try {
					 idCardImgName=ImageHelper.uploadPic(upload,uploadFileName,uploadContentType,maxUploadSize,uploadPath);
				} catch (Exception e) {
					result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, e.getMessage());
					return SUCCESS;
				} 
			}else{
				result = Ajax.AppJsonResult(Constants.APP_IDCARD_NULL, "身份证照片不能为空!");
				return SUCCESS;
			}
			
			if(drivingUpload!=null && drivingUploadFileName!=null ){
				try {
					drivingImgName=ImageHelper.uploadPic(drivingUpload,drivingUploadFileName,drivingUploadContentType,maxUploadSize,uploadPath);
				} catch (Exception e) {
					result = Ajax.AppJsonResult(Constants.APP_RESULT_CODE_FAIL, e.getMessage());
					return SUCCESS;
				} 
			}else{
				result = Ajax.AppJsonResult(Constants.APP_DRIVING_NULL, "驾驶证照片不能为空!");
				return SUCCESS;
			}
			subscriber.setName(name);
			subscriber.setState(Subscriber.STATE_WAIT_CONFIRMED);
			if(!StringUtils.isEmpty(idCardImgName)){
				subscriber.setIdCardImg(uploadPath+idCardImgName);
			}
			if(!StringUtils.isEmpty(drivingImgName)){
				subscriber.setDrivingLicenseImg(uploadPath+drivingImgName);
			}
		    subscriberService.completeRegister(subscriber,true);
		    result = Ajax.AppJsonResult(Constants.APP_LOGIN_SUCCESS, "数据提交成功,请等待审核！");
		} catch (Exception e) {
			logger.error(e);
			result = Ajax.AppJsonResult(Constants.APP_SERVER_EXCEPTION, "系统异常！");
		}
		return SUCCESS;
	}
	
	/**
	 * 获取当前用户一定距离以内的网点
	 * @return
	 */
	public String appbranchdots(){
		try{
			String data = getRequest().getParameter("data");
			Map<String, String> pmap = JsonTools.desjsonForMap(data);
			/** 请求参数校验**/
			String lat= pmap.get("lat");
			String lng= pmap.get("lng");
			String raidus = "3000";
			Map map = MapDistance.getAround(lat, lng, raidus);
			System.out.println(map);
			List<BranchDot> branchDots = branchDotService.branchDots(map.get("minLng").toString()
					,map.get("maxLng").toString(),map.get("minLat").toString(),map.get("maxLat").toString());
			List<Map<String,Object>> list = new ArrayList<Map<String, Object>>(); 
			
			int[] distance= new int[branchDots.size()] ;
			int i = 0;
			Map<String,String> disdot = new HashMap<String, String>();
			for (BranchDot branchDot : branchDots) {
				String id = branchDot.getId();
				Integer carcount = branchDotService.getCarCount(id);
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("carcount", carcount);
				m.put("name", branchDot.getName());
				m.put("latlng", branchDot.getLat()+","+branchDot.getLng());
				list.add(m);
				if(carcount>0){
					//网点与当前位置的距离
					distance[i]=MapDistance.getDistance(Double.parseDouble(lat), Double.parseDouble(lng),
							Double.parseDouble(branchDot.getLat().toString()), Double.parseDouble(branchDot.getLng().toString()));
					disdot.put(distance[i]+"", branchDot.getName()+",,"+branchDot.getLat()+","+branchDot.getLng());
					i++;
				}
			}
			 
			Quick q = new Quick();
			int [] arrays = q.quick_sort(distance,distance.length);
			
			Map<String,Object> user = new HashMap<String,Object>();
			user.put("ulatlng", lat+","+lng);
			user.put("dlatlng", disdot.get(arrays[0]+"").split(",,")[1]);
			user.put("dotname", disdot.get(arrays[0]+"").split(",,")[0]);
			user.put("distance",arrays[0]);
			Map<String,Object> rm = new HashMap<String,Object>();
			rm.put("dots", list);
			rm.put("user", user);
			result = Ajax.AppbzJsonResult(Constants.APP_LOGIN_SUCCESS, "成功", rm);
		}catch(Exception e){
			logger.error(e);
			result = Ajax.AppJsonResult(Constants.APP_SERVER_EXCEPTION, "系统异常！");
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

	public AppToken getAppToken() {
		return appToken;
	}

	public void setAppToken(AppToken appToken) {
		this.appToken = appToken;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public WechatUserInfoService getWechatUserInfoService() {
		return wechatUserInfoService;
	}

	public void setWechatUserInfoService(WechatUserInfoService wechatUserInfoService) {
		this.wechatUserInfoService = wechatUserInfoService;
	}

	public WechatUserInfo getWechatUserInfo() {
		return wechatUserInfo;
	}

	public void setWechatUserInfo(WechatUserInfo wechatUserInfo) {
		this.wechatUserInfo = wechatUserInfo;
	}
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}
	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
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
