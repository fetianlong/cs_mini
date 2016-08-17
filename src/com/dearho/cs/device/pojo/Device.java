package com.dearho.cs.device.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dearho.cs.sys.pojo.Company;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.FieldComment;
import com.dearho.cs.sys.service.CompanyService;
import com.dearho.cs.sys.util.DictUtil;

public class Device implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**主键ID*/
	private String id;
	/**内部编码*/
	@FieldComment("车机编号")
	private String setNo;
	/**车机厂家*/
	private String setName;
	@FieldComment("厂家名称")
	private String setNameTitle;
	/**车机型号*/
	private String setType;
	@FieldComment("车机型号")
	private String setTypeName;
	/**手机卡号*/
	@FieldComment("SIM卡号")
	private String simId;
	/**是否刷过固件*/
	private Integer updateFirmware;
	@FieldComment("是否刷过固件")
	private String updateFirmwareMark;
	/**当前固件版本*/
	@FieldComment("当前版本")
	private String firmwareVersion;
	/**车机状态*/
	private String setState;
	/**车机生产日期*/
	@FieldComment("生产日期")
	private Date productDate;
	/**车机首次启用日期*/
	@FieldComment("首次启用日期")
	private Date onlineDate;
	/**创建人Id*/
	private String creatorId;
	/**创建时间*/
	private Date createDate;
	/**时间戳*/
	private Date ts;
	@FieldComment("备注")
	private String remark;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getSetNo() {
		return setNo;
	}

	public void setSetNo(String setNo) {
		this.setNo = setNo;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public String getSetType() {
		return setType;
	}

	public void setSetType(String setType) {
		this.setType = setType;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	


	public Integer getUpdateFirmware() {
		return updateFirmware;
	}

	public void setUpdateFirmware(Integer updateFirmware) {
		this.updateFirmware = updateFirmware;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public String getSetState() {
		return setState;
	}

	public void setSetState(String setState) {
		this.setState = setState;
	}
	
	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public Device() {
		super();
	}

	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	public Date getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Override
	public String toString() {
		return "Device [id=" + id + ", setNo=" + setNo + ", setName=" + setName
				+ ", setType=" + setType + ", simId=" + simId
				+ ", updateFirmware=" + updateFirmware + ", firmwareVersion="
				+ firmwareVersion + ", setState=" + setState
				+ ", productDate=" + productDate + ", onlineDate=" + onlineDate
				+ ", creatorId=" + creatorId + ", createDate=" + createDate
				+ ", ts=" + ts + "]";
	}

	public Device(String id, String setNo, String setName, String setType,
			String simId, Integer updateFirmware, String firmwareVersion,
			String setState, Date productDate, Date onlineDate,
			String creatorId, Date createDate, Date ts) {
		super();
		this.id = id;
		this.setNo = setNo;
		this.setName = setName;
		this.setType = setType;
		this.simId = simId;
		this.updateFirmware = updateFirmware;
		this.firmwareVersion = firmwareVersion;
		this.setState = setState;
		this.productDate = productDate;
		this.onlineDate = onlineDate;
		this.creatorId = creatorId;
		this.createDate = createDate;
		this.ts = ts;
	}
	public String getSetTypeName() {
		Dict dict = DictUtil.getDictById(setType);
		return dict.getCnName();
	}
	public void setSetTypeName(String setTypeName) {
		this.setTypeName = setTypeName;
	}
	public String getUpdateFirmwareMark() {
		if(updateFirmware==0){
			return "否";
		}else{
			return "是";
		}
	}
	public void setUpdateFirmwareMark(String updateFirmwareMark) {
		this.updateFirmwareMark = updateFirmwareMark;
	}
	public String getSetNameTitle() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		CompanyService companyService = (CompanyService) webApplicationContext.getBean("companyService");
		Company company = companyService.searchCompanyById(setName);
		return company.getName();
	}
	public void setSetNameTitle(String setNameTitle) {
		this.setNameTitle = setNameTitle;
	}

	
}
