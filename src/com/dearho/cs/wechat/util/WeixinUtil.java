package com.dearho.cs.wechat.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dearho.cs.wechat.pojo.Menu;
import com.dearho.cs.wechat.pojo.WechatTokenInfo;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.pojo.WeixinOauth2Token;
import com.dearho.cs.wechat.service.WechatTokenInfoService;
import com.swetake.util.Qrcode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 微信公众平台通用接口工具类
 * 
 * @author wangjing
 * 
 */
public class WeixinUtil {

	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	//主账号
	public static final String appId = "wx19b57f86af33ef10";
	public static final String appSecret = "bcb402bb352589afb85e9d09cf375dda";
	public static final String hostUrlString = "http://www.dearho.com/cs_mini";
	
	
	//测试号
//	public static final String appId = "wxb649600235a2d869";
//	public static final String appSecret = "424bc2f984f6622b942157cb65384321";
//	public static final String hostUrlString = "http://123.57.37.220/cs";
	
	

	
	public static WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();  
	
	private static WechatTokenInfoService wechatTokenInfoService;
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
	 */
	public static JSONObject httpsRequest(String requestUrl,
			String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			//  创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tmManagers = { new DearhoX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tmManagers, new java.security.SecureRandom());
			//  从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpsUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpsUrlConn.setSSLSocketFactory(ssf);
			httpsUrlConn.setDoOutput(true);
			httpsUrlConn.setDoInput(true);
			httpsUrlConn.setUseCaches(false);

			//  设置请求方式（GET/POST）
			httpsUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				httpsUrlConn.connect();
			}

			//  当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpsUrlConn.getOutputStream();

				//  注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			//  将返回的输入流转换成字符串
			InputStream inputStream = httpsUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();

			// 释放资源
			inputStream.close();
			inputStream = null;
			httpsUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 向微信发起https请求获取jsapi_ticket
	 * @return
	 */
	public static JSONObject requestJsapiTicket(){
		WechatTokenInfo tokenInfo = getAccessToken();
		String access_token = tokenInfo.getWxValue();
		
		String jsapi_ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
		String requestUrl = jsapi_ticket_url.replace("ACCESS_TOKEN", access_token);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		return jsonObject;
	}
	
	/**
	 * 获取jsapi_ticket
	 * @return
	 */
	public static WechatTokenInfo getJsapiTicket(){
		wechatTokenInfoService = (WechatTokenInfoService)webApplicationContext.getBean("wechatTokenInfoService");
		
		//先到数据库查看是否已经有access_token
		WechatTokenInfo jsapiTicketInfo = wechatTokenInfoService.getAccessToken("jsapi_ticket");
		//如果没有，发请求
		if(jsapiTicketInfo == null){
			JSONObject jsonObject = requestJsapiTicket();
			if (null != jsonObject) {
				jsapiTicketInfo = new WechatTokenInfo();
				jsapiTicketInfo.setWxKey("jsapi_ticket");
				jsapiTicketInfo.setWxValue(jsonObject.getString("ticket"));
				long currentTimeLong = System.currentTimeMillis();
				jsapiTicketInfo.setCreateTime(new Date(currentTimeLong));
				Date deadTime = new Date(currentTimeLong + jsonObject.getInt("expires_in")*1000);
				jsapiTicketInfo.setDeadTime(deadTime);
				wechatTokenInfoService.addAccessToken(jsapiTicketInfo);//存储token
			}
		}else{
			Date dadeLineTime = jsapiTicketInfo.getDeadTime();//获取截止时间
			Date currentTime = new Date();//当前时间
			Calendar c1 = Calendar.getInstance();  
			Calendar c2 = Calendar.getInstance();  
		    c1.setTime(dadeLineTime);  
		    c2.setTime(currentTime);  
		    
		    int result = c2.compareTo(c1);  
		    //不在有效期内，需要重新获取
		    if (result >= 0){
		    	JSONObject jsonObject = requestJsapiTicket();
				if (null != jsonObject) {
					jsapiTicketInfo.setWxValue(jsonObject.getString("ticket"));
					long currentTimeLong = System.currentTimeMillis();
					jsapiTicketInfo.setCreateTime(new Date(currentTimeLong));
					Date deadTime = new Date(currentTimeLong + jsonObject.getInt("expires_in")*1000);
					jsapiTicketInfo.setDeadTime(deadTime);
					wechatTokenInfoService.updateAccessToken(jsapiTicketInfo);//存储token
				}
		    }
		}
		
		return jsapiTicketInfo;
	}
	
	/**
	 * 向微信发起https请求获取access_token
	 * @return
	 */
	public static JSONObject requestToken(){
		String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
		
		String requestUrl = access_token_url.replace("APPID", appId).replace(
				"APPSECRET", appSecret);
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		System.out.println("requestToken"+jsonObject);
		return jsonObject;
	}
	
	/**
	 * 得到access_token
	 * 
	 * @return
	 */
	public static WechatTokenInfo getAccessToken() {
		wechatTokenInfoService = (WechatTokenInfoService)webApplicationContext.getBean("wechatTokenInfoService");
		
		//先到数据库查看是否已经有access_token
		WechatTokenInfo tokenInfo = wechatTokenInfoService.getAccessToken("access_token");
		//如果没有，发请求
		if(tokenInfo == null){
			JSONObject jsonObject = requestToken();
			// 如果成功
			if (null != jsonObject) {
				tokenInfo = new WechatTokenInfo();
				System.out.println("tokenInfo"+tokenInfo);
				tokenInfo.setWxKey("access_token");
				tokenInfo.setWxValue(jsonObject.getString("access_token"));
				long currentTimeLong = System.currentTimeMillis();
				tokenInfo.setCreateTime(new Date(currentTimeLong));
				Date deadTime = new Date(currentTimeLong + jsonObject.getInt("expires_in")*1000);
				tokenInfo.setDeadTime(deadTime);
				wechatTokenInfoService.addAccessToken(tokenInfo);//存储token
			}
		}else{
			Date dadeLineTime = tokenInfo.getDeadTime();//获取截止时间
			Date currentTime = new Date();//当前时间
			Calendar c1 = Calendar.getInstance();  
			Calendar c2 = Calendar.getInstance();  
		    c1.setTime(dadeLineTime);  
		    c2.setTime(currentTime);  
		    
		    int result = c2.compareTo(c1);  
		    //不在有效期内，需要重新获取
		    if (result >= 0){
		    	JSONObject jsonObject = requestToken();
				// 如果成功
				if (null != jsonObject) {
					tokenInfo.setWxValue(jsonObject.getString("access_token"));
					long currentTimeLong = System.currentTimeMillis();
					tokenInfo.setCreateTime(new Date(currentTimeLong));
					Date deadTime = new Date(currentTimeLong + jsonObject.getInt("expires_in")*1000);
					tokenInfo.setDeadTime(deadTime);
					wechatTokenInfoService.updateAccessToken(tokenInfo);//存储token
				}
		    }
		    	
		}
		return tokenInfo;
	}

	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

		int result = 0;

		//  拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);

		//  将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();

		// 调用接口创建菜单
		JSONObject jsonObject = httpsRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
			}
		}
		return result;
	}
	
	/**
	 * 请求网页授权凭证
	 * @param response 
	 */
	public static void requestOauth2Code(HttpServletResponse response, String scope) {
		// 拼接请求地址
		String  getCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		if("snsapi_base".equals(scope)){
			getCodeRequest = getCodeRequest.replace("APPID", appId)
					.replace("REDIRECT_URI", urlEncodeUTF8(hostUrlString+"/baseOAuthServlet")).replace("SCOPE", scope);
		}else{
			getCodeRequest = getCodeRequest.replace("APPID", appId)
					.replace("REDIRECT_URI", urlEncodeUTF8(hostUrlString+"/oauthServlet")).replace("SCOPE", scope);
		}
		try {
			response.sendRedirect(getCodeRequest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 请求网页授权凭证
	 * @param response 
	 */
	public static void requestOauth2Code(HttpServletResponse response, String scope,String redirectURI) {
		// 拼接请求地址
		String  getCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		
		getCodeRequest = getCodeRequest.replace("APPID", appId)
					.replace("REDIRECT_URI", urlEncodeUTF8(hostUrlString+redirectURI)).replace("SCOPE", scope);
		
		try {
			response.sendRedirect(getCodeRequest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取网页授权凭证
	 * 
	 * @param appId
	 *            公众账号的唯一标识
	 * @param appSecret
	 *            公众账号的密钥
	 * @param code
	 * @return WeixinAouth2Token
	 */
	public static WeixinOauth2Token getOauth2AccessToken(String code) {
		WeixinOauth2Token wat = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", appId);
		requestUrl = requestUrl.replace("SECRET", appSecret);
		requestUrl = requestUrl.replace("CODE", code);
		// 获取网页授权凭证
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
		System.out.println(jsonObject.toString());
		if (null != jsonObject) {
			try {
				wat = new WeixinOauth2Token();
				wat.setAccessToken(jsonObject.getString("access_token"));
				wat.setExpiresIn(jsonObject.getInt("expires_in"));
				wat.setRefreshToken(jsonObject.getString("refresh_token"));
				wat.setOpenId(jsonObject.getString("openid"));
				wat.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wat = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取网页授权凭证失败 errcode:{} errmsg:{}", errorCode,
						errorMsg);
			}
		}
		return wat;
	}

	/**
	 * 通过网页授权获取用户信息
	 * 
	 * @param accessToken 网页授权接口调用凭证
	 * @param openId 用户标识
	 * @return WechatUserInfo
	 */	
	@SuppressWarnings("deprecation")
	public static WechatUserInfo getWechatUserInfo(WeixinOauth2Token weixinOauth2Token) {
		WechatUserInfo snsUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", weixinOauth2Token.getAccessToken()).replace("OPENID", weixinOauth2Token.getOpenId());
		// 通过网页授权获取用户信息
		JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			System.out.println(jsonObject);
			try {
				snsUserInfo = new WechatUserInfo();
				// 用户的标识
				snsUserInfo.setOpenId(jsonObject.getString("openid"));
				// 昵称
				snsUserInfo.setNickname(jsonObject.getString("nickname"));
				// 性别（1是男性，2是女性，0是未知）
				snsUserInfo.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				snsUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				snsUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				snsUserInfo.setCity(jsonObject.getString("city"));
				// 用户头像
				snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				// 用户特权信息
				snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
				
				snsUserInfo.setCreateTime(new Date());
				
				try {
					snsUserInfo.setUnionId(jsonObject.getString("unionid"));
				} catch (Exception e) {
					log.error("unionId"+e.getMessage());
				}
				
				// 用户特权信息
			} catch (Exception e) {
				snsUserInfo = null;
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
			}
		}
		return snsUserInfo;
	}
	
	/**
	 * URL编码（utf-8）
	 * 
	 * @param source
	 * @return
	 */
	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 生成二维码
	 * @param param 二维码里面的参数
	 * @param savePath 保存的路径
	 * @throws Exception
	 */
	public static void creatQRCode(String param, String savePath) throws Exception {
		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeErrorCorrect('M');
		qrcode.setQrcodeEncodeMode('B');
		qrcode.setQrcodeVersion(7);
		byte[] bstr = param.getBytes("UTF-8");
		
		BufferedImage bi = new BufferedImage(139, 139,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g = bi.createGraphics();
		g.setBackground(Color.WHITE); // 背景颜色
		g.clearRect(0, 0, 139, 139);
		g.setColor(Color.BLACK); // 条码颜色

		if (bstr.length > 0 && bstr.length < 123) {
			boolean[][] b = qrcode.calQrcode(bstr);
			for (int i = 0; i < b.length; i++) {
				for (int j = 0; j < b.length; j++) {
					if (b[j][i]) {
						g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
					}
				}
			}
		}
		g.dispose();
		bi.flush();
		File f = new File(savePath);
		ImageIO.write(bi, "jpg", f);
	}
	
	
	public static void main(String[] args) throws Exception {
		creatQRCode( "www.baidu.com",  "d:4.jpg");
	}
	public static Map<String, String> sign(String jsapi_ticket, String url) {
		Map<String, String> ret = new HashMap<String, String>();
		String nonce_str = create_nonce_str();
		String timestamp = create_timestamp();
		String string1;
		String signature = "";

		// 注意这里参数名必须全部小写，且必须有序
		string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonce_str
				+ "&timestamp=" + timestamp + "&url=" + url;
//		System.out.println(string1);
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		ret.put("url", url);
		ret.put("jsapi_ticket", jsapi_ticket);
		ret.put("nonceStr", nonce_str);
		ret.put("timestamp", timestamp);
		ret.put("signature", signature);

		return ret;
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	private static String create_nonce_str() {
		return UUID.randomUUID().toString();
	}

	private static String create_timestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * 合成签名所需的URL
	 * @param httpServletRequest
	 * @return
	 */
	public static String getUrlForSignature(HttpServletRequest httpServletRequest) {
		String headUrl = "http://"+httpServletRequest.getHeader("Host") + httpServletRequest.getRequestURI();
		StringBuffer url = new StringBuffer();
		url.append(headUrl);
		
		if(httpServletRequest.getParameterMap()!=null){
			Enumeration enu = httpServletRequest.getParameterNames();  
			int count = 0;
			while(enu.hasMoreElements()){  
				count++;
				String paraName=(String)enu.nextElement();  
				if(count == 1){
					url.append("?").append(paraName).append("=").append(httpServletRequest.getParameter(paraName));
				}else{
					url.append("&").append(paraName).append("=").append(httpServletRequest.getParameter(paraName));
				}
			} 
		}
		return url.toString();
	}

}
