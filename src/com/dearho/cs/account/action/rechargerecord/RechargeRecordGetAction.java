package com.dearho.cs.account.action.rechargerecord;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.RechargeRecord;
import com.dearho.cs.account.service.RechargeRecordService;
import com.dearho.cs.sys.action.AbstractAction;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-17
 *
 */
public class RechargeRecordGetAction extends AbstractAction  {

	
	private static final long serialVersionUID = 3530792839096296655L;


	private RechargeRecordService rechargeRecordService;
	private RechargeRecord rechargeRecord ;
	
	
	public RechargeRecordGetAction(){
		super();
		rechargeRecord=new RechargeRecord();
	}
	
	@Override
	public String process() {
		if(!StringUtils.isEmpty(rechargeRecord.getId())){
			rechargeRecord=rechargeRecordService.getRechargeRecordById(rechargeRecord.getId());
		}
		
		return SUCCESS;
	}

	public RechargeRecordService getRechargeRecordService() {
		return rechargeRecordService;
	}

	public void setRechargeRecordService(RechargeRecordService rechargeRecordService) {
		this.rechargeRecordService = rechargeRecordService;
	}

	public RechargeRecord getRechargeRecord() {
		return rechargeRecord;
	}

	public void setRechargeRecord(RechargeRecord rechargeRecord) {
		this.rechargeRecord = rechargeRecord;
	}

	
	
	
}
