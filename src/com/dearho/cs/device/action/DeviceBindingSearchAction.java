package com.dearho.cs.device.action;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Company;
import com.dearho.cs.sys.service.CompanyService;
import com.dearho.cs.util.StringHelper;

public class DeviceBindingSearchAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	private DeviceBindingService deviceBindingService;
	
	private Page<DeviceBinding> page=new Page<DeviceBinding>();
	
	private DeviceBinding deviceBinding;
	
	private CompanyService companyService;
	private List<Company> companys;
	
	public List<Company> getCompanys() {
		return companys;
	}
	public void setCompanys(List<Company> companys) {
		this.companys = companys;
	}
	public List<Company> getCompanys(String typeCode){
		companys = companyService.searchCompanyByBizType(typeCode);
		return companys;
	}
	public CompanyService getCompanyService() {
		return companyService;
	}
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	public String getCompNameById(String id){
		if(companys == null){
			if(StringHelper.isEmpty(id)){
				return "";
			}
			else{
				Company c = companyService.searchCompanyById(id);
				if(c != null)
				return c.getName();
				
			}
		}
		return "";
	}
	
	public DeviceBindingSearchAction() {
		super();
		deviceBinding=new DeviceBinding();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}




	public DeviceBindingService getDeviceBindingService() {
		return deviceBindingService;
	}




	public void setDeviceBindingService(DeviceBindingService deviceBindingService) {
		this.deviceBindingService = deviceBindingService;
	}




	public Page<DeviceBinding> getPage() {
		return page;
	}


	public void setPage(Page<DeviceBinding> page) {
		this.page = page;
	}




	public DeviceBinding getDeviceBinding() {
		return deviceBinding;
	}




	public void setDeviceBinding(DeviceBinding deviceBinding) {
		this.deviceBinding = deviceBinding;
	}




	@Override
	public String process() {
		page=deviceBindingService.queryCarBindPages(page, deviceBinding);
		return SUCCESS;
	}

}
