package com.dearho.cs.wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dearho.cs.wechat.message.resp.Article;
import com.dearho.cs.wechat.message.resp.NewsMessage;
import com.dearho.cs.wechat.util.MessageUtil;
import com.dearho.cs.wechat.util.WeixinUtil;

/**
 * 核心服务类
 * 
 * @author wangjing
 * 
 */
public class CoreService {

	
	/**
	 * 处理微信发来的请求，并返回XML结果推送给微信
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			request.setCharacterEncoding("UTF-8");
			
			// 默认返回的文本消息内容  
//			String respContent = "请求处理异常，请稍后尝试！";
			
			// XML解析  
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			
			// 公众账号
			String toUserName = requestMap.get("ToUserName");
			
			// 消息类型
			String msgType = requestMap.get("MsgType").toLowerCase();
			
			//文本消息
			if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
				// 创建图文消息  
				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);
				
				List<Article> articleList = new ArrayList<Article>();
				Article article = new Article();
				article.setTitle("“华泰”电动汽车分时租赁系统解决方案");
				article.setDescription("想更多了解“华泰”分时租赁系统，登录公司官方网站：www.dearho.com");
				article.setPicUrl(WeixinUtil.hostUrlString+"/mobile/common/images/introduce/dearhoLogo.png");
				article.setUrl(WeixinUtil.hostUrlString+"/mobile/pages/introduce.jsp");
				articleList.add(article);
				
				newsMessage.setArticleCount(articleList.size());
				newsMessage.setArticles(articleList);
				respMessage = MessageUtil.newsMessageToXml(fromUserName, toUserName, newsMessage);
			}
			
			//事件推送
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
				//事件类型
				String eventType = requestMap.get("Event").toLowerCase();
				//订阅，第一次关注的时候，推送事件
				if(eventType.equals(MessageUtil.REQ_MESSAGE_TYPE_SUBSCRIBE)){
					// 创建图文消息  
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
					newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setFuncFlag(0);
					
					List<Article> articleList = new ArrayList<Article>();
					Article article = new Article();
					article.setTitle("“华泰”电动汽车分时租赁系统解决方案");
					article.setDescription("想更多了解“华泰”分时租赁系统，登录公司官方网站：www.dearho.com");
					article.setPicUrl(WeixinUtil.hostUrlString+"/mobile/common/images/introduce/dearhoLogo.png");
					article.setUrl(WeixinUtil.hostUrlString+"/mobile/pages/introduce.jsp");
					articleList.add(article);
					
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(fromUserName, toUserName, newsMessage);
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}
	
	/**
	 * emoji表情转换
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji){
		return String.valueOf(Character.toChars(hexEmoji));
	}

}
