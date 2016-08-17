package com.dearho.cs.carservice.action.insurance;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.dearho.cs.carservice.pojo.CarInsurance;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:30:49
 */
public class CarInsuranceUpdateAction extends CarInsuranceAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String process(){
		try {
			CarInsurance updateInsurance = getCarInsurance();
			CarInsurance oldCi = getCarInsuranceService().searchCarInsuranceById(getCarInsurance().getId());
			
			updateInsurance.setCreateTime(oldCi.getCreateTime());
			updateInsurance.setCreatorId(oldCi.getCreatorId());
			updateInsurance.setUpdateTime(new Date());
			updateInsurance.setTs(new Date());
			getCarInsuranceService().updateCarInsurance(updateInsurance);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功");
			
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			
			CarInsurance newCi = getCarInsuranceService().searchCarInsuranceById(updateInsurance.getId());
			SystemOperateLogUtil.sysUpdateOperateLog(oldCi, newCi, user, "车辆保养管理", newCi.getCode());
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功");
			
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return Action.ERROR;
		}
	}
}
