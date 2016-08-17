package com.dearho.cs.carservice.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.FieldComment;
import com.dearho.cs.sys.util.DictUtil;

/**
 * @author GaoYunpeng
 * @Description 车辆维修信息
 * @version 1.0 2015年6月23日 下午4:30:47
 */
public class CarRepair implements Serializable{

	private static final long serialVersionUID = -224463111149915463L;
	
	
	private String id;
	private String code;
	private String city;
	private String orderId;
	private String memberId;
	private String carId;
	private String repairAddress;
	@FieldComment("维修地点") 
	private String repairAddressName;
	private Date sendTime;
	private Date outTime;
	@FieldComment("维修单号") 
	private String repairOrderCode;
	@FieldComment("维修描述")
	private String repairDesc;
	@FieldComment("维修费用")
	private Double repairFee;
	private Integer isSettled;
	@FieldComment("是否结算")
	private String isSettledDesc;
	private Date settleTime;
	private String status;
	@FieldComment("维修状态") 
	private String statusName;
	private String creatorId;
	@FieldComment("备注")
	private String remark;
	private Integer isDiscard;
	private Date createTime;
	private Date updateTime;
	private Date ts;
	
	@FieldComment("城市") 
	private String cityName;
	@FieldComment("事故订单") 
	private String orderCode;
	@FieldComment("送修人")
	private String memberName;
	@FieldComment("车牌号")
	private String plateNumber;
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public String getRepairAddress() {
		return repairAddress;
	}
	public void setRepairAddress(String repairAddress) {
		this.repairAddress = repairAddress;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	public String getRepairOrderCode() {
		return repairOrderCode;
	}
	public void setRepairOrderCode(String repairOrderCode) {
		this.repairOrderCode = repairOrderCode;
	}
	public String getRepairDesc() {
		return repairDesc;
	}
	public void setRepairDesc(String repairDesc) {
		this.repairDesc = repairDesc;
	}
	public Double getRepairFee() {
		return repairFee;
	}
	public void setRepairFee(Double repairFee) {
		this.repairFee = repairFee;
	}
	public Integer getIsSettled() {
		return isSettled;
	}
	public void setIsSettled(Integer isSettled) {
		this.isSettled = isSettled;
	}
	public Date getSettleTime() {
		return settleTime;
	}
	public void setSettleTime(Date settleTime) {
		this.settleTime = settleTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getRepairAddressName() {
		return repairAddressName;
	}
	public void setRepairAddressName(String repairAddressName) {
		this.repairAddressName = repairAddressName;
	}
	public String getIsSettledDesc() {
		if(isSettled==0){
			return "否";
		}else{
			return "是";
		}
	}
	public void setIsSettledDesc(String isSettledDesc) {
		this.isSettledDesc = isSettledDesc;
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
	
	
	
	
}
