package com.dearho.cs.resmonitor.dao.impl;

import java.util.Date;
import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.resmonitor.dao.CarLocationDAO;
import com.dearho.cs.resmonitor.pojo.CarLocation;
import com.dearho.cs.util.DateUtil;

public class CarLocationDAOImpl extends AbstractDaoSupport implements CarLocationDAO {

	@Override
	public Page<CarLocation> getLocationByDeviceNo(Page<CarLocation> page,String hql) {
		Page<CarLocation> resultPage = pageCache(CarLocation.class ,page, hql);
		resultPage.setResults(idToObj(CarLocation.class, resultPage.getmResults()));
		return resultPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CarLocation> getLocationByParam(String deviceNo,
			Date startTime, Date endTime) {
		String hql = "from CarLocation c where c.deviceNo = '"+deviceNo+"'";
		if(startTime != null){
			hql += " and c.ts > '"+DateUtil.getChar19DateString(startTime)+"'";
		}
		if(endTime != null){
			hql += " and c.ts < '"+DateUtil.getChar19DateString(endTime)+"'";
		}
		hql += " order by c.ts ";
		
		return getHibernateTemplate().find(hql);
	}

	@Override
	public void add(CarLocation cl) {
		addEntity(cl);
	}

}
