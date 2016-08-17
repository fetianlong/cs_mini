package com.dearho.cs.quartz;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dearho.cs.account.service.AccountService;
import com.dearho.cs.car.service.CarService;
import com.dearho.cs.feestrategy.pojo.StrategyBase;
import com.dearho.cs.feestrategy.service.StrategyBaseService;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersDetail;
import com.dearho.cs.orders.pojo.Tickets;
import com.dearho.cs.orders.service.OrdersDetailService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.orders.service.TicketsService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SMSUtil;

/**
 * 订单超时 生成超时订单  定时任务
 * @author wangjing
 *
 */
public class OrderTimeOutQuartzJob {
	private static  Log log = LogFactory.getLog(OrderTimeOutQuartzJob.class);
	private OrdersService ordersService;
	private OrdersDetailService ordersDetailService;
	private TicketsService ticketsService;
	private AccountService accountService;
	private SubscriberService subscriberService;
	private CarService carService;
	private StrategyBaseService strategyBaseService;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Date logTime;
	
	public void execute(){
		logTime=new Date();
	    log.debug(sdf.format(logTime)+" 执行新订单以及扫描订单是否超时  start");
	    
		Date date=new Date();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		
		//所有到开始时间还没开启的首次预付费订单(已支付的)，自动开启该订单
		String hql1="select a.id from OrdersDetail a where  a.isRunning='0' and a.isPrePay='1' and a.isPaid='1' and a.isOver='0' and a.isException='0' and a.ordersDetailNo like'%001' and a.beginTime <= '"+sdf.format(calendar.getTime())+"'";
//		String hql1="select a.id from OrdersDetail a where isRunning is null and a.isPrePay='1' and a.isOver is null and a.ordersDetailNo like'%001' and a.beginTime <= '"+sdf.format(calendar.getTime())+"'";
		List<OrdersDetail> listBackPayFirstOrder = ordersDetailService.queryOrdersDetail(hql1);
		if(listBackPayFirstOrder != null){
			for (int i = 0; i < listBackPayFirstOrder.size(); i++) {
				OrdersDetail curOrdersDetail = listBackPayFirstOrder.get(i);
				//得到主订单
				Orders orders = ordersService.queryOrdersByOrderNo(curOrdersDetail.getOrdersNo());
				Subscriber subscriber=subscriberService.querySubscriberById(orders.getMemberId());
				
				orders.setBeginTime(curOrdersDetail.getBeginTime());
				orders.setState("3");
				orders.setRunningDetailNo(curOrdersDetail.getOrdersDetailNo());
				ordersService.updateOrders(orders);
				
				curOrdersDetail.setIsRunning("1");
				ordersDetailService.updateOrdersDetail(curOrdersDetail);
				
				String msgInfostr="尊敬的"+subscriber.getName()+"，您的("+curOrdersDetail.getTypeName()+")订单'"
						+curOrdersDetail.getOrdersNo()+"'已经开始计费，请您尽快用车。【乐途出行】";
				try {
					SMSUtil.sendSMS(subscriber.getPhoneNo(), msgInfostr,SMSRecord.TYPE_ORDER);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}		
		
		//所有进行中的预付费订单马上结束，执行续租订单，没有续租按超时处理
		String hql2="select a.id from OrdersDetail a where isRunning='1' and a.isPrePay='1' and a.endTime <= '"+sdf.format(calendar.getTime())+"'";
		List<OrdersDetail> listPrePay = ordersDetailService.queryOrdersDetail(hql2);
		if(listPrePay!=null&&listPrePay.size()>0){
			for (int i = 0; i < listPrePay.size(); i++) {
				OrdersDetail curOrdersDetail=listPrePay.get(i);
				//得到主订单
				Orders orders = ordersService.queryOrdersByOrderNo(curOrdersDetail.getOrdersNo());
				Subscriber subscriber = subscriberService.querySubscriberById(orders.getMemberId());
				String msgInfostr="尊敬的"+subscriber.getName()+"，您的订单("+orders.getOrdersNo()+")已经超时，系统将按时租计费，请尽快还车。【乐途出行】";
				
				StrategyBase currentStrategy = strategyBaseService.searchStrategyBaseById(curOrdersDetail.getStrategyId());//当前订单对应的策略
				StrategyBase timeOutStrategy = strategyBaseService.searchStrategyBaseById(curOrdersDetail.getTimeoutStrategyid());//当前订单对应的超时策略
				Dict d = DictUtil.getDictById(timeOutStrategy.getType());
				
				DecimalFormat df = new DecimalFormat("000");
				int count = ordersDetailService.getAllOrdersDetailsByNo(orders.getOrdersNo()).size();
				
				//新增一个超时子订单
				OrdersDetail timeoutOrdersDetail = new OrdersDetail();
				timeoutOrdersDetail.setOrdersNo(orders.getOrdersNo());
				timeoutOrdersDetail.setCreateTime(curOrdersDetail.getEndTime());
				timeoutOrdersDetail.setBeginTime(curOrdersDetail.getEndTime());
				timeoutOrdersDetail.setIsRunning("1");
				timeoutOrdersDetail.setOrdersDetailNo(orders.getOrdersNo()+df.format(count+1));
				timeoutOrdersDetail.setPreOrders(curOrdersDetail.getId());
				timeoutOrdersDetail.setStrategyTitle(timeOutStrategy.getName());
				timeoutOrdersDetail.setIsPrePay(timeOutStrategy.getIsPrepaidPay().toString());//超时是后付费
				timeoutOrdersDetail.setTypeId(d.getId());
				timeoutOrdersDetail.setTypeName(d.getCnName());
				timeoutOrdersDetail.setStrategyId(timeOutStrategy.getId());
				timeoutOrdersDetail.setTimeoutRemark("1");//超时订单
				timeoutOrdersDetail.setSubscriberId(orders.getMemberId());
				timeoutOrdersDetail.setOrdersId(orders.getId());
				timeoutOrdersDetail.setIsPaid("0");
				timeoutOrdersDetail.setIsOver("0");
				timeoutOrdersDetail.setIsTimeout("0");
				timeoutOrdersDetail.setIsException("0");
				
				//如果当前子订单的策略中有对应的罚款，则要新增罚款
				if(currentStrategy.getOvertimePenalty() != null && !currentStrategy.getOvertimePenalty().equals(0)){
					Tickets tickets = new Tickets();
					tickets.setCreateTime(curOrdersDetail.getEndTime());
					tickets.setOrdersNo(timeoutOrdersDetail.getOrdersNo());
					tickets.setOrdersDetailNo(timeoutOrdersDetail.getOrdersDetailNo());
					tickets.setTotalFee(currentStrategy.getOvertimePenalty());
					tickets.setDescription("订单"+timeoutOrdersDetail.getOrdersNo()+"中（"+curOrdersDetail.getOrdersDetailNo()+"）超时，对用户进行罚款");
					ticketsService.addTicket(tickets);
					timeoutOrdersDetail.setTicketsId(tickets.getId());
					timeoutOrdersDetail.setTicketsFee(tickets.getTotalFee());
//					BigDecimal beforFee = curOrdersDetail.getTotalFee();
//					BigDecimal afterFee = beforFee.add(tickets.getTotalFee());
					
//					BigDecimal beforOrderFee = orders.getTotalFee();
//					BigDecimal afterOrderFee = beforOrderFee.add(tickets.getTotalFee());
//					orders.setTotalFee(afterOrderFee);
					msgInfostr="尊敬的"+subscriber.getName()+"，您的当前订单("+orders.getOrdersNo()+")已超时，系统将扣去您"+currentStrategy.getOvertimePenalty()+"元的超时罚款，超时后系统将按照时租进行计费，请您尽快还车。【乐途出行】";
				}else{
					msgInfostr="尊敬的"+subscriber.getName()+"，您的当前订单("+orders.getOrdersNo()+")已超时，系统将会按照超时进行计费，请您尽快还车或者选择续租。【乐途出行】";
				}
				ordersDetailService.addOrdersDetail(timeoutOrdersDetail);
				
				//结束当前子订单
				curOrdersDetail.setIsRunning("0");
				curOrdersDetail.setIsOver("1");
				curOrdersDetail.setIsTimeout("1");//标记该子订单超时
				ordersDetailService.updateOrdersDetail(curOrdersDetail);
				
				//主订单中标记当前运行的子订单
				orders.setRunningDetailNo(timeoutOrdersDetail.getOrdersDetailNo());
				ordersService.updateOrders(orders);
				try {
					SMSUtil.sendSMS(subscriber.getPhoneNo(), msgInfostr,SMSRecord.TYPE_ORDER);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		 log.debug(sdf.format(logTime)+"  执行新订单以及扫描订单是否超时  end");
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
	public TicketsService getTicketsService() {
		return ticketsService;
	}
	public void setTicketsService(TicketsService ticketsService) {
		this.ticketsService = ticketsService;
	}
	public StrategyBaseService getStrategyBaseService() {
		return strategyBaseService;
	}
	public void setStrategyBaseService(StrategyBaseService strategyBaseService) {
		this.strategyBaseService = strategyBaseService;
	}
}
