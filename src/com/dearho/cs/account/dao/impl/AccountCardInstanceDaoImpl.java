/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountTradeRecordDaoImpl.java creation date:[2015-11-16 下午02:38:41] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.dao.impl;

import java.util.List;

import com.dearho.cs.account.dao.AccountCardInstanceDao;
import com.dearho.cs.account.pojo.AccountCardInstance;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

/**
 * @Author liusong
 * @Description 会员充值卡实例DAO
 * @Version 1.0,2015-11-17
 *
 */
public class AccountCardInstanceDaoImpl extends AbstractDaoSupport implements AccountCardInstanceDao {


	@Override
	public void addAccountCardInstance(AccountCardInstance accountCardInstance) {
		addEntity(accountCardInstance);
		
	}


	@Override
	public void updateAccountCardInstance(AccountCardInstance accountCardInstance) {
		updateEntity(accountCardInstance);
		
	}


	@Override
	public Page<AccountCardInstance> queryAccountCardInstanceByPage(Page<AccountCardInstance> page, String hql) {
		Page<AccountCardInstance> resultPage=pageCache(AccountCardInstance.class, page, hql);
		return resultPage;
	}


	@Override
	public AccountCardInstance getAccountCardInstanceById(String id) {
		return get(AccountCardInstance.class, id);
	}


	@Override
	public List<AccountCardInstance> queryAccountCardInstanceList(String hql) {
		return getList(AccountCardInstance.class, queryFList(hql));
	}


	@Override
	public void deleteAccountCardInstance(String queryString, String[] checkdel) {
		deleteEntity(AccountCardInstance.class, queryString, checkdel);
		
	}
	
	public List<AccountCardInstance> queryEnabledCardInstanceByAccountId(String accountId) {
		getSession().clear();
		StringBuffer hql = new StringBuffer();
		hql.append(" select a.id from AccountCardInstance a where 1=1 ");
		hql.append(" and subscriberId ='"+accountId+"' ");
		hql.append(" and isValid="+AccountCardInstance.IS_VALID_TRUE);
		hql.append(" order by isSystem asc,createTime desc");
		return queryAccountCardInstanceList(hql.toString());
	}


}
