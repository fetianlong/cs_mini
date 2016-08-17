package com.dearho.cs.core.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.pojo.Menu;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.GroupService;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.CookieTool;
import com.dearho.cs.util.Md5Util;
import com.dearho.cs.wechat.util.WeixinUtil;

public class LoginFilter implements Filter {
	private String[] checkUrls = { ".action", ".jsp" };

	private String[] escapeUrls = {
			"login.action",
			"index.jsp",
			"no_permission.jsp",
			"yzmimg",
			
			// 网点展示
			"/portal/place/dotInfoSearch.action",
			
		/*	
			"sendPhoneFindPwdCode.action",
			"resetPwd.action",

*/			
			//后台登录
			"/common/login.action", "/common/doLogin.action",
			"/mobile/sendLoginCode.action",
			// 微信端登录
			"/mobile/wechatLogin.action","/mobile/doLogin.action","/mobile/creatWechatMenu.action","/mobile/account/toCustomerService.action",
			
			//微信端用户注册
			"/mobile/subscriber/register.action",
			"/mobile/subscriber/sendPhoneVerificationCode.action",
			"/mobile/subscriber/registerStepOne.action",
			"/mobile/subscriber/isPhoneEngagedNew.action",
			"/mobile/pages/introduce.jsp",
			//微信端费用估算计费规则
			"/mobile/showFee.action",
			"/mobile/pages/jifeiguize.jsp",
			
			//微信约车页面
			"/mobile/dotInfoSearch.action",
			"/mobile/toBookingInfoCheck.action",
			"/mobile/toShowCarFee.action",
		
			//支付返回链接
			"/unionpay/recharegeBackRcvResponse.action",
			"/unionpay/rechargeFontRcvResponse.action",
			"/unionpay/orderPayWebBackRcveive.action",
			"/unionpay/orderPayWebFontRcveive.action",
			"/unionpay/orderFinishPayWebBackRcveive.action",
			"/unionpay/orderFinishPayWebFontRcveive.action",
			"/unionpay/refundBackRceive.action",
			"/unionpay/DFBackRceive.action",
			"/portal/pages/bookingfinish.jsp",
			"/unionpay/orderRefundBackRceive.action",
			
			"/alipay/webBackReceive.action",
			"/alipay/webFontReceive.action",
			"/alipay/jsdzBackReceive.action",
			"/alipay/jsdzFontReceive.action",
			"/alipay/orderCreateJsdzBackReceive.action",
			"/alipay/orderCreateJsdzFontReceive.action",
			
			"/alipay/batchTransRefundBackReceive.action",
			
			"/alipay/orderRefundBackReceive.action",
			"/alipay/accountRefundBackReceive.action",
			
			
			"/wxpay/nativePayOrderBackRcveive.action",
			"/wxpay/nativePayRechargeBackRcveive.action",
			"/wxpay/nativePayBackRcveive.action",
			"/wxpay/jsPayRechargeBackRcveive.action",
			"/wxpay/jsPayOrderPayBackRcveive.action",
			"/wxpay/jsPayOrderFinishPayBackRcveive.action"
	};
	// , "index.jsp","no_permission.jsp"

	private UserService userService;
	private GroupService groupService;

	private SubscriberService subscriberService;

	private List<String> menuUrlList = new ArrayList<String>();

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest r, ServletResponse o, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) r;
		HttpServletResponse response = (HttpServletResponse) o;
		String url = request.getRequestURI();
		// 先判断是不是需要检查session的资源大类

		System.out.println(url);
		
		
		String path = request.getContextPath();
		if (url.startsWith(path)
				&& (url.endsWith(".action") || url.endsWith(".jsp"))) {
			url = url.replaceFirst(path, "");
		}

		// 自动登录设置,
		String indexUrl = null;
		if (url.length() > 1) {
			indexUrl = url.substring(0, url.length() - 1);
		}
		if ((path.equals(indexUrl) || (url.startsWith("/portal") && url
				.endsWith(".action")))
				&& request.getSession().getAttribute(
						Constants.SESSION_SUBSCRIBER) == null) {
			String subscriberId = CookieTool.getCurrentUser(request);
			if (!StringUtils.isEmpty(subscriberId)) {
				Subscriber s = subscriberService
						.querySubscriberById(subscriberId);
				if (s != null) {
					request.getSession().setAttribute(
							Constants.SESSION_SUBSCRIBER, s);
				}
			}
		}
		
		// 属于权限验证类型后缀
		if (check(url, checkUrls) && !check(url, escapeUrls)) {
			/*
			 * 前台后台判断 1.前台则只验证用户登录状态 2.后台验证 1.登录 2.是否需要校验 3.校验
			 */

			// 前台
			
			if (url.startsWith("/app")) {
				
				
			} else if (url.startsWith("/mobile")) {
				// 微信手机端
				if (request.getSession().getAttribute(Constants.SESSION_SUBSCRIBER) == null) {
					String login_referrer = request.getRequestURI() + "?"+ request.getQueryString();
					request.getSession().setAttribute("login_referrer",login_referrer);
					WeixinUtil.requestOauth2Code(response, "snsapi_userinfo");
//					request.getRequestDispatcher("/mobile/wechatLogin.action").forward(request, response);
				}
			} else {
				// 后台
				if (request.getSession().getAttribute(Constants.SESSION_USER) == null) {
					// 后台链接 用户成功登录过则访问登录页，未登录成功过则跳转至首页
					if (CookieTool.getCookieValueByName(request, "CS_ADMINUSER") != null
							&& Md5Util.MD5Encode("adminUser").equals(
									CookieTool.getCookieValueByName(request,
											"CS_ADMINUSER"))) {
						response.sendRedirect(request.getContextPath()
								+ "/common/login.action");
					} else {
						response.sendRedirect(request.getContextPath());
					}

					return;
				} else {
						 if(needPermissionValidation(url)){ 
							 User user=(User)request.getSession().getAttribute(Constants.SESSION_USER);
							 
							  Object objPermission =request.getSession().getAttribute(Constants.SESSION_USER_PERMISSION);
							  List<String> permissionList=null; 
							  if(objPermission!=null){
								  permissionList=(List<String>)objPermission; 
							  }else{
								  permissionList =groupService.getUserPermissionByGroupId(user.getGroupId());
								  request.getSession().setAttribute(Constants.SESSION_USER_PERMISSION,permissionList); 
							  }
							  if(!permissionList.contains(url)){
								  response.sendRedirect(request.getContextPath()+"/no_permission.jsp"); 
								  return; 
							  }
					  }
				}
			}

		}

		chain.doFilter(request, response);
	}

	private boolean check(String element, String[] elements) {
		for (String e : elements) {
			if (element.endsWith(e)) {
				return true;
			}
		}
		return false;
	}

	private Boolean needPermissionValidation(String url) {
		return menuUrlList.contains(url);
	}



	@Override
	public void init(FilterConfig config) throws ServletException {
		ApplicationContext act = ContextLoader
				.getCurrentWebApplicationContext();
		userService = (UserService) act.getBean("userService");
		groupService = (GroupService) act.getBean("groupService");
		subscriberService = (SubscriberService) act
				.getBean("subscriberService");
		
		List<Menu> menuList = groupService.getAllMenu();
		if (menuList != null && menuList.size() > 0) {
			for (Menu menu : menuList) {
				menuUrlList.add(menu.getMenuUrl());
			}

		}
		System.err.println("初始化完成");
	}

}
