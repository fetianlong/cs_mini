package com.dearho.cs.advice.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.advice.dao.FeedbackDao;
import com.dearho.cs.advice.pojo.Feedback;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

/**
 * @author jyt
 * @since 2016年7月15日 下午1:38:41
 */
public class FeedbackDaoImpl extends AbstractDaoSupport  implements FeedbackDao {

	@Override
	public Feedback getFeedbackById(String id) {
		return this.get(Feedback.class, id);
	}

	@Override
	public Page<Feedback> queryFeedbackByPage(Page<Feedback> page, String hql) {
		Page<Feedback> resultPage=pageCache(Feedback.class, page, hql);
		List<Serializable> list = resultPage.getmResults();
		List<Feedback >fbList = new ArrayList<> ();
		if(null != list && list.size()>0)
		{
			for(Serializable s: list){
				fbList.add((Feedback) s);
			}
		}
		resultPage.setResults( fbList);
		return resultPage;
	}
	
	
	
	@Override
	public void updateFeedback(Feedback t) {
		this.updateEntity(t);
	}

	@Override
	public void addFeedback(Feedback t) {
		this.addEntity(t);
	}

	@Override
	public void delFeedBackByIds(String[] ids) {
		this.delFeedBackByIds(ids);
	}

}
