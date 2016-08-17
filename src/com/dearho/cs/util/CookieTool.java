package com.dearho.cs.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.pojo.User;




public class CookieTool {
	
	
	/**
	 * 字符编码
	 */
	public final static String encoding = "UTF-8";
	
	
	
	//#cookie失效时间 3600 * 24 * 15 = 12960000 15天
	public final static Integer 	maxAge = 12960000;
	
	private static final DESEncryptHelper desHelper = new DESEncryptHelper("4T9Gd3W2");
	
	/**
	 * 设置当前登录用户
	 * @param request
	 * @param response
	 * @param user
	 * @param autoLogin
	 */
	public static void setCurrentUser(HttpServletRequest request, HttpServletResponse response, Subscriber user, boolean autoLogin) {
		
		
		try {
		
			// 1.设置cookie有效时间
			int maxAgeTemp = -1;
			if (autoLogin) {
				maxAgeTemp = maxAge;
			}
	
			// 2.设置用户名到cookie
			addCookie(response, "", "/", true, "SUBSCRIBER_PHONENO", user.getPhoneNo(), maxAgeTemp);
			
			if(!autoLogin){
				return;
			}
	
			// 3.生成登陆认证cookie
			String userIds = user.getId();
			String ips = getIpAddr(request);
			String userAgent = request.getHeader("User-Agent");
			long date = ToolDateTime.getDateByTime();
	
			StringBuilder token = new StringBuilder();// 时间戳#USERID#USER_IP#USER_AGENT
			token.append(date).append(".#.").append(userIds).append(".#.").append(ips).append(".#.").append(userAgent);
			
			
			addCookie(response,  "", "/", true, "authmark", desHelper.getEncryptStr(token.toString()), maxAgeTemp);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		
	}
	
	
	public static String getCurrentUser(HttpServletRequest request) {
		String loginCookie = getCookieValueByName(request, "authmark");
		if (null != loginCookie && !loginCookie.equals("")) {
			try{
				
				String data =desHelper.decrypt(loginCookie);
				String[] datas = data.split(".#.");	//arr[0]：时间戳，arr[1]：USERID，arr[2]：USER_IP， arr[3]：USER_AGENT
				
				// 3. 获取数据
				long loginDateTimes = Long.parseLong(datas[0]);// 时间戳
				String userIds = datas[1];// 用户id
				String ips = datas[2];// ip地址
				String userAgent = datas[3];// USER_AGENT

				String newIp = getIpAddr(request);
				String newUserAgent = request.getHeader("User-Agent");

				Date start = ToolDateTime.getDate();
				start.setTime(loginDateTimes);
				int day = ToolDateTime.getDateDaySpace(start, ToolDateTime.getDate());
				
				// 4. 验证数据有效性
				if (ips.equals(newIp) &&  userAgent.equals(newUserAgent) && day <= 15) {
					return userIds;
				}
				
			}catch(Exception e){
				return null;
			}
			
		}

		return null;
	}
	
	
	public static void removeCurrentUser(HttpServletRequest request,HttpServletResponse response){
		addCookie(response,  "", "/", true, "authmark", "", maxAge);
	}
	
	/**
	 * 
	 * @param response
	 * @param domain		设置cookie所在域
	 * @param path			设置cookie所在路径
	 * @param isHttpOnly	是否只读
	 * @param name			cookie的名称
	 * @param value			cookie的值
	 * @param maxAge		cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
	 */
	public static void addCookie(HttpServletResponse response, 
			String domain, String path, boolean isHttpOnly, 
			String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);

		// 所在域：比如a1.4bu4.com 和 a2.4bu4.com 共享cookie
		if(null != domain && !domain.isEmpty()){
			cookie.setDomain(domain);			
		}
		
		// 设置cookie所在路径
		cookie.setPath("/");
		if(null != path && !path.isEmpty()){
			cookie.setPath(path);				
		}
		
		// 是否只读
		//cookie.setHttpOnly(isHttpOnly);
		
		// 设置cookie的过期时间
		if (maxAge > 0){
			cookie.setMaxAge(maxAge);
		}
		
		// 添加cookie
		response.addCookie(cookie);
	}
	
	
	/**
	 * 获取客户端IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	
	/**
	 * 获取cookie的值
	 * 
	 * @param request
	 * @param name
	 *            cookie的名称
	 * @return
	 */
	public static String getCookieValueByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = readCookieMap(request);
		// 判断cookie集合中是否有我们像要的cookie对象 如果有返回它的值
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			
			if(StringUtils.isEmpty(cookie.getValue())){
				return null;
			}else{
				return cookie.getValue();
			}
		} else {
			return null;
		}
	}
	
	
	/**
	 * 获得所有cookie
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		// 从request范围中得到cookie数组 然后遍历放入map集合中
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				cookieMap.put(cookies[i].getName(), cookies[i]);
			}
		}
		return cookieMap;
	}

	
	
	/**
	 * 设置当前登录用户
	 * @param request
	 * @param response
	 * @param user
	 * @param autoLogin
	 */
	public static void setCurrentAdmin(HttpServletRequest request, HttpServletResponse response, User user, boolean autoLogin) {
		try {
		
			// 1.设置cookie有效时间
			int maxAgeTemp = -1;
			if (autoLogin) {
				maxAgeTemp = maxAge;
			}
	
			// 2.设置用户名到cookie
			addCookie(response, "", "/", true, "CS_ADMIN_PHONENO", user.getLoginName(), maxAgeTemp);
			
			if(!autoLogin){
				addCookie(response,  "", "/", true, "cs_admin_authmark", null, maxAgeTemp);
				return;
			}
	
			// 3.生成登陆认证cookie
			String userIds = user.getId();
			String ips = getIpAddr(request);
			String userAgent = request.getHeader("User-Agent");
			long date = ToolDateTime.getDateByTime();
	
			StringBuilder token = new StringBuilder();// 时间戳#USERID#USER_IP#USER_AGENT
			token.append(date).append(".#.").append(userIds).append(".#.").append(ips).append(".#.").append(userAgent);
			
		
			addCookie(response,  "", "/", true, "cs_admin_authmark", desHelper.getEncryptStr(token.toString()), maxAgeTemp);
			
	//		addCookie(response,  "", "/", true, DESEncryptHelper.getEncryptStr(token.toString()),user.getLoginName() , maxAgeTemp);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static String getCurrentAdmin(HttpServletRequest request) {
		String loginCookie = getCookieValueByName(request, "cs_admin_authmark");
		if (null != loginCookie && !loginCookie.equals("")) {
			try{
				
				String data =desHelper.decrypt(loginCookie);
				String[] datas = data.split(".#.");	//arr[0]：时间戳，arr[1]：USERID，arr[2]：USER_IP， arr[3]：USER_AGENT
				
				// 3. 获取数据
				long loginDateTimes = Long.parseLong(datas[0]);// 时间戳
				String userIds = datas[1];// 用户id
				String ips = datas[2];// ip地址
				String userAgent = datas[3];// USER_AGENT

				String newIp = getIpAddr(request);
				String newUserAgent = request.getHeader("User-Agent");

				Date start = ToolDateTime.getDate();
				start.setTime(loginDateTimes);
				int day = ToolDateTime.getDateDaySpace(start, ToolDateTime.getDate());
				
				// 4. 验证数据有效性
				if (ips.equals(newIp) &&  userAgent.equals(newUserAgent) && day <= 15) {
					return userIds;
				}
				
			}catch(Exception e){
				return null;
			}
			
		}

		return null;
	}
	
	
	public static void removeCurrentAdmin(HttpServletRequest request,HttpServletResponse response){
		addCookie(response,  "", "/", true, "cs_admin_authmark", "", maxAge);
	}
	
	
	

	/**
	 * 设置当前登录用户
	 * @param request
	 * @param response
	 * @param user
	 * @param autoLogin
	 */
	public static void setCurrentAdminToken(HttpServletRequest request, HttpServletResponse response, User user) {
		try {
		
			if(user==null || user.getId()==null){
				addCookie(response,  "", "/", true, "cs_admin_token", null, 0);
				return;
			}

			// 3.生成登陆认证cookie
			String userIds = user.getId();
			String ips = getIpAddr(request);
			String userAgent = request.getHeader("User-Agent");
			long date = ToolDateTime.getDateByTime();
	
			StringBuilder token = new StringBuilder();// 时间戳#USERID#USER_IP#USER_AGENT
			token.append(date).append(".#.").append(userIds).append(".#.").append(ips).append(".#.").append(userAgent);
			
			addCookie(response,  "", "/", true, "cs_admin_token", desHelper.getEncryptStr(token.toString()), 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static String getCurrentAdminToken(HttpServletRequest request) {
		String loginCookie = getCookieValueByName(request, "cs_admin_token");
		if (null != loginCookie && !loginCookie.equals("")) {
			try{
				
				String data =desHelper.decrypt(loginCookie);
				String[] datas = data.split(".#.");	//arr[0]：时间戳，arr[1]：USERID，arr[2]：USER_IP， arr[3]：USER_AGENT
				
				// 3. 获取数据
				long loginDateTimes = Long.parseLong(datas[0]);// 时间戳
				String userIds = datas[1];// 用户id
				String ips = datas[2];// ip地址
				String userAgent = datas[3];// USER_AGENT

				String newIp = getIpAddr(request);
				String newUserAgent = request.getHeader("User-Agent");

				Date start = ToolDateTime.getDate();
				start.setTime(loginDateTimes);
				int day = ToolDateTime.getDateDaySpace(start, ToolDateTime.getDate());
				
				// 4. 验证数据有效性
				if (ips.equals(newIp) &&  userAgent.equals(newUserAgent)) {
					return userIds;
				}
				
			}catch(Exception e){
				return null;
			}
			
		}

		return null;
	}
	
	
	
	
	/**
	 * 设置当前App登录用户
	 * @param request
	 * @param response
	 * @param user
	 * @param autoLogin
	 */
	public static void setAppSubscriberToken(HttpServletRequest request, HttpServletResponse response, Subscriber user) {
		try {
		
			if(user==null || user.getId()==null){
				addCookie(response,  "", "/", true, "app_subscriber_token", null, 0);
				return;
			}

			// 3.生成登陆认证cookie
			String userIds = user.getId();
//			String ips = getIpAddr(request);
			
			long date = ToolDateTime.getDateByTime();
			StringBuilder token = new StringBuilder();// 时间戳#USERID#USER_IP#USER_AGENT
//			token.append(date).append(".#.").append(userIds).append(".#.").append(ips);
			token.append(date).append(".#.").append(userIds);
			
			addCookie(response,  "", "/", true, "app_subscriber_token", desHelper.getEncryptStr(token.toString()), 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	

	/**
	 * 取得当前App登录用户
	 * @param request
	 * @param response
	 * @param user
	 * @param autoLogin
	 */
	public static String getAppSubscriberToken(HttpServletRequest request) {
		String loginCookie = getCookieValueByName(request, "app_subscriber_token");
		if (null != loginCookie && !loginCookie.equals("")) {
			try{
				
				String data =desHelper.decrypt(loginCookie);
				String[] datas = data.split(".#.");	//arr[0]：时间戳，arr[1]：USERID，arr[2]：USER_IP， arr[3]：USER_AGENT
				
				// 3. 获取数据
				long loginDateTimes = Long.parseLong(datas[0]);// 时间戳
				String userIds = datas[1];// 用户id
				
				return userIds;
				//String ips = datas[2];// ip地址
				
			//	String newIp = getIpAddr(request);
			
			/*	Date start = ToolDateTime.getDate();
				start.setTime(loginDateTimes);
*/
				// 4. 验证数据有效性
			/*	if (ips.equals(newIp)) {
					return userIds;
				}*/
				
			}catch(Exception e){
				return null;
			}
			
		}

		return null;
	}
	
	
}
