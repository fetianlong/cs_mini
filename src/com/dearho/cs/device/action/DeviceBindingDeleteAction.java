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
import com.opensymphony.webwork.ServletActionContext;

public class DeviceBindingDeleteAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	private DeviceBindingService deviceBindingService;
	
	private String[] checkdel;
	
	public DeviceBindingService getDeviceBindingService() {
		return deviceBindingService;
	}

	public void setDeviceBindingService(DeviceBindingService deviceBindingService) {
		this.deviceBindingService = deviceBindingService;
	}

	public String[] getCheckdel() {
		return checkdel;
	}

	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
	}
	@Override
	public String process() {
		try{
			if(checkdel!=null&&checkdel.length>0){
				HttpSession session = ServletActionContext.getRequest().getSession();
				User user=(User) session.getAttribute("user");
				Map<String, String> contentMap = new HashMap<String, String>();
				
				for (int i = 0; i < checkdel.length; i++) {
					DeviceBinding dbinding = deviceBindingService.queryCarBindById(checkdel[i]);
					contentMap.put("车机编号", dbinding.getDeviceNo());
					contentMap.put("车牌号码", dbinding.getCarPlateNo());
				}
				SystemOperateLogUtil.sysDeleteOperateLog(user, "车机绑定管理", contentMap);
				
				deviceBindingService.deleteCarBindByIds(checkdel);
				result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功");
			}else{
				result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败");
				return ERROR;
			}
		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败");
			return ERROR;
		}
			
	
		return SUCCESS;
	}

}
