package com.dearho.cs.device.action;

import java.util.Date;

import javax.servlet.http.HttpSession;

import com.dearho.cs.device.pojo.Device;
import com.dearho.cs.device.service.DeviceService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;

public class DeviceUpdateAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	private Device device;
	private DeviceService deviceService;
	
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public DeviceUpdateAction() {
		super();
		device=new Device();
	}

	@Override
	public String process() {
		try{
			Device beforObj = deviceService.queryCarSetById(device.getId());
			device.setTs(new Date());
			deviceService.updateCarSet(device);
			Device afterObj = deviceService.queryCarSetById(device.getId());
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "更新成功");
			
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			SystemOperateLogUtil.sysUpdateOperateLog(beforObj, afterObj, user, "车机管理", afterObj.getSetNo());
		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "更新失败");
			return ERROR;
		}
		return SUCCESS;
	}

}
