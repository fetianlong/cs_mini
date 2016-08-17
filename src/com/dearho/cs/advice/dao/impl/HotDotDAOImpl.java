package com.dearho.cs.advice.dao.impl;

import java.util.List;




import com.dearho.cs.advice.dao.HotDotDAO;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.advice.pojo.HotDot;

/**
 * 催我建点 DAO 实现类
 * @fileName:HotDotDAOImpl.java
 * @author:赵振明
 * @createTime:2016年7月15日上午10:41:31
 *
 */
public class HotDotDAOImpl extends AbstractDaoSupport implements HotDotDAO {

	@Override
	public void addHotDot(HotDot hotDot) {
		this.addEntity(hotDot);
	}

	@Override
	public void updateHotDot(HotDot hotDot) {
		this.updateEntity(hotDot);
	}

	@Override
	public void deleteHotDot(String[] id) {
		final String queryString="delete BranchDot where id in (:ids)";
		deleteEntity(HotDot.class, queryString, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HotDot> searchHotDot(String hql) {
		return this.getHibernateTemplate().find(hql);
	}
}
