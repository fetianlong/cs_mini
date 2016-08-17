package com.dearho.cs.carservice.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author GaoYunpeng
 * @Description 车辆巡检
 * @version 1.0 2015年6月30日 下午5:25:57
 */
public class CarPatrol implements Serializable{
	
	private static final long serialVersionUID = 2896644858481494860L;

	private String id;
	private String code;
	
	private String parkingPatrolId;
	private String carId;
	private Integer paint;
	private Integer glass;
	private Integer tirePressure;
	private Double SOC;
	private String frontWheel;
	private String rearWheel;
	private String safetyBelt;
	private String sunVisorL;
	private String sunVisorR;
	private String windshield;
	private String horn;
	private String skylightSwitch;
	private String wiper;
	private String lampSwitch;
	private String chairFace;
	private String chairAdjust;
	private String reflector;
	private String wiperSwitch;
	private String interior;
	private String doorHandle;
	private String handbrake;
	private String doorMat;
	private String windowGlass;
	private String brake;
	private String taxStandard;
	private String skylight;
	private String airConditioning;
	private String vehicleGPS;
	private String light;
	private String sound;
	private String childrenSeat;
	private String jack;
	private String cigaretteLighter;
	private String carKey;
	private String toolKit;
	private String ashtray;
	private String drivingLicense;
	private String failureWarningPlate;
	private String storageBox;
	private String autoLogo;
	private String spareTire;
	private String windowLift;
	private String book;
	private String fireExtinguisher;
	private String indoorLight;
	private String dashboard;
	private String steeringLamp;
	private String steeringWheel;
	private String inRearLamp;
	private String frontPhoto;
	private String backPhoto;
	private String leftPhoto;
	private String rightPhoto;
	private String sparePhoto1;
	private String sparePhoto2;
	private Integer isOk;
	
	private String remark;
	private Integer isDiscard;
	private String creatorId;
	private Date createTime;
	private Date updateTime;
	private Date ts;
	
	private String plateNumber;
	private String dotId;
	private String parkingPatrolCode;
	private String dotName;
	
	public String getDotName() {
		return dotName;
	}
	public void setDotName(String dotName) {
		this.dotName = dotName;
	}
	public String getDotId() {
		return dotId;
	}
	public void setDotId(String dotId) {
		this.dotId = dotId;
	}
	public String getParkingPatrolCode() {
		return parkingPatrolCode;
	}
	public void setParkingPatrolCode(String parkingPatrolCode) {
		this.parkingPatrolCode = parkingPatrolCode;
	}
	
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getParkingPatrolId() {
		return parkingPatrolId;
	}
	public void setParkingPatrolId(String parkingPatrolId) {
		this.parkingPatrolId = parkingPatrolId;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public Integer getPaint() {
		return paint;
	}
	public void setPaint(Integer paint) {
		this.paint = paint;
	}
	public Integer getGlass() {
		return glass;
	}
	public void setGlass(Integer glass) {
		this.glass = glass;
	}
	
	public Integer getTirePressure() {
		return tirePressure;
	}
	public void setTirePressure(Integer tirePressure) {
		this.tirePressure = tirePressure;
	}
	public Double getSOC() {
		return SOC;
	}
	public void setSOC(Double sOC) {
		SOC = sOC;
	}
	public String getFrontWheel() {
		return frontWheel;
	}
	public void setFrontWheel(String frontWheel) {
		this.frontWheel = frontWheel;
	}
	public String getRearWheel() {
		return rearWheel;
	}
	public void setRearWheel(String rearWheel) {
		this.rearWheel = rearWheel;
	}
	public String getSafetyBelt() {
		return safetyBelt;
	}
	public void setSafetyBelt(String safetyBelt) {
		this.safetyBelt = safetyBelt;
	}
	public String getSunVisorL() {
		return sunVisorL;
	}
	public void setSunVisorL(String sunVisorL) {
		this.sunVisorL = sunVisorL;
	}
	public String getSunVisorR() {
		return sunVisorR;
	}
	public void setSunVisorR(String sunVisorR) {
		this.sunVisorR = sunVisorR;
	}
	public String getWindshield() {
		return windshield;
	}
	public void setWindshield(String windshield) {
		this.windshield = windshield;
	}
	public String getHorn() {
		return horn;
	}
	public void setHorn(String horn) {
		this.horn = horn;
	}
	public String getSkylightSwitch() {
		return skylightSwitch;
	}
	public void setSkylightSwitch(String skylightSwitch) {
		this.skylightSwitch = skylightSwitch;
	}
	public String getWiper() {
		return wiper;
	}
	public void setWiper(String wiper) {
		this.wiper = wiper;
	}
	public String getLampSwitch() {
		return lampSwitch;
	}
	public void setLampSwitch(String lampSwitch) {
		this.lampSwitch = lampSwitch;
	}
	public String getChairFace() {
		return chairFace;
	}
	public void setChairFace(String chairFace) {
		this.chairFace = chairFace;
	}
	public String getChairAdjust() {
		return chairAdjust;
	}
	public void setChairAdjust(String chairAdjust) {
		this.chairAdjust = chairAdjust;
	}
	public String getReflector() {
		return reflector;
	}
	public void setReflector(String reflector) {
		this.reflector = reflector;
	}
	public String getWiperSwitch() {
		return wiperSwitch;
	}
	public void setWiperSwitch(String wiperSwitch) {
		this.wiperSwitch = wiperSwitch;
	}
	public String getInterior() {
		return interior;
	}
	public void setInterior(String interior) {
		this.interior = interior;
	}
	public String getDoorHandle() {
		return doorHandle;
	}
	public void setDoorHandle(String doorHandle) {
		this.doorHandle = doorHandle;
	}
	public String getHandbrake() {
		return handbrake;
	}
	public void setHandbrake(String handbrake) {
		this.handbrake = handbrake;
	}
	public String getDoorMat() {
		return doorMat;
	}
	public void setDoorMat(String doorMat) {
		this.doorMat = doorMat;
	}
	public String getWindowGlass() {
		return windowGlass;
	}
	public void setWindowGlass(String windowGlass) {
		this.windowGlass = windowGlass;
	}
	public String getBrake() {
		return brake;
	}
	public void setBrake(String brake) {
		this.brake = brake;
	}
	public String getTaxStandard() {
		return taxStandard;
	}
	public void setTaxStandard(String taxStandard) {
		this.taxStandard = taxStandard;
	}
	public String getSkylight() {
		return skylight;
	}
	public void setSkylight(String skylight) {
		this.skylight = skylight;
	}
	public String getAirConditioning() {
		return airConditioning;
	}
	public void setAirConditioning(String airConditioning) {
		this.airConditioning = airConditioning;
	}
	public String getVehicleGPS() {
		return vehicleGPS;
	}
	public void setVehicleGPS(String vehicleGPS) {
		this.vehicleGPS = vehicleGPS;
	}
	public String getLight() {
		return light;
	}
	public void setLight(String light) {
		this.light = light;
	}
	public String getSound() {
		return sound;
	}
	public void setSound(String sound) {
		this.sound = sound;
	}
	public String getChildrenSeat() {
		return childrenSeat;
	}
	public void setChildrenSeat(String childrenSeat) {
		this.childrenSeat = childrenSeat;
	}
	public String getJack() {
		return jack;
	}
	public void setJack(String jack) {
		this.jack = jack;
	}
	public String getCigaretteLighter() {
		return cigaretteLighter;
	}
	public void setCigaretteLighter(String cigaretteLighter) {
		this.cigaretteLighter = cigaretteLighter;
	}
	public String getCarKey() {
		return carKey;
	}
	public void setCarKey(String carKey) {
		this.carKey = carKey;
	}
	public String getToolKit() {
		return toolKit;
	}
	public void setToolKit(String toolKit) {
		this.toolKit = toolKit;
	}
	public String getAshtray() {
		return ashtray;
	}
	public void setAshtray(String ashtray) {
		this.ashtray = ashtray;
	}
	public String getDrivingLicense() {
		return drivingLicense;
	}
	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}
	public String getFailureWarningPlate() {
		return failureWarningPlate;
	}
	public void setFailureWarningPlate(String failureWarningPlate) {
		this.failureWarningPlate = failureWarningPlate;
	}
	public String getStorageBox() {
		return storageBox;
	}
	public void setStorageBox(String storageBox) {
		this.storageBox = storageBox;
	}
	public String getAutoLogo() {
		return autoLogo;
	}
	public void setAutoLogo(String autoLogo) {
		this.autoLogo = autoLogo;
	}
	public String getSpareTire() {
		return spareTire;
	}
	public void setSpareTire(String spareTire) {
		this.spareTire = spareTire;
	}
	public String getWindowLift() {
		return windowLift;
	}
	public void setWindowLift(String windowLift) {
		this.windowLift = windowLift;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public String getFireExtinguisher() {
		return fireExtinguisher;
	}
	public void setFireExtinguisher(String fireExtinguisher) {
		this.fireExtinguisher = fireExtinguisher;
	}
	public String getIndoorLight() {
		return indoorLight;
	}
	public void setIndoorLight(String indoorLight) {
		this.indoorLight = indoorLight;
	}
	public String getDashboard() {
		return dashboard;
	}
	public void setDashboard(String dashboard) {
		this.dashboard = dashboard;
	}
	public String getSteeringLamp() {
		return steeringLamp;
	}
	public void setSteeringLamp(String steeringLamp) {
		this.steeringLamp = steeringLamp;
	}
	public String getSteeringWheel() {
		return steeringWheel;
	}
	public void setSteeringWheel(String steeringWheel) {
		this.steeringWheel = steeringWheel;
	}
	public String getInRearLamp() {
		return inRearLamp;
	}
	public void setInRearLamp(String inRearLamp) {
		this.inRearLamp = inRearLamp;
	}
	public String getFrontPhoto() {
		return frontPhoto;
	}
	public void setFrontPhoto(String frontPhoto) {
		this.frontPhoto = frontPhoto;
	}
	public String getBackPhoto() {
		return backPhoto;
	}
	public void setBackPhoto(String backPhoto) {
		this.backPhoto = backPhoto;
	}
	public String getLeftPhoto() {
		return leftPhoto;
	}
	public void setLeftPhoto(String leftPhoto) {
		this.leftPhoto = leftPhoto;
	}
	public String getRightPhoto() {
		return rightPhoto;
	}
	public void setRightPhoto(String rightPhoto) {
		this.rightPhoto = rightPhoto;
	}
	public String getSparePhoto1() {
		return sparePhoto1;
	}
	public void setSparePhoto1(String sparePhoto1) {
		this.sparePhoto1 = sparePhoto1;
	}
	public String getSparePhoto2() {
		return sparePhoto2;
	}
	public void setSparePhoto2(String sparePhoto2) {
		this.sparePhoto2 = sparePhoto2;
	}
	public Integer getIsOk() {
		return isOk;
	}
	public void setIsOk(Integer isOk) {
		this.isOk = isOk;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getIsDiscard() {
		return isDiscard;
	}
	public void setIsDiscard(Integer isDiscard) {
		this.isDiscard = isDiscard;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	
	
}
