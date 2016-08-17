/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file Subscriber.java creation date:[2015-5-21 下午04:40:25] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.subscriber.pojo;

import java.io.Serializable;
import java.util.Date;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.sys.pojo.FieldComment;

/**
 * @Author liusong
 * @Description 会员
 * @Version 1.0,2015-5-21
 *
 */
public class Subscriber implements Serializable{

	
	private static final long serialVersionUID = 2678842066539911939L;
	
	public static final Integer STATE_UNCONFIRMED=1;   //资料未提交
	public static final Integer STATE_WAIT_CONFIRMED=2;//资料待审核
	public static final Integer STATE_NO_CONFIRMED=4;  //审核未通过
	public static final Integer STATE_NORMAL=3;        //资料已审核
	
	
	public static final Integer STATE_CARD_ISSUED=5;   //已发卡
	public static final Integer STATE_CARD_LOSS=6;     //已挂失
	
	
	public static final Integer EVENT_STATE_HALF=4; //半锁  不能下单/不能操作账户
	public static final Integer EVENT_STATE_FUll=5; //全锁 不能登录
	
	
	
	public static final Integer ORDER_BY_CT_ASC =1;//创建时间 升序 
	public static final Integer ORDER_BY_CT_DESC=2;//创建时间 降序 
	
	public static final String  SEX_MAN="man";
	public static final String  SEX_WOMAN="woman";
	
	public static final String  SOURCE_PORTAL="portal";
	public static final String  SOURCE_WECHAT="wechat";
	
	
	
	private String id;
	/**用户姓名 */
	@FieldComment("昵称")
	private String nickname;
	@FieldComment("昵称状态")
	private Integer nickstatus;
	
	@FieldComment("姓名")
	private String name;
	
	private String password; 
	/**手机号 **/
	@FieldComment("手机号")
	private String phoneNo;
	/** 状态 */
	@FieldComment("状态")
	private Integer state;
	/** 锁定状态 */
	@FieldComment("锁定状态")
	private Integer eventState;
	/** 驾驶证 */
	@FieldComment("驾驶证号")
	private String drivingLicenseNo;
	/**邮箱 */
	@FieldComment("邮箱")
	private String email;
	/** 微信账号**/
	@FieldComment("微信账号")
	private String webchatNo;
	/** 默认城市**/
	/** 积分**/
	private Integer points;
	@FieldComment("性别")
	private String sex;
	
	/** 身份证图片**/
	@FieldComment("身份证图片")
	private String idCardImg;
	/** 驾驶证*/
	@FieldComment("驾驶证图片")
	private String drivingLicenseImg;
	
	@FieldComment("紧急联系人姓名")
	private String emergencyContact;//紧急联系人
	
	@FieldComment("紧急联系人电话")
	private String emergencyContactPhone;//紧急联系人电话
	
	@FieldComment("紧急联系人地址")
	private String emergencyContactAddress;//紧急联系人地址
	
	private String avatar;
	
	private String source;
	
	/**注册时间*/
	
	private Date createDate;
	
	/**注册地*/
	private String registerPlace;
	
	/**时间戳*/
	private Date ts;
	
	/** */
	private Integer errorCount;
	private Date stopDate;
	
	private Account account; //账号详情

	private Integer orderBy;
	
	/** 发票抬头**/
	@FieldComment("发票抬头")
	private String billTitle;
	
	/** 邮编**/
	@FieldComment("邮编")
	private String billPostCode;
	
	/** 邮寄地址**/
	@FieldComment("邮寄地址")
	private String postAddress;
	
	@FieldComment("省份")
	private String province;
	
	@FieldComment("地级市")
	private String city;
	
	@FieldComment("县级市")
	private String county;
	
	@FieldComment("家庭住址")
	private String address;
	
	@FieldComment("备注")
	private String remark;
	
	
	
	//public String loginType;
	//登录设备mac地址
	public String loginMacAddress;
	
	//绑定微信账号的unionid
	public String wechatUnionId;
	
	//短信验证码
	public String smsCode;
	
	public Integer getNickstatus() {
		return nickstatus;
	}
	public void setNickstatus(Integer nickstatus) {
		this.nickstatus = nickstatus;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDrivingLicenseNo() {
		return drivingLicenseNo;
	}
	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Integer getEventState() {
		return eventState;
	}
	public void setEventState(Integer eventState) {
		this.eventState = eventState;
	}
	
	public String getWebchatNo() {
		return webchatNo;
	}
	public void setWebchatNo(String webchatNo) {
		this.webchatNo = webchatNo;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPostAddress() {
		return postAddress;
	}
	public void setPostAddress(String postAddress) {
		this.postAddress = postAddress;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	
	public String getIdCardImg() {
		return idCardImg;
	}
	public void setIdCardImg(String idCardImg) {
		this.idCardImg = idCardImg;
	}
	public String getDrivingLicenseImg() {
		return drivingLicenseImg;
	}
	public void setDrivingLicenseImg(String drivingLicenseImg) {
		this.drivingLicenseImg = drivingLicenseImg;
	}
	public Integer getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
	
	public String getEmergencyContact() {
		return emergencyContact;
	}
	public void setEmergencyContact(String emergencyContact) {
		this.emergencyContact = emergencyContact;
	}
	public String getEmergencyContactPhone() {
		return emergencyContactPhone;
	}
	public void setEmergencyContactPhone(String emergencyContactPhone) {
		this.emergencyContactPhone = emergencyContactPhone;
	}
	public String getEmergencyContactAddress() {
		return emergencyContactAddress;
	}
	public void setEmergencyContactAddress(String emergencyContactAddress) {
		this.emergencyContactAddress = emergencyContactAddress;
	}
	public static String getState(Integer sta){
		String stateDesc="未知";
		if(sta==null){	
		}else if(STATE_UNCONFIRMED.equals(sta)){
			stateDesc="资料未提交";
		}else if(STATE_WAIT_CONFIRMED.equals(sta)){
			stateDesc="资料待审核";
		}else if(STATE_NORMAL.equals(sta)){
			stateDesc="资料已审核";
		}else if(STATE_NO_CONFIRMED.equals(sta)){
			stateDesc="审核未通过";
		}else if(STATE_CARD_ISSUED.equals(sta)){
			stateDesc="已发卡";
		}else if(STATE_CARD_LOSS.equals(sta)){
			stateDesc="已挂失";
		}
		
		return stateDesc;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}

	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	public Date getStopDate() {
		return stopDate;
	}
	public void setStopDate(Date stopDate) {
		this.stopDate = stopDate;
	}
	public String getRegisterPlace() {
		return registerPlace;
	}
	public void setRegisterPlace(String registerPlace) {
		this.registerPlace = registerPlace;
	}
	public String getBillTitle() {
		return billTitle;
	}
	public void setBillTitle(String billTitle) {
		this.billTitle = billTitle;
	}
	public String getBillPostCode() {
		return billPostCode;
	}
	public void setBillPostCode(String billPostCode) {
		this.billPostCode = billPostCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getWechatUnionId() {
		return wechatUnionId;
	}
	public void setWechatUnionId(String wechatUnionId) {
		this.wechatUnionId = wechatUnionId;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getLoginMacAddress() {
		return loginMacAddress;
	}
	public void setLoginMacAddress(String loginMacAddress) {
		this.loginMacAddress = loginMacAddress;
	}
	
	
	
	
}
