package com.dearho.cs.account.action.account;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.service.AccountTradeRecordService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-10-19
 *
 */
public class AccountTradingAction extends AbstractAction {

	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5635059102554639723L;


	private AccountTradeRecordService accountTradeRecordService;
	
	
	private AccountTradeRecord accountTradeRecord;
	
	private Page<AccountTradeRecord> page=new Page<AccountTradeRecord>();
	
	public AccountTradingAction(){
		super();
		accountTradeRecord=new AccountTradeRecord();
		page.setCurrentPage(1);
		page.setCountField("a.id");
		

	}

	@Override
	public String process() {
	
		if(StringUtils.isEmpty(accountTradeRecord.getSubscriberId())){
			return 	SUCCESS;
		}
		page=accountTradeRecordService.queryAccountTradeRecordByPage(page, accountTradeRecord, null, null);
		return SUCCESS;
	}

	
	public void setAccountTradeRecordService(AccountTradeRecordService accountTradeRecordService) {
		this.accountTradeRecordService = accountTradeRecordService;
	}


	public AccountTradeRecordService getAccountTradeRecordService() {
		return accountTradeRecordService;
	}

	public AccountTradeRecord getAccountTradeRecord() {
		return accountTradeRecord;
	}

	public void setAccountTradeRecord(AccountTradeRecord accountTradeRecord) {
		this.accountTradeRecord = accountTradeRecord;
	}

	public Page<AccountTradeRecord> getPage() {
		return page;
	}

	public void setPage(Page<AccountTradeRecord> page) {
		this.page = page;
	}
	
	
	
	
	
	
}
