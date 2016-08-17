package com.dearho.cs.account.dao;




import java.util.List;

import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.core.db.page.Page;

public interface AccountTradeRecordDao {
	
	void addAccountTradeRecord(AccountTradeRecord accountTradeRecord);
	
	void updateAccountTradeRecord(AccountTradeRecord accountTradeRecord);
	
	Page<AccountTradeRecord> queryAccountTradeRecordByPage(Page<AccountTradeRecord> page,String hql);
	
	AccountTradeRecord getAccountTradeRecordById(String id);
	
	public List<AccountTradeRecord> queryAccountTradeRecordList(String hql) ;

	List<AccountTradeRecord> queryTradeRecordByTradeOrderNo(String tradeOrderNo,Integer type);
	
	List<AccountTradeRecord> queryTradeRecordBySubOrderNo(String subOrderno,Integer type);
	
	List<AccountTradeRecord> queryAccountTradeRecordList(AccountTradeRecord accountTradeRecord);
	
	public List<AccountTradeRecord> queryTradeRecordBySubOrderNo(String subOrderno,Integer type,Integer result) ;


}
