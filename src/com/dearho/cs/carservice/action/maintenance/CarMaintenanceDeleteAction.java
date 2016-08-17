package com.dearho.cs.carservice.action.maintenance;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.carservice.pojo.CarMaintenance;
import com.dearho.cs.carservice.service.CarMaintenanceService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:22:43
 */
public class CarMaintenanceDeleteAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] checkdel;
	
	private CarMaintenanceService carMaintenanceService;
	
	private String id;

	@Override
	public String process() {
		try {
			if(checkdel == null && StringHelper.isNotEmpty(id)){
				checkdel = new String[]{id};
			}
			
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			Map<String, String> contentMap = new HashMap<String, String>();
			for (int i = 0; i < checkdel.length; i++) {
				CarMaintenance data = carMaintenanceService.searchCarMaintenanceById(checkdel[i]);
				contentMap.put("编号", data.getCode());
			}
			SystemOperateLogUtil.sysDeleteOperateLog(user, "车辆保养管理", contentMap);
			
			carMaintenanceService.deleteCarMaintenance(checkdel);
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

	public CarMaintenanceService getCarMaintenanceService() {
		return carMaintenanceService;
	}

	public void setCarMaintenanceService(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService = carMaintenanceService;
	}

	
}
