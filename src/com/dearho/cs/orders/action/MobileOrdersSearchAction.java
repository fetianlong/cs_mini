package com.dearho.cs.orders.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.pojo.CarVehicleModel;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.feestrategy.pojo.StrategyBase;
import com.dearho.cs.feestrategy.service.StrategyBaseService;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersBill;
import com.dearho.cs.orders.pojo.OrdersDetail;
import com.dearho.cs.orders.pojo.Tickets;
import com.dearho.cs.orders.service.OrdersBillService;
import com.dearho.cs.orders.service.OrdersDetailService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.orders.service.TicketsService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.webwork.ServletActionContext;

/**
 * 
 * 微网站用户订单查询
 * @author GaoYunpeng
 *
 */
public class MobileOrdersSearchAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private OrdersService ordersService;
	
	private SubscriberService subscriberService;
	
	private TicketsService ticketsService;
	
	private OrdersDetailService ordersDetailService;
	
	private OrdersBillService ordersBillService;
	
	private StrategyBaseService strategyBaseService;
	
	private StrategyBase currentStrategyBase; 

	private CarService carService;
	
	private String orderState;
	private String begintime;
	private String endtime;
	private String searchType;
	private String lasttimetype;
	private Integer currentNum;
	
	private String lastOrderTime;
	private String type;
	
	private Orders order;
	
	private List<Orders> ordersList = new ArrayList<Orders>();
	
	private String orderNos;
	
	private BigDecimal totalFee;
	
	private Subscriber subscriber;
	
	private Dict currentType;
	
	public MobileOrdersSearchAction() {
		super();
	}

	@Override
	public String process() {
		return SUCCESS;
	}
	
	public String toOrderList(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return ERROR;
		}
		lasttimetype = "1";
		orderState = "0";
		return SUCCESS;
	}
	
	/**
	 * 索取发票，选择订单列表页面
	 * @return
	 */
	public String needInvoice(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return ERROR;
		}
		
		if(StringUtils.isEmpty(s.getPostAddress()) || StringUtils.isEmpty(s.getBillPostCode()) || StringUtils.isEmpty(s.getBillTitle())){
			//请您先完善个人信息中的发票信息！
			result = Ajax.JSONResult(2, "");
		}else{
			result = Ajax.JSONResult(1, "");
		}
		lasttimetype = "1";
		orderState = "0";
		return SUCCESS;
	}
	
	public String chooseNeedBillOrders(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return ERROR;
		}
		List<Orders> list = ordersService.getNoOpenBillOrders(s.getId());
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
							c.setCarVehicleModel(cvm);
							orders.setCar(c);
							ordersList.add(orders);
						}
					}
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 确认索取发票
	 * @return
	 */
	public String confirmNeedInvoice(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		if( session.getAttribute(Constants.SESSION_SUBSCRIBER) == null){
			return ERROR;
		}
		subscriber=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		
		String[] ordersNoStrings = orderNos.split(",");
		if(ordersNoStrings.length != 0){
			for(int i=0;i<ordersNoStrings.length;i++){
				String no = ordersNoStrings[i];
				if(!StringUtils.isEmpty(no)){
					Orders orders = ordersService.queryOrdersByOrderNo(no);
					orders.setIsOpenBill(1);
					ordersService.updateOrders(orders);
				}else{
					result = Ajax.JSONResult(1,"请您选择代开发票的订单！");
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
		return SUCCESS;
	}
	
	public String searchOrders(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			result = Ajax.toJson(1, "请您先登录");
			return SUCCESS;
		}
		List<Orders> orders = ordersService.queryOrdersList(type,lastOrderTime,currentNum,begintime,endtime,searchType,lasttimetype,orderState,s);
		if(StringHelper.isEmpty(searchType)){
			searchType = "rangetime";
			lasttimetype = "1";
			orderState = "0";
		}
		else if("rangetime".equals(searchType)){
			lasttimetype = "0";
		}
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
							c.setCarVehicleModel(cvm);
							o.setCar(c);
						}
					}
				}
				if(o.getState() != null){
					o.setStateName(DictUtil.getCnNameByCode("14",o.getState()));
				}
				o.setBeginTimeStr(o.getBeginTime() == null ? "" : transDate12String(o.getBeginTime()));
				o.setEndTimeStr(o.getEndTime() == null ? "" : transDate12String(o.getEndTime()));
			}
		}
		result = Ajax.JSONResult(0, "", orders);
		return SUCCESS;
	}
	
	/**
	 * 查询订单详情
	 * @return
	 */
	public String toOrdersDetail() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		Subscriber s=(Subscriber) session.getAttribute(Constants.SESSION_SUBSCRIBER);
		if(s == null){
			return ERROR;
		}
		String orderId = (String) getRequest().getParameter("orderId");
		order = ordersService.queryOrdersById(orderId);
		if(order == null || !s.getId().equals(order.getMemberId())){
			return ERROR;
		}
		if(!StringHelper.isRealNull(order.getMemberId())){
			Subscriber subscriber=getMemberName(order.getMemberId());
			order.setMemberName(subscriber.getName());
			order.setMemberPhoneNo(subscriber.getPhoneNo());
		}
		if(!StringHelper.isRealNull(order.getCarId())){
			Car c = carService.queryCarById(order.getCarId());
			if(c != null){
				order.setPlateNumber(c.getVehiclePlateId());
				CarVehicleModel cvm = c.getCarVehicleModel();
				if(cvm != null){
					Dict d = DictUtil.getDictById(cvm.getBrand());
					order.setVehicleModelName(d.getCnName()+" "+cvm.getName());
					c.setCarVehicleModel(cvm);
					order.setCar(c);
				}
			}
		}
		
		if(order.getState() != null){
			order.setStateName(DictUtil.getCnNameByCode("14",order.getState()));
		}
		
		if(order.getPayStyle() != null && order.getPayStyle().matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")){
			order.setPayStyleName(Account.getPayType(Integer.parseInt(order.getPayStyle())));
		}
		
		if(order.getTotalFee()==null){
			order.setTotalFee(new BigDecimal(0));
		}
		
		List<OrdersDetail> detailList = ordersDetailService.getOrdersDetailsByNo(order.getOrdersNo());
		if(detailList!=null && detailList.size()!=0){
			for(int i=0;i<detailList.size();i++){
				OrdersDetail ordersDetail = detailList.get(i);
				BigDecimal mileFee = strategyBaseService.conMileFee(ordersDetail.getStrategyId(),ordersDetail.getBeginTime(),new Date(),order.getCarId());
				BigDecimal mile = strategyBaseService.conMile(ordersDetail.getStrategyId(),ordersDetail.getBeginTime(),new Date(),order.getCarId());
				BigDecimal timeFee = strategyBaseService.conTimeFee(ordersDetail.getStrategyId(),ordersDetail.getBeginTime(),new Date(),order.getCarId());
				
				ordersDetail.setMileFee(mileFee);
				ordersDetail.setMileage(mile);
				ordersDetail.setTimeFee(timeFee);
			}
		}
		
		order.setOrdersDetail(detailList);
		
		OrdersDetail currentOrdersDetail = ordersDetailService.getRunningOrdersDetail(order.getOrdersNo());
		if(currentOrdersDetail!=null){
			currentStrategyBase = strategyBaseService.searchStrategyBaseById(currentOrdersDetail.getStrategyId());
			currentType = DictUtil.getDictById(currentStrategyBase.getType());
		}
		return SUCCESS;
	}
	
	public Subscriber getMemberName(String id){
		Subscriber subscriber=subscriberService.querySubscriberById(id);
		return subscriber;
	}

	public String searchOrdersDetailTickets(String ordersDetailNo){
		Tickets tickets = ticketsService.getTicketByOrdersDetailNo(ordersDetailNo);//得到该子订单的罚单信息
		result = Ajax.JSONResult(0, "", tickets);
		return SUCCESS;
	}
	
	public CarService getCarService() {
		return carService;
	}
	public void setCarService(CarService carService) {
		this.carService = carService;
	}
	
	public String getOrderState() {
		return orderState;
	}
	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}


	public String getBegintime() {
		return begintime;
	}


	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}


	public String getSearchType() {
		return searchType;
	}


	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}


	public String getLasttimetype() {
		return lasttimetype;
	}


	public void setLasttimetype(String lasttimetype) {
		this.lasttimetype = lasttimetype;
	}

	public SubscriberService getSubscriberService() {
		return subscriberService;
	}


	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}


	public OrdersService getOrdersService() {
		return ordersService;
	}


	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}


	public String getEndtime() {
		return endtime;
	}


	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}


	public Integer getCurrentNum() {
		return currentNum;
	}


	public void setCurrentNum(Integer currentNum) {
		this.currentNum = currentNum;
	}
	public String getLastOrderTime() {
		return lastOrderTime;
	}
	public void setLastOrderTime(String lastOrderTime) {
		this.lastOrderTime = lastOrderTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}

	public OrdersDetailService getOrdersDetailService() {
		return ordersDetailService;
	}

	public void setOrdersDetailService(OrdersDetailService ordersDetailService) {
		this.ordersDetailService = ordersDetailService;
	}

	public TicketsService getTicketsService() {
		return ticketsService;
	}

	public void setTicketsService(TicketsService ticketsService) {
		this.ticketsService = ticketsService;
	}
	
	public OrdersBillService getOrdersBillService() {
		return ordersBillService;
	}

	public void setOrdersBillService(OrdersBillService ordersBillService) {
		this.ordersBillService = ordersBillService;
	}

	public List<Orders> getOrdersList() {
		return ordersList;
	}

	public void setOrdersList(List<Orders> ordersList) {
		this.ordersList = ordersList;
	}

	public String getOrderNos() {
		return orderNos;
	}

	public void setOrderNos(String orderNos) {
		this.orderNos = orderNos;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public Subscriber getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	public Dict getCurrentType() {
		return currentType;
	}

	public void setCurrentType(Dict currentType) {
		this.currentType = currentType;
	}

	public StrategyBaseService getStrategyBaseService() {
		return strategyBaseService;
	}

	public void setStrategyBaseService(StrategyBaseService strategyBaseService) {
		this.strategyBaseService = strategyBaseService;
	}
	
	public StrategyBase getCurrentStrategyBase() {
		return currentStrategyBase;
	}

	public void setCurrentStrategyBase(StrategyBase currentStrategyBase) {
		this.currentStrategyBase = currentStrategyBase;
	}
}
