package com.dearho.cs.subscriber.service.impl;


import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.dao.SubscriberLoginRecordDao;
import com.dearho.cs.subscriber.pojo.SubscriberLoginRecord;
import com.dearho.cs.subscriber.service.SubscriberLoginRecordService;
import com.dearho.cs.util.StringHelper;

public class SubscriberLoginRecordServiceImpl implements SubscriberLoginRecordService {
	
	private SubscriberLoginRecordDao subscriberLoginRecordDao;
	

	@Override
	public void addSubscriberLoginRecord(
			SubscriberLoginRecord subscriberLoginRecord) {
		subscriberLoginRecordDao.addSubscriberLoginRecord(subscriberLoginRecord);	
	}

	@Override
	public SubscriberLoginRecord getLastLoginRecordBySubscriberId(
			String subscriberId) {
		
		Page<SubscriberLoginRecord> page=new Page<SubscriberLoginRecord>();
		page.setPageSize(2);
		
		StringBuffer hql=new StringBuffer();
		hql.append("select a.id from SubscriberLoginRecord a where subscriberId ='"+subscriberId+"' order by loginTime desc");
		page=subscriberLoginRecordDao.querySubscriberLoginRecordByPage(page, hql.toString());
		if(page!=null && page.getResults()!=null && page.getResults().size()==2){
			return (SubscriberLoginRecord) page.getResults().get(1);
		}else{
			return null;
		}
		
	}


	@Override
	public Page<SubscriberLoginRecord> querySubscriberLoginRecordByPage(
			Page<SubscriberLoginRecord> page, SubscriberLoginRecord subscriberLoginRecord) {
		
		StringBuffer hql=new StringBuffer();
		hql.append("select a.id from SubscriberLoginRecord a where 1=1");
		
		/*if(subscriberCard!=null){
			if(!StringUtils.isEmpty(subscriberCard.getCardId())){
				hql.append(" and cardId like '%"+subscriberCard.getCardId()).append("%' ");
			}
			if(subscriberCard.getState()!=null){
				hql.append(" and state =").append(subscriberCard.getState());
			}
			if(!StringUtils.isEmpty(subscriberCard.getType())){
				hql.append(" and type ='").append(subscriberCard.getType()).append("' ");
			}
		}*/
		hql.append(StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		page=subscriberLoginRecordDao.querySubscriberLoginRecordByPage(page, hql.toString());
		return page;
	}

	public SubscriberLoginRecordDao getSubscriberLoginRecordDao() {
		return subscriberLoginRecordDao;
	}

	public void setSubscriberLoginRecordDao(
			SubscriberLoginRecordDao subscriberLoginRecordDao) {
		this.subscriberLoginRecordDao = subscriberLoginRecordDao;
	}


	


	
	

}
