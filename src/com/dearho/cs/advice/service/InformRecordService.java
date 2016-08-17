package com.dearho.cs.advice.service;

import com.dearho.cs.advice.pojo.InformRecord;

/**
 * @author jyt
 * @since 2016年7月21日 下午5:12:41
 */
public interface InformRecordService {

	/**
	 * 添加消息记录
	 * @param record
	 */
	void addInformRecord(InformRecord record);

	/**
	 *获取会员姓名
	 * @param id
	 * @return
	 */
	String getSubscriberNameByInformId(String id);

}
