package com.dearho.cs.carservice.action.patrol.car;


import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.dearho.cs.carservice.pojo.CarPatrol;
import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.carservice.service.CarPatrolService;
import com.dearho.cs.carservice.service.ParkingPatrolService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.ImageHelper;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:14:43
 */
public class CarPatrolAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarPatrolService carPatrolService;
	
	private CarPatrol carPatrol;
	
	private ParkingPatrolService parkingPatrolService;
	
	private File frontPhoto;
	private String frontPhotoContentType;
	private String frontPhotoFileName;
	private File backPhoto;
	private String backPhotoContentType;
	private String backPhotoFileName;
	private File leftPhoto;
	private String leftPhotoContentType;
	private String leftPhotoFileName;
	private File rightPhoto;
	private String rightPhotoContentType;
	private String rightPhotoFileName;
	private File sparePhoto1;
	private String sparePhoto1ContentType;
	private String sparePhoto1FileName;
	private File sparePhoto2;
	private String sparePhoto2ContentType;
	private String sparePhoto2FileName;
	
	public CarPatrolAddAction(){
		super();
		carPatrol = new CarPatrol();
	}
	
	public String process(){
		try {
			if(carPatrol != null && !StringHelper.isRealNull(carPatrol.getCode())){
				List<CarPatrol> list = carPatrolService.searchCarPatrol(carPatrol);
				if (list != null && list.size() > 0){
					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "编码已存在！");
					return Action.ERROR;
				}
			}
			
			ParkingPatrol pp = parkingPatrolService.searchParkingPatrolById(carPatrol.getParkingPatrolId());
			if(pp != null){
				carPatrol.setDotId(pp.getDotId());
			}
			
			if(frontPhoto != null){
				carPatrol.setFrontPhoto(ImageHelper.uploadPic(frontPhoto, frontPhotoFileName, 
						frontPhotoContentType, 1024*1024*10, "carpatrolimgs"));
			}
			if(backPhoto != null){
				carPatrol.setBackPhoto(ImageHelper.uploadPic(backPhoto, backPhotoFileName, 
						backPhotoContentType, 1024*1024*10, "carpatrolimgs"));
			}
			if(leftPhoto != null){
				carPatrol.setLeftPhoto(ImageHelper.uploadPic(leftPhoto, leftPhotoFileName, 
						leftPhotoContentType, 1024*1024*10, "carpatrolimgs"));
			}
			if(rightPhoto != null){
				carPatrol.setRightPhoto(ImageHelper.uploadPic(rightPhoto, rightPhotoFileName, 
						rightPhotoContentType, 1024*1024*10, "carpatrolimgs"));
			}
			if(sparePhoto1 != null){
				carPatrol.setSparePhoto1(ImageHelper.uploadPic(sparePhoto1, sparePhoto1FileName, 
						sparePhoto1ContentType, 1024*1024*10, "carpatrolimgs"));
			}
			if(sparePhoto2 != null){
				carPatrol.setSparePhoto2(ImageHelper.uploadPic(sparePhoto2, sparePhoto2FileName, 
						sparePhoto2ContentType, 1024*1024*10, "carpatrolimgs"));
			}
			
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user != null){
				carPatrol.setCreatorId(user.getId());
			}
			carPatrol.setIsDiscard(0);
			carPatrol.setCreateTime(new Date());
			carPatrol.setTs(new Date());
			carPatrolService.addCarPatrol(carPatrol);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "保存成功！");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "保存失败！");
			return Action.ERROR;
		}
	}

	public CarPatrolService getCarPatrolService() {
		return carPatrolService;
	}

	public void setCarPatrolService(CarPatrolService carPatrolService) {
		this.carPatrolService = carPatrolService;
	}

	public CarPatrol getCarPatrol() {
		return carPatrol;
	}

	public void setCarPatrol(CarPatrol carPatrol) {
		this.carPatrol = carPatrol;
	}
	public ParkingPatrolService getParkingPatrolService() {
		return parkingPatrolService;
	}
	public void setParkingPatrolService(
			ParkingPatrolService parkingPatrolService) {
		this.parkingPatrolService = parkingPatrolService;
	}

	public File getFrontPhoto() {
		return frontPhoto;
	}

	public void setFrontPhoto(File frontPhoto) {
		this.frontPhoto = frontPhoto;
	}

	public String getFrontPhotoContentType() {
		return frontPhotoContentType;
	}

	public void setFrontPhotoContentType(String frontPhotoContentType) {
		this.frontPhotoContentType = frontPhotoContentType;
	}

	public String getFrontPhotoFileName() {
		return frontPhotoFileName;
	}

	public void setFrontPhotoFileName(String frontPhotoFileName) {
		this.frontPhotoFileName = frontPhotoFileName;
	}

	public File getBackPhoto() {
		return backPhoto;
	}

	public void setBackPhoto(File backPhoto) {
		this.backPhoto = backPhoto;
	}

	public String getBackPhotoContentType() {
		return backPhotoContentType;
	}

	public void setBackPhotoContentType(String backPhotoContentType) {
		this.backPhotoContentType = backPhotoContentType;
	}

	public String getBackPhotoFileName() {
		return backPhotoFileName;
	}

	public void setBackPhotoFileName(String backPhotoFileName) {
		this.backPhotoFileName = backPhotoFileName;
	}

	public File getLeftPhoto() {
		return leftPhoto;
	}

	public void setLeftPhoto(File leftPhoto) {
		this.leftPhoto = leftPhoto;
	}

	public String getLeftPhotoContentType() {
		return leftPhotoContentType;
	}

	public void setLeftPhotoContentType(String leftPhotoContentType) {
		this.leftPhotoContentType = leftPhotoContentType;
	}

	public String getLeftPhotoFileName() {
		return leftPhotoFileName;
	}

	public void setLeftPhotoFileName(String leftPhotoFileName) {
		this.leftPhotoFileName = leftPhotoFileName;
	}

	public File getRightPhoto() {
		return rightPhoto;
	}

	public void setRightPhoto(File rightPhoto) {
		this.rightPhoto = rightPhoto;
	}

	public String getRightPhotoContentType() {
		return rightPhotoContentType;
	}

	public void setRightPhotoContentType(String rightPhotoContentType) {
		this.rightPhotoContentType = rightPhotoContentType;
	}

	public String getRightPhotoFileName() {
		return rightPhotoFileName;
	}

	public void setRightPhotoFileName(String rightPhotoFileName) {
		this.rightPhotoFileName = rightPhotoFileName;
	}

	public File getSparePhoto1() {
		return sparePhoto1;
	}

	public void setSparePhoto1(File sparePhoto1) {
		this.sparePhoto1 = sparePhoto1;
	}

	public String getSparePhoto1ContentType() {
		return sparePhoto1ContentType;
	}

	public void setSparePhoto1ContentType(String sparePhoto1ContentType) {
		this.sparePhoto1ContentType = sparePhoto1ContentType;
	}

	public String getSparePhoto1FileName() {
		return sparePhoto1FileName;
	}

	public void setSparePhoto1FileName(String sparePhoto1FileName) {
		this.sparePhoto1FileName = sparePhoto1FileName;
	}

	public File getSparePhoto2() {
		return sparePhoto2;
	}

	public void setSparePhoto2(File sparePhoto2) {
		this.sparePhoto2 = sparePhoto2;
	}

	public String getSparePhoto2ContentType() {
		return sparePhoto2ContentType;
	}

	public void setSparePhoto2ContentType(String sparePhoto2ContentType) {
		this.sparePhoto2ContentType = sparePhoto2ContentType;
	}

	public String getSparePhoto2FileName() {
		return sparePhoto2FileName;
	}

	public void setSparePhoto2FileName(String sparePhoto2FileName) {
		this.sparePhoto2FileName = sparePhoto2FileName;
	}



}
