package com.dearho.cs.subscriber.action.register;


/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberRegisterOneAction.java creation date:[2015-5-18 上午10:04:40] by liusong
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
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.ImageHelper;
import com.opensymphony.webwork.ServletActionContext;
/**
 * @Author liusong
 * @Description 会员注册第二步 
 * @Version 1.0,2015-5-18
 *
 */
public class SubscriberRegisterTwoAction extends AbstractAction {

	private static final long serialVersionUID = -3312264055146365391L;
	
	private static final Log logger = LogFactory.getLog(SubscriberRegisterTwoAction.class);
	private static final Integer maxUploadSize=1024*1024*5;
	private SubscriberService subscriberService;
	private Subscriber subscriber ;
	

	
	
	private String retMsg;
	
	
	private File  upload;  //文件
	private String uploadContentType;  //文件类型
	private String uploadFileName;   //文件名
	
	private File drivingUpload;  //文件
	private String drivingUploadContentType;  //文件类型
	private String drivingUploadFileName;   //文件名
	
	private static final String uploadPath="upload/authImage/";
	private static final String avatarUploadPath="upload/avatar/subscriber/";
	
	
	public SubscriberRegisterTwoAction() {
		super();
		subscriber=new Subscriber();
	}

	
	@Override
	public String process() {

		HttpSession session = ServletActionContext.getRequest().getSession();
		if(session.getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		Subscriber s=(Subscriber)session.getAttribute(Constants.SESSION_SUBSCRIBER);
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
		if(StringUtils.isEmpty(subscriber.getDrivingLicenseNo())){
			retMsg="驾驶证号不能为空!";
			return "fail";
		}
//		if(StringUtils.isEmpty(subscriber.getPostAddress())){
//			retMsg="邮寄地址不能为空!";
//			return "fail";
//		}
		s = subscriberService.querySubscriberById(s.getId());
	    if(Subscriber.STATE_WAIT_CONFIRMED.equals(s.getState()) ){
	    	retMsg="资料正在审核,无需再次上传，请耐心等待!";
			return "fail";
	    }
	    if(Subscriber.STATE_NORMAL.equals(s.getState()) || Subscriber.STATE_CARD_ISSUED.equals(s.getState()) ){
	    	retMsg="资料已审核通过，不能再次上传!";
			return "fail";
	    }
		subscriber.setId(s.getId());
	    subscriberService.completeRegister(subscriber,false);
		
	
		return SUCCESS;
	
		
	}
	
	/**
	 * 第二步会员注册成功
	 * @return
	 */
	public String registerFinish(){
		return SUCCESS;
	}
	
	
	
	
	public String mobileRegister() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if(session.getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		Subscriber s=(Subscriber)session.getAttribute(Constants.SESSION_SUBSCRIBER);
		s = subscriberService.querySubscriberById(s.getId());
		if(StringUtils.isEmpty(subscriber.getName())){
			retMsg="真实姓名不能为空!";
			return "fail";
		}
		if(StringUtils.isEmpty(subscriber.getDrivingLicenseNo())){
			retMsg="驾驶证号不能为空!";
			return "fail";
		}
		
		if(StringUtils.isEmpty(subscriber.getProvince())){
			retMsg="省份不能为空!";
			return "fail";
		}
		if(StringUtils.isEmpty(subscriber.getCity())){
			retMsg="地级市不能为空!";
			return "fail";
		}
		if(StringUtils.isEmpty(subscriber.getCounty())){
			retMsg="县级市不能为空!";
			return "fail";
		}
		if(StringUtils.isEmpty(subscriber.getAddress())){
			retMsg="家庭住址不能为空!";
			return "fail";
		}
		if(StringUtils.isEmpty(s.getIdCardImg())){
			if(upload==null){
				retMsg = "身份证照片不能为空!";
				return "fail";
			}
		}
		if(StringUtils.isEmpty(s.getDrivingLicenseImg())){
			if(drivingUpload==null){
				retMsg = "驾驶证照片不能为空!";
				return "fail";
			}
		}
		
	    if(Subscriber.STATE_WAIT_CONFIRMED.equals(s.getState()) ){
	    	retMsg="资料正在审核,无需再次上传，请耐心等待!";
			return "fail";
	    }
	    if(Subscriber.STATE_NORMAL.equals(s.getState()) || Subscriber.STATE_CARD_ISSUED.equals(s.getState()) ){
	    	retMsg="资料已审核通过，不能再次上传!";
			return "fail";
	    }
	    
		
		String idCardImgName=null;
		String drivingImgName=null;
		
		if(upload!=null&& uploadFileName!=null){
			try {
				 idCardImgName=ImageHelper.uploadPic(upload,uploadFileName,uploadContentType,maxUploadSize,uploadPath);
			} catch (Exception e) {
				retMsg = e.getMessage();
				return "fail";
			} 
		}
		
		
		if(drivingUpload!=null && drivingUploadFileName!=null ){
			try {
				drivingImgName=ImageHelper.uploadPic(drivingUpload,drivingUploadFileName,drivingUploadContentType,maxUploadSize,uploadPath);
			} catch (Exception e) {
				retMsg = e.getMessage();
				return "fail";
			} 
		}
		
		
		subscriber.setId(s.getId());
		if(!StringUtils.isEmpty(idCardImgName)){
			subscriber.setIdCardImg(uploadPath+idCardImgName);
		}
		if(!StringUtils.isEmpty(drivingImgName)){
			subscriber.setDrivingLicenseImg(uploadPath+drivingImgName);
		}
	    subscriberService.completeRegister(subscriber,true);
		
		return SUCCESS;
		
	}
	
	
	
	
	/**
	 * 上传证件图片(原图和用户截图区域)
	 * @return
	 */
	/*public String upload(File upload,String uploadFileName,String uploadContentType,String canvasData) throws Exception{
		
			if(upload==null ||canvasData==null){
				throw new Exception("截图数据为空!");
				//result=new JSONObject().element("result", "").element("state", 500).element("message", "上传数据为空!").toString();
				//return SUCCESS;
			}
			
			//上传原图
			String imageName=ImageHelper.uploadPic(upload, uploadFileName,uploadContentType, 1024*1024*10, uploadPath);
			
			
			String imageNameCut=imageName.substring(0, imageName.indexOf("."))+"_cut.png";

			//上传裁剪图
			imageNameCut=ImageHelper.uploadForCanvas(canvasData, uploadPath, imageNameCut);
			
			return imageName;
			//result=new JSONObject().element("result", uploadPath+imageNameCut).element("state", 200).element("message", "成功").element("image",uploadPath+imageName).toString();
	
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
			String imageName=ImageHelper.uploadImgForAutoName(upload, uploadFileName,uploadContentType, maxUploadSize, uploadPath);
			
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
		
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			result=new JSONObject().element("result", "").element("state", 500).element("message", "请重新登录!").toString();
			return SUCCESS;
		}
	
		
		try {
			String imageName=ImageHelper.uploadForCanvas(canvasData, avatarUploadPath, null);
			
			
			Subscriber s=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
			Subscriber subscriber=subscriberService.querySubscriberById(s.getId());
			subscriber.setAvatar(avatarUploadPath+imageName);
			subscriberService.updateSubscriber(subscriber);
			
			String imagePath="";
			if(StringUtils.isEmpty(getRequest().getContextPath())){
				imagePath="/"+avatarUploadPath+imageName;
			}else{
				imagePath=getRequest().getContextPath()+"/"+avatarUploadPath+imageName;
			}
			result=new JSONObject().element("result", imagePath).element("state", 200).element("message", "成功").toString();
		} catch (Exception e) {
			logger.error(e);
			result=new JSONObject().element("result", "").element("state", 500).element("message", "系统异常!").toString();
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	
	public String mobileUpload(){
		
		if(upload==null){
			result=new JSONObject().element("result", "").element("state", 500).element("message", "上传数据为空!").toString();
			return SUCCESS;
		}
		
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			result=new JSONObject().element("result", "").element("state", 500).element("message", "请重新登录!").toString();
			return SUCCESS;
		}
	
		
		try {
			String imageName=ImageHelper.uploadPic(upload, uploadFileName, uploadContentType, maxUploadSize, uploadPath);
			

			result=new JSONObject().element("result", uploadPath+File.pathSeparatorChar+imageName).element("state", 200).element("message", "成功").toString();
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


	public static String getUploadpath() {
		return uploadPath;
	}




}
