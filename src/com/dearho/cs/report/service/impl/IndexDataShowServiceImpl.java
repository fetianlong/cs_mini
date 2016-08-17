package com.dearho.cs.report.service.impl;

import java.util.Date;

import com.dearho.cs.report.dao.IndexDataShowDAO;
import com.dearho.cs.report.service.IndexDataShowService;
import com.dearho.cs.report.util.ReportDateUtil;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

public class IndexDataShowServiceImpl implements IndexDataShowService {

	private IndexDataShowDAO indexDataShowDAO;
	
	public IndexDataShowDAO getIndexDataShowDAO() {
		return indexDataShowDAO;
	}
	public void setIndexDataShowDAO(IndexDataShowDAO indexDataShowDAO) {
		this.indexDataShowDAO = indexDataShowDAO;
	}

	
	@Override
	public Integer getRegMemberCount(Date startDate, Date endDate, Integer... states) {
		String hql = "select count(*) from Subscriber a where 1=1 ";
		if(startDate != null){
			hql += " and a.createDate > '"+ DateUtil.formatDate(startDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		if(endDate != null){
			hql += " and a.createDate < '"+ DateUtil.formatDate(endDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		if(states != null && states.length > 0){
			hql += " and (";
			for (int i = 0; i < states.length; i++) {
				if(i > 0){
					hql += " or ";
				}
				Integer state = states[i];
				hql += "  a.state = " + state;
			}
			hql += ")";
			
		}
		Object obj = indexDataShowDAO.executeQuery(hql);
		if(obj == null){
			return 0;
		}
		return Integer.parseInt(String.valueOf(obj));
	}
	
	
	@Override
	public Integer getLoginCount(Date startDate, Date endDate) {
		String hql = "select count(*) from SubscriberLoginRecord a where 1=1 ";
		if(startDate != null){
			hql += " and a.loginTime > '"+ DateUtil.formatDate(startDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		if(endDate != null){
			hql += " and a.loginTime < '"+ DateUtil.formatDate(endDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		return Integer.parseInt(String.valueOf(indexDataShowDAO.executeQuery(hql)));
	}
	@Override
	public Double getRechargeMoney(Date startDate, Date endDate) {
		String hql = "select sum(a.amount) from AccountTradeRecord a where 1=1 and a.type=1 ";
		if(startDate != null){
			hql += " and a.tradeTime > '"+ DateUtil.formatDate(startDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		if(endDate != null){
			hql += " and a.tradeTime < '"+ DateUtil.formatDate(endDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		return Double.parseDouble(StringHelper.isRealNull(String.valueOf(indexDataShowDAO.executeQuery(hql)))
				? "0" : String.valueOf(indexDataShowDAO.executeQuery(hql)));
	}
	@Override
	public Integer getOrderNums(Date startDate, Date endDate) {
		String hql = "select count(*) from Orders a where 1=1 and isAbnormity = 0";
		if(startDate != null){
			hql += " and a.createDate > '"+ DateUtil.formatDate(startDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		if(endDate != null){
			hql += " and a.createDate < '"+ DateUtil.formatDate(endDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		return Integer.parseInt(String.valueOf(indexDataShowDAO.executeQuery(hql)));
	}
	@Override
	public Double getOrderMoney(Date startDate, Date endDate) {
		String hql = "select sum(a.totalFee) from Orders a where 1=1 and isAbnormity = 0 and state = 4";
		if(startDate != null){
			hql += " and a.endTime > '"+ DateUtil.formatDate(startDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		if(endDate != null){
			hql += " and a.endTime < '"+ DateUtil.formatDate(endDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		return Double.parseDouble(StringHelper.isRealNull(String.valueOf(indexDataShowDAO.executeQuery(hql)))
				? "0" : String.valueOf(indexDataShowDAO.executeQuery(hql)));
	}
	@Override
	public Double getOrderHours(Date startDate, Date endDate) {
		String hql = "select sum(a.ordersDuration) from Orders a where 1=1 and isAbnormity = 0 and state = 4";
		if(startDate != null){
			hql += " and a.endTime > '"+ DateUtil.formatDate(startDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		if(endDate != null){
			hql += " and a.endTime < '"+ DateUtil.formatDate(endDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		return Double.parseDouble(StringHelper.isRealNull(String.valueOf(indexDataShowDAO.executeQuery(hql)))
				? "0" : String.valueOf(indexDataShowDAO.executeQuery(hql)));
	}
	@Override
	public Integer getBizFlow(Integer bizType, Integer result, Date startDate,
			Date endDate) {
		String hql = "select count(*) from BusinessFlow a where 1=1 ";
		if(bizType != null){
			hql += " and a.businessType="+bizType;
		}
		if(result != null){
			hql += " and a.result="+result;
		}
		if(startDate != null){
			hql += " and a.reviewTime > '"+ DateUtil.formatDate(startDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		if(endDate != null){
			hql += " and a.reviewTime < '"+ DateUtil.formatDate(endDate, "yyyy-MM-dd HH:mm:ss") + "'";
		}
		return Integer.parseInt(String.valueOf(indexDataShowDAO.executeQuery(hql)));
	}
	@Override
	public Integer getDotCount(int isActive) {
		String hql = "select count(*) from BranchDot a where a.isActive = "+isActive;
		return Integer.parseInt(String.valueOf(indexDataShowDAO.executeQuery(hql)));
	}
	@Override
	public Integer getAreaDotCount() {
		String hql = "select count(*) from AdministrativeArea a where a.parentCode is not null ";
		return Integer.parseInt(String.valueOf(indexDataShowDAO.executeQuery(hql)));
	}
	@Override
	public Integer getCarCount(int type) {
		String hql = "";
		Dict d = null;
		switch (type) {
		//所有车辆
		case 0:
			hql = "select count(*) from Car a ";
			break;
		//运营车辆
		case 1:
			Dict ydDict = DictUtil.getDictByCodes("carBizState", "3");
			hql = "select count(*) from Car a where a.bizState != '"+ydDict.getId()+"'";
			break;
		//保养
		case 2:
			d = DictUtil.getDictByCodes("maintenanceStatus", "03");
			if(d != null){
				hql = "select count(*) from CarMaintenance a where a.status = '" + d.getId() + "'";
			}
			break;
		//维修
		case 3:
			d = DictUtil.getDictByCodes("repairStatus", "1");
			if(d != null){
				hql = "select count(*) from CarRepair a where a.status = '" + d.getId() + "'";
			}
			break;
		//交强险7天到期
		case 4:
			hql = "select count(*) from CarInsurance a where a.trfInsEndDate > '" 
					+ DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") 
					+ "' and a.trfInsEndDate < '"
					+ DateUtil.formatDate(ReportDateUtil.getOtherDay(new Date(),7, 2), "yyyy-MM-dd HH:mm:ss")+"'";
			break;
		//商业险7天到期
		case 5:
			hql = "select count(*) from CarInsurance a where a.commInsEndDate > '" 
					+ DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") 
					+ "' and a.commInsEndDate < '"
					+ DateUtil.formatDate(ReportDateUtil.getOtherDay(new Date(),7, 2), "yyyy-MM-dd HH:mm:ss")+"'";
			break;
		//车辆事故
		case 6:
			//已处理完成的事故
			Dict accetHandleStatus = DictUtil.getDictByCodes("handleStatus", "05");
			hql = "select count(*) from CarAccident a where a.handleStatus != '"+accetHandleStatus.getId()+"'";
			break;
		//车辆违章
		case 7:
			//已处理完成的事故
			Dict voBizStatus = DictUtil.getDictByCodes("violationBizStatus", "01");
			hql = "select count(*) from CarViolation a where a.bizStatus = '"+voBizStatus.getId()+"' ";
			break;
		//近15天处理的违章
		case 8:
			d = DictUtil.getDictByCodes("violationBizStatus", "01");
			if(d != null){
				hql = "select count(*) from CarViolation a where a.bizStatus != '" + d.getId() + "' and ts > '" 
						+ DateUtil.formatDate(ReportDateUtil.getOtherDay(new Date(),-15, 1), "yyyy-MM-dd HH:mm:ss")+"'";
			}
			break;
		//需要保养得车辆
		case 9:
			d = DictUtil.getDictByCodes("maintenanceStatus", "04");
			if(d != null){
				hql = "select count(*) from CarMaintenance b where b.maintenanceTime in (select max(a.maintenanceTime) from CarMaintenance a group by a.carId)"
						+ " and b.maintenanceTime < '" 
						+ DateUtil.formatDate(ReportDateUtil.getOtherDay(ReportDateUtil.getOtherMonth(-3, 2),7, 2), "yyyy-MM-dd HH:mm:ss")+"'";
			}
			
			break;
		//未处理的退款
		case 10:
			hql = "select count(*) from BusinessFlow bf,SubscriberConfirm sc where bf.businessId = sc.id"
					+ " and bf.businessType = 2 and sc.isComplete = 1";
			break;
		//未租借车辆
		case 11:
			Dict wzjDict = DictUtil.getDictByCodes("carBizState", "0");
			hql = "select count(*) from Car a where a.bizState = '"+wzjDict.getId()+"'";
			break;
		//已租借车辆
		case 12:
			Dict yzDict = DictUtil.getDictByCodes("carBizState", "1");
			hql = "select count(*) from Car a where a.bizState = '"+yzDict.getId()+"'";
			break;
		//已下线车辆
		case 13:
			Dict xlzDict = DictUtil.getDictByCodes("carBizState", "2");
			Dict tzsyDict = DictUtil.getDictByCodes("carBizState", "3");
			hql = "select count(*) from Car a where a.bizState = '"+xlzDict.getId()+"' or a.bizState = '"+tzsyDict.getId()+"'";
			break;
		//维修车辆
		case 14:
			xlzDict = DictUtil.getDictByCodes("carBizState", "2");
			hql = "select count(*) from Car a where a.bizState = '"+xlzDict.getId()+"'";
			break;
		//停用车辆
		case 15:
			tzsyDict = DictUtil.getDictByCodes("carBizState", "3");
			hql = "select count(*) from Car a where a.bizState = '"+tzsyDict.getId()+"'";
			break;
		default:
			break;
		}
		if(StringHelper.isNotEmpty(hql)){
			return Integer.parseInt(String.valueOf(indexDataShowDAO.executeQuery(hql)));
		}
		else {
			return 0;
		}
	}
	@Override
	public Integer getOrderCount(String stateDictId,String strategyType,Date startTime,Date endTime) {
		String hql = "select count(*) from Orders a,OrdersDetail b where a.firstDetailNo=b.ordersDetailNo ";
		if(StringHelper.isNotEmpty(stateDictId)){
			hql += " and a.state = '"+stateDictId+"'";
		}
		if(StringHelper.isNotEmpty(strategyType)){
			hql += " and b.typeId = '"+strategyType+"'";
		}
		if(startTime != null){
			hql += " and a.endTime > '"+DateUtil.formatDate(startTime, "yyyy-MM-dd HH:mm:ss")+"'";
		}
		if(endTime != null){
			hql += " and a.endTime < '"+DateUtil.formatDate(endTime, "yyyy-MM-dd HH:mm:ss")+"'";
		}
		return Integer.parseInt(String.valueOf(indexDataShowDAO.executeQuery(hql)));
	}
	@Override
	public Integer getChargeStationCount() {
		String hql = "select count(*) from ChargeStation a";
		return Integer.parseInt(String.valueOf(indexDataShowDAO.executeQuery(hql)));
	}
	

}
