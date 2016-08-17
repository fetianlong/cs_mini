package com.dearho.cs.sys.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.SystemOperateLogDAO;
import com.dearho.cs.sys.pojo.SysOperateLogRecord;

public class SystemOperateLogDAOImpl extends AbstractDaoSupport implements SystemOperateLogDAO{

	@Override
	public void addOperateLog(SysOperateLogRecord log) {
		addEntity(log);
	}

	@Override
	public List<SysOperateLogRecord> querySysOperateLogRecords(String hql) {
		return getList(SysOperateLogRecord.class, queryFList(hql));
	}

	@Override
	public Page<SysOperateLogRecord> querySystemOperateLogPage(
			Page<SysOperateLogRecord> page, String hql) {
		Page<SysOperateLogRecord> resultPage=pageCache(SysOperateLogRecord.class, page, hql);
		resultPage.setResults(idToObj(SysOperateLogRecord.class, resultPage.getmResults()));
		return resultPage;
	}

}
