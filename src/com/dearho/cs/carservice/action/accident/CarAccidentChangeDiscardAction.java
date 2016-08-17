package com.dearho.cs.carservice.action.accident;

import com.dearho.cs.carservice.pojo.CarAccident;
import com.dearho.cs.carservice.service.CarAccidentService;
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
public class CarAccidentChangeDiscardAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarAccidentService carAccidentService;
	
	private String id;
	
	private String isDiscard;

	@Override
	public String process() {
		try {
			if(StringHelper.isEmpty(id) || StringHelper.isEmpty(isDiscard)){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "废除状态更新失败！");
				return Action.ERROR;
			}
			CarAccident ca = carAccidentService.searchCarAccidentById(id);
			if(ca == null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "废除状态更新失败！");
				return Action.ERROR;
			}
			ca.setIsDiscard("0".equals(isDiscard) ? 1 : "1".equals(isDiscard) ? 0 : -1);
			carAccidentService.updateCarAccident(ca);
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

	public CarAccidentService getCarAccidentService() {
		return carAccidentService;
	}

	public void setCarAccidentService(CarAccidentService carAccidentService) {
		this.carAccidentService = carAccidentService;
	}

	
}
