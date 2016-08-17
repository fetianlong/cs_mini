package com.dearho.cs.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.dearho.cs.sys.pojo.User;
import com.hp.hpl.sparta.Element;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class Ajax
{
  public static String xmlHead()
  {
    return "<?xml version=\"1.0\" encoding=\"utf-8\"?><data>";
  }
  public static String wsHead() {
    return "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
  }
  public static String xmlResult(int code, String description) {
    return xmlResult(code, description, "");
  }
  public static String xmlResults(int code, String description) {
    return xmlResults(code, description, "");
  }
  public static String xmlResult(int code, String description, String content) {
    StringBuffer sb = new StringBuffer();
    sb.append(xmlHead());
    sb.append("<code>" + code + "</code>");
    sb.append("<desc><![CDATA[" + description + "]]></desc>");
    sb.append(content);
    sb.append(xmlFoot());
    return sb.toString();
  }
  public static String xmlResults(int code, String description, String content) {
    StringBuffer sb = new StringBuffer();
    sb.append(xmlHead());
    sb.append("<code>" + code + "</code>");
    sb.append("<desc>" + description + "</desc>");
    sb.append(content);
    sb.append(xmlFoot());
    return sb.toString();
  }
  public static String toJson(Object obj) {
    JsonConfig cfg = new JsonConfig();
    cfg.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
      public Object processObjectValue(String key, Object value, JsonConfig arg2) {
        if (value == null)
          return "";
        if ((value instanceof Date)) {
          return Long.valueOf(((Date)value).getTime());
        }
        return value.toString();
      }
      public Object processArrayValue(Object value, JsonConfig arg1) {
        return null;
      }
    });
    return JSONObject.fromObject(obj, cfg).toString();
  }
  public static String toJsonFromObject(Object obj){
	  JSONArray json = JSONArray.fromObject(obj);
	  return json.toString();
  }
  
  public static String toJson(int code, String description) {
    return toJson(code, description, null);
  }

  public static String toJson(int code, String description, Object obj)
  {
    return toJson(obj);
  }
  public static String JSONResult(int code, String description) {
    return JSONResult(code, description, null);
  }
  public static String JSONResult(int code, String description, Object info) {
    return new JSONObject().element("result", code).element("msg", description).element("info", info).toString();
  }
  public static String JSONResultMblog(int code, String description, Object obj) {
    StringBuffer sf = new StringBuffer();
    sf.append("{");
    if (obj != null) {
      sf.append("\"info\":{\"talk\":");
      sf.append(JSONArray.fromObject(obj));
      sf.append("},");
    }
    sf.append("\"result\":" + code + ",");
    sf.append("\"msg\":\"" + description + "\"");
    sf.append("}");
    return sf.toString();
  }
  public static String xmlFoot() {
    return "</data>";
  }
  public static String getTagWhere(String funName, String tag) {
    tag = tag.trim();
    if (StringHelper.isEmpty(tag.trim()))
      return "";
    tag = StringHelper.replaceString(tag, "，", ",");
    String[] a = tag.split(",");
    StringBuffer sb = new StringBuffer();
    sb.append(" and ( 1=1 ");
    String str = "";
    for (int i = 0; i < a.length; i++) {
      str = a[i].trim();
      if (StringHelper.isNotEmpty(str))
        sb.append(" and " + funName + " like '%" + str + "%'");
    }
    sb.append(") ");
    return sb.toString();
  }
  public static String getTagOrWhere(String funName, String tag) {
    tag = tag.trim();
    if (StringHelper.isEmpty(tag.trim()))
      return "";
    tag = StringHelper.replaceString(tag, "，", ",");
    String[] a = tag.split(",");
    StringBuffer sb = new StringBuffer();
    if (a.length > 0) {
      sb.append(" and ( ");
      String str = "";
      for (int i = 0; i < a.length; i++) {
        str = a[i].trim();
        if (StringHelper.isNotEmpty(str)) {
          if (i > 0)
            sb.append(" or ");
          sb.append(funName + " like '%" + str + "%'");
        }
      }
      sb.append(") ");
    }
    return sb.toString();
  }
  public static String getTagOrString(String funName, String tag) {
    tag = tag.trim();
    if (StringHelper.isEmpty(tag.trim()))
      return "";
    tag = StringHelper.replaceString(tag, "，", ",");
    String[] a = tag.split(",");
    StringBuffer sb = new StringBuffer();
    if (a.length > 0) {
      sb.append(" ( ");
      String str = "";
      for (int i = 0; i < a.length; i++) {
        str = a[i].trim();
        if (StringHelper.isNotEmpty(str)) {
          if (i > 0)
            sb.append(" or ");
          sb.append(funName + " like '%" + str + "%'");
        }
      }
      sb.append(") ");
    }
    return sb.toString();
  }
  
 
    /**
     * 业务错误返回
     * @param resultCode
     * @param resultMsg
     * @return
     */
    public static String AppJsonResult(int resultCode,String resultMsg) {
    	 return new JSONObject().element("resultCode", resultCode).element("resultMsg", resultMsg).toString();
    }
   public static String AppJsonResult(int resultCode,String resultMsg,String token) {
   	 return new JSONObject().element("resultCode", resultCode).element("resultMsg", resultMsg).element("token", token).toString();
   }
    /**
     * 正确返回
     * @param resultCode
     * @param resultValue
     * @return
     */
    public static String AppJsonResult(int resultCode,Object resultValue) {
    	String valueStr = toJsonFromObject(resultValue);
//    	DESEncryptHelper desHelper = new DESEncryptHelper(Constants.APP_DES_KEY);
    	try {
//			valueStr = desHelper.getEncryptStr(valueStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new JSONObject().element("resultCode", resultCode).element("resultValue", valueStr).toString();
    }
    /**
     * json 返回
     * @param resultCode
     * @param resultMsg
     * @param data
     * @return
     */
    public static String AppbzJsonResult(int resultCode,String resultMsg,Object data) {
    	String valueStr = toJsonFromObject(data);
    	return new JSONObject().element("resultCode", resultCode).element("resultMsg",resultMsg).element("data", valueStr).toString();
    }
    public static String AppResult(int resultCode,String resultMsg,Object data) {
    	String valueStr = JSONObject.fromObject(data).toString();
    	return new JSONObject().element("resultCode", resultCode).element("resultMsg",resultMsg).element("data", valueStr).toString();
    }
    
    public static void main(String[] args) {
    	User us = new User();
    	us.setId("23131");
    	us.setLoginName("高云鹏");
		String s = AppJsonResult(0, us);
		System.out.println(s);
	}
}