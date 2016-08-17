package com.dearho.cs.account.dao.impl;



import com.dearho.cs.account.dao.AccountDao;
import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.pojo.BusinessFlow;

public class AccountDaoImpl extends AbstractDaoSupport implements AccountDao {

	@Override
	public void addAccount(Account account) {
		addEntity(account);
		
	}

	
	@Override
	public Account getAccountById(String id) {
		return get(Account.class, id);
	}

	
	@Override
	public Page<Subscriber> queryAccountByPage(Page<Subscriber> page,String hql) {
	
		Page<Subscriber> resultPage=page(page, hql);
		resultPage.setResults(idToObj(Subscriber.class, resultPage.getmResults()));
		if(resultPage.getResults()!=null){
			for(int i=0;i<resultPage.getResults().size();i++){
				Subscriber subscriber=(Subscriber) resultPage.getResults().get(i);
				
				Account account=get(Account.class,subscriber.getId());
				subscriber.setAccount(account);
				
			}
		}
		return resultPage;
	}


	@Override
	public void  updateAccount(Account account){
		updateEntity(account);	
	}

	
	public Page<BusinessFlow> queryApplyRefundListByPage(Page<BusinessFlow> page, String hql) {
		
		Page<BusinessFlow> resultPage=page(page, hql);
		resultPage.setResults(idToObj(BusinessFlow.class, resultPage.getmResults()));
		if(resultPage.getResults()!=null){
			for(int i=0;i<resultPage.getResults().size();i++){
				BusinessFlow businessFlow=(BusinessFlow) resultPage.getResults().get(i);
				Subscriber subscriber=get(Subscriber.class,businessFlow.getApplicantId());
				businessFlow.setApplicantSubscriber(subscriber);
				Account account=get(Account.class,businessFlow.getApplicantId());
				businessFlow.setApplicantAccount(account);
			}
		}
		return resultPage;
	}
}
