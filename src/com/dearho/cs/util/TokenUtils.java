package com.dearho.cs.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.dearho.cs.appinterface.sys.pojo.AppToken;
import com.dearho.cs.core.db.memcached.MemCachedFactory;
import com.dearho.cs.subscriber.pojo.Subscriber;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * 
* @ClassName: TokenUtils 
* @Description: Token 工具类
* @author LH
* @date 2016年3月9日 下午4:24:15 
*
 */
public class TokenUtils {
	
	/**
	 * @Title: getTokenEncode 
	* @Description: 加密 规则:当前时间戳MD5的16加密 加 code的32位加密
	* @param @param time
	* @param @param code
	* @return String
	 */
	public static String getTokenEncode(String time,String code){
		String md5code = Md5Util.MD5Encode(code);
		String md5time = Md5Util.MD5Encode(time).toString().substring(8, 24);
		BASE64Encoder base64Encoder = new BASE64Encoder();
		return base64Encoder.encode((md5time+md5code).getBytes());
	}
	public static Map<String, String> getTokenDecoder(String code) {
		Map<String, String> map = new HashMap<String,String>();
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] decodeBuffer;
		try {
			decodeBuffer = decoder.decodeBuffer(code);
			String md5Token = new String(decodeBuffer);
			map.put("md5Time", md5Token.substring(0, 16));
			map.put("md5Code", md5Token.substring(16, md5Token.length()));
		} catch (Exception e) {
			map=null;
		}
		return map;
	}
	
	/**
	 * 获取会员信息
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static Subscriber getSubscriber(String data) throws Exception{
		Subscriber subscriber = null;
		Map<String, String> map = JsonTools.desjsonForMap(data);
		String token = map.get("token");
		Map<String, String> toktenmap = TokenUtils.getTokenDecoder(token);
		if(toktenmap!=null){
			subscriber = (Subscriber) MemCachedFactory.get(toktenmap.get("md5Code"));
		}
		return subscriber;
	}
	/**
	 * 获取token
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String getToken(String data) throws Exception{
		String token = null;
		Map<String, String> map = JsonTools.desjsonForMap(data);
		String apptoken = map.get("token");
		Map<String, String> toktenmap = TokenUtils.getTokenDecoder(apptoken);
		if(toktenmap!=null){
			token = (String) MemCachedFactory.get(toktenmap.get("md5Code")+"token");
		}
		return token;
	}
	
}
