package com.dearho.cs.carservice.action.insurance;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.carservice.pojo.CarInsurance;
import com.dearho.cs.carservice.service.CarInsuranceService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:14:43
 */
public class CarInsuranceAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarInsuranceService carInsuranceService;
	
	private CarInsurance carInsurance;
	
	public CarInsuranceAddAction(){
		super();
		carInsurance = new CarInsurance();
	}
	
	public String process(){
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user != null){
				carInsurance.setCreatorId(user.getId());
			}
			carInsurance.setIsDiscard(0);
			carInsurance.setCreateTime(new Date());
			carInsurance.setTs(new Date());
			
			carInsuranceService.addCarInsurance(carInsurance);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "保存成功！");
			
			//记录日志
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("保单编号", carInsurance.getCode());
			SystemOperateLogUtil.sysAddOperateLog(carInsurance.getId(), user, "车辆保单管理", contentMap);
			
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "保存失败！");
			return Action.ERROR;
		}
	}

	public CarInsuranceService getCarInsuranceService() {
		return carInsuranceService;
	}

	public void setCarInsuranceService(CarInsuranceService carInsuranceService) {
		this.carInsuranceService = carInsuranceService;
	}

	public CarInsurance getCarInsurance() {
		return carInsurance;
	}

	public void setCarInsurance(CarInsurance carInsurance) {
		this.carInsurance = carInsurance;
	}
	



}
