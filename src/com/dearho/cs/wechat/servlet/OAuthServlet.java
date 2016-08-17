package com.dearho.cs.wechat.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.pojo.SubscriberLoginRecord;
import com.dearho.cs.subscriber.service.SubscriberLoginRecordService;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.CookieTool;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.pojo.WeixinOauth2Token;
import com.dearho.cs.wechat.service.WechatUserInfoService;
import com.dearho.cs.wechat.util.WeixinUtil;

/**
 * 微信用户授权
 * @author wangjing
 *
 */
public class OAuthServlet  extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext(); 
	
	private WechatUserInfoService wechatUserInfoService;
	private SubscriberService subscriberService;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			// 用户同意授权后，能获取到code
			String wechatCode = request.getParameter("code");
			String state =request.getParameter("state");
			
			// 用户同意授权
			if (wechatCode != null && !"authdeny".equals(wechatCode)) {
				
				// 获取网页授权access_token
				WeixinOauth2Token weixinOauth2Token = WeixinUtil.getOauth2AccessToken(wechatCode);
				
				wechatUserInfoService = (WechatUserInfoService)webApplicationContext.getBean("wechatUserInfoService"); 
				
				//通过微信号获得数据库中该用户
				WechatUserInfo wechatUserInfo = wechatUserInfoService.getUserInfoByOpenId(weixinOauth2Token.getOpenId());
				
				if(wechatUserInfo==null){
					
					//如果该用户第一次登陆，则获取微信账号信息，并且记录下来
					wechatUserInfo = WeixinUtil.getWechatUserInfo(weixinOauth2Token);
					wechatUserInfoService.addUserInfo(wechatUserInfo);
					request.getSession().setAttribute("wechatUserInfo", wechatUserInfo);
					request.getRequestDispatcher("/mobile/wechatLogin.action").forward(request, response);
				}else{
					
					//如果该用户不是第一次登陆，则看他是不是已经跟会员号绑定
					String unionId = wechatUserInfo.getUnionId();
					subscriberService = (SubscriberService)webApplicationContext.getBean("subscriberService"); 
					Subscriber subscriber=subscriberService.querySubscriberByUnionId(unionId);
					//&& StringUtils.isNotEmpty(state)  && state.equals(subscriber.getWechatUnionId()
					if(subscriber!=null  && StringUtils.isNotEmpty(subscriber.getWechatUnionId() ) ){
						request.getSession().setAttribute(Constants.SESSION_SUBSCRIBER, subscriber);
						
						SubscriberLoginRecordService subscriberLoginRecordService= (SubscriberLoginRecordService)webApplicationContext.getBean("subscriberLoginRecordService"); 
						
						SubscriberLoginRecord loginRecord =new SubscriberLoginRecord();
						loginRecord.setLoginTime(new Date());
						loginRecord.setSubscriberId(subscriber.getId());
						loginRecord.setTs(new Date());
						loginRecord.setIp(CookieTool.getIpAddr(request));
						subscriberLoginRecordService.addSubscriberLoginRecord(loginRecord);
					
						Object referrer=request.getSession().getAttribute("login_referrer");
						String login_referrer="";
						if(referrer!=null){
							login_referrer=(String)referrer;
							response.sendRedirect(login_referrer);
						}else {
							response.sendRedirect("/mobile/toBookCar.action");
						}
					}else{
						//如果没有绑定，跳转登陆页
						request.getSession().setAttribute("wechatUserInfo", wechatUserInfo);
						request.getRequestDispatcher("/mobile/wechatLogin.action").forward(request, response);
					}
					
					
				}
			}else{
				
				request.getRequestDispatcher("/mobile/wechatLogin.action").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/mobile/wechatLogin.action").forward(request, response);
		}
	}

	
}
