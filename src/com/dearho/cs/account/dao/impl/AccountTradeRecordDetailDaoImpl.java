
package com.dearho.cs.account.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.account.dao.AccountTradeRecordDetailDao;
import com.dearho.cs.account.pojo.AccountPaymentAccount;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.AccountTradeRecordDetail;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-11-15
 *
 */
public class AccountTradeRecordDetailDaoImpl  extends AbstractDaoSupport implements AccountTradeRecordDetailDao {



	@Override
	public void addAccountTradeRecordDetail(AccountTradeRecordDetail accountTradeRecordDetail) {
		addEntity(accountTradeRecordDetail);
	}


	@Override
	public void updateAccountTradeRecordDetail(AccountTradeRecordDetail accountTradeRecordDetail) {
		updateEntity(accountTradeRecordDetail);
		
	}

	@Override
	public AccountTradeRecordDetail getAccountTradeRecordDetailById(String id) {
		return get(AccountTradeRecordDetail.class, id);
	}


	@Override
	public List<AccountTradeRecordDetail> getAccountTradeRecordDetailByBizId(String bizId) {
		getSession().clear();
		String hql="select a.id from AccountTradeRecordDetail a where a.tradeRecordId ='"+bizId+"' order by orderIndex desc";
		return queryAccountTradeRecordDetailList(hql);
	}
	
	public List<AccountTradeRecord> queryTradeRecordByBizAndType(String bizId,Integer type) {
		String hql="select a.id from AccountTradeRecord a where a.bizId ='"+bizId+"' and a.type="+type+" order by ts desc";
		return getList(AccountTradeRecord.class, queryFList(hql));
	}


	@Override
	public List<AccountTradeRecordDetail> queryAccountTradeRecordDetailList(String hql) {
		return getList(AccountTradeRecordDetail.class, queryFList(hql));
	}
	
	@Override
	public AccountTradeRecord getTradeRecordByCarInstanceId(String instanceId) {
		AccountTradeRecord accountTradeRecord=null;
		AccountTradeRecordDetail accountTradeRecordDetail=null;
		String hql="select a.id from AccountTradeRecordDetail a where a.rechargeCardInstanceId ='"+instanceId+"'";
		List<AccountTradeRecordDetail> list=queryAccountTradeRecordDetailList(hql);
		if(list==null){
			return null;
		}else{
			accountTradeRecordDetail=list.get(0);
		}
		if(StringUtils.isNotEmpty(accountTradeRecordDetail.getPaymentAccountId())){
			accountTradeRecordDetail.setAccountPaymentAccount(get(AccountPaymentAccount.class, accountTradeRecordDetail.getPaymentAccountId()));
		}
	
		accountTradeRecord= get(AccountTradeRecord.class, accountTradeRecordDetail.getTradeRecordId());
		accountTradeRecord.setAccountTradeRecordDetail(accountTradeRecordDetail);
		
		return accountTradeRecord;
	}

	public Page<AccountTradeRecordDetail> queryAccountTradeRecordDetailByPage(Page<AccountTradeRecordDetail> page, String hql) {
		Page<AccountTradeRecordDetail> resultPage=pageCache(AccountTradeRecordDetail.class, page, hql);
		if(resultPage.getmResults()!=null){
			for(int i=0;i<resultPage.getmResults().size();i++){
				AccountTradeRecordDetail detail=(AccountTradeRecordDetail) resultPage.getmResults().get(i);
					if(StringUtils.isNotEmpty(detail.getPaymentAccountId())){
						detail.setAccountPaymentAccount(get(AccountPaymentAccount.class, detail.getPaymentAccountId()));
				}
			}
		}
		return resultPage;
	}

}
