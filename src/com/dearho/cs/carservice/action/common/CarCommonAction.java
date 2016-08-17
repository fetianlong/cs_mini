package com.dearho.cs.carservice.action.common;

import com.dearho.cs.carservice.pojo.CarCommon;
import com.dearho.cs.carservice.service.CarCommonService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.StringHelper;

public class CarCommonAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private CarCommonService carCommonService;
	
	private Page<CarCommon> page=new Page<CarCommon>();
	
	private CarCommon carCommon;
	
	private String carId;
	
	private String [] imgurl;
	
	/**
	 * 分页
	 */
	public CarCommonAction() {
		super();
		page=new Page<CarCommon>();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	@Override
	public String process() {
		return null;
	}

	/**
	 * 查询车辆评价
	 * @return
	 */
	public String searchCarCommon(){
		if(carId!=null){
			page = carCommonService.searchCarCommon(page,carId);
		}
		return SUCCESS;		
	}
	
	/**
	 * 评价详情
	 * @return
	 */
	public String caCommonDetail(){
		
		carCommon = carCommonService.queryCarCommonById(carCommon.getId());
		if(StringHelper.isNotEmpty(carCommon.getCarImg())){
			imgurl = carCommon.getCarImg().split("\\|");
		}
		return SUCCESS;
	}
	
	public CarCommonService getCarCommonService() {
		return carCommonService;
	}
 
	public void setCarCommonService(CarCommonService carCommonService) {
		this.carCommonService = carCommonService;
	}
 
	public Page<CarCommon> getPage() {
		return page;
	}

	public void setPage(Page<CarCommon> page) {
		this.page = page;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}

	public CarCommon getCarCommon() {
		return carCommon;
	}

	public void setCarCommon(CarCommon carCommon) {
		this.carCommon = carCommon;
	}

	public String[] getImgurl() {
		return imgurl;
	}

	public void setImgurl(String[] imgurl) {
		this.imgurl = imgurl;
	}
	
	
	

}
