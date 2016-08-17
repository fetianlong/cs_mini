package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.SysOperateLogRecord;

public interface SystemOperateLogDAO {

	public void addOperateLog(SysOperateLogRecord log);
	
	public List<SysOperateLogRecord> querySysOperateLogRecords(String hql);

	public Page<SysOperateLogRecord> querySystemOperateLogPage(
			Page<SysOperateLogRecord> page, String string);
	
}
