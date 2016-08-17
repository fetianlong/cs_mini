package com.dearho.cs.carservice.action.violation;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.dearho.cs.carservice.pojo.CarViolation;
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
public class CarViolationUpdateAction extends CarViolationAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String process(){
		try {
			
			CarViolation updateViolation = getCarViolation();
			CarViolation oldCv = getCarViolationService().searchCarViolationById(getCarViolation().getId());
			
			if(!StringHelper.isRealNull(oldCv.getBizStatus()) && !StringHelper.isRealNull(updateViolation.getBizStatus())){
				if(oldCv.getBizStatus().compareTo(updateViolation.getBizStatus()) > 0){
					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！业务状态不能后退！");
					return Action.ERROR;
				}
			}
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(StringHelper.isRealNull(oldCv.getStatusChangePerson1()) ){
				if(user != null){
					updateViolation.setStatusChangePerson1(user.getId());
				}
				updateViolation.setStatusChangeTime1(new Date());
			}
			else if(StringHelper.isRealNull(oldCv.getStatusChangePerson2()) ){
				updateViolation.setStatusChangePerson1(oldCv.getStatusChangePerson1());
				updateViolation.setStatusChangeTime1(oldCv.getStatusChangeTime1());
				if(user != null){
					updateViolation.setStatusChangePerson2(user.getId());
				}
				updateViolation.setStatusChangeTime2(new Date());
			}
			else if(StringHelper.isRealNull(oldCv.getStatusChangePerson3()) ){
				updateViolation.setStatusChangePerson1(oldCv.getStatusChangePerson1());
				updateViolation.setStatusChangeTime1(oldCv.getStatusChangeTime1());
				updateViolation.setStatusChangePerson2(oldCv.getStatusChangePerson2());
				updateViolation.setStatusChangeTime2(oldCv.getStatusChangeTime2());
				if(user != null){
					updateViolation.setStatusChangePerson3(user.getId());
				}
				updateViolation.setStatusChangeTime3(new Date());
			}
			updateViolation.setCreateTime(oldCv.getCreateTime());
			updateViolation.setCreatorId(oldCv.getCreatorId());
			updateViolation.setUpdateTime(new Date());
			updateViolation.setTs(new Date());
			getCarViolationService().updateCarViolation(updateViolation);
			CarViolation newCv = getCarViolationService().searchCarViolationById(updateViolation.getId());
			
			SystemOperateLogUtil.sysUpdateOperateLog(oldCv, newCv, user, "车辆违章管理", newCv.getCode());
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功");
			return Action.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return Action.ERROR;
		}
	}
}
