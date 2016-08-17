package com.dearho.cs.subscriber.action.subscriber;


/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberCheckGetAction.java creation date:[2015-5-18 上午10:04:40] by liusong
 *http://www.dearho.com
 */



import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.service.WechatUserInfoService;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-5-18
 *
 */
public class SubscriberGetAction extends AbstractAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6988611144656386336L;
	private SubscriberService SubscriberService;
	private Subscriber subscriber ;
	
	private OrdersService ordersService;
	private WechatUserInfoService wechatUserInfoService;
	
	
	private List<AdministrativeArea> citys=new ArrayList<AdministrativeArea>();
	private List<AdministrativeArea> areas=new ArrayList<AdministrativeArea>();
	private List<BranchDot> dots=new ArrayList<BranchDot>();
	
	private WechatUserInfo wechatUserInfo=null;

	protected String result;
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public SubscriberGetAction() {
		super();
		subscriber=new Subscriber();
		
	}

	/**
	 * 根据会员姓名查询 会员是否存在！
	 * @return
	 */
	public String isExistSubscriberName(){
		try {
			Subscriber sub  =SubscriberService.querySubscriberByName(subscriber.getName());
			if( null != sub){
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "");
			}else{
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "");
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "");
			return SUCCESS;
		}
	}
	
	
	@Override
	public String process() {
		subscriber=SubscriberService.querySubscriberAllInfoById(subscriber.getId());
		
		if(!StringUtils.isEmpty(subscriber.getWechatUnionId())){
			wechatUserInfo=wechatUserInfoService.getUserInfoByUnionId(subscriber.getWechatUnionId());
		}
		
		
		
		//默认城市
		/*citys=ordersService.queryAreaByCity(null);
		AdministrativeArea area=null;
		if(!StringUtils.isEmpty(subscriber.getCity()) && citys!=null){
			for(int i=0;i<citys.size();i++){
				if(subscriber.getCity().equals(citys.get(i).getId())){
					area=citys.get(i);
				}
			}
		}
		//默认区域
		if(area!=null){
			areas=ordersService.queryAreaByCon(area);
		}
		if(!StringUtils.isEmpty(subscriber.getDefaultArea())){
			dots=ordersService.queryDotByCon(null);
		}*/
		return SUCCESS;
	}



	public SubscriberService getSubscriberService() {
		return SubscriberService;
	}



	public void setSubscriberService(SubscriberService subscriberService) {
		SubscriberService = subscriberService;
	}



	public Subscriber getSubscriber() {
		return subscriber;
	}



	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}



	public OrdersService getOrdersService() {
		return ordersService;
	}



	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}



	public List<AdministrativeArea> getCitys() {
		return citys;
	}



	public void setCitys(List<AdministrativeArea> citys) {
		this.citys = citys;
	}



	public List<AdministrativeArea> getAreas() {
		return areas;
	}



	public void setAreas(List<AdministrativeArea> areas) {
		this.areas = areas;
	}



	public List<BranchDot> getDots() {
		return dots;
	}



	public void setDots(List<BranchDot> dots) {
		this.dots = dots;
	}



	public WechatUserInfoService getWechatUserInfoService() {
		return wechatUserInfoService;
	}



	public void setWechatUserInfoService(WechatUserInfoService wechatUserInfoService) {
		this.wechatUserInfoService = wechatUserInfoService;
	}



	public WechatUserInfo getWechatUserInfo() {
		return wechatUserInfo;
	}



	public void setWechatUserInfo(WechatUserInfo wechatUserInfo) {
		this.wechatUserInfo = wechatUserInfo;
	}

	
	

	
}
