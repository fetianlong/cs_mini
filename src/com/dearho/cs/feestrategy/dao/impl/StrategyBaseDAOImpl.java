package com.dearho.cs.feestrategy.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.feestrategy.dao.StrategyBaseDAO;
import com.dearho.cs.feestrategy.pojo.StrategyBase;
import com.dearho.cs.util.StringHelper;

public class StrategyBaseDAOImpl extends AbstractDaoSupport implements StrategyBaseDAO {

	@SuppressWarnings("unchecked")
	@Override
	public StrategyBase searchStrategyBaseById(String id) {
		if(StringHelper.isEmpty(id)){
			return null;
		}
		String hql = "from StrategyBase r where r.id = ?";
		List<StrategyBase> strategyBases = getHibernateTemplate().find(hql, id);
		if(strategyBases != null && strategyBases.size() > 0){
			return strategyBases.get(0);
		}
		return null;
	}

	@Override
	public void addStrategyBase(StrategyBase strategyBase) {
		addEntity(strategyBase);;
	}

	@Override
	public void updateStrategyBase(StrategyBase strategyBase) {
		updateEntity(strategyBase);
	}


	@Override
	public Page<StrategyBase> searchStrategyBase(Page<StrategyBase> page, String hql) {
		Page<StrategyBase> resultPage = pageCache(StrategyBase.class ,page, hql);
		resultPage.setResults(idToObj(StrategyBase.class, resultPage.getmResults()));
		return resultPage;
	}

	@Override
	public void deleteStrategyBase(String[] checkdel) {
		final String queryString="delete StrategyBase where id in (:ids)";
		deleteEntity(StrategyBase.class, queryString, checkdel);
	}

	@Override
	public List<StrategyBase> getDiscountsByHql(String hql, Object... param) {
		return getHibernateTemplate().find(hql, param);
	}


}
