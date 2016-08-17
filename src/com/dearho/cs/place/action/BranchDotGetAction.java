package com.dearho.cs.place.action;

import java.util.List;

import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.AdministrativeArea;
import com.dearho.cs.sys.service.AreaService;
import com.dearho.cs.util.StringHelper;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:31
 */
public class BranchDotGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private BranchDotService branchDotService;
	
	private BranchDot branchDot;
	
	private AreaService areaService;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			branchDot = new BranchDot();
		}else{
			branchDot = branchDotService.getBranchDotById(id);
//			String returnbackDots = branchDot.getReturnbackDot();
//			if(StringHelper.isNotEmpty(returnbackDots)){
//				String[] dots = returnbackDots.split(",");
//				String rtname = "";
//				for (String dot : dots) {
//					if(StringHelper.isNotEmpty(dot)){
//						BranchDot bd = branchDotService.getBranchDotById(dot);
//						if(bd != null){
//							rtname += bd.getName() + "；";
//						}
//					}
//				}
//				branchDot.setReturnbackDotName(rtname);
//			}
		}
		return SUCCESS;
	}
	
	public List<AdministrativeArea> getAreas(){
		return areaService.searchAreaByCode(null);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BranchDotService getBranchDotService() {
		return branchDotService;
	}

	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}

	public BranchDot getBranchDot() {
		return branchDot;
	}

	public void setBranchDot(BranchDot branchDot) {
		this.branchDot = branchDot;
	}

	public AreaService getAreaService() {
		return areaService;
	}
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	
}
