package com.dearho.cs.carservice.action;



import com.dearho.cs.carservice.pojo.CarBreak;
import com.dearho.cs.carservice.service.CarBreakService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.StringHelper;


/**
 *  损坏上报 ACTION
 * @fileName:CarBreakAction.java
 * @author:赵振明
 * @createTime:2016年7月12日下午5:04:05
 *
 */
public class CarBreakAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private Page<CarBreak> page;
	
	private CarBreak carBreak;
	
	private String [] imgurl;
	
	private  CarBreakService carBreakService;
	
	private SubscriberService subscriberService;
	
	
	public Page<CarBreak> getPage() {
		return page;
	}

	public void setPage(Page<CarBreak> page) {
		this.page = page;
	}

	public CarBreak getCarBreak() {
		return carBreak;
	}

	public void setCarBreak(CarBreak carBreak) {
		this.carBreak = carBreak;
	}

	public CarBreakService getCarBreakService() {
		return carBreakService;
	}

	public void setCarBreakService(CarBreakService carBreakService) {
		this.carBreakService = carBreakService;
	}
 
	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public String[] getImgurl() {
		return imgurl;
	}

	public void setImgurl(String[] imgurl) {
		this.imgurl = imgurl;
	}

	public CarBreakAction() {
		super();
		page=new Page<CarBreak>();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}


	

	
	@Override
	public String process() {
		page=carBreakService.queryCarBreak(page, carBreak);
		
		return SUCCESS;
	}

	
	/**
	 * TODO:carBreakDetail(查看上报细节，描述和图片 请求)
	 * @author:赵振明
	 * @createTime:2016年7月15日上午10:26:56
	 * @return:String
	 * @return
	 */
	public String carBreakDetail(){
		
		carBreak = carBreakService.queryCarBreakById(carBreak.getId());
		if(StringHelper.isNotEmpty(carBreak.getBreakImg())){
			imgurl = carBreak.getBreakImg().split("\\|");
		}
		return SUCCESS;
	}
	
	
	

}
