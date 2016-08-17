package com.dearho.cs.place.action;


import java.util.Date;

import javax.servlet.http.HttpSession;

import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:14:43
 */
public class BranchDotAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BranchDotService branchDotService;
	private BranchDot branchDot;
	
	public BranchDotAddAction(){
		super();
		branchDot = new BranchDot();
	}
	
	public String process(){
		try {
			BranchDot branchDot2 = branchDotService.getBranchDotByCode(branchDot.getCode());
			if (branchDot2 != null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "网点编码重复！");
				return Action.ERROR;
			}
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			branchDot.setCreateTime(new Date());
			branchDot.setCreatorId(user.getId());
			branchDot.setTs(new Date());
			branchDotService.addBranchDot(branchDot);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "保存成功！");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "保存失败！");
			return Action.ERROR;
		}
	}

	public BranchDotService getBranchDotService() {
		return branchDotService;
	}

	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}

	public BranchDot getBranchDot() {
		return branchDot;
	}

	public void setBranchDot(BranchDot branchDot) {
		this.branchDot = branchDot;
	}


}
