package com.dearho.cs.carservice.action.patrol.parking;

import java.util.List;

import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.carservice.service.ParkingPatrolService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.AreaService;
import com.dearho.cs.sys.service.UserService;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class ParkingPatrolSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ParkingPatrolService parkingPatrolService;
	
	private Page<ParkingPatrol> page = new Page<ParkingPatrol>();
	
	private ParkingPatrol parkingPatrol;
	
	private AreaService areaService;
	
	private BranchDotService branchDotService;
	private UserService userService;	
	
	private String state;
	
	public ParkingPatrolSearchAction(){
		super();
		parkingPatrol = new ParkingPatrol();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			page = parkingPatrolService.searchParkingPatrol(page, parkingPatrol);
			if(page != null && page.getResults() != null){
				for (Object obj : page.getResults()) {
					ParkingPatrol cm = (ParkingPatrol)obj;
					BranchDot dot = branchDotService.getBranchDotById(cm.getDotId());
					if(dot != null){
						cm.setDotName(dot.getName());
					}
					User u = userService.searchUserByID(cm.getUserId());
					if(u != null){
						cm.setUserName(u.getName());
					}
				}
			}
			if(state != null && "page".equals(state)){
				return "search";
			}
			else{
				return Action.SUCCESS;
			}
			
		} catch (Exception e) {
			return Action.ERROR;
		}
	}

	public List<AdministrativeArea> getCitys(){
		return areaService.getAreasByParentCode(null);
	}
	public ParkingPatrolService getParkingPatrolService() {
		return parkingPatrolService;
	}

	public void setParkingPatrolService(ParkingPatrolService parkingPatrolService) {
		this.parkingPatrolService = parkingPatrolService;
	}

	public Page<ParkingPatrol> getPage() {
		return page;
	}

	public void setPage(Page<ParkingPatrol> page) {
		this.page = page;
	}

	public ParkingPatrol getParkingPatrol() {
		return parkingPatrol;
	}

	public void setParkingPatrol(ParkingPatrol parkingPatrol) {
		this.parkingPatrol = parkingPatrol;
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}
	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}

	
}
