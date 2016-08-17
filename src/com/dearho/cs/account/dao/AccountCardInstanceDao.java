package com.dearho.cs.account.dao;




import java.util.List;

import com.dearho.cs.account.pojo.AccountCardInstance;
import com.dearho.cs.core.db.page.Page;

public interface AccountCardInstanceDao  {
	
	void addAccountCardInstance(AccountCardInstance accountCardInstance);
	
	void updateAccountCardInstance(AccountCardInstance accountCardInstance);
	
	Page<AccountCardInstance> queryAccountCardInstanceByPage(Page<AccountCardInstance> page,String hql);
	
	AccountCardInstance getAccountCardInstanceById(String id);
	
	public List<AccountCardInstance> queryAccountCardInstanceList(String hql) ;

	void deleteAccountCardInstance(String queryString, String[] checkdel);
	
	List<AccountCardInstance> queryEnabledCardInstanceByAccountId(String accountId);
	
	
	

}
