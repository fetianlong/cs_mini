package com.dearho.cs.device.dao.impl;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.device.dao.DeviceBindingDao;
import com.dearho.cs.device.pojo.Device;
import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.util.StringHelper;

public class DeviceBindingDaoImpl extends AbstractDaoSupport implements DeviceBindingDao {

	@Override
	public void addCarBind(DeviceBinding bind) {
		addEntity(bind);
		clearCache(Device.class);
		clearCache(Car.class);
	}

	@Override
	public void updateCarBind(DeviceBinding bind) {
		updateEntity(bind);
		clearCache(Device.class);
		clearCache(Car.class);
	}

	@Override
	public void deleteCarBind(String[] ids) {
		final String str="delete DeviceBinding where id in (:ids)";
		deleteEntity(DeviceBinding.class, str, ids);
		clearCache(Device.class);
		clearCache(Car.class);

	}

	@Override
	public Page<DeviceBinding> queryPageCarBind(Page<DeviceBinding> page, String hql) {
		Page<DeviceBinding> resultPage=page(page, hql);
		resultPage.setResults(idToObj(DeviceBinding.class, resultPage.getmResults()));
		if(resultPage.getResults()!=null){
			for(int i=0;i<resultPage.getResults().size();i++){
				DeviceBinding bind=(DeviceBinding) resultPage.getResults().get(i);
				Car car=get(Car.class,bind.getCarId());
				if(StringHelper.isNotEmpty(car.getModelId())){
					car.setCarVehicleModel(get(CarVehicleModel.class, car.getModelId()));
				}
				Device carSet=get(Device.class,bind.getDeviceId());
				bind.setCar(car);
				bind.setDevice(carSet);
			}
		}
		
		return resultPage;
	}

	@Override
	public DeviceBinding queryBindById(String id) {
		return get(DeviceBinding.class, id);
	}

	@Override
	public List<DeviceBinding> queryBindList(String hql) {
		return getList(DeviceBinding.class, queryFList(hql));
	}

}
