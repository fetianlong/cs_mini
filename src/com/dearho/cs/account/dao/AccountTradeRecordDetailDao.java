package com.dearho.cs.account.dao;




import java.util.List;

import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.AccountTradeRecordDetail;
import com.dearho.cs.core.db.page.Page;

public interface AccountTradeRecordDetailDao {
	
	void addAccountTradeRecordDetail(AccountTradeRecordDetail accountTradeRecordDetail);
	
	void updateAccountTradeRecordDetail(AccountTradeRecordDetail accountTradeRecordDetail);
	
	AccountTradeRecordDetail getAccountTradeRecordDetailById(String id);
	
	List<AccountTradeRecordDetail> getAccountTradeRecordDetailByBizId(String id);
	
	List<AccountTradeRecordDetail> queryAccountTradeRecordDetailList(String hql) ;
	
	//退款时根据付款id查询充值记录
	AccountTradeRecord getTradeRecordByCarInstanceId(String instanceId);
	
	public Page<AccountTradeRecordDetail> queryAccountTradeRecordDetailByPage(Page<AccountTradeRecordDetail> page, String hql);
	
	public List<AccountTradeRecord> queryTradeRecordByBizAndType(String bizId,Integer type) ;

}
