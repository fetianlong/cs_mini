package com.dearho.cs.sys.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dearho.cs.sys.pojo.FieldComment;
import com.dearho.cs.sys.pojo.SysOperateLogRecord;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.SystemOperateLogService;

/**
 * 后台系统操作日志工具类
 * 
 * @author wangjing
 * 
 */
public class SystemOperateLogUtil {

	public static WebApplicationContext webApplicationContext = ContextLoader
			.getCurrentWebApplicationContext();

	private static SystemOperateLogService systemOperateLogService;

	public final static String ADD_OPERATION = "add";

	public final static String DELETE_OPERATION = "delete";

	public final static String UPDATE_OPERATION = "update";

	public final static String LOGIN_OPERATION = "login";
	
	public final static String MODEL_SUBSCRIBER="用户管理";
	
	public final static String MODEL_CALL="呼叫中心";
	
	public final static String MODEL_SUBSCRIBER_INFO="用户信息修改";
	
	public final static String MODEL_RECHARGE_CARD="充值卡管理";
	
	/**
	 * 新增操作日志
	 * @param dataId
	 * @param user
	 * @param functionName 模块名称
	 * @param key 关键数据名称
	 * @param value 关键数据值
	 */
	public static void sysAddOperateLog(String dataId, User user,
			String functionName, Map<String, String> contentMap) {
		systemOperateLogService = (SystemOperateLogService) webApplicationContext
				.getBean("systemOperateLogService");

		SysOperateLogRecord log = new SysOperateLogRecord();
		Date date = new Date();
		if(user!=null){
			log.setOperatorId(user.getId());
			log.setOperatorName(user.getName());
		}
		
		log.setOperateDate(date);
		log.setOperateRemark(ADD_OPERATION);
		log.setRecordId(dataId);
		log.setModelName(functionName);
		
		StringBuffer contentStr = new StringBuffer();
		Iterator<Map.Entry<String, String>> it = contentMap.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			contentStr.append(entry.getKey() + "：" + entry.getValue() + "； ");
		}
		log.setOperateContent(contentStr.toString());
		
		// 记录日志
		systemOperateLogService.addSysOperateLogRecord(log);
	}

	/**
	 * 物理删除操作记录
	 * @param object
	 * @param user 用户
	 * @param functionName 模块名称
	 */
	public static void sysDeleteOperateLog(User user, String functionName, Map<String, String> contentMap) {
		systemOperateLogService = (SystemOperateLogService) webApplicationContext
				.getBean("systemOperateLogService");

		SysOperateLogRecord log = new SysOperateLogRecord();
		Date date = new Date();
		log.setOperatorId(user.getId());
		log.setOperatorName(user.getName());
		log.setOperateDate(date);
		log.setOperateRemark(DELETE_OPERATION);
		log.setModelName(functionName);

		StringBuffer contentStr = new StringBuffer();
		Iterator<Map.Entry<String, String>> it = contentMap.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			contentStr.append(entry.getKey() + "：" + entry.getValue() + "； ");
		}
		log.setOperateContent(contentStr.toString());
		
		// 记录日志
		systemOperateLogService.addSysOperateLogRecord(log);
	}
	
	/**
	 * 逻辑删除操作记录
	 * @param object
	 * @param user 用户
	 * @param functionName 模块名称
	 */
	public static void sysDeleteOperateLog(String dataId, User user, String functionName, Map<String, String> contentMap) {
		systemOperateLogService = (SystemOperateLogService) webApplicationContext
				.getBean("systemOperateLogService");

		SysOperateLogRecord log = new SysOperateLogRecord();
		Date date = new Date();
		log.setOperatorId(user.getId());
		log.setOperatorName(user.getName());
		log.setOperateDate(date);
		log.setOperateRemark(DELETE_OPERATION);
		log.setModelName(functionName);
		log.setRecordId(dataId);
		
		StringBuffer contentStr = new StringBuffer();
		Iterator<Map.Entry<String, String>> it = contentMap.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			contentStr.append(entry.getKey() + "：" + entry.getValue() + "； ");
		}
		log.setOperateContent(contentStr.toString());
		
		// 记录日志
		systemOperateLogService.addSysOperateLogRecord(log);
	}
	
	/**
	 * 更新操作记录
	 * @param beforeObject
	 * @param afterObject
	 * @param user 用户实体
	 * @param functionName 模块名称
	 * @throws ClassNotFoundException
	 */
	public static void sysUpdateOperateLog(Object beforeObject,
			Object afterObject, User user, String functionName, String keyword)
			throws ClassNotFoundException {
		systemOperateLogService = (SystemOperateLogService) webApplicationContext
				.getBean("systemOperateLogService");

		Field[] fields = beforeObject.getClass().getDeclaredFields();

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());

		JSONObject beforeJsonObj = JSONObject.fromObject(beforeObject,
				jsonConfig);
		JSONObject afterJsonObj = JSONObject
				.fromObject(afterObject, jsonConfig);

		SysOperateLogRecord log = new SysOperateLogRecord();
		Date date = new Date();
		if(user!=null){
			log.setOperatorId(user.getId());
			log.setOperatorName(user.getName());
		}
		log.setOperateDate(date);
		log.setOperateRemark(UPDATE_OPERATION);
		log.setModelName(functionName);
		log.setKeyword(keyword);
		
		String id = beforeJsonObj.getString("id");// 得到操作 数据的ID
		log.setRecordId(id);
		String contentString = contractObject(beforeJsonObj, afterJsonObj, fields);
		log.setOperateContent(contentString);

		// 记录日志
		systemOperateLogService.addSysOperateLogRecord(log);
	}

	/**
	 * 登录日志
	 * @param user
	 */
	public static void sysLoginOperateLog(User user) {
		systemOperateLogService = (SystemOperateLogService) webApplicationContext
				.getBean("systemOperateLogService");
		SysOperateLogRecord log = new SysOperateLogRecord();
		Date date = new Date();
		log.setOperatorId(user.getId());
		log.setOperatorName(user.getName());
		log.setOperateDate(date);
		log.setOperateRemark(LOGIN_OPERATION);
		log.setModelName("登录操作");

		// 记录日志
		systemOperateLogService.addSysOperateLogRecord(log);
	}

	/**
	 * 比较两个对象，记录更改项
	 * @param beforeObject
	 * @param afterObject
	 * @param fields
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static String contractObject(JSONObject beforeObject,
			JSONObject afterObject, Field[] fields) {

		Map recordMap = new HashMap();
		StringBuffer resultStr = new StringBuffer();
		for (int i = 0; i < beforeObject.names().size(); i++) {
			if (isTheSame(beforeObject, afterObject, i)) {
				continue;
			}
			if (!isTheSame(beforeObject, afterObject, i)) {
				loadRecord(beforeObject, afterObject, fields, recordMap, i);
			}
		}

		Iterator<Map.Entry<String, String>> it = recordMap.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = it.next();
			resultStr.append(entry.getKey() + "：" + entry.getValue() + "； ");
		}
		return resultStr.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void loadRecord(JSONObject obj1, JSONObject obj2,
			Field[] fields, Map record, int i) {
		Object value1 = obj1.get(obj1.names().get(i).toString());
		Object value2 = obj2.get(obj2.names().get(i).toString());

		String feildName = obj1.names().get(i).toString();
		String fieldComment = "";
		
		for (int j = 0; j < fields.length; j++) {
			if (feildName.equals(fields[j].getName())) {
				boolean isPresent = fields[j]
						.isAnnotationPresent(FieldComment.class);
				if (isPresent) {
					// 取注解中的文字说明
					FieldComment comment = fields[j]
							.getAnnotation(FieldComment.class);
					if (comment != null) {
						fieldComment = comment.value();
						
						if (value1 instanceof BigDecimal) {
							BigDecimal beforValue = (BigDecimal) value1;
							BigDecimal afterValue = (BigDecimal) value2;
							record.put(fieldComment + "字段", "由“" + beforValue + "”修改为“"
									+ afterValue + "”");
						} else if (value1 instanceof Integer) {
							Integer beforValue = (Integer) value1;
							Integer afterValue = (Integer) value2;
							record.put(fieldComment + "字段", "由“" + beforValue + "”修改为“"
									+ afterValue + "”");
						}else if (value1 instanceof Double) {
							Double beforValue = (Double) value1;
							Double afterValue = (Double) value2;
							record.put(fieldComment + "字段", "由“" + beforValue + "”修改为“"
									+ afterValue + "”");
						}else if (value1 instanceof String){
							String beforValue = (String) value1;
							String afterValue = (String) value2;
							record.put(fieldComment + "字段", "由“" + beforValue + "”修改为“"
									+ afterValue + "”");
						}
					}
				}
			}
		}

	}
	
	private static boolean isTheSame(JSONObject obj1, JSONObject obj2, int i) {
		Object value1 = obj1.get(obj1.names().get(i));
		Object value2 = obj2.get(obj2.names().get(i));
		boolean isSame = false;
		if (value1 instanceof BigDecimal) {
			BigDecimal beforValue = (BigDecimal) value1;
			BigDecimal afterValue = (BigDecimal) value2;
			isSame = beforValue.equals(afterValue);
		} else if (value1 instanceof Integer) {
			Integer beforValue = (Integer) value1;
			Integer afterValue = (Integer) value2;
			isSame = beforValue.equals(afterValue);
		} else if (value1 instanceof Double) {
			Double beforValue = (Double) value1;
			Double afterValue = (Double) value2;
			isSame = beforValue.equals(afterValue);
		}else if (value1 instanceof String){
			String beforValue = (String) value1;
			String afterValue = (String) value2;
			isSame = beforValue.equals(afterValue);
		}
		return isSame;
	}

}
