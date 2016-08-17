package com.dearho.cs.car.action;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.ImageHelper;
import com.opensymphony.webwork.ServletActionContext;

public class CarVehicleModelAddAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private CarVehicleModel carVehicleModel;
	
	private CarVehicleModelService carVehicleModelService;
	
	private File webModelPhoto;
	private String webModelPhotoContentType;
	private String webModelPhotoFileName;
	
	private File androidModelPhoto;
	private String androidModelPhotoContentType;
	private String androidModelPhotoFileName;
	
	private File IOSModelPhoto;
	private String IOSModelPhotoContentType;
	private String IOSModelPhotoFileName;
	
	private File microWebModelPhoto;
	private String microWebModelPhotoContentType;
	private String microWebModelPhotoFileName;
	
	
	public CarVehicleModel getCarVehicleModel() {
		return carVehicleModel;
	}


	public void setCarVehicleModel(CarVehicleModel carVehicleModel) {
		this.carVehicleModel = carVehicleModel;
	}


	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}


	public void setCarVehicleModelService(
			CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}

	
	public CarVehicleModelAddAction() {
		super();
		carVehicleModel=new CarVehicleModel();
	}


	@Override
	public String process() {
		try{
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(webModelPhoto != null){
				carVehicleModel.setWebModelPhoto(ImageHelper.uploadPic(webModelPhoto, webModelPhotoFileName, 
						webModelPhotoContentType, 1024*1024*10, "vehiclemodelimgs/web"));
			}
			
			if(androidModelPhoto != null){
				carVehicleModel.setAndroidModelPhoto(ImageHelper.uploadPic(androidModelPhoto, androidModelPhotoFileName, 
						androidModelPhotoContentType, 1024*1024*10, "vehiclemodelimgs/android"));
			}
			
			if(webModelPhoto != null){
				carVehicleModel.setIOSModelPhoto(ImageHelper.uploadPic(IOSModelPhoto, IOSModelPhotoFileName, 
						IOSModelPhotoContentType, 1024*1024*10, "vehiclemodelimgs/ios"));
			}
			if(microWebModelPhoto != null){
				carVehicleModel.setMicroWebModelPhoto(ImageHelper.uploadPic(microWebModelPhoto, microWebModelPhotoFileName, 
						microWebModelPhotoContentType, 1024*1024*10, "vehiclemodelimgs/microweb"));
			}
			
			carVehicleModel.setCreatorId(user.getId());
			carVehicleModel.setCreateDate(new Date());
			carVehicleModel.setTs(new Date());
			carVehicleModelService.addVeicleModel(carVehicleModel);
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "添加成功!");
			
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("车辆型号", carVehicleModel.getName());
			SystemOperateLogUtil.sysAddOperateLog(carVehicleModel.getId(), user, "车型管理", contentMap);
		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "添加失败!");
			return ERROR;
		}
		
		return SUCCESS;
	}


	public File getWebModelPhoto() {
		return webModelPhoto;
	}


	public void setWebModelPhoto(File webModelPhoto) {
		this.webModelPhoto = webModelPhoto;
	}


	public String getWebModelPhotoContentType() {
		return webModelPhotoContentType;
	}


	public void setWebModelPhotoContentType(String webModelPhotoContentType) {
		this.webModelPhotoContentType = webModelPhotoContentType;
	}


	public String getWebModelPhotoFileName() {
		return webModelPhotoFileName;
	}


	public void setWebModelPhotoFileName(String webModelPhotoFileName) {
		this.webModelPhotoFileName = webModelPhotoFileName;
	}


	public File getAndroidModelPhoto() {
		return androidModelPhoto;
	}


	public void setAndroidModelPhoto(File androidModelPhoto) {
		this.androidModelPhoto = androidModelPhoto;
	}


	public String getAndroidModelPhotoContentType() {
		return androidModelPhotoContentType;
	}


	public void setAndroidModelPhotoContentType(String androidModelPhotoContentType) {
		this.androidModelPhotoContentType = androidModelPhotoContentType;
	}


	public String getAndroidModelPhotoFileName() {
		return androidModelPhotoFileName;
	}


	public void setAndroidModelPhotoFileName(String androidModelPhotoFileName) {
		this.androidModelPhotoFileName = androidModelPhotoFileName;
	}


	public File getIOSModelPhoto() {
		return IOSModelPhoto;
	}


	public void setIOSModelPhoto(File iOSModelPhoto) {
		IOSModelPhoto = iOSModelPhoto;
	}


	public String getIOSModelPhotoContentType() {
		return IOSModelPhotoContentType;
	}


	public void setIOSModelPhotoContentType(String iOSModelPhotoContentType) {
		IOSModelPhotoContentType = iOSModelPhotoContentType;
	}


	public String getIOSModelPhotoFileName() {
		return IOSModelPhotoFileName;
	}


	public void setIOSModelPhotoFileName(String iOSModelPhotoFileName) {
		IOSModelPhotoFileName = iOSModelPhotoFileName;
	}


	public File getMicroWebModelPhoto() {
		return microWebModelPhoto;
	}


	public void setMicroWebModelPhoto(File microWebModelPhoto) {
		this.microWebModelPhoto = microWebModelPhoto;
	}


	public String getMicroWebModelPhotoContentType() {
		return microWebModelPhotoContentType;
	}


	public void setMicroWebModelPhotoContentType(
			String microWebModelPhotoContentType) {
		this.microWebModelPhotoContentType = microWebModelPhotoContentType;
	}


	public String getMicroWebModelPhotoFileName() {
		return microWebModelPhotoFileName;
	}


	public void setMicroWebModelPhotoFileName(String microWebModelPhotoFileName) {
		this.microWebModelPhotoFileName = microWebModelPhotoFileName;
	}


	

}
