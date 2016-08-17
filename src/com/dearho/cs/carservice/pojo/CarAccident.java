package com.dearho.cs.carservice.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.FieldComment;
import com.dearho.cs.sys.util.DictUtil;

/**
 * @author GaoYunpeng
 * @Description 车辆事故信息
 * @version 1.0 2015年6月23日 下午4:30:47
 */
public class CarAccident implements Serializable{

	private static final long serialVersionUID = -224463111149915463L;
	
	private String id;
	private String code;
	private String city;
	private String orderId;
	private String memberId;
	private String carId;
	private String accidentType;
	@FieldComment("事故类型")
	private String accidentTypeName;
	@FieldComment("车辆损失情况")
	private String carLose;
	@FieldComment("事故描述")
	private String accidentDesc;
	private String imgUrls;
	@FieldComment("发生时间")
	private Date happenTime;
	
	private Integer noticeCs;
	@FieldComment("是否通知客服")
	private String noticeCsDesc;
	
	private Date noticeTime;
	private String acceptFlag;
	@FieldComment("受理标记")
	private String acceptFlagName;
	@FieldComment("受理人")
	private String acceptMember;

	private String handleStatus;
	@FieldComment("处理状态")
	private String handleStatusName;
	
	private String statusChangePerson1;
	private Date statusChangeTime1;
	
	private String statusChangePerson2;
	private Date statusChangeTime2;
	
	private String statusChangePerson3;
	private Date statusChangeTime3;
	
	private String statusChangePerson4;
	private Date statusChangeTime4;
	
	private String creatorId;
	@FieldComment("备注")
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
	
	private String photo0;
	private String photo1;
	private String photo2;
	private String photo3;
	private String photo4;
	private String photo5;
	private String photo6;
	private String photo7;
	private String photo8;
	private String photo9;
	
	private String callCode;//事故报案编号
	
	public Date getNoticeTime() {
		return noticeTime;
	}
	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
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
	public String getAccidentType() {
		return accidentType;
	}
	public void setAccidentType(String accidentType) {
		this.accidentType = accidentType;
	}
	public String getCarLose() {
		return carLose;
	}
	public void setCarLose(String carLose) {
		this.carLose = carLose;
	}
	public String getAccidentDesc() {
		return accidentDesc;
	}
	public void setAccidentDesc(String accidentDesc) {
		this.accidentDesc = accidentDesc;
	}
	public String getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}
	public Date getHappenTime() {
		return happenTime;
	}
	public void setHappenTime(Date happenTime) {
		this.happenTime = happenTime;
	}
	public Integer getNoticeCs() {
		return noticeCs;
	}
	public void setNoticeCs(Integer noticeCs) {
		this.noticeCs = noticeCs;
	}
	public String getAcceptFlag() {
		return acceptFlag;
	}
	public void setAcceptFlag(String acceptFlag) {
		this.acceptFlag = acceptFlag;
	}
	public String getAcceptMember() {
		return acceptMember;
	}
	public void setAcceptMember(String acceptMember) {
		this.acceptMember = acceptMember;
	}
	public String getHandleStatus() {
		return handleStatus;
	}
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
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
	public String getStatusChangePerson4() {
		return statusChangePerson4;
	}
	public void setStatusChangePerson4(String statusChangePerson4) {
		this.statusChangePerson4 = statusChangePerson4;
	}
	public Date getStatusChangeTime4() {
		return statusChangeTime4;
	}
	public void setStatusChangeTime4(Date statusChangeTime4) {
		this.statusChangeTime4 = statusChangeTime4;
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
	public String getPhoto0() {
		return photo0;
	}
	public void setPhoto0(String photo0) {
		this.photo0 = photo0;
	}
	public String getPhoto1() {
		return photo1;
	}
	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}
	public String getPhoto2() {
		return photo2;
	}
	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}
	public String getPhoto3() {
		return photo3;
	}
	public void setPhoto3(String photo3) {
		this.photo3 = photo3;
	}
	public String getPhoto4() {
		return photo4;
	}
	public void setPhoto4(String photo4) {
		this.photo4 = photo4;
	}
	public String getPhoto5() {
		return photo5;
	}
	public void setPhoto5(String photo5) {
		this.photo5 = photo5;
	}
	public String getPhoto6() {
		return photo6;
	}
	public void setPhoto6(String photo6) {
		this.photo6 = photo6;
	}
	public String getPhoto7() {
		return photo7;
	}
	public void setPhoto7(String photo7) {
		this.photo7 = photo7;
	}
	public String getPhoto8() {
		return photo8;
	}
	public void setPhoto8(String photo8) {
		this.photo8 = photo8;
	}
	public String getPhoto9() {
		return photo9;
	}
	public void setPhoto9(String photo9) {
		this.photo9 = photo9;
	}
	public String getAccidentTypeName() {
		if(!StringUtils.isEmpty(accidentType)){
			Dict dict = DictUtil.getDictById(accidentType);
			return dict.getCnName();
		}else{
			return "";
		}
	}
	public void setAccidentTypeName(String accidentTypeName) {
		this.accidentTypeName = accidentTypeName;
	}
	public String getNoticeCsDesc() {
		if(noticeCs==0){
			return "否";
		}else{
			return "是";
		}
	}
	public void setNoticeCsDesc(String noticeCsDesc) {
		this.noticeCsDesc = noticeCsDesc;
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
	public String getHandleStatusName() {
		if(!StringUtils.isEmpty(handleStatus)){
			Dict dict = DictUtil.getDictById(handleStatus);
			return dict.getCnName();
		}else{
			return "";
		}
	}
	public void setHandleStatusName(String handleStatusName) {
		this.handleStatusName = handleStatusName;
	}
	public String getCallCode() {
		return callCode;
	}
	public void setCallCode(String callCode) {
		this.callCode = callCode;
	}
	
	
	
	
}
