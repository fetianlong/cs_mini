package com.dearho.cs.carservice.action.maintenance;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.carservice.pojo.CarMaintenance;
import com.dearho.cs.carservice.service.CarMaintenanceService;
import com.dearho.cs.core.db.page.Page;
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
public class CarMaintenanceSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarMaintenanceService carMaintenanceService;
	
	private Page<CarMaintenance> page = new Page<CarMaintenance>();
	
	private CarMaintenance carMaintenance;
	
	private CarVehicleModelService carVehicleModelService;
	
	private AreaService areaService;
	
	private CarService carService;
	
	private String statusCode;
	
	private String queryType;
	
	public CarMaintenanceSearchAction(){
		super();
		carMaintenance = new CarMaintenance();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			if(StringHelper.isNotEmpty(queryType)){
				if("near7days".equals(queryType)){
					page = carMaintenanceService.searchCarMaintenanceNear7Days(page, carMaintenance);
				}
			}
			else{
				if(StringHelper.isNotEmpty(statusCode)){
					Dict d = DictUtil.getDictByCodes("maintenanceStatus", statusCode);
					if(d != null){
						carMaintenance.setStatus(d.getId());
					}
				}
				page = carMaintenanceService.searchCarMaintenance(page, carMaintenance);
			}
			
			if(page != null && page.getResults() != null){
				for (Object obj : page.getResults()) {
					CarMaintenance cm = (CarMaintenance)obj;
					AdministrativeArea area = areaService.searchAreaById(cm.getCity());
					if(area != null ){
						cm.setCityName(area.getName());
					}
					Car c = carService.queryCarById(cm.getCarId());
					if(c != null){
						cm.setPlateNumber(c.getVehiclePlateId());
						cm.setVehicleModel(c.getCarVehicleModel().getName());
					}
				}
			}
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}

	public List<AdministrativeArea> getCitys(){
		return areaService.getAreasByParentCode(null);
	}
	public List<CarVehicleModel> getVehicleModels(){
		return carVehicleModelService.queryModelByCon(null);
	}
	public CarMaintenanceService getCarMaintenanceService() {
		return carMaintenanceService;
	}

	public void setCarMaintenanceService(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService = carMaintenanceService;
	}

	public Page<CarMaintenance> getPage() {
		return page;
	}

	public void setPage(Page<CarMaintenance> page) {
		this.page = page;
	}

	public CarMaintenance getCarMaintenance() {
		return carMaintenance;
	}

	public void setCarMaintenance(CarMaintenance carMaintenance) {
		this.carMaintenance = carMaintenance;
	}

	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}

	public void setCarVehicleModelService(
			CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}

	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
	
}
