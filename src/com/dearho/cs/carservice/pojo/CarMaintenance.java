package com.dearho.cs.carservice.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.FieldComment;
import com.dearho.cs.sys.util.DictUtil;

/**
 * @author GaoYunpeng
 * @Description 车辆保养
 * @version 1.0 2015年6月23日 下午4:31:20
 */
public class CarMaintenance implements Serializable{

	private static final long serialVersionUID = -1169397014282079577L;

	private String id;
	private String code;
	private String city;
	private String carId;
	
	private String vehicleModel;
	private String type;
	@FieldComment("保期") 
	private String typeName;
	@FieldComment("保养日期") 
	private Date maintenanceTime;
	@FieldComment("预计保养里程") 
	private Double expectKm;
	@FieldComment("实际保养里程") 
	private Double actualKm;
	@FieldComment("当前里程") 
	private Double currentKm;
	private String feeType;
	@FieldComment("费别") 
	private String feeTypeName;
	@FieldComment("保养费用") 
	private Double fee;
	private String status;
	@FieldComment("状态") 
	private String statusName;
	
	private String statusChangePerson1;
	private Date statusChangeTime1;
	
	private String statusChangePerson2;
	private Date statusChangeTime2;
	
	private String statusChangePerson3;
	private Date statusChangeTime3;
	
	private String creatorId;
	@FieldComment("备注")
	private String remark;
	private Integer isDiscard;
	private Date createTime;
	private Date updateTime;
	private Date ts;
	
	@FieldComment("城市") 
	private String cityName;
	@FieldComment("车牌号")
	private String plateNumber;
	
	private String maintenancePlace;
	private String operator;
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getVehicleModel() {
		return vehicleModel;
	}
	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getMaintenanceTime() {
		return maintenanceTime;
	}
	public void setMaintenanceTime(Date maintenanceTime) {
		this.maintenanceTime = maintenanceTime;
	}
	public Double getExpectKm() {
		return expectKm;
	}
	public void setExpectKm(Double expectKm) {
		this.expectKm = expectKm;
	}
	public Double getActualKm() {
		return actualKm;
	}
	public void setActualKm(Double actualKm) {
		this.actualKm = actualKm;
	}
	public Double getCurrentKm() {
		return currentKm;
	}
	public void setCurrentKm(Double currentKm) {
		this.currentKm = currentKm;
	}
	public String getFeeType() {
		return feeType;
	}
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusChangePerson1() {
		return statusChangePerson1;
	}
	public void setStatusChangePerson1(String statusChangePerson1) {
		this.statusChangePerson1 = statusChangePerson1;
	}
	public Date getStatusChangeTime1() {
		return statusChangeTime1;
	}
	public void setStatusChangeTime1(Date statusChangeTime1) {
		this.statusChangeTime1 = statusChangeTime1;
	}
	public String getStatusChangePerson2() {
		return statusChangePerson2;
	}
	public void setStatusChangePerson2(String statusChangePerson2) {
		this.statusChangePerson2 = statusChangePerson2;
	}
	public Date getStatusChangeTime2() {
		return statusChangeTime2;
	}
	public void setStatusChangeTime2(Date statusChangeTime2) {
		this.statusChangeTime2 = statusChangeTime2;
	}
	public String getStatusChangePerson3() {
		return statusChangePerson3;
	}
	public void setStatusChangePerson3(String statusChangePerson3) {
		this.statusChangePerson3 = statusChangePerson3;
	}
	public Date getStatusChangeTime3() {
		return statusChangeTime3;
	}
	public void setStatusChangeTime3(Date statusChangeTime3) {
		this.statusChangeTime3 = statusChangeTime3;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
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
	public String getMaintenancePlace() {
		return maintenancePlace;
	}
	public void setMaintenancePlace(String maintenancePlace) {
		this.maintenancePlace = maintenancePlace;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getTypeName() {
		if(!StringUtils.isEmpty(type)){
			Dict dict = DictUtil.getDictById(type);
			return dict.getCnName();
		}else{
			return "";
		}
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getStatusName() {
		if(!StringUtils.isEmpty(status)){
			Dict dict = DictUtil.getDictById(status);
			return dict.getCnName();
		}else{
			return "";
		}
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getFeeTypeName() {
		if(!StringUtils.isEmpty(feeType)){
			Dict dict = DictUtil.getDictById(feeType);
			return dict.getCnName();
		}else{
			return "";
		}
	}
	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}
	
}
