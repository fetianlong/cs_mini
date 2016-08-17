package com.dearho.cs.account.action.rechargecard;

/**
 * Copyrigh  (c) dearho Team
 * All rights reserved.
 *
 *This file MemberInfoSearchAction.java creation date:[2015-5-20 上午09:27:25] by liusong
 *http://www.dearho.com
 */


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.account.pojo.RechargeCard;
import com.dearho.cs.account.service.RechargeCardService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;

/**
 * @Author liusong
 * @Description 
 * @Version 2.0,2015-11-17
 *
 */
public class RechargeCardUpdateAction extends AbstractAction {

	private static final long serialVersionUID = 3504119720102723802L;
	private RechargeCardService rechargeCardService;
	private RechargeCard rechargeCard ;
	
	public RechargeCardUpdateAction(){
		super();
		rechargeCard=new RechargeCard();
	}
	@Override
	public String process() {
		
		if(rechargeCard.getAmount()==null ||StringUtils.isEmpty(rechargeCard.getChannel())||rechargeCard.getEndValidTime()==null
				||rechargeCard.getIsValid()==null||StringUtils.isEmpty(rechargeCard.getName())||rechargeCard.getStartValidTime()==null){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"参数不完整");
			return SUCCESS;
		}
		
		if(rechargeCard.getEndValidTime().before(rechargeCard.getStartValidTime())){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED,"请填写正确的起始、结束时间");
			return SUCCESS;
		}
	
		
		if(StringUtils.isEmpty(rechargeCard.getId())){
			
			User operator=(User)getSession().getAttribute(Constants.SESSION_USER);
		
			rechargeCard.setCreateTime(new Date());
			rechargeCard.setTs(new Date());
			rechargeCard.setCreatorName(operator.getName());
			rechargeCard.setCreatorId(operator.getId());
			rechargeCardService.addRechargeCard(rechargeCard);
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"添加成功!");
			
			try {
				Map<String, String> contentMap = new HashMap<String, String>();
				contentMap.put("充值卡金额", rechargeCard.getAmount()+"");
				SystemOperateLogUtil.sysAddOperateLog(rechargeCard.getId(), (User)getSession().getAttribute(Constants.SESSION_USER), SystemOperateLogUtil.MODEL_RECHARGE_CARD,contentMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else {
			RechargeCard card=rechargeCardService.getRechargeCardById(rechargeCard.getId());
			Object beforeObject=rechargeCardService.getRechargeCardById(rechargeCard.getId());
		
			card.setAmount(rechargeCard.getAmount());
			card.setChannel(rechargeCard.getChannel());
			card.setEndValidTime(rechargeCard.getEndValidTime());
			card.setIsValid(rechargeCard.getIsValid());
			card.setName(rechargeCard.getName());
			card.setSortField(rechargeCard.getSortField());
			card.setStartValidTime(rechargeCard.getStartValidTime());
			card.setShowName(rechargeCard.getShowName());
			card.setTs(new Date());
			rechargeCardService.updateRechargeCard(card);
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"修改成功!");
			Object afterObject=rechargeCardService.getRechargeCardById(rechargeCard.getId());
			try {
				SystemOperateLogUtil.sysUpdateOperateLog(beforeObject, afterObject, (User)getSession().getAttribute(Constants.SESSION_USER), SystemOperateLogUtil.MODEL_CALL, card.getName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return SUCCESS;
	}
	public RechargeCardService getRechargeCardService() {
		return rechargeCardService;
	}
	public void setRechargeCardService(RechargeCardService rechargeCardService) {
		this.rechargeCardService = rechargeCardService;
	}
	public RechargeCard getRechargeCard() {
		return rechargeCard;
	}
	public void setRechargeCard(RechargeCard rechargeCard) {
		this.rechargeCard = rechargeCard;
	}

	
	
}
