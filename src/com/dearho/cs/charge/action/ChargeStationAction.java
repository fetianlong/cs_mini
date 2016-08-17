package com.dearho.cs.charge.action;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.dearho.cs.charge.pojo.ChargeStation;
import com.dearho.cs.charge.service.ChargeStationService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Company;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.CompanyService;
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
public class ChargeStationAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ChargeStationService chargeStationService;
	
	private ChargeStation chargeStation;
	
	private String[] checkdel;
	
	private String id;
	
	private Page<ChargeStation> page = new Page<ChargeStation>();
	
	private CompanyService companyService;
	
	private List<Company> companys;
	
	public ChargeStationAction(){
		super();
		chargeStation = new ChargeStation();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		return SUCCESS;
	}
	
	public String addChargeStation(){
		try {
			List<ChargeStation> list = chargeStationService.searchChargeStationByCode(chargeStation.getCode());
			if (list != null && list.size() > 0){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "充电站编码已存在！");
				return Action.ERROR;
			}
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user != null){
				chargeStation.setCreatorId(user.getId());
			}
			chargeStation.setCreateTime(new Date());
			chargeStation.setTs(new Date());
			chargeStationService.addChargeStation(chargeStation);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"保存成功！");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "保存失败！");
			return Action.ERROR;
		}
	}
	
	public String deleteChareStation(){
		try {
			if(checkdel != null && checkdel.length > 0){
				chargeStationService.deleteChargeStation(checkdel);
			}
			else if(StringHelper.isNotEmpty(id)){
				checkdel = new String[]{id};
				chargeStationService.deleteChargeStation(checkdel);
			}
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功！");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！");
			return Action.ERROR;
		}
	}

	public String toAddOrUpdateChargeStation(){
		if (id == null || id.trim().equals("")){
			chargeStation = new ChargeStation();
		}else{
			ChargeStation eEntity = chargeStationService.searchChargeStationById(id);
			if (eEntity == null){
				chargeStation = new ChargeStation();
			}
			else {
				chargeStation = eEntity;
			}
			
		}
		return SUCCESS;
	}
	
	public String searchChargeStation(){
		try {
			page = chargeStationService.searchChargeStation(page, chargeStation);
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}
	
	public String updateChargeStation(){
		try {
			List<ChargeStation> hasChargeStations = getChargeStationService().searchChargeStationByCode(getChargeStation().getCode());
			if(hasChargeStations != null && hasChargeStations.size() > 0 && !hasChargeStations.get(0).getId().equals(getChargeStation().getId())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！编码重复！");
				return Action.ERROR;
			}
			ChargeStation updateChargeStation = getChargeStation();
			updateChargeStation.setUpdateTime(new Date());
			updateChargeStation.setTs(new Date());
			getChargeStationService().updateChargeStation(updateChargeStation);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return Action.ERROR;
		}
	}


	public List<Company> getCompanys(String typeCode){
		return companyService.searchCompanyByBizType(typeCode);
	}
	
	
	public String getCompNameById(String id){
		if(StringHelper.isEmpty(id)){
			return "";
		}
		Company company = companyService.searchCompanyById(id);
		return company.getName();
	}
	
	
	public ChargeStationService getChargeStationService() {
		return chargeStationService;
	}

	public void setChargeStationService(ChargeStationService chargeStationService) {
		this.chargeStationService = chargeStationService;
	}

	public ChargeStation getChargeStation() {
		return chargeStation;
	}

	public void setChargeStation(ChargeStation chargeStation) {
		this.chargeStation = chargeStation;
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

	public Page<ChargeStation> getPage() {
		return page;
	}

	public void setPage(Page<ChargeStation> page) {
		this.page = page;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public List<Company> getCompanys() {
		return companys;
	}

	public void setCompanys(List<Company> companys) {
		this.companys = companys;
	}
	

}
