package com.dearho.cs.feestrategy.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.feestrategy.pojo.StrategyBase;
/**
 * @author GaoYunpeng
 * 策略基本信息DAO
 */
public interface StrategyBaseDAO {
	StrategyBase searchStrategyBaseById(String id);
	void addStrategyBase(StrategyBase strategyBase);
	void updateStrategyBase(StrategyBase strategyBase);
	Page<StrategyBase> searchStrategyBase(Page<StrategyBase> page,String hql);
	void deleteStrategyBase(String[] checkdel);
	List<StrategyBase> getDiscountsByHql(String hql, Object... param);
}
