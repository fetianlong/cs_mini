package com.dearho.cs.orders.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.dao.impl.OrdersCommentDaoImpl;
import com.dearho.cs.orders.pojo.OrdersComment;
import com.dearho.cs.orders.service.OrdersCommentService;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

public class OrdersCommentServiceImpl implements OrdersCommentService{

	private OrdersCommentDaoImpl ordersCommentDao;
	
	public OrdersCommentDaoImpl getOrdersCommentDao() {
		return ordersCommentDao;
	}

	public void setOrdersCommentDao(OrdersCommentDaoImpl ordersCommentDao) {
		this.ordersCommentDao = ordersCommentDao;
	}

	@Override
	public Page<OrdersComment> queryCommentPage(Page<OrdersComment> page,
			OrdersComment comment, String startTime, String endTime) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from OrdersComment a , Subscriber s  where  a.commentPerson = s.id ");
		if(comment != null){
			if(StringHelper.isNotEmpty(comment.getAuditState())){
				sb.append(" and a.auditState = '").append(comment.getAuditState()).append("' ");
			}
			
			if(StringHelper.isNotEmpty(comment.getCommentPersonName())){
				sb.append(" and s.name like '%").append(comment.getCommentPersonName().trim()).append("%' ");
			}
		}
		
		if(StringHelper.isNotEmpty(startTime)){
			Date startDate = null;
			try {
				startDate = DateUtil.parseDate(startTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
				sb.append(" and a.commentTime > '").append(DateUtil.getChar19DateString(startDate)).append("' ");
			} catch (Exception e) {
			}
		}
		if(StringHelper.isNotEmpty(endTime)){
			Date endDate = null;
			try {
				endDate = DateUtil.parseDate(endTime+" 00:00:00", "yyyy-MM-dd HH:mm:ss");
				sb.append(" and a.commentTime < '").append(DateUtil.getChar19DateString(endDate)).append("' ");
			} catch (Exception e) {
			}
		}
		sb.append(" order by a.commentTime desc");
		page=ordersCommentDao.queryCommentPage(page, sb.toString());
		return page;
	}

	/**
	 * 根据ID获取评论
	 * @Title getCommentById
	 * @Description:(方法描述)
	 * @param id
	 * @return
	 * @throws
	 */
	@Override
	public OrdersComment getCommentById(String id) {
		return ordersCommentDao.getCommentById(id);
	}

	@Override
	public void deleteComment(String[] commentIds) {
		String str="delete OrdersComment a where a.id in (:ids)";
		ordersCommentDao.deleteEntity(OrdersComment.class, str, commentIds);
	}
	
	@Override
	public void update(OrdersComment comment) {
		ordersCommentDao.update(comment);
	}
	
	@Override
	public void addComment(OrdersComment comment) {
		ordersCommentDao.addComment(comment);
	}

	@Override
	@SuppressWarnings("unchecked")
	public double getAvgScore(String carId) {
		String scoreHql = "select avg(oc.score) from OrdersComment oc,Orders o where oc.ordersId = o.id and o.carId = '"+carId+"' and oc.auditState = '1' group by o.carId " ;
		List<Object> scores = ordersCommentDao.getHibernateTemplate().find(scoreHql);
		if(scores != null && scores.size() > 0){
			if(scores.get(0) != null && StringHelper.isNotEmpty(String.valueOf(scores.get(0)))){
				return Double.parseDouble(String.valueOf(scores.get(0)) == null ? "0" : String.valueOf(scores.get(0)).substring(0, String.valueOf(scores.get(0)).indexOf(".")+2));
			}
			
		}
		return 0;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OrdersComment> queryComment(String type, String carId,
			String lastCommentId) {
		StringBuffer sb=new StringBuffer();
		sb.append("select a.id from OrdersComment a,Orders o where a.ordersId = o.id and o.carId = '").append(carId).append("' ");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		
		if(type != null && "add".equals(type) && StringHelper.isNotEmpty(lastCommentId)){
			OrdersComment comment = ordersCommentDao.getCommentById(lastCommentId);
			if(comment != null){
				sb.append(" and a.commentTime < '").append(DateUtil.getChar19DateString(comment.getCommentTime())).append("'");
			}
		}
		
		sb.append(" and a.auditState = '1' order by a.commentTime desc ");
		
		Page<OrdersComment> page = new Page<OrdersComment>();
		page.setCountField("a.id");
		page.setCurrentPage(0);
		page.setPageSize(5);
		
		page=ordersCommentDao.queryCommentPage(page, sb.toString());
		
		List<OrdersComment> orders = page.getResults();
		return orders;
	}

	@Override
	public Integer getCommentCount(String carId) {
		String countHql = "select count(oc.id) from OrdersComment oc,Orders o where oc.ordersId = o.id and o.carId = '"+carId+"' and  oc.auditState = '1'" ;
		List<Object> counts = ordersCommentDao.getHibernateTemplate().find(countHql);
		if(counts != null && counts.size() > 0){
			if(counts.get(0) != null && StringHelper.isNotEmpty(String.valueOf(counts.get(0)))){
				return Integer.parseInt(String.valueOf(counts.get(0)));
			}
			
		}
		return 0;
	}

}
