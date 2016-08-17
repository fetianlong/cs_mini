package com.dearho.cs.wechat.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.util.Constants;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.pojo.WeixinOauth2Token;
import com.dearho.cs.wechat.service.WechatUserInfoService;
import com.dearho.cs.wechat.util.WeixinUtil;

/**
 * 微信静默授权
 * @author wangjing
 *
 */
public class BaseOAuthServlet  extends HttpServlet {

	private static final long serialVersionUID = -1847238807216447030L;;
	
	public static WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext(); 
	
	private WechatUserInfoService wechatUserInfoService;
	private SubscriberService subscriberService;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		// 用户同意授权后，能获取到code
		String wechatCode = request.getParameter("code");
		String state =request.getParameter("state");
		if (!"authdeny".equals(wechatCode)) {
			// 获取网页授权access_token
			WeixinOauth2Token weixinOauth2Token = WeixinUtil.getOauth2AccessToken(wechatCode);
			
			wechatUserInfoService = (WechatUserInfoService)webApplicationContext.getBean("wechatUserInfoService"); 
			
			WechatUserInfo wechatUserInfo = wechatUserInfoService.getUserInfoByOpenId(weixinOauth2Token.getOpenId());//获得数据库中该用户
			
			if(wechatUserInfo==null){
				//如果没有该用户信息，则请求用户授权
				WeixinUtil.requestOauth2Code(response, "snsapi_userinfo");
			}else{
				String unionId = wechatUserInfo.getUnionId();
				subscriberService = (SubscriberService)webApplicationContext.getBean("subscriberService"); 
				Subscriber subscriber=subscriberService.querySubscriberByUnionId(unionId);
				//&& StringUtils.isNotEmpty(state) && state.equals(subscriber.getWechatUnionId()
				if(subscriber!=null && StringUtils.isNotEmpty(subscriber.getWechatUnionId()) ){
					request.getSession().setAttribute(Constants.SESSION_SUBSCRIBER, subscriber);
				}else{
					request.getSession().setAttribute("wechatUserInfo", wechatUserInfo);
					request.getRequestDispatcher("/mobile/wechatLogin.action").forward(request, response);
					
				}
			}
		}
	}

	
}
