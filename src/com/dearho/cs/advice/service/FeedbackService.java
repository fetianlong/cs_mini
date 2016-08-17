package com.dearho.cs.advice.service;

import com.dearho.cs.advice.pojo.Feedback;
import com.dearho.cs.core.db.page.Page;

/**
 * @author jyt
 * @since 2016年7月15日 上午11:52:14
 */
public interface FeedbackService {

	/**
	 * 通过条件查询投诉列表数据
	 * @param feedback
	 * @param page
	 * @return
	 */
	Page<Feedback> queryFeedBack(Feedback feedback, Page<Feedback> page);

	/**
	 * 通过ID查询 投诉详情
	 * @param id
	 * @return
	 */
	Feedback getFeedbackById(String id);

	/**
	 *  修改投诉状态和处理说明
	 * @param feedback
	 */
	void updateFeedbackState(Feedback feedback) throws Exception;

	/**
	 * 添加投诉
	 * @param feedback
	 * @throws Exception
	 */
	void addFeedback(Feedback feedback);
	
	/**
	 * 删除投诉 By ids
	 * @param ids
	 * @throws Exception
	 */
	void delFeedBackByIds(String[] ids);
}
