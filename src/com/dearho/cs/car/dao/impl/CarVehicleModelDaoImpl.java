package com.dearho.cs.car.dao.impl;

import java.util.List;

import com.dearho.cs.car.dao.CarVehicleModelDao;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

public class CarVehicleModelDaoImpl extends AbstractDaoSupport implements
		CarVehicleModelDao {

	@Override
	public void addModel(CarVehicleModel model) {
		addEntity(model);
	}

	@Override
	public void updateModel(CarVehicleModel model) {
		updateEntity(model);
	}

	@Override
	public void deleteModelByIds(String[] ids) {
		final String str="delete CarVehicleModel where id in (:ids)";
		deleteEntity(CarVehicleModel.class, str, ids);
	}

	@Override
	public CarVehicleModel queryModelByID(String id) {
		return get(CarVehicleModel.class, id);
	}

	@Override
	public Page<CarVehicleModel> queryPageModel(Page<CarVehicleModel> page, String hql) {
		Page<CarVehicleModel> resultPage=page(page, hql);
		resultPage.setResults(idToObj(CarVehicleModel.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public List<CarVehicleModel> queryModel(String hql) {
		return getList(CarVehicleModel.class, queryFList(hql));
	}

}
