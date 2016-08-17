package com.dearho.cs.wechat.message.req;

/**
 * 消息基类
 * @author wangjing
 *
 */
public class BaseMessage {

	/**
	 * 开发者微信号
	 */
	private String ToUserName; 
	
	/**
	 * 发送方微信号
	 */
	private String FromUserName; 
	
	/**
	 * 消息创建时间
	 */
	private long CreateTime; 
	
	/**
	 * 消息类型(text/image/location/link)
	 */
	private String MsgType; 
	
	/**
	 * 消息id
	 */
	private String MsgId;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	
}
