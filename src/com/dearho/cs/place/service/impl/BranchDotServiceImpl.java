package com.dearho.cs.place.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.BookCarInfoEntity;
import com.dearho.cs.place.dao.BranchDotDAO;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.resmonitor.service.CarRealtimeStateService;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.StringHelper;

public class BranchDotServiceImpl implements BranchDotService {
	
	private BranchDotDAO branchDotDAO;
	private CarVehicleModelService carVehicleModelService;
	private CarRealtimeStateService carRealtimeStateService;
	private CarService carService;

	public BranchDotDAO getBranchDotDAO() {
		return branchDotDAO;
	}

	public void setBranchDotDAO(BranchDotDAO branchDotDAO) {
		this.branchDotDAO = branchDotDAO;
	}

	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}
	public void setCarVehicleModelService(
			CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}
	
	public CarRealtimeStateService getCarRealtimeStateService() {
		return carRealtimeStateService;
	}
	public void setCarRealtimeStateService(
			CarRealtimeStateService carRealtimeStateService) {
		this.carRealtimeStateService = carRealtimeStateService;
	}
	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	
	@Override
	public void addBranchDot(BranchDot branchDot) {
		branchDotDAO.addBranchDot(branchDot);
	}

	@Override
	public void updateBranchDot(BranchDot branchDot) {
		branchDotDAO.updateBranchDot(branchDot);
	}

	@Override
	public void deleteBranchDot(String[] checkdel) {
		branchDotDAO.deleteBranchDot(checkdel);
	}

	@Override
	public Page<BranchDot> searchBranchDot(Page<BranchDot> page,
			BranchDot branchDot) {
		StringBuffer hql = new StringBuffer("select a.id from BranchDot a where 1=1");
		if(branchDot != null){
			if(!StringHelper.isRealNull(branchDot.getCode())){
				hql.append(" and a.code = '").append(branchDot.getCode()).append("'");
			}
			if(!StringHelper.isRealNull(branchDot.getName())){
				hql.append(" and a.name like '%").append(branchDot.getName()).append("%'");
			}
			if(!StringHelper.isRealNull(branchDot.getAddress())){
				hql.append(" and a.address like '%").append(branchDot.getAddress()).append("%'");
			}
			if(!StringHelper.isRealNull(branchDot.getAreaId())){
				hql.append(" and a.areaId = '").append(branchDot.getAreaId()).append("'");
			}
			if(branchDot.getIsActive() != null){
				hql.append(" and a.isActive = ").append(branchDot.getIsActive());
			}
		}
		hql.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createTime desc" : page.getOrderByString());
		
		return branchDotDAO.searchBranchDot(page, hql.toString());
	}

	@Override
	public BranchDot getBranchDotById(String id) {
		return branchDotDAO.getBranchDotById(id);
	}

	@Override
	public BranchDot getBranchDotByCode(String code) {
		String hql = " from BranchDot a where a.code = '" + code +"'";
		List<BranchDot> list = branchDotDAO.searchBranchDot(hql);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<BranchDot> searchBranchDot(Integer isActive, String... params) {
		String hql = " from BranchDot a where 1=1";
		if(isActive != null){
			hql += " and a.isActive = "+isActive;
		}
		if(params != null && params.length > 0){
			if(StringHelper.isNotEmpty(params[0])){
				hql += " and a.name like '%"+params[0]+"%'";
			}
		}
		return branchDotDAO.searchBranchDot(hql);
	}

	@Override
	public Page<BranchDot> searchReturnbackBranchDot(Page<BranchDot> page,
			BranchDot branchDot) {
		StringBuffer hql = new StringBuffer("select a.id from BranchDot a where 1=1");
		if(branchDot != null){
			if(!StringHelper.isRealNull(branchDot.getCode())){
				hql.append(" and a.code = '").append(branchDot.getCode()).append("'");
			}
			if(!StringHelper.isRealNull(branchDot.getName())){
				hql.append(" and a.name like '%").append(branchDot.getName()).append("%'");
			}
			if(!StringHelper.isRealNull(branchDot.getAddress())){
				hql.append(" and a.address like '%").append(branchDot.getAddress()).append("%'");
			}
			if(!StringHelper.isRealNull(branchDot.getAreaId())){
				hql.append(" and a.areaId = '").append(branchDot.getAreaId()).append("'");
			}
			if(branchDot.getIsActive() != null){
				hql.append(" and a.isActive = ").append(branchDot.getIsActive());
			}
			else{
				hql.append(" and a.isActive = 1");
			}
			//2016年7月18日17:48:36 注释 不排除当前网点本身
//			if(!StringHelper.isRealNull(branchDot.getId())){
//				hql.append(" and a.id != '").append(branchDot.getId()).append("'");
//			}
		}
		hql.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createTime desc" : page.getOrderByString());
		
		return branchDotDAO.searchBranchDot(page, hql.toString());
	}

	@Override
	public List<BookCarInfoEntity> getUnbookCars(String id) {
		List<Car> cars = branchDotDAO.getUnbookCars(id);
		List<BookCarInfoEntity> carInfos = new ArrayList<BookCarInfoEntity>();
		if(cars != null){
			for (Car car : cars) {
				BookCarInfoEntity carInfo = new BookCarInfoEntity();
				carInfo.setCar(car);//车辆
				CarVehicleModel model = carVehicleModelService.queryModelById(car.getModelId());
				Dict brandDict = DictUtil.getDictById(model.getBrand());
				carInfo.setBrandModel(brandDict.getCnName()+" "+model.getName());//品牌/型号
				carInfo.setCarImg(model.getWebModelPhoto());//车辆图片
				Dict levelDict = DictUtil.getDictById(model.getLevel());
				carInfo.setCarType(levelDict.getCnName());//级别
				String scoreHql = "select avg(oc.score) from OrdersComment oc,Orders o where oc.ordersId = o.id and o.carId = '"+car.getId()+"' group by o.carId" ;
				List<Object> scores = branchDotDAO.getHt().find(scoreHql);
				if(scores != null && scores.size() > 0){
					if(scores.get(0) != null && StringHelper.isNotEmpty(String.valueOf(scores.get(0)))){
						carInfo.setCommentScore(Double.parseDouble(String.valueOf(scores.get(0))));//评分
					}
					
				}
				CarRealtimeState state = carRealtimeStateService.getCarRealTimeState(car.getId());
				if(state != null){
					carInfo.setSOC(Double.parseDouble(new java.text.DecimalFormat("0").format(state.getSOC())));
					int socRage = new Double(state.getSOC()/10).intValue();
					switch (socRage) {
					case 0:
					case 1:
						carInfo.setKm(model.getMileage20());
						break;
					case 2:
						carInfo.setKm(model.getMileage30());
						break;
					case 3:
						carInfo.setKm(model.getMileage40());
						break;
					case 4:
						carInfo.setKm(model.getMileage50());
						break;
					case 5:
						carInfo.setKm(model.getMileage60());
						break;
					case 6:
						carInfo.setKm(model.getMileage70());
						break;
					case 7:
						carInfo.setKm(model.getMileage80());
						break;
					case 8:
						carInfo.setKm(model.getMileage90());
						break;
					case 9:
					case 10:
						carInfo.setKm(model.getMileage100());
						break;
					default:
						carInfo.setKm(0.0);
						break;
					}
				}
				carInfos.add(carInfo);
			}
		}
		return carInfos;
	}

	@Override
	public Integer getCarCount(String id) {
		List<Car> cars = branchDotDAO.getUnbookCars(id);
		if(cars == null){
			return 0;
		}
		return cars.size();
	}

	@Override
	public BookCarInfoEntity getCarInfo(String carId) {
		BookCarInfoEntity carInfo = new BookCarInfoEntity();
		Car car = carService.queryCarById(carId);
		if(car == null){
			return null;
		}
		
		
		CarVehicleModel model = carVehicleModelService.queryModelById(car.getModelId());
		car.setCarVehicleModel(model);
		carInfo.setCar(car);//车辆
		Dict brandDict = DictUtil.getDictById(model.getBrand());
		carInfo.setBrandModel(brandDict.getCnName()+" "+model.getName());//品牌/型号
		carInfo.setCarImg(model.getWebModelPhoto());//车辆图片
		Dict levelDict = DictUtil.getDictById(model.getLevel());
		carInfo.setCarType(levelDict.getCnName());//级别
		String scoreHql = "select avg(oc.score) from OrdersComment oc,Orders o where oc.ordersId = o.id and o.carId = '"+car.getId()+"' group by o.carId" ;
		List<Object> scores = branchDotDAO.getHt().find(scoreHql);
		if(scores != null && scores.size() > 0){
			if(scores.get(0) != null && StringHelper.isNotEmpty(String.valueOf(scores.get(0)))){
				BigDecimal bd = new BigDecimal(String.valueOf(scores.get(0)));
				bd.setScale(1,BigDecimal.ROUND_HALF_UP);
				carInfo.setCommentScore(bd.doubleValue());//评分
			}
			
		}
		CarRealtimeState state = carRealtimeStateService.getCarRealTimeState(car.getId());
		if(state != null){
			carInfo.setSOC(Math.floor(state.getSOC()));
			int socRage = new Double(state.getSOC()/10).intValue();
			switch (socRage) {
			case 0:
			case 1:
				carInfo.setKm(model.getMileage20());
				break;
			case 2:
				carInfo.setKm(model.getMileage30());
				break;
			case 3:
				carInfo.setKm(model.getMileage40());
				break;
			case 4:
				carInfo.setKm(model.getMileage50());
				break;
			case 5:
				carInfo.setKm(model.getMileage60());
				break;
			case 6:
				carInfo.setKm(model.getMileage70());
				break;
			case 7:
				carInfo.setKm(model.getMileage80());
				break;
			case 8:
				carInfo.setKm(model.getMileage90());
				break;
			case 9:
			case 10:
				carInfo.setKm(model.getMileage100());
				break;
			default:
				carInfo.setKm(0.0);
				break;
			}
		}
		String dotshql = "select bd.id,bd.name,bd.address,bd.lng,bd.lat from BranchDot bd,CarDotBinding cdb where bd.id=cdb.dotId  and cdb.carId = '"+carId+"'";
		List<Object[]> dotIds = branchDotDAO.getHt().find(dotshql);
		if(dotIds != null){
			carInfo.setDotId((String)(dotIds.get(0)[0]));
			carInfo.setDotName((String)(dotIds.get(0)[1]));
			carInfo.setAddress((String)(dotIds.get(0)[2]));
			carInfo.setLng((Double)(dotIds.get(0)[3]));
			carInfo.setLat((Double)(dotIds.get(0)[4]));
//			if(StringHelper.isNotEmpty(dotIdsStr)){
//				StringBuffer dotidstr = new StringBuffer();
//				for (String dotid : dotIdsStr.split(",")) {
//					if(StringHelper.isNotEmpty(dotid)){
//						dotidstr.append("'"+dotid + "',");
//					}
//				}
//				dotidstr.deleteCharAt(dotidstr.length() - 1);
//				String dotHql = "from BranchDot where id in (" + dotidstr.toString() + ")";
//				List<BranchDot> branchDots = branchDotDAO.getHt().find(dotHql);
//				carInfo.setReturnDots(branchDots);
//			}
		}
		return carInfo;
	}

	@Override
	public BranchDot getBranchDotByCarId(String carId) {
		String dotshql = "from BranchDot bd,CarDotBinding cdb where bd.id = cdb.dotId and cdb.carId = '"+carId+"'";
		List<Object[]> objs = branchDotDAO.getHt().find(dotshql);
		if(objs != null && objs.size() > 0){
			return (BranchDot)objs.get(0)[0];
		}
		return null;
	}

	@Override
	public List<BranchDot> find(String dotHql) {
		return branchDotDAO.getHt().find(dotHql);
	}

	@Override
	public List<BranchDot> searchDotsIn(String ids) {
		String hql = " from BranchDot a where a.id in ("+ids+")";
		return branchDotDAO.searchBranchDot(hql);
	}

	@Override
	public List<BranchDot> searchDotsNotIn(String ids) {
		String hql = " from BranchDot a where a.id not in ("+ids+")";
		return branchDotDAO.searchBranchDot(hql);
	}

	@Override
	public List<BranchDot> branchDots(String minLng, String maxLng, String minLat,
			String maxLat) {
		StringBuffer hql = new StringBuffer(" from BranchDot a where 1=1");
		if(!StringHelper.isRealNull(minLng)){
			hql.append(" and a.lng > '").append(minLng).append("'");
		}
		if(!StringHelper.isRealNull(maxLng)){
			hql.append(" and a.lng < '").append(maxLng).append("'");
		}
		if(!StringHelper.isRealNull(minLat)){
			hql.append(" and a.lat > '").append(minLat).append("'");
		}
		if(!StringHelper.isRealNull(maxLat)){
			hql.append(" and a.lat < '").append(maxLat).append("'");
		}
		return  branchDotDAO.searchBranchDot(hql.toString());
	}

}
