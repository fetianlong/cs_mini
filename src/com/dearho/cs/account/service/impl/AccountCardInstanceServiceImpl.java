/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountServiceImpl.java creation date:[2015-6-1 下午02:48:52] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.service.impl;

import java.util.List;

import com.dearho.cs.account.dao.AccountCardInstanceDao;
import com.dearho.cs.account.pojo.AccountCardInstance;
import com.dearho.cs.account.service.AccountCardInstanceService;
import com.dearho.cs.core.db.page.Page;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-16
 *
 */
public class AccountCardInstanceServiceImpl implements AccountCardInstanceService {
	
	private AccountCardInstanceDao accountCardInstanceDao;


	@Override
	public Page<AccountCardInstance> queryAccountCardInstanceByPage(Page<AccountCardInstance> page,
			AccountCardInstance accountCardInstance) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public AccountCardInstance getCardInstanceById(String id) {
		return accountCardInstanceDao.getAccountCardInstanceById(id);
	}


	@Override
	public void addAccountCardInstance(AccountCardInstance accountCardInstance) {
		accountCardInstanceDao.addAccountCardInstance(accountCardInstance);
		
	}


	@Override
	public void updateAccountCardInstance(AccountCardInstance accountCardInstance) {
		accountCardInstanceDao.updateAccountCardInstance(accountCardInstance);
		
	}


	@Override
	public List<AccountCardInstance> queryEnabledCardInstanceByAccountId(String accountId) {
		return accountCardInstanceDao.queryEnabledCardInstanceByAccountId(accountId);
	}



	/**
	 * 折扣率升序、代办降序，创建时间降序
	 */
	public List<AccountCardInstance> queryCardInstanceByAccountId(String accountId) {
		StringBuffer hql = new StringBuffer();
		hql.append(" select a.id from RechargeCard a where 1=1 ");
		hql.append("subscriberId ='"+accountId+"'");
		hql.append(" order by createTime desc");
		return accountCardInstanceDao.queryAccountCardInstanceList(hql.toString());
	}


	public AccountCardInstanceDao getAccountCardInstanceDao() {
		return accountCardInstanceDao;
	}


	public void setAccountCardInstanceDao(AccountCardInstanceDao accountCardInstanceDao) {
		this.accountCardInstanceDao = accountCardInstanceDao;
	}

	

	
	
}
