package com.dearho.cs.subscriber.action.register;


/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberRegisterAction.java creation date:[2015-5-18 上午10:04:40] by liusong
 *http://www.dearho.com
 */

import java.io.File;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.ImageHelper;
import com.opensymphony.webwork.ServletActionContext;
/**
 * @Author liusong
 * @Description 会员注册
 * @Version 1.0,2015-5-18
 *
 */
public class SubscriberRegisterAction extends AbstractAction {

	private static final long serialVersionUID = -3312264055146365391L;
	
	private static final Log logger = LogFactory.getLog(SubscriberRegisterAction.class);
	
	private SubscriberService subscriberService;
	private Subscriber subscriber ;
	
	
	private String retMsg;
	
	
	private File upload;  //文件
	private String uploadContentType;  //文件类型
	private String uploadFileName;   //文件名
	
	
	

	private static final String uploadPath="upload/image/";
	
	
	public SubscriberRegisterAction() {
		super();
		subscriber=new Subscriber();
	}

	
	@Override
	public String process() {
		return SUCCESS;
	}
	
	/**
	 * 会员注册第一步
	 * @return
	 */
	public String registerOne(){
		if(StringUtils.isEmpty(subscriber.getPhoneNo())||StringUtils.isEmpty(subscriber.getPassword())){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请输入手机号或密码！");
			return "fail";
		}
		String code=ServletActionContext.getRequest().getParameter("code");
		if(StringUtils.isEmpty(code)){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请输入验证码！");
			return "fail";
		}
		
//		if(!code.equals(subscriberService.findLatestMobileCode())){
//			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "验证码有误！");
//			return "fail";
//		}
		
//		Subscriber s=subscriberService.querySubscriberByPhoneNo(subscriber.getPhoneNo());
//		if(s!=null){
//			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "此手机号已注册！");
//			return "fail";
//		}
//		try{
//			subscriberService.addSubscriber(subscriber);
//			
//			HttpSession session = ServletActionContext.getRequest().getSession();
//			session.setAttribute(Constants.SESSION_MEMBER, subscriber);
//			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "登录成功！");
//		}catch(Exception e){
//			logger.error(e);
//			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "系统异常！");
//			
//		}
		return SUCCESS;
 }
	
	/**
	 * 会员注册--下发手机验证码
	 * @return
	 */
	public String sendPhoneVerificationCode(){
//		if(StringUtils.isEmpty(subscriber.getPhoneNo())){
//			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "手机号不能为空!");
//			return 	SUCCESS;
//		}
//		subscriberService.sendMobileCode(subscriber.getPhoneNo());
//		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "下发成功!");
	return SUCCESS;
	}
	/**
	 * 会员注册第二步
	 * 保存上传证件信息
	 * @return
	 */
/*	public String registerTwo(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber)session.getAttribute(Constants.SESSION_MEMBER);
		if(s==null){
			return LOGIN;
		}
		if(StringUtils.isEmpty(subscriber.getName())){
			retMsg="真实姓名不能为空!";
			return "fail";
		}
		if(StringUtils.isEmpty(subscriber.getIdCardImg())){
			retMsg="身份证照片不能为空!";
			return "fail";
		}
		if(StringUtils.isEmpty(subscriber.getIdCardImg())){
			retMsg="驾驶证照片不能为空!";
			return "fail";
		}
		s=subscriberService.querySubscriberById(s.getId());
		s.setName(subscriber.getName());
		s.setIdCardImg(subscriber.getIdCardImg());
		s.setDrivingLicenseImg(subscriber.getDrivingLicenseImg());
	    s.setState(Subscriber.STATE_UNCONFIRMED);
	    subscriberService.updateSubscriber(s);
		
		session.setAttribute(Constants.SESSION_MEMBER,s);
		return SUCCESS;
	}
*/	/**
	 * 完善用户信息
	 * @return
	 */
/*	public String registerFinish(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber)session.getAttribute(Constants.SESSION_MEMBER);
		if(s==null){
			return LOGIN;
		}
		s=subscriberService.querySubscriberById(s.getId());
		s.setEmail(subscriber.getEmail());
	
		
		subscriberService.updateSubscriber(s);
		
		session.setAttribute(Constants.SESSION_MEMBER,s);
		
		return SUCCESS;
	}
	*/
	
	
	/**
	 * 上传证件图片(原图和用户截图区域)
	 * @return
	 */
	public String upload(){
		try{
			String canvasData = ServletActionContext.getRequest().getParameter("imageData");
			if(upload==null ||canvasData==null){
				result=new JSONObject().element("result", "").element("state", 500).element("message", "上传数据为空!").toString();
				return SUCCESS;
			}
			
			//上传原图
			String imageName=ImageHelper.uploadPic(upload, uploadFileName,uploadContentType, 1024*1024*10, uploadPath);
			log.info("原图路径:"+imageName);
			
			String imageNameCut=imageName.substring(0, imageName.indexOf("."))+"_cut.png";

			//上传裁剪图
			imageNameCut=ImageHelper.uploadForCanvas(canvasData, uploadPath, imageNameCut);
			
			result=new JSONObject().element("result", uploadPath+imageNameCut).element("state", 200).element("message", "成功").element("image",uploadPath+imageName).toString();
			
		}catch(Exception e){
			logger.error(e);
			result=new JSONObject().element("result", "").element("state", 500).element("message", "系统异常!").toString();
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	
	
	/**
	 * 上传用户头像
	 * @return
	 */
	public String uploadAvatar(){
		String canvasData = ServletActionContext.getRequest().getParameter("imageData");
		if(canvasData==null){
			result=new JSONObject().element("result", "").element("state", 500).element("message", "上传数据为空!").toString();
			return SUCCESS;
		}
		try {
			String imageName=ImageHelper.uploadForCanvas(canvasData, uploadPath, null);
			
			result=new JSONObject().element("result", uploadPath+imageName).element("state", 200).element("message", "成功").toString();
		} catch (Exception e) {
			logger.error(e);
			result=new JSONObject().element("result", "").element("state", 500).element("message", "系统异常!").toString();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	
	
	
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
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



	

}
