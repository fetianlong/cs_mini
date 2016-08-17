package com.dearho.cs.appinterface.orders.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.account.service.OrderPayService;
import com.dearho.cs.appinterface.orders.pojo.AppBookingInfo;
import com.dearho.cs.appinterface.orders.pojo.AppCurrentOrders;
import com.dearho.cs.appinterface.orders.pojo.AppOrdersDetail;
import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.feestrategy.entity.StrategyCarShowInfo;
import com.dearho.cs.feestrategy.pojo.StrategyBase;
import com.dearho.cs.feestrategy.service.StrategyBaseService;
import com.dearho.cs.orders.pojo.BookCarInfoEntity;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersBill;
import com.dearho.cs.orders.pojo.OrdersDetail;
import com.dearho.cs.orders.service.OrdersBillService;
import com.dearho.cs.orders.service.OrdersDetailService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.resmonitor.service.CarRealtimeStateService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.GpsTransUtil;
import com.dearho.cs.util.NumberUtil;
import com.dearho.cs.util.Point;
import com.dearho.cs.util.StringHelper;
import com.dearho.cs.wechat.service.WechatUserInfoService;

/**
 * 订单模块APP接口
 * @author wangjing
 *
 */
public class OrdersAppInterfaceAction extends AbstractAction{

	private static final long serialVersionUID = 6764782008907112330L;

	private BranchDotService branchDotService;
	private CarService carService;
	private OrdersBillService ordersBillService;
	private OrdersService ordersService;
	private SubscriberService subscriberService;
	private CarVehicleModelService carVehicleModelService;
	private OrdersDetailService ordersDetailService;
	private StrategyBaseService strategyBaseService;
	private OrderPayService orderPayService;
	private WechatUserInfoService wechatUserInfoService;
	private AccountService accountService;
	private CarRealtimeStateService carRealtimeStateService;
	
	@Override
	public String process() {
		return null;
	}

	/**
	 * 车辆预定页面信息接口
	 * @return
	 */
	public String toBookingInfo() {
		String subscriberId = getRequest().getParameter("subscriberId");
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(6001, "参数subscriberId不能为空");
			return 	ERROR;
		}
		
		String dotId = getRequest().getParameter("dotId");
		if(StringUtils.isEmpty(dotId)){
			result = Ajax.AppJsonResult(6001, "参数dotId不能为空");
			return 	ERROR;
		}
		
		String carId = getRequest().getParameter("carId");
		if(StringUtils.isEmpty(dotId)){
			result = Ajax.AppJsonResult(6001, "参数carId不能为空");
			return 	ERROR;
		}
		
		List<BookCarInfoEntity> entitys = branchDotService.getUnbookCars(dotId);
		List<StrategyCarShowInfo> strategyCarShowInfos = new ArrayList<StrategyCarShowInfo>();
		if(entitys != null && entitys.size() > 0){
			for (BookCarInfoEntity entity : entitys) {
				strategyCarShowInfos = strategyBaseService.getShowInfo(entity.getCar().getModelId());
			}
		}
		
		Car car = carService.queryCarById(carId);
		CarVehicleModel carModel = car.getCarVehicleModel();//得到该车的车型
		StrategyBase shizuStrategy = strategyBaseService.searchStrategyBaseById(carModel.getShizuStrategy());
		StrategyBase rizuStrategy = strategyBaseService.searchStrategyBaseById(carModel.getRizuStrategy());
		
		BookCarInfoEntity carInfo = branchDotService.getCarInfo(carId);
		
		CarVehicleModel model = carVehicleModelService.queryModelById(car.getModelId());
		if(model != null){
			carInfo.setCarImg(model.getMicroWebModelPhoto());
		}
		
		AppBookingInfo appBookingInfo = new AppBookingInfo();
		appBookingInfo.setCarId(carId);
		appBookingInfo.setCarType(carInfo.getCarType());
		appBookingInfo.setBrandModel(carInfo.getBrandModel());
		appBookingInfo.setCarImg(carInfo.getCarImg());
		appBookingInfo.setDotName(carInfo.getDotName());
		appBookingInfo.setKm(carInfo.getKm());
		appBookingInfo.setSoc(carInfo.getSOC());
		appBookingInfo.setVehiclePlateId(car.getVehiclePlateId());
		appBookingInfo.setStrategyCarShowInfos(strategyCarShowInfos);
		appBookingInfo.setRizuStrategyBase(rizuStrategy);
		appBookingInfo.setShizuStrategyBase(shizuStrategy);
		
		result = Ajax.AppJsonResult(0, appBookingInfo);
		return SUCCESS;
	}
	
	/**
	 * 确认提交订单接口
	 * @return
	 */
	public String confirmCommitOrders() {
		String subscriberId = getRequest().getParameter("subscriberId");
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(1, "参数subscriberId不能为空");
			return 	ERROR;
		}
		
		String carId = getRequest().getParameter("carId");
		if(StringUtils.isEmpty(carId)){
			result = Ajax.AppJsonResult(2, "参数carId不能为空");
			return 	ERROR;
		}
		
		String strategyBaseId = getRequest().getParameter("strategyBaseId");
		if(StringUtils.isEmpty(strategyBaseId)){
			result = Ajax.AppJsonResult(3, "参数strategyBaseId不能为空");
			return 	ERROR;
		}
		StrategyBase strategyBase = strategyBaseService.searchStrategyBaseById(strategyBaseId);//找到策略
		Dict d = DictUtil.getDictById(strategyBase.getType());//用户选择的租赁类型
		Integer unitCount = 0;
		if("rizu".equals(d.getCode())){
			String unitCountStr = getRequest().getParameter("unitCount");
			if(StringUtils.isEmpty(unitCountStr)){
				result = Ajax.AppJsonResult(4, "参数unitCount不能为空");
				return 	ERROR;
			}
			unitCount = Integer.valueOf(unitCountStr);
		}
		
		Subscriber s = subscriberService.querySubscriberById(subscriberId);
		
		Account account=accountService.getAccoutDetail(s.getId());
		
		/** 校验 start **/
		if (Subscriber.EVENT_STATE_HALF.equals(s.getEventState())) {
			result = Ajax.AppJsonResult(5, "此账号已冻结，不能租车。 如有疑问，请联系客服。");
			return ERROR;
		}
		
		if (Subscriber.STATE_WAIT_CONFIRMED.equals(s.getState())
				|| Subscriber.STATE_NO_CONFIRMED.equals(s.getState())
				|| Subscriber.STATE_UNCONFIRMED.equals(s.getState())) {
			
			result = Ajax.AppJsonResult(6,"帐户资料未审核通过，不能租车");
			return ERROR;
		}
		
		if(Account.IS_REFUND_TRUE.equals(account.getIsRefund())){
			result = Ajax.AppJsonResult(7,"退款流程处理中,不能租车");
			return ERROR;
		}
		
		
		BigDecimal maxFrozenMoney = new BigDecimal(Integer.parseInt(DictUtil.getCnNameByCode("maxFrozenMoney", "maxFrozenMoney")));
		if ((new BigDecimal(account.getFrozenAmount()).compareTo(maxFrozenMoney))<0) {
			result = Ajax.AppJsonResult(8,"账户押金不足，请先充值");
			return ERROR;
		}
		
		Orders currentOrders = ordersService.queryCurrentOrder(s.getId());
		if (currentOrders != null) {
			result = Ajax.AppJsonResult(9,
					"您当前有未完成的订单！");
			return ERROR;
		}
		
		Orders orders = new Orders();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date currentDate = new Date();
			orders.setOrdersTime(currentDate);
			orders.setCarId(carId);
			// 验证车辆当前状态
			Car car = carService.queryCarById(carId);
			if (car != null) {
				Dict ydDict = DictUtil.getDictByCodes("carBizState", "0");
				if (!car.getBizState().equals(ydDict.getId())) {
					result = Ajax.AppJsonResult(10,
							"当前车辆已预定！");
					return ERROR;
				}
			} else {
				result = Ajax.AppJsonResult(11, "车辆不存在！");
				return ERROR;
			}
			
			orders.setCreateDate(currentDate);
			orders.setTs(currentDate);
			if (s != null && StringHelper.isNotEmpty(s.getId())) {
				orders.setMemberId(s.getId());
			} else {
				result = Ajax.AppJsonResult(12, "登录超时！");
				return ERROR;
			}
			BookCarInfoEntity entity = branchDotService.getCarInfo(carId);
			
//			if (orders.getIsBill() != null && orders.getIsBill() == 1) {
//				ordersBillService.addBill(ordersBill);
//				orders.setBillId(ordersBill.getId());
//			}
			String ordersNo = getOrderNo(currentDate);
			orders.setState("1");
			orders.setOrdersNo(ordersNo);
			orders.setBeginSiteId(entity.getDotId());
			
			DecimalFormat df = new DecimalFormat("000");
			String ordersDetailNoStr = orders.getOrdersNo()+df.format(1); 
			CarVehicleModel carModel = car.getCarVehicleModel();//得到该车的车型
			
			OrdersDetail newOrdersDetail = new OrdersDetail();
			
			if(strategyBase != null && strategyBase.getIsPrepaidPay()==0){//如果后付费，像时租
				newOrdersDetail.setOrdersNo(orders.getOrdersNo());
				newOrdersDetail.setCreateTime(currentDate);
				newOrdersDetail.setStrategyId(strategyBase.getId());
				newOrdersDetail.setStrategyTitle(strategyBase.getName());
				newOrdersDetail.setTypeId(d.getId());
				newOrdersDetail.setTypeName(d.getCnName());
				newOrdersDetail.setOrdersDetailNo(ordersDetailNoStr);
				newOrdersDetail.setSubscriberId(s.getId());
				newOrdersDetail.setOrdersId(orders.getId());
				newOrdersDetail.setIsPrePay(strategyBase.getIsPrepaidPay().toString());
				newOrdersDetail.setIsPaid("0");
				newOrdersDetail.setIsOver("0");
				newOrdersDetail.setIsTimeout("0");
				newOrdersDetail.setIsException("0");
				newOrdersDetail.setIsRunning("0");
				
				Integer timeBeforeGet = strategyBase.getTimeBeforeGet();//可提前取车的时间(分钟)
				Long startDateLong = (timeBeforeGet*60*1000) + currentDate.getTime();
				Date startDate = new Date(startDateLong);
				newOrdersDetail.setBeginTime(startDate);
				
				ordersDetailService.addOrdersDetail(newOrdersDetail);
				orders.setFirstDetailNo(newOrdersDetail.getOrdersDetailNo());
				orders.setRunningDetailNo(newOrdersDetail.getOrdersDetailNo());
				orders = ordersService.bookCarAndAddOrder(orders,car);
			}else if(strategyBase !=null && strategyBase.getIsPrepaidPay()==1){//预付费
				newOrdersDetail.setOrdersNo(orders.getOrdersNo());
				newOrdersDetail.setCreateTime(currentDate);
				newOrdersDetail.setStrategyId(strategyBase.getId());
				newOrdersDetail.setStrategyTitle(strategyBase.getName());
				newOrdersDetail.setTypeId(d.getId());
				newOrdersDetail.setTypeName(d.getCnName());
				newOrdersDetail.setOrdersDetailNo(ordersDetailNoStr);
				newOrdersDetail.setSubscriberId(s.getId());
				newOrdersDetail.setOrdersId(orders.getId());
				newOrdersDetail.setIsPrePay(strategyBase.getIsPrepaidPay().toString());
				newOrdersDetail.setIsPaid("0");
				newOrdersDetail.setIsOver("0");
				newOrdersDetail.setIsTimeout("0");
				newOrdersDetail.setIsException("0");
				newOrdersDetail.setIsRunning("0");
				
				//超时策略直接取时租策略
				newOrdersDetail.setTimeoutStrategyid(carModel.getShizuStrategy());
				
				Date startDate = getStartTime(currentDate, strategyBase);
				Date endDate = getEndTime(currentDate, strategyBase, startDate, unitCount);
				newOrdersDetail.setBeginTime(startDate);
				newOrdersDetail.setEndTime(endDate);
				
				//得到改租赁策略的单价
				BigDecimal bdBigDecimal = strategyBase.getBasePrice();
				bdBigDecimal = bdBigDecimal.multiply(BigDecimal.valueOf(unitCount));//预租周期乘以单价，得到总费用
				newOrdersDetail.setTotalFee(bdBigDecimal);
				
				ordersDetailService.addOrdersDetail(newOrdersDetail);
				orders.setRunningDetailNo(newOrdersDetail.getOrdersDetailNo());
				orders.setFirstDetailNo(newOrdersDetail.getOrdersDetailNo());
				orders = ordersService.bookCarAndAddOrder(orders,car);
//				result = Ajax.JSONResult(13, "跳转预定成功页面", newOrdersDetail.getOrdersDetailNo());
				result = Ajax.AppJsonResult(13, "跳转预定成功页面");
				return SUCCESS;
			}
			
			String str = "尊敬的" + s.getName() + ":您的时租订单已经预定成功，请您在"
					+ sdf.format(new Date(orders.getOrdersTime().getTime() + 20*60*1000))
					+ "前去指定网点取车，否则本次订单将自动取消。";
			if(StringHelper.isNotEmpty(orders.getId())){
				try {
					if (s != null) {
						SMSUtil.sendSMS(s.getPhoneNo(), str, SMSRecord.TYPE_ORDER);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
//				result = Ajax.JSONResult(13, "跳转预定成功页面", newOrdersDetail.getOrdersDetailNo());
				result = Ajax.AppJsonResult(13, "跳转预定成功页面");
				return SUCCESS;
			}else{
				result = Ajax.AppJsonResult(14, "预定失败！");
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.AppJsonResult(14, "预定失败！");
			return ERROR;
		}
	}
	
	/**
	 * 生成子订单号
	 * @param currentDate
	 * @return
	 */
	public String getOrderNo(Date currentDate){
		List<Orders> list = ordersService.querySingleDayOrder(currentDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateString = sdf.format(currentDate);
		StringBuffer ordersNo = new StringBuffer();
		if(list!=null && list.size()!=0){
			int count = list.size();
			DecimalFormat df = new DecimalFormat("00000");
			ordersNo.append(dateString).append(df.format(count+1));
		}else{
			ordersNo.append(dateString).append("00001");
		}
		return ordersNo.toString();
	}
	
	/**
	 * 根据策略得到开始时间
	 * @param currentDate
	 * @param strategyBase
	 * @return
	 */
	public Date getStartTime(Date currentDate, StrategyBase strategyBase){
		Integer timeBeforeGet = strategyBase.getTimeBeforeGet();//可提前取车的时间(分钟)
		Long startDateLong = (timeBeforeGet*60*1000) + currentDate.getTime();
		Date startDate = new Date(startDateLong);
		return startDate;
	}
	
	/**
	 * 根据策略得到结束时间
	 * @param currentDate
	 * @param strategyBase
	 * @param startTime
	 * @return
	 */
	public Date getEndTime(Date currentDate, StrategyBase strategyBase, Date startTime, Integer unitCount){
		Long endDateLong = new Long(0);
		long singleTimeLong = 24*60*60*1000;//一天的时间长度
		endDateLong = startTime.getTime() + unitCount*singleTimeLong;//得到多少个周期以后的时间
		Date endDate = new Date(endDateLong);
		return endDate;
	}
	
	/**
	 * 订单详情接口
	 * @return
	 */
	public String getOrdersDetail() {
		String subscriberId = getRequest().getParameter("subscriberId");
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(6001, "参数subscriberId不能为空");
			return 	ERROR;
		}
		
		String ordersId = getRequest().getParameter("ordersId");
		if(StringUtils.isEmpty(ordersId)){
			result = Ajax.AppJsonResult(6001, "参数ordersId不能为空");
			return 	ERROR;
		}
		
		Orders orders = ordersService.queryOrdersById(ordersId);
		if(orders == null){
			result = Ajax.AppJsonResult(6002, "该订单不存在");
			return 	ERROR;
		}
		
		Car c = carService.queryCarById(orders.getCarId());
		
		AppOrdersDetail obj = new AppOrdersDetail();
		obj.setOrdersNo(orders.getOrdersNo());
		obj.setBeginTime(orders.getBeginTime());
		obj.setEndTime(orders.getEndTime());
		
		obj.setPlateNumber(c.getVehiclePlateId());
		
		CarVehicleModel cvm = c.getCarVehicleModel();
		if(cvm != null){
			Dict d = DictUtil.getDictById(cvm.getBrand());
			obj.setVehicleModelName(d.getCnName()+" "+cvm.getName());
			obj.setCasesNum(cvm.getCasesNum());
			obj.setLevelName(DictUtil.getCnNameByGroupCodeAndDictId("1",cvm.getLevel()));
		}
		
		if(orders.getState() != null){
			obj.setStateName(DictUtil.getCnNameByCode("14",orders.getState()));
		}
		
		BigDecimal currentFee = orders.getTotalFee();
		//得到当前进行中的子订单
		OrdersDetail currentOrdersDetail = 	ordersDetailService.getOrdersDetailByNo(orders.getRunningDetailNo());	
		if(!"1".equals(currentOrdersDetail.getIsPrePay())){//如果当前是时租，则计算费用
			BigDecimal countFee = strategyBaseService.conMoney(currentOrdersDetail.getStrategyId(),currentOrdersDetail.getBeginTime(),new Date(),orders.getMemberId(),orders.getCarId());
			currentFee = currentFee.add(countFee);
		}
		obj.setCurrentFee(currentFee);
		
		List<OrdersDetail> ordersDetails = ordersDetailService.getPaidOrdersDetailsByNo(orders.getOrdersNo());
//		obj.setOrders(orders);
		obj.setOrdersDetail(ordersDetails);
		result = Ajax.AppJsonResult(0, obj);
		return 	SUCCESS;
	}

	/**
	 * 历史订单列表接口
	 * @return
	 */
	public String getOrdersList() {
		String subscriberId = getRequest().getParameter("subscriberId");
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(6001, "参数subscriberId不能为空");
			return 	ERROR;
		}
		Subscriber subscriber = subscriberService.querySubscriberById(subscriberId);
		
		String type = getRequest().getParameter("type");
		
		String lastOrderId = getRequest().getParameter("lastOrderId");
		
		Integer currentNum=0;
		String currentNumStr = getRequest().getParameter("currentNum");
		if(!StringUtils.isEmpty(currentNumStr)){
			currentNum = Integer.valueOf(getRequest().getParameter("currentNum"));
		}
		
		String begintime = getRequest().getParameter("begintime");
		
		String endtime = getRequest().getParameter("endtime");
		
//		String lasttimetype = getRequest().getParameter("lasttimetype");
		
		String orderState = getRequest().getParameter("orderState");
		
		List<Orders> orders = ordersService.queryOrdersList(type,lastOrderId,currentNum,begintime,endtime,"lasttime","1",orderState,subscriber);
		
		if(orders != null && orders.size() > 0){
			for(Object obj : orders){
				Orders o = (Orders)obj;
				
				if(!StringHelper.isRealNull(o.getCarId())){
					Car c = carService.queryCarById(o.getCarId());
					if(c != null){
						o.setPlateNumber(c.getVehiclePlateId());
						
						CarVehicleModel cvm = c.getCarVehicleModel();
						if(cvm != null){
							Dict d = DictUtil.getDictById(cvm.getBrand());
							o.setVehicleModelName(d.getCnName()+" "+cvm.getName());
							o.setMicroWebModelPhoto(cvm.getMicroWebModelPhoto());
						}
					}
				}
				if(o.getState() != null){
					o.setStateName(DictUtil.getCnNameByCode("14",o.getState()));
				}
				o.setBeginTimeStr(o.getBeginTime() == null ? "" : transDate12String(o.getBeginTime()));
				o.setEndTimeStr(o.getEndTime() == null ? "" : transDate12String(o.getEndTime()));
				
				String firstOrderDetailNo = o.getFirstDetailNo();
				OrdersDetail od = ordersDetailService.getOrdersDetailByNo(firstOrderDetailNo);
				o.setFirstOrderType(od.getTypeName());
			}
		}
		
		result = Ajax.AppJsonResult(0, orders);
		return 	SUCCESS;
	}
	
	/**
	 * 当前订单接口
	 * @return
	 */
	public String getCurrentOrders() {
		String subscriberId = getRequest().getParameter("subscriberId");
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(6001, "参数subscriberId不能为空");
			return 	ERROR;
		}
		
		Subscriber subscriber = subscriberService.querySubscriberById(subscriberId);
		if(subscriber == null){
			result = Ajax.AppJsonResult(6002, "会员不存在");
			return 	ERROR;
		}
		
		Orders orders = ordersService.queryCurrentOrder(subscriber.getId());
		if(orders == null){
			result = Ajax.AppJsonResult(6003, "没有当前订单");
			return 	ERROR;
		}
		
		AppCurrentOrders obj = new AppCurrentOrders();
		List<OrdersDetail> ordersDetails = ordersDetailService.getPaidOrdersDetailsByNo(orders.getOrdersNo());
		
		CarRealtimeState carRealtimeState = carRealtimeStateService.getCarRealTimeState(orders.getCarId());
		Point res = GpsTransUtil.transPoint(carRealtimeState.getLat(), carRealtimeState.getLng());
		
		Car c = carService.queryCarById(orders.getCarId());
		CarVehicleModel cvm = c.getCarVehicleModel();
		int soc = NumberUtil.double2Int(carRealtimeState.getSOC());
		int km = getKmBySOC(soc, cvm);
			
		BigDecimal currentFee = orders.getTotalFee() == null ? new BigDecimal(0) : orders.getTotalFee();
		//得到当前进行中的子订单
		OrdersDetail currentOrdersDetail = 	ordersDetailService.getOrdersDetailByNo(orders.getRunningDetailNo());	
		if(!"1".equals(currentOrdersDetail.getIsPrePay())){//如果当前是时租，则计算费用
			BigDecimal countFee = strategyBaseService.conMoney(currentOrdersDetail.getStrategyId(),currentOrdersDetail.getBeginTime(),new Date(),subscriber.getId(),orders.getCarId());
			currentFee = currentFee.add(countFee);
		}
		
//		obj.setOrders(orders);
		obj.setOrdersId(orders.getId());
		obj.setOrdersDetail(ordersDetails);
		obj.setLongitude(res.getLng());
		obj.setLatitude(res.getLat());
		obj.setSoc(soc);
		obj.setKm(km);
		obj.setCurrentFee(currentFee);
		result = Ajax.AppJsonResult(0, obj);
		return 	SUCCESS;
	}
	
	public static Integer getKmBySOC(Integer soc,CarVehicleModel cvm){
		if(cvm == null){
			return 0;
		}
		Integer km = 0;
		if(soc >= 90){
			km = NumberUtil.double2Int(cvm.getMileage90() + ((cvm.getMileage100() - cvm.getMileage90()) * ((soc - 90.0)/10.0)));
		}
		else if(soc >= 80){
			km = NumberUtil.double2Int(cvm.getMileage80() + ((cvm.getMileage90() - cvm.getMileage80()) * ((soc - 80.0)/10.0)));
		}
		else if(soc >= 70){
			km = NumberUtil.double2Int(cvm.getMileage70() + ((cvm.getMileage80() - cvm.getMileage70()) * ((soc - 70.0)/10.0)));
		}
		else if(soc >= 60){
			km = NumberUtil.double2Int(cvm.getMileage60() + ((cvm.getMileage70() - cvm.getMileage60()) * ((soc - 60.0)/10.0)));
		}
		else if(soc >= 50){
			km = NumberUtil.double2Int(cvm.getMileage50() + ((cvm.getMileage60() - cvm.getMileage50()) * ((soc - 50.0)/10.0)));
		}
		else if(soc >= 40){
			km = NumberUtil.double2Int(cvm.getMileage40() + ((cvm.getMileage50() - cvm.getMileage40()) * ((soc - 40.0)/10.0)));
		}
		else if(soc >= 30){
			km = NumberUtil.double2Int(cvm.getMileage30() + ((cvm.getMileage40() - cvm.getMileage30()) * ((soc - 30.0)/10.0)));
		}
		else if(soc >= 20){
			km = NumberUtil.double2Int(cvm.getMileage20() + ((cvm.getMileage30() - cvm.getMileage20()) * ((soc - 20.0)/10.0)));
		}
		else if(soc >= 0){
			km = NumberUtil.double2Int( cvm.getMileage20() * soc / 20.0);
		}
		return km;
	}
	
	
	/**
	 * 选择需要开发票的订单
	 * @return
	 */
	public String chooseNeedBillOrders() {
		String subscriberId = getRequest().getParameter("subscriberId");
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(6001, "参数subscriberId不能为空");
			return 	ERROR;
		}
		
		List<Orders> list = ordersService.getNoOpenBillOrders(subscriberId);
		if(list!=null&&list.size()!=0){
			for(int i=0;i<list.size();i++){
				Orders orders = list.get(i);
				if(!StringHelper.isRealNull(orders.getCarId())){
					Car c = carService.queryCarById(orders.getCarId());
					if(c != null){
						orders.setPlateNumber(c.getVehiclePlateId());
						CarVehicleModel cvm = c.getCarVehicleModel();
						if(cvm != null){
							Dict d = DictUtil.getDictById(cvm.getBrand());
							orders.setVehicleModelName(d.getCnName()+" "+cvm.getName());
							orders.setMicroWebModelPhoto(cvm.getMicroWebModelPhoto());
						}
					}
				}
				
				orders.setBeginTimeStr(orders.getBeginTime() == null ? "" : transDate12String(orders.getBeginTime()));
				orders.setEndTimeStr(orders.getEndTime() == null ? "" : transDate12String(orders.getEndTime()));
			}
		}
		
		result = Ajax.AppJsonResult(0, list);
		return 	SUCCESS;
	}
	
	/**
	 * 确认索取发票
	 * @return
	 */
	public String confirmRequestBill() {
		String subscriberId = getRequest().getParameter("subscriberId");
		if(StringUtils.isEmpty(subscriberId)){
			result = Ajax.AppJsonResult(1, "参数subscriberId不能为空");
			return 	ERROR;
		}
		Subscriber subscriber = subscriberService.querySubscriberById(subscriberId);
		
		String orderNos = getRequest().getParameter("orderNos");
		if(StringUtils.isEmpty(orderNos)){
			result = Ajax.AppJsonResult(2, "参数orderNos不能为空");
			return 	ERROR;
		}
		
		String totalFeeStr = getRequest().getParameter("totalFee");
		BigDecimal totalFee = new BigDecimal(0);
		if(StringUtils.isEmpty(totalFeeStr)){
			result = Ajax.AppJsonResult(3, "参数totalFee不能为空");
			return 	ERROR;
		}else{
			BigDecimal bd = new BigDecimal(Integer.valueOf(totalFeeStr));
			totalFee = totalFee.add(bd);
		}
		
		
		String[] ordersNoStrings = orderNos.split(",");
		if(ordersNoStrings.length != 0){
			for(int i=0;i<ordersNoStrings.length;i++){
				String no = ordersNoStrings[i];
				if(!StringUtils.isEmpty(no)){
					Orders orders = ordersService.queryOrdersByOrderNo(no);
					orders.setIsOpenBill(1);
					ordersService.updateOrders(orders);
				}else{
					result = Ajax.AppJsonResult(4,"请您选择代开发票的订单！");
					return ERROR;
				}
			}
		}
		
		OrdersBill ordersBill = new OrdersBill();
		ordersBill.setRecipients(subscriber.getName());
		ordersBill.setAddress(subscriber.getPostAddress());
		ordersBill.setTelphone(subscriber.getPhoneNo());
		ordersBill.setTitle(subscriber.getBillTitle());
		ordersBill.setTotalFee(totalFee);
		ordersBill.setState("0");
		ordersBill.setCreateDate(new Date());
		ordersBillService.addBill(ordersBill);
		
		result = Ajax.AppJsonResult(0, "索取发票成功");
		return 	SUCCESS;
	}
	
	/**
	 * 订单剩余时间
	 * @return
	 */
	public String getGetCarRemainTime(){
		String ordersNo = getRequest().getParameter("ordersNo");
		if(StringUtils.isEmpty(ordersNo)){
			result = Ajax.AppJsonResult(1, "参数ordersNo不能为空");
			return 	ERROR;
		}
		
		Orders orders = ordersService.queryOrdersByOrderNo(ordersNo);
		OrdersDetail ordersDetail = ordersDetailService.getOrdersDetailByNo(orders.getFirstDetailNo());
		StrategyBase strategyBase = strategyBaseService.searchStrategyBaseById(ordersDetail.getStrategyId());
		
		Integer timeBeforeGet = strategyBase.getTimeBeforeGet();//可提前取车时间.单位分钟
		Date ordtime = ordersDetail.getCreateTime();
		Long time = new Date().getTime() - ordtime.getTime();
		result = Ajax.AppJsonResult(0, timeBeforeGet*60*1000 - time);
		return SUCCESS;
	}
	
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}

	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}

	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}

	public OrdersBillService getOrdersBillService() {
		return ordersBillService;
	}

	public void setOrdersBillService(OrdersBillService ordersBillService) {
		this.ordersBillService = ordersBillService;
	}

	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}

	public void setCarVehicleModelService(
			CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}

	public OrdersDetailService getOrdersDetailService() {
		return ordersDetailService;
	}

	public void setOrdersDetailService(OrdersDetailService ordersDetailService) {
		this.ordersDetailService = ordersDetailService;
	}

	public StrategyBaseService getStrategyBaseService() {
		return strategyBaseService;
	}

	public void setStrategyBaseService(StrategyBaseService strategyBaseService) {
		this.strategyBaseService = strategyBaseService;
	}

	public OrderPayService getOrderPayService() {
		return orderPayService;
	}

	public void setOrderPayService(OrderPayService orderPayService) {
		this.orderPayService = orderPayService;
	}

	public WechatUserInfoService getWechatUserInfoService() {
		return wechatUserInfoService;
	}

	public void setWechatUserInfoService(WechatUserInfoService wechatUserInfoService) {
		this.wechatUserInfoService = wechatUserInfoService;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public CarRealtimeStateService getCarRealtimeStateService() {
		return carRealtimeStateService;
	}

	public void setCarRealtimeStateService(
			CarRealtimeStateService carRealtimeStateService) {
		this.carRealtimeStateService = carRealtimeStateService;
	}

	
}
