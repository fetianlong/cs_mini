package com.dearho.cs.wechat.pojo;

/**
 * view事件按钮（点击按钮跳转页面）
 * @author wangjing
 *
 */
public class ViewButton extends Button{

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
