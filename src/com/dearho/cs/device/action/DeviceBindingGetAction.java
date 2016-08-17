package com.dearho.cs.device.action;

import java.util.List;

import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Company;
import com.dearho.cs.sys.service.CompanyService;
import com.dearho.cs.util.StringHelper;

public class DeviceBindingGetAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private DeviceBinding deviceBinding;
	
	private DeviceBindingService deviceBindingService;
	private CompanyService companyService;
	public CompanyService getCompanyService() {
		return companyService;
	}
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	public List<Company> getCompanys(String typeCode){
		return companyService.searchCompanyByBizType(typeCode);
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}




	public DeviceBinding getDeviceBinding() {
		return deviceBinding;
	}


	public void setDeviceBinding(DeviceBinding deviceBinding) {
		this.deviceBinding = deviceBinding;
	}


	public DeviceBindingService getDeviceBindingService() {
		return deviceBindingService;
	}


	public void setDeviceBindingService(DeviceBindingService deviceBindingService) {
		this.deviceBindingService = deviceBindingService;
	}


	public DeviceBindingGetAction() {
		super();
	}


	@Override
	public String process() {
		if(StringHelper.isNotEmpty(id)){
			deviceBinding=deviceBindingService.queryCarBindById(id);
			if(deviceBinding==null){
				deviceBinding=new DeviceBinding();
			}
		}else{
			deviceBinding=new DeviceBinding();
		}
		return SUCCESS;
	}

}
