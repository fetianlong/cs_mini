package com.dearho.cs.sys.action.dict;


import java.util.Date;

import javax.servlet.http.HttpSession;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.DictService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:14:43
 */
public class DictAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DictService dictService;
	
	private Dict rEntity;
	
	public DictAddAction(){
		super();
		rEntity = new Dict();
	}
	
	public String process(){
		try {
			Dict dict = dictService.getDictByGroupIdAndDictCode(rEntity.getGroupId(), rEntity.getCode());
			if (dict != null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "字典已存在！");
				return Action.ERROR;
			}
			rEntity.setIsSystem(0);
			rEntity.setCreateTime(new Date());
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user != null){
				rEntity.setCreatorId(user.getId());
			}
			
			dictService.addDict(rEntity);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, rEntity.getId());
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "字典保存失败！");
			return Action.ERROR;
		}
	}
	

	public DictService getDictService() {
		return dictService;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

	public Dict getrEntity() {
		return rEntity;
	}

	public void setrEntity(Dict rEntity) {
		rEntity.setIsSystem(0);
		this.rEntity = rEntity;
	}


}
