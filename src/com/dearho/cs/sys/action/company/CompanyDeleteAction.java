package com.dearho.cs.sys.action.company;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.service.CompanyService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:22:43
 */
public class CompanyDeleteAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] checkdel;
	
	private CompanyService companyService;
	
	private String id;

	@Override
	public String process() {
		try {
			if(checkdel != null && checkdel.length > 0){
				companyService.deleteCompany(checkdel);
			}
			else if(StringHelper.isNotEmpty(id)){
				checkdel = new String[]{id};
				companyService.deleteCompany(checkdel);
			}
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功！");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！");
			return Action.ERROR;
		}
	}

	public String[] getCheckdel() {
		return checkdel;
	}

	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
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

	
}
