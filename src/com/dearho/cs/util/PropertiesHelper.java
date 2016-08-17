package com.dearho.cs.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public final class PropertiesHelper {
	
	
	private static final Logger logger = Logger.getLogger(PropertiesHelper.class);
    
    private static Properties config = new Properties();
     
    static {
        try {
            config.load(PropertiesHelper.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (Exception e) {
            logger.error("app.properties 文件加载失败!");
        }
    }
     
    public static String getValue(String key) {
        return config.getProperty(key);
    }
     
    public static void setValue(String key, String value) {
    	config.setProperty(key, value);
        try {
            FileOutputStream out = new FileOutputStream(new File("app.properties"));
            config.store(out, "This is Configuration File");
        } catch (FileNotFoundException e) {
            logger.info("保存系统配置时未找到app.properties文件");
        } catch (IOException e) {
            logger.info("保存系统配置时IO异常");
        }
    }
    public Properties getProperties(){
    	return config;
    }
    
    public static void main(String[] args) {
    	System.out.println(getValue("s1"));
	}
	
	
	public static boolean getBoolean(String property, Properties properties) {
		return Boolean.valueOf(properties.getProperty(property)).booleanValue();
	}

	public static boolean getBoolean(String property, Properties properties,
			boolean defaultValue) {
		String setting = properties.getProperty(property);
		return setting == null ? defaultValue : Boolean.valueOf(setting)
				.booleanValue();
	}

	public static int getInt(String property, Properties properties,
			int defaultValue) {
		String propValue = properties.getProperty(property);
		return propValue == null ? defaultValue : Integer.parseInt(propValue);
	}

	public static long getLong(String property, Properties properties,
			long defaultValue) {
		String propValue = properties.getProperty(property);
		return propValue == null ? defaultValue : Long.parseLong(propValue);
	}

	public static String getString(String property, Properties properties,
			String defaultValue) {
		String propValue = properties.getProperty(property);
		return propValue == null ? defaultValue : propValue;
	}

	public static Integer getInteger(String property, Properties properties) {
		String propValue = properties.getProperty(property);
		return propValue == null ? null : Integer.valueOf(propValue);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map toMap(String property, String delim, Properties properties) {
		Map map = new HashMap();
		String propValue = properties.getProperty(property);
		if (propValue != null) {
			StringTokenizer tokens = new StringTokenizer(propValue, delim);
			while (tokens.hasMoreTokens()) {
				map.put(tokens.nextToken(),
						tokens.hasMoreElements() ? tokens.nextToken() : "");
			}
		}
		return map;
	}

	public static String[] toStringArray(String property, String delim,
			Properties properties) {
		return toStringArray(properties.getProperty(property), delim);
	}

	public static String[] toStringArray(String propValue, String delim) {
		if (propValue != null) {
			return StringHelper.split(delim, propValue);
		}
		return null;
	}

	public static Properties maskOut(Properties props, String key) {
		Properties clone = (Properties) props.clone();
		if (clone.get(key) != null) {
			clone.setProperty(key, "****");
		}
		return clone;
	}
}