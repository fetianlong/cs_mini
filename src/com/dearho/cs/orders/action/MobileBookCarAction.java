package com.dearho.cs.orders.action;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.account.service.OrderPayService;
import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.charge.pojo.ChargeStation;
import com.dearho.cs.charge.service.ChargeStationService;
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
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.pojo.CarDotBinding;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.place.service.CarDotBindingService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Config;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.service.ConfigService;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;
import com.dearho.cs.wechat.pojo.WechatTokenInfo;
import com.dearho.cs.wechat.pojo.WechatUserInfo;
import com.dearho.cs.wechat.service.WechatUserInfoService;
import com.dearho.cs.wechat.util.WeixinUtil;
import com.dearho.cs.wxpay.WxPayUtil;
import com.opensymphony.webwork.ServletActionContext;

public class MobileBookCarAction extends AbstractAction {

	private static final long serialVersionUID = 8464279510197825300L;
	private BranchDotService branchDotService;
	private ChargeStationService chargeStationService;
	private String dotId;
	private CarService carService;
	private BookCarInfoEntity carInfo;
	private Orders orders;
	private OrdersDetail ordersDetail;
	private OrdersBill ordersBill;

	private OrdersBillService ordersBillService;
	private StrategyBaseService strategyBaseService;
	private OrdersService ordersService;
	private SubscriberService subscriberService;
	private String getCarTime;
	private String backCarTime;
	private BranchDot choseCarBranchDot;//微信端选择车辆的网点
	private String carId;

	//微信定位所需要的参数
	private String appId;
	private String timestamp;
	private String nonceStr;
	private String signature;

	private String ordersBackSiteId;//还车网点id
	private BranchDot ordersBackSite;//还车网点
	private String bookFee;
	private String carImg;//车辆图片
	private CarVehicleModelService carVehicleModelService;
	
	private String showLoading;//是否显示loading图片(默认显示loading，如果为0，则不显示)
	private String hasCurrentOrder;//是否有当前订单 
	
	private Integer remainTime; //取车剩余时间
	
	private OrdersDetailService ordersDetailService;
	
	private String strategyBaseId;
	
	private String ordersDetailNo;
	
	private BigDecimal totalFee;
	
	private List<String> typeNameList;
	
	private List<StrategyCarShowInfo> strategyCarShowInfos;
	
	private Integer unitCount;
	
	private CarDotBindingService carDotBindingService;
	
	private WechatUserInfoService wechatUserInfoService;
	
	private OrderPayService orderPayService;
	
	private AccountService accountService;
	
	private String retMsg;//支付错误提示
	
	private StrategyBase shizuStrategy; 
	
	private StrategyBase rizuStrategy; 
	
	private ConfigService configService;
	
	@Override
	public String process() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s = (Subscriber) session
				.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请您先登录！");
			return ERROR;
		}
		Account account=accountService.getAccoutDetail(s.getId());
		

		/** 校验 start **/
		if (Subscriber.EVENT_STATE_HALF.equals(s.getEventState())) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "此账号已冻结，不能租车。 如有疑问，请联系客服。");
			return ERROR;
		}
		if (Subscriber.STATE_WAIT_CONFIRMED.equals(s.getState())
				|| Subscriber.STATE_NO_CONFIRMED.equals(s.getState())
				|| Subscriber.STATE_UNCONFIRMED.equals(s.getState())) {
			
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"帐户资料未审核通过，不能租车");
			return ERROR;
		}
		
		if(Account.IS_REFUND_TRUE.equals(account.getIsRefund())){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"退款流程处理中,不能租车");
			return ERROR;
		}
		
		
		BigDecimal maxFrozenMoney = new BigDecimal(Integer.parseInt(DictUtil.getCnNameByCode("maxFrozenMoney", "maxFrozenMoney")));
		if ((new BigDecimal(account.getFrozenAmount()).compareTo(maxFrozenMoney))<0) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"账户押金不足，请先充值");
			return ERROR;
		}
		
		Orders currentOrders = ordersService.queryCurrentOrder(s.getId());
		if (currentOrders != null) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,
					"您当前有未完成的订单！");
			return ERROR;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			orders.setOrdersTime(new Date());
			orders.setCarId(carId);
			// 验证车辆当前状态
			Car car = carService.queryCarById(carId);
			if (car != null) {
				Dict ydDict = DictUtil.getDictByCodes("carBizState", "0");
				if (!car.getBizState().equals(ydDict.getId())) {
					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,
							"当前车辆已预定！");
					return ERROR;
				}
			} else {
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "车辆不存在！");
				return ERROR;
			}
			Date currentDate = new Date();
			orders.setCreateDate(currentDate);
			orders.setTs(currentDate);
			if (s != null && StringHelper.isNotEmpty(s.getId())) {
				orders.setMemberId(s.getId());
			} else {
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "登录超时！");
				return ERROR;
			}
			BookCarInfoEntity entity = branchDotService.getCarInfo(carId);
			
			if (orders.getIsBill() != null && orders.getIsBill() == 1) {
				ordersBillService.addBill(ordersBill);
				orders.setBillId(ordersBill.getId());
			}
			String ordersNo = getOrderNo(currentDate);
			orders.setState("1");
			orders.setOrdersNo(ordersNo);
			orders.setBeginSiteId(entity.getDotId());
			
			DecimalFormat df = new DecimalFormat("000");
			String ordersDetailNoStr = orders.getOrdersNo()+df.format(1); 
			CarVehicleModel carModel = car.getCarVehicleModel();//得到该车的车型
			
			StrategyBase strategyBase = strategyBaseService.searchStrategyBaseById(strategyBaseId);//找到策略
			Dict d = DictUtil.getDictById(strategyBase.getType());//用户选择的租赁类型
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
				Date endDate = getEndTime(currentDate, strategyBase, startDate);
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
				result = Ajax.JSONResult(3, "", newOrdersDetail.getOrdersDetailNo());
				return SUCCESS;
			}
			
			String str = "尊敬的" + s.getName() + ":您的时租订单已经预定成功，请您在"
					+ sdf.format(new Date(orders.getOrdersTime().getTime() + 20*60*1000))
					+ "前去指定网点取车，否则本次订单将自动取消。【乐途出行】";
			if(StringHelper.isNotEmpty(orders.getId())){
				try {
					if (s != null) {
						SMSUtil.sendSMS(s.getPhoneNo(), str, SMSRecord.TYPE_ORDER);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "", orders);
				return SUCCESS;
			}else{
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "预定失败！");
				return ERROR;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "预定失败！");
			return ERROR;
		}

	}
	
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
	public Date getEndTime(Date currentDate, StrategyBase strategyBase, Date startTime){
		Long endDateLong = new Long(0);
		long singleTimeLong = 24*60*60*1000;//一天的时间长度
		endDateLong = startTime.getTime() + unitCount*singleTimeLong;//得到多少个周期以后的时间
		Date endDate = new Date(endDateLong);
		return endDate;
	}
	
	/**
	 * 跳转支付页面
	 * @return
	 */
	public String toPayFee(){
		Subscriber subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		if(ordersDetailNo == null){
			orders = ordersService.queryCurrentOrder(subscriber.getId());
			ordersDetail = ordersDetailService.getOrdersDetailByNo(orders.getRunningDetailNo());
		}else{
			ordersDetail = ordersDetailService.getOrdersDetailByNo(ordersDetailNo);
		}
		
		getRequest().getSession().setAttribute("payOrdersDetail", ordersDetail);
		BigDecimal totalFee =new BigDecimal(0);
		totalFee=totalFee.add(ordersDetail.getTotalFee());
		if(!StringUtils.isEmpty(ordersDetail.getNextOrders()) && ordersDetail.getIsPrePay().equals("0")){
			OrdersDetail ordersNextDetail=ordersDetailService.getOrdersDetailById(ordersDetail.getNextOrders());
			totalFee=totalFee.add(ordersNextDetail.getTotalFee());
			if (ordersNextDetail.getTicketsFee()!=null)
			totalFee=totalFee.add(ordersNextDetail.getTicketsFee());
		}
		
		getRequest().setAttribute("totalFee", totalFee.doubleValue());
		Account account=accountService.getAccoutDetail(subscriber.getId());
		getRequest().setAttribute("UsableAmount", account.getUsableAmount());
		
		boolean isEnoughMoney=true;
		if(account.getUsableAmount()!=null && totalFee.compareTo(new BigDecimal(account.getUsableAmount()))>0){
			isEnoughMoney=false;
		}
		getRequest().setAttribute("isEnoughMoney",isEnoughMoney);
		
//		WechatUserInfo wechatUserInfo=wechatUserInfoService.getUserInfoBySubscriberId(subscriber.getId());
//		if(wechatUserInfo==null){
//			getRequest().setAttribute("isAssociatedWeChat",false);
//		}else{
			getRequest().setAttribute("isAssociatedWeChat",true);
//		}
		
		return SUCCESS;
	}
	
	/**
	 * 确认付款
	 * @return
	 */
	public String confirmPayOrders(){
		Subscriber subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		
		OrdersDetail payOrdersDetail =null;
		if(getRequest().getSession().getAttribute("payOrdersDetail")!=null){
			payOrdersDetail = (OrdersDetail)getRequest().getSession().getAttribute("payOrdersDetail");
		}
		
		if(payOrdersDetail==null){
			retMsg="支付订单未找到";
			return "fail";
		}
		
		if("1".equals(payOrdersDetail.getIsPaid())){
			retMsg="订单已经支付，请不要重复提交！";
			return "fail";
		}
		
		String payType=	getRequest().getParameter("payType");
		if(StringUtils.isEmpty(payType)){
			retMsg="支付类型为空！";
			return "fail";
		}
		
		BigDecimal totalFee =new BigDecimal(0);
		List<OrdersDetail> orderList=new ArrayList<OrdersDetail>();
		totalFee=totalFee.add(payOrdersDetail.getTotalFee());
		orderList.add(payOrdersDetail);
		
		if(!StringUtils.isEmpty(payOrdersDetail.getNextOrders()) && payOrdersDetail.getIsPrePay().equals("0")){
			OrdersDetail ordersNextDetail=ordersDetailService.getOrdersDetailById(payOrdersDetail.getNextOrders());
			totalFee=totalFee.add(ordersNextDetail.getTotalFee());
			orderList.add(ordersNextDetail);
		}
		
		if(Account.PAY_TYPE_ACCOUNT.toString().equals(payType)){
			//先创建订单
			try {
				Account account=accountService.getAccoutDetail(subscriber.getId());
				if(totalFee.compareTo(new BigDecimal(account.getUsableAmount()))>0){
					retMsg="帐户余额不足，请选择其他支付方式！";
					return "fail";
				}
				orderPayService.orderPayForAccount(orderList, subscriber.getId(), Account.PAY_CHANNEL_WECHAT,OrderPayService.STEP_CRATE);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				retMsg="创建充值订单失败！";
				return "fail";
			}
			//扣款成功跳转
			return "account_pay_success";
		}else if(Account.PAY_TYPE_WECHAT.toString().equals(payType)){
			
			
				String openId=	getRequest().getParameter("openId");
		
				if(StringUtils.isEmpty(openId)){
					String orderPayReferrer="/rechargePrepareServlet?payType="+payType+"&url=/mobile/online/confirmPayOrders.action";
					WeixinUtil.requestOauth2Code(getResponse(), "snsapi_userinfo",orderPayReferrer);
					return null;
				}
				
				WechatUserInfo wechatUserInfo = wechatUserInfoService.getUserInfoBySubscriberId(subscriber.getId());
				if(wechatUserInfo!=null){
					if(!wechatUserInfo.getOpenId().equals(openId)){
						retMsg="支付账号与绑定账号不一致,请先解绑或者使用绑定账号支付";
						return "fail";
					}else{
						openId=wechatUserInfo.getOpenId();
					}
					
				}
		
	
				//先创建订单23
				AccountTradeRecord accountTradeRecord=null;
				try {
					accountTradeRecord=orderPayService.orderPayForOnline(orderList, subscriber.getId(), Account.PAY_CHANNEL_WECHAT, Account.PAY_TYPE_WECHAT);
				} catch (Exception e) {
					log.error(e);
					retMsg="创建充值订单失败！";
					return "fail";
				}
				
				//如无异常则跳转至银行付款页面

				String body=accountTradeRecord.getDescription();
				String orderNo=accountTradeRecord.getTradeOrderNo();
				
				try {
					WxPayUtil.orderPay(openId, body, orderNo, accountTradeRecord.getAmount(),accountTradeRecord.getTradeTime(),OrderPayService.STEP_CRATE,getRequest(), getResponse());
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e);
					retMsg="微信统一下单失败："+e.getMessage();
					return "fail";
				}
				
				return "wx_pay_success";
		
			
			
			
			
			
		}else{
			retMsg="未知的支付渠道！";
			return "fail";
		}
		
	}
	
	/**
	 * 跳转支付页面
	 * @return
	 */
	public String toPayOrderFinishFee(){
		Subscriber subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		 
		if(getSession().getAttribute(Constants.RETURN_BACK_CAR_PAY_ORDERS)!=null){
			ordersDetail = (OrdersDetail)getSession().getAttribute(Constants.RETURN_BACK_CAR_PAY_ORDERS);
		}
		if(ordersDetail==null){
			retMsg="支付订单为空";
			return "fail";
		}
		BigDecimal totalFee =new BigDecimal(0);
		totalFee=totalFee.add(ordersDetail.getTotalFee());
		if (ordersDetail.getTicketsFee() != null)
		totalFee=totalFee.add(ordersDetail.getTicketsFee());
		getRequest().setAttribute("totalFee", totalFee.doubleValue());
		
		Account account=accountService.getAccoutDetail(subscriber.getId());
		getRequest().setAttribute("VirtualAmount", account.getUsableAmount());
		
		boolean isEnoughMoney=true;
		if(ordersDetail.getTotalFee().compareTo(new BigDecimal(account.getUsableAmount()))>0){
			isEnoughMoney=false;
		}
		getRequest().setAttribute("isEnoughMoney",isEnoughMoney);
		
		WechatUserInfo wechatUserInfo=wechatUserInfoService.getUserInfoBySubscriberId(subscriber.getId());
		if(wechatUserInfo==null){
			getRequest().setAttribute("isAssociatedWeChat",false);
		}else{
			getRequest().setAttribute("isAssociatedWeChat",true);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 计算订单费用
	 * @return
	 */
	public String countingOrdersFee(){
		BigDecimal totalBigDecimal = BigDecimal.valueOf(0);
		OrdersDetail payOrdersDetail = new OrdersDetail();
		if(getRequest().getSession().getAttribute("payOrdersDetail")!=null){
			payOrdersDetail = (OrdersDetail)getSession().getAttribute(Constants.RETURN_BACK_CAR_PAY_ORDERS);
		}	
		
		if(payOrdersDetail != null){
			totalBigDecimal = totalBigDecimal.add(payOrdersDetail.getTotalFee());
			if(payOrdersDetail.getNextOrders()!=null && !"".equals(payOrdersDetail.getNextOrders())){
				OrdersDetail nextOrdersDetail = ordersDetailService.getOrdersDetailById(payOrdersDetail.getNextOrders());
				totalBigDecimal = totalBigDecimal.add(nextOrdersDetail.getTotalFee());
			}
		}
		
		DecimalFormat df=new DecimalFormat("0.00"); 
		result = Ajax.JSONResult(0, "", df.format(totalBigDecimal).toString());
		return SUCCESS;
	}
	
	public String toBookingFinish(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s = (Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return LOGIN;
		}
		orders = ordersService.queryCurrentOrder(s.getId());
		if(orders == null || !"1".equals(orders.getState())){
			return ERROR;
		}
		Car car = carService.queryCarById(orders.getCarId());
		CarVehicleModel model = carVehicleModelService.queryModelById(car.getModelId());
		carImg = model.getMicroWebModelPhoto();
		car.setCarVehicleModel(model);
		orders.setCar(car);
		choseCarBranchDot = branchDotService.getBranchDotByCarId(car.getId());
		ordersDetail = ordersDetailService.getOrdersDetailByNo(orders.getFirstDetailNo());
		return SUCCESS;
	}

	@SuppressWarnings("rawtypes")
	public String toBookCar() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s = (Subscriber) session
				.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请您先登录！");
			return LOGIN;
		}
		Orders currentOrders = ordersService.queryCurrentOrder(s.getId());
		if (currentOrders != null) {
			hasCurrentOrder = "true";
		}
		else{
			hasCurrentOrder = "false";
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
		log.info("appId:"+appId);
		log.info("jsapi_ticket:"+jsapi_ticket);
		log.info("nonceStr:"+nonceStr);
		log.info("timestamp:"+timestamp);
		log.info("signature:"+signature);
		log.info("url:"+url);
		
		return SUCCESS;
	}
	
	/**
	 * 先到欢迎页
	 * @return
	 */
	public String toWelcomePage() {
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String dotInfoSearch() {
		String searchDotName = getRequest().getParameter("searchDotName");
		List<BranchDot> xyBranchDots = branchDotService.searchBranchDot(1,searchDotName);
		if(xyBranchDots == null || xyBranchDots.size() <= 0){
			xyBranchDots = branchDotService.searchBranchDot(1);
		}
		if (xyBranchDots != null && xyBranchDots.size() > 0) {
			for (BranchDot branchDot : xyBranchDots) {
				branchDot.setCarCount(branchDotService.getCarCount(branchDot
						.getId()));
			}
		}
		List<BranchDot> ysBranchDots = branchDotService.searchBranchDot(0);
		List<ChargeStation> chargeStations = chargeStationService.searchChargeStationByCode(null);
		
		Map<String, List> resMap = new HashMap<String, List>();
		resMap.put("xydots", xyBranchDots);
		resMap.put("ysdots", ysBranchDots);
		resMap.put("chargeStations", chargeStations);
		if (StringHelper.isNotEmpty(dotId)) {
			List<BranchDot> dots = new ArrayList<BranchDot>();
			BranchDot bd = branchDotService.getBranchDotById(dotId);
			bd.setCarCount(branchDotService.getCarCount(dotId));
			dots.add(bd);
			resMap.put("dots", dots);
		}

		result = Ajax.JSONResult(0, "", resMap);
		return SUCCESS;
	}

	public String getDotDetailInfo() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s = (Subscriber) session
				.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请您先登录！");
			return LOGIN;
		}
		List<BookCarInfoEntity> entitys = branchDotService.getUnbookCars(dotId);
		if(entitys != null && entitys.size() > 0){
			for (BookCarInfoEntity entity : entitys) {
				strategyCarShowInfos = strategyBaseService.getShowInfo(entity.getCar().getModelId());
				entity.setStrategyCarShowInfos(strategyCarShowInfos);
			}
		}
		result = Ajax.JSONResult(0, "", entitys);
		return SUCCESS;
	}
	
	public String toShowDotCarList(){
		choseCarBranchDot = branchDotService.getBranchDotById(dotId);
		return SUCCESS;
	}
	
	@SuppressWarnings("rawtypes")
	public String toShowReturnDot() {
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
	
	public String getReturnDot(){
		choseCarBranchDot = branchDotService.getBranchDotById(dotId);
		List<BranchDot> returnBranchDots = new ArrayList<BranchDot>();
		if(choseCarBranchDot != null){
//			Config config = configService.getConfigByCode("dotReturnBackType");
//			if(config == null){
//				result = Ajax.JSONResult(1, "找不到可还车网点，请联系客服");
//				return SUCCESS;
//			}
//			if(config.getValue().equals("0")){
//				String dotHql = "from BranchDot where isActive = 1";
//				returnBranchDots = branchDotService.find(dotHql);
//				
//			}
//			returnBranchDots.add(choseCarBranchDot);
			String returnDotIdStr = choseCarBranchDot.getReturnbackDot();
			if(StringHelper.isEmpty(returnDotIdStr)){
				returnBranchDots.add(choseCarBranchDot);
				result = Ajax.JSONResult(0, "", returnBranchDots);
				return SUCCESS;
			}
			returnDotIdStr = returnDotIdStr.substring(0, returnDotIdStr.length() - 1);
			StringBuffer str = new StringBuffer();
			for (String rdid : returnDotIdStr.split(",")) {
				str.append("'").append(rdid).append("',");
			}
			returnBranchDots = branchDotService.searchDotsIn(str.deleteCharAt(str.length() - 1).toString());
			returnBranchDots.add(choseCarBranchDot);
			for (BranchDot branchDot : returnBranchDots) {
				List<CarDotBinding> carDotBindings = carDotBindingService.searchBindingByDotId(branchDot.getId(), 1);
				branchDot.setRemainderParkingPlace(branchDot.getTotalParkingPlace() - carDotBindings.size());
			}
		}
		result = Ajax.JSONResult(0, "", returnBranchDots);
		return SUCCESS;
	}

	public String toBookingInfoCheck() {
		if (StringHelper.isEmpty(carId)) {
			result = Ajax.JSONResult(1, "非常抱歉，该车辆已经被预定，请您选择其他车辆！");
			return SUCCESS;
		}
		Car c = carService.queryCarById(carId);
		Dict d = DictUtil.getDictByCodes("carBizState", "0");
		if (!c.getBizState().equals(d.getId())) {
			result = Ajax.JSONResult(1, "非常抱歉，该车辆已经被预定，请您选择其他车辆！");
			return SUCCESS;
		}

		result = Ajax.JSONResult(0, "");
		return SUCCESS;
	}

	public String toBookingInfo() {
		queryCarInfo();
		if( carInfo == null){
			return ERROR;
		}
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s = (Subscriber) session
				.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请您先登录！");
			return LOGIN;
		}
		
		List<BookCarInfoEntity> entitys = branchDotService.getUnbookCars(dotId);
		if(entitys != null && entitys.size() > 0){
			for (BookCarInfoEntity entity : entitys) {
				strategyCarShowInfos = strategyBaseService.getShowInfo(entity.getCar().getModelId());
				entity.setStrategyCarShowInfos(strategyCarShowInfos);
			}
		}
		
		Car car = carService.queryCarById(carId);
		CarVehicleModel carModel = car.getCarVehicleModel();//得到该车的车型
		shizuStrategy = strategyBaseService.searchStrategyBaseById(carModel.getShizuStrategy());
		rizuStrategy = strategyBaseService.searchStrategyBaseById(carModel.getRizuStrategy());
		return SUCCESS;
	}
	
	public String toBookingInfoTwo(){
		queryCarInfo();
		if (carInfo == null) {
			return ERROR;
		}
		choseCarBranchDot = branchDotService.getBranchDotByCarId(carId);
		ordersBackSite = branchDotService.getBranchDotById(ordersBackSiteId);
		return SUCCESS;
	}
	
	private void queryCarInfo(){
		carInfo = branchDotService.getCarInfo(carId);
		Car car = carService.queryCarById(carId);
		if(car != null){
			CarVehicleModel model = carVehicleModelService.queryModelById(car.getModelId());
			if(model != null){
				carInfo.setCarImg(model.getMicroWebModelPhoto());
			}
		}
	}

	public String toShowCarFee() {
		choseCarBranchDot = branchDotService.getBranchDotByCarId(carId);
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s = (Subscriber) session
				.getAttribute(Constants.SESSION_SUBSCRIBER);
		return SUCCESS;
	}

	// 费用估计
	public String showFee() {
		String startTimeStr = getRequest().getParameter("startTime");
		String backTimeStr = getRequest().getParameter("returnBackTime");
		String strategyRelationId = getRequest().getParameter("strategyRelationId");
		if(StringHelper.isEmpty(strategyRelationId)){
			result = Ajax.JSONResult(1, "请选择策略");
			return SUCCESS;
		}
		Date startTime;
		Date backTime;
		if(StringHelper.isEmpty(startTimeStr)){
			startTime = new Date();
		}
		else{
			try {
				startTime = DateUtil.parseDate(startTimeStr, "yyyy-MM-dd HH:mm");
			} catch (ParseException e) {
				result = Ajax.JSONResult(1, "取车时间错误");
				e.printStackTrace();
				return SUCCESS;
			}
		}
		if(StringHelper.isEmpty(backTimeStr)){
			result = Ajax.JSONResult(1, "请输入还车时间");
			return SUCCESS;
		}
		else{
			try {
				backTime = DateUtil.parseDate(backTimeStr, "yyyy-MM-dd HH:mm");
			} catch (ParseException e) {
				result = Ajax.JSONResult(1, "预估还车时间错误");
				e.printStackTrace();
				return SUCCESS;
			}
		}
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s = (Subscriber) session
				.getAttribute(Constants.SESSION_SUBSCRIBER);
		result = Ajax.JSONResult(0, "",0d);
		return SUCCESS;
	}

	/**
	 * 扫描二维码
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String scanQRCode() {
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
	
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}

	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}

	public String getDotId() {
		return dotId;
	}

	public void setDotId(String dotId) {
		this.dotId = dotId;
	}

	public CarService getCarService() {
		return carService;
	}

	public void setCarService(CarService carService) {
		this.carService = carService;
	}

	public BookCarInfoEntity getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(BookCarInfoEntity carInfo) {
		this.carInfo = carInfo;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public OrdersBill getOrdersBill() {
		return ordersBill;
	}

	public void setOrdersBill(OrdersBill ordersBill) {
		this.ordersBill = ordersBill;
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

	public String getGetCarTime() {
		return getCarTime;
	}

	public void setGetCarTime(String getCarTime) {
		this.getCarTime = getCarTime;
	}

	public String getBackCarTime() {
		return backCarTime;
	}

	public void setBackCarTime(String backCarTime) {
		this.backCarTime = backCarTime;
	}

	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}

	public BranchDot getChoseCarBranchDot() {
		return choseCarBranchDot;
	}

	public void setChoseCarBranchDot(BranchDot choseCarBranchDot) {
		this.choseCarBranchDot = choseCarBranchDot;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
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
	public String getOrdersBackSiteId() {
		return ordersBackSiteId;
	}
	public void setOrdersBackSiteId(String ordersBackSiteId) {
		this.ordersBackSiteId = ordersBackSiteId;
	}
	public String getBookFee() {
		return bookFee;
	}
	public void setBookFee(String bookFee) {
		this.bookFee = bookFee;
	}
	public BranchDot getOrdersBackSite() {
		return ordersBackSite;
	}
	public void setOrdersBackSite(BranchDot ordersBackSite) {
		this.ordersBackSite = ordersBackSite;
	}
	public String getCarImg() {
		return carImg;
	}
	public void setCarImg(String carImg) {
		this.carImg = carImg;
	}
	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}
	public void setCarVehicleModelService(
			CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}

	public String getShowLoading() {
		return showLoading;
	}

	public void setShowLoading(String showLoading) {
		this.showLoading = showLoading;
	}

	public String getHasCurrentOrder() {
		return hasCurrentOrder;
	}

	public void setHasCurrentOrder(String hasCurrentOrder) {
		this.hasCurrentOrder = hasCurrentOrder;
	}
	public Integer getRemainTime() {
		return remainTime;
	}
	public void setRemainTime(Integer remainTime) {
		this.remainTime = remainTime;
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

	public String getOrdersDetailNo() {
		return ordersDetailNo;
	}

	public void setOrdersDetailNo(String ordersDetailNo) {
		this.ordersDetailNo = ordersDetailNo;
	}
	
	public List<String> getTypeNameList() {
		return typeNameList;
	}

	public void setTypeNameList(List<String> typeNameList) {
		this.typeNameList = typeNameList;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public List<StrategyCarShowInfo> getStrategyCarShowInfos() {
		return strategyCarShowInfos;
	}

	public void setStrategyCarShowInfos(List<StrategyCarShowInfo> strategyCarShowInfos) {
		this.strategyCarShowInfos = strategyCarShowInfos;
	}

	public Integer getUnitCount() {
		return unitCount;
	}

	public void setUnitCount(Integer unitCount) {
		this.unitCount = unitCount;
	}

	public CarDotBindingService getCarDotBindingService() {
		return carDotBindingService;
	}
	public void setCarDotBindingService(
			CarDotBindingService carDotBindingService) {
		this.carDotBindingService = carDotBindingService;
	}

	public String confirmPayOrdersFinish(){

		Subscriber subscriber=(Subscriber)getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		
		OrdersDetail payOrdersDetail =null;
		if(getSession().getAttribute(Constants.RETURN_BACK_CAR_PAY_ORDERS)!=null){
			payOrdersDetail = (OrdersDetail)getSession().getAttribute(Constants.RETURN_BACK_CAR_PAY_ORDERS);
		}
		
		if(payOrdersDetail==null){
			retMsg="支付订单为空";
			return "fail";
		}
		
		String payType=	getRequest().getParameter("payType");
		if(StringUtils.isEmpty(payType)){
			retMsg="支付类型为空";
			return "fail";
		}
		
		
		List<OrdersDetail> orderList=new ArrayList<OrdersDetail>();
		orderList.add(payOrdersDetail);
	
				
		if(Account.PAY_TYPE_ACCOUNT.toString().equals(payType)){
			//先创建订单
			try {
				subscriber=subscriberService.querySubscriberAllInfoById(subscriber.getId());
				BigDecimal orderFee =payOrdersDetail.getTotalFee();
				orderFee=orderFee.add(payOrdersDetail.getTicketsFee() == null ? new BigDecimal(0) : payOrdersDetail.getTicketsFee());
				if(orderFee.compareTo(new BigDecimal(subscriber.getAccount().getUsableAmount()))>0){
					retMsg="帐户余额不足，请选择其他支付方式";
					return "fail";
				}
				
				orderPayService.orderPayForAccount(orderList, subscriber.getId(), Account.PAY_CHANNEL_WECHAT,OrderPayService.STEP_FINISH);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				retMsg="订单支付创建失败";
				return "fail";
			}
			//扣款成功跳转
			return "account_pay_success";
			
		}else if(Account.PAY_TYPE_WECHAT.toString().equals(payType)){
			

				String openId=	getRequest().getParameter("openId");
			

				if(StringUtils.isEmpty(openId)){
					String orderPayReferrer="/rechargePrepareServlet?payType="+payType+"&url=/mobile/online/confirmPayOrdersFinish.action";
					WeixinUtil.requestOauth2Code(getResponse(), "snsapi_userinfo",orderPayReferrer);
					return null;
				}
				
				
				WechatUserInfo wechatUserInfo = wechatUserInfoService.getUserInfoBySubscriberId(subscriber.getId());
				if(wechatUserInfo!=null){
					if(!wechatUserInfo.getOpenId().equals(openId)){
						retMsg="支付账号与绑定账号不一致,请先解绑或者使用绑定账号支付";
						return "fail";
					}else{
						openId=wechatUserInfo.getOpenId();
					}
					
				}
		
			
				//先创建订单23
				AccountTradeRecord accountTradeRecord=null;
				try {
					accountTradeRecord=orderPayService.orderPayForOnline(orderList, subscriber.getId(), Account.PAY_CHANNEL_WECHAT, Account.PAY_TYPE_WECHAT);
				} catch (Exception e) {
					log.error(e);
					retMsg="创建充值订单失败";
					return "fail";
				}
				
				
				
				
				String body=accountTradeRecord.getDescription();
				String orderNo=accountTradeRecord.getTradeOrderNo();
				
				try {
					WxPayUtil.orderPay(openId, body, orderNo, accountTradeRecord.getAmount(),accountTradeRecord.getTradeTime(),OrderPayService.STEP_FINISH,getRequest(), getResponse());
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e);
					retMsg="微信统一下单失败："+e.getMessage();
					return "fail";
				}
				
				return "wx_pay_success";

			
			
			
		}else{
			retMsg="未知的支付渠道!";
			return "fail";
		}
		
		
		

	}
	public OrdersDetail getOrdersDetail() {
		return ordersDetail;
	}

	public void setOrdersDetail(OrdersDetail ordersDetail) {
		this.ordersDetail = ordersDetail;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
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

	public String getStrategyBaseId() {
		return strategyBaseId;
	}

	public void setStrategyBaseId(String strategyBaseId) {
		this.strategyBaseId = strategyBaseId;
	}

	public ChargeStationService getChargeStationService() {
		return chargeStationService;
	}

	public void setChargeStationService(ChargeStationService chargeStationService) {
		this.chargeStationService = chargeStationService;
	}

	public StrategyBase getShizuStrategy() {
		return shizuStrategy;
	}

	public void setShizuStrategy(StrategyBase shizuStrategy) {
		this.shizuStrategy = shizuStrategy;
	}

	public StrategyBase getRizuStrategy() {
		return rizuStrategy;
	}

	public void setRizuStrategy(StrategyBase rizuStrategy) {
		this.rizuStrategy = rizuStrategy;
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
	
	
}
