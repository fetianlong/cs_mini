package com.dearho.cs.carservice.action.maintenance;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.dearho.cs.carservice.pojo.CarMaintenance;
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
 * @version 1.0 2015年4月22日 上午10:30:49
 */
public class CarMaintenanceUpdateAction extends CarMaintenanceAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String process(){
		try {
			CarMaintenance updateMaintenance = getCarMaintenance();
			CarMaintenance oldCm = getCarMaintenanceService().searchCarMaintenanceById(getCarMaintenance().getId());
			
//			if(!StringHelper.isRealNull(oldCm.getStatus()) && !StringHelper.isRealNull(updateMaintenance.getStatus())){
//				if(oldCm.getStatus().compareTo(updateMaintenance.getStatus()) > 0){
//					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！状态不能后退！");
//					return Action.ERROR;
//				}
//			}
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(StringHelper.isRealNull(oldCm.getStatusChangePerson1()) ){
				if(user != null){
					updateMaintenance.setStatusChangePerson1(user.getId());
				}
				updateMaintenance.setStatusChangeTime1(new Date());
			}
			else if(StringHelper.isRealNull(oldCm.getStatusChangePerson2()) ){
				updateMaintenance.setStatusChangePerson1(oldCm.getStatusChangePerson1());
				updateMaintenance.setStatusChangeTime1(oldCm.getStatusChangeTime1());
				if(user != null){
					updateMaintenance.setStatusChangePerson2(user.getId());
				}
				updateMaintenance.setStatusChangeTime2(new Date());
			}
			else if(StringHelper.isRealNull(oldCm.getStatusChangePerson3()) ){
				updateMaintenance.setStatusChangePerson1(oldCm.getStatusChangePerson1());
				updateMaintenance.setStatusChangeTime1(oldCm.getStatusChangeTime1());
				updateMaintenance.setStatusChangePerson2(oldCm.getStatusChangePerson2());
				updateMaintenance.setStatusChangeTime2(oldCm.getStatusChangeTime2());
				if(user != null){
					updateMaintenance.setStatusChangePerson3(user.getId());
				}
				updateMaintenance.setStatusChangeTime3(new Date());
			}
			
			updateMaintenance.setCreateTime(oldCm.getCreateTime());
			updateMaintenance.setCreatorId(oldCm.getCreatorId());
			
			updateMaintenance.setUpdateTime(new Date());
			updateMaintenance.setTs(new Date());
			getCarMaintenanceService().updateCarMaintenance(updateMaintenance);
			
			CarMaintenance newCm = getCarMaintenanceService().searchCarMaintenanceById(updateMaintenance.getId());
			SystemOperateLogUtil.sysUpdateOperateLog(oldCm, newCm, user, "车辆保养管理", newCm.getCode());
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return Action.ERROR;
		}
	}
}
