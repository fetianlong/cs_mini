package com.dearho.cs.device.action;


import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.DeviceSocket;

public class DeviceChangeIpPortAction extends AbstractAction {
	
	
	
	private static final long serialVersionUID = 1L;
	private String deviceNo;
	public DeviceChangeIpPortAction() {
		super();
	}


	@Override
	public String process() {
		if(deviceNo != null){
			String reStr = DeviceSocket.sendDeviceCommand("ipportSet", deviceNo,"182.92.165.121","10080");
			result = Ajax.JSONResult(0, reStr);
		}
		return SUCCESS;
	}


	public String getDeviceNo() {
		return deviceNo;
	}


	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
}
