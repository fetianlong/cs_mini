package com.dearho.cs.carservice.action.patrol.parking;

import com.dearho.cs.carservice.pojo.ParkingPatrol;
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
public class ParkingPatrolChangeDiscardAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ParkingPatrolService parkingPatrolService;
	
	private String id;
	
	private String isDiscard;

	@Override
	public String process() {
		try {
			if(StringHelper.isEmpty(id) || StringHelper.isEmpty(isDiscard)){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "废除状态更新失败！");
				return Action.ERROR;
			}
			ParkingPatrol cm = parkingPatrolService.searchParkingPatrolById(id);
			if(cm == null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "废除状态更新失败！");
				return Action.ERROR;
			}
			cm.setIsDiscard("0".equals(isDiscard) ? 1 : "1".equals(isDiscard) ? 0 : -1);
			parkingPatrolService.updateParkingPatrol(cm);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "废除状态更新成功！");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "废除状态更新失败！");
			return Action.ERROR;
		}
	}

	public String getIsDiscard() {
		return isDiscard;
	}
	public void setIsDiscard(String isDiscard) {
		this.isDiscard = isDiscard;
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
