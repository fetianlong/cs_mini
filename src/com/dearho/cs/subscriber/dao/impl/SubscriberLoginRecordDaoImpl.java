package com.dearho.cs.subscriber.dao.impl;


import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.dao.SubscriberLoginRecordDao;
import com.dearho.cs.subscriber.pojo.SubscriberLoginRecord;

public class SubscriberLoginRecordDaoImpl extends AbstractDaoSupport implements SubscriberLoginRecordDao {

	
	
	


	@Override
	public void addSubscriberLoginRecord(
			SubscriberLoginRecord subscriberLoginRecord) {
		addEntity(subscriberLoginRecord);
		
	}


	@Override
	public SubscriberLoginRecord getLastLoginRecordBySubscriberId(
			String subscriberId) {
		
	/*	List<SubscriberLoginRecord> list=getList(SubscriberLoginRecord.class, queryFList(hql));
		if(list!=null && list.size()==1){
			return list.get(0);
		}else{
			return null;
		}
		*/
		return null;
	}


	@Override
	public SubscriberLoginRecord getSubscriberLoginRecordById(String id) {
		return get(SubscriberLoginRecord.class, id);
	}


	@Override
	public Page<SubscriberLoginRecord> querySubscriberLoginRecordByPage(
			Page<SubscriberLoginRecord> page, String hql) {
		Page<SubscriberLoginRecord> resultPage=pageCache(SubscriberLoginRecord.class, page, hql);
		
		resultPage.setResults(idToObj(SubscriberLoginRecord.class, resultPage.getmResults()));
//		if(resultPage.getResults()!=null){
//			for(int i=0;i<resultPage.getResults().size();i++){
//				SubscriberCard card=(SubscriberCard) resultPage.getResults().get(i);
//				
//				String hqlT="select id from SubscriberCardBinding where cardId='"+card.getCardId()+"' and state ="+SubscriberCard.STATE_BIND;
//				SubscriberCardBinding subscriberCardBinding=queryCardBind(hqlT);
//				if(subscriberCardBinding!=null){
//					Subscriber subscriber=get(Subscriber.class,subscriberCardBinding.getSubscriberId());
//					subscriberCardBinding.setSubscriber(subscriber);
//				}
//				card.setSubscriberCardBinding(subscriberCardBinding);
//			}
//		}
	
		
		return resultPage;
	}
}
