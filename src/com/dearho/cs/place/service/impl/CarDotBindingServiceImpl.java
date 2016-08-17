package com.dearho.cs.place.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.car.dao.CarVehicleModelDao;
import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.place.dao.CarDotBindingDao;
import com.dearho.cs.place.pojo.CarDotBinding;
import com.dearho.cs.place.service.CarDotBindingService;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.StringHelper;

public class CarDotBindingServiceImpl implements CarDotBindingService {

	private CarDotBindingDao carDotBindingDao;
	
	private CarVehicleModelDao carVehicleModelDao;

	public CarDotBindingDao getCarDotBindingDao() {
		return carDotBindingDao;
	}

	public void setCarDotBindingDao(CarDotBindingDao carDotBindingDao) {
		this.carDotBindingDao = carDotBindingDao;
	}
	
	public CarVehicleModelDao getCarVehicleModelDao() {
		return carVehicleModelDao;
	}
	public void setCarVehicleModelDao(CarVehicleModelDao carVehicleModelDao) {
		this.carVehicleModelDao = carVehicleModelDao;
	}
	
	@Override
	public Page<Car> searchCars(Page<Car> page, Car car, String dotId,String type,String vehiclePlateId,int isUsed) {
		StringBuffer sb=new StringBuffer();
		
		sb.append(" from CarDotBinding a where a.isUsed = ? ");
		if("add".equals(type)){
			List<CarDotBinding> carDotBindings = carDotBindingDao.queryCarDotBinding(sb.toString(),isUsed);//获得已经绑定的车辆信息
			StringBuffer carHql = new StringBuffer();
			carHql.append("select a.id from Car a where 1=1 ");
			String carIds = "";
			if(carDotBindings != null && carDotBindings.size() > 0){
				for (CarDotBinding carDotBinding : carDotBindings) {
					carIds += "'"+ carDotBinding.getCarId() + "',";
				}
			}
			if(!"".equals(carIds)){
				carIds = carIds.substring(0, carIds.length() - 1);
				carHql.append(" and a.id not in (").append(carIds).append(")");
			}
			Dict wzDict = DictUtil.getDictByCodes("carBizState", "0");
			Dict ddDict = DictUtil.getDictByCodes("carBizState", "5");
			carHql.append(" and a.bizState in ('").append(wzDict.getId()).append("','").append(ddDict.getId()).append("')");
			
			if(StringHelper.isNotEmpty(vehiclePlateId)){//车牌号模糊查询的条件
				carHql.append(" and a.vehiclePlateId like '%").append(vehiclePlateId).append("%' ");
			}
			
			carHql.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createDate desc " : page.getOrderByString());
			page=carDotBindingDao.queryCarByPage(page, carHql.toString());
			
		}
		else if("delete".equals(type) && StringHelper.isNotEmpty(dotId)){
			sb.append(" and a.dotId = ?");
			List<CarDotBinding> carDotBindings = carDotBindingDao.queryCarDotBinding(sb.toString(),isUsed,dotId);
			if(carDotBindings != null && carDotBindings.size() > 0){
				StringBuffer carHql = new StringBuffer();
				carHql.append("select a.id from Car a where 1=1 ");
				String carIds = "";
				for (CarDotBinding carDotBinding : carDotBindings) {
					carIds += "'"+ carDotBinding.getCarId() + "',";
				}
				if(!"".equals(carIds)){
					carIds = carIds.substring(0, carIds.length() - 1);
					carHql.append(" and a.id in (").append(carIds).append(")");
				}
				if(StringHelper.isNotEmpty(vehiclePlateId)){
					carHql.append(" and a.vehiclePlateId like '%").append(vehiclePlateId).append("%' ");
				}
				Dict wzDict = DictUtil.getDictByCodes("carBizState", "0");
				carHql.append(" and a.bizState = '").append(wzDict.getId()).append("'");
				carHql.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createDate desc " : page.getOrderByString());
				page=carDotBindingDao.queryCarByPage(page, carHql.toString());
			}
		}
		if(page != null && page.getResults() != null){
			List<Car> cars = page.getResults();
			for (Car car2 : cars) {
				if(car2.getModelId() == null){
					continue;
				}
				CarVehicleModel model = carVehicleModelDao.queryModelByID(car2.getModelId());
				car2.setCarVehicleModel(model);
			}
		}
		return page;
	}

	@Override
	public void add(CarDotBinding carDotBinding) {
		carDotBindingDao.add(carDotBinding);
	}

	@Override
	public void delete(CarDotBinding carDotBinding) {
		if(carDotBinding == null){
			return;
		}
		StringBuffer sb=new StringBuffer();
		sb.append(" from CarDotBinding a where carId ='").append(carDotBinding.getCarId())
			.append("' and dotId = '").append(carDotBinding.getDotId()).append("'");
		List<CarDotBinding> carDotBindings = carDotBindingDao.queryCarDotBinding(sb.toString());
		if(carDotBindings == null || carDotBindings.size() <= 0){
			return;
		}
		String[] ids = new String[carDotBindings.size()];
		for (int i = 0; i < carDotBindings.size(); i++) {
			CarDotBinding carDotBinding2 = carDotBindings.get(i);
			ids[i] = carDotBinding2.getId();
		}
		
		carDotBindingDao.delete(ids);
	}

	@Override
	public void update(CarDotBinding carDotBinding) {
		carDotBindingDao.update(carDotBinding);
	}

	@Override
	public CarDotBinding getBindingByCarId(String carId, int isUsed) {
		StringBuffer sb=new StringBuffer();
		sb.append(" from CarDotBinding a where a.carId = ? and a.isUsed = ? ");
		List<CarDotBinding> carDotBindings = carDotBindingDao.queryCarDotBinding(sb.toString(),carId,isUsed);//获得已经绑定的车辆信息
		if(carDotBindings != null && carDotBindings.size() > 0){
			return carDotBindings.get(0);
		}
		return null;
	}

	@Override
	public List<CarDotBinding> searchBindingByDotId(String dotId, Integer isUsed) {
		StringBuffer sb=new StringBuffer();
		sb.append(" from CarDotBinding a where a.dotId = ? and a.isUsed = ? ");
		List<CarDotBinding> carDotBindings = carDotBindingDao.queryCarDotBinding(sb.toString(),dotId,isUsed);//获得已经绑定的车辆信息
		return carDotBindings == null ? new ArrayList<CarDotBinding>() : carDotBindings;
	}

}
