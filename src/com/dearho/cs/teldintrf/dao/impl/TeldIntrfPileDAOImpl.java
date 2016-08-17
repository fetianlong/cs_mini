package com.dearho.cs.teldintrf.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.teldintrf.dao.TeldIntrfPileDAO;
import com.dearho.cs.teldintrf.pojo.TeldIntrfPile;

public class TeldIntrfPileDAOImpl extends AbstractDaoSupport implements TeldIntrfPileDAO {

	@Override
	public void addTeldIntrfPile(TeldIntrfPile pile) {
		addEntity(pile);
	}

	@Override
	public void updateTeldIntrfPile(TeldIntrfPile pile) {
		updateEntity(pile);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TeldIntrfPile> findByAllHql(String hql, Object... objects) {
		return getHibernateTemplate().find(hql,objects);
	}

	@Override
	public Page<TeldIntrfPile> getTeldPilePages(
			Page<TeldIntrfPile> page, String hql) {
		Page<TeldIntrfPile> resultPage = pageCache(TeldIntrfPile.class ,page, hql);
		resultPage.setResults(idToObj(TeldIntrfPile.class, resultPage.getmResults()));
		return resultPage;
	}

}
