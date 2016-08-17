package com.dearho.cs.advice.service.impl;

import java.util.List;

import com.dearho.cs.advice.dao.HotDotDAO;
import com.dearho.cs.advice.pojo.HotDot;
import com.dearho.cs.advice.service.HotDotService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.util.StringHelper;


/**
 * 催我建点 业务实现
 * @fileName:HotDotServiceImpl.java
 * @author:赵振明
 * @createTime:2016年7月15日上午10:48:49
 *
 */
public class HotDotServiceImpl implements HotDotService {
	
	private HotDotDAO hotDotDAO;

	private SubscriberService subscriberService;
	
	
	
	
	
	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public HotDotDAO getHotDotDAO() {
		return hotDotDAO;
	}

	public void setHotDotDAO(HotDotDAO hotDotDAO) {
		this.hotDotDAO = hotDotDAO;
	}

	@Override
	public void addHotDot(HotDot hotDot) {
		hotDotDAO.addHotDot(hotDot);
	}

	@Override
	public void updateHotDot(HotDot hotDot) {
		hotDotDAO.updateHotDot(hotDot);
	}

	@Override
	public void deleteHotDot(String[] id) {
		hotDotDAO.deleteHotDot(id);
	}

	@Override
	public List<HotDot> searchHotDot(String hql) {
		return hotDotDAO.searchHotDot(hql);
	}
	
	
	public List<HotDot> searchHotDotByHotDot(HotDot hotDot ,String beginDate , String endDate){
		
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from HotDot h where 1=1 ");
		
		if(null!= hotDot){
			
			if(StringHelper.isNotEmpty(hotDot.getSubscriberId())){
				
				hql.append(" and h.subscriberId = '"+hotDot.getSubscriberId()+"'");
				
			}
			
		}
		
		if(StringHelper.isNotEmpty(beginDate)){
			
			hql.append(" and h.ts >= '"+beginDate+" 00:00:00'");
			
		}
		
		if(StringHelper.isNotEmpty(endDate)){
			
			hql.append(" and h.ts <= '"+endDate+" 23:59:59'");
			
		}
		
		hql.append(" order by h.ts desc");
		
		
		List<HotDot> List = hotDotDAO.searchHotDot(hql.toString());
		
		for (HotDot ht : List) {
			Subscriber sub = subscriberService.querySubscriberById(ht.getSubscriberId());
			ht.setSubscriberName(sub.getName());
		}
		
		return List;
	}
	

	 
}
