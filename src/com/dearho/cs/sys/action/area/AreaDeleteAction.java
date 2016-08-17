package com.dearho.cs.sys.action.area;

import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.service.AreaService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:22:43
 */
public class AreaDeleteAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[] checkdel;
	
	private AreaService areaService;
	
	private String id;

	@Override
	public String process() {
		try {
			if(checkdel == null && StringHelper.isNotEmpty(id)){
				checkdel = new String[]{id};
			}
			boolean deleteFlag = true;
			for (String areaId : checkdel) {
				AdministrativeArea parentArea = areaService.searchAreaById(areaId);
				List<AdministrativeArea> parentlist = areaService.getAreasByParentCode(parentArea.getCode());
				if(parentlist != null && parentlist.size() > 0){
					deleteFlag = false;
					continue;
				}
				areaService.deleteArea(new String[]{areaId});
			}
			if(!deleteFlag){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "不能删除，行政区划下面有子项！");
				return Action.ERROR;
			}
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功！");
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

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	
}
