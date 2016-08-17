package com.dearho.cs.car.service.impl;

import java.util.List;

import com.dearho.cs.car.dao.CarVehicleModelDao;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.feestrategy.pojo.StrategyBase;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.StringHelper;

public class CarVehicleModelServiceImpl implements CarVehicleModelService {
	
	private CarVehicleModelDao carVehicleModelDao;
	

	public void setCarVehicleModelDao(CarVehicleModelDao carVehicleModelDao) {
		this.carVehicleModelDao = carVehicleModelDao;
	}

	@Override
	public void addVeicleModel(CarVehicleModel vehicleModel) {
		carVehicleModelDao.addModel(vehicleModel);
	}

	@Override
	public void updateVeicleModel(CarVehicleModel vehicleModel) {
		carVehicleModelDao.updateModel(vehicleModel);
	}

	@Override
	public void deleteModelByIds(String[] ids) {
		carVehicleModelDao.deleteModelByIds(ids);
	}

	@Override
	public Page<CarVehicleModel> queryPageModel(Page<CarVehicleModel> page,
			CarVehicleModel model) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from CarVehicleModel a where 1=1");
		if(model!=null){
			//条件查询
			if(StringHelper.isNotEmpty(model.getName())){
				sb.append(" and a.name like '%").append(model.getName()).append("%'");
			}
			if(StringHelper.isNotEmpty(model.getBrand())){
				sb.append(" and a.brand = '").append(model.getBrand()).append("'");
			}
			if(StringHelper.isNotEmpty(model.getLevel())){
				sb.append(" and a.level = '").append(model.getLevel()).append("'");
			}
			if(StringHelper.isNotEmpty(model.getEngineType())){
				sb.append(" and a.engineType = '").append(model.getEngineType()).append("'");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createDate desc" : page.getOrderByString());
		page = carVehicleModelDao.queryPageModel(page, sb.toString());
		return page;
	}

	@Override
	public CarVehicleModel queryModelById(String id) {
		return carVehicleModelDao.queryModelByID(id);
	}

	@Override
	public List<CarVehicleModel> queryModelByCon(CarVehicleModel model) {
		StringBuffer sb=new StringBuffer();
		sb.append("select id from CarVehicleModel where 1=1");
		if(model!=null){
			if(!StringHelper.isRealNull(model.getBrand())){
				sb.append(" and brand = '").append(model.getBrand()).append("'");
			}
		}
		return carVehicleModelDao.queryModel(sb.toString());
	}

	@Override
	public List<CarVehicleModel> queryModelByName(
			CarVehicleModel carVehicleModel) {
		StringBuffer sb=new StringBuffer();
		sb.append("select id from CarVehicleModel where 1=1");
		if(carVehicleModel !=null ){
			if(!StringHelper.isRealNull(carVehicleModel.getName())){
				sb.append(" and name = '").append(carVehicleModel.getName()).append("'");
			}
		}
		return carVehicleModelDao.queryModel(sb.toString());
	}

	@Override
	public boolean updateModelStrategy(StrategyBase oldstrategyBase,
			StrategyBase strategyBase) {
		Dict dict = DictUtil.getDictById(strategyBase.getType());
		if(dict == null){
			return false;
		}
		StringBuffer sb=new StringBuffer();
		if("jishizu".equals(dict.getCode())){
			sb.append("select id from CarVehicleModel where shizuStrategy='").append(oldstrategyBase.getId()).append("'");
		}
		else{
			sb.append("select id from CarVehicleModel where rizuStrategy='").append(oldstrategyBase.getId()).append("'");
		}
		
		List<CarVehicleModel> models = carVehicleModelDao.queryModel(sb.toString());
		if(models == null || models.size() <= 0){
			return true;
		}
		for (CarVehicleModel carVehicleModel : models) {
			if("jishizu".equals(dict.getCode())){
				carVehicleModel.setShizuStrategy(strategyBase.getId());
			}
			else{
				carVehicleModel.setRizuStrategy(strategyBase.getId());
			}
			updateVeicleModel(carVehicleModel);
		}
		return true;
		
	}


}
