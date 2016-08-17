package com.dearho.cs.feestrategy.action;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.car.service.CarVehicleModelService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.feestrategy.pojo.StrategyBase;
import com.dearho.cs.feestrategy.service.StrategyBaseService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
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
public class StrategyBaseAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	private StrategyBaseService strategyBaseService;
	
	private StrategyBase strategyBase;
	
	private CarVehicleModelService carVehicleModelService;
	
	
	private String id;
	
	private Page<StrategyBase> page = new Page<StrategyBase>();
	
	private String state;
	
	private String targetId;  //用来在选择策略的时候带入
	private String targetName;
	
	public StrategyBaseAction(){
		strategyBase = new StrategyBase();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		return SUCCESS;
	}
	

	public String searchStrategyBase(){
		strategyBase.setIsUsed(1);
		if(targetId != null){
			if(targetId.startsWith("jsz")){
				Dict jszDict = DictUtil.getDictByCodes("strategyBaseType", "jishizu");
				strategyBase.setType(jszDict.getId());
			}
			else if(targetId.startsWith("rz")){
				Dict rzDict = DictUtil.getDictByCodes("strategyBaseType", "rizu");
				strategyBase.setType(rzDict.getId());
			}
			
		}
		page = strategyBaseService.searchStrategyBase(page, strategyBase);
		if(StringHelper.isNotEmpty(state)){
			return state;
		}
		return SUCCESS;
	}
	
	public String checkName(){
		if(strategyBase == null || StringHelper.isEmpty(strategyBase.getName())){
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "");
			return Action.SUCCESS;
		}
		strategyBase = strategyBaseService.getStrategyBaseByName(strategyBase.getName(),1);
		if (strategyBase != null){
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "");
			return Action.SUCCESS;
		}
		result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "");
		return SUCCESS;
	}
	
	public String editStrategyBase(){
		if(StringHelper.isEmpty(id)){
			strategyBase = new StrategyBase();
		}
		else{
			strategyBase = strategyBaseService.searchStrategyBaseById(id);
			if(strategyBase == null){
				strategyBase = new StrategyBase();
			}
		}
		return SUCCESS;
	}
	
	public String addStrategyBase(){
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user == null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请登录！");
				return Action.SUCCESS;
			}
			if(strategyBase == null || StringHelper.isEmpty(strategyBase.getName())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "保存失败，请填写策略名称！");
				return Action.SUCCESS;
			}
			StrategyBase oldStrategy = strategyBaseService.getStrategyBaseByName(strategyBase.getName(),1);
			if (oldStrategy != null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "策略名称已存在，请更换策略名称！");
				return Action.SUCCESS;
			}
			Dict dict = DictUtil.getDictById(strategyBase.getType());
			if(dict == null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "没有策略类型！");
				return Action.SUCCESS;
			}
			if("jishizu".equals(dict.getCode())){
				strategyBase.setIsPrepaidPay(0);
			}
			else{
				strategyBase.setIsPrepaidPay(1);
			}
			strategyBase.setCreateTime(new Date());
			strategyBase.setCreator(user.getId());
			strategyBase.setIsUsed(1);
			strategyBaseService.addStrategyBase(strategyBase);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "保存成功");
			
			//记录日志
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("策略名称", strategyBase.getName());
			SystemOperateLogUtil.sysAddOperateLog(strategyBase.getId(), user, "基础计费策略管理", contentMap);
			
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "保存失败！请注意必填项！");
			return Action.SUCCESS;
		}
	}
	
	public String updateStrategyBase(){
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user == null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请登录！");
				return Action.SUCCESS;
			}
			if(strategyBase == null || StringHelper.isEmpty(strategyBase.getId())){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
				return Action.SUCCESS;
			}
			StrategyBase oldSb = strategyBaseService.searchStrategyBaseById(strategyBase.getId());
			StrategyBase oldstrategyBase = strategyBaseService.getStrategyBaseByName(strategyBase.getName(),1);
			if (oldstrategyBase != null){
				if(!oldstrategyBase.getId().equals(strategyBase.getId())){
					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "策略名称已存在，请更换策略名称！");
					return Action.SUCCESS;
				}
			}
			oldstrategyBase = strategyBaseService.searchStrategyBaseById(strategyBase.getId());
			Dict dict = DictUtil.getDictById(strategyBase.getType());
			if(dict == null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "没有策略类型！");
				return Action.SUCCESS;
			}
			strategyBase.setId(null);
			if("jishizu".equals(dict.getCode())){
				strategyBase.setIsPrepaidPay(0);
			}
			else{
				strategyBase.setIsPrepaidPay(1);
			}
			strategyBase.setCreateTime(oldstrategyBase.getCreateTime());
			strategyBase.setCreator(oldstrategyBase.getCreator());
			strategyBase.setUpdateTime(null);
			strategyBase.setIsUsed(oldstrategyBase.getIsUsed());
			strategyBaseService.addStrategyBase(strategyBase);
			
			oldstrategyBase.setIsUsed(0);
			oldstrategyBase.setUpdateTime(new Date());
			strategyBaseService.updateStrategyBase(oldstrategyBase);
			
			boolean flag = carVehicleModelService.updateModelStrategy(oldstrategyBase,strategyBase);
			if(!flag){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "新策略与车型绑定失败，请手动绑定！");
				return Action.SUCCESS;
			}
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "更新成功");
			
			StrategyBase newSb = strategyBaseService.searchStrategyBaseById(strategyBase.getId());
			SystemOperateLogUtil.sysUpdateOperateLog(oldSb, newSb, user, "基础计费策略管理", newSb.getName());
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败！");
			return Action.SUCCESS;
		}
	}
	public String deleteStrategyBase(){
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user == null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请登录！");
				return Action.SUCCESS;
			}
			if(strategyBase == null || StringHelper.isEmpty(id)){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！");
				return Action.SUCCESS;
			}
			
			String[] checkdel = new String[]{id};
			Map<String, String> contentMap = new HashMap<String, String>();
			for (int i = 0; i < checkdel.length; i++) {
				StrategyBase data = strategyBaseService.searchStrategyBaseById(checkdel[i]);
				contentMap.put("策略名称", data.getName());
			}
			SystemOperateLogUtil.sysDeleteOperateLog(user, "策略管理", contentMap);
			
			strategyBaseService.deleteStrategyBase(checkdel);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！");
			return Action.SUCCESS;
		}
	}
	public String abandonedStrategyBase(){
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user == null){
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "请登录！");
				return Action.SUCCESS;
			}
			if(strategyBase == null || StringHelper.isEmpty(strategyBase.getId())){
				if(StringHelper.isEmpty(id)){
					result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败！");
					return Action.SUCCESS;
				}
				else{
					strategyBase = strategyBaseService.searchStrategyBaseById(id);
				}
			}
			
			strategyBase.setIsUsed(0);
			
			String[] checkdel = new String[]{id};
			Map<String, String> contentMap = new HashMap<String, String>();
			for (int i = 0; i < checkdel.length; i++) {
				StrategyBase data = strategyBaseService.searchStrategyBaseById(checkdel[i]);
				contentMap.put("策略名称", data.getName());
			}
			SystemOperateLogUtil.sysDeleteOperateLog(user, "策略管理", contentMap);
			
			strategyBaseService.updateStrategyBase(strategyBase);
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "废除成功");
			return Action.SUCCESS;
		} catch (Exception e) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "废除失败！");
			return Action.SUCCESS;
		}
	}

	public StrategyBase getStrategyBaseById(String strategyBaseId){
		StrategyBase strategy = strategyBaseService.searchStrategyBaseById(strategyBaseId);
		return strategy;
	}
	
	public String getStrategyNames(String strategyBaseIds){
		String names = "";
		if(StringHelper.isEmpty(strategyBaseIds)){
			return "";
		}
		for (String id : strategyBaseIds.split(";")) {
			StrategyBase strategy = strategyBaseService.searchStrategyBaseById(id);
			names += strategy.getName()+"；";
		}
		
		return names;
	}
	
	public StrategyBaseService getStrategyBaseService() {
		return strategyBaseService;
	}

	public void setStrategyBaseService(StrategyBaseService strategyBaseService) {
		this.strategyBaseService = strategyBaseService;
	}
	public StrategyBase getStrategyBase() {
		return strategyBase;
	}
	public void setStrategyBase(StrategyBase strategyBase) {
		this.strategyBase = strategyBase;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Page<StrategyBase> getPage() {
		return page;
	}
	public void setPage(Page<StrategyBase> page) {
		this.page = page;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public CarVehicleModelService getCarVehicleModelService() {
		return carVehicleModelService;
	}

	public void setCarVehicleModelService(CarVehicleModelService carVehicleModelService) {
		this.carVehicleModelService = carVehicleModelService;
	}
}
