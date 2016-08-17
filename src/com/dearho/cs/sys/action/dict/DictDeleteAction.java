package com.dearho.cs.sys.action.dict;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.service.DictService;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:22:43
 */
public class DictDeleteAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] checkdel;
	
	private DictService dictService;
	
	private String id;

	@Override
	public String process() {
		try {
			if(checkdel != null && checkdel.length > 0){
				for (int i = 0; i < checkdel.length; i++) {
					Dict dict = DictUtil.getDictById(checkdel[i]);
					if(dict.getIsSystem() == 1){
						result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！字典项【"+dict.getCnName()+"】是系统预制字段，不能删除！");
						return Action.ERROR;
					}
				}
				dictService.deleteDict(checkdel);
			}
			else if(StringHelper.isNotEmpty(id)){
				Dict dict = DictUtil.getDictById(id);
				if(dict.getIsSystem() == 1){
					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！字典项【"+dict.getCnName()+"】是系统预制字段，不能删除！");
					return Action.ERROR;
				}
				checkdel = new String[]{id};
				dictService.deleteDict(checkdel);
			}
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "字典删除成功！");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！");
			return Action.ERROR;
		}
	}

	public String[] getCheckdel() {
		return checkdel;
	}

	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
	}

	public DictService getDictService() {
		return dictService;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	
}
