package com.dearho.cs.carservice.action.patrol.parking;

import java.util.Date;
import java.util.List;


import com.dearho.cs.carservice.pojo.ParkingPatrol;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:30:49
 */
public class ParkingPatrolUpdateAction extends ParkingPatrolAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String process(){
		try {
			List<ParkingPatrol> hasAccidents = getParkingPatrolService().searchParkingPatrol(getParkingPatrol());
			if(hasAccidents != null && hasAccidents.size() > 0 && !hasAccidents.get(0).getId().equals(getParkingPatrol().getId())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！编码重复！");
				return Action.ERROR;
			}
			ParkingPatrol updateParkingPatrol = getParkingPatrol();
			
			ParkingPatrol oldCm = getParkingPatrolService().searchParkingPatrolById(getParkingPatrol().getId());
			
//			if(!StringHelper.isRealNull(oldCm.getStatus()) && !StringHelper.isRealNull(updateMaintenance.getStatus())){
//				if(oldCm.getStatus().compareTo(updateMaintenance.getStatus()) > 0){
//					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！状态不能后退！");
//					return Action.ERROR;
//				}
//			}
			if(!oldCm.getDotId().equals(updateParkingPatrol.getDotId())){
				boolean b = getParkingPatrolService().checkHasChildPatrol(updateParkingPatrol.getId());
				if(b){
					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "网点巡检单下有车辆巡检单，不能更换网点！");
					return Action.ERROR;
				}
			}
			updateParkingPatrol.setCreateTime(oldCm.getCreateTime());
			updateParkingPatrol.setCreatorId(oldCm.getCreatorId());
			
			updateParkingPatrol.setUpdateTime(new Date());
			updateParkingPatrol.setTs(new Date());
			getParkingPatrolService().updateParkingPatrol(updateParkingPatrol);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, updateParkingPatrol.getId());
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return Action.ERROR;
		}
	}
}
