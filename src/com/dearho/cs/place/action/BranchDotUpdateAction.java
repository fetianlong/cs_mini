package com.dearho.cs.place.action;

import java.util.Date;

import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:30:49
 */
public class BranchDotUpdateAction extends BranchDotAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String process(){
		try {
			BranchDot hasDot = getBranchDotService().getBranchDotByCode(getBranchDot().getCode());
			if(hasDot != null && !hasDot.getId().equals(getBranchDot().getId())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！编码重复！");
				return Action.ERROR;
			}
			BranchDot updateDot = getBranchDot();
			updateDot.setUpdateTime(new Date());
			updateDot.setCreateTime(hasDot.getCreateTime());
			updateDot.setCreatorId(hasDot.getCreatorId());
			updateDot.setTs(new Date());
			getBranchDotService().updateBranchDot(updateDot);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "更新成功");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return Action.ERROR;
		}
	}
}
