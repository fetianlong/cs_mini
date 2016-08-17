package com.dearho.cs.carservice.action.maintenance;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.carservice.pojo.CarMaintenance;
import com.dearho.cs.carservice.service.CarMaintenanceService;
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
public class CarMaintenanceAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarMaintenanceService carMaintenanceService;
	
	private CarMaintenance carMaintenance;
	
	public CarMaintenanceAddAction(){
		super();
		carMaintenance = new CarMaintenance();
	}
	
	public String process(){
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user != null){
				carMaintenance.setCreatorId(user.getId());
			}
			carMaintenance.setIsDiscard(0);
			carMaintenance.setCreateTime(new Date());
			carMaintenance.setTs(new Date());
			
			List<CarMaintenance> list = carMaintenanceService.searchCarMaintenanceCurrentDate();
			Dict dict = DictUtil.getDictByCodes("carManageHeadCode", "carMaintenance");
			String headCode = NumberUtil.createFormNo(dict.getCnName(), list.size());
			carMaintenance.setCode(headCode);
			
			carMaintenanceService.addCarMaintenance(carMaintenance);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "保存成功！");
			
			//记录日志
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("编号", carMaintenance.getCode());
			SystemOperateLogUtil.sysAddOperateLog(carMaintenance.getId(), user, "车辆保养管理", contentMap);
			
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "保存失败！");
			return Action.ERROR;
		}
	}

	public CarMaintenanceService getCarMaintenanceService() {
		return carMaintenanceService;
	}

	public void setCarMaintenanceService(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService = carMaintenanceService;
	}

	public CarMaintenance getCarMaintenance() {
		return carMaintenance;
	}

	public void setCarMaintenance(CarMaintenance carMaintenance) {
		this.carMaintenance = carMaintenance;
	}
	



}
