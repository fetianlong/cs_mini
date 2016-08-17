package com.dearho.cs.carservice.action.patrol.parking;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.carservice.service.ParkingPatrolService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:14:43
 */
public class ParkingPatrolAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ParkingPatrolService parkingPatrolService;
	
	private ParkingPatrol parkingPatrol;
	
	public ParkingPatrolAddAction(){
		super();
		parkingPatrol = new ParkingPatrol();
	}
	
	public String process(){
		try {
			List<ParkingPatrol> list = parkingPatrolService.searchParkingPatrol(parkingPatrol);
			if (list != null && list.size() > 0){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "编码已存在！");
				return Action.ERROR;
			}
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user != null){
				parkingPatrol.setCreatorId(user.getId());
			}
			parkingPatrol.setIsDiscard(0);
			parkingPatrol.setCreateTime(new Date());
			parkingPatrol.setTs(new Date());
			parkingPatrolService.addParkingPatrol(parkingPatrol);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, parkingPatrol.getId());
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "保存失败！");
			return Action.ERROR;
		}
	}

	public ParkingPatrolService getParkingPatrolService() {
		return parkingPatrolService;
	}

	public void setParkingPatrolService(ParkingPatrolService parkingPatrolService) {
		this.parkingPatrolService = parkingPatrolService;
	}

	public ParkingPatrol getParkingPatrol() {
		return parkingPatrol;
	}

	public void setParkingPatrol(ParkingPatrol parkingPatrol) {
		this.parkingPatrol = parkingPatrol;
	}
	



}
