package com.dearho.cs.orders.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.account.dao.AccountTradeRecordDao;
import com.dearho.cs.account.dao.AccountTradeRecordDetailDao;
import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.AccountTradeRecordDetail;
import com.dearho.cs.orders.dao.OrdersDetailDao;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersDetail;
import com.dearho.cs.orders.service.OrdersDetailService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.util.SMSUtil;

/**
 * 子订单服务接口实现类
 * @author wangjing
 *
 */
public class OrdersDetailServiceImpl implements OrdersDetailService{

	private OrdersDetailDao ordersDetailDao;
	private OrdersService ordersService;
	private SubscriberService subscriberService;

	private AccountTradeRecordDetailDao accountTradeRecordDetailDao;
	private AccountTradeRecordDao accountTradeRecordDao;
	
	public OrdersDetailDao getOrdersDetailDao() {
		return ordersDetailDao;
	}

	public void setOrdersDetailDao(OrdersDetailDao ordersDetailDao) {
		this.ordersDetailDao = ordersDetailDao;
	}
	
	@Override
	public List<OrdersDetail> getPaidOrdersDetailsByNo(String ordersNo) {
		if(ordersNo!=null && !"".equals(ordersNo)){
			StringBuffer sb=new StringBuffer();
			sb.append("select a.id from OrdersDetail a where a.ordersNo ='"+ordersNo+"' order by a.beginTime ");
			List<OrdersDetail> ordersDetails = ordersDetailDao.getOrdersDetailsByHql(sb.toString());
			List<OrdersDetail> list = new ArrayList<OrdersDetail>();
			if(ordersDetails != null){
				Orders orders = ordersService.queryOrdersByOrderNo(ordersNo);
				String firstOrderNo = orders.getFirstDetailNo();
				for(int i=0;i<ordersDetails.size();i++){
					OrdersDetail ordersDetail = ordersDetails.get(i);//支付不成功的预付费订单，不要展示
					if(!firstOrderNo.equals(ordersDetail.getOrdersDetailNo()) && ordersDetail.getIsPrePay().equals("1") && ordersDetail.getIsPaid().equals("0")){
						continue;
					}else{
						 List<AccountTradeRecord> recordList=accountTradeRecordDao.queryTradeRecordBySubOrderNo(ordersDetail.getOrdersDetailNo(), Account.TYPE_ORDER, AccountTradeRecord.RESULT_SUCCESS);
						 if(recordList!=null&& recordList.size()>0){
							 AccountTradeRecord accountTradeRecord=recordList.get(0);
							 List<AccountTradeRecordDetail> detailList= accountTradeRecordDetailDao.getAccountTradeRecordDetailByBizId(accountTradeRecord.getId());
							 if(detailList!=null && detailList.size()>0 ){
								 accountTradeRecord.setAccountTradeRecordDetail(detailList.get(0));
							 }
							 ordersDetail.setAccountTradeRecord(accountTradeRecord);
						 }
						
						list.add(ordersDetail);
					}
					
				}
				return list;
			}else{
				return null;
			}
			
		}else{
			return null;
		}
	}

	@Override
	public List<OrdersDetail> getOrdersDetailsByNo(String ordersNo) {
		if(ordersNo!=null && !"".equals(ordersNo)){
			List<OrdersDetail> ordersDetails = new ArrayList<OrdersDetail>();
			StringBuffer sb=new StringBuffer();
			sb.append("select a.id from OrdersDetail a where a.ordersNo ='"+ordersNo+"' and a.isException ='0' order by a.beginTime ");
			ordersDetails = ordersDetailDao.getOrdersDetailsByHql(sb.toString());
			if(ordersDetails != null){
				return ordersDetails;
			}else{
				Orders orders = ordersService.queryOrdersByOrderNo(ordersNo);
				if(orders!=null){
					String firstOrdersDetailNo = orders.getFirstDetailNo();
					ordersDetails = ordersDetailDao.getOrdersDetailsByHql("select a.id from OrdersDetail a where a.ordersDetailNo ='"+firstOrdersDetailNo+"' ");
					return ordersDetails;
				}else{
					return null;
				}
			}
		}else{
			return null;
		}
	}
	
	@Override
	public List<OrdersDetail> getAllOrdersDetailsByNo(String ordersNo) {
		if(ordersNo!=null && !"".equals(ordersNo)){
			StringBuffer sb=new StringBuffer();
			sb.append("select a.id from OrdersDetail a where a.ordersNo ='"+ordersNo+"' order by a.beginTime desc ");
			List<OrdersDetail> ordersDetails = ordersDetailDao.getOrdersDetailsByHql(sb.toString());
			if(ordersDetails != null){
				return ordersDetails;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 得到该订车流程中正在进行的订单
	 */
	@Override
	public OrdersDetail getRunningOrdersDetail(String ordersNo) {
		OrdersDetail ordersDetail = null;
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from OrdersDetail a where a.ordersNo ='"+ordersNo+"' and a.isRunning ='1' ");
		List<OrdersDetail> ordersDetailList = ordersDetailDao.getOrdersDetailsByHql(sb.toString());
		if(ordersDetailList != null && ordersDetailList.size() != 0){
			ordersDetail = ordersDetailList.get(0);
		}
		return ordersDetail;
	}

	/**
	 * 新增子订单
	 * @param ordersDetail
	 */
	@Override
	public void addOrdersDetail(OrdersDetail ordersDetail) {
		ordersDetailDao.addOrdDetail(ordersDetail);
	}

	/**
	 * 更新子订单
	 * @param ordersDetail
	 */
	@Override
	public void updateOrdersDetail(OrdersDetail ordersDetail) {
		ordersDetailDao.updateOrdDetail(ordersDetail);
	}

	/**
	 * HQL查找订单
	 */
	@Override
	public List<OrdersDetail> queryOrdersDetail(String hql) {
		return ordersDetailDao.queryOrdersDetail(hql);
	}

	/**
	 * 通过ID得到子订单
	 * @param id 
	 * @return
	 */
	@Override
	public OrdersDetail getOrdersDetailById(String id) {
		OrdersDetail ordersDetail = new OrdersDetail();
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from OrdersDetail a where a.id ='"+id+"'");
		List<OrdersDetail> ordersDetailList = ordersDetailDao.getOrdersDetailsByHql(sb.toString());
		if(ordersDetailList!=null){
			ordersDetail = ordersDetailList.get(0);
			return ordersDetail;
		}else{
			return null;
		}
	}

	/**
	 * 得到这个总订单中的超时订单
	 * @param ordersNo 
	 * @return
	 */
	@Override
	public OrdersDetail getTimeOutOrdersDetail(String ordersNo) {
		OrdersDetail ordersDetail = new OrdersDetail();
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from OrdersDetail a where a.ordersNo ='"+ordersNo+"' and isTimeOut ='1' ");
		List<OrdersDetail> ordersDetailList = ordersDetailDao.getOrdersDetailsByHql(sb.toString());
		ordersDetail = ordersDetailList.get(0);
		return ordersDetail;
	}

	/**
	 * 通过ordersDetailNo得到子订单
	 * @param ordersDetailNo 
	 * @return
	 */
	@Override
	public OrdersDetail getOrdersDetailByNo(String ordersDetailNo) {
		OrdersDetail ordersDetail = new OrdersDetail();
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from OrdersDetail a where a.ordersDetailNo ='"+ordersDetailNo+"'");
		List<OrdersDetail> ordersDetailList = ordersDetailDao.getOrdersDetailsByHql(sb.toString());
		if(ordersDetailList!=null){
			ordersDetail = ordersDetailList.get(0);
			return ordersDetail;
		}else{
			return null;
		}
	}

	/**
	 * 通过子订单编号,得到该笔子订单的费用 
	 * @param ordersDetailNo
	 * @return
	 */
	@Override
	public BigDecimal getOrdersDetailFee(String ordersDetailNo) {
		OrdersDetail ordersDetail = new OrdersDetail();
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from OrdersDetail a where a.ordersDetailNo ='"+ordersDetailNo+"'");
		List<OrdersDetail> ordersDetailList = ordersDetailDao.getOrdersDetailsByHql(sb.toString());
		BigDecimal totalFee = new BigDecimal("0.00"); 
		if(ordersDetailList!=null){
			ordersDetail = ordersDetailList.get(0);
			totalFee = ordersDetail.getTotalFee();
		}
		return totalFee;
	}

	/**
	 * 通过订单编号,得到该订单的总费用 
	 * @param ordersNo
	 * @return
	 */
	@Override
	public BigDecimal getOrdersTotalFee(String ordersNo) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from OrdersDetail a where a.ordersNo ='"+ordersNo+"'");
		List<OrdersDetail> ordersDetailList = ordersDetailDao.getOrdersDetailsByHql(sb.toString());
		BigDecimal totalFee = new BigDecimal("0.00"); 
		for(OrdersDetail ordersDetail : ordersDetailList){
			//只计算已经付款了的费用
			if(ordersDetail.getIsPaid() != null && ordersDetail.getIsPaid().equals("1")){
				BigDecimal ordersDetailFee = ordersDetail.getTotalFee();
				if(ordersDetailFee!=null){
					totalFee = totalFee.add(ordersDetailFee);
				}
				if(ordersDetail.getTicketsFee()!=null){
					totalFee = totalFee.add(ordersDetail.getTicketsFee());
				}
			}
		}
		return totalFee;
	}

	@Override
	public void deleteOrdersDetail(OrdersDetail ordersDetail) {
		ordersDetailDao.addOrdDetail(ordersDetail);
	}
	
	/**
	 * 支付成功时回调方法（仅适用于下单时）
	 */
	public void ordersPaySuccess(String ordersDetailNo,Integer payType){
		OrdersDetail payOrdersDetail = getOrdersDetailByNo(ordersDetailNo);
		BigDecimal totalBigDecimal = BigDecimal.valueOf(0);
		if(payOrdersDetail != null){
			totalBigDecimal = totalBigDecimal.add(payOrdersDetail.getTotalFee());
			Orders orders = ordersService.queryOrdersByOrderNo(payOrdersDetail.getOrdersNo());
			if(payOrdersDetail.getNextOrders()!=null && !"".equals(payOrdersDetail.getNextOrders())){
				OrdersDetail nextOrdersDetail = getOrdersDetailById(payOrdersDetail.getNextOrders());
				
				//如果当前订单是即时租，则立即切换套餐
				if(!payOrdersDetail.getIsPrePay().equals("1")){
					payOrdersDetail.setIsRunning("0");
					payOrdersDetail.setIsOver("1");//结束当前即时租订单
					nextOrdersDetail.setIsPaid("1");//标记已支付
					nextOrdersDetail.setIsRunning("1");//开启下一订单
					orders.setRunningDetailNo(nextOrdersDetail.getOrdersDetailNo());//更新主订单
				}
				totalBigDecimal = totalBigDecimal.add(nextOrdersDetail.getTotalFee());
				updateOrdersDetail(nextOrdersDetail);
			}
			payOrdersDetail.setPayType(payType);
			payOrdersDetail.setIsPaid("1");
			updateOrdersDetail(payOrdersDetail);
			//修改主订单的总费用金额
			BigDecimal totalFeeBigDecimal = getOrdersTotalFee(payOrdersDetail.getOrdersNo());
			orders.setTotalFee(totalFeeBigDecimal);
			ordersService.updateOrders(orders);
			
			//支付成功，给用户发短信
			Subscriber s = subscriberService.querySubscriberAllInfoById(orders.getMemberId());
			
			StringBuffer message = new StringBuffer("");
			if(payOrdersDetail.getPreOrders()==null || "".equals(payOrdersDetail.getPreOrders())){//预约流程
				message.append("尊敬的" + s.getName() + ":您已经成功支付"+totalBigDecimal+"元，恭喜您的日租订单已经预定成功，请您尽快取车，逾时订单将自动开启。【乐途出行】");
			}else{//续租流程
				message.append("尊敬的" + s.getName() + ":您已经成功支付"+totalBigDecimal+"元");
				if("1".equals(payOrdersDetail.getTimeoutRemark())){
					BigDecimal ticketFee = payOrdersDetail.getTicketsFee();
					BigDecimal totalFee = payOrdersDetail.getTicketsFee();
					message.append("，其中超时费用为"+ticketFee.add(totalFee)+"元。");
				}else if(payOrdersDetail.getNextOrders()!=null){
					message.append("，恭喜您续租成功，欢迎您继续使用。");
				}
				message.append("【乐途出行】");
			}
			try {
				if (s != null) {
					SMSUtil.sendSMS(s.getPhoneNo(), message.toString(), SMSRecord.TYPE_ORDER);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
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

	public AccountTradeRecordDetailDao getAccountTradeRecordDetailDao() {
		return accountTradeRecordDetailDao;
	}

	public void setAccountTradeRecordDetailDao(AccountTradeRecordDetailDao accountTradeRecordDetailDao) {
		this.accountTradeRecordDetailDao = accountTradeRecordDetailDao;
	}

	public AccountTradeRecordDao getAccountTradeRecordDao() {
		return accountTradeRecordDao;
	}

	public void setAccountTradeRecordDao(AccountTradeRecordDao accountTradeRecordDao) {
		this.accountTradeRecordDao = accountTradeRecordDao;
	}

	
	

}
