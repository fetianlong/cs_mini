package com.dearho.cs.carservice.action.repair;

import com.dearho.cs.carservice.pojo.CarRepair;
import com.dearho.cs.carservice.service.CarRepairService;
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
public class CarRepairChangeDiscardAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarRepairService carRepairService;
	
	private String id;
	
	private String isDiscard;

	@Override
	public String process() {
		try {
			if(StringHelper.isEmpty(id) || StringHelper.isEmpty(isDiscard)){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "废除状态更新失败！");
				return Action.ERROR;
			}
			CarRepair cm = carRepairService.searchCarRepairById(id);
			if(cm == null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "废除状态更新失败！");
				return Action.ERROR;
			}
			cm.setIsDiscard("0".equals(isDiscard) ? 1 : "1".equals(isDiscard) ? 0 : -1);
			carRepairService.updateCarRepair(cm);
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

	public CarRepairService getCarRepairService() {
		return carRepairService;
	}
	public void setCarRepairService(CarRepairService carRepairService) {
		this.carRepairService = carRepairService;
	}
	
}
