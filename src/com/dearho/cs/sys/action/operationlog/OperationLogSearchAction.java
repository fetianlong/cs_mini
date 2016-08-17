package com.dearho.cs.sys.action.operationlog;



import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.OperateLog;
import com.dearho.cs.sys.service.OperateLogService;


public class OperationLogSearchAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private OperateLogService operateLogService;
	private OperateLog operateLog;
	
	private Page<OperateLog> page = new Page<OperateLog>();
	
	public OperationLogSearchAction(){
		super();
		operateLog = new OperateLog();
		
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process() {
		if(operateLog.getCarId()==null){
			return SUCCESS;
		}
		page=operateLogService.searchOperateLog(page, operateLog);
		return SUCCESS;
	}


	public OperateLogService getOperateLogService() {
		return operateLogService;
	}


	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}


	public OperateLog getOperateLog() {
		return operateLog;
	}


	public void setOperateLog(OperateLog operateLog) {
		this.operateLog = operateLog;
	}


	public Page<OperateLog> getPage() {
		return page;
	}


	public void setPage(Page<OperateLog> page) {
		this.page = page;
	}
	
	

}
