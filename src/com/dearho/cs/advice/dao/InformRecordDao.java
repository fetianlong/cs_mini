package com.dearho.cs.advice.dao;

import com.dearho.cs.advice.pojo.InformRecord;

/**
 * 通知记录Dao接口
 * @author jyt
 * @since 2016年7月20日 下午2:35:39
 */
public interface InformRecordDao {

	/**
	 * 添加消息读取情况记录
	 * @param record
	 */
	void addInformRecord(InformRecord record);

	/**
	 * @param hql
	 * @return
	 */
	String queryInformRecordSubscriberName(String hql);

}
