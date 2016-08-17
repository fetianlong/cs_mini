package com.dearho.cs.account.dao;




import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.pojo.BusinessFlow;

public interface AccountDao {
	
	void addAccount(Account account);
	
	void updateAccount(Account account);
	
	public Page<Subscriber> queryAccountByPage(Page<Subscriber> page,String hql);
	
	Account getAccountById(String id);
	
	Page<BusinessFlow> queryApplyRefundListByPage(Page<BusinessFlow> page, String hql);
	
	
	

}
