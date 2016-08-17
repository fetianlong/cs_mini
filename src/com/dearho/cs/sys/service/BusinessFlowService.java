package com.dearho.cs.sys.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.BusinessFlow;
import com.dearho.cs.sys.pojo.Role;

/**
 * 
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-25
 *
 */
public interface BusinessFlowService {
	

	void addBusinessFlow(BusinessFlow businessFlow);
	
	void updateBusinessFlow(BusinessFlow businessFlow);
	

	void deleteBusinessFlow(final String[] checkdel);
	
	Page<BusinessFlow> searchBusinessFlow(Page<BusinessFlow> page,BusinessFlow businessFlow);

	BusinessFlow getBusinessFlowByBizId(String bizId);
	
	BusinessFlow getBusinessFlowByConfirmId(String bizId);
}
