package com.dearho.cs.subscriber.service.impl;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.dao.AccountDao;
import com.dearho.cs.advice.dao.InformDao;
import com.dearho.cs.advice.dao.InformRecordDao;
import com.dearho.cs.advice.pojo.Inform;
import com.dearho.cs.advice.pojo.InformRecord;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.dao.SubscriberConfirmDao;
import com.dearho.cs.subscriber.dao.SubscriberDao;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.pojo.SubscriberConfirm;
import com.dearho.cs.subscriber.service.SubscriberConfirmService;
import com.dearho.cs.sys.dao.BusinessFlowDAO;
import com.dearho.cs.sys.pojo.BusinessFlow;
import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SMSUtil;
import com.dearho.cs.util.StringHelper;

public class SubscriberConfirmServiceImpl implements SubscriberConfirmService {

	public InformDao getInformDao() {
		return informDao;
	}




	public void setInformDao(InformDao informDao) {
		this.informDao = informDao;
	}




	public InformRecordDao getInformRecordDao() {
		return informRecordDao;
	}




	public void setInformRecordDao(InformRecordDao informRecordDao) {
		this.informRecordDao = informRecordDao;
	}




	private SubscriberConfirmDao subscriberConfirmDao;
	private SubscriberDao subscriberDao;
	private BusinessFlowDAO businessFlowDAO;
	private AccountDao accountDao;
	private InformDao informDao;
	private InformRecordDao informRecordDao;
	
	@Override
	public  void confirm(SubscriberConfirm subscriberConfirm,User operator) throws Exception{
		if(subscriberConfirm==null ){
			throw new RuntimeException("审核入参为空!");
		}
		if(subscriberConfirm.getId()==null ){
			throw new RuntimeException("审核ID不能为空!");
		}
		if(subscriberConfirm.getSubscriberId()==null ){
			throw new RuntimeException("审核会员不能为空!");
		}
		if(subscriberConfirm.getResult()==null ){
			throw new RuntimeException("审核结果不能为空!");
		}
		
		
		//审核成功
		if(SubscriberConfirm.RESULT_SUCCESS.equals(subscriberConfirm.getResult())){
			
			SubscriberConfirm confirm=subscriberConfirmDao.getSubscriberConfirm(subscriberConfirm.getId());
			confirm.setIsComplete(SubscriberConfirm.IS_COMPLETE_TRUE);
			confirm.setResult(SubscriberConfirm.RESULT_SUCCESS);
			subscriberConfirmDao.updateSubscriberConfirm(confirm);
			
			Subscriber subscriber=subscriberDao.querySubscriberById(confirm.getSubscriberId());
			
			//--15位身份证号码：第7、8位为出生年份(两位数)，第9、10位为出生月份，第11、12位代表出生日期，第15位代表性别，奇数为男，偶数为女。  
			//--18位身份证号码：第7、8、9、10位为出生年份(四位数)，第11、第12位为出生月份，第13、14位代表出生日期，第17位代表性别，奇数为男，偶数为女。  
			if(!StringUtils.isEmpty(subscriber.getDrivingLicenseNo())){
				if(subscriber.getDrivingLicenseNo().length()==15){
					String sex=subscriber.getDrivingLicenseNo().substring(14, 15);
					if(Integer.parseInt(sex)%2==0){
						subscriber.setSex(Subscriber.SEX_WOMAN);
					}else{
						subscriber.setSex(Subscriber.SEX_MAN);
					}
				}else if(subscriber.getDrivingLicenseNo().length()==18){
					String sex=subscriber.getDrivingLicenseNo().substring(16, 17);
					if(Integer.parseInt(sex)%2==0){
						subscriber.setSex(Subscriber.SEX_WOMAN);
					}else{
						subscriber.setSex(Subscriber.SEX_MAN);
					}
				}
			} 
			
			subscriber.setState(Subscriber.STATE_NORMAL);
			subscriberDao.updateSubscriber(subscriber);
			
			BusinessFlow businessFlow=businessFlowDAO.getBusinessFlowByConfirmId(confirm.getId());
			businessFlow.setReviewerId(operator.getId());
			businessFlow.setReviewTime(new Date());
			businessFlow.setResult(BusinessFlow.RESULT_SUCCESS);
			businessFlow.setResultDesc("审核通过");
			businessFlowDAO.updateBusinessFlow(businessFlow);
			
			//String smsMessage= "尊敬的"+subscriber.getName()+"您好，您的华泰会员身份审核已通过，会员卡已经通过快递方式寄出，请您注意查收。";
			String smsMessage= "尊敬的"+subscriber.getName()+"您好，您的会员身份审核已通过。";
			String phoneNo=subscriber.getPhoneNo();
			//添加通知记录
			Inform inform = new Inform();
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			inform.setId(id);
			inform.setTs(new Date());
			inform.setInformContent(smsMessage);
			inform.setInformType(0);
			inform.setInformSendType(2);
			informDao.addInform(inform);
			System.out.println(inform.getId());
			InformRecord record  =new InformRecord(id,subscriber.getId(),0,subscriber.getTs());
			informRecordDao.addInformRecord(record);
			//发送短信
			SMSUtil.sendSMS(phoneNo, smsMessage,SMSRecord.TYPE_REGISTER);
			
		//审核失败
		}else{
			
			SubscriberConfirm confirm=subscriberConfirmDao.getSubscriberConfirm(subscriberConfirm.getId());
			confirm.setIsComplete(SubscriberConfirm.IS_COMPLETE_TRUE);
			confirm.setResult(SubscriberConfirm.RESULT_FAIL);
			subscriberConfirmDao.updateSubscriberConfirm(confirm);
			
			Subscriber subscriber=subscriberDao.querySubscriberById(confirm.getSubscriberId());
			subscriber.setState(Subscriber.STATE_NO_CONFIRMED);
			subscriberDao.updateSubscriber(subscriber);
			
			BusinessFlow businessFlow=businessFlowDAO.getBusinessFlowByConfirmId(confirm.getId());
			businessFlow.setReviewerId(operator.getId());
			businessFlow.setReviewTime(new Date());
			businessFlow.setResult(BusinessFlow.RESULT_FAIL);
			businessFlow.setResultDesc(subscriberConfirm.getResultDesc());
			businessFlowDAO.updateBusinessFlow(businessFlow);
			
			
			StringBuffer smsMessageBuffer = new StringBuffer();
			smsMessageBuffer.append("尊敬的"+subscriber.getName()+"您好，您的会员身份审核未通过，原因:");
			
			String[] failResultArr=subscriberConfirm.getResultDesc().split(",");
			for(int i=0;i<failResultArr.length;i++){
				smsMessageBuffer.append(DictUtil.getCnNameByCode("6", failResultArr[i].trim()));
				smsMessageBuffer.append("、");
			}
			
			String smsMessage=smsMessageBuffer.substring(0, smsMessageBuffer.length()-1);
			smsMessage=smsMessage+"。";
			
			String phoneNo=subscriber.getPhoneNo();
			
			//添加通知记录
			Inform inform = new Inform();
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			inform.setId(id);
			inform.setTs(new Date());
			inform.setInformContent(smsMessage);
			inform.setInformType(0);
			inform.setInformSendType(2);
			informDao.addInform(inform);
			
			InformRecord record  =new InformRecord(id,subscriber.getId(),0,subscriber.getTs());
			informRecordDao.addInformRecord(record);
			//发送短信
			SMSUtil.sendSMS(phoneNo, smsMessage,SMSRecord.TYPE_CONFIRM);
			
		}
		
	}

	
	
	
	@Override
	public SubscriberConfirm querySubscriberConfirmById(String id) {
		SubscriberConfirm subscriberConfirm=subscriberConfirmDao.getSubscriberConfirm(id);
		Subscriber subscriber=subscriberDao.querySubscriberById(subscriberConfirm.getSubscriberId());
		subscriber.setAccount(accountDao.getAccountById(subscriberConfirm.getSubscriberId()));
		subscriberConfirm.setSubscriber(subscriber);
		subscriberConfirm.setBusinessFlow(businessFlowDAO.getBusinessFlowByConfirmId(subscriberConfirm.getId()));
		return  subscriberConfirm;
	}

	@Override
	public Page<SubscriberConfirm> querySubscriberConfirmByPage(Page<SubscriberConfirm> page,
			SubscriberConfirm subscriberConfirm) {
		StringBuffer hql = new StringBuffer();
		hql.append("select a.id from SubscriberConfirm a ,BusinessFlow b ,Subscriber s where a.subscriberId=s.id and a.id=b.confirmId ");
		
		if(subscriberConfirm!=null){
			
			if(subscriberConfirm.getIsComplete()!=null){
				hql.append(" and a.isComplete=").append(subscriberConfirm.getIsComplete());
			}
			
			if(subscriberConfirm.getSubscriber()!=null){
				if(!StringUtils.isEmpty(subscriberConfirm.getSubscriber().getPhoneNo())){
					hql.append("and s.phoneNo like '%").append(subscriberConfirm.getSubscriber().getPhoneNo()).append("%'");
				}
			}
			if(subscriberConfirm.getBusinessFlow()!=null){
				if(subscriberConfirm.getBusinessFlow().getResult()!=null){
					hql.append("and b.result=").append(subscriberConfirm.getBusinessFlow().getResult());
				}
				if(subscriberConfirm.getBusinessFlow().getBusinessType()!=null){
					hql.append("and b.businessType=").append(subscriberConfirm.getBusinessFlow().getBusinessType());
				}
			}
			
		}
		hql.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by b.applyTime desc " : page.getOrderByString());
		
		
		return subscriberConfirmDao.queryPageSubscriberConfirm(page, hql.toString());
		
	}
	

	
	
	@Override
	public List<SubscriberConfirm> querySubscriberConfirmBySubscriberId(String subscriberId) {
		String hql="select id from SubscriberConfirm where subscriberId='"+subscriberId+"'";
		return subscriberConfirmDao.querySubscriberConfirmList(hql);
	}

	public void update(SubscriberConfirm subscriberConfirm) {
		subscriberConfirmDao.updateSubscriberConfirm(subscriberConfirm);
	}
	
	
	//get and set


	public void setSubscriberConfirmDao(SubscriberConfirmDao subscriberConfirmDao) {
		this.subscriberConfirmDao = subscriberConfirmDao;
	}

	public void setSubscriberDao(SubscriberDao subscriberDao) {
		this.subscriberDao = subscriberDao;
	}

	public void setBusinessFlowDAO(BusinessFlowDAO businessFlowDAO) {
		this.businessFlowDAO = businessFlowDAO;
	}




	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}




	

}
