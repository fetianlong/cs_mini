package com.dearho.cs.appinterface.bookcar.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class StrtgInfo implements Serializable{

	private static final long serialVersionUID = -8464261154786477352L;
	private String color;
	private String strtgTypeName;
	private String strtgType;
	private BigDecimal price;
	private String unitStr;
	
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getStrtgTypeName() {
		return strtgTypeName;
	}
	public void setStrtgTypeName(String strtgTypeName) {
		this.strtgTypeName = strtgTypeName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getUnitStr() {
		return unitStr;
	}
	public void setUnitStr(String unitStr) {
		this.unitStr = unitStr;
	}
	public String getStrtgType() {
		return strtgType;
	}
	public void setStrtgType(String strtgType) {
		this.strtgType = strtgType;
	}

}
