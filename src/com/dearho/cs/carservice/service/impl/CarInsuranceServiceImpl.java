package com.dearho.cs.carservice.service.impl;

import java.util.Date;
import java.util.List;

import com.dearho.cs.carservice.dao.CarInsuranceDAO;
import com.dearho.cs.carservice.pojo.CarInsurance;
import com.dearho.cs.carservice.service.CarInsuranceService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.report.util.ReportDateUtil;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

public class CarInsuranceServiceImpl implements CarInsuranceService{
	
	private CarInsuranceDAO carInsuranceDAO;
	 
	public void setCarInsuranceDAO(CarInsuranceDAO carInsuranceDAO) {
		this.carInsuranceDAO = carInsuranceDAO;
	}
	public CarInsuranceDAO getCarInsuranceDAO() {
		return carInsuranceDAO;
	}

	@Override
	public List<CarInsurance> searchCarInsurance(CarInsurance carInsurance) {
		String hql = "from CarInsurance r where 1=1 ";
		
		if(carInsurance != null){
			if(!StringHelper.isRealNull(carInsurance.getCode())){
				hql += "and r.code = '"+carInsurance.getCode()+"'";
			}
		}
		return carInsuranceDAO.searchCarInsurance(hql);
	}

	@Override
	public void addCarInsurance(CarInsurance carInsurance) {
		carInsuranceDAO.addCarInsurance(carInsurance);
	}

	@Override
	public void updateCarInsurance(CarInsurance carInsurance) {
		carInsuranceDAO.updateCarInsurance(carInsurance);
	}

	@Override
	public void deleteCarInsurance(String[] checkdel) {
		carInsuranceDAO.deleteCarInsurance(checkdel);
	}

	@Override
	public Page<CarInsurance> searchCarInsurance(Page<CarInsurance> page,
			CarInsurance carInsurance) {
		String hql = "select a.id from CarInsurance a  ";
		if(carInsurance != null){
			if(!StringHelper.isRealNull(carInsurance.getPlateNumber())){
				hql += ",Car c";
			}
			
			hql += " where 1=1 ";
			
			if(!StringHelper.isRealNull(carInsurance.getPlateNumber())){
				hql += " and a.carId = c.id and c.vehiclePlateId ='" + carInsurance.getPlateNumber().trim() + "'";
			}
			
			if(StringHelper.isNotEmpty(carInsurance.getCode())){
				hql += " and a.code = '"+carInsurance.getCode().trim()+"'";
			}
			if(StringHelper.isNotEmpty(carInsurance.getPolicyHolder())){
				hql += " and a.policyHolder = '"+carInsurance.getPolicyHolder().trim()+"'";
			}
			if(StringHelper.isNotEmpty(carInsurance.getIpName())){
				hql += " and a.ipName = '"+carInsurance.getIpName().trim()+"'";
			}
			if(StringHelper.isNotEmpty(carInsurance.getCity())){
				hql += " and a.city = '"+carInsurance.getCity().trim()+"'";
			}
			if(carInsurance.getIsDiscard() != null){
				hql += " and a.isDiscard = "+carInsurance.getIsDiscard();
			}
			if(carInsurance.getTrfInsEndDate() != null){
				hql += " and a.trfInsEndDate > '" 
					+ DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") 
					+ "' and a.trfInsEndDate < '"
					+ DateUtil.formatDate(carInsurance.getTrfInsEndDate(), "yyyy-MM-dd HH:mm:ss")+"'";
			}
			if(carInsurance.getCommInsEndDate() != null){
				hql += " and a.commInsEndDate > '" 
					+ DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") 
					+ "' and a.commInsEndDate < '"
					+ DateUtil.formatDate(carInsurance.getCommInsEndDate(), "yyyy-MM-dd HH:mm:ss")+"'";
			}
		}
		hql += (StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createTime desc " : page.getOrderByString());
		return carInsuranceDAO.searchCarInsurance(page, hql);
	}

	@Override
	public CarInsurance searchCarInsuranceById(String id) {
		return carInsuranceDAO.searchCarInsuranceById(id);
	}

}
