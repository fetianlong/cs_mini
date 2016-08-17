package com.dearho.cs.advice.service.impl;

import com.dearho.cs.advice.dao.InformDao;
import com.dearho.cs.advice.dao.InformRecordDao;
import com.dearho.cs.advice.pojo.InformRecord;
import com.dearho.cs.advice.service.InformRecordService;

/**
 * 通知服务层接口实现类
 * @author jyt
 * @since 2016年7月20日 下午2:00:19
 */
public class InformRecordServiceImpl implements InformRecordService {

	private InformDao informDao;

	private InformRecordDao informRecordDao;
	
	public InformDao getInformDao() {
		return informDao;
	}

	public void setInformDao(InformDao informDao) {
		this.informDao = informDao;
	}
	
	public InformRecordDao getInformRecordDao() {
		return informRecordDao;
	}

	public void setInformRecordDao(InformRecordDao informRecordDao) {
		this.informRecordDao = informRecordDao;
	}

	@Override
	public void addInformRecord(InformRecord record) {
		this.informRecordDao.addInformRecord(record);
	}

	@Override
	public String getSubscriberNameByInformId(String id) {
		String hql =" Select c.name From Inform a ,InformRecord b , Subscriber c "
				+ "where a.id = b.informId and b.subscriberId = c.id   and  a.id ='"+id+"'";
		return this.informRecordDao.queryInformRecordSubscriberName(hql);
	}


	
}
