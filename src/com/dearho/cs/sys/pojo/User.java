package com.dearho.cs.sys.pojo;

import java.util.Date;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class User implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static String  defaultPassword="123456";
	
	public static final Integer STATUS_INIT=0;//初始
	public static final Integer STATUS_EFFECT=1;//有效
	public static final Integer STATUS_ARREARAGE=2;//欠费
	public static final Integer STATUS_REVIEWING=3;//审核中
	public static final Integer STATUS_NOTPASS=4;//审核未通过
	public static final Integer STATUS_INVALID=5;//失效
	
	
	private String id;
	
	private String loginName;//登录名称
	private String name;//真实姓名
	private String password;
	
	private String phoneNo;
	
	
	private String avatar;

	
	private Integer status;
	
	/**用户组 **/
	private String groupId;
	
	/**上次登录时间 **/
	private Date lastLoginTime;
	/**本次登录时间 **/
	private Date thisLoginTime;
	
	
	private Integer errorCount;
	private Date stopDate;

	
	public User(){
		id = "";
		name = "";
		password = "";
	}
	
	public User(String n, String p){
		this.name = n;
		this.password = p;
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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getThisLoginTime() {
		return thisLoginTime;
	}

	public void setThisLoginTime(Date thisLoginTime) {
		this.thisLoginTime = thisLoginTime;
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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	
	
	
	
	
	
}
