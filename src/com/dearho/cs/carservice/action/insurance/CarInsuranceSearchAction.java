package com.dearho.cs.carservice.action.insurance;

import java.util.Date;
import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.carservice.pojo.CarInsurance;
import com.dearho.cs.carservice.service.CarInsuranceService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.report.util.ReportDateUtil;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.service.AreaService;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class CarInsuranceSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CarInsuranceService carInsuranceService;
	
	private Page<CarInsurance> page = new Page<CarInsurance>();
	
	private CarInsurance carInsurance;
	
	private AreaService areaService;
	
	private CarService carService;
	
	private String queryType;
	
	
	public CarInsuranceSearchAction(){
		super();
		carInsurance = new CarInsurance();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			if(StringHelper.isNotEmpty(queryType)){
				if("trff7Days".equals(queryType)){
					carInsurance.setTrfInsEndDate(ReportDateUtil.getOtherDay(new Date(),7, 2));
				}
				if("comm7Days".equals(queryType)){
					carInsurance.setCommInsEndDate(ReportDateUtil.getOtherDay(new Date(),7, 2));
				}
			}
			page = carInsuranceService.searchCarInsurance(page, carInsurance);
			if(page != null && page.getResults() != null){
				for (Object obj : page.getResults()) {
					CarInsurance cm = (CarInsurance)obj;
					Car c = carService.queryCarById(cm.getCarId());
					if(c != null){
						cm.setPlateNumber(c.getVehiclePlateId());
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
	public CarInsuranceService getCarInsuranceService() {
		return carInsuranceService;
	}

	public void setCarInsuranceService(CarInsuranceService carInsuranceService) {
		this.carInsuranceService = carInsuranceService;
	}

	public Page<CarInsurance> getPage() {
		return page;
	}

	public void setPage(Page<CarInsurance> page) {
		this.page = page;
	}

	public CarInsurance getCarInsurance() {
		return carInsurance;
	}

	public void setCarInsurance(CarInsurance carInsurance) {
		this.carInsurance = carInsurance;
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

	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
}
