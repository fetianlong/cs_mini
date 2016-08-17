package com.dearho.cs.carservice.action.repair;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarRepair;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:30:49
 */
public class CarRepairUpdateAction extends CarRepairAddAction{

	private static final long serialVersionUID = 1L;
	private CarService carService;
	
	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}

	public String process(){
		try {
			List<CarRepair> hasAccidents = getCarRepairService().searchCarRepair(getCarRepair());
			if(hasAccidents != null && hasAccidents.size() > 0 && !hasAccidents.get(0).getId().equals(getCarRepair().getId())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！编码重复！");
				return Action.ERROR;
			}
			CarRepair carRepair = getCarRepair();
			
			CarRepair oldCr = getCarRepairService().searchCarRepairById(getCarRepair().getId());
			
//			if(!StringHelper.isRealNull(oldCm.getStatus()) && !StringHelper.isRealNull(updateMaintenance.getStatus())){
//				if(oldCm.getStatus().compareTo(updateMaintenance.getStatus()) > 0){
//					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！状态不能后退！");
//					return Action.ERROR;
//				}
//			}
			
			carRepair.setCreateTime(oldCr.getCreateTime());
			carRepair.setCreatorId(oldCr.getCreatorId());
			
			carRepair.setUpdateTime(new Date());
			carRepair.setTs(new Date());
			getCarRepairService().updateCarRepair(carRepair);
			
			//更新该车辆的状态
			Car car = carService.queryCarById(getCarRepair().getCarId());
			if(car.getBizState().equals(DictUtil.getDictByCodes("carBizState" ,"2").getId()) 
					&& getCarRepair().getStatus().equals(DictUtil.getDictByCodes("repairStatus" ,"2").getId())){//如果当前车辆状态是修理中，而且维修完成，则车辆状态改为未租赁
				car.setBizState(DictUtil.getDictByCodes("carBizState" ,"0").getId());
				carService.updateCar(car);
			}
			
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			
			CarRepair newCm = getCarRepairService().searchCarRepairById(carRepair.getId());
			SystemOperateLogUtil.sysUpdateOperateLog(oldCr, newCm, user, "车辆维修管理", newCm.getCode());
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return Action.ERROR;
		}
	}
}
