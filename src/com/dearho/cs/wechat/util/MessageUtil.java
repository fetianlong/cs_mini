package com.dearho.cs.wechat.util;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dearho.cs.wechat.message.resp.Article;
import com.dearho.cs.wechat.message.resp.NewViewMessage;
import com.dearho.cs.wechat.message.resp.NewsMessage;
import com.dearho.cs.wechat.message.resp.TextMessage;

/**
 * 消息工具类
 * 
 * @author wangjing
 * 
 */
public class MessageUtil {

	/**
	 * 响应消息类型：文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 响应消息类型：音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 响应消息类型：图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";

	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";

	/**
	 * 请求消息类型：推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String REQ_MESSAGE_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String REQ_MESSAGE_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型：click(自定义菜单点击)
	 */
	public static final String REQ_MESSAGE_TYPE_CLICK = "CLICK";
	
	/**
	 * 事件类型：扫描事件
	 */
	public static final String REQ_MESSAGE_TYPE_SCAN = "scan";
	
	/**
	 * 自定义菜单点击,打开新页面
	 */
	public static final String REQ_MESSAGE_TYPE_VIEW = "VIEW";

	/**
	 * 解析微信发来的请求(XML)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> parseXml(HttpServletRequest request)
			throws Exception {
		// 将解析结果放在HashMap中
		Map<String, String> map = new HashMap<String, String>();

		// 从请求中取得输入流
		InputStream inputStream = request.getInputStream();

		// 读取输入流
		SAXReader reader = new SAXReader();

		Document document = reader.read(inputStream);

		// 得到XML根元素
		Element root = document.getRootElement();

		// 获得根元素的所有子节点
		@SuppressWarnings("unchecked")
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element element : elementList) {
			map.put(element.getName(), element.getText());
		}

		// 释放资源
		inputStream.close();
		inputStream = null;
		return map;
	}

	/**
	 * 文本消息对象转换成xml 
	 * 
	 * @param openID
	 * @param toUserName
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String textMessageToXml(String fromUserName, String toUserName,
			TextMessage textMessage) throws Exception {
		String response = "<xml>" + "<ToUserName><![CDATA[" + fromUserName
				+ "]]></ToUserName>" + "<FromUserName><![CDATA[" + toUserName
				+ "]]></FromUserName>" + "<CreateTime>"
				+ String.valueOf(new Date().getTime()) + "</CreateTime>"
				+ "<MsgType><![CDATA[text]]></MsgType>" + "<Content><![CDATA["
				+ textMessage.getContent() + "]]></Content>" + "</xml>";
		return response;
	}

	/**
	 * 点击事件打开新窗口 
	 * @param fromUserName
	 * @param toUserName
	 * @param newViewMessage
	 * @return
	 * @throws Exception
	 */
	public static String NewViewMessageToXml(String fromUserName, String toUserName,
			NewViewMessage newViewMessage) throws Exception {
		String response = "<xml>" + "<ToUserName><![CDATA[" + fromUserName
				+ "]]></ToUserName>" + "<FromUserName><![CDATA[" + toUserName
				+ "]]></FromUserName>" + "<CreateTime>"
				+ String.valueOf(new Date().getTime()) + "</CreateTime>"
				+ "<MsgType><![CDATA[event]]></MsgType>" 
				+ "<Event><![CDATA[" + newViewMessage.getEvent() + "]]></Event>" 
				+ "<EventKey><![CDATA[" + newViewMessage.getEventKey() + "]]></EventKey><AgentID>1</AgentID>" 
				+ "</xml>";
		return response;
	}
	
	/**
	 * 图文消息对象转换成xml 
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String newsMessageToXml(String openID, String toUserName,
			NewsMessage newsMessage) throws Exception {
		StringBuffer response = new StringBuffer();
		String responseHead = "";
		StringBuffer responseContent = new StringBuffer();
		String responseFoot = "";

		List<Article> articles = newsMessage.getArticles();
		if (articles.size() != 0) {
			responseHead = "<xml>" + "<ToUserName><![CDATA[" + openID
					+ "]]></ToUserName>" + "<FromUserName><![CDATA["
					+ toUserName + "]]></FromUserName>" + "<CreateTime>"
					+ String.valueOf(new Date().getTime()) + "</CreateTime>"
					+ "<MsgType><![CDATA[news]]></MsgType>" + "<ArticleCount>"
					+ articles.size() + "</ArticleCount>" + "<Articles>";

			for (int i = 0; i < articles.size(); i++) {
				Article article = articles.get(i);
				String itemStr = "<item>" + "<Title><![CDATA["
						+ article.getTitle() + "]]></Title> "
						+ "<Description><![CDATA[" + article.getDescription()
						+ "]]></Description>" + "<PicUrl><![CDATA["
						+ article.getPicUrl() + "]]></PicUrl>"
						+ "<Url><![CDATA[" + article.getUrl() + "]]></Url>"
						+ "</item>";
				responseContent.append(itemStr);
			}
			responseFoot = "</Articles>" + "</xml>";

			response.append(responseHead).append(responseContent)
					.append(responseFoot);
		}
		return response.toString();
	}

	/**
	 * 音乐消息对象转换成xml 
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	// public static String musicMessageToXml(MusicMessage musicMessage)
	// throws Exception {
	// return splitMessageToXml(musicMessage);
	// }

}
