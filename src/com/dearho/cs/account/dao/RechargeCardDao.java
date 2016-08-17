package com.dearho.cs.account.dao;




import java.util.List;

import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.core.db.page.Page;

public interface RechargeCardDao  {
	
	void addRechargeCard(RechargeCard rechargeCard);
	
	void updateRechargeCard(RechargeCard rechargeCard);
	
	Page<RechargeCard> queryRechargeCardByPage(Page<RechargeCard> page,String hql);
	
	RechargeCard getRechargeCardById(String id);
	
	public List<RechargeCard> queryRechargeCardList(String hql) ;

	void deleteRechargeCard(String queryString, String[] checkdel);

	
	

}
