package com.dearho.cs.subscriber.action.confirm;

/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberCheckGetAction.java creation date:[2015-5-18 上午10:04:40] by liusong
 *http://www.dearho.com
 */



import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.subscriber.pojo.SubscriberConfirm;
import com.dearho.cs.subscriber.service.SubscriberConfirmService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.sys.service.impl.UserServiceImpl;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-18
 *
 */
public class SubscriberConfirmGetAction extends AbstractAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6988611144656386336L;
	private SubscriberConfirmService subscriberConfirmService;
	private SubscriberConfirm subscriberConfirm ;
	private UserService userService;
	
	private List<String> resultList;
	
	public SubscriberConfirmGetAction() {
		super();
		subscriberConfirm=new SubscriberConfirm();
	}


	
	@Override
	public String process() {
		subscriberConfirm=subscriberConfirmService.querySubscriberConfirmById(subscriberConfirm.getId());
		
		if(subscriberConfirm.getBusinessFlow()!=null && StringUtils.isNotEmpty(subscriberConfirm.getBusinessFlow().getReviewerId())){
			User user=userService.searchUserByID(subscriberConfirm.getBusinessFlow().getReviewerId());
			if(user!=null){
				subscriberConfirm.getBusinessFlow().setReviewerId(user.getName());
			}
		}
		
		if(subscriberConfirm.getBusinessFlow()!=null && StringUtils.isNotEmpty(subscriberConfirm.getBusinessFlow().getResultDesc())){
			String resultDesc=subscriberConfirm.getBusinessFlow().getResultDesc();
			
			resultList=Arrays.asList(resultDesc.split(","));
			
		}
		getRequest().setAttribute("type", getRequest().getParameter("type"));
		
		return SUCCESS;
	}


	public String showOriginalImage(){
		String imageType=getRequest().getParameter("imageType");
		String imageScr=null;
		if("idCard".equals(imageType)){
			imageScr=subscriberConfirm.getSubscriber().getIdCardImg();
		}else if("drivingCard".equals(imageType) ){
			imageScr=subscriberConfirm.getSubscriber().getDrivingLicenseImg();
		}
		getRequest().setAttribute("imageScr", imageScr);
		return SUCCESS;
	}
	

	public SubscriberConfirmService getSubscriberConfirmService() {
		return subscriberConfirmService;
	}



	public void setSubscriberConfirmService(
			SubscriberConfirmService subscriberConfirmService) {
		this.subscriberConfirmService = subscriberConfirmService;
	}



	public SubscriberConfirm getSubscriberConfirm() {
		return subscriberConfirm;
	}



	public void setSubscriberConfirm(SubscriberConfirm subscriberConfirm) {
		this.subscriberConfirm = subscriberConfirm;
	}



	



	


	public UserService getUserService() {
		return userService;
	}



	public void setUserService(UserService userService) {
		this.userService = userService;
	}



	public List<String> getResultList() {
		return resultList;
	}



	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}
	
	
	
	
}
