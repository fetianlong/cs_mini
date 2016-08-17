/**
 * 
 */
package com.dearho.cs.advice.service.impl;

import com.dearho.cs.advice.dao.FeedbackDao;
import com.dearho.cs.advice.pojo.Feedback;
import com.dearho.cs.advice.service.FeedbackService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.util.StringHelper;

/**
 * @author jyt
 * @since 2016年7月15日 上午11:54:33
 */
public class FeedbackServiceImpl  implements FeedbackService{

	private FeedbackDao feedbackDao;

	public FeedbackDao getFeedbackDao() {
		return feedbackDao;
	}

	public void setFeedbackDao(FeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}

	@Override
	public Page<Feedback> queryFeedBack(Feedback feedback, Page<Feedback> page) {
		StringBuffer sb=new StringBuffer();
		if(feedback !=null){
			sb.append("select  new Feedback( a.id"
					+ ", a.subscriberId,a.feedbackDesc,a.contactType,a.feedbackImg"
					+ ",a.state,a.comment,a.ts,b.name ) "
					+ " from Feedback a ,  Subscriber  b  where  a.subscriberId = b.id  ");
			 
			if(StringHelper.isNotEmpty(feedback.getSubscriberId())){
				sb.append(" and a.subscriberId = '"+feedback.getSubscriberId()+"'");
			}
			if(StringHelper.isNotEmpty(feedback.getName())){
				sb.append(" and b.name like '%"+feedback.getName()+"%'");
			}
			if(StringHelper.isNotEmpty(feedback.getState())){
				sb.append(" and a.state = '"+feedback.getState()+"'");
			}
			if(StringHelper.isNotEmpty(feedback.getContactType())){
				sb.append(" and a.contactType like '%"+feedback.getContactType()+"%'");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.ts desc" : "order by a."+page.getOrderByString().replace("order by", ""));
		page=feedbackDao.queryFeedbackByPage(page, sb.toString());
		
		return page;
	}

	@Override
	public Feedback getFeedbackById(String id) {
		return feedbackDao.getFeedbackById(id);
	}

	@Override
	public void updateFeedbackState(Feedback feedback) throws Exception {
		
		feedbackDao.updateFeedback(feedback);
	}

	@Override
	public void addFeedback(Feedback feedback) {
		feedbackDao.addFeedback(feedback);
	}

	@Override
	public void delFeedBackByIds(String[] ids) {
		feedbackDao.delFeedBackByIds(ids);
	}
	
	
}
