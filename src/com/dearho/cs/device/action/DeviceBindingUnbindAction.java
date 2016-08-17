package com.dearho.cs.device.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.webwork.ServletActionContext;

public class DeviceBindingUnbindAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private DeviceBindingService deviceBindingService;
	private DeviceBinding deviceBinding;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DeviceBindingService getDeviceBindingService() {
		return deviceBindingService;
	}

	public void setDeviceBindingService(DeviceBindingService deviceBindingService) {
		this.deviceBindingService = deviceBindingService;
	}

	public DeviceBinding getDeviceBinding() {
		return deviceBinding;
	}

	public void setDeviceBinding(DeviceBinding deviceBinding) {
		this.deviceBinding = deviceBinding;
	}
	
	public DeviceBindingUnbindAction() {
		super();
		deviceBinding=new DeviceBinding();
	}

	@Override
	public String process() {
		try{
			deviceBinding = deviceBindingService.queryCarBindById(id);
			if(deviceBinding!=null&&StringHelper.isNotEmpty(deviceBinding.getId())){
				deviceBinding.setBindType(0);
				deviceBindingService.updateCarBind(deviceBinding);
				result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "解绑成功");
				
				HttpSession session = ServletActionContext.getRequest().getSession();
				User user=(User) session.getAttribute("user");
				Map<String, String> contentMap = new HashMap<String, String>();
				contentMap.put("车机编号", deviceBinding.getDeviceNo());
				contentMap.put("车牌号码", deviceBinding.getCarPlateNo());
				SystemOperateLogUtil.sysDeleteOperateLog(deviceBinding.getId(), user, "车机绑定管理", contentMap);
				return SUCCESS;
			}else{
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "解绑失败");
				return ERROR;
			}
		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "解绑失败");
			return ERROR;
		}
	}

}
