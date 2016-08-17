package com.dearho.cs.sys.action.area;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.AreaService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:14:43
 */
public class AreaAddAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AreaService areaService;
	
	private AdministrativeArea area;
	
	public AreaAddAction(){
		super();
		area = new AdministrativeArea();
	}
	
	public String process(){
		try {
			List<AdministrativeArea> list = areaService.searchAreaByCode(area.getCode());
			if (list != null && list.size() > 0){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "行政区域编码已存在！");
				return Action.ERROR;
			}
			if(!StringHelper.isRealNull(area.getParentCode())){
				List<AdministrativeArea> parentlist = areaService.searchAreaByCode(area.getParentCode());
				if(parentlist == null || parentlist.size() <= 0){
					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "父级编码不存在！");
					return Action.ERROR;
				}
			}
			
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user != null){
				area.setCreatorId(user.getId());
			}
			area.setCreateTime(new Date());
			area.setTs(new Date());
			areaService.addArea(area);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "保存成功！");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "保存失败！");
			return Action.ERROR;
		}
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public AdministrativeArea getArea() {
		return area;
	}

	public void setArea(AdministrativeArea area) {
		this.area = area;
	}
	



}
