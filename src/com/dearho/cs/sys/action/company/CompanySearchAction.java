package com.dearho.cs.sys.action.company;


import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Company;
import com.dearho.cs.sys.service.CompanyService;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class CompanySearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CompanyService companyService;
	
	private Page<Company> page = new Page<Company>();
	
	private Company company;
	
	
	public CompanySearchAction(){
		super();
		company = new Company();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			page = companyService.searchCompany(page, company);
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public Page<Company> getPage() {
		return page;
	}

	public void setPage(Page<Company> page) {
		this.page = page;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	
}
