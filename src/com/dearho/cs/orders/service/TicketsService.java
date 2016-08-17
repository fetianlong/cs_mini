package com.dearho.cs.orders.service;

import java.util.List;

import com.dearho.cs.orders.pojo.Tickets;

/**
 * 罚单服务接口类
 * @author wangjing
 *
 */
public interface TicketsService {

	/**
	 * 新增罚单
	 * @param tickets
	 * @return
	 */
	public void addTicket(Tickets tickets);
	
	/**
	 * 更新罚单
	 * @param tickets
	 * @return
	 */
	public void updateTicket(Tickets tickets);
	
	/**
	 * 通过ID获取罚单
	 * @param id
	 * @return
	 */
	public Tickets getTicketById(String id);
	
	/**
	 * 通过ordersNo获取该订单下的所有罚单
	 * @param ordersNo
	 * @return
	 */
	public List<Tickets> getTicketByOrdersNo(String ordersNo);
	
	/**
	 * 通过ordersDetailNo获取该超时订单对应的罚单
	 * @param ordersDetailNo
	 * @return
	 */
	public Tickets getTicketByOrdersDetailNo(String ordersDetailNo);
}
