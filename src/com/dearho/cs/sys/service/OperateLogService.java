package com.dearho.cs.sys.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.OperateLog;


/**
 * @author GaoYunpeng
 *
 */
public interface OperateLogService {
	
	public List<OperateLog> searchOperateLog(OperateLog operateLog);

	public void addOperateLog(OperateLog operateLog);

	public OperateLog searchOperateLogByID(String id);

	public void deleteOperateLog(String[] checkdel);

	public void updateOperateLog(OperateLog OperateLog);

	public Page<OperateLog> searchOperateLog(Page<OperateLog> page, OperateLog operateLog);

	/**
	 * 查询最近一次车门车锁记录
	 * @param userId
	 * @param ordersId
	 * @param command
	 * @param timeLong
	 * @return
	 */
	public OperateLog searchLastDoorOperateLog(String userId, String ordersId,
			String command, int timeLong);
	
	
}
