package com.dearho.cs.place.action;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.pojo.CarDotBinding;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.place.service.CarDotBindingService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.service.AreaService;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class BranchDotSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BranchDotService branchDotService;
	
	private Page<BranchDot> page = new Page<BranchDot>();
	
	private BranchDot branchDot;
	
	private String state;
	
	private AreaService areaService;
	
	private List<AdministrativeArea> areas;
	
	private CarDotBindingService carDotBindingService;
	
	private CarService carService;
	
	
	public BranchDotSearchAction(){
		super();
		branchDot = new BranchDot();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			String returnPage = "";
			if("returnbackdot".equals(state)){
				page = branchDotService.searchReturnbackBranchDot(page, branchDot);
				returnPage = "returnbackdot";
			}
			else if("page".equals(state)){
				page = branchDotService.searchBranchDot(page, branchDot);
				returnPage = "page";
			}
			else{
				page = branchDotService.searchBranchDot(page, branchDot);
				returnPage = Action.SUCCESS;
			}
			if(page.getResults() != null){
				for (Object opdotObj : page.getResults()) {
					BranchDot opdot = (BranchDot)opdotObj;
					List<CarDotBinding> bindings = carDotBindingService.searchBindingByDotId(opdot.getId(), 1);
					int unUsedCarNum = 0;
					if(bindings != null){
						for (CarDotBinding carDotBinding : bindings) {
							Car car = carService.queryCarById(carDotBinding.getCarId());
							Dict ydDict = DictUtil.getDictByCodes("carBizState", "0");
							if(ydDict.getId().equals(car.getBizState())){
								unUsedCarNum++;
							}
						}
					}
					opdot.setCarCount(bindings == null ? 0 : bindings.size());
					opdot.setRemainderParkingPlace(opdot.getTotalParkingPlace() - unUsedCarNum);
//					String returnbackDots = opdot.getReturnbackDot();
//					if(StringHelper.isNotEmpty(returnbackDots)){
//						String[] dots = returnbackDots.split(",");
//						String rtname = "";
//						for (String dot : dots) {
//							if(StringHelper.isNotEmpty(dot)){
//								BranchDot bd = branchDotService.getBranchDotById(dot);
//								if(bd != null){
//									rtname += bd.getName() + "；";
//								}
//							}
//						}
//						opdot.setReturnbackDotName(rtname);
//					}
				}
			}
			return returnPage;
		} catch (Exception e) {
			e.printStackTrace();
			return Action.ERROR;
		}
	}

	public List<AdministrativeArea> getAreas(){
		List<AdministrativeArea> allAreas = areaService.searchAreaByCode(null);
		areas = allAreas;
		return areas;
	}
	
	public String getAreaName(String id){
		if(areas != null && areas.size() > 0){
			for (AdministrativeArea a : areas) {
				if(a.getId().equals(id)){
					return a.getName();
				}
			}
		}
		return "";
	}
	
	public AreaService getAreaService() {
		return areaService;
	}
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public BranchDotService getBranchDotService() {
		return branchDotService;
	}

	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}

	public Page<BranchDot> getPage() {
		return page;
	}

	public void setPage(Page<BranchDot> page) {
		this.page = page;
	}

	public BranchDot getBranchDot() {
		return branchDot;
	}

	public void setBranchDot(BranchDot branchDot) {
		this.branchDot = branchDot;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public CarDotBindingService getCarDotBindingService() {
		return carDotBindingService;
	}
	public void setCarDotBindingService(
			CarDotBindingService carDotBindingService) {
		this.carDotBindingService = carDotBindingService;
	}
	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
}
