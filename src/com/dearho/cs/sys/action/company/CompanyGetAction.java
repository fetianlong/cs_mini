package com.dearho.cs.sys.action.company;


import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Company;
import com.dearho.cs.sys.service.CompanyService;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:31
 */
public class CompanyGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private CompanyService companyService;
	
	private Company company;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			company = new Company();
		}else{
			Company eEntity = companyService.searchCompanyById(id);
			if (eEntity == null){
				company = new Company();
			}
			else {
				company = eEntity;
			}
			
		}
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	
}
