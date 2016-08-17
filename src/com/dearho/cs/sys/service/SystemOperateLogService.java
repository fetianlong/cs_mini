package com.dearho.cs.sys.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.SysOperateLogRecord;

public interface SystemOperateLogService {

	/**
	 * 得到所有日志记录
	 * @param endTime 
	 * @param startTime 
	 * @param page 
	 * @param operateRemark 
	 * @return
	 */
	public Page<SysOperateLogRecord> getSysOperateLogRecords(Page<SysOperateLogRecord> page, String startTime, String endTime, String operateRemark);
	
	/**
	 * 新增日志
	 * @param logRecord
	 */
	public void addSysOperateLogRecord(SysOperateLogRecord logRecord);

	/**
	 * 根据每条数据的ID和类型，查看对这条数据的操作记录
	 * @param dataId
	 * @param modelName
	 * @return
	 */
	public List<SysOperateLogRecord> getSysOperateLogByDataId(String dataId,String modelName);
	
}
