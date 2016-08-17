package com.dearho.cs.orders.service;

import java.math.BigDecimal;
import java.util.List;

import com.dearho.cs.orders.pojo.OrdersDetail;

/**
 * 子订单服务接口类
 * @author wangjing
 *
 */
public interface OrdersDetailService {

	/**
	 * 通过订单编号得到该订单所有有效的子订单
	 * @param ordersNo
	 * @return
	 */
	public List<OrdersDetail> getOrdersDetailsByNo(String ordersNo);
	
	/**
	 * 通过订单编号得到该订单所有子订单，包括异常单
	 * @param ordersNo
	 * @return
	 */
	public List<OrdersDetail> getAllOrdersDetailsByNo(String ordersNo);
	
	/**
	 * 得到这个总订单中，正在进行的订单
	 * @param ordersNo 
	 * @return
	 */
	public OrdersDetail getRunningOrdersDetail(String ordersNo);
	
	/**
	 * 得到这个总订单中的超时订单
	 * @param ordersNo 
	 * @return
	 */
	public OrdersDetail getTimeOutOrdersDetail(String ordersNo);
	
	/**
	 * 通过ID得到子订单
	 * @param id 
	 * @return
	 */
	public OrdersDetail getOrdersDetailById(String id);
	
	/**
	 * 通过ordersDetailNo得到子订单
	 * @param ordersDetailNo 
	 * @return
	 */
	public OrdersDetail getOrdersDetailByNo(String ordersDetailNo);
	
	/**
	 * 新增子订单
	 * @param ordersDetail
	 */
	public void addOrdersDetail(OrdersDetail ordersDetail);
	
	/**
	 * 更新子订单
	 * @param ordersDetail
	 */
	public void updateOrdersDetail(OrdersDetail ordersDetail);
	
	/**
	 * 通过HQL查找
	 * @param hql
	 * @return
	 */
	public List<OrdersDetail> queryOrdersDetail(String hql);

	/**
	 * 通过子订单编号,得到该笔子订单的费用 
	 * @param ordersDetailNo
	 * @return
	 */
	public BigDecimal getOrdersDetailFee(String ordersDetailNo);
	
	/**
	 * 通过订单编号,得到该订单的总费用 
	 * @param ordersNo
	 * @return
	 */
	public BigDecimal getOrdersTotalFee(String ordersNo);
	
	/**
	 * 通过订单编号,删除子订单
	 * @param ordersDetailNo
	 * @return
	 */
	public void deleteOrdersDetail(OrdersDetail ordersDetail);
	
	/**
	 * 通过订单编号,获得所有已支付的订单
	 * @param ordersNo
	 * @return
	 */
	public List<OrdersDetail> getPaidOrdersDetailsByNo(String ordersNo);
	
	
	/**
	 * 付款成功后的业务逻辑
	 * @param ordersDetailNo
	 */
	public void ordersPaySuccess(String ordersDetailNo,Integer payType);
}
