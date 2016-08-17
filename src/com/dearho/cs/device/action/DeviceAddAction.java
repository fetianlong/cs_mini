package com.dearho.cs.device.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.device.pojo.Device;
import com.dearho.cs.device.service.DeviceService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.opensymphony.webwork.ServletActionContext;

public class DeviceAddAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	private DeviceService deviceService;
	
	private Device device;
	
	
	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public DeviceAddAction() {
		super();
		device=new Device();
	}

	@Override
	public String process() {
		try{
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			if(user!=null){
				device.setCreatorId(user.getId());
			}
			device.setCreateDate(new Date());
			device.setTs(new Date());
			deviceService.addCarSet(device);
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "添加成功");
			
			Map<String, String> contentMap = new HashMap<String, String>();
			contentMap.put("车机编号", device.getSetNo());
			SystemOperateLogUtil.sysAddOperateLog(device.getId(), user, "车机管理", contentMap);
		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "添加失败");
		}
		return SUCCESS;
	}

}
