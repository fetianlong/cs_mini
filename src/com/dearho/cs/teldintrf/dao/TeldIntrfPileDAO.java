package com.dearho.cs.teldintrf.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.teldintrf.pojo.TeldIntrfPile;

public interface TeldIntrfPileDAO {

	void addTeldIntrfPile(TeldIntrfPile pile);

	void updateTeldIntrfPile(TeldIntrfPile pile);

	List<TeldIntrfPile> findByAllHql(String hql, Object...objects );

	Page<TeldIntrfPile> getTeldPilePages(Page<TeldIntrfPile> page,
			String hql);

}
