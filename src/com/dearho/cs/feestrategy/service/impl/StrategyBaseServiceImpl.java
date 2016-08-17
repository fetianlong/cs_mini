
package com.dearho.cs.feestrategy.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.feestrategy.dao.StrategyBaseDAO;
import com.dearho.cs.feestrategy.entity.StrategyCarShowInfo;
import com.dearho.cs.feestrategy.pojo.StrategyBase;
import com.dearho.cs.feestrategy.service.StrategyBaseService;
import com.dearho.cs.resmonitor.pojo.CarLocation;
import com.dearho.cs.resmonitor.service.CarLocationService;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.GpsTransUtil;
import com.dearho.cs.util.StringHelper;

/**
 * @author GaoYunpeng
 */
public class StrategyBaseServiceImpl implements StrategyBaseService {
	

	private StrategyBaseDAO strategyBaseDAO;
	
	private SubscriberService subscriberService;
	
	private CarVehicleModelService carVehicleModelService;
	
	private DeviceBindingService deviceBindingService;
	
	private CarLocationService carLocationService;
	
	public StrategyBaseDAO getStrategyBaseDAO() {
		return strategyBaseDAO;
	}

	public void setStrategyBaseDAO(StrategyBaseDAO strategyBaseDAO) {
		this.strategyBaseDAO = strategyBaseDAO;
	}

	@Override
	public StrategyBase searchStrategyBaseById(String id) {
		return strategyBaseDAO.searchStrategyBaseById(id);
	}

	@Override
	public void addStrategyBase(StrategyBase strategyBase) {
		strategyBaseDAO.addStrategyBase(strategyBase);
	}

	@Override
	public void updateStrategyBase(StrategyBase strategyBase) {
		strategyBaseDAO.updateStrategyBase(strategyBase);
	}


	@Override
	public Page<StrategyBase> searchStrategyBase(Page<StrategyBase> page, StrategyBase strategyBase) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.id from StrategyBase a where 1 = 1 ");
		if (strategyBase != null){
			if(strategyBase.getIsUsed() != null){
				sb.append(" and a.isUsed = ").append(strategyBase.getIsUsed());
			}
			if(StringHelper.isNotEmpty(strategyBase.getType())){
				sb.append(" and a.type = '").append(strategyBase.getType()).append("'");
			}
			if(StringHelper.isNotEmpty(strategyBase.getName())){
				sb.append(" and a.name like '%").append(strategyBase.getName()).append("%'");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createTime  desc" : page.getOrderByString());
		page = strategyBaseDAO.searchStrategyBase(page, sb.toString());
		return page;
	}

	@Override
	public StrategyBase getStrategyBaseByName(String name, int isUsed) {
		String hql = "from StrategyBase a where a.name = ? and a.isUsed = "+isUsed;
		List<StrategyBase> strategyBases = strategyBaseDAO.getDiscountsByHql(hql,StringHelper.isEmpty(name) ? "" : name.trim());
		if(strategyBases != null && strategyBases.size() > 0){
			return strategyBases.get(0);
		}
		return null;
	}

	@Override
	public void deleteStrategyBase(String[] checkdel) {
		strategyBaseDAO.deleteStrategyBase(checkdel);
	}

	@Override
	public BigDecimal conMoney(String strategyBaseId, Date startTime, Date endTime,String subscriberId,String carId) {
		StrategyBase strategyBase = strategyBaseDAO.searchStrategyBaseById(strategyBaseId);
		if(strategyBase == null){
			return new BigDecimal(0);
		}
		
		
		//即时租策略类型
		Dict jszDict = DictUtil.getDictByCodes("strategyBaseType", "jishizu");
		
		//即时租
		if(strategyBase.getType().equals(jszDict.getId())){
			if(startTime.compareTo(endTime) > 0){
				return new BigDecimal(0);
			}
			//总时长 = (结束时间 - 开始时间) 
			BigDecimal allTime = new BigDecimal(endTime.getTime()).subtract(new BigDecimal(startTime.getTime()));
			//基本计价时长
			Integer timelyFeeLong = strategyBase.getTimelyFeeLong();
			//时间基数
			BigDecimal timeJs = getTimeLongByUnit(new BigDecimal(timelyFeeLong), strategyBase.getTimelyFeeUnit());
			BigDecimal allPrice = new BigDecimal(0);
			while(allTime.compareTo(new BigDecimal(24*60*60*1000)) > 0){
				allTime = allTime.subtract(new BigDecimal(24*60*60*1000));
				if(strategyBase.getMaxConsumption() != null){
					allPrice = allPrice.add(strategyBase.getMaxConsumption());
				}
				else{
					BigDecimal gs = new BigDecimal(24*60*60*1000).divide(timeJs,0);
					allPrice = allPrice.add(strategyBase.getBasePrice().multiply(gs));
				}
			}
			
			//计费基数个数 = (总时长 / 时间基数 )
			BigDecimal gs = allTime.divide(timeJs,0);
			//总费用
			allPrice = allPrice.add(strategyBase.getBasePrice().multiply(gs));
			
			/**计算里程价格**/
			BigDecimal mile = conMile(strategyBaseId, startTime, endTime, carId);
			allPrice = allPrice.add(strategyBase.getKmPrice().multiply(mile));
			if(allPrice.compareTo(strategyBase.getMinConsumption()) < 0){
				return strategyBase.getMinConsumption();
			}
			return allPrice;
		}
		//分段租
		else{
			return strategyBase.getBasePrice();
		}
	}
	
	
	@Override
	public BigDecimal conTimeFee(String strategyBaseId, Date startTime, Date endTime,
			String carId) {
		StrategyBase strategyBase = strategyBaseDAO.searchStrategyBaseById(strategyBaseId);
		//即时租
		if(startTime.compareTo(endTime) > 0){
			return new BigDecimal(0);
		}
		//总时长 = (结束时间 - 开始时间) 
		BigDecimal allTime = new BigDecimal(endTime.getTime()).subtract(new BigDecimal(startTime.getTime()));
		//基本计价时长
		Integer timelyFeeLong = strategyBase.getTimelyFeeLong();
		//时间基数
		BigDecimal timeJs = getTimeLongByUnit(new BigDecimal(timelyFeeLong), strategyBase.getTimelyFeeUnit());
		BigDecimal allPrice = new BigDecimal(0);
		/*while(allTime.compareTo(new BigDecimal(24*60*60*1000)) > 0){
			allTime = allTime.subtract(new BigDecimal(24*60*60*1000));
			if(strategyBase.getMaxConsumption() != null){
				allPrice = allPrice.add(strategyBase.getMaxConsumption());
			}
			else{
				BigDecimal gs = new BigDecimal(24*60*60*1000).divide(timeJs,0);
				allPrice = allPrice.add(strategyBase.getBasePrice().multiply(gs));
			}
		}*/
		
		//计费基数个数 = (总时长 / 时间基数 )
		BigDecimal gs = allTime.divide(timeJs,0);
		//计时总费用
		allPrice = allPrice.add(strategyBase.getBasePrice().multiply(gs));
		return allPrice;
	}
	
	/**
	 * 计算里程费用
	 * @param strategyBaseId
	 * @param startTime
	 * @param endTime
	 * @param subscriberId
	 * @param carId
	 * @return
	 */
	@Override
	public BigDecimal conMileFee(String strategyBaseId, Date startTime, Date endTime,String carId) {
		BigDecimal mile = conMile(strategyBaseId, startTime, endTime, carId);
		StrategyBase strategyBase = strategyBaseDAO.searchStrategyBaseById(strategyBaseId);
		BigDecimal kmPrice = new BigDecimal(0);
		if(strategyBase.getKmPrice()!=null){
			kmPrice = strategyBase.getKmPrice().multiply(mile);
		}
		return kmPrice;
	}
	
	/**
	 * 计算里程
	 * @param strategyBaseId
	 * @param startTime
	 * @param endTime
	 * @param subscriberId
	 * @param carId
	 * @return
	 */
	@Override
	public BigDecimal conMile(String strategyBaseId, Date startTime, Date endTime,String carId) {
		StrategyBase strategyBase = strategyBaseDAO.searchStrategyBaseById(strategyBaseId);
		if(strategyBase == null){
			return new BigDecimal(0);
		}
		/**计算里程价格**/
		BigDecimal mile = new BigDecimal(0);
		//计算里程
		List<DeviceBinding> bindings = deviceBindingService.queryBindByCarId(carId, 1);
		if(bindings != null && bindings.size() > 0){
			DeviceBinding binding = bindings.get(0);
			String deviceNo = binding.getDeviceNo();
			List<CarLocation> locations = carLocationService.getLocationByParam(deviceNo, startTime,endTime);
			if(locations != null && locations.size() > 2){
				double traLen = 0;
				for (int i = 0; i < locations.size() - 2; i++) {
					traLen += GpsTransUtil.getDistance(locations.get(i).getLat(), locations.get(i).getLng(),
							locations.get(i + 1).getLat(), locations.get(i + 1).getLng());
				}
				double len = traLen / 10000;
				mile = new BigDecimal(len);
			}
		}	
		return mile;
//		return new BigDecimal(20);
	}
	
	@Override
	public List<StrategyCarShowInfo> getShowInfo(String modelId) {
		CarVehicleModel model = carVehicleModelService.queryModelById(modelId);
		String shizuId = model.getShizuStrategy();
		List<StrategyCarShowInfo> infos = new ArrayList<StrategyCarShowInfo>();
		if(StringHelper.isNotEmpty(shizuId)){
			StrategyBase shizuStrty = searchStrategyBaseById(shizuId);
			StrategyCarShowInfo shizuInfo = new StrategyCarShowInfo();
			Dict shizuDict = DictUtil.getDictById(shizuStrty.getType());
			shizuInfo.setColor(shizuDict.getColor());
			shizuInfo.setPrice(shizuStrty.getBasePrice());
			shizuInfo.setStrategyBase(shizuStrty);
			shizuInfo.setStrategyType(shizuDict);
			Dict unit = DictUtil.getDictById(shizuStrty.getTimelyFeeUnit());
			shizuInfo.setUnitStr("元/"+shizuStrty.getTimelyFeeLong()+unit.getCnName());
			infos.add(shizuInfo);
		}
		String rizuId = model.getRizuStrategy();
		if(StringHelper.isNotEmpty(rizuId)){
			StrategyBase rizuStrty = searchStrategyBaseById(rizuId);
			StrategyCarShowInfo rizuInfo = new StrategyCarShowInfo();
			Dict rizuDict = DictUtil.getDictById(rizuStrty.getType());
			rizuInfo.setColor(rizuDict.getColor());
			rizuInfo.setPrice(rizuStrty.getBasePrice());
			rizuInfo.setStrategyBase(rizuStrty);
			rizuInfo.setStrategyType(rizuDict);
			Dict unit = DictUtil.getDictById(rizuStrty.getTimelyFeeUnit());
			rizuInfo.setUnitStr("元/"+rizuStrty.getTimelyFeeLong()+unit.getCnName());
			infos.add(rizuInfo);
		}
		return infos;
	}

	

	@Override
	public BigDecimal getTimeLongByUnit(BigDecimal time, String unitId) {
		BigDecimal timeLong = new BigDecimal(0);
		Dict unitDict = DictUtil.getDictById(unitId);
		if("fenzhong".equals(unitDict.getCode())){
			timeLong = new BigDecimal(time.doubleValue() * 60 * 1000);
		}
		else if("xiaoshi".equals(unitDict.getCode())){
			timeLong = new BigDecimal(time.doubleValue() *60 * 60 * 1000);
		}
		else if("tian".equals(unitDict.getCode())){
			timeLong = new BigDecimal(time.doubleValue() * 24 * 60 * 60 * 1000);
		}
		else if("yue".equals(unitDict.getCode())){
			Calendar nowCal = Calendar.getInstance();
			nowCal.add(Calendar.MONTH,time.intValue());
			timeLong = new BigDecimal(nowCal.getTime().getTime() - new Date().getTime());		
		}
		
		return timeLong;
	}
	

	public SubscriberService getSubscriberService() {
		return subscriberService;
	}
	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}
	
	public static void main(String[] args) {
	}

	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}

	public void setCarVehicleModelService(CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}

	public DeviceBindingService getDeviceBindingService() {
		return deviceBindingService;
	}

	public void setDeviceBindingService(DeviceBindingService deviceBindingService) {
		this.deviceBindingService = deviceBindingService;
	}
	
	public CarLocationService getCarLocationService() {
		return carLocationService;
	}
	public void setCarLocationService(CarLocationService carLocationService) {
		this.carLocationService = carLocationService;
	}

	
}
