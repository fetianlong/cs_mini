package com.dearho.cs.sys.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.OperateLogDAO;
import com.dearho.cs.sys.pojo.OperateLog;


/**
 * @author GaoYunpeng
 *
 */
public class OperateLogDAOImpl  extends AbstractDaoSupport implements OperateLogDAO {

	


	@Override
	public void addOperateLog(OperateLog operateLog) {
		addEntity(operateLog);
		
	}

	@Override
	public void deleteOperateLog(String[] checkdel) {
		final String queryString="delete OperateLog where id in (:ids)";
		deleteEntity(OperateLog.class, queryString, checkdel);
		
	}

	@Override
	public OperateLog getOperateLogById(String operateLogId) {
		
		return get(OperateLog.class, operateLogId);
	}

	@Override
	public Page<OperateLog> searchOperateLog(Page<OperateLog> page, String hql) {
		Page<OperateLog> resultPage=pageCache(OperateLog.class, page, hql);
		
		resultPage.setResults(idToObj(OperateLog.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public void updateOperateLog(OperateLog operateLog) {
		updateEntity(operateLog);
		
	}

	@Override
	public List<OperateLog> searchOperateLog(String hql) {
		return getList(OperateLog.class, queryFList( hql));
	}



}
