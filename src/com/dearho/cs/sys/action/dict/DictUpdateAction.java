package com.dearho.cs.sys.action.dict;

import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:30:49
 */
public class DictUpdateAction extends DictAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String process(){
		try {
			Dict dict = getrEntity();
			Dict hasDict = getDictService().getDictByGroupIdAndDictCode(dict.getGroupId(), dict.getCode());
			if(hasDict != null && !hasDict.getId().equals(dict.getId())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "字典更新失败！字典编码重复！");
				return Action.ERROR;
			}
			dict.setCreateTime(hasDict.getCreateTime());
			dict.setCreatorId(hasDict.getCreatorId());
			getDictService().updateDict(dict);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, dict.getId());
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "字典更新失败！");
			return Action.ERROR;
		}
	}
}
