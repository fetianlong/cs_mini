/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file SubscriberChangePassword.java creation date:[2015-5-27 上午11:14:06] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.subscriber.action.personalCenter;

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
 * @Version 1.0,2015-5-27
 *
 */
public class SubscriberBaseInfoGetAction extends AbstractAction{
	
	

	private static final long serialVersionUID = -9061235443319548376L;
	private SubscriberService subscriberService;
	private OrdersService ordersService;
	private WechatUserInfoService  wechatUserInfoService;
	private Subscriber subscriber ;
	private WechatUserInfo wechatUserInfo;
	
	private List<AdministrativeArea> citys=new ArrayList<AdministrativeArea>();
	private List<AdministrativeArea> areas=new ArrayList<AdministrativeArea>();
	private List<BranchDot> dots=new ArrayList<BranchDot>();

	public SubscriberBaseInfoGetAction(){
		super();
		subscriber=new Subscriber();
	}
	@Override
	public String process() {
		
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		subscriber=subscriberService.querySubscriberById(subscriber.getId());
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
		
		//更新session 用户状态可能变更
		getSession().setAttribute(Constants.SESSION_SUBSCRIBER,subscriber);
		return SUCCESS;
	}
	
	public String showBillInfo(){
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		return SUCCESS;
	}
	
	public String centerIndex(){
		if(getSession().getAttribute(Constants.SESSION_SUBSCRIBER)==null){
			return LOGIN;
		}
		return SUCCESS;
	}
	
	public String searchCity(){
		citys=ordersService.queryAreaByCity(subscriber.getCity());
		if(citys!=null && citys.size()>0){
			areas=ordersService.queryAreaByCon(citys.get(0));
		}
		result=Ajax.JSONResult(0, "", areas);
		return 	SUCCESS;
	}
	public SubscriberService getSubscriberService() {
		return subscriberService;
	}
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
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
	public List<AdministrativeArea> getCitys() {
		return citys;
	}
	public void setCitys(List<AdministrativeArea> citys) {
		this.citys = citys;
	}
	public WechatUserInfo getWechatUserInfo() {
		return wechatUserInfo;
	}
	public void setWechatUserInfo(WechatUserInfo wechatUserInfo) {
		this.wechatUserInfo = wechatUserInfo;
	}
	public void setWechatUserInfoService(WechatUserInfoService wechatUserInfoService) {
		this.wechatUserInfoService = wechatUserInfoService;
	}
	
	
	
}
