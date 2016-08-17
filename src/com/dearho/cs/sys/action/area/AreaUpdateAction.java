package com.dearho.cs.sys.action.area;

import java.util.Date;
import java.util.List;

import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:30:49
 */
public class AreaUpdateAction extends AreaAddAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String process(){
		try {
			List<AdministrativeArea> hasAreas = getAreaService().searchAreaByCode(getArea().getCode());
			if(hasAreas != null && hasAreas.size() > 0 && !hasAreas.get(0).getId().equals(getArea().getId())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！编码重复！");
				return Action.ERROR;
			}
			if(!StringHelper.isRealNull(getArea().getParentCode())){
			List<AdministrativeArea> parentlist = getAreaService().searchAreaByCode(getArea().getParentCode());
				if(parentlist == null || parentlist.size() <= 0){
					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "父级编码不存在！");
					return Action.ERROR;
				}
			}
			AdministrativeArea updateArea = getArea();
			updateArea.setUpdateTime(new Date());
			updateArea.setTs(new Date());
			getAreaService().updateArea(updateArea);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return Action.ERROR;
		}
	}
}
