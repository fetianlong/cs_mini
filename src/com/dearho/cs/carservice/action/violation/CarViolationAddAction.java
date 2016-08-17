package com.dearho.cs.carservice.action.violation;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.carservice.pojo.CarViolation;
import com.dearho.cs.carservice.service.CarViolationService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.NumberUtil;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:14:43
 */
public class CarViolationAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarViolationService carViolationService;
	
	private CarViolation carViolation;
	
	public CarViolationAddAction(){
		super();
		carViolation = new CarViolation();
	}
	
	public String process(){
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user != null){
				carViolation.setCreatorId(user.getId());
			}
			carViolation.setIsDiscard(0);
			carViolation.setCreateTime(new Date());
			carViolation.setTs(new Date());
			
			List<CarViolation> list = carViolationService.searchCarViolationCurrentDate();
			Dict dict = DictUtil.getDictByCodes("carManageHeadCode", "carIllegal");
			String headCode = NumberUtil.createFormNo(dict.getCnName(), list.size());
			carViolation.setCode(headCode);
			
			carViolationService.addCarViolation(carViolation);
			
			//记录日志
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("编号", carViolation.getCode());
			SystemOperateLogUtil.sysAddOperateLog(carViolation.getId(), user, "车辆违章管理", contentMap);
			
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "保存成功！");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "保存失败！");
			return Action.ERROR;
		}
	}

	public CarViolationService getCarViolationService() {
		return carViolationService;
	}

	public void setCarViolationService(CarViolationService carViolationService) {
		this.carViolationService = carViolationService;
	}

	public CarViolation getCarViolation() {
		return carViolation;
	}

	public void setCarViolation(CarViolation carViolation) {
		this.carViolation = carViolation;
	}
	



}
