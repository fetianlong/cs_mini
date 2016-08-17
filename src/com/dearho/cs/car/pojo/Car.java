package com.dearho.cs.car.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.FieldComment;
import com.dearho.cs.sys.util.DictUtil;
/**
 * 车辆实体
 * @author lvlq
 * @Version 1.0, 2015-4-30
 */
public class Car implements Serializable {

	private static final long serialVersionUID = 1L;
	/**主键*/
	private String id;
	/**车牌号*/
	@FieldComment("车牌号") 
	private String vehiclePlateId;
	/**车架号*/
	@FieldComment("车架号") 
	private String vin;
	/**昵称*/
	@FieldComment("昵称") 
	private String nickName;
	/**运营城市*/
	private String operationCity;
	/**颜色*/
	@FieldComment("车辆颜色") 
	private String color;
	/**车型Id*/
	private String modelId;
	@FieldComment("车型名称") 
	private String modelName;
	/**是否是广告车*/
	private Integer isAd;
	@FieldComment("是否是广告车") 
	private String isAdDesc;
	/**创建人Id*/
	private String creatorId;
	/**创建时间*/
	private Date createDate;
	/**时间戳*/
	private Date ts;
	/**
	 * 车辆业务状态
	 */
	private String bizState;
	
	/**
	 * 业务状态code
	 */
	private String bizStateCode;
	
	private CarVehicleModel carVehicleModel;
	
	@FieldComment("发动机号") 
	private String engineNumber;
	@FieldComment("行驶证号") 
	private String drivingLicenseNumber;
	@FieldComment("购置时间") 
	private Date buyTime;
	@FieldComment("购置地址") 
	private String buyPlace;
	
	
	public CarVehicleModel getCarVehicleModel() {
		return carVehicleModel;
	}

	public void setCarVehicleModel(CarVehicleModel carVehicleModel) {
		this.carVehicleModel = carVehicleModel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVehiclePlateId() {
		return vehiclePlateId;
	}

	public void setVehiclePlateId(String vehiclePlateId) {
		this.vehiclePlateId = vehiclePlateId;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getOperationCity() {
		return operationCity;
	}

	public void setOperationCity(String operationCity) {
		this.operationCity = operationCity;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public Integer getIsAd() {
		return isAd;
	}

	public void setIsAd(Integer isAd) {
		this.isAd = isAd;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", vehiclePlateId=" + vehiclePlateId
				+ ", vin=" + vin + ", nickName=" + nickName
				+ ", operationCity=" + operationCity + ", color=" + color
				+ ", modelId=" + modelId + ", isAd=" + isAd + ", creatorId="
				+ creatorId + ", createDate=" + createDate + ", ts=" + ts + "]";
	}

	public Car(String id, String vehiclePlateId, String vin, String nickName,
			String operationCity, String color, String modelId, int isAd,
			String creatorId, Date createDate, Date ts) {
		super();
		this.id = id;
		this.vehiclePlateId = vehiclePlateId;
		this.vin = vin;
		this.nickName = nickName;
		this.operationCity = operationCity;
		this.color = color;
		this.modelId = modelId;
		this.isAd = isAd;
		this.creatorId = creatorId;
		this.createDate = createDate;
		this.ts = ts;
	}

	public Car() {
		super();
	}
	public String getBizState() {
		return bizState;
	}
	public void setBizState(String bizState) {
		this.bizState = bizState;
	}
	public String getBizStateCode() {
		return bizStateCode;
	}

	public void setBizStateCode(String bizStateCode) {
		this.bizStateCode = bizStateCode;
	}
	public String getModelName() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		CarVehicleModelService carVehicleModelService = (CarVehicleModelService) webApplicationContext.getBean("carVehicleModelService");
		CarVehicleModel model = carVehicleModelService.queryModelById(modelId);
		Dict brandDict = DictUtil.getDictById(model.getBrand());
		return brandDict.getCnName()+"-"+model.getName();
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getIsAdDesc() {
		if(isAd==0){
			return "否";
		}else{
			return "是";
		}
	}

	public void setIsAdDesc(String isAdDesc) {
		this.isAdDesc = isAdDesc;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getDrivingLicenseNumber() {
		return drivingLicenseNumber;
	}

	public void setDrivingLicenseNumber(String drivingLicenseNumber) {
		this.drivingLicenseNumber = drivingLicenseNumber;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public String getBuyPlace() {
		return buyPlace;
	}

	public void setBuyPlace(String buyPlace) {
		this.buyPlace = buyPlace;
	}
	
}
