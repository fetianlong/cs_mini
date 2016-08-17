/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file AccountService.java creation date:[2015-6-1 下午02:47:34] by liusong
 *http://www.dearho.com
 */
package com.dearho.cs.account.service;

import java.util.List;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.AccountCardInstance;
import com.dearho.cs.account.pojo.AccountPaymentAccount;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.pojo.User;


/**
 * @Author liusong
 * @Description 
 * @Version 1.0,2015-6-1
 *
 */
public interface AccountService {
	

	
	
	
	
	/**
	 * 账号详情
	 * @Title: getAccoutDetail
	 * @Description:
	 * @param id
	 * @return
	 * @throws
	 */
	public Account getAccoutDetail(String id);
	
	
	
	
	
	public Page<Subscriber> queryAccountByPage(Page<Subscriber> page, Subscriber subscriber);
	/**
	* 查询最近几天操作的账户
	* @param page
	* @param days
	* @param typeRefund
	* @return
	* @return Page<Subscriber>
	*/
	public Page<Subscriber> queryLastOperate(Page<Subscriber> page,int days, Integer typeRefund);
	
	/**
	 * 代充
	 * @param rechargeCardId
	 * @param subscriberId
	 * @param operatorId
	 */
	public void commissionRechargeCard(String rechargeCardId,Double amount, String subscriberId, User operator,String payType,String remark) throws Exception ;
	
	
	
	public void addAccountPaymentAccount(AccountPaymentAccount accountPaymentAccount);





	public void commissionCutPayment(Integer customAmount, String id, User operator, String remark)throws Exception;





	public List<AccountCardInstance> queryCardInstanceByIds(String ids);
	
	
}
