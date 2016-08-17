package com.dearho.cs.carservice.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dearho.cs.carservice.dao.CarViolationDAO;
import com.dearho.cs.carservice.pojo.CarViolation;
import com.dearho.cs.carservice.service.CarViolationService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.report.util.ReportDateUtil;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

public class CarViolationServiceImpl implements CarViolationService{
	
	private CarViolationDAO carViolationDAO;
	 
	public void setCarViolationDAO(CarViolationDAO carViolationDAO) {
		this.carViolationDAO = carViolationDAO;
	}
	public CarViolationDAO getCarViolationDAO() {
		return carViolationDAO;
	}

	@Override
	public List<CarViolation> searchCarViolation(CarViolation carViolation) {
		String hql = "from CarViolation r where 1=1 ";
		
		if(carViolation != null){
			if(!StringHelper.isRealNull(carViolation.getCode())){
				hql += "and r.code = '"+carViolation.getCode()+"'";
			}
		}
		return carViolationDAO.searchCarViolation(hql);
	}

	@Override
	public List<CarViolation> searchCarViolationCurrentDate() {
		Calendar todayStart = Calendar.getInstance();  
	    todayStart.set(Calendar.HOUR, 0);  
	    todayStart.set(Calendar.MINUTE, 0);  
	    todayStart.set(Calendar.SECOND, 0);  
	    todayStart.set(Calendar.MILLISECOND, 0);  
	    Date startDate = todayStart.getTime();  
	    Date endDate = new Date();
	    
	    String hql = "from CarViolation r where 1=1 and r.createTime >= '"+DateUtil.getChar19DateString(startDate)+"' and r.createTime <= '"+DateUtil.getChar19DateString(endDate)+"'";
		return carViolationDAO.searchCarViolation(hql);
	}
	
	@Override
	public void addCarViolation(CarViolation carViolation) {
		carViolationDAO.addCarViolation(carViolation);
	}

	@Override
	public void updateCarViolation(CarViolation carViolation) {
		carViolationDAO.updateCarViolation(carViolation);
	}

	@Override
	public void deleteCarViolation(String[] checkdel) {
		carViolationDAO.deleteCarViolation(checkdel);
	}

	@Override
	public Page<CarViolation> searchCarViolation(Page<CarViolation> page,
			CarViolation carViolation) {
		String hql = "select a.id from CarViolation a";
		if(carViolation != null){
			if(!StringHelper.isRealNull(carViolation.getOrderCode())){
				hql += ",Orders o";
			}
			if(!StringHelper.isRealNull(carViolation.getMemberName())){
				hql += ",Subscriber s";
			}
			if(!StringHelper.isRealNull(carViolation.getPlateNumber())){
				hql += ",Car c";
			}
			
			hql += " where 1=1 ";
			
			if(!StringHelper.isRealNull(carViolation.getOrderId())){
				hql += " and a.orderId = '"+carViolation.getOrderId()+"'";
			}
			if(!StringHelper.isRealNull(carViolation.getOrderCode())){
				hql += " and a.orderId = o.id and o.ordersNo = '" + carViolation.getOrderCode().trim() +"'" ;
			}
			if(!StringHelper.isRealNull(carViolation.getMemberId())){
				hql += " and a.memberId = '"+carViolation.getMemberId()+"'";
			}
			if(!StringHelper.isRealNull(carViolation.getMemberName())){
				hql += " and a.memberId = s.id and s.name like '%"+carViolation.getMemberName().trim()+"%'";
			}
			if(!StringHelper.isRealNull(carViolation.getCarId())){
				hql += " and a.carId = '"+carViolation.getCarId()+"'";
			}
			if(!StringHelper.isRealNull(carViolation.getPlateNumber())){
				hql += " and a.carId = c.id and c.vehiclePlateId ='" + carViolation.getPlateNumber().trim() + "'";
			}
			
			if(!StringHelper.isRealNull(carViolation.getCode())){
				hql += " and a.code = '"+carViolation.getCode().trim()+"'";
			}
			if(!StringHelper.isRealNull(carViolation.getCity())){
				hql += " and a.city = '"+carViolation.getCity().trim()+"'";
			}
			if(carViolation.getIsDiscard() != null){
				hql += " and a.isDiscard = "+carViolation.getIsDiscard();
			}
		}
		hql += (StringHelper.isEmpty(page.getOrderByString()) ? "order by a.createTime desc" : page.getOrderByString());
		return carViolationDAO.searchCarViolation(page, hql);
	}

	@Override
	public CarViolation searchCarViolationById(String id) {
		return carViolationDAO.searchCarViolationById(id);
	}
	@Override
	public Page<CarViolation> searchCarViolationNear15Days(
			Page<CarViolation> page, CarViolation carViolation) {
		String hql = "select a.id from CarViolation a";
		Dict d = DictUtil.getDictByCodes("violationBizStatus", "01");
		if(d != null){
			hql += " where a.bizStatus != '" + d.getId() + "' and ts > '" 
					+ DateUtil.formatDate(ReportDateUtil.getOtherDay(new Date(),-15, 1), "yyyy-MM-dd HH:mm:ss")+"'";
		}
		hql += (StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		return carViolationDAO.searchCarViolation(page, hql);
	}

}
