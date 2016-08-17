package com.dearho.cs.sys.action.dictgroup;


import java.util.Date;

import javax.servlet.http.HttpSession;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.DictGroup;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.DictGroupService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:14:43
 */
public class DictGroupAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DictGroupService dictGroupService;
	
	private DictGroup rEntity;
	
	public DictGroupAddAction(){
		super();
		rEntity = new DictGroup();
	}
	
	public String process(){
		try {
			DictGroup dictGroup = dictGroupService.getDictGroupByCode(rEntity.getCode());
			if (dictGroup != null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "字典组已存在！");
				return Action.ERROR;
			}
			rEntity.setCreateTime(new Date());
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user != null){
				rEntity.setCreatorId(user.getId());
			}
			dictGroupService.addDictGroup(rEntity);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, rEntity.getId());
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "字典组保存失败！");
			return Action.ERROR;
		}
	}
	

	public DictGroupService getDictGroupService() {
		return dictGroupService;
	}

	public void setDictGroupService(DictGroupService dictGroupService) {
		this.dictGroupService = dictGroupService;
	}

	public DictGroup getrEntity() {
		return rEntity;
	}

	public void setrEntity(DictGroup rEntity) {
		this.rEntity = rEntity;
	}


}
