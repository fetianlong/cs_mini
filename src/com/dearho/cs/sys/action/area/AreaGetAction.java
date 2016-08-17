package com.dearho.cs.sys.action.area;

import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.service.AreaService;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:31
 */
public class AreaGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private AreaService areaService;
	
	private AdministrativeArea area;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			area = new AdministrativeArea();
		}else{
			AdministrativeArea eEntity = areaService.searchAreaById(id);
			if (eEntity == null){
				area = new AdministrativeArea();
			}
			else {
				area = eEntity;
			}
			
		}
		return SUCCESS;
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

	public AdministrativeArea getArea() {
		return area;
	}

	public void setArea(AdministrativeArea area) {
		this.area = area;
	}

	
}
