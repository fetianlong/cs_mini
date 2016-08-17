package com.dearho.cs.place.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.place.dao.BranchDotDAO;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.StringHelper;

public class BranchDotDAOImpl extends AbstractDaoSupport implements BranchDotDAO {

	@Override
	public void addBranchDot(BranchDot branchDot) {
		addEntity(branchDot);
	}

	@Override
	public void updateBranchDot(BranchDot branchDot) {
		updateEntity(branchDot);
	}

	@Override
	public void deleteBranchDot(String[] checkdel) {
		final String queryString="delete BranchDot where id in (:ids)";
		deleteEntity(BranchDot.class, queryString, checkdel);
	}

	@Override
	public Page<BranchDot> searchBranchDot(Page<BranchDot> page, String hql) {
		Page<BranchDot> resultPage = pageCache(BranchDot.class ,page, hql);
		resultPage.setResults(idToObj(BranchDot.class, resultPage.getmResults()));
		return resultPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BranchDot> searchBranchDot(String hql) {
		return getHibernateTemplate().find(hql);
	}

	@Override
	public BranchDot getBranchDotById(String id) {
		if(StringHelper.isEmpty(id)){
			return null;
		}
		return get(BranchDot.class,id.trim());
	}

	@Override
	public List<Car> getUnbookCars(String id) {
		StringBuffer sb=new StringBuffer();
		//默认电量排序
		sb.append("select a.id from Car a,CarRealtimeState crs where a.id=crs.id and a.bizState='");
		Dict dict = DictUtil.getDictByCodes("carBizState", "0");
		if(dict == null){
			return null;
		}
		sb.append(dict.getId()).append("'");
		if(StringHelper.isEmpty(id)){
			return null;
		}
		sb.append(" and a.id in (select p.carId from CarDotBinding p where p.dotId = '");
		sb.append(id);
		sb.append("') ");
		sb.append(" order by crs.SOC desc ");
		
		
		return getList(Car.class, queryFList(sb.toString()));
	}
	
	
	@Override
	public HibernateTemplate getHt(){
		return getHibernateTemplate();
	}

	@Override
	public List<BranchDot> getList(String hql) {
		return getList(BranchDot.class, queryFList(hql));
	}

}
