package com.dearho.cs.sys.dao;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.BusinessFlow;

/**
 * 
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-25
 *
 */
public interface BusinessFlowDAO {


	
	void addBusinessFlow(BusinessFlow businessFlow);
	
	void updateBusinessFlow(BusinessFlow businessFlow);
	

	void deleteBusinessFlow(final String[] checkdel);
	
	Page<BusinessFlow> searchBusinessFlow(Page<BusinessFlow> page,String hql);

	BusinessFlow getBusinessFlowByBizId(String bizId);
	
	BusinessFlow getBusinessFlowById(String id);
	
	BusinessFlow getBusinessFlowByConfirmId(String confirmId);
}
