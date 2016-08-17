package com.dearho.cs.advice.action;

import com.dearho.cs.advice.pojo.Feedback;
import com.dearho.cs.advice.service.FeedbackService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;

/**
 *  一键投诉 Action
 * @author jyt
 * @since 2016年7月15日 上午11:48:38
 */
public class FeedbackAction  extends AbstractAction {

	private static final long serialVersionUID = 2736156581752182268L;

	private String id;
	
	private String [] imgurl;
	
	private Feedback feedback;
	
	private Page<Feedback> page ;
	
	
	private FeedbackService feedbackService;
	
	/**
	 * queryFeedbacks
	 */
	@Override
	public String process() {
		
		try {
			page = this.feedbackService.queryFeedBack(feedback,page);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		
	}

	
	/**
	 * 获取一键投诉详细信息
	 * @return
	 */
	public String getFeedbackById(){
		try {
			feedback = this.feedbackService.getFeedbackById( id);
			if(StringHelper.isNotEmpty(feedback.getFeedbackImg())){
				imgurl = feedback.getFeedbackImg().split("\\|");
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 *  修改投诉信息（处理状态）
	 * @return
	 */
	
	public String updateFeedbackState(){
		
		try {
			Feedback fb = this.feedbackService.getFeedbackById( feedback.getId());
			fb.setState(feedback.getState());
			fb.setComment(feedback.getComment());
			 this.feedbackService.updateFeedbackState( fb);
			 result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return ERROR;
		}
	}
	
	
	public FeedbackAction(){
		super();
		feedback = new Feedback();
		page = new Page<Feedback>();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getImgurl() {
		return imgurl;
	}

	public void setImgurl(String[] imgurl) {
		this.imgurl = imgurl;
	}

	public Feedback getFeedback() {
		return feedback;
	}

	public void setFeedback(Feedback feedback) {
		this.feedback = feedback;
	}

	public Page<Feedback> getPage() {
		return page;
	}

	public void setPage(Page<Feedback> page) {
		this.page = page;
	}

	public FeedbackService getFeedbackService() {
		return feedbackService;
	}

	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}
	
}
