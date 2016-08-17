package com.dearho.cs.sys.action.area;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.service.AreaService;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class AreaSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private AreaService areaService;
	
	private Page<AdministrativeArea> page = new Page<AdministrativeArea>();
	
	private AdministrativeArea area;
	
	
	public AreaSearchAction(){
		super();
		area = new AdministrativeArea();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			page = areaService.searchArea(page, area);
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public Page<AdministrativeArea> getPage() {
		return page;
	}

	public void setPage(Page<AdministrativeArea> page) {
		this.page = page;
	}

	public AdministrativeArea getArea() {
		return area;
	}

	public void setArea(AdministrativeArea area) {
		this.area = area;
	}

	
}
