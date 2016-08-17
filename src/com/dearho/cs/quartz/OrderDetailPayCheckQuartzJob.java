package com.dearho.cs.quartz;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersDetail;
import com.dearho.cs.orders.service.OrdersDetailService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SMSUtil;

/**
 * 子订单支付检查
 * @author wangjing
 *
 */
public class OrderDetailPayCheckQuartzJob {

	private static  Log log = LogFactory.getLog(OrderDetailPayCheckQuartzJob.class);
	private OrdersService ordersService;
	private OrdersDetailService ordersDetailService;
	private AccountService accountService;
	private SubscriberService subscriberService;
	private CarService carService;
	
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Date logTime;
	
	public void execute(){
		logTime=new Date();
	    log.debug(sdf.format(logTime)+" 扫描支付异常的订单，超时标记异常  start");
	    
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, -10);

		//所有创建10分钟后未支付的预付费订单
		String hql1="select a.id from OrdersDetail a where a.isPrePay='1' and a.isPaid='0' and a.isException='0' and a.createTime <= '"+sdf.format(calendar.getTime())+"'";
		List<OrdersDetail> listBackPayFirstOrder = ordersDetailService.queryOrdersDetail(hql1);
		if(listBackPayFirstOrder != null){
			for (int i = 0; i < listBackPayFirstOrder.size(); i++) {
				OrdersDetail curOrdersDetail = listBackPayFirstOrder.get(i);
				Orders orders = ordersService.queryOrdersByOrderNo(curOrdersDetail.getOrdersNo());
				String msgInfostr= "";
				Subscriber subscriber=subscriberService.querySubscriberById(orders.getMemberId());
				curOrdersDetail.setIsException("1");
				curOrdersDetail.setNextOrders("");
				try {
					//结束主订单
					orders.setState("0");
					orders.setEndTime(date);
					//更新车辆状态
					Car car = carService.queryCarById(orders.getCarId());
					//看看是否是预下线
					Dict yxxDict = DictUtil.getDictByCodes("carBizState", "6");
					if(yxxDict.getId().equals(car.getBizState())){
						//如果是预下线，改成下线状态
						Dict ydDict = DictUtil.getDictByCodes("carBizState", "3");
						car.setBizState(ydDict.getId());
					}
					else{
						Dict ydDict = DictUtil.getDictByCodes("carBizState", "0");
						car.setBizState(ydDict.getId());
					}
					carService.updateCar(car);
					ordersService.updateOrders(orders);
					ordersDetailService.updateOrdersDetail(curOrdersDetail);
					msgInfostr="尊敬的"+subscriber.getName()+"，您的租车订单("+curOrdersDetail.getOrdersNo()+")支付超时，已经取消，请重新下单。【乐途出行】";
					SMSUtil.sendSMS(subscriber.getPhoneNo(), msgInfostr,SMSRecord.TYPE_ORDER);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		log.debug(sdf.format(logTime)+"  扫描支付异常的订单，超时标记异常   end");
	}

	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public OrdersDetailService getOrdersDetailService() {
		return ordersDetailService;
	}

	public void setOrdersDetailService(OrdersDetailService ordersDetailService) {
		this.ordersDetailService = ordersDetailService;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
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
	
}
