package com.dearho.cs.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.OperateLogDAO;
import com.dearho.cs.sys.pojo.OperateLog;
import com.dearho.cs.sys.service.OperateLogService;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;


/**
 * @author GaoYunpeng
 *
 */
public class OperateLogServiceImpl implements OperateLogService{

	private  OperateLogDAO  operateLogDAO;
	@Override
	public void addOperateLog(OperateLog operateLog) {
		operateLogDAO.addOperateLog(operateLog);
		
	}

	@Override
	public void deleteOperateLog(String[] checkdel) {
		operateLogDAO.deleteOperateLog(checkdel);
		
	}

	@Override
	public List<OperateLog> searchOperateLog(OperateLog operateLog) {
		StringBuilder hql = new StringBuilder();
		hql.append("select a.id from OperateLog a where 1 = 1 ");
		return operateLogDAO.searchOperateLog(hql.toString());
	}

	@Override
	public Page<OperateLog> searchOperateLog(Page<OperateLog> page, OperateLog operateLog) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.id from OperateLog a where 1 = 1 ");
		if (operateLog != null){
			if(!StringUtils.isEmpty(operateLog.getCarId())){
				sb.append(" and carId ='"+operateLog.getCarId()+"'");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by ts desc" : page.getOrderByString());
		page = operateLogDAO.searchOperateLog(page, sb.toString());
		return page;
	}

	@Override
	public OperateLog searchOperateLogByID(String operateLogId) {
		
		return operateLogDAO.getOperateLogById(operateLogId);
	}

	@Override
	public void updateOperateLog(OperateLog operateLog) {
		operateLogDAO.updateOperateLog(operateLog);
		
	}

	public OperateLogDAO getOperateLogDAO() {
		return operateLogDAO;
	}

	public void setOperateLogDAO(OperateLogDAO operateLogDAO) {
		this.operateLogDAO = operateLogDAO;
	}

	@Override
	public OperateLog searchLastDoorOperateLog(String userId, String ordersId,
			String command, int timeLong) {
		StringBuilder hql = new StringBuilder();
		String ts = DateUtil.formatDate(new Date(new Date().getTime() - timeLong), "yyyy-MM-dd HH:mm:ss");
		hql.append("select a.id from OperateLog a where a.userId = '").append(userId).
		append("' and a.modular='").append(ordersId).append("' and a.type='").append(command).
		append("' and a.ts > '").append(ts).append("'");
		List<OperateLog> operateLogs = operateLogDAO.searchOperateLog(hql.toString());
		if(operateLogs != null && operateLogs.size() > 0){
			return operateLogs.get(0);
		}
		return null;
	}

	

}
