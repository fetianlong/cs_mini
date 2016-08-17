/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountTradeRecordDaoImpl.java creation date:[2015-6-1 下午02:38:41] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.account.dao.AccountTradeRecordDao;
import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.AccountTradeRecordDetail;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.Subscriber;

/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public class AccountTradeRecordDaoImpl  extends AbstractDaoSupport implements AccountTradeRecordDao {


	@Override
	public void addAccountTradeRecord(AccountTradeRecord accountTradeRecord) {
		addEntity(accountTradeRecord);
	}

	
	@Override
	public AccountTradeRecord getAccountTradeRecordById(String id) {
		
		return get(AccountTradeRecord.class, id);
	}

	@Override
	public Page<AccountTradeRecord> queryAccountTradeRecordByPage(
			Page<AccountTradeRecord> page, String hql) {
		Page<AccountTradeRecord> resultPage=pageCache(AccountTradeRecord.class, page, hql);
		if(resultPage.getmResults()!=null){
			for(int i=0;i<resultPage.getmResults().size();i++){
				AccountTradeRecord accountTradeRecord=(AccountTradeRecord) resultPage.getmResults().get(i);
				accountTradeRecord.setSubscriber(get(Subscriber.class, accountTradeRecord.getSubscriberId()));
			}
		}
		return resultPage;
	}

	@Override
	public void updateAccountTradeRecord(AccountTradeRecord accountTradeRecord) {
		updateEntity(accountTradeRecord);

	}
	
	public List<AccountTradeRecord> queryAccountTradeRecordList(String hql) {
		return getList(AccountTradeRecord.class, queryFList(hql));
	}
	


	@Override
	public List<AccountTradeRecord> queryTradeRecordByTradeOrderNo(String tradeOrderNo,Integer type) {
		String hql="select a.id from AccountTradeRecord a where a.tradeOrderNo ='"+tradeOrderNo+"' and a.type="+type+" order by ts desc";
		return getList(AccountTradeRecord.class, queryFList(hql));
	}
	
	@Override
	public List<AccountTradeRecord> queryTradeRecordBySubOrderNo(String subOrderno,Integer type) {
		String hql="select a.id from AccountTradeRecord a where a.subOrderId ='"+subOrderno+"' and a.type="+type+"  order by ts desc";
		return getList(AccountTradeRecord.class, queryFList(hql));
	}
	
	public List<AccountTradeRecord> queryTradeRecordBySubOrderNo(String subOrderno,Integer type,Integer result) {
		if(result==null){
			result=0;
		}
		String hql="select a.id from AccountTradeRecord a where a.subOrderId ='"+subOrderno+"' and a.type="+type+" and result="+result+"  order by ts desc";
		return getList(AccountTradeRecord.class, queryFList(hql));
	}

	public List<AccountTradeRecord> queryAccountTradeRecordList(AccountTradeRecord accountTradeRecord) {
		StringBuffer hql = new StringBuffer();
		hql.append("select a.id  from AccountTradeRecord a where 1=1 ");
		
		if(accountTradeRecord!=null){
			
			if(accountTradeRecord.getType()!=null){
				hql.append(" and a.type= "+accountTradeRecord.getType());
			}
			if(StringUtils.isNotEmpty(accountTradeRecord.getSubscriberId())){
				hql.append(" and a.subscriberId= '"+accountTradeRecord.getSubscriberId()+"'");
			}
			if(accountTradeRecord.getResult()!=null){
				hql.append(" and a.result= "+accountTradeRecord.getResult());
			}
			if(StringUtils.isNotEmpty(accountTradeRecord.getTradeOrderNo())){
				hql.append(" and a.tradeOrderNo= '"+accountTradeRecord.getTradeOrderNo()+"'");
			}
			if(StringUtils.isNotEmpty(accountTradeRecord.getBizId())){
				hql.append(" and a.bizId= '"+accountTradeRecord.getBizId()+"'");
			}
			if(StringUtils.isNotEmpty(accountTradeRecord.getSubOrderId())){
				hql.append(" and a.subOrderId= '"+accountTradeRecord.getSubOrderId()+"'");
			}
		}
		
	
		hql.append(" order by a.tradeTime desc");

		return getList(AccountTradeRecord.class, queryFList(hql.toString()));
	}

}
