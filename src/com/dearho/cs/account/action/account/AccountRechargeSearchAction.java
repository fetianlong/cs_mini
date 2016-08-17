package com.dearho.cs.account.action.account;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.service.AccountTradeRecordService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;

/**
 * @Description 
 *
 */
public class AccountRechargeSearchAction extends AbstractAction {
  
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5635059102554639723L;


	private AccountTradeRecordService accountTradeRecordService;
	private AccountTradeRecord accountTradeRecord;
	
	private Page<AccountTradeRecord> page=new Page<AccountTradeRecord>();
	
	public AccountRechargeSearchAction(){
		super();
		accountTradeRecord=new AccountTradeRecord();
		accountTradeRecord.setType(Account.TYPE_RECHARGE);
		page.setCurrentPage(1);
		page.setCountField("a.id");
		System.out.println("loding.............finish");
		

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
