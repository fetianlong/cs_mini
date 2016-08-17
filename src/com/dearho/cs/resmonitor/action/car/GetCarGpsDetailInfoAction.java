package com.dearho.cs.resmonitor.action.car;


import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.resmonitor.pojo.CarLocation;
import com.dearho.cs.resmonitor.service.CarLocationService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 获取车辆详细信息
 * @version 1.0 2015年4月24日 下午3:25:22
 */
public class GetCarGpsDetailInfoAction extends AbstractAction{


	/**
	 * 
	 */
	private static final long serialVersionUID = -2373643031610305608L;
	private String id;
	private Page<CarLocation> page = new Page<CarLocation>();
	
	private CarLocationService carLocationService;
	
	private DeviceBindingService deviceBindingService;
	
	public GetCarGpsDetailInfoAction() {
		super();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	@Override
	public String process() {
		if(StringHelper.isEmpty(page.getOrderByString() )){
			page.setOrderString("ts");
			page.setOrderFlag(1);
		}
		
		try {
			List<DeviceBinding> deviceBindings = deviceBindingService.queryBindByCarId(id, 1);
			if(deviceBindings == null || deviceBindings.size() <= 0){
				return Action.ERROR;
			}
			page = carLocationService.getLocationsByDeviceNo(page, deviceBindings.get(0).getDeviceNo());
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Page<CarLocation> getPage() {
		return page;
	}

	public void setPage(Page<CarLocation> page) {
		this.page = page;
	}

	public CarLocationService getCarLocationService() {
		return carLocationService;
	}

	public void setCarLocationService(CarLocationService carLocationService) {
		this.carLocationService = carLocationService;
	}

	public DeviceBindingService getDeviceBindingService() {
		return deviceBindingService;
	}

	public void setDeviceBindingService(DeviceBindingService deviceBindingService) {
		this.deviceBindingService = deviceBindingService;
	}
	
	
	
}
