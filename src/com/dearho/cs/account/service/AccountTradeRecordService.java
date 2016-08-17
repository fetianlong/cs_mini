/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountService.java creation date:[2015-6-1 下午02:47:34] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.service;

import java.util.Date;
import java.util.List;

import com.dearho.cs.account.pojo.AccountTradeRecord;
import com.dearho.cs.account.pojo.AccountTradeRecordDetail;
import com.dearho.cs.core.db.page.Page;


/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public interface AccountTradeRecordService {

	public Page<AccountTradeRecord> queryAccountTradeRecordByPage(Page<AccountTradeRecord> page,AccountTradeRecord accountTradeRecord,Date fromDate,Date toDate) ;
	public AccountTradeRecord       getAccountTradeRecordById(String id);
	public  List<AccountTradeRecordDetail>  getAccountTradeRecordDetailByBiz(String recordId) ;
	public  Page<AccountTradeRecordDetail> queryAccountTradeRecordDetailByPage(Page<AccountTradeRecordDetail> page,AccountTradeRecordDetail accountTradeRecordDetail);
	

}
