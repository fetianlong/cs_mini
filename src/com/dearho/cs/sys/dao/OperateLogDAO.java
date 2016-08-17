package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.OperateLog;


/**
 * @author GaoYunpeng
 *
 */
public interface OperateLogDAO {
	OperateLog getOperateLogById(String id);
	
	void addOperateLog(OperateLog operateLog);
	
	void deleteOperateLog(String[] checkdel);

	void updateOperateLog(OperateLog operateLog);
	
	Page<OperateLog> searchOperateLog(Page<OperateLog> page, String hql);

	List<OperateLog> searchOperateLog(String string);
	
	
}
