package com.dearho.cs.feestrategy.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.FieldComment;
import com.dearho.cs.sys.util.DictUtil;

public class StrategyBase implements Serializable{
	
	private static final long serialVersionUID = 1123122312313L;

	private String id;
	@FieldComment("策略名称") 
	private String name;
	private String type;  //类型	时租 白天 夜晚 日租 周租 月租	从字典中获取，还可以增加2小时租，4小时租
	@FieldComment("类型") 
	private String typeName;
	@FieldComment("基础价格")
	private BigDecimal basePrice;  //基础价格
	@FieldComment("里程价格")
	private BigDecimal kmPrice;   //里程价格
	
	private String timelyFeeUnit;  //时租计价单位	分钟，小时
	@FieldComment("基础计价单位")
	private String timelyFeeUnitName;
	@FieldComment("基础计价时长")
	private Integer timelyFeeLong;  //时租计价时长
	@FieldComment("预留取车时间")
	private Integer timeBeforeGet;   //取车时间
	@FieldComment("最低消费")
	private BigDecimal minConsumption;  //最低消费
	@FieldComment("最高消费")
	private BigDecimal maxConsumption;  //最高消费（时租24小时）
	@FieldComment("超时罚金")
	private BigDecimal overtimePenalty;//超时罚金
	private Integer isPrepaidPay;   //是否是预付费

	private Integer isUsed;
	private Date createTime;  
	private String creator;
	private Date updateTime;
	
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}
	public BigDecimal getKmPrice() {
		return kmPrice;
	}
	public void setKmPrice(BigDecimal kmPrice) {
		this.kmPrice = kmPrice;
	}
	public String getTimelyFeeUnit() {
		return timelyFeeUnit;
	}
	public void setTimelyFeeUnit(String timelyFeeUnit) {
		this.timelyFeeUnit = timelyFeeUnit;
	}
	public Integer getTimelyFeeLong() {
		return timelyFeeLong;
	}
	public void setTimelyFeeLong(Integer timelyFeeLong) {
		this.timelyFeeLong = timelyFeeLong;
	}
	public Integer getTimeBeforeGet() {
		return timeBeforeGet;
	}
	public void setTimeBeforeGet(Integer timeBeforeGet) {
		this.timeBeforeGet = timeBeforeGet;
	}
	public BigDecimal getMinConsumption() {
		return minConsumption;
	}
	public void setMinConsumption(BigDecimal minConsumption) {
		this.minConsumption = minConsumption;
	}
	public BigDecimal getMaxConsumption() {
		return maxConsumption;
	}
	public void setMaxConsumption(BigDecimal maxConsumption) {
		this.maxConsumption = maxConsumption;
	}
	public BigDecimal getOvertimePenalty() {
		return overtimePenalty;
	}
	public void setOvertimePenalty(BigDecimal overtimePenalty) {
		this.overtimePenalty = overtimePenalty;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}
	public Integer getIsPrepaidPay() {
		return isPrepaidPay;
	}
	public void setIsPrepaidPay(Integer isPrepaidPay) {
		this.isPrepaidPay = isPrepaidPay;
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
	public String getTimelyFeeUnitName() {
		if(!StringUtils.isEmpty(timelyFeeUnit)){
			Dict dict = DictUtil.getDictById(timelyFeeUnit);
			return dict.getCnName();
		}else{
			return "";
		}
	}
	public void setTimelyFeeUnitName(String timelyFeeUnitName) {
		this.timelyFeeUnitName = timelyFeeUnitName;
	}
}
