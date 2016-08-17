package com.dearho.cs.sys.service.impl;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.BusinessFlowDAO;
import com.dearho.cs.sys.dao.RoleDAO;
import com.dearho.cs.sys.pojo.BusinessFlow;
import com.dearho.cs.sys.pojo.Role;
import com.dearho.cs.sys.service.BusinessFlowService;
import com.dearho.cs.util.StringHelper;


public class BusinessFlowServiceImpl implements BusinessFlowService {

	private BusinessFlowDAO businessFlowDAO;
	



	

	
	@Override
	public void addBusinessFlow(BusinessFlow businessFlow) {
		businessFlowDAO.addBusinessFlow(businessFlow);
		
	}

	
	@Override
	public void deleteBusinessFlow(String[] checkdel) {
		businessFlowDAO.deleteBusinessFlow(checkdel);
		
	}

	
	@Override
	public BusinessFlow getBusinessFlowByBizId(String bizId) {
		
		return businessFlowDAO.getBusinessFlowByBizId(bizId);
	}

	
	@Override
	public Page<BusinessFlow> searchBusinessFlow(Page<BusinessFlow> page,
			BusinessFlow businessFlow) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select a.id from BusinessFlow a where 1 = 1 ");
		if (businessFlow != null){
			if (StringHelper.isNotEmpty(businessFlow.getBusinessId())){
				sb.append("and a.businessId = '"+businessFlow.getBusinessId()+"'");
			}
			if (businessFlow.getBusinessType()!=null){
				sb.append("and a.businessType = "+businessFlow.getBusinessType());
			}
			if (StringHelper.isNotEmpty(businessFlow.getApplicantId())){
				sb.append("and a.applicantId = '"+businessFlow.getApplicantId()+"'");
			}
			if (businessFlow.getApplyDateStart()!=null){
				sb.append("and a.applyDateStart >= "+businessFlow.getApplyDateStart());
			}
			if (businessFlow.getApplyDateEnd()!=null){
				sb.append("and a.applyDateEnd <= "+businessFlow.getApplyDateEnd());
			}
			if (StringHelper.isNotEmpty(businessFlow.getReviewerId())){
				sb.append("and a.reviewerId = '"+businessFlow.getReviewerId()+"'");
			}
			if (businessFlow.getReviewDateStart()!=null){
				sb.append("and a.reviewDateStart >= "+businessFlow.getReviewDateStart());
			}
			if (businessFlow.getReviewDateEnd()!=null){
				sb.append("and a.reviewDateEnd >= "+businessFlow.getReviewDateEnd());
			}
			if (businessFlow.getResult()!=null){
				sb.append("and a.result = "+businessFlow.getResult());
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		page = businessFlowDAO.searchBusinessFlow(page, sb.toString());
		
		return page;
	}

	@Override
	public void updateBusinessFlow(BusinessFlow businessFlow) {
		businessFlowDAO.updateBusinessFlow(businessFlow);
		
	}


	@Override
	public BusinessFlow getBusinessFlowByConfirmId(String confirmId) {
		return businessFlowDAO.getBusinessFlowByConfirmId(confirmId);
	}

}
