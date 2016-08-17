package com.dearho.cs.teldintrf.util;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import com.dearho.cs.teldintrf.entity.RequestMsgHeader;
import com.dearho.cs.teldintrf.entity.TeldIntrfToken;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.CryptoTools;
import com.dearho.cs.util.HttpRequestUtil;
import com.dearho.cs.util.JsonTools;
import com.dearho.cs.util.Md5Util;
import com.dearho.cs.util.StringHelper;

public class TeldInterface {
	
	private static TeldIntrfToken teldIntrfToken;
	
	private static final String tokenUrl = "http://api.wyqcd.cn:8004/OAuth/Token";
	private static final String stationUrl = "http://api.wyqcd.cn:8004/api/Sta/PostSta";
	private static final String detailUrl = "http://api.wyqcd.cn:8004/api/Sta/PostStaDetail";
	private static final String pileUrl = "http://api.wyqcd.cn:8004/api/Sta/PostPileDetail";
	
	private static final String grantType = "client_credentials";
	private static final String clientId = "teldtub1y1pdffh3svod";
	private static final String clientSecret = "qXid3bLHOi";
	private static final String key = "MLJwU36cz5Iz1UYcFb1Fc4hSEgPWhrhI";
	public static final String desIV = "g9A6eELT";
	public static final String desKey = "GTvn6aEw";
	
	public static TeldIntrfToken getTeldIntrfToken() {
		if(teldIntrfToken == null || teldIntrfToken.getOutTime() == null ||
				new Date().compareTo(teldIntrfToken.getOutTime()) > 0){
			teldIntrfToken = new TeldIntrfToken();
			String tokenStr = OAuthToken();
			if(tokenStr != null){
				JSONObject jsonObject = JSONObject.fromObject(tokenStr);
				teldIntrfToken.setAccessToken(String.valueOf(jsonObject.get("access_token")));
				teldIntrfToken.setTokenType(String.valueOf(jsonObject.get("token_type")));
				teldIntrfToken.setExpiresIn(Integer.parseInt(String.valueOf(jsonObject.get("expires_in"))));
				teldIntrfToken.setOutTime(new Date(new Date().getTime() + teldIntrfToken.getExpiresIn()*100));
			}
			
		}
		return teldIntrfToken;
	}
	
	private static String OAuthToken(){
		String returnToken = HttpRequestUtil.sendPost(tokenUrl, null,
				"grant_type="+grantType+"&client_id="+clientId+"&client_secret="+clientSecret);
		if(returnToken != null && returnToken.contains("access_token")){
			return returnToken;
		}
		return null;
	}
	
	public static String getStations(){
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("province","北京");
//		requestMap.put("city", "北京");
//		requestMap.put("region", "大兴区");
//		requestMap.put("type", "公共站");
//		requestMap.put("opState", "运营中");
//		requestMap.put("pageNo",null);
//		requestMap.put("pageSize", null);
		return queryInterface(requestMap,stationUrl);
	}
	
	
	public static void main(String[] args) {
//		String tokenJson = TeldInterface.OAuthToken();
//		System.out.println(tokenJson);
//		String str = "0lWv0MQV9f5qv2W3J5mWcdxWzzMKl6mTRWkHQUpX2HK7MLAXusF0RV0tRiQaoy8UWq7ErGB2dNC00DIEkNJElxj4pKX0CIZqpphMc62Yda1KtsjgdIMap7A4dfce39OCiV9E0VxYqpD2AfEQzZWr0XaHkZJ7NCY7_jhyArGP4GfM_GqBzT1f2UrTpm7PoXyyD36ouKsawcNJjHpJXZ8LsO_77o9IIYdGVdSKFcmt_pRjgwKL";
//		System.out.println(str.length());
//		TeldIntrfToken tokenJson = TeldInterface.getTeldIntrfToken();
//		System.out.println(tokenJson.toString());
		String str = TeldInterface.getPileInfo("1101120231107");
		System.out.println(str);
	}

	public static String getStationDetail(String staCode) {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("staCode",staCode);
		return queryInterface(requestMap,detailUrl);
	}
	public static String getPileInfo(String pileCode) {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("pileCode",pileCode);
		return queryInterface(requestMap,pileUrl);
	}
	
	
	private static String queryInterface(Map<String, String> requestMap,String url){
		RequestMsgHeader rmHeader = new RequestMsgHeader();
		rmHeader.setRequestMsg(requestMap);
		System.out.println("requestMsg = "+Ajax.toJson(requestMap));
		Map<String, String> sortedMap = new TreeMap<String, String>(new Comparator<String>() {  
	        public int compare(String key1, String key2) {  
	        	char c1 = key1.charAt(0);
	        	char c2 = key2.charAt(0);
	            return c1 - c2;  
	        }});  
		Map<String,String> signMap = new HashMap<String, String>();
		for (Entry<String, String> entry : requestMap.entrySet()) {
			if(StringHelper.isNotEmpty(entry.getValue())){
				signMap.put(entry.getKey(), entry.getValue());
			}
		}
	    sortedMap.putAll(signMap);  
	    String md5Msg = "requestMsg="+Ajax.toJson(sortedMap)+key;
//	    String md5Msg = "requestMsg={\"province\":\"北京\"}"+key;
		rmHeader.setSign(Md5Util.MD5Encode(md5Msg).toLowerCase());
		Map<String,String> headerMap = new HashMap<String,String>();
		String token = TeldInterface.getTeldIntrfToken().getAccessToken();
		headerMap.put("Authorization", "Bearer "+token);
		String result = "";
//		rmHeader.setEncrypt("noencrypt");
		try {
			CryptoTools des = new CryptoTools(desIV.getBytes(),desKey.getBytes());//自定义密钥
//			String s = "{\"province\":\"北京\"}";
//			System.out.println("DES加密前数据："+Ajax.toJson(s));
			String m = "{\"requestMsg\":\""+des.encode(Ajax.toJson(requestMap))+"\",\"sign\":\""+rmHeader.getSign()+"\"}";
			result = HttpRequestUtil.sendPost(url, headerMap, m);
		} catch (Exception e) {
			
		}
		System.out.println(result);
		if(StringHelper.isEmpty(result)){
			return "";
		}
		Map<String, Object> stationInfoMap = JsonTools.parseJSON2Map(result);
		if(0 != Integer.parseInt(String.valueOf(stationInfoMap.get("returnCode"))) 
				|| 0 != Integer.parseInt(String.valueOf(stationInfoMap.get("resultCode")))){
			System.out.println("请求失败");
			return null;
		}
		else{
			return String.valueOf(stationInfoMap.get("resultValue"));
		}
	}

	public static JSONObject desResultToJSONObject(String resultListInfoStr) {
		CryptoTools des;
		String stationJson = "";
		try {
			des = new CryptoTools(TeldInterface.desIV.getBytes(),TeldInterface.desKey.getBytes());
			stationJson = des.decode(resultListInfoStr);
		} catch (Exception e) {
			return null;
		}
		
		JSONObject jsonObject = JSONObject.fromObject(stationJson);
		return jsonObject;
	}
	
}
