package com.dearho.cs.sys.action.company;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Company;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.CompanyService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:14:43
 */
public class CompanyAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CompanyService companyService;
	
	private Company company;
	
	public CompanyAddAction(){
		super();
		company = new Company();
	}
	
	public String process(){
		try {
			List<Company> list = companyService.searchCompanyByCode(company.getCode());
			if (list != null && list.size() > 0){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "公司编码已存在！");
				return Action.ERROR;
			}
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user != null){
				company.setCreatorId(user.getId());
			}
			company.setCreateTime(new Date());
			company.setTs(new Date());
			companyService.addCompany(company);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "保存成功！");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "保存失败！");
			return Action.ERROR;
		}
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
