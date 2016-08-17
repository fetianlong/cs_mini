package com.dearho.cs.device.action;

import java.util.List;

import com.dearho.cs.device.pojo.Device;
import com.dearho.cs.device.service.DeviceService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Company;
import com.dearho.cs.sys.service.CompanyService;
import com.dearho.cs.util.StringHelper;

public class DeviceGetAction extends AbstractAction {
	
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private Device device;
	
	private DeviceService deviceService;
	
	private String state;
	
	private CompanyService companyService;
	
	
	
	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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

	public CompanyService getCompanyService() {
		return companyService;
	}
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	public List<Company> getCompanys(String typeCode){
		return companyService.searchCompanyByBizType(typeCode);
	}

	@Override
	public String process() {
		if(StringHelper.isNotEmpty(id)){
			device=deviceService.queryCarSetById(id);
			if(device==null){
				device=new Device();
			}
		}else{
			device=new Device();
		}
		return state;
	}

}
