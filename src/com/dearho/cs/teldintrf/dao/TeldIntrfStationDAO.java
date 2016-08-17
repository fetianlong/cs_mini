package com.dearho.cs.teldintrf.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.teldintrf.pojo.TeldIntrfStation;

public interface TeldIntrfStationDAO {

	void addTeldIntrfStation(TeldIntrfStation station);

	void updateTeldIntrfStation(TeldIntrfStation station);

	List<TeldIntrfStation> findByAllHql(String hql, Object...objects );

	Page<TeldIntrfStation> getTeldStationPages(Page<TeldIntrfStation> page,
			String hql);

}
