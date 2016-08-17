package com.dearho.cs.orders.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.orders.dao.TicketsDao;
import com.dearho.cs.orders.pojo.Tickets;

/**
 * 罚单DAO 实现类
 * @author wangjing
 *
 */
public class TicketsDaoImpl  extends AbstractDaoSupport implements TicketsDao{

	@Override
	public List<Tickets> getTicketsByHql(String hql) {
		return getList(Tickets.class,queryFList(hql));
	}

	@Override
	public void updateTickets(Tickets tickets) {
		updateEntity(tickets);
	}

	@Override
	public void addTickets(Tickets tickets) {
		addEntity(tickets);
	}

}
