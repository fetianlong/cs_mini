package com.dearho.cs.device.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.dearho.cs.device.pojo.Device;
import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.device.service.DeviceService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.webwork.ServletActionContext;

public class DeviceDeleteAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	private String[] checkdel;
	private DeviceService deviceService;
	private DeviceBindingService deviceBindingService;
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String[] getCheckdel() {
		return checkdel;
	}

	public DeviceBindingService getDeviceBindingService() {
		return deviceBindingService;
	}

	public void setDeviceBindingService(DeviceBindingService deviceBindingService) {
		this.deviceBindingService = deviceBindingService;
	}

	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
	}


	public DeviceService getDeviceService() {
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	@Override
	public String process() {
		try{
			if(StringHelper.isNotEmpty(id)){
				checkdel = new String[]{id};
			}
			for (int i = 0; i <checkdel.length; i++) {
				List<DeviceBinding> list=deviceBindingService.queryBindByDeviceId(checkdel[i],null);
				if(list!=null&&list.size()>0){
					result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "已绑定或使用过的车机不允许删除！");
					return ERROR;
				}
			}
			
			HttpSession session = ServletActionContext.getRequest().getSession();
			User user=(User) session.getAttribute("user");
			Map<String, String> contentMap = new HashMap<String, String>();
			for (int i = 0; i < checkdel.length; i++) {
				Device device = deviceService.queryCarSetById(checkdel[i]);
				contentMap.put("车机编号", device.getSetNo());
			}
			SystemOperateLogUtil.sysDeleteOperateLog(user, "车机管理", contentMap);
			
			deviceService.deleteByArrays(checkdel);
			result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "删除成功！");
		}catch(Exception e){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "删除失败");
			return ERROR;
		}
		return SUCCESS;
	}

}
