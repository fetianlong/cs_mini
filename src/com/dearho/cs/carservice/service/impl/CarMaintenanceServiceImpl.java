package com.dearho.cs.carservice.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.web.context.ContextLoader;

import com.dearho.cs.carservice.dao.CarMaintenanceDAO;
import com.dearho.cs.carservice.pojo.CarMaintenance;
import com.dearho.cs.carservice.service.CarMaintenanceService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.report.util.ReportDateUtil;
import com.dearho.cs.sys.pojo.Config;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.service.ConfigService;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

public class CarMaintenanceServiceImpl implements CarMaintenanceService{
	
	private CarMaintenanceDAO carMaintenanceDAO;
	 
	public void setCarMaintenanceDAO(CarMaintenanceDAO carMaintenanceDAO) {
		this.carMaintenanceDAO = carMaintenanceDAO;
	}
	public CarMaintenanceDAO getCarMaintenanceDAO() {
		return carMaintenanceDAO;
	}

	@Override
	public List<CarMaintenance> searchCarMaintenance(CarMaintenance carMaintenance) {
		String hql = "from CarMaintenance r where 1=1 ";
		
		if(carMaintenance != null){
			if(!StringHelper.isRealNull(carMaintenance.getCode())){
				hql += " and r.code = '"+carMaintenance.getCode() + "'";
			}
		}
		return carMaintenanceDAO.searchCarMaintenance(hql);
	}

	@Override
	public void addCarMaintenance(CarMaintenance carMaintenance) {
		carMaintenanceDAO.addCarMaintenance(carMaintenance);
	}

	@Override
	public void updateCarMaintenance(CarMaintenance carMaintenance) {
		carMaintenanceDAO.updateCarMaintenance(carMaintenance);
	}

	@Override
	public void deleteCarMaintenance(String[] checkdel) {
		carMaintenanceDAO.deleteCarMaintenance(checkdel);
	}

	@Override
	public Page<CarMaintenance> searchCarMaintenance(Page<CarMaintenance> page,
			CarMaintenance carMaintenance) {
		String hql = "select a.id from CarMaintenance a ";
		if(carMaintenance != null){
			if(!StringHelper.isRealNull(carMaintenance.getPlateNumber())){
				hql += ",Car c";
			}
			
			hql += " where 1=1 ";
			
			if(StringHelper.isNotEmpty(carMaintenance.getCarId())){
				hql += " and a.carId = '"+carMaintenance.getCarId()+"'";
			}
			if(!StringHelper.isRealNull(carMaintenance.getPlateNumber())){
				hql += " and a.carId = c.id and c.vehiclePlateId ='" + carMaintenance.getPlateNumber().trim() + "'";
			}
			
			if(StringHelper.isNotEmpty(carMaintenance.getCode())){
				hql += " and a.code = '"+carMaintenance.getCode().trim()+"'";
			}
			if(StringHelper.isNotEmpty(carMaintenance.getVehicleModel())){
				hql += " and a.vehicleModel = '"+carMaintenance.getVehicleModel().trim()+"'";
			}
			if(StringHelper.isNotEmpty(carMaintenance.getCity())){
				hql += " and a.city = '"+carMaintenance.getCity().trim()+"'";
			}
			if(StringHelper.isNotEmpty(carMaintenance.getStatus())){
				hql += " and a.status = '"+carMaintenance.getStatus().trim()+"'";
			}
			if(carMaintenance.getIsDiscard() != null){
				hql += " and a.isDiscard = "+carMaintenance.getIsDiscard();
			}
			
		}
		hql += (StringHelper.isEmpty(page.getOrderByString()) ? "order by a.createTime desc" : page.getOrderByString());
		return carMaintenanceDAO.searchCarMaintenance(page, hql);
	}

	@Override
	public CarMaintenance searchCarMaintenanceById(String id) {
		return carMaintenanceDAO.searchCarMaintenanceById(id);
	}
	@Override
	public Page<CarMaintenance> searchCarMaintenanceNear7Days(
			Page<CarMaintenance> page, CarMaintenance carMaintenance) {
		String hql = "select a.id from CarMaintenance a ";
		Dict d = DictUtil.getDictByCodes("maintenanceStatus", "04");
		if(d != null){
			hql += " where a.maintenanceTime in (select max(b.maintenanceTime) from CarMaintenance b group by b.carId)"
					+ " and a.maintenanceTime < '" 
					+ DateUtil.formatDate(ReportDateUtil.getOtherDay(ReportDateUtil.getOtherMonth(-3, 2),7, 2), "yyyy-MM-dd HH:mm:ss")+"'";
		}
		
		hql += (StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		return carMaintenanceDAO.searchCarMaintenance(page, hql);
	}
	@Override
	public List<CarMaintenance> searchCarMaintenanceCurrentDate() {
		Calendar todayStart = Calendar.getInstance();  
	    todayStart.set(Calendar.HOUR, 0);  
	    todayStart.set(Calendar.MINUTE, 0);  
	    todayStart.set(Calendar.SECOND, 0);  
	    todayStart.set(Calendar.MILLISECOND, 0);  
	    Date startDate = todayStart.getTime();  
	    Date endDate = new Date();
	    
	    String hql = "from CarMaintenance r where 1=1 and r.createTime >= '"+DateUtil.getChar19DateString(startDate)+"' and r.createTime <= '"+DateUtil.getChar19DateString(endDate)+"'";
		return carMaintenanceDAO.searchCarMaintenance(hql);
	}
	@Override
	public Page<CarMaintenance> searchCarMaintenanceWarn(
			Page<CarMaintenance> page, CarMaintenance carMaintenance) {
		ConfigService configService = (ConfigService)ContextLoader.getCurrentWebApplicationContext()
				.getBean("configService");
		if(configService != null){
			Config config = configService.getConfigByCode("maintenanceWarnDay");
			if(StringHelper.isNotEmpty(config.getValue())){
				Integer dayNum = Integer.parseInt(config.getValue());
				Calendar lastDay = Calendar.getInstance();
				lastDay.add(Calendar.DAY_OF_YEAR, 0-dayNum);
				String hql = "select a.id from CarMaintenance a ";
				if(carMaintenance != null){
					if(!StringHelper.isRealNull(carMaintenance.getPlateNumber())){
						hql += ",Car c";
					}
					hql += " where 1=1 ";					
					if(!StringHelper.isRealNull(carMaintenance.getPlateNumber())){
						hql += " and a.carId = c.id and c.vehiclePlateId = '" + carMaintenance.getPlateNumber().trim() + "'";
					}
					
				}
				else{
					hql += " where 1=1";
				}
				hql += " and a.id in (select id from CarMaintenance b where b.ts in (select max(ts) from CarMaintenance where maintenanceTime <= '"+DateUtil.formatDate(lastDay.getTime(), "yyyy-MM-dd") + " 00:00:00'  group by carId ))";
//				hql += " and a.maintenanceTime <= '"+DateUtil.formatDate(lastDay.getTime(), "yyyy-MM-dd") + " 00:00:00' ";
				
				hql += (StringHelper.isEmpty(page.getOrderByString()) ? "order by a.createTime " : page.getOrderByString());
				return carMaintenanceDAO.searchCarMaintenance(page, hql);
			}
		}
		return null;
	}

}
