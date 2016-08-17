/**
 * 
 */
package com.dearho.cs.advice.dao;

import com.dearho.cs.advice.pojo.Feedback;
import com.dearho.cs.core.db.page.Page;

/**
 * @author jyt
 * @since 2016年7月15日 下午1:38:11
 */
public interface FeedbackDao {

	/**
	 * 通过Id查询投诉记录
	 * @param id
	 * @return
	 */
	public Feedback getFeedbackById(String id);

	/**
	 *   通过hql  语句查询 投诉(分页)
	 * @param page
	 * @param string
	 * @return
	 */
	public Page<Feedback> queryFeedbackByPage(Page<Feedback> page, String hql);

	/**
	 * 更新一键投诉
	 * @param feedback
	 */
	public void updateFeedback(Feedback feedback);

	/**
	 * @param feedback
	 */
	public void addFeedback(Feedback feedback);

	/**
	 * @param ids
	 */
	public void delFeedBackByIds(String[] ids);
	
}
