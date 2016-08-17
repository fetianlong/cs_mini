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

import com.dearho.cs.account.pojo.RechargeRecord;
import com.dearho.cs.core.db.page.Page;


/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-16
 *
 */
public interface RechargeRecordService {
	public Page<RechargeRecord> queryRechargeRecordByPage(Page<RechargeRecord> page,RechargeRecord rechargeRecord,Date fromDate,Date toDate) ;
	public RechargeRecord       getRechargeRecordById(String id);
	public void addRechargeRecord(RechargeRecord rechargeRecord);
	public void updateRechargeRecord(RechargeRecord rechargeRecord);
	public List<RechargeRecord> queryRechargeRecord(RechargeRecord rechargeRecord);

}
