package com.dearho.cs.sys.action.dictgroup;

import com.dearho.cs.sys.pojo.DictGroup;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:30:49
 */
public class DictGroupUpdateAction extends DictGroupAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String process(){
		try {
			DictGroup group = getrEntity();
			DictGroup hasDictGroup = getDictGroupService().getDictGroupById(group.getId());
			if(hasDictGroup != null && !hasDictGroup.getId().equals(group.getId())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "字典更新失败！字典编码重复！");
				return Action.ERROR;
			}
			group.setCreateTime(hasDictGroup.getCreateTime());
			group.setCreatorId(hasDictGroup.getCreatorId());
			getDictGroupService().updateDictGroup(group);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, group.getId());
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "字典组更新失败！");
			return Action.ERROR;
		}
	}
}
