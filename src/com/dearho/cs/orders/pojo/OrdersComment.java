package com.dearho.cs.orders.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单评论
 * @author Carsharing05
 *
 */
public class OrdersComment  implements Serializable{
	private static final long serialVersionUID = 1L;
	/**主键ID*/
	private String id;
	/**订单ID*/
	private String ordersId;
	/**订单编号*/
	private String ordersNo;
	/**评论时间*/
	private Date commentTime;
	/**评论人*/
	private String commentPerson;
	/**评论人名字*/
	private String commentPersonName;
	/**评论内容*/
	private String commentContent;
	/**审核状态*/
	private String auditState;
	/**审核人*/
	private String auditor;
	/**审核人*/
	private String auditorName;
	/**审核时间*/
	private Date auditTime;
	/**评分*/
	private int score;
	/**评论人手机**/
	private String commentPersonPhone;
	/**评论时间字符串*/
	private String commentTimeStr;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}
	public Date getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	public String getCommentPerson() {
		return commentPerson;
	}
	public void setCommentPerson(String commentPerson) {
		this.commentPerson = commentPerson;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getAuditState() {
		return auditState;
	}
	public void setAuditState(String auditState) {
		this.auditState = auditState;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getCommentPersonName() {
		return commentPersonName;
	}
	public void setCommentPersonName(String commentPersonName) {
		this.commentPersonName = commentPersonName;
	}
	public String getOrdersNo() {
		return ordersNo;
	}
	public void setOrdersNo(String ordersNo) {
		this.ordersNo = ordersNo;
	}
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getCommentPersonPhone() {
		return commentPersonPhone;
	}
	public void setCommentPersonPhone(String commentPersonPhone) {
		this.commentPersonPhone = commentPersonPhone;
	}
	public String getCommentTimeStr() {
		return commentTimeStr;
	}
	public void setCommentTimeStr(String commentTimeStr) {
		this.commentTimeStr = commentTimeStr;
	}
}
