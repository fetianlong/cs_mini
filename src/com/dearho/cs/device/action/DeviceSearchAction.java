package com.dearho.cs.device.action;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.device.pojo.Device;
import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.device.service.DeviceService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Company;
import com.dearho.cs.sys.service.CompanyService;
import com.dearho.cs.util.StringHelper;

public class DeviceSearchAction extends AbstractAction {
	
	
	
	private static final long serialVersionUID = 1L;
	private Page<Device> page=new Page<Device>();
	private Device device;
	private DeviceService deviceService;
	private String state;
	private CompanyService companyService;
	private List<Company> companys;
	private DeviceBindingService deviceBindingService;
	
	public DeviceBindingService getDeviceBindingService() {
		return deviceBindingService;
	}
	public void setDeviceBindingService(
			DeviceBindingService deviceBindingService) {
		this.deviceBindingService = deviceBindingService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}



	public Page<Device> getPage() {
		return page;
	}


	public void setPage(Page<Device> page) {
		this.page = page;
	}




	public Device getDevice() {
		return device;
	}


	public void setDevice(Device device) {
		this.device = device;
	}


	public DeviceService getDeviceService() {
		return deviceService;
	}


	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}
	public List<Company> getCompanys(String typeCode){
		companys = companyService.searchCompanyByBizType(typeCode);
		return companys;
	}
	public String getCompNameById(String id){
		if(companys == null || StringHelper.isEmpty(id)){
			return "";
		}
		for (Company comp : companys) {
			if(id.equals(comp.getId())){
				return comp.getName();
			}
		}
		return "";
	}
	public DeviceSearchAction() {
		super();
		device=new Device();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}


	@Override
	public String process() {
		
		if(StringHelper.isNotEmpty(state)){
			page = deviceService.queryPageBindCarSet(page, device, 0);
			return "search";
		}
		else{
			page=deviceService.queryPageCarSet(page, device);
		}
		return SUCCESS;
	}
	
	public String getState(String deviceId){
		List<DeviceBinding> deviceBindings = deviceBindingService.queryBindByDeviceId(deviceId,null);
		boolean bindType = false;
		if(deviceBindings == null || deviceBindings.size() <= 0){
			return "未绑定";
		}
		else{
			for (DeviceBinding deviceBinding : deviceBindings) {
				if(deviceBinding.getBindType() == 1){
					bindType = true;
					break;
				}
			}
			if(bindType){
				return "已绑定";
			}
			else{
				return "已解绑";
			}
		}
		
	}

}
