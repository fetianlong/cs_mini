package com.dearho.cs.orders.action;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.charge.pojo.ChargeStation;
import com.dearho.cs.charge.service.ChargeStationService;
import com.dearho.cs.device.pojo.Device;
import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.device.service.DeviceService;
import com.dearho.cs.feestrategy.pojo.StrategyBase;
import com.dearho.cs.feestrategy.service.StrategyBaseService;
import com.dearho.cs.orders.pojo.ChargingLog;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersDetail;
import com.dearho.cs.orders.service.ChargingLogService;
import com.dearho.cs.orders.service.OrdersDetailService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.pojo.CarDotBinding;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.place.service.CarDotBindingService;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.resmonitor.service.CarRealtimeStateService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.OperateLog;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.service.OperateLogService;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.DeviceSocket;
import com.dearho.cs.util.GpsTransUtil;
import com.dearho.cs.util.NumberUtil;
import com.dearho.cs.util.Point;
import com.dearho.cs.util.StringHelper;
import com.dearho.cs.wechat.pojo.WechatTokenInfo;
import com.dearho.cs.wechat.util.WeixinUtil;
import com.opensymphony.webwork.ServletActionContext;

/**
 * 
 * 微网站用户当前订单
 * @author GaoYunpeng
 *
 */
public class MobileCurrentOrdersAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	public static final Log logger = LogFactory.getLog(MobileCurrentOrdersAction.class);
	
	private OrdersService ordersService;
	private SubscriberService subscriberService;
	private CarService carService;
	private CarRealtimeStateService carRealtimeStateService;
	private ChargeStationService chargeStationService;
	private ChargingLogService chargingLogService;
	private AccountService accountService;
	private BranchDotService branchDotService;
	private DeviceBindingService deviceBindingService;
	private StrategyBaseService strategyBaseService;

	private Orders orders;
	private Integer soc;
	private Integer km;
	private String ordersId;
	private ChargingLog chargingLog;
	private String chargingLogId;
	private BranchDot carBranchDot;
	
	private String socimg;
	private String kmimg;
	
	private String useCarTimeStr;
	private Long remainTime;

	//微信定位所需要的参数
	private String appId;
	private String timestamp;
	private String nonceStr;
	private String signature;
	
	private CarRealtimeState carRealtimeState;
	
	private OperateLogService operateLogService;
	
//	private List<BranchDot> returnBranchDots;
	private String dotId;
	private String showLoading;
	private String carId;
	
	private DeviceService deviceService;
	private OrdersDetailService ordersDetailService;
	
	private OrdersDetail currentOrdersDetail;
	
	private String ordersDetailId;
	
	private CarDotBindingService carDotBindingService;
	
	private StrategyBase currentStrategyBase;
	
	private Dict currentType;
	
	public MobileCurrentOrdersAction() {
		super();
	}

	@Override
	public String process() {
		return SUCCESS;
	}
	
	public String getGetCarRemainTime(){
		orders = ordersService.queryOrdersById(ordersId);
		OrdersDetail ordersDetail = ordersDetailService.getOrdersDetailByNo(orders.getFirstDetailNo());
		StrategyBase strategyBase = strategyBaseService.searchStrategyBaseById(ordersDetail.getStrategyId());
		
		if(ordersDetail != null && ordersDetail.getIsPrePay().equals("1")){
			Integer timeBeforeGet = strategyBase.getTimeBeforeGet();//可提前取车时间.单位分钟
			Date ordtime = ordersDetail.getCreateTime();
			Long time = new Date().getTime() - ordtime.getTime();
			result = Ajax.JSONResult(0, "", timeBeforeGet*60*1000 - time);
		}else{
			Date ordtime = orders.getCreateDate();
			Long time = new Date().getTime() - ordtime.getTime();
			result = Ajax.JSONResult(0, "", 20*60*1000 - time);
		}
		return SUCCESS;
	}
	
	/**
	 * 到当前订单页面
	 * @return
	 */
	public String toCurrentOrder(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return LOGIN;
		}
		orders = ordersService.queryCurrentOrder(s.getId());
		if(orders == null || !s.getId().equals(orders.getMemberId())){
			orders = ordersService.queryLast5MinutesOrders(s.getId());
			if(orders != null){
				Car car = carService.queryCarById(orders.getCarId());
				CarVehicleModel cvm = car.getCarVehicleModel();
				if(cvm != null){
					Dict d = DictUtil.getDictById(cvm.getBrand());
					orders.setVehicleModelName(d.getCnName()+" "+cvm.getName());
					car.setCarVehicleModel(cvm);
					orders.setCar(car);
				}
				Long useTime = orders.getEndTime().getTime() - orders.getBeginTime().getTime();
				useCarTimeStr = (useTime/(1000*60*60))+"时"+((useTime/(1000*60))%60)+"分";
				remainTime =  (orders.getEndTime().getTime() + 1000*60*5) - new Date().getTime();
				return "returnbackcar";
			}
			orders = null;
			return SUCCESS;
		}
		
		OrdersDetail firstDetail = ordersDetailService.getOrdersDetailByNo(orders.getFirstDetailNo());
		if(orders != null && "1".equals(firstDetail.getIsPrePay()) && !"1".equals(firstDetail.getIsPaid())){//没有付款的预付费订单
			return "pay";
		}
		if("1".equals(orders.getState())){
			return "finish";//开车门页面
		}
		else if(!"3".equals(orders.getState())){
			return "history";
		}
		if(!StringHelper.isRealNull(orders.getCarId())){
			Car c = carService.queryCarById(orders.getCarId());
			if(c != null){
				orders.setPlateNumber(c.getVehiclePlateId());
				CarVehicleModel cvm = c.getCarVehicleModel();
				if(cvm != null){
					Dict d = DictUtil.getDictById(cvm.getBrand());
					orders.setVehicleModelName(d.getCnName()+" "+cvm.getName());
					c.setCarVehicleModel(cvm);
					orders.setCar(c);
				}
				carRealtimeState = carRealtimeStateService.getCarRealTimeState(orders.getCarId());
				if(carRealtimeState != null){
					
					Point res = GpsTransUtil.transPoint(carRealtimeState.getLat(), carRealtimeState.getLng());
					carRealtimeState.setLat(res.getLat());
					carRealtimeState.setLng(res.getLng());
					
					soc = NumberUtil.double2Int(carRealtimeState.getSOC());
					km = getKmBySOC(soc, cvm);
					Double kmPre = km * 100 / cvm.getMileage100();
					Integer socint = NumberUtil.double2Int(soc/5) * 5;
					Integer kmint = NumberUtil.double2Int(kmPre/5) * 5;
					socimg = ""+socint;
					kmimg = ""+kmint;
					
					List<OrdersDetail> ordersDetails = ordersDetailService.getPaidOrdersDetailsByNo(orders.getOrdersNo());
					if(ordersDetails!=null && ordersDetails.size()!=0){
						for(int i=0;i<ordersDetails.size();i++){
							OrdersDetail ordersDetail = ordersDetails.get(i);
							BigDecimal mileFee = strategyBaseService.conMileFee(ordersDetail.getStrategyId(),ordersDetail.getBeginTime(),new Date(),orders.getCarId());
							BigDecimal mile = strategyBaseService.conMile(ordersDetail.getStrategyId(),ordersDetail.getBeginTime(),new Date(),orders.getCarId());
							BigDecimal timeFee = strategyBaseService.conTimeFee(ordersDetail.getStrategyId(),ordersDetail.getBeginTime(),new Date(),orders.getCarId());
							
							ordersDetail.setMileFee(mileFee);
							ordersDetail.setMileage(mile);
							ordersDetail.setTimeFee(timeFee);
						}
					}
					
					currentOrdersDetail = ordersDetailService.getRunningOrdersDetail(orders.getOrdersNo());
					currentStrategyBase = strategyBaseService.searchStrategyBaseById(currentOrdersDetail.getStrategyId());
					currentType = DictUtil.getDictById(currentStrategyBase.getType());
					orders.setOrdersDetail(ordersDetails);
				}
			}
			
		}
		
		WechatTokenInfo tokenInfo = WeixinUtil.getJsapiTicket();
		String jsapi_ticket = tokenInfo.getWxValue();
		String url = WeixinUtil.getUrlForSignature(getRequest());
		Map<String, String> ret = WeixinUtil.sign(jsapi_ticket, url);
		for (Map.Entry entry : ret.entrySet()) {
			if (entry.getKey().equals("signature")) {
				signature = (String) entry.getValue();
			}else if (entry.getKey().equals("timestamp")) {
				timestamp = (String) entry.getValue();
			}else if (entry.getKey().equals("nonceStr")) {
				nonceStr = (String) entry.getValue();
			}
		}
		appId = WeixinUtil.appId;
		return SUCCESS;
	}
	
	public String getOrderStartTime(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return LOGIN;
		}
		orders = ordersService.queryCurrentOrder(s.getId());
		OrdersDetail odDetail = ordersDetailService.getOrdersDetailByNo(orders.getRunningDetailNo());
		if(orders!=null && orders.getBeginTime()!=null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,odDetail.getBeginTime().getTime()+"");
		}else{
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "");
		}
		return 	SUCCESS;
	}
	
	/**
	 * 开始使用车辆
	 * @return
	 */
	public String startUseCar(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return LOGIN;
		}
		orders = ordersService.queryOrdersById(ordersId);
		OrdersDetail ordersDetail = ordersDetailService.getOrdersDetailByNo(orders.getFirstDetailNo());
		if(orders == null || !s.getId().equals(orders.getMemberId())){
			result = Ajax.JSONResult(1, "订单无效");
			return SUCCESS;
		}
		if(!sendCommand()){
			return SUCCESS;
		}
		
		Date startUseCarTime = new Date();
		orders.setBeginTime(startUseCarTime);
		orders.setState("3");
		orders.setRunningDetailNo(ordersDetail.getOrdersDetailNo());
		ordersService.updateOrders(orders);
		
		//开始使用车辆，修改子订单状态
		if(ordersDetail.getIsPrePay().equals("0")){
			ordersDetail.setBeginTime(startUseCarTime);
		}else{
			Date beginTime = ordersDetail.getBeginTime();
			if(startUseCarTime.getTime() < beginTime.getTime()){
				//如果开门时间在预定开始时间之前，则取当前开门时间为订单开始时间，订单结束时间不变
				ordersDetail.setBeginTime(startUseCarTime);
			}
		}
		ordersDetail.setIsRunning("1");
		ordersDetailService.updateOrdersDetail(ordersDetail);
		result = Ajax.JSONResult(0, "");
		return SUCCESS;
	}
	
	private boolean sendCommand(){
		String str="Login";
		List<DeviceBinding> dbs = deviceBindingService.queryBindByCarId(orders.getCarId(), 1);
		if(dbs == null ||  dbs.size() <= 0){
			result = Ajax.JSONResult(1, "找不到对应的车辆");
			return false;
		}
		String deviceId = dbs.get(0).getDeviceId();
		Device device = deviceService.queryCarSetById(deviceId);
		try {
			SMSUtil.sendSMS(device.getSimId().trim(), str,SMSRecord.TYPE_ORDER);
		} catch (Exception e1) {
			logger.error(e1);
		}
		return true;
	}
	
	/**
	 * 开车门
	 * @return
	 */
	public String openDoor(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return LOGIN;
		}
		orders = ordersService.queryOrdersById(ordersId);
		if(orders == null || !s.getId().equals(orders.getMemberId())){
			result = Ajax.JSONResult(1, "订单无效");
			return SUCCESS;
		}
		OperateLog operateLog = null;
//		OperateLog operateLog = operateLogService.searchLastDoorOperateLog(s.getId(),ordersId,"unlock",30*1000);
//		if(operateLog != null){
//			result = Ajax.JSONResult(1, "您的操作太频繁，请稍后再试");
//			return SUCCESS;
//		}
		List<DeviceBinding> dbs = deviceBindingService.queryBindByCarId(orders.getCarId(), 1);
		if(dbs != null && dbs.size() > 0){
			String returnStr = "";
			boolean hasSendMsg = false;
			for (int i = 0; i < 3; i++) {
				returnStr = DeviceSocket.sendDeviceCommand("unlock", dbs.get(0).getDeviceNo(),null,null);
				if("0".equals(returnStr)){
					break;
				}
				if(!hasSendMsg){
					if(!sendCommand()){
						return SUCCESS;
					}
					hasSendMsg = true;
				}
				try {
					Thread.sleep(5*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if("0".equals(returnStr)){
				result = Ajax.JSONResult(0, "");
				operateLog = new OperateLog();
				operateLog.setUserId(s.getId());
				operateLog.setModular(ordersId);
				operateLog.setType("unlock");
				operateLog.setTs(new Date());
				operateLogService.addOperateLog(operateLog);
				return SUCCESS;
			}
			else{
				result = Ajax.JSONResult(1, "车机正在启动，请稍后再试");
				return SUCCESS;
			}
		}
		else{
			result = Ajax.JSONResult(1, "无法获取车机编号");
			return SUCCESS;
		}
		
	}
	
	/**
	 * 锁车门
	 * @return
	 */
	public String closeDoor(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return LOGIN;
		}
		orders = ordersService.queryOrdersById(ordersId);
		if(orders == null || !s.getId().equals(orders.getMemberId())){
			result = Ajax.JSONResult(1, "订单无效");
			return SUCCESS;
		}
		OperateLog operateLog = null;
//		OperateLog operateLog = operateLogService.searchLastDoorOperateLog(s.getId(),ordersId,"lock",30*1000);
//		if(operateLog != null){
//			result = Ajax.JSONResult(1, "您的操作太频繁，请稍后再试");
//			return SUCCESS;
//		}
		List<DeviceBinding> dbs = deviceBindingService.queryBindByCarId(orders.getCarId(), 1);
		if(dbs != null && dbs.size() > 0){
			String returnStr = "";
			boolean hasSendMsg = false;
			for (int i = 0; i < 3; i++) {
				returnStr = DeviceSocket.sendDeviceCommand("lock", dbs.get(0).getDeviceNo(),null,null);
				if("0".equals(returnStr)){
					break;
				}
				if(!hasSendMsg){
					if(!sendCommand()){
						return SUCCESS;
					}
					hasSendMsg = true;
				}
				try {
					Thread.sleep(5*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if("0".equals(returnStr)){
				result = Ajax.JSONResult(0, "");
				operateLog = new OperateLog();
				operateLog.setUserId(s.getId());
				operateLog.setModular(ordersId);
				operateLog.setType("lock");
				operateLog.setTs(new Date());
				operateLogService.addOperateLog(operateLog);
				return SUCCESS;
			}
			else{
				result = Ajax.JSONResult(1, "下发指令失败");
				return SUCCESS;
			}
		}
		else{
			result = Ajax.JSONResult(1, "无法获取车机编号");
			return SUCCESS;
		}
	}

	
	
	
	/**
	 * 还车页面
	 * @return
	 */
	public String toBackCarInfo(){
		orders = ordersService.queryOrdersByDetailId(ordersDetailId);
//		orders = ordersService.queryOrdersById(ordersId);
		if(orders == null){
			return ERROR;
		}
		ordersId = orders.getId();
		dotId = orders.getBeginSiteId();
		carId = orders.getCarId();
//		BranchDot bd = branchDotService.getBranchDotById(orders.getBeginSiteId());
//		if(bd != null){
//			String returnDotIdStr = bd.getReturnbackDot();
//			if(StringHelper.isEmpty(returnDotIdStr)){
//				returnBranchDots = new ArrayList<BranchDot>();
//				returnBranchDots.add(bd);
//			}
//			else{
//				returnDotIdStr = returnDotIdStr.substring(0, returnDotIdStr.length() - 1);
//				String dotHql = "from BranchDot where id in (" + returnDotIdStr + ")";
//				returnBranchDots = branchDotService.find(dotHql);
//			}
//			
//		}
		return SUCCESS;
	}
	
	public String getCarRealTimeState(){
		CarRealtimeState crs = carRealtimeStateService.getCarRealTimeState(carId);
		result = Ajax.JSONResult(0, "", crs);
		return SUCCESS;
	}
	
	public String countCurrentFee(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return LOGIN;
		}
		orders = ordersService.queryCurrentOrder(s.getId());
		OrdersDetail odDetail = ordersDetailService.getOrdersDetailByNo(orders.getRunningDetailNo());
		
		BigDecimal currentFee = strategyBaseService.conMoney(odDetail.getStrategyId(),odDetail.getBeginTime(),new Date(),s.getId(),orders.getCarId());
		if(odDetail.getTicketsFee()!=null){
			currentFee = currentFee.add(odDetail.getTicketsFee());
		}
		
		result = Ajax.JSONResult(0, "", currentFee);
		return SUCCESS;
	}
	
	/**
	 * 计算里程费用
	 * @return
	 */
	public String countKmFee(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return LOGIN;
		}
		orders = ordersService.queryCurrentOrder(s.getId());
		OrdersDetail odDetail = ordersDetailService.getOrdersDetailByNo(orders.getRunningDetailNo());
		
		BigDecimal currentFee = strategyBaseService.conMoney(odDetail.getStrategyId(),odDetail.getBeginTime(),new Date(),s.getId(),orders.getCarId());
		result = Ajax.JSONResult(0, "", currentFee);
		return SUCCESS;
	}
	
	/**
	 * 检查是否可以还车
	 * @return
	 */
	public String checkReturnBackCar(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			result = Ajax.JSONResult(6, "");
			return SUCCESS;
		}
		orders = ordersService.queryOrdersById(ordersId);
		if(orders == null || !s.getId().equals(orders.getMemberId()) ){
			result = Ajax.JSONResult(1, "订单无效");
			return SUCCESS;
		}
		if("2".equals(orders.getState())){
			result = Ajax.JSONResult(1, "还没有开始使用车辆，不用还车");
			return SUCCESS;
		}
		else if(!"3".equals(orders.getState())){
			result = Ajax.JSONResult(1, "订单已经结束");
			return SUCCESS;
		}
		
		String returnBackDotId = getRequest().getParameter("returnBackDotId");
		if(returnBackDotId == null){
			result = Ajax.JSONResult(1, "找不到对应的还车网点");
			return SUCCESS;
		}
		BranchDot returnBackDot = branchDotService.getBranchDotById(returnBackDotId);
		if(returnBackDot == null){
			result = Ajax.JSONResult(1, "找不到对应的还车网点");
			return SUCCESS;
		}
		Integer place = returnBackDot.getTotalParkingPlace();
		List<CarDotBinding> carDotBindings = carDotBindingService.searchBindingByDotId(returnBackDotId, 1);
		if(carDotBindings == null || carDotBindings.size() >= place){
			result = Ajax.JSONResult(4, "网点车辆已满，不能还车了，请您开车到其他网点还车，谢谢！");
			return SUCCESS;
		}
//		chargingLog = chargingLogService.queryChargingLogByOrdersId(ordersId);
//		if(chargingLog != null && chargingLog.getEndTime() == null){
//			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"您的车辆在补电中，请您先结束补电再还车。");
//			return SUCCESS;
//		}
		OrdersDetail ordersDetail = ordersDetailService.getRunningOrdersDetail(orders.getOrdersNo());
		if(ordersDetail == null){
			result = Ajax.JSONResult(1, "订单已经结束");
			return SUCCESS;
		}

		if(!"1".equals(ordersDetail.getIsPaid())){
			BigDecimal fee = strategyBaseService.conMoney(ordersDetail.getStrategyId(), ordersDetail.getBeginTime(), 
					ordersDetail.getEndTime() == null ? new Date() : ordersDetail.getEndTime(), orders.getMemberId(),orders.getCarId());
//			OrderMessage orderMessage = new OrderMessage();
//			orderMessage = subscriberService.validateAccountForOrder(s.getId(), fee.doubleValue(), orders.getPayStyle());
//			
//			if (orderMessage.getCode() == OrderMessage.CODE_FAIL) {
//				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,
//						"本次租车消费"+fee+"，您的余额不足，请您充值后还车。");
//				return SUCCESS;
//			}
			ordersDetail.setTotalFee(fee);
			ordersDetailService.updateOrdersDetail(ordersDetail);
			
			orders.setEndSiteId(returnBackDotId);
			ordersService.updateOrders(orders);
			
			session.setAttribute(Constants.RETURN_BACK_CAR_PAY_ORDERS, ordersDetail);
			result = Ajax.JSONResult(5,"需要支付租车费用");
			return SUCCESS;
		}
		result = Ajax.JSONResult(0, "");
		return SUCCESS;
		
	}
	
	/**
	 * 还车
	 * @return
	 */
	public String returnBackCar(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			result = Ajax.JSONResult(1, "请您重新登录");
			return SUCCESS;
		}
		orders = ordersService.queryOrdersById(ordersId);
		if(orders == null || !s.getId().equals(orders.getMemberId()) ){
			result = Ajax.JSONResult(2, "当前无订单，请您预定车辆");
			return SUCCESS;
		}
		if(!"3".equals(orders.getState())){
			result = Ajax.JSONResult(3, "订单未开启，请您启动车辆，开启订单");
			return SUCCESS;
		}
		String returnBackDotId = getRequest().getParameter("returnBackDotId");
		if(returnBackDotId == null){
			result = Ajax.JSONResult(3, "找不到还车网点");
			return SUCCESS;
		}
		BranchDot returnBackDot = branchDotService.getBranchDotById(returnBackDotId);
		if(returnBackDot == null){
			result = Ajax.JSONResult(3, "找不到还车网点");
			return SUCCESS;
		}
		Integer place = returnBackDot.getTotalParkingPlace();
		List<CarDotBinding> carDotBindings = carDotBindingService.searchBindingByDotId(returnBackDotId, 1);
		if(carDotBindings == null || carDotBindings.size() >= place){
			result = Ajax.JSONResult(3, "网点车辆已满，不能还车了，请您开车到其他网点还车，谢谢！");
			return SUCCESS;
		}
		
		orders.setEndSiteId(returnBackDotId);
		ordersService.updateOrders(orders);
		boolean returnResult = ordersService.completeOrder(ordersDetailId,null,null,getRequest(),getResponse());
		if(returnResult){
			result = Ajax.JSONResult(0, "");
			return SUCCESS;
		}
		result = Ajax.JSONResult(3, "出了点小问题，请您稍后再试");
		return SUCCESS;
	}
	
	public String toCompleteOrder(){
		result = Ajax.JSONResult(0, "");
		return SUCCESS;
	}
	
	public String toReturnComplete(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return LOGIN;
		}
		String hql = "select a.id from Orders a where a.memberId = '"+s.getId()+"' and a.state = '4' order by a.endTime desc";
		List<Orders> ordersList = ordersService.queryOrders(hql);
		if(ordersList != null && ordersList.size() > 0){
			orders = ordersList.get(0);
			Car car = carService.queryCarById(orders.getCarId());
			orders.setVehicleModelName(car.getCarVehicleModel().getName());
		}
		return SUCCESS;
	}
	
	/**
	 * 到当前车辆信息页面
	 * @return
	 */
	public String toCurrentCarInfo(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return LOGIN;
		}
		orders = ordersService.queryOrdersById(ordersId);
		Date beginTime = orders.getBeginTime();
		if(beginTime != null){
			orders.setBeginTimeStr(DateUtil.formatDate(beginTime, "yyyy-MM-dd HH:mm"));
		}
		
		if(orders != null){
			Car car = carService.queryCarById(orders.getCarId());
			carBranchDot = branchDotService.getBranchDotByCarId(car.getId());
			CarVehicleModel cvm = car.getCarVehicleModel();
			if(cvm != null){
				Dict d = DictUtil.getDictById(cvm.getBrand());
				orders.setVehicleModelName(d.getCnName()+" "+cvm.getName());
				car.setCarVehicleModel(cvm);
				orders.setCar(car);
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 到附近充电站列表页面
	 * @return
	 */
	public String toChargingStationListPage(){
		orders = ordersService.queryOrdersById(ordersId);
		carId = orders.getCarId();
		return SUCCESS;
	}
	
	/**
	 * 查询附近充电站
	 * @return
	 */
	public String searchChargeStation(){
		List<ChargeStation> stations = chargeStationService.searchChargeStationByCode(null);
		result = Ajax.JSONResult(0, "", stations);
		return SUCCESS;
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
	public String getDotNameById(String dotId){
		BranchDot dot = branchDotService.getBranchDotById(dotId);
		if(dot != null){
			return dot.getName();
		}
		return "";
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
	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	public CarRealtimeStateService getCarRealtimeStateService() {
		return carRealtimeStateService;
	}
	public void setCarRealtimeStateService(CarRealtimeStateService carRealtimeStateService) {
		this.carRealtimeStateService = carRealtimeStateService;
	}
	public Integer getSoc() {
		return soc;
	}
	public void setSoc(Integer soc) {
		this.soc = soc;
	}
	public Integer getKm() {
		return km;
	}
	public void setKm(Integer km) {
		this.km = km;
	}
	public String getOrdersId() {
		return ordersId;
	}
	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}
	public ChargingLogService getChargingLogService() {
		return chargingLogService;
	}
	public void setChargingLogService(ChargingLogService chargingLogService) {
		this.chargingLogService = chargingLogService;
	}
	public ChargingLog getChargingLog() {
		return chargingLog;
	}
	public void setChargingLog(ChargingLog chargingLog) {
		this.chargingLog = chargingLog;
	}
	public String getChargingLogId() {
		return chargingLogId;
	}
	public void setChargingLogId(String chargingLogId) {
		this.chargingLogId = chargingLogId;
	}
	public String getSocimg() {
		return socimg;
	}
	public void setSocimg(String socimg) {
		this.socimg = socimg;
	}
	public String getKmimg() {
		return kmimg;
	}
	public void setKmimg(String kmimg) {
		this.kmimg = kmimg;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public AccountService getAccountService() {
		return accountService;
	}
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}
	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}
	public String getUseCarTimeStr() {
		return useCarTimeStr;
	}
	public void setUseCarTimeStr(String useCarTimeStr) {
		this.useCarTimeStr = useCarTimeStr;
	}
	public Long getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(Long remainTime) {
		this.remainTime = remainTime;
	}
	public BranchDot getCarBranchDot() {
		return carBranchDot;
	}
	public void setCarBranchDot(BranchDot carBranchDot) {
		this.carBranchDot = carBranchDot;
	}
	public DeviceBindingService getDeviceBindingService() {
		return deviceBindingService;
	}
	public void setDeviceBindingService(
			DeviceBindingService deviceBindingService) {
		this.deviceBindingService = deviceBindingService;
	}
	public CarRealtimeState getCarRealtimeState() {
		return carRealtimeState;
	}
	public void setCarRealtimeState(CarRealtimeState carRealtimeState) {
		this.carRealtimeState = carRealtimeState;
	}
	public OperateLogService getOperateLogService() {
		return operateLogService;
	}
	public void setOperateLogService(OperateLogService operateLogService) {
		this.operateLogService = operateLogService;
	}
//	public List<BranchDot> getReturnBranchDots() {
//		return returnBranchDots;
//	}
//	public void setReturnBranchDots(List<BranchDot> returnBranchDots) {
//		this.returnBranchDots = returnBranchDots;
//	}
	public String getShowLoading() {
		return showLoading;
	}
	public void setShowLoading(String showLoading) {
		this.showLoading = showLoading;
	}
	public String getDotId() {
		return dotId;
	}
	public void setDotId(String dotId) {
		this.dotId = dotId;
	}
	public String getCarId() {
		return carId;
	}
	public void setCarId(String carId) {
		this.carId = carId;
	}
	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}
	public DeviceService getDeviceService() {
		return deviceService;
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


	public OrdersDetail getCurrentOrdersDetail() {
		return currentOrdersDetail;
	}

	public void setCurrentOrdersDetail(OrdersDetail currentOrdersDetail) {
		this.currentOrdersDetail = currentOrdersDetail;
	}

	public String getOrdersDetailId() {
		return ordersDetailId;
	}

	public void setOrdersDetailId(String ordersDetailId) {
		this.ordersDetailId = ordersDetailId;
	}
	public CarDotBindingService getCarDotBindingService() {
		return carDotBindingService;
	}
	public void setCarDotBindingService(
			CarDotBindingService carDotBindingService) {
		this.carDotBindingService = carDotBindingService;
	}

	public ChargeStationService getChargeStationService() {
		return chargeStationService;
	}

	public void setChargeStationService(ChargeStationService chargeStationService) {
		this.chargeStationService = chargeStationService;
	}

	public StrategyBase getCurrentStrategyBase() {
		return currentStrategyBase;
	}

	public void setCurrentStrategyBase(StrategyBase currentStrategyBase) {
		this.currentStrategyBase = currentStrategyBase;
	}

	public Dict getCurrentType() {
		return currentType;
	}

	public void setCurrentType(Dict currentType) {
		this.currentType = currentType;
	}

}
