package com.dearho.cs.feestrategy.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dearho.cs.feestrategy.pojo.StrategyBase;
import com.dearho.cs.sys.pojo.Dict;

/**
 * 在车辆列表页面或选择策略页面，显示的策略信息。
 * 包括策略类型简称（时 日 月等），策略价格（30元/小时  180元/天 等），策略实体类
 * @author GaoYunpeng
 *
 */
public class StrategyCarShowInfo implements Serializable{

	private static final long serialVersionUID = 7968243745369562005L;

	private Dict strategyType;
	private BigDecimal price;
	private String unitStr;
	private StrategyBase strategyBase;
	private String strategyRelationId;
	private String color;//颜色值
	
	public Dict getStrategyType() {
		return strategyType;
	}
	public void setStrategyType(Dict strategyType) {
		this.strategyType = strategyType;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public StrategyBase getStrategyBase() {
		return strategyBase;
	}
	public void setStrategyBase(StrategyBase strategyBase) {
		this.strategyBase = strategyBase;
	}
	public String getStrategyRelationId() {
		return strategyRelationId;
	}
	public void setStrategyRelationId(String strategyRelationId) {
		this.strategyRelationId = strategyRelationId;
	}
	public String getUnitStr() {
		return unitStr;
	}
	public void setUnitStr(String unitStr) {
		this.unitStr = unitStr;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
	
}
