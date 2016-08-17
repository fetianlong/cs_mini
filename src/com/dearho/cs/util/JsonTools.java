package com.dearho.cs.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonTools {
	
	/**
	* @Title: jsonForMap 
	* @Description: jsonForMap LH
	*/
	public static Map<String, String> jsonForMap(String jsonStr) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Map<String, String> result = new HashMap<String, String>();
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);

		}
		return result;
	}

	public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}

	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// 如果内层还是数组的话，继续解析
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	public static List<Map<String, Object>> getListByUrl(String url) {
		try {
			// 通过HTTP获取JSON数据
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return parseJSON2List(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, Object> getMapByUrl(String url) {
		try {
			// 通过HTTP获取JSON数据
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return parseJSON2Map(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "deprecation" })
	public static Object fromJsonToJava(JSONObject json, Class<?> pojo) throws Exception {
		// 首先得到pojo所定义的字段
		Field[] fields = pojo.getDeclaredFields();
		// 根据传入的Class动态生成pojo对象
		Object obj = pojo.newInstance();
		for (Field field : fields) {
			// 设置字段可访问（必须，否则报错）
			field.setAccessible(true);
			// 得到字段的属性名
			String name = field.getName();
			// 这一段的作用是如果字段在JSONObject中不存在会抛出异常，如果出异常，则跳过。
			try {
				json.get(name);
			} catch (Exception ex) {
				continue;
			}
			if (json.get(name) != null && !"".equals(json.getString(name))) {
				// 根据字段的类型将值转化为相应的类型，并设置到生成的对象中。
				if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
					field.set(obj, Long.parseLong(json.getString(name)));
				} else if (field.getType().equals(String.class)) {
					field.set(obj, json.getString(name));
				} else if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
					field.set(obj, Double.parseDouble(json.getString(name)));
				} else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
					field.set(obj, Integer.parseInt(json.getString(name)));
				} else if (field.getType().equals(java.util.Date.class)) {
					field.set(obj, Date.parse(json.getString(name)));
				} else {
					continue;
				}
			}
		}
		return obj;
	}

	
	public static Map<String, String> desjsonForMap(String jsonStr) throws Exception {
        CryptoTools des = new CryptoTools("HeGeA8G3".getBytes(),"6LA2EyQm".getBytes());//自定义密钥
        jsonStr = des.decode(jsonStr);
		
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Map<String, String> result = new HashMap<String, String>();
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (iterator.hasNext()) {
			key = (String) iterator.next();
			value = jsonObject.getString(key);
			result.put(key, value);

		}
		return result;
	}
	
	// test
	public static void main(String[] args) {
		/*
		 * String url = "http://..."; List<Map<String,Object>> list =
		 * getListByUrl(url);
		 */
		
			long startTime = System.currentTimeMillis();//获取当前时间
			
			String jsonStr = "{'userName':'qqq','userPassword':'qqqq'}";
			System.out.println(jsonForMap(jsonStr));
		
			long endTime = System.currentTimeMillis();
			System.out.println("程序运行时间： "+(endTime-startTime)+"ms"); 
		
	}
}
