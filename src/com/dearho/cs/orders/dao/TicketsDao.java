package com.dearho.cs.orders.dao;

import java.util.List;

import com.dearho.cs.orders.pojo.Tickets;

/**
 * 罚单DAO
 * @author wangjing
 *
 */
public interface TicketsDao {

	public List<Tickets> getTicketsByHql(String hql);
	
	public void updateTickets(Tickets tickets);
	
	public void addTickets(Tickets tickets);
}
