package com.dearho.cs.carservice.action.patrol.car;

import java.io.File;
import java.util.Date;
import java.util.List;








import com.dearho.cs.carservice.pojo.CarPatrol;
import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.ImageHelper;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:30:49
 */
public class CarPatrolUpdateAction extends CarPatrolAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	private String[] deletePhotoArr;
	
	public String process(){
		try {
			List<CarPatrol> hasAccidents = getCarPatrolService().searchCarPatrol(getCarPatrol());
			if(hasAccidents != null && hasAccidents.size() > 0 && !hasAccidents.get(0).getId().equals(getCarPatrol().getId())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！编码重复！");
				return Action.ERROR;
			}
			CarPatrol updateCarPatrol = getCarPatrol();
			
			CarPatrol oldCm = getCarPatrolService().searchCarPatrolById(getCarPatrol().getId());
			
//			if(!StringHelper.isRealNull(oldCm.getStatus()) && !StringHelper.isRealNull(updateCarPatrol.getStatus())){
//				if(oldCm.getStatus().compareTo(updateCarPatrol.getStatus()) > 0){
//					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！状态不能后退！");
//					return Action.ERROR;
//				}
//			}
			ParkingPatrol pp = getParkingPatrolService().searchParkingPatrolById(updateCarPatrol.getParkingPatrolId());
			if(pp != null){
				updateCarPatrol.setDotId(pp.getDotId());
			}
			if(frontPhoto != null){
				updateCarPatrol.setFrontPhoto(ImageHelper.uploadPic(frontPhoto, frontPhotoFileName, 
						frontPhotoContentType, 1024*1024*10, "carpatrolimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[0])){
				updateCarPatrol.setFrontPhoto(null);
			}
			else{
				updateCarPatrol.setFrontPhoto(oldCm.getFrontPhoto());
			}
			
			if(backPhoto != null){
				updateCarPatrol.setBackPhoto(ImageHelper.uploadPic(backPhoto, backPhotoFileName, 
						backPhotoContentType, 1024*1024*10, "carpatrolimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[1])){
				updateCarPatrol.setBackPhoto(null);
			}
			else{
				updateCarPatrol.setBackPhoto(oldCm.getBackPhoto());
			}
			
			if(leftPhoto != null){
				updateCarPatrol.setLeftPhoto(ImageHelper.uploadPic(leftPhoto, leftPhotoFileName, 
						leftPhotoContentType, 1024*1024*10, "carpatrolimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[2])){
				updateCarPatrol.setLeftPhoto(null);
			}
			else{
				updateCarPatrol.setLeftPhoto(oldCm.getLeftPhoto());
			}
			
			if(rightPhoto != null){
				updateCarPatrol.setRightPhoto(ImageHelper.uploadPic(rightPhoto, rightPhotoFileName, 
						rightPhotoContentType, 1024*1024*10, "carpatrolimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[3])){
				updateCarPatrol.setRightPhoto(null);
			}
			else{
				updateCarPatrol.setRightPhoto(oldCm.getRightPhoto());
			}
			
			if(sparePhoto1 != null){
				updateCarPatrol.setSparePhoto1(ImageHelper.uploadPic(sparePhoto1, sparePhoto1FileName, 
						sparePhoto1ContentType, 1024*1024*10, "carpatrolimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[4])){
				updateCarPatrol.setSparePhoto1(null);
			}
			else{
				updateCarPatrol.setSparePhoto1(oldCm.getSparePhoto1());
			}
			
			if(sparePhoto2 != null){
				updateCarPatrol.setSparePhoto2(ImageHelper.uploadPic(sparePhoto2, sparePhoto2FileName, 
						sparePhoto2ContentType, 1024*1024*10, "carpatrolimgs"));
			}
			else if(deletePhotoArr != null && deletePhotoArr.length >= 1 && "0".equals(deletePhotoArr[0].split(",")[5])){
				updateCarPatrol.setSparePhoto2(null);
			}
			else{
				updateCarPatrol.setSparePhoto2(oldCm.getSparePhoto2());
			}
			
			updateCarPatrol.setCreateTime(oldCm.getCreateTime());
			updateCarPatrol.setCreatorId(oldCm.getCreatorId());
			
			updateCarPatrol.setUpdateTime(new Date());
			updateCarPatrol.setTs(new Date());
			getCarPatrolService().updateCarPatrol(updateCarPatrol);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return Action.ERROR;
		}
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
	public String[] getDeletePhotoArr() {
		return deletePhotoArr;
	}
	public void setDeletePhotoArr(String[] deletePhotoArr) {
		this.deletePhotoArr = deletePhotoArr;
	}
	
	
}
