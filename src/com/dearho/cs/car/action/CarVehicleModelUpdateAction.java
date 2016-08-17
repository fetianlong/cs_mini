package com.dearho.cs.car.action;

import java.io.File;
import java.util.Date;
import java.util.List;

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
import com.opensymphony.xwork.Action;

public class CarVehicleModelUpdateAction extends AbstractAction {

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
	
	private String[] deletePhotoArr;
	
	
	@Override
	public String process() {
		try{
			List<CarVehicleModel> hasAccidents = getCarVehicleModelService().queryModelByName(getCarVehicleModel());
			if(hasAccidents != null && hasAccidents.size() > 0 && !hasAccidents.get(0).getId().equals(getCarVehicleModel().getId())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！编码重复！");
				return Action.ERROR;
			}
			CarVehicleModel updateCarVehicleModel = getCarVehicleModel();
			
			CarVehicleModel oldCm = getCarVehicleModelService().queryModelById(getCarVehicleModel().getId());
			
//			if(!StringHelper.isRealNull(oldCm.getStatus()) && !StringHelper.isRealNull(updateCarVehicleModel.getStatus())){
//				if(oldCm.getStatus().compareTo(updateCarVehicleModel.getStatus()) > 0){
//					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！状态不能后退！");
//					return Action.ERROR;
//				}
//			}
			if(webModelPhoto != null){
				updateCarVehicleModel.setWebModelPhoto(ImageHelper.uploadPic(webModelPhoto, webModelPhotoFileName, 
						webModelPhotoContentType, 1024*1024*10, "vehiclemodelimgs/web"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[0])){
				updateCarVehicleModel.setWebModelPhoto(null);
			}
			else{
				updateCarVehicleModel.setWebModelPhoto(oldCm.getWebModelPhoto());
			}
			
			if(androidModelPhoto != null){
				updateCarVehicleModel.setAndroidModelPhoto(ImageHelper.uploadPic(androidModelPhoto, androidModelPhotoFileName, 
						androidModelPhotoContentType, 1024*1024*10, "vehiclemodelimgs/android"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 
					&& deletePhotoArr[0].split(",").length > 1 && "0".equals(deletePhotoArr[0].split(",")[1])){
				updateCarVehicleModel.setAndroidModelPhoto(null);
			}
			else{
				updateCarVehicleModel.setAndroidModelPhoto(oldCm.getAndroidModelPhoto());
			}
			
			if(IOSModelPhoto != null){
				updateCarVehicleModel.setIOSModelPhoto(ImageHelper.uploadPic(IOSModelPhoto, IOSModelPhotoFileName, 
						IOSModelPhotoContentType, 1024*1024*10, "vehiclemodelimgs/ios"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 
					&& deletePhotoArr[0].split(",").length > 2 && "0".equals(deletePhotoArr[0].split(",")[2])){
				updateCarVehicleModel.setIOSModelPhoto(null);
			}
			else{
				updateCarVehicleModel.setIOSModelPhoto(oldCm.getIOSModelPhoto());
			}
			
			if(microWebModelPhoto != null){
				updateCarVehicleModel.setMicroWebModelPhoto(ImageHelper.uploadPic(microWebModelPhoto, microWebModelPhotoFileName, 
						microWebModelPhotoContentType, 1024*1024*10, "vehiclemodelimgs/microweb"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 
					&& deletePhotoArr[0].split(",").length > 3 && "0".equals(deletePhotoArr[0].split(",")[3])){
				updateCarVehicleModel.setMicroWebModelPhoto(null);
			}
			else{
				updateCarVehicleModel.setMicroWebModelPhoto(oldCm.getMicroWebModelPhoto());
			}
			
			updateCarVehicleModel.setCreateDate(oldCm.getCreateDate());
			updateCarVehicleModel.setCreatorId(oldCm.getCreatorId());
			
			updateCarVehicleModel.setTs(new Date());
			carVehicleModelService.updateVeicleModel(updateCarVehicleModel);
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "更新成功!");
			
			HttpSession session = ServletActionContext.getRequest().getSession();
			User s=(User) session.getAttribute("user");
			SystemOperateLogUtil.sysUpdateOperateLog(oldCm, updateCarVehicleModel, s, "车型管理", updateCarVehicleModel.getName());
		}catch(Exception e){
			e.printStackTrace();
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败!");
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	
	/**
//	public static void main(String[] args) {
//		String str = "carVehicleModel.id=402881e84d8dde8e014d8e2eca0c0001&
 * carVehicleModel.name=wwer&
 * carVehicleModel.brand=G341&
 * carVehicleModel.level=402881e94d70649f014d706af8f50003&
 * carVehicleModel.engineType=402881f04d745186014d74544f700001&
 * carVehicleModel.seatingNum=&
 * carVehicleModel.luggageNum=&
 * carVehicleModel.casesNum=&
 * carVehicleModel.gearboxType=&
 * carVehicleModel.standardMileage=&
 * carVehicleModel.entertainmentSystem=1";
//		String value = CarVehicleModelUpdateAction.findValue("carVehicleModel.id", str);
//		System.out.println(value);
//	}
	*/

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

	
	public CarVehicleModelUpdateAction() {
		super();
		carVehicleModel=new CarVehicleModel();
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

	public String[] getDeletePhotoArr() {
		return deletePhotoArr;
	}
	public void setDeletePhotoArr(String[] deletePhotoArr) {
		this.deletePhotoArr = deletePhotoArr;
	}

}
