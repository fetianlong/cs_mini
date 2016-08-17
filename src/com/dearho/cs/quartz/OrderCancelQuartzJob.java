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
import com.dearho.cs.feestrategy.pojo.StrategyBase;
import com.dearho.cs.feestrategy.service.StrategyBaseService;
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
 * 即时租订单，超时20分钟取消
 * @author Carsharing05
 *
 */
public class OrderCancelQuartzJob {
	private static  Log log = LogFactory.getLog(OrderCancelQuartzJob.class);
	
	private OrdersService ordersService;
	private OrdersDetailService ordersDetailService;
	private AccountService accountService;
	private SubscriberService subscriberService;
	private CarService carService;
	private StrategyBaseService strategyBaseService;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Date logTime;
	
	public void execute(){
		
//		
//		logTime=new Date();
//	    log.debug(sdf.format(logTime)+"  订单超时 自动取消 任务调度start");
//	    
//		Date date=new Date();
//		Calendar calendar=Calendar.getInstance();
//		calendar.setTime(date);
//		calendar.add(Calendar.MINUTE, -20);
//		
//		//后付费的超时20分钟取消订单
//		String hql="select a.id from OrdersDetail a where a.isRunning='0' and a.preOrders is null and a.isPrePay='0' and a.isPaid='0' and a.isOver='0' and a.isException='0' and a.createTime <= '"+sdf.format(calendar.getTime())+"'";
//		List<OrdersDetail> list = ordersDetailService.queryOrdersDetail(hql);
//		if(list != null && list.size() > 0){
//			for (int i = 0; i < list.size(); i++) {
//				OrdersDetail curOrdersDetail = list.get(i);
//				//得到主订单
//				Orders orders = ordersService.queryOrdersByOrderNo(curOrdersDetail.getOrdersNo());
//				
//				Car car = carService.queryCarById(orders.getCarId());
//				//更新订单状态
//				orders.setState("2");
//				orders.setTotalFee(new BigDecimal(0));
//				orders.setEndTime(new Date());
//				ordersService.updateOrders(orders);
//				
//				//更新车辆状态
//				//看看是否是预下线
//				Dict yxxDict = DictUtil.getDictByCodes("carBizState", "6");
//				if(yxxDict.getId().equals(car.getBizState())){
//					//如果是预下线，改成下线状态
//					Dict ydDict = DictUtil.getDictByCodes("carBizState", "3");
//					car.setBizState(ydDict.getId());
//				}
//				else{
//					Dict ydDict = DictUtil.getDictByCodes("carBizState", "0");
//					car.setBizState(ydDict.getId());
//				}
//				carService.updateCar(car);
//				Subscriber subscriber=subscriberService.querySubscriberById(orders.getMemberId());
//				//扣除最低消费
//				try {
//					StrategyBase sBase = strategyBaseService.searchStrategyBaseById(curOrdersDetail.getStrategyId());
//					orders.setTotalFee(sBase.getMinConsumption());
//					curOrdersDetail.setTotalFee(sBase.getMinConsumption());
//					curOrdersDetail.setIsOver("1");
//					ordersDetailService.updateOrdersDetail(curOrdersDetail);
//					ordersService.updateOrders(orders);
//					
//					//扣除最低消费
////					accountService.orderPayment(orders,0d,0);
//					String str = "尊敬的" + subscriber.getName() + "，您的订单因超时未取车，系统自动取消并扣除" 
//							+ sBase.getMinConsumption() + "元,请您合理安排您的用车计划。【乐途出行】";
//					
//					SMSUtil.sendSMS(subscriber.getPhoneNo(), str,SMSRecord.TYPE_ORDER);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
//			}
//		}
//	
//		 log.debug(sdf.format(logTime)+"  订单超时 自动取消 任务调度end");
//	  
	
	
	}

	public StrategyBaseService getStrategyBaseService() {
		return strategyBaseService;
	}

	public void setStrategyBaseService(StrategyBaseService strategyBaseService) {
		this.strategyBaseService = strategyBaseService;
	}

	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
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
	 
	public OrdersDetailService getOrdersDetailService() {
		return ordersDetailService;
	}

	public void setOrdersDetailService(OrdersDetailService ordersDetailService) {
		this.ordersDetailService = ordersDetailService;
	} 
	 
}
