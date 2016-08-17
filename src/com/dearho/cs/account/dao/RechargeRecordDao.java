package com.dearho.cs.account.dao;




import java.util.List;

import com.dearho.cs.account.pojo.RechargeRecord;
import com.dearho.cs.core.db.page.Page;

public interface RechargeRecordDao  {
	
	void addRechargeRecord(RechargeRecord rechargeRecord);
	
	void updateRechargeRecord(RechargeRecord rechargeRecord);
	
	Page<RechargeRecord> queryRechargeRecordByPage(Page<RechargeRecord> page,String hql);
	
	RechargeRecord getRechargeRecordById(String id);
	
	List<RechargeRecord> queryRechargeRecordList(String hql) ;

}
