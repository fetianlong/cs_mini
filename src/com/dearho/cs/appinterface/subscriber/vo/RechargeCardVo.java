package com.dearho.cs.appinterface.subscriber.vo;

import java.io.Serializable;

import com.dearho.cs.account.pojo.RechargeCard;


/**
 * 充值卡
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-16
 */
public class RechargeCardVo  implements Serializable{
	
	private static final long serialVersionUID = -7747645354169733905L;
	
	public RechargeCardVo(){
		super();
	}
	public RechargeCardVo(RechargeCard rechargeCard){
		super();
		this.setAmount(rechargeCard.getAmount());
		this.setId(rechargeCard.getId());
		this.setShowName(rechargeCard.getShowName());
		this.setSortField(rechargeCard.getSortField());
	}

	private String id;
	private String showName;
	private Integer sortField;
	private Double amount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public Integer getSortField() {
		return sortField;
	}
	public void setSortField(Integer sortField) {
		this.sortField = sortField;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
	
	
}
