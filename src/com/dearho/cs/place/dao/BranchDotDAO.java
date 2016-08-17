package com.dearho.cs.place.dao;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.place.pojo.BranchDot;

public interface BranchDotDAO {
	void addBranchDot(BranchDot branchDot);
	void updateBranchDot(BranchDot branchDot);
	void deleteBranchDot(final String[] checkdel);
	Page<BranchDot> searchBranchDot(Page<BranchDot> page,String hql);
	List<BranchDot> searchBranchDot(String hql);
	BranchDot getBranchDotById(String id);
	List<Car> getUnbookCars(String id);
	HibernateTemplate getHt();
	List<BranchDot> getList(String hql);
}
