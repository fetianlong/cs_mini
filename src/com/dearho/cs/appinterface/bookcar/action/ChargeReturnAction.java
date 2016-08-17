package com.dearho.cs.appinterface.bookcar.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dearho.cs.appinterface.bookcar.entity.ChargeStationVo;
import com.dearho.cs.charge.pojo.ChargeStation;
import com.dearho.cs.charge.service.ChargeStationService;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.resmonitor.service.CarRealtimeStateService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.StringHelper;

public class ChargeReturnAction extends AbstractAction{

	private static final long serialVersionUID = 7793126067729537895L;
	
	private ChargeStationService chargeStationService;
	private CarRealtimeStateService carRealtimeStateService;

	@Override
	public String process() {
		return SUCCESS;
	}
	
	public String queryChargeStations(){
		List<ChargeStation> stations = chargeStationService.searchChargeStationByCode(null);
		
		if(stations == null || stations.size() <= 0){
			result = Ajax.AppJsonResult(1, "没有充电站信息！");
			return SUCCESS;
		}
		String carId = getRequest().getParameter("carId");
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(StringHelper.isNotEmpty(carId)){
			CarRealtimeState crs = carRealtimeStateService.getCarRealTimeState(carId);
			if(crs != null){
				map.put("carLat", crs.getLat());
				map.put("carLng", crs.getLng());
			}
		}
		List<ChargeStationVo> vos = new ArrayList<ChargeStationVo>();
		for (ChargeStation sts : stations) {
			ChargeStationVo vo = new ChargeStationVo();
			vo.setAcableNum(sts.getAcableNum());
			vo.setAddress(sts.getAddress());
			vo.setDcableNum(sts.getDcableNum());
			vo.setLat(sts.getLat());
			vo.setLng(sts.getLng());
			vos.add(vo);
		}
		
		map.put("chargeStations", vos);
		result = Ajax.AppJsonResult(0,map);
		return SUCCESS;
	}
	
	
	public ChargeStationService getChargeStationService() {
		return chargeStationService;
	}
	public void setChargeStationService(
			ChargeStationService chargeStationService) {
		this.chargeStationService = chargeStationService;
	}
	public CarRealtimeStateService getCarRealtimeStateService() {
		return carRealtimeStateService;
	}
	public void setCarRealtimeStateService(
			CarRealtimeStateService carRealtimeStateService) {
		this.carRealtimeStateService = carRealtimeStateService;
	}

}
