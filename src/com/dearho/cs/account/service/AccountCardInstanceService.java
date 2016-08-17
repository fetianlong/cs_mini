/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountService.java creation date:[2015-6-1 下午02:47:34] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.service;

import java.util.List;

import com.dearho.cs.account.pojo. AccountCardInstance;
import com.dearho.cs.core.db.page.Page;


/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-19
 *
 */
public interface AccountCardInstanceService {
	public Page< AccountCardInstance> queryAccountCardInstanceByPage(Page< AccountCardInstance> page, AccountCardInstance  accountCardInstance) ;
	public  AccountCardInstance      getCardInstanceById(String id);

	public void addAccountCardInstance( AccountCardInstance  AccountCardInstance);
	public void updateAccountCardInstance( AccountCardInstance card);

	public List< AccountCardInstance> queryEnabledCardInstanceByAccountId(String accountId);
	public List< AccountCardInstance> queryCardInstanceByAccountId(String accountId);
	
}
