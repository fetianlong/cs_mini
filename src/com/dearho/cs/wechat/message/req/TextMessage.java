package com.dearho.cs.wechat.message.req;

/**
 * 文本消息
 * @author wangjing
 *
 */
public class TextMessage extends BaseMessage{

	/**
	 * 消息内容
	 */
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		this.Content = content;
	}
}
