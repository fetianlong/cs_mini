package com.dearho.cs.subscriber.dao.impl;


import java.io.Serializable;
import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.dao.SubscriberDao;
import com.dearho.cs.subscriber.pojo.Subscriber;

public class SubscriberDaoImpl extends AbstractDaoSupport implements SubscriberDao {

	@Override
	public void addSubscriber(Subscriber Subscriber) {
		addEntity(Subscriber);
	}

	@Override
	public Subscriber querySubscriberById(String id) {
		return get(Subscriber.class, id);
	}

	@Override
	public Page<Subscriber> querySubscriberByPage(Page<Subscriber> page, String hql) {		
		Page<Subscriber> resultPage=pageCache(Subscriber.class, page, hql);
		//resultPage.setResults(resultPage);
		return resultPage;
	}

	@Override
	public Subscriber querySubscriberByPhone(String phoneNo) {
		String hql="from Subscriber where phoneNo='"+phoneNo+"'";
		
		
		List<Serializable> list=queryFList(hql);
		if(list ==null || list.size()==0){
			return null;
		}
		return (Subscriber) list.get(0);
	}

	public Subscriber querySubscriberByUnionid(String unionId) {
		String hql="from Subscriber where wechatUnionId='"+unionId+"'";
		
		
		List<Serializable> list=queryFList(hql);
		if(list ==null || list.size()==0){
			return null;
		}
		return (Subscriber) list.get(0);
	}

	@Override
	public void updateSubscriber(Subscriber Subscriber) {
		updateEntity(Subscriber);
		
	}
	/** 最新充值/退款记录列表 */
/*	public Page<AccountCurrentAccount> querySubscriberRechargeLatestByPage(Page<AccountCurrentAccount> page,String hql) {
		
		Page<AccountCurrentAccount> resultPage=page(page, hql,true);
		resultPage.setResults(idToObj(AccountCurrentAccount.class, resultPage.getResults()));
		if(resultPage.getResults()!=null){
			for(int i=0;i<resultPage.getResults().size();i++){
				AccountCurrentAccount accountCurrentAccount=(AccountCurrentAccount) resultPage.getResults().get(i);
				
				Subscriber subscriber=get(Subscriber.class,accountCurrentAccount.getSubscriberId());
				Account account=get(Account.class,accountCurrentAccount.getSubscriberId());
				subscriber.setAccount(account);
				
				accountCurrentAccount.setSubscriber(subscriber);
			
			}
		}
		return resultPage;
	}*/
	
	/** 最多充值记录列表 */
	/*public Page<AccountCurrentAccount> querySubscriberRechargeMostByPage(Page<AccountCurrentAccount> page,String hql) {
		
		Page<AccountCurrentAccount> resultPage=page(page, hql,true);
		List<AccountCurrentAccount> resultlist=new ArrayList<AccountCurrentAccount>();
		if(resultPage.getResults()!=null){
			for(int i=0;i<resultPage.getResults().size();i++){
				Object[] attr=(Object[])resultPage.getResults().get(i);
				AccountCurrentAccount accountCurrentAccount=new AccountCurrentAccount();
				if(attr[1]!=null){
					accountCurrentAccount.setAmount(((BigDecimal)attr[1]).doubleValue());
				}
				
				
				Subscriber subscriber=get(Subscriber.class,(String)attr[0]);
				Account account=get(Account.class,(String)attr[0]);
				
				
				subscriber.setAccount(account);
				accountCurrentAccount.setSubscriber(subscriber);
			
				resultlist.add(accountCurrentAccount);
			}
		}
		resultPage.setResults(resultlist);
		return resultPage;
	}
*/
	
	/**
	 * 首次用车
	 */
	public Page querySubscriberOrderFirstByPage(Page page, String hql) {
		/*	
		Page<AccountTradeRecordList> resultPage=page(page, hql,true);
		resultPage.setResults(idToObj(AccountTradeRecordList.class, resultPage.getResults()));
		if(resultPage.getResults()!=null){
			for(int i=0;i<resultPage.getResults().size();i++){
				AccountTradeRecordList accountTradeRecord=(AccountTradeRecordList) resultPage.getResults().get(i);
				
				Subscriber subscriber=get(Subscriber.class,accountTradeRecord.getSubscriberId());
				Account account=get(Account.class,accountTradeRecord.getSubscriberId());
				subscriber.setAccount(account);
				
				accountTradeRecord.setSubscriber(subscriber);
			
			}
		}
		return resultPage;
		*/
		return null;
		
	}

		@Override
	public Page querySubscriberOrderMostByPage(Page page, String hql) {
			/*	
		Page<AccountCurrentAccount> resultPage=page(page, hql,true);
		List<AccountCurrentAccount> resultlist=new ArrayList<AccountCurrentAccount>();
		if(resultPage.getResults()!=null){
			for(int i=0;i<resultPage.getResults().size();i++){
				Object[] attr=(Object[])resultPage.getResults().get(i);
				AccountCurrentAccount accountCurrentAccount=new AccountCurrentAccount();
				//字段复用仅展示作用
				accountCurrentAccount.setPayType(((BigInteger)attr[1]).intValue());
				
				Subscriber subscriber=get(Subscriber.class,(String)attr[0]);
				Account account=get(Account.class,(String)attr[0]);
				
				
				subscriber.setAccount(account);
				accountCurrentAccount.setSubscriber(subscriber);
			
				resultlist.add(accountCurrentAccount);
			}
		}
		resultPage.setResults(resultlist);
		return resultPage;
		*/
			return null;
	}

	@Override
	public List<Subscriber> getSubscriberByHql(String hql) {
		return getList(Subscriber.class, queryFList(hql));
	}

	@Override
	public Subscriber querySubscriberByName(String name) {
		String hql="from Subscriber where name='"+name+"'";

		List<Serializable> list=queryFList(hql);
		if(list ==null || list.size()==0){
			return null;
		}
		return (Subscriber) list.get(0);
	}

	
	/*private AccountCurrentAccount queryLatestAccountCurrentAccountBySubscriberId(String subscriberId) {
		String hql="select id from AccountCurrentAccount where subscriberId ='"+subscriberId+"' order by createTime desc  LIMIT 1 ";

		List<AccountCurrentAccount> list= getList(AccountCurrentAccount.class,queryFList(hql));
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}*/
	
}
