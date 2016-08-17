package com.dearho.cs.carservice.action.accident;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarAccident;
import com.dearho.cs.carservice.service.CarAccidentService;
import com.dearho.cs.carservice.util.CarServiceUtil;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.ImageHelper;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:30:49
 */
public class CarAccidentUpdateAction extends CarAccidentAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File accidentImage0;
	private String accidentImage0ContentType;
	private String accidentImage0FileName;
	
	private File accidentImage1;
	private String accidentImage1ContentType;
	private String accidentImage1FileName;

	private File accidentImage2;
	private String accidentImage2ContentType;
	private String accidentImage2FileName;

	private File accidentImage3;
	private String accidentImage3ContentType;
	private String accidentImage3FileName;

	private File accidentImage4;
	private String accidentImage4ContentType;
	private String accidentImage4FileName;

	private File accidentImage5;
	private String accidentImage5ContentType;
	private String accidentImage5FileName;

	private File accidentImage6;
	private String accidentImage6ContentType;
	private String accidentImage6FileName;

	private File accidentImage7;
	private String accidentImage7ContentType;
	private String accidentImage7FileName;

	private File accidentImage8;
	private String accidentImage8ContentType;
	private String accidentImage8FileName;

	private File accidentImage9;
	private String accidentImage9ContentType;
	private String accidentImage9FileName;


	private String formData;
	
	private String[] deletePhotoArr;
	
	private CarAccidentService carAccidentService;
	private CarService carService;
	
	public String process(){
		try {
			try {
				formData = java.net.URLDecoder.decode(formData, "utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			CarAccident updateAccident = new CarAccident();
			String id = CarServiceUtil.findValue("carAccident.id", formData);
			updateAccident = carAccidentService.searchCarAccidentById(id);
			boolean flag = CarServiceUtil.paramStr2Obj(formData,updateAccident);
			if(!flag){
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败!");
				return ERROR;
			}
			CarAccident oldCa = getCarAccidentService().searchCarAccidentById(updateAccident.getId());
			
			if(!StringHelper.isRealNull(oldCa.getHandleStatus()) && !StringHelper.isRealNull(updateAccident.getHandleStatus())){
				if(oldCa.getHandleStatus().compareTo(updateAccident.getHandleStatus()) > 0){
					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！处理状态不能后退！");
					return Action.ERROR;
				}
			}
			
			String imgUrls = "";
			if(accidentImage0 != null){
				updateAccident.setPhoto0(ImageHelper.uploadPic(accidentImage0, accidentImage0FileName, 
						accidentImage0ContentType, 1024*1024*10, "caraccidentimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[0])){
				updateAccident.setPhoto0(null);
			}
			imgUrls= imgUrls +"|"+( null==updateAccident.getPhoto0()?null:updateAccident.getPhoto0());
			if(accidentImage1 != null){
				updateAccident.setPhoto1(ImageHelper.uploadPic(accidentImage1, accidentImage1FileName, 
						accidentImage1ContentType, 1024*1024*10, "caraccidentimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[1])){
				updateAccident.setPhoto1(null);
			}
			imgUrls= imgUrls +"|"+ (null==updateAccident.getPhoto1()?null:updateAccident.getPhoto1());
			if(accidentImage2 != null){
				updateAccident.setPhoto2(ImageHelper.uploadPic(accidentImage2, accidentImage2FileName, 
						accidentImage2ContentType, 1024*1024*10, "caraccidentimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[2])){
				updateAccident.setPhoto2(null);
			}
			imgUrls= imgUrls +"|"+ (null==updateAccident.getPhoto2()?null:updateAccident.getPhoto2());
			if(accidentImage3 != null){
				updateAccident.setPhoto3(ImageHelper.uploadPic(accidentImage3, accidentImage3FileName, 
						accidentImage3ContentType, 1024*1024*10, "caraccidentimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[3])){
				updateAccident.setPhoto3(null);
			}
			imgUrls= imgUrls +"|"+ (null==updateAccident.getPhoto3()?null:updateAccident.getPhoto3());
			if(accidentImage4 != null){
				updateAccident.setPhoto4(ImageHelper.uploadPic(accidentImage4, accidentImage4FileName, 
						accidentImage4ContentType, 1024*1024*10, "caraccidentimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[4])){
				updateAccident.setPhoto4(null);
			}
			imgUrls= imgUrls +"|"+ (null==updateAccident.getPhoto4()?null:updateAccident.getPhoto4());
			if(accidentImage5 != null){
				updateAccident.setPhoto5(ImageHelper.uploadPic(accidentImage5, accidentImage5FileName, 
						accidentImage5ContentType, 1024*1024*10, "caraccidentimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[5])){
				updateAccident.setPhoto5(null);
			}
			imgUrls= imgUrls +"|"+ (null==updateAccident.getPhoto5()?null:updateAccident.getPhoto5());
			if(accidentImage6 != null){
				updateAccident.setPhoto6(ImageHelper.uploadPic(accidentImage6, accidentImage6FileName, 
						accidentImage6ContentType, 1024*1024*10, "caraccidentimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[6])){
				updateAccident.setPhoto6(null);
			}
			imgUrls= imgUrls +"|"+ (null==updateAccident.getPhoto6()?null:updateAccident.getPhoto6());
			if(accidentImage7 != null){
				updateAccident.setPhoto7(ImageHelper.uploadPic(accidentImage7, accidentImage7FileName, 
						accidentImage7ContentType, 1024*1024*10, "caraccidentimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[7])){
				updateAccident.setPhoto7(null);
			}
			imgUrls= imgUrls +"|"+ (null==updateAccident.getPhoto7()?null:updateAccident.getPhoto7());
			if(accidentImage8 != null){
				updateAccident.setPhoto8(ImageHelper.uploadPic(accidentImage8, accidentImage8FileName, 
						accidentImage8ContentType, 1024*1024*10, "caraccidentimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[8])){
				updateAccident.setPhoto8(null);
			}
			imgUrls= imgUrls +"|"+ (null==updateAccident.getPhoto8()?null:updateAccident.getPhoto8());
			if(accidentImage9 != null){
				updateAccident.setPhoto9(ImageHelper.uploadPic(accidentImage9, accidentImage9FileName, 
						accidentImage9ContentType, 1024*1024*10, "caraccidentimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[9])){
				updateAccident.setPhoto9(null);
			}
			imgUrls= imgUrls +"|"+ (null==updateAccident.getPhoto9()?null:updateAccident.getPhoto9());
			
			updateAccident.setImgUrls(imgUrls.substring(1));
			
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(StringHelper.isRealNull(oldCa.getStatusChangePerson1()) ){
				if(user != null){
					updateAccident.setStatusChangePerson1(user.getId());
				}
				updateAccident.setStatusChangeTime1(new Date());
			}
			else if(StringHelper.isRealNull(oldCa.getStatusChangePerson2()) ){
				updateAccident.setStatusChangePerson1(oldCa.getStatusChangePerson1());
				updateAccident.setStatusChangeTime1(oldCa.getStatusChangeTime1());
				if(user != null){
					updateAccident.setStatusChangePerson2(user.getId());
				}
				updateAccident.setStatusChangeTime2(new Date());
			}
			else if(StringHelper.isRealNull(oldCa.getStatusChangePerson3()) ){
				updateAccident.setStatusChangePerson1(oldCa.getStatusChangePerson1());
				updateAccident.setStatusChangeTime1(oldCa.getStatusChangeTime1());
				updateAccident.setStatusChangePerson2(oldCa.getStatusChangePerson2());
				updateAccident.setStatusChangeTime2(oldCa.getStatusChangeTime2());
				if(user != null){
					updateAccident.setStatusChangePerson3(user.getId());
				}
				updateAccident.setStatusChangeTime3(new Date());
			}
			else if(StringHelper.isRealNull(oldCa.getStatusChangePerson4()) ){
				updateAccident.setStatusChangePerson1(oldCa.getStatusChangePerson1());
				updateAccident.setStatusChangeTime1(oldCa.getStatusChangeTime1());
				updateAccident.setStatusChangePerson2(oldCa.getStatusChangePerson2());
				updateAccident.setStatusChangeTime2(oldCa.getStatusChangeTime2());
				updateAccident.setStatusChangePerson3(oldCa.getStatusChangePerson3());
				updateAccident.setStatusChangeTime3(oldCa.getStatusChangeTime3());
				if(user != null){
					updateAccident.setStatusChangePerson4(user.getId());
				}
				updateAccident.setStatusChangeTime4(new Date());
			}
			updateAccident.setCreateTime(oldCa.getCreateTime());
			updateAccident.setCreatorId(oldCa.getCreatorId());
			updateAccident.setUpdateTime(new Date());
			updateAccident.setTs(new Date());
			getCarAccidentService().updateCarAccident(updateAccident);
			if(updateAccident.getHandleStatus().equals(DictUtil.getDictByCodes("handleStatus" ,"05").getId())){
				Car car = carService.queryCarById(updateAccident.getCarId());
				Dict dict = DictUtil.getDictByCodes("carBizState", "3");
				car.setBizState(dict.getId());
				carService.updateCar(car);
			}
			CarAccident newCa = carAccidentService.searchCarAccidentById(updateAccident.getId());
			SystemOperateLogUtil.sysUpdateOperateLog(oldCa, newCa, user, "车辆事故管理", updateAccident.getCode());
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功");
			return Action.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return Action.ERROR;
		}
	}

	public String[] getDeletePhotoArr() {
		return deletePhotoArr;
	}
	public void setDeletePhotoArr(String[] deletePhotoArr) {
		this.deletePhotoArr = deletePhotoArr;
	}

	public CarAccidentService getCarAccidentService() {
		return carAccidentService;
	}
	public void setCarAccidentService(CarAccidentService carAccidentService) {
		this.carAccidentService = carAccidentService;
	}
	public File getAccidentImage0() {
		return accidentImage0;
	}
	public void setAccidentImage0(File accidentImage0) {
		this.accidentImage0 = accidentImage0;
	}


	public String getAccidentImage0ContentType() {
		return accidentImage0ContentType;
	}


	public void setAccidentImage0ContentType(String accidentImage0ContentType) {
		this.accidentImage0ContentType = accidentImage0ContentType;
	}


	public String getAccidentImage0FileName() {
		return accidentImage0FileName;
	}


	public void setAccidentImage0FileName(String accidentImage0FileName) {
		this.accidentImage0FileName = accidentImage0FileName;
	}


	public File getAccidentImage1() {
		return accidentImage1;
	}


	public void setAccidentImage1(File accidentImage1) {
		this.accidentImage1 = accidentImage1;
	}


	public String getAccidentImage1ContentType() {
		return accidentImage1ContentType;
	}


	public void setAccidentImage1ContentType(String accidentImage1ContentType) {
		this.accidentImage1ContentType = accidentImage1ContentType;
	}


	public String getAccidentImage1FileName() {
		return accidentImage1FileName;
	}


	public void setAccidentImage1FileName(String accidentImage1FileName) {
		this.accidentImage1FileName = accidentImage1FileName;
	}


	public File getAccidentImage2() {
		return accidentImage2;
	}


	public void setAccidentImage2(File accidentImage2) {
		this.accidentImage2 = accidentImage2;
	}


	public String getAccidentImage2ContentType() {
		return accidentImage2ContentType;
	}


	public void setAccidentImage2ContentType(String accidentImage2ContentType) {
		this.accidentImage2ContentType = accidentImage2ContentType;
	}


	public String getAccidentImage2FileName() {
		return accidentImage2FileName;
	}


	public void setAccidentImage2FileName(String accidentImage2FileName) {
		this.accidentImage2FileName = accidentImage2FileName;
	}


	public File getAccidentImage3() {
		return accidentImage3;
	}


	public void setAccidentImage3(File accidentImage3) {
		this.accidentImage3 = accidentImage3;
	}


	public String getAccidentImage3ContentType() {
		return accidentImage3ContentType;
	}


	public void setAccidentImage3ContentType(String accidentImage3ContentType) {
		this.accidentImage3ContentType = accidentImage3ContentType;
	}


	public String getAccidentImage3FileName() {
		return accidentImage3FileName;
	}


	public void setAccidentImage3FileName(String accidentImage3FileName) {
		this.accidentImage3FileName = accidentImage3FileName;
	}


	public File getAccidentImage4() {
		return accidentImage4;
	}


	public void setAccidentImage4(File accidentImage4) {
		this.accidentImage4 = accidentImage4;
	}


	public String getAccidentImage4ContentType() {
		return accidentImage4ContentType;
	}


	public void setAccidentImage4ContentType(String accidentImage4ContentType) {
		this.accidentImage4ContentType = accidentImage4ContentType;
	}


	public String getAccidentImage4FileName() {
		return accidentImage4FileName;
	}


	public void setAccidentImage4FileName(String accidentImage4FileName) {
		this.accidentImage4FileName = accidentImage4FileName;
	}


	public File getAccidentImage5() {
		return accidentImage5;
	}


	public void setAccidentImage5(File accidentImage5) {
		this.accidentImage5 = accidentImage5;
	}


	public String getAccidentImage5ContentType() {
		return accidentImage5ContentType;
	}


	public void setAccidentImage5ContentType(String accidentImage5ContentType) {
		this.accidentImage5ContentType = accidentImage5ContentType;
	}


	public String getAccidentImage5FileName() {
		return accidentImage5FileName;
	}


	public void setAccidentImage5FileName(String accidentImage5FileName) {
		this.accidentImage5FileName = accidentImage5FileName;
	}


	public File getAccidentImage6() {
		return accidentImage6;
	}


	public void setAccidentImage6(File accidentImage6) {
		this.accidentImage6 = accidentImage6;
	}


	public String getAccidentImage6ContentType() {
		return accidentImage6ContentType;
	}


	public void setAccidentImage6ContentType(String accidentImage6ContentType) {
		this.accidentImage6ContentType = accidentImage6ContentType;
	}


	public String getAccidentImage6FileName() {
		return accidentImage6FileName;
	}


	public void setAccidentImage6FileName(String accidentImage6FileName) {
		this.accidentImage6FileName = accidentImage6FileName;
	}


	public File getAccidentImage7() {
		return accidentImage7;
	}


	public void setAccidentImage7(File accidentImage7) {
		this.accidentImage7 = accidentImage7;
	}


	public String getAccidentImage7ContentType() {
		return accidentImage7ContentType;
	}


	public void setAccidentImage7ContentType(String accidentImage7ContentType) {
		this.accidentImage7ContentType = accidentImage7ContentType;
	}


	public String getAccidentImage7FileName() {
		return accidentImage7FileName;
	}


	public void setAccidentImage7FileName(String accidentImage7FileName) {
		this.accidentImage7FileName = accidentImage7FileName;
	}


	public File getAccidentImage8() {
		return accidentImage8;
	}


	public void setAccidentImage8(File accidentImage8) {
		this.accidentImage8 = accidentImage8;
	}


	public String getAccidentImage8ContentType() {
		return accidentImage8ContentType;
	}


	public void setAccidentImage8ContentType(String accidentImage8ContentType) {
		this.accidentImage8ContentType = accidentImage8ContentType;
	}


	public String getAccidentImage8FileName() {
		return accidentImage8FileName;
	}


	public void setAccidentImage8FileName(String accidentImage8FileName) {
		this.accidentImage8FileName = accidentImage8FileName;
	}


	public File getAccidentImage9() {
		return accidentImage9;
	}


	public void setAccidentImage9(File accidentImage9) {
		this.accidentImage9 = accidentImage9;
	}


	public String getAccidentImage9ContentType() {
		return accidentImage9ContentType;
	}


	public void setAccidentImage9ContentType(String accidentImage9ContentType) {
		this.accidentImage9ContentType = accidentImage9ContentType;
	}


	public String getAccidentImage9FileName() {
		return accidentImage9FileName;
	}


	public void setAccidentImage9FileName(String accidentImage9FileName) {
		this.accidentImage9FileName = accidentImage9FileName;
	}


	public String getFormData() {
		return formData;
	}


	public void setFormData(String formData) {
		this.formData = formData;
	}
	
	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}

}
