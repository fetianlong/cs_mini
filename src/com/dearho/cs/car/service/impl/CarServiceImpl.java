package com.dearho.cs.car.service.impl;

import java.util.Date;
import java.util.List;

import com.dearho.cs.car.dao.CarDao;
import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.resmonitor.service.CarRealtimeStateService;
import com.dearho.cs.sys.pojo.Company;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.StringHelper;

public class CarServiceImpl implements CarService {
	private CarDao carDao;
	private CarRealtimeStateService carRealtimeStateService;
	
	public void setCarDao(CarDao carDao) {
		this.carDao = carDao;
	}
	public CarRealtimeStateService getCarRealtimeStateService() {
		return carRealtimeStateService;
	}
	public void setCarRealtimeStateService(
			CarRealtimeStateService carRealtimeStateService) {
		this.carRealtimeStateService = carRealtimeStateService;
	}
	
	@Override
	public void addCar(Car car) {
		carDao.addCar(car);
	}
	
	

	@Override
	public void updateCar(Car car) {
		CarRealtimeState crs = carRealtimeStateService.getCarRealTimeState(car.getId());
		if(crs == null){
			Dict wzlDict = DictUtil.getDictByCodes("carBizState", "0");
			if(wzlDict != null && wzlDict.getId().equals(car.getBizState())){
				crs = new CarRealtimeState();
				crs.setId(car.getId());
				crs.setTs(new Date());
				try {
					carRealtimeStateService.add(crs);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		}
		
		carDao.updateCar(car);
		
	}

	@Override
	public Page<Car> queryCarByPage(Page<Car> page, Car car) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Car a , CarVehicleModel cv where a.modelId = cv.id ");
		if(car !=null){
			if(StringHelper.isNotEmpty(car.getVehiclePlateId())){
				sb.append(" and a.vehiclePlateId like '%").append(car.getVehiclePlateId()).append("%'");
			}
			if(StringHelper.isNotEmpty(car.getModelId())){
				car.setModelId(car.getModelId().trim());
				sb.append(" and a.modelId = '").append(car.getModelId().trim()).append("'");
			}
			if(StringHelper.isNotEmpty(car.getBizState())){
				sb.append(" and a.bizState = '").append(car.getBizState().trim()).append("'");
			}
			if(car.getIsAd() != null){
				sb.append(" and a.isAd = ").append(car.getIsAd());
			}
			
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createDate desc" : "order by a."+page.getOrderByString().replace("order", "").replace("by", "").trim());
		page=carDao.queryCarByPage(page, sb.toString());
		return page;
	}

	@Override
	public Car queryCarById(String id) {
		return carDao.queryCarById(id);
	}

	@Override
	public void deleteCarByIds(String[] ids) {
		carDao.deleteCarArrays(ids);
	}

	@Override
	public List<Car> queryCarListByModelId(String modelId) {
		return carDao.queryCarByModelId(modelId);
	}

	@Override
	public Car queryCarByVehiclePlateId(String vehiclePlateId) {
		return carDao.queryCarByVehiclePlateId(vehiclePlateId);
	}

	@Override
	public Page<Car> queryBindingCarByPage(Page<Car> page, Car car,
			int bindType) {
		StringBuffer sb=new StringBuffer();
		//查询未绑定的
		if(bindType == 0){
			sb.append("select a.id from Car a where a.id not in (select d.carId from DeviceBinding d where d.bindType = 1) ");
		}
		//查询绑定的
		else if(bindType == 1){
			sb.append("select a.id from Car a,DeviceBinding d where a.id = d.carId and d.bindType = 1");
		}
		
		
		if(car !=null){
			if(StringHelper.isNotEmpty(car.getVehiclePlateId())){
				sb.append(" and a.vehiclePlateId like '%").append(car.getVehiclePlateId()).append("%'");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createDate desc" : 
			"order by a."+page.getOrderByString().replace("order", "").replace("by", "").trim());
		page=carDao.queryCarByPage(page, sb.toString());
		return page;
	}

	@Override
	public List<Car> queryCars(Car car) {
		return carDao.queryCars(car);
	}

	@Override
	public List<Car> queryCars(Car car, Company company) {
		String hql="select a.id from Car a where 1=1";
		if(car!=null){
			if(StringHelper.isNotEmpty(car.getModelId())){
				hql += " and modelId = '" + car.getModelId()+"'";
			}
			if(StringHelper.isNotEmpty(car.getVehiclePlateId())){
				hql += " and vehiclePlateId like '%"+car.getVehiclePlateId()+"%'";
			}
		}
		if(company!=null){
			if(StringHelper.isNotEmpty(company.getId())){
				hql+=" and a.id in (select b.carId from CarParkingBind b where b.parkingId in (select p.id from Parking p where p.bizComp='"+company.getId()+"') )";
			}
		}
		return carDao.queryCarsByHql(hql);
	}

	@Override
	public Page<Car> queryCarByPageDot(Page<Car> page, Car car,String dotId) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Car a , CarDotBinding cpb where a.id = cpb.carId and cpb.dotId = '");
		sb.append(dotId);
		sb.append("'");
			
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createDate desc" : 
			"order by a."+page.getOrderByString().replace("order", "").replace("by", "").trim());
		page=carDao.queryCarByPage(page, sb.toString());
		return page;
	}

	@Override
	public Page<Car> queryViolationCarByPage(Page<Car> page, Car car) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Car a , CarVehicleModel cv,CarViolation cvil where a.modelId = cv.id and a.id=cvil.carId");
		if(car !=null){
			if(StringHelper.isNotEmpty(car.getVehiclePlateId())){
				sb.append(" and a.vehiclePlateId like '%").append(car.getVehiclePlateId()).append("%'");
			}
			if(StringHelper.isNotEmpty(car.getModelId())){
				car.setModelId(car.getModelId().trim());
				sb.append(" and a.modelId = '").append(car.getModelId().trim()).append("'");
			}
			if(car.getCarVehicleModel() != null){
				if(StringHelper.isNotEmpty(car.getCarVehicleModel().getBrand())){
					sb.append(" and cv.brand = '").append(car.getCarVehicleModel().getBrand().trim()).append("'");
				}
				if(StringHelper.isNotEmpty(car.getCarVehicleModel().getEngineType())){
					sb.append(" and cv.engineType = '").append(car.getCarVehicleModel().getEngineType().trim()).append("'");
				}
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createDate desc" : 
			"order by a."+page.getOrderByString().replace("order", "").replace("by", "").trim());
		page=carDao.queryCarByPage(page, sb.toString());
		return page;
	}

	@Override
	public Page<Car> queryCarToRepair(Page<Car> page, Car car) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Car a , CarVehicleModel cv where a.modelId = cv.id ");
		if(car !=null){
			if(StringHelper.isNotEmpty(car.getVehiclePlateId())){
				sb.append(" and a.vehiclePlateId like '%").append(car.getVehiclePlateId()).append("%'");
			}
			if(StringHelper.isNotEmpty(car.getModelId())){
				car.setModelId(car.getModelId().trim());
				sb.append(" and a.modelId = '").append(car.getModelId().trim()).append("'");
			}
			if(car.getCarVehicleModel() != null){
				if(StringHelper.isNotEmpty(car.getCarVehicleModel().getBrand())){
					sb.append(" and cv.brand = '").append(car.getCarVehicleModel().getBrand().trim()).append("'");
				}
				if(StringHelper.isNotEmpty(car.getCarVehicleModel().getEngineType())){
					sb.append(" and cv.engineType = '").append(car.getCarVehicleModel().getEngineType().trim()).append("'");
				}
			}
			
		}
		Dict d = DictUtil.getDictByCodes("carBizState", "0");
		sb.append(" and (a.bizState ='").append(d.getId()).append("'");
		d = DictUtil.getDictByCodes("carBizState", "4");
		sb.append(" or a.bizState ='").append(d.getId()).append("')");
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createDate desc " : 
			"order by a."+page.getOrderByString().replace("order", "").replace("by", "").trim());
		page=carDao.queryCarByPage(page, sb.toString());
		return page;
	}
	@Override
	public List<Car> searchCarIn(String ids) {
		String hql = "select a.id from Car a where a.id in ("+ids+")";
		return carDao.queryCarsByHql(hql);
	}
	@Override
	public List<Car> searchCarNotIn(String ids) {
		String hql = "select a.id from Car a where a.id not in ("+ids+")";
		return carDao.queryCarsByHql(hql);
	}

	
}
