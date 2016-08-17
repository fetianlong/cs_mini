package com.dearho.cs.carservice.action.patrol.parking;


import java.util.List;

import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.carservice.service.ParkingPatrolService;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.AreaService;
import com.dearho.cs.sys.service.UserService;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:31
 */
public class ParkingPatrolGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private ParkingPatrolService parkingPatrolService;
	
	private ParkingPatrol parkingPatrol;
	
	private AreaService areaService;
	private BranchDotService branchDotService;
	private UserService userService;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			parkingPatrol = new ParkingPatrol();
		}else{
			ParkingPatrol eEntity = parkingPatrolService.searchParkingPatrolById(id);
			if (eEntity == null){
				parkingPatrol = new ParkingPatrol();
			}
			else {
				parkingPatrol = eEntity;
				BranchDot dot = branchDotService.getBranchDotById(parkingPatrol.getDotId());
				if(dot != null){
					parkingPatrol.setDotName(dot.getName());
					parkingPatrol.setDotId(dot.getId());
				}
				User u = userService.searchUserByID(parkingPatrol.getUserId());
				if(u != null){
					parkingPatrol.setUserName(u.getName());
				}
			}
			
		}
		return SUCCESS;
	}
	public List<AdministrativeArea> getCitys(){
		return areaService.getAreasByParentCode(null);
	}
	public AreaService getAreaService() {
		return areaService;
	}
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
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
	public ParkingPatrol getParkingPatrol() {
		return parkingPatrol;
	}
	public void setParkingPatrol(ParkingPatrol parkingPatrol) {
		this.parkingPatrol = parkingPatrol;
	}
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}
	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
}
