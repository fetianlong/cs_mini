/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountTradeRecordDaoImpl.java creation date:[2015-6-1 下午02:38:41] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.dao.impl;

import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.dao.AccountPaymentAccountDao;
import com.dearho.cs.account.pojo.AccountPaymentAccount;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.core.db.AbstractDaoSupport;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-17
 *
 */
public class AccountPaymentAccountDaoImpl extends AbstractDaoSupport implements AccountPaymentAccountDao {


	@Override
	public void addAccountPaymentAccount(AccountPaymentAccount accountPaymentAccount) {
		addEntity(accountPaymentAccount);
		
	}


	@Override
	public void updateAccountPaymentAccount(AccountPaymentAccount accountTradeRecordDetail) {
		updateEntity(accountTradeRecordDetail);
		
	}


	@Override
	public AccountPaymentAccount getAccountPaymentAccountById(String id) {
		return get(AccountPaymentAccount.class, id);
	}


	@Override
	public List<AccountPaymentAccount> getAccountPaymentAccountBySubscriberId(String id) {
		String hql ="select from AccountPaymentAccount where subscriberId='"+id+"'";
		return queryAccountPaymentAccountList(hql);
	}


	@Override
	public List<AccountPaymentAccount> queryAccountPaymentAccountList(String hql) {
		return getList(AccountPaymentAccount.class, queryFList(hql));
	}


	@Override
	public AccountPaymentAccount queryPaymentAccountByAccountNoAndSubscriberId(String accountNo,String subscriberId) {
		
		String hql ="select a.id from AccountPaymentAccount a where a.subscriberId='"+subscriberId+"' ";
		if(!StringUtils.isEmpty(accountNo)){
			hql+=" and a.accountNo='"+accountNo+"' ";
		}
		List<AccountPaymentAccount> list=queryAccountPaymentAccountList(hql);
		if(list==null || list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}


	

}
