package com.dearho.cs.place.action;

import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:22:43
 */
public class BranchDotDeleteAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] checkdel;
	
	private BranchDotService branchDotService;
	
	private String id;
	

	@Override
	public String process() {
		try {
			if(checkdel != null && checkdel.length > 0 ){
				branchDotService.deleteBranchDot(checkdel);
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功！");
			}
			else if(StringHelper.isNotEmpty(id)){
				checkdel = new String[]{id};
				branchDotService.deleteBranchDot(checkdel);
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功！");
			}
			
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！");
			return Action.ERROR;
		}
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String[] getCheckdel() {
		return checkdel;
	}

	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
	}

	public BranchDotService getBranchDotService() {
		return branchDotService;
	}

	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}

	
}
