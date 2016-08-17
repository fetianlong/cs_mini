package com.dearho.cs.advice.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.dearho.cs.advice.dao.InformRecordDao;
import com.dearho.cs.advice.pojo.InformRecord;
import com.dearho.cs.core.db.AbstractDaoSupport;

/**
 * 通知记录Dao口实现类
 * @author jyt
 * @since 2016年7月20日 下午2:36:47
 */
public class InformRecordDaoImpl  extends AbstractDaoSupport implements InformRecordDao {

	@Override
	public void addInformRecord(InformRecord record) {
		this.addEntity(record);
	}

	@Override
	public String  queryInformRecordSubscriberName(String hql) {

		Query q = this.getQuery(hql);
	     String name = q.list().get(0).toString();
		return name;
	}

}
