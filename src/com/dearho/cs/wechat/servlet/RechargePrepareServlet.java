package com.dearho.cs.wechat.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.pojo.WeixinOauth2Token;
import com.dearho.cs.wechat.service.WechatUserInfoService;
import com.dearho.cs.wechat.util.WeixinUtil;
import com.foxinmy.weixin4j.util.StringUtil;

/**
 * 微信用户授权
 * @author liusong
 *
 */
public class RechargePrepareServlet  extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext(); 
	
	private WechatUserInfoService wechatUserInfoService;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");

			// 用户同意授权后，能获取到code
			String wechatCode = request.getParameter("code");
			
			
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
				}
				
				
			
				String url=request.getParameter("url");
				
				if(!StringUtil.isEmpty(url)){
				
					StringBuffer param=new StringBuffer();
					param.append("?unionId="+wechatUserInfo.getUnionId());
					param.append("&openId="+wechatUserInfo.getOpenId());
					Map<String,String[]> map=request.getParameterMap();
					if(map!=null){
						  for (Map.Entry<String, String[]> entry : map.entrySet()) {
							  if(entry.getValue()!=null && entry.getValue().length>0){
								  param.append("&"+entry.getKey()+"="+entry.getValue()[0]);
							  }
						  }
					}
					
					
					response.sendRedirect(WeixinUtil.hostUrlString+url+param.toString());
				
				}else{
					request.getRequestDispatcher("/mobile/wechatLogin.action").forward(request, response);
				}
					
				
			}else{
				System.out.println("D=================");
				request.getRequestDispatcher("/mobile/wechatLogin.action").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("e=================");
			request.getRequestDispatcher("/mobile/wechatLogin.action").forward(request, response);
		}
	}

	
}
