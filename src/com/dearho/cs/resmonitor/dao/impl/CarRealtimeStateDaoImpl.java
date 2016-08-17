package com.dearho.cs.resmonitor.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.resmonitor.dao.CarRealtimeStateDao;
import com.dearho.cs.resmonitor.entity.CarInfoEntiry;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

public class CarRealtimeStateDaoImpl extends AbstractDaoSupport implements CarRealtimeStateDao {
	
	private CarVehicleModelService carVehicleModelService;
	private BranchDotService branchDotService;
	private SubscriberService subscriberService;

	@Override
	public List<CarInfoEntiry> getCarInfo(List<String> ids) {
		String hql = "select c.id,c.vehiclePlateId,c.nickName,crs.lat,crs.lng,crs.state,"
				+ "crs.totalVoltage,crs.totalCurrent,crs.SOC,crs.currentMileage,crs.currentGear,"
				+ "crs.motorSpeed,crs.motorCurrent,crs.motorTemperature,c.bizState,crs.batteryPower,crs.lifeMileage,crs.OBDTime,crs.speed,c.modelId"
				+ " from Car c, "
				+ " CarRealtimeState crs where c.id = crs.id";
		if(ids != null && ids.size() > 0){
			StringBuffer idsArrStr = new StringBuffer();
			for (String id : ids) {
				idsArrStr.append("'"+id+"'" + ",");
			}
			idsArrStr.deleteCharAt(idsArrStr.length() - 1);
			hql += " and c.id in (" + idsArrStr.toString() +")";
		}
		List<CarInfoEntiry> list = search(CarInfoEntiry.class,hql);
		List<CarInfoEntiry> resultList = new ArrayList<CarInfoEntiry>();
		if(list != null && list.size() > 0){
			for (int i = 0; i < list.size(); i++) {
				Object entity = list.get(i);
				Object[] objs = (Object[])entity;
				String carState = DictUtil.getCnNameByCode("8", String.valueOf(objs[5]));
				String carBizState = DictUtil.getCnNameByGroupCodeAndDictId("carBizState", String.valueOf(objs[14]));
				CarInfoEntiry entiry = new CarInfoEntiry(String.valueOf(objs[0]),String.valueOf(objs[1]),
						Double.valueOf(String.valueOf(objs[3])), Double.valueOf(String.valueOf(objs[4])), 
						carState,carBizState);
				entiry.setTotalVoltage(Double.valueOf(StringHelper.isRealNull(String.valueOf(objs[6])) ? "0" : String.valueOf(objs[6])));
				entiry.setTotalCurrent(Double.valueOf(StringHelper.isRealNull(String.valueOf(objs[7])) ? "0" : String.valueOf(objs[7])));
				entiry.setSOC(Double.valueOf(String.valueOf(objs[8])));
				entiry.setCurrentMileage(Double.valueOf(StringHelper.isRealNull(String.valueOf(objs[9])) ? "0" : String.valueOf(objs[9])));
				entiry.setCurrentGear(String.valueOf(objs[10]));
				entiry.setMotorSpeed(Integer.parseInt(StringHelper.isRealNull(String.valueOf(objs[11])) ? "0" : String.valueOf(objs[11])));
				entiry.setMotorCurrent(Double.valueOf(StringHelper.isRealNull(String.valueOf(objs[12])) ? "0" : String.valueOf(objs[12])));
				entiry.setMotorTemperature(Double.valueOf(StringHelper.isRealNull(String.valueOf(objs[13])) ? "0" : String.valueOf(objs[13])));
				entiry.setBatteryPower(Double.valueOf(StringHelper.isRealNull(String.valueOf(objs[15])) ? "0" : String.valueOf(objs[15])));
				entiry.setLifeMileage(Double.valueOf(StringHelper.isRealNull(String.valueOf(objs[16])) ? "0" : String.valueOf(objs[16])));
				if(objs[17] != null && !StringHelper.isRealNull(String.valueOf(objs[17]))){
					Timestamp t = (Timestamp)objs[17];
					entiry.setObdTime(DateUtil.formatDate(new Date(t.getTime()), "yyyy-MM-dd HH:mm:ss"));
				}
				else{
					entiry.setObdTime("");
				}
				entiry.setSpeed(Double.valueOf(StringHelper.isRealNull(String.valueOf(objs[18])) ? "0" : String.valueOf(objs[18])));
				
				CarVehicleModel cvm = carVehicleModelService.queryModelById(String.valueOf(objs[19]));
				if(cvm != null){
					entiry.setBrandModel(DictUtil.getCnNameByGroupCodeAndDictId("10", cvm.getBrand()) + cvm.getName());
					entiry.setEngineTypeName(DictUtil.getCnNameByGroupCodeAndDictId("2", cvm.getEngineType()) );
					entiry.setGearboxType(DictUtil.getCnNameByGroupCodeAndDictId("3", cvm.getGearboxType()));
				}
				
				
				
				BranchDot bd = branchDotService.getBranchDotByCarId(entiry.getId());
				entiry.setOrdersStartDot(bd);
				String ordersHql = "select o.ordersNo,o.memberId,o.ordersTime,o.ordersBackTime,o.ordersBackSiteId from Orders o where o.carId = '"+entiry.getId()+"' and o.state in ('1','3')";
				
				List<Object[]> objLists = getHibernateTemplate().find(ordersHql);
				if(objLists != null && objLists.size() > 0){
					Object[] ordersInfo = objLists.get(0);
					entiry.setOrdersCode(String.valueOf(ordersInfo[0]));
					Subscriber user = subscriberService.querySubscriberById(String.valueOf(ordersInfo[1]));
					entiry.setOrdersUserName(user.getName());
					Timestamp startTime = (Timestamp)ordersInfo[2];
					if(startTime != null){
						entiry.setOrdersStartTime(DateUtil.formatDate(new Date(startTime.getTime()), "yyyy-MM-dd HH:mm"));
						
					}
					Timestamp endTime = (Timestamp)ordersInfo[3];
					if(endTime != null){
						entiry.setOrdersEndTime(DateUtil.formatDate(new Date(endTime.getTime()), "yyyy-MM-dd HH:mm"));
					}
					BranchDot endbd = branchDotService.getBranchDotById(String.valueOf(ordersInfo[4]));
					entiry.setOrdersEndDot(endbd);
				}
				resultList.add(entiry);
			}
		}
		
		return resultList;
	}

	@Override
	public void save(CarRealtimeState crs) {
		addEntity(crs);
	}

	@Override
	public CarRealtimeState getCarRealTimeStateById(String id) {
		return get(CarRealtimeState.class, id);
	}
	
	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}
	public void setCarVehicleModelService(
			CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}
	
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}
	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}
	public SubscriberService getSubscriberService() {
		return subscriberService;
	}
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	@Override
	public void update(CarRealtimeState crs) {
		updateEntity(crs);
	}

}
