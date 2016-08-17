package com.dearho.cs.appinterface.bookcar.action;

import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.appinterface.bookcar.entity.BranchDotVo;
import com.dearho.cs.appinterface.bookcar.entity.DotCarInfo;
import com.dearho.cs.appinterface.bookcar.entity.StrtgInfo;
import com.dearho.cs.feestrategy.entity.StrategyCarShowInfo;
import com.dearho.cs.feestrategy.service.StrategyBaseService;
import com.dearho.cs.orders.pojo.BookCarInfoEntity;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.StringHelper;

public class AppBookCarAction extends AbstractAction{

	private static final long serialVersionUID = -5534826462780190111L;
	
	private BranchDotService branchDotService;
	private StrategyBaseService strategyBaseService;

	@Override
	public String process() {
		return SUCCESS;
	}

	public String queryBranchDots(){
		List<BranchDot> xyBranchDots = branchDotService.searchBranchDot(1);
		List<BranchDotVo> dotVos = new ArrayList<BranchDotVo>();
		if (xyBranchDots != null && xyBranchDots.size() > 0) {
			for (BranchDot branchDot : xyBranchDots) {
				BranchDotVo dotVo = BranchDotVo.toVo(branchDot);
				dotVo.setCarCount(branchDotService.getCarCount(branchDot
						.getId()));
				dotVos.add(dotVo);
			}
		}
		else{
			result = Ajax.AppJsonResult(1, "暂时还没有网点！");
			return SUCCESS;
		}

		result = Ajax.AppJsonResult(0, dotVos);
		return SUCCESS;
	}
	
	public String queryDotCarInfos(){
		String dotId = getRequest().getParameter("dotId");
		if(StringHelper.isEmpty(dotId)){
			result = Ajax.AppJsonResult(1, "查询参数错误");
			return SUCCESS;
		}
		BranchDot dot = branchDotService.getBranchDotById(dotId);
		if(dot == null){
			result = Ajax.AppJsonResult(1, "查询参数错误");
			return SUCCESS;
		}
		List<BookCarInfoEntity> entitys = branchDotService.getUnbookCars(dotId);
		List<DotCarInfo> dotCarInfos = new ArrayList<DotCarInfo>();
		if(entitys != null && entitys.size() > 0){
			for (BookCarInfoEntity entity : entitys) {
				DotCarInfo dotCarInfo = new DotCarInfo();
				dotCarInfo.setBrandModel(entity.getBrandModel());
				dotCarInfo.setCarId(entity.getCar().getId());
				dotCarInfo.setCarImg(entity.getCarImg());
				dotCarInfo.setCarType(entity.getCarType());
				dotCarInfo.setKm(entity.getKm());
				dotCarInfo.setSOC(entity.getSOC());
				dotCarInfo.setVehiclePlateId(entity.getCar().getVehiclePlateId());
				
				List<StrategyCarShowInfo> strategyCarShowInfos = strategyBaseService.getShowInfo(entity.getCar().getModelId());
				if(strategyCarShowInfos != null && strategyCarShowInfos.size() > 0){
					for (StrategyCarShowInfo info : strategyCarShowInfos) {
						StrtgInfo strtgInfo = new StrtgInfo();
						strtgInfo.setColor(info.getColor());
						strtgInfo.setPrice(info.getPrice());
						strtgInfo.setStrtgTypeName(info.getStrategyType().getRemark());
						strtgInfo.setStrtgType(info.getStrategyType().getCode());
						strtgInfo.setUnitStr(info.getUnitStr());
						dotCarInfo.getStrtgInfos().add(strtgInfo);
					}
				}
				
				dotCarInfos.add(dotCarInfo);
			}
		}
		 
		result = Ajax.AppJsonResult(0, dotCarInfos);
		return SUCCESS;
	}
	
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}
	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}
	public StrategyBaseService getStrategyBaseService() {
		return strategyBaseService;
	}
	public void setStrategyBaseService(StrategyBaseService strategyBaseService) {
		this.strategyBaseService = strategyBaseService;
	}
}
