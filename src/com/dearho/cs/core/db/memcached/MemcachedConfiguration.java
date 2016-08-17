package com.dearho.cs.core.db.memcached;

import java.io.InputStream;
import java.util.Properties;

import com.dearho.cs.util.PropertiesHelper;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class MemcachedConfiguration {
	private static MemcachedConfiguration instance;
	private String[] servers = null;
	private Integer[] weights = null;
	private Integer initConn = null;
	private Integer minConn = null;
	private Integer maxConn = null;
	private long maxIdle;
	private long maintSleep;
	private boolean nagle = false;
	private Integer socketTO = null;
	private Integer socketConnectTO = null;
	private boolean compressEnable = true;
	private long compressThreshold;
	private boolean primitiveAsString = true;

	public static synchronized MemcachedConfiguration getInstance() {
		if (instance == null) {
			instance = new MemcachedConfiguration("/memcached.properties");
		}
		return instance;
	}

	protected MemcachedConfiguration(String propertiesFile) {
//		InputStream fin = getClass().getResourceAsStream(propertiesFile);
//		Properties dbProps = new Properties();
//		try {
//			dbProps.load(fin);
//			this.servers = PropertiesHelper.getString("memcached.servers",
//					dbProps, "127.0.0.1:11211").split(",");
//			this.weights = toIntArray(PropertiesHelper.getString(
//					"memcached.port", dbProps, "1").split(","));
//			this.initConn = Integer.valueOf(PropertiesHelper.getInt(
//					"memcached.initConn", dbProps, 20));
//			this.minConn = Integer.valueOf(PropertiesHelper.getInt(
//					"memcached.minConn", dbProps, 20));
//			this.maxConn = Integer.valueOf(PropertiesHelper.getInt(
//					"memcached.maxConn", dbProps, 250));
//			this.maxIdle = PropertiesHelper.getLong("memcached.maxIdle",
//					dbProps, 360000000L);
//			this.maintSleep = PropertiesHelper.getLong("memcached.maintSleep",
//					dbProps, 3000L);
//			this.nagle = PropertiesHelper.getBoolean("memcached.nagle",
//					dbProps, false);
//			this.socketTO = Integer.valueOf(PropertiesHelper.getInt(
//					"memcached.socketTO", dbProps, 3000));
//			this.socketConnectTO = Integer.valueOf(PropertiesHelper.getInt(
//					"memcached.socketConnectTO", dbProps, 0));
//			this.compressEnable = PropertiesHelper.getBoolean(
//					"memcached.compressEnable", dbProps, true);
//			this.compressThreshold = PropertiesHelper.getLong(
//					"memcached.compressThreshold", dbProps, 20480L);
//			this.primitiveAsString = PropertiesHelper.getBoolean(
//					"memcached.primitiveAsString", dbProps, true);
//			fin.close();
//		} catch (Exception localException) {
//		}
	}

	public static Integer[] toIntArray(String[] str) {
		Integer[] num = new Integer[str.length];
		for (int i = 0; i < num.length; i++) {
			num[i] = Integer.valueOf(Integer.parseInt(str[i]));
		}
		return num;
	}

	public String[] getServers() {
		return this.servers;
	}

	public Integer[] getWeights() {
		return this.weights;
	}

	public Integer getInitConn() {
		return this.initConn;
	}

	public Integer getMinConn() {
		return this.minConn;
	}

	public Integer getMaxConn() {
		return this.maxConn;
	}

	public boolean getNagle() {
		return this.nagle;
	}

	public Integer getSocketTO() {
		return this.socketTO;
	}

	public Integer getSocketConnectTO() {
		return this.socketConnectTO;
	}

	public boolean getCompressEnable() {
		return this.compressEnable;
	}

	public boolean getPrimitiveAsString() {
		return this.primitiveAsString;
	}

	public long getMaxIdle() {
		return this.maxIdle;
	}

	public long getMaintSleep() {
		return this.maintSleep;
	}

	public long getCompressThreshold() {
		return this.compressThreshold;
	}
}