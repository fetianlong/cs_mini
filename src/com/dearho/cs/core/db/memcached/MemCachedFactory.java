package com.dearho.cs.core.db.memcached;

import java.util.Date;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class MemCachedFactory {
	private static MemCachedClient mcc = new MemCachedClient();

	static {
		MemcachedConfiguration mc = MemcachedConfiguration.getInstance();
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(mc.getServers());
		pool.setWeights(mc.getWeights());
		pool.setInitConn(mc.getInitConn().intValue());
		pool.setMinConn(mc.getMinConn().intValue());
		pool.setMaxConn(mc.getMaxConn().intValue());
		pool.setMaxIdle(mc.getMaxIdle());
		pool.setMaintSleep(mc.getMaintSleep());
		pool.setNagle(mc.getNagle());
		pool.setSocketTO(mc.getSocketTO().intValue());
		pool.setSocketConnectTO(mc.getSocketConnectTO().intValue());
		pool.initialize();

		mcc.setPrimitiveAsString(mc.getPrimitiveAsString());
	}

	public static MemCachedClient INS() {
		return mcc;
	}

	public static boolean set(String key, Object value) {
		return mcc.set(key, value);
	}

	public static boolean set(String key, Object value, Date date) {
		return mcc.set(key, value, date);
	}

	public static boolean set(String key, Object value, Integer size) {
		return mcc.set(key, value, size);
	}

	public static Object get(String key) {
		
		
		//return mcc.get(key);
		return null;
	}

	public static Object delete(String key) {
		return Boolean.valueOf(mcc.delete(key));
	}
}