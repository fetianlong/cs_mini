package com.dearho.cs.carservice.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.FieldComment;
import com.dearho.cs.sys.util.DictUtil;

/**
 * @author GaoYunpeng
 * @Description 车辆保险
 * @version 1.0 2015年6月30日 上午10:06:53
 */
public class CarInsurance implements Serializable{

	private static final long serialVersionUID = -5899216885146972952L;

	private String id;
	@FieldComment("保单编号") 
	private String code;
	private String city;
	private String carId;
	private String vehicleModel;
	private String carColor;
	private String carVin;
	@FieldComment("参保车辆") 
	private String plateNumber;
	@FieldComment("投保人") 
	private String policyHolder;
	private String phTel;
	private String insuredPersonDeptType;
	@FieldComment("投保人联系方式")
	private String phPhone;
	private String ipName;
	private String ipTel;
	private String ipPhone;
	private String ipAddress;
	@FieldComment("行驶证注册日期")
	private Date firstRegTime;
	private Integer usedYear;
	private String carUseType;
	@FieldComment("车辆使用性质")
	private String carUseTypeName;
	private String carLose;
	private Double newCarPrice;
	private String thirdPerson;
	private String peopleIncar;
	private Integer personCount;
	private String goodsIncar;
	private String stolenRescue;
	private String glassBroken;
	private String glassType;
	@FieldComment("玻璃类型")
	private String glassTypeName;
	private String stopLose;
	private Double slMdFee;
	private Integer slMdDay;
	private String spontLoss;
	private String scratch;
	private String fbs;
	private String noFaultResp;
	private String addEquLoss;
	private String exclDeducSpecial;
	private String freeOddsSpecial;
	private String fosCar;
	private String fosThird;
	private String rescueSpecial;
	@FieldComment("备注")
	private String remark;
	private Integer isDiscard;
	private String creatorId;
	private Date createTime;
	private Date updateTime;
	private Date ts;
	@FieldComment("交强险开始日期")
	private Date trfInsStartDate;   //交强险开始日期
	@FieldComment("交强险结束日期")
	private Date trfInsEndDate;     //交强险结束日期
	@FieldComment("商业保险开始日期")
	private Date commInsStartDate;   //商业保险开始日期
	@FieldComment("商业保险结束日期")
	private Date commInsEndDate;     //商业保险结束日期
	
	@FieldComment("车辆投保日期")
	private Date insuranceTime;   //上保险日期
	private String engineWater;   //发动机涉水险
	@FieldComment("保险公司出单员")
	private String billCreator;   //保险公司出单员
	@FieldComment("出单员联系方式")
	private String billCreatorPhone; //出单员联系方式
	
	private String carEngineNumber;//车辆发动机号
	
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
	public String getCarColor() {
		return carColor;
	}
	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
	public String getCarVin() {
		return carVin;
	}
	public void setCarVin(String carVin) {
		this.carVin = carVin;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getPolicyHolder() {
		return policyHolder;
	}
	public void setPolicyHolder(String policyHolder) {
		this.policyHolder = policyHolder;
	}
	public String getPhTel() {
		return phTel;
	}
	public void setPhTel(String phTel) {
		this.phTel = phTel;
	}
	public String getInsuredPersonDeptType() {
		return insuredPersonDeptType;
	}
	public void setInsuredPersonDeptType(String insuredPersonDeptType) {
		this.insuredPersonDeptType = insuredPersonDeptType;
	}
	public String getPhPhone() {
		return phPhone;
	}
	public void setPhPhone(String phPhone) {
		this.phPhone = phPhone;
	}
	public String getIpName() {
		return ipName;
	}
	public void setIpName(String ipName) {
		this.ipName = ipName;
	}
	public String getIpTel() {
		return ipTel;
	}
	public void setIpTel(String ipTel) {
		this.ipTel = ipTel;
	}
	public String getIpPhone() {
		return ipPhone;
	}
	public void setIpPhone(String ipPhone) {
		this.ipPhone = ipPhone;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Date getFirstRegTime() {
		return firstRegTime;
	}
	public void setFirstRegTime(Date firstRegTime) {
		this.firstRegTime = firstRegTime;
	}
	public Integer getUsedYear() {
		return usedYear;
	}
	public void setUsedYear(Integer usedYear) {
		this.usedYear = usedYear;
	}
	public String getCarUseType() {
		return carUseType;
	}
	public void setCarUseType(String carUseType) {
		this.carUseType = carUseType;
	}
	public String getCarLose() {
		return carLose;
	}
	public void setCarLose(String carLose) {
		this.carLose = carLose;
	}
	public Double getNewCarPrice() {
		return newCarPrice;
	}
	public void setNewCarPrice(Double newCarPrice) {
		this.newCarPrice = newCarPrice;
	}
	public String getThirdPerson() {
		return thirdPerson;
	}
	public void setThirdPerson(String thirdPerson) {
		this.thirdPerson = thirdPerson;
	}
	public String getPeopleIncar() {
		return peopleIncar;
	}
	public void setPeopleIncar(String peopleIncar) {
		this.peopleIncar = peopleIncar;
	}
	public Integer getPersonCount() {
		return personCount;
	}
	public void setPersonCount(Integer personCount) {
		this.personCount = personCount;
	}
	public String getGoodsIncar() {
		return goodsIncar;
	}
	public void setGoodsIncar(String goodsIncar) {
		this.goodsIncar = goodsIncar;
	}
	public String getStolenRescue() {
		return stolenRescue;
	}
	public void setStolenRescue(String stolenRescue) {
		this.stolenRescue = stolenRescue;
	}
	public String getGlassBroken() {
		return glassBroken;
	}
	public void setGlassBroken(String glassBroken) {
		this.glassBroken = glassBroken;
	}
	public String getGlassType() {
		return glassType;
	}
	public void setGlassType(String glassType) {
		this.glassType = glassType;
	}
	public String getStopLose() {
		return stopLose;
	}
	public void setStopLose(String stopLose) {
		this.stopLose = stopLose;
	}
	public Double getSlMdFee() {
		return slMdFee;
	}
	public void setSlMdFee(Double slMdFee) {
		this.slMdFee = slMdFee;
	}
	public Integer getSlMdDay() {
		return slMdDay;
	}
	public void setSlMdDay(Integer slMdDay) {
		this.slMdDay = slMdDay;
	}
	public String getSpontLoss() {
		return spontLoss;
	}
	public void setSpontLoss(String spontLoss) {
		this.spontLoss = spontLoss;
	}
	public String getScratch() {
		return scratch;
	}
	public void setScratch(String scratch) {
		this.scratch = scratch;
	}
	public String getFbs() {
		return fbs;
	}
	public void setFbs(String fbs) {
		this.fbs = fbs;
	}
	public String getNoFaultResp() {
		return noFaultResp;
	}
	public void setNoFaultResp(String noFaultResp) {
		this.noFaultResp = noFaultResp;
	}
	public String getAddEquLoss() {
		return addEquLoss;
	}
	public void setAddEquLoss(String addEquLoss) {
		this.addEquLoss = addEquLoss;
	}
	public String getExclDeducSpecial() {
		return exclDeducSpecial;
	}
	public void setExclDeducSpecial(String exclDeducSpecial) {
		this.exclDeducSpecial = exclDeducSpecial;
	}
	public String getFreeOddsSpecial() {
		return freeOddsSpecial;
	}
	public void setFreeOddsSpecial(String freeOddsSpecial) {
		this.freeOddsSpecial = freeOddsSpecial;
	}
	public String getFosCar() {
		return fosCar;
	}
	public void setFosCar(String fosCar) {
		this.fosCar = fosCar;
	}
	public String getFosThird() {
		return fosThird;
	}
	public void setFosThird(String fosThird) {
		this.fosThird = fosThird;
	}
	public String getRescueSpecial() {
		return rescueSpecial;
	}
	public void setRescueSpecial(String rescueSpecial) {
		this.rescueSpecial = rescueSpecial;
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
	public Date getTrfInsStartDate() {
		return trfInsStartDate;
	}
	public void setTrfInsStartDate(Date trfInsStartDate) {
		this.trfInsStartDate = trfInsStartDate;
	}
	public Date getTrfInsEndDate() {
		return trfInsEndDate;
	}
	public void setTrfInsEndDate(Date trfInsEndDate) {
		this.trfInsEndDate = trfInsEndDate;
	}
	public Date getCommInsStartDate() {
		return commInsStartDate;
	}
	public void setCommInsStartDate(Date commInsStartDate) {
		this.commInsStartDate = commInsStartDate;
	}
	public Date getCommInsEndDate() {
		return commInsEndDate;
	}
	public void setCommInsEndDate(Date commInsEndDate) {
		this.commInsEndDate = commInsEndDate;
	}
	public Date getInsuranceTime() {
		return insuranceTime;
	}
	public void setInsuranceTime(Date insuranceTime) {
		this.insuranceTime = insuranceTime;
	}
	public String getEngineWater() {
		return engineWater;
	}
	public void setEngineWater(String engineWater) {
		this.engineWater = engineWater;
	}
	public String getBillCreator() {
		return billCreator;
	}
	public void setBillCreator(String billCreator) {
		this.billCreator = billCreator;
	}
	public String getBillCreatorPhone() {
		return billCreatorPhone;
	}
	public void setBillCreatorPhone(String billCreatorPhone) {
		this.billCreatorPhone = billCreatorPhone;
	}
	public String getCarEngineNumber() {
		return carEngineNumber;
	}
	public void setCarEngineNumber(String carEngineNumber) {
		this.carEngineNumber = carEngineNumber;
	}
	public String getCarUseTypeName() {
		if(!StringUtils.isEmpty(carUseType)){
			Dict dict = DictUtil.getDictById(carUseType);
			return dict.getCnName();
		}else{
			return "";
		}
	}
	public void setCarUseTypeName(String carUseTypeName) {
		this.carUseTypeName = carUseTypeName;
	}
	public String getGlassTypeName() {
		if(!StringUtils.isEmpty(glassType)){
			Dict dict = DictUtil.getDictById(glassType);
			return dict.getCnName();
		}else{
			return "";
		}
	}
	public void setGlassTypeName(String glassTypeName) {
		this.glassTypeName = glassTypeName;
	}
	
	
}
