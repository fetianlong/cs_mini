package com.dearho.cs.sys.action.company;

import java.util.Date;
import java.util.List;

import com.dearho.cs.sys.pojo.Company;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:30:49
 */
public class CompanyUpdateAction extends CompanyAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String process(){
		try {
			List<Company> hasCompanys = getCompanyService().searchCompanyByCode(getCompany().getCode());
			if(hasCompanys != null && hasCompanys.size() > 0 && !hasCompanys.get(0).getId().equals(getCompany().getId())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！编码重复！");
				return Action.ERROR;
			}
			Company updateCompany = getCompany();
			updateCompany.setUpdateTime(new Date());
			updateCompany.setTs(new Date());
			getCompanyService().updateCompany(updateCompany);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return Action.ERROR;
		}
	}
}
