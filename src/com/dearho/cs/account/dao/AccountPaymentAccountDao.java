package com.dearho.cs.account.dao;




import java.util.List;

import com.dearho.cs.account.pojo.AccountPaymentAccount;
import com.dearho.cs.account.pojo.AccountTradeRecord;

public interface AccountPaymentAccountDao {
	
	void addAccountPaymentAccount(AccountPaymentAccount accountPaymentAccount);
	
	void updateAccountPaymentAccount(AccountPaymentAccount accountTradeRecordDetail);
	
	AccountPaymentAccount getAccountPaymentAccountById(String id);
	
	List<AccountPaymentAccount> getAccountPaymentAccountBySubscriberId(String id);
	
	List<AccountPaymentAccount> queryAccountPaymentAccountList(String hql) ;

	AccountPaymentAccount queryPaymentAccountByAccountNoAndSubscriberId(String accountNo,String subscriberId) ;
	
	
	
	

}
