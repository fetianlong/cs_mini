package com.dearho.cs.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.SystemOperateLogDAO;
import com.dearho.cs.sys.pojo.SysOperateLogRecord;
import com.dearho.cs.sys.service.SystemOperateLogService;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

public class SystemOperateLogServiceImpl implements SystemOperateLogService{

	private SystemOperateLogDAO systemOperateLogDAO;
	
	/**
	 * 新增日志记录
	 */
	@Override
	public void addSysOperateLogRecord(SysOperateLogRecord logRecord) {
		systemOperateLogDAO.addOperateLog(logRecord);
	}

	@Override
	public Page<SysOperateLogRecord> getSysOperateLogRecords(
			Page<SysOperateLogRecord> page, String startTime, String endTime, String operateRemark) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from SysOperateLogRecord a where 1=1");
		
		if(StringHelper.isNotEmpty(operateRemark)){
			sb.append(" and a.operateRemark = '").append(operateRemark).append("' ");
		}
		
		if(StringHelper.isNotEmpty(startTime)){
			Date startDate = null;
			try {
				startDate = DateUtil.parseDate(startTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
				sb.append(" and a.operateDate >= '").append(DateUtil.getChar19DateString(startDate)).append("'");
			} catch (Exception e) {
			}
		}
		if(StringHelper.isNotEmpty(endTime)){
			Date endDate = null;
			try {
				endDate = DateUtil.parseDate(endTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
				sb.append(" and a.operateDate < '").append(DateUtil.getChar19DateString(endDate)).append("'");
			} catch (Exception e) {
			}
		}
		sb.append(" order by a.operateDate desc");
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		page=systemOperateLogDAO.querySystemOperateLogPage(page, sb.toString());
		return page;
	}
	
	/**
	 * 根据每条数据的ID，查看对这条数据的操作记录
	 */
	@Override
	public List<SysOperateLogRecord> getSysOperateLogByDataId(String dataId,String modelName) {
		if(!StringUtils.isEmpty(dataId)){
			StringBuffer sb=new StringBuffer();
			sb.append("select a.id from SysOperateLogRecord a where 1=1 and a.recordId='"+dataId+"' ");
			if(StringHelper.isNotEmpty(modelName)){
				sb.append(" and a.modelName = '").append(modelName).append("'");
			}
			sb.append(" order by a.operateDate desc");
			List<SysOperateLogRecord> list = systemOperateLogDAO.querySysOperateLogRecords(sb.toString());
			return list;
		}else{
			return null;
		}
	}
	
	public SystemOperateLogDAO getSystemOperateLogDAO() {
		return systemOperateLogDAO;
	}

	public void setSystemOperateLogDAO(SystemOperateLogDAO systemOperateLogDAO) {
		this.systemOperateLogDAO = systemOperateLogDAO;
	}

}
