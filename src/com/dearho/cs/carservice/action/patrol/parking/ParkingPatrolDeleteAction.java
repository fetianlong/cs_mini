package com.dearho.cs.carservice.action.patrol.parking;

import com.dearho.cs.carservice.service.ParkingPatrolService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:22:43
 */
public class ParkingPatrolDeleteAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] checkdel;
	
	private ParkingPatrolService parkingPatrolService;
	
	private String id;

	@Override
	public String process() {
		try {
			if(checkdel == null && StringHelper.isNotEmpty(id)){
				checkdel = new String[]{id};
			}
			boolean flag = true;
			for (String pid : checkdel) {
				boolean b = parkingPatrolService.checkHasChildPatrol(pid);
				if(b){
					flag = false;
					continue;
				}
				parkingPatrolService.deleteParkingPatrol(new String[]{pid});
			}
			if(flag){
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功！");
				return Action.SUCCESS;
			}
			else{
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "网点巡检单下有相关的车辆巡检单或充电桩巡检单，不能删除！");
				return Action.ERROR;
			}
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

	public ParkingPatrolService getParkingPatrolService() {
		return parkingPatrolService;
	}

	public void setParkingPatrolService(ParkingPatrolService parkingPatrolService) {
		this.parkingPatrolService = parkingPatrolService;
	}

	
}
