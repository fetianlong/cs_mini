package com.dearho.cs.sys.action.operationlog;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.SysOperateLogRecord;
import com.dearho.cs.sys.service.SystemOperateLogService;
import com.dearho.cs.util.StringHelper;

public class SysOperateLogRecordAction  extends AbstractAction{

	private static final long serialVersionUID = 1L;

	private SystemOperateLogService systemOperateLogService;
	
	private SysOperateLogRecord sysOperateLogRecord;
	
	private Page<SysOperateLogRecord> page=new Page<SysOperateLogRecord>();
	
	private List<SysOperateLogRecord> logList;
	
	private String startTime;
	
	private String endTime;
	
	private String operateRemark;
	
	public SysOperateLogRecordAction(){
		super();
		sysOperateLogRecord = new SysOperateLogRecord();
		page.setCountField("a.id");
		page.setCurrentPage(1);
	}
	
	@Override
	public String process() {
		page = systemOperateLogService.getSysOperateLogRecords(page, startTime, endTime, operateRemark);
		return SUCCESS;
	}

	/**
	 * 根据每条数据的ID，查看对这条数据的操作记录
	 * @return
	 */
	public String getSysOperateLogByDataId() {
		String dataId = getRequest().getParameter("dataId");
		String modelName = getRequest().getParameter("modelName");
		if(StringHelper.isNotEmpty(modelName)){
			try {
				modelName = java.net.URLDecoder.decode(modelName,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		logList = systemOperateLogService.getSysOperateLogByDataId(dataId,modelName);
		return SUCCESS;
	}
	
	public SystemOperateLogService getSystemOperateLogService() {
		return systemOperateLogService;
	}

	public void setSystemOperateLogService(SystemOperateLogService systemOperateLogService) {
		this.systemOperateLogService = systemOperateLogService;
	}

	public SysOperateLogRecord getSysOperateLogRecord() {
		return sysOperateLogRecord;
	}

	public void setSysOperateLogRecord(SysOperateLogRecord sysOperateLogRecord) {
		this.sysOperateLogRecord = sysOperateLogRecord;
	}

	public Page<SysOperateLogRecord> getPage() {
		return page;
	}

	public void setPage(Page<SysOperateLogRecord> page) {
		this.page = page;
	}

	public String getOperateRemark() {
		return operateRemark;
	}

	public void setOperateRemark(String operateRemark) {
		this.operateRemark = operateRemark;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public List<SysOperateLogRecord> getLogList() {
		return logList;
	}

	public void setLogList(List<SysOperateLogRecord> logList) {
		this.logList = logList;
	}


}
