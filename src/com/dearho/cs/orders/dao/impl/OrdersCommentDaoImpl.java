package com.dearho.cs.orders.dao.impl;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.dao.OrdersCommentDao;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.orders.pojo.OrdersComment;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.pojo.User;

public class OrdersCommentDaoImpl extends AbstractDaoSupport implements OrdersCommentDao{

	@Override
	public Page<OrdersComment> queryCommentPage(Page<OrdersComment> page,
			String hql) {
		Page<OrdersComment> resultPage=pageCache(OrdersComment.class, page, hql);
		resultPage.setResults(idToObj(OrdersComment.class, resultPage.getmResults()));
		if(resultPage.getResults()!=null){
			for(int i=0;i<resultPage.getResults().size();i++){
				OrdersComment comment = (OrdersComment)resultPage.getResults().get(i);
				Orders orders = get(Orders.class , comment.getOrdersId());//得到该订单
				Subscriber subscriber = get(Subscriber.class , comment.getCommentPerson());//得到该会员信息
				User user = get(User.class , comment.getAuditor());//得到审批人信息
				comment.setOrdersNo(orders.getOrdersNo());
				comment.setCommentPersonName(subscriber.getName());
				comment.setAuditorName(user == null ? "" : user.getName() );
			}
		}
		return resultPage;
	}

	@Override
	public OrdersComment getCommentById(String id) {
		return  get(OrdersComment.class , id);
	}

	@Override
	public void update(OrdersComment comment) {
		updateEntity(comment);
	}
	
	@Override
	public void addComment(OrdersComment comment) {
		addEntity(comment);
	}

}
