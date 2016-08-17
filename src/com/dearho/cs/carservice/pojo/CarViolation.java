package com.dearho.cs.carservice.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.FieldComment;
import com.dearho.cs.sys.util.DictUtil;

/**
 * @author GaoYunpeng
 * @Description 车辆违章
 * @version 1.0 2015年6月10日 下午3:16:54
 */
public class CarViolation implements Serializable{

	private static final long serialVersionUID = 6425103520107360742L;

	private String id;
	private String code;
	private String city;
	private String orderId;
	private String memberId;
	private String carId;
	@FieldComment("违章罚款")
	private Double money;
	@FieldComment("违章扣分") 
	private Integer score;
	@FieldComment("发生时间")
	private Date happenTime;
	private String desc;
	@FieldComment("通知会员时间")
	private Date noticeTime;
	@FieldComment("最迟处理违章时间")
	private Date offTime;
	private String handleType;
	private String handleDept;
	@FieldComment("处理方式")
	private String handleTypeName;
	@FieldComment("处理部门")
	private String handleDeptName;
	@FieldComment("处理时间")
	private Date handleTime;
	private String sendType;
	@FieldComment("寄送方式")
	private String sendTypeName;
	@FieldComment("寄送地址")
	private String sendAddress;
	@FieldComment("受理人员")
	private String acceptMember;
	private String acceptFlag;
	
	@FieldComment("受理标记")
	private String acceptFlagName;
	
	private String bizStatus;
	@FieldComment("业务状态")
	private String bizStatusName;
	
	private String statusChangePerson1;
	private Date statusChangeTime1;
	
	private String statusChangePerson2;
	private Date statusChangeTime2;
	
	private String statusChangePerson3;
	private Date statusChangeTime3;
	
	private String creatorId;
	@FieldComment("违章描述")
	private String remark;
	private Integer isDiscard;
	private Date createTime;
	private Date updateTime;
	private Date ts;
	
	@FieldComment("城市") 
	private String cityName;
	@FieldComment("订单编号") 
	private String orderCode;
	private String memberName;
	private String plateNumber;
	
	
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
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Date getHappenTime() {
		return happenTime;
	}
	public void setHappenTime(Date happenTime) {
		this.happenTime = happenTime;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getNoticeTime() {
		return noticeTime;
	}
	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}
	public Date getOffTime() {
		return offTime;
	}
	public void setOffTime(Date offTime) {
		this.offTime = offTime;
	}
	public String getHandleType() {
		return handleType;
	}
	public void setHandleType(String handleType) {
		this.handleType = handleType;
	}
	public String getHandleDept() {
		return handleDept;
	}
	public void setHandleDept(String handleDept) {
		this.handleDept = handleDept;
	}
	public Date getHandleTime() {
		return handleTime;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getSendAddress() {
		return sendAddress;
	}
	public void setSendAddress(String sendAddress) {
		this.sendAddress = sendAddress;
	}
	public String getAcceptMember() {
		return acceptMember;
	}
	public void setAcceptMember(String acceptMember) {
		this.acceptMember = acceptMember;
	}
	public String getAcceptFlag() {
		return acceptFlag;
	}
	public void setAcceptFlag(String acceptFlag) {
		this.acceptFlag = acceptFlag;
	}
	public String getBizStatus() {
		return bizStatus;
	}
	public void setBizStatus(String bizStatus) {
		this.bizStatus = bizStatus;
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
	public String getStatusChangePerson1() {
		return statusChangePerson1;
	}
	public void setStatusChangePerson1(String statusChangePerson1) {
		this.statusChangePerson1 = statusChangePerson1;
	}
	public String getStatusChangePerson2() {
		return statusChangePerson2;
	}
	public void setStatusChangePerson2(String statusChangePerson2) {
		this.statusChangePerson2 = statusChangePerson2;
	}
	public String getStatusChangePerson3() {
		return statusChangePerson3;
	}
	public void setStatusChangePerson3(String statusChangePerson3) {
		this.statusChangePerson3 = statusChangePerson3;
	}
	public Date getStatusChangeTime1() {
		return statusChangeTime1;
	}
	public void setStatusChangeTime1(Date statusChangeTime1) {
		this.statusChangeTime1 = statusChangeTime1;
	}
	public Date getStatusChangeTime2() {
		return statusChangeTime2;
	}
	public void setStatusChangeTime2(Date statusChangeTime2) {
		this.statusChangeTime2 = statusChangeTime2;
	}
	public Date getStatusChangeTime3() {
		return statusChangeTime3;
	}
	public void setStatusChangeTime3(Date statusChangeTime3) {
		this.statusChangeTime3 = statusChangeTime3;
	}
	
	
	public String getHandleTypeName() {
		if(!StringUtils.isEmpty(handleType)){
			Dict dict = DictUtil.getDictById(handleType);
			return dict.getCnName();
		}else{
			return "";
		}
	}
	public void setHandleTypeName(String handleTypeName) {
		this.handleTypeName = handleTypeName;
	}
	
	public String getHandleDeptName() {
		if(!StringUtils.isEmpty(handleDept)){
			Dict dict = DictUtil.getDictById(handleDept);
			return dict.getCnName();
		}else{
			return "";
		}
	}
	public void setHandleDeptName(String handleDeptName) {
		this.handleDeptName = handleDeptName;
	}
	
	public String getSendTypeName() {
		if(!StringUtils.isEmpty(sendType)){
			Dict dict = DictUtil.getDictById(sendType);
			return dict.getCnName();
		}else{
			return "";
		}
	}
	public void setSendTypeName(String sendTypeName) {
		this.sendTypeName = sendTypeName;
	}
	
	public String getAcceptFlagName() {
		if(!StringUtils.isEmpty(acceptFlag)){
			Dict dict = DictUtil.getDictById(acceptFlag);
			return dict.getCnName();
		}else{
			return "";
		}
	}
	public void setAcceptFlagName(String acceptFlagName) {
		this.acceptFlagName = acceptFlagName;
	}
	
	
}
