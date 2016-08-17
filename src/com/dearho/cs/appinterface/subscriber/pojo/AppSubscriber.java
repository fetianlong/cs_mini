package com.dearho.cs.appinterface.subscriber.pojo;



public class AppSubscriber {
	public static final Integer STATE_UNCONFIRMED=1;   //资料未提交
	public static final Integer STATE_WAIT_CONFIRMED=2;//资料待审核
	public static final Integer STATE_NO_CONFIRMED=4;  //审核未通过
	public static final Integer STATE_NORMAL=3;        //资料已审核
	
	public static final Integer STATE_CARD_ISSUED=5;   //已发卡
	public static final Integer STATE_CARD_LOSS=6;     //已挂失
	private String id;
	private String name; // 姓名
	private String phoneNo;//手机号
	private Integer state;//
	private String drivingLicenseNo;//驾驶证号
	private String email;//邮箱
	private String webchatName;// 微信帐号名称
	private String province;//省份
	private String city;//地级市
	private String county;//县级市
	private String address;//家庭住址
	private String emergencyContact;//紧急联系人
	private String emergencyContactPhone;//紧急联系人电话
	private String emergencyContactAddress;//紧急联系人地址
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
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getDrivingLicenseNo() {
		return drivingLicenseNo;
	}
	public void setDrivingLicenseNo(String drivingLicenseNo) {
		this.drivingLicenseNo = drivingLicenseNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebchatName() {
		return webchatName;
	}
	public void setWebchatName(String webchatName) {
		this.webchatName = webchatName;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
}
