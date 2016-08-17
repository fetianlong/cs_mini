package com.dearho.cs.sys.dao.impl;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.BusinessFlowDAO;
import com.dearho.cs.sys.pojo.BusinessFlow;

public class BusinessFlowDAOImpl extends AbstractDaoSupport implements BusinessFlowDAO {

	

	@Override
	public void addBusinessFlow(BusinessFlow businessFlow){
		addEntity(businessFlow);
	}

	@Override
	public void updateBusinessFlow(BusinessFlow businessFlow){
		updateEntity(businessFlow);
	}

	@Override
	public void deleteBusinessFlow(final String[] checkdel) {
		final String queryString="delete Role BusinessFlow id in (:ids)";
		deleteEntity(BusinessFlow.class, queryString, checkdel);
	}

	@Override
	public Page<BusinessFlow> searchBusinessFlow(Page<BusinessFlow> page,String hql) {
		Page<BusinessFlow> resultPage = pageCache(BusinessFlow.class ,page, hql);
		resultPage.setResults(idToObj(BusinessFlow.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public BusinessFlow getBusinessFlowByBizId(String bizId) {
		return get(BusinessFlow.class, (String)getQuery("select id from BusinessFlow where businessId = '"+bizId+"'").uniqueResult());
	}
	public BusinessFlow getBusinessFlowByConfirmId(String confirmId) {
		return get(BusinessFlow.class, (String)getQuery("select id from BusinessFlow where confirmId = '"+confirmId+"'").uniqueResult());
	}

	
	@Override
	public BusinessFlow getBusinessFlowById(String id) {
		return get(BusinessFlow.class,id);
	}

}
