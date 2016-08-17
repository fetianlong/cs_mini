package com.dearho.cs.teldintrf.action;



import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.teldintrf.pojo.TeldIntrfStation;
import com.dearho.cs.teldintrf.service.TeldIntrfStationService;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 */
public class TeldIntrfStationAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TeldIntrfStationService teldIntrfStationService;
	
	private Page<TeldIntrfStation> page = new Page<TeldIntrfStation>();
	
	private TeldIntrfStation teldIntrfStation;
	
	private String state;
	
	
	public TeldIntrfStationAction(){
		super();
		teldIntrfStation = new TeldIntrfStation();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		return SUCCESS;
	}
	
	public String teldIntrfStationSearch(){
		try {
			if("map".equals(state)){
				page = teldIntrfStationService.getAllTeldStationPages(page, teldIntrfStation);
			}
			else{
				page = teldIntrfStationService.getTeldStationPages(page, teldIntrfStation);
			}
			
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}

	public TeldIntrfStationService getTeldIntrfStationService() {
		return teldIntrfStationService;
	}

	public void setTeldIntrfStationService(
			TeldIntrfStationService teldIntrfStationService) {
		this.teldIntrfStationService = teldIntrfStationService;
	}

	public Page<TeldIntrfStation> getPage() {
		return page;
	}

	public void setPage(Page<TeldIntrfStation> page) {
		this.page = page;
	}

	public TeldIntrfStation getTeldIntrfStation() {
		return teldIntrfStation;
	}

	public void setTeldIntrfStation(TeldIntrfStation teldIntrfStation) {
		this.teldIntrfStation = teldIntrfStation;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	
	
}
