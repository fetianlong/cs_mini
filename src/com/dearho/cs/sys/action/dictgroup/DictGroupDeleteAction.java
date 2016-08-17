package com.dearho.cs.sys.action.dictgroup;

import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.DictGroup;
import com.dearho.cs.sys.service.DictGroupService;
import com.dearho.cs.sys.service.DictService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:22:43
 */
public class DictGroupDeleteAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] checkdel;
	private String id;
	
	private DictGroupService dictGroupService;
	
	private DictService dictService;

	@Override
	public String process() {
		try {
			if(checkdel != null && checkdel.length > 0){
				for (int i = 0; i < checkdel.length; i++) {
					List<Dict> dictHas = dictService.getDictsByGroupId(checkdel[i]);
					if(dictHas != null && dictHas.size() > 0){
						DictGroup dictGroupHas = dictGroupService.getDictGroupById(checkdel[i]);
						result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！字典组【"+dictGroupHas.getRemark()+"】下有字典项，不能删除！");
						return Action.ERROR;
					}
				}
				dictGroupService.deleteDictGroup(checkdel);
			}
			else if(StringHelper.isNotEmpty(id)){
				List<Dict> dictHas = dictService.getDictsByGroupId(id);
				if(dictHas != null && dictHas.size() > 0){
					DictGroup dictGroupHas = dictGroupService.getDictGroupById(id);
					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！字典组【"+dictGroupHas.getRemark()+"】下有字典项，不能删除！");
					return Action.ERROR;
				}
				dictGroupService.deleteDictGroupById(id);
			}
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "字典组删除成功！");
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

	public DictGroupService getDictGroupService() {
		return dictGroupService;
	}

	public void setDictGroupService(DictGroupService dictGroupService) {
		this.dictGroupService = dictGroupService;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}
	public DictService getDictService() {
		return dictService;
	}
	
}
