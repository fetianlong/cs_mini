package com.dearho.cs.wechat.message.resp;

/**
 * 点击事件跳转链接事件推送
 * @author wangjing
 */
public class NewViewMessage extends BaseMessage{
	
	/**
	 * 事件类型，VIEW 
	 */
	private String event;
	
	/**
	 * 事件KEY值，设置的跳转URL 
	 */
	private String eventKey;
	

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
}
