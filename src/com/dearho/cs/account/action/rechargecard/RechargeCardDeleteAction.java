package com.dearho.cs.account.action.rechargecard;

import java.util.HashMap;
import java.util.Map;

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
public class RechargeCardDeleteAction extends AbstractAction  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6934087234917089175L;
	private RechargeCardService rechargeCardService;
	private RechargeCard rechargeCard ;
	
	private String[] checkdel;
	
	public RechargeCardDeleteAction(){
		super();
		rechargeCard=new RechargeCard();
	}
	@Override
	public String process() {

		try{
			if(checkdel!=null&&checkdel.length>0){
				rechargeCardService.deleteRechargeCard( checkdel);
				result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功");
				
				Map<String, String> contentMap = new HashMap<String, String>();
				contentMap.put("充值IDS", checkdel.toString());
				SystemOperateLogUtil.sysDeleteOperateLog(null, (User)getSession().getAttribute(Constants.SESSION_USER),  SystemOperateLogUtil.MODEL_RECHARGE_CARD, contentMap);
			}else{
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "未选择删除项");
				return ERROR;
			}
		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, e.getMessage());
			return ERROR;
		}
		
		return SUCCESS;
	}
	
	public String[] getCheckdel() {
		return checkdel;
	}
	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
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
