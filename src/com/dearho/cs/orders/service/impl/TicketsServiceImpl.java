package com.dearho.cs.orders.service.impl;

import java.util.List;

import com.dearho.cs.orders.dao.TicketsDao;
import com.dearho.cs.orders.pojo.Tickets;
import com.dearho.cs.orders.service.TicketsService;

/**
 * 罚单服务接口实现类
 * @author wangjing
 *
 */
public class TicketsServiceImpl implements TicketsService{

	private TicketsDao ticketsDao;
	
	public TicketsDao getTicketsDao() {
		return ticketsDao;
	}
	
	public void setTicketsDao(TicketsDao ticketsDao) {
		this.ticketsDao = ticketsDao;
	}
	
	@Override
	public Tickets getTicketById(String id) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Tickets a where a.id ='"+id+"'");
		List<Tickets> ticketsList = ticketsDao.getTicketsByHql(sb.toString());
		if(ticketsList!=null && ticketsList.size()==0){
			return null;
		}else{
			return ticketsList.get(0);
		}
	}
	
	/**
	 * 新增罚单
	 */
	@Override
	public void addTicket(Tickets tickets) {
		ticketsDao.addTickets(tickets);
	}

	/**
	 * 更新罚单
	 */
	@Override
	public void updateTicket(Tickets tickets) {
		ticketsDao.updateTickets(tickets);
	}

	/**
	 * 通过ordersNo获取该订单下的所有罚单
	 * @param ordersNo
	 * @return
	 */
	@Override
	public List<Tickets> getTicketByOrdersNo(String ordersNo) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Tickets a where a.ordersNo ='"+ordersNo+"'");
		List<Tickets> ticketsList = ticketsDao.getTicketsByHql(sb.toString());
		return ticketsList;
	}

	/**
	 * 通过ordersDetailNo获取该超时订单对应的罚单
	 * @param ordersDetailNo
	 * @return
	 */
	@Override
	public Tickets getTicketByOrdersDetailNo(String ordersDetailNo) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from Tickets a where a.ordersDetailNo ='"+ordersDetailNo+"'");
		List<Tickets> ticketsList = ticketsDao.getTicketsByHql(sb.toString());
		if(ticketsList!=null && ticketsList.size()==0){
			return null;
		}else{
			return ticketsList.get(0);
		}
	}

}
