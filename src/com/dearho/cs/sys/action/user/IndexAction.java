package com.dearho.cs.sys.action.user;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.subscriber.action.register.SubscriberRegisterTwoAction;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.ImageHelper;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.Action;

import net.sf.json.JSONObject;

/**
 * @Author liusong
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class IndexAction extends AbstractAction{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7626422453615688323L;
	private UserService userService;
	private User loginUser;
	private static final Log logger = LogFactory.getLog(IndexAction.class);
	
	public IndexAction(){
		loginUser=new User();
	}
	
	
	public String process() {
		User u=(User)getSession().getAttribute(Constants.SESSION_USER);
		loginUser=userService.searchUserByID(u.getId());
		return Action.SUCCESS;
	
	}
	
	
	private static final String uploadPath="upload/avatar/user/";
	/**
	 * 上传用户头像
	 * @return
	 */
	public String uploadAvatar(){
		String canvasData = ServletActionContext.getRequest().getParameter("imageData");
		if(canvasData==null){
			result=new JSONObject().element("result", "").element("state", 500).element("message", "上传数据为空!").toString();
			return SUCCESS;
		}
		
		if(getSession().getAttribute(Constants.SESSION_USER)==null){
			result=new JSONObject().element("result", "").element("state", 500).element("message", "请重新登录!").toString();
			return SUCCESS;
		}
	
		
		try {
			User u=(User)getSession().getAttribute(Constants.SESSION_USER);
			User user=userService.searchUserByID(u.getId());
			String imgurl=ImageHelper.uploadForCanvasToService(canvasData, uploadPath, null,user.getId());
			user.setAvatar(imgurl);
			userService.updateUser(user);
			result=new JSONObject().element("result", imgurl).element("state", 200).element("message", "成功").toString();
		} catch (Exception e) {
			logger.error(e);
			result=new JSONObject().element("result", "").element("state", 500).element("message", "系统异常!").toString();
			return ERROR;
		}
		return SUCCESS;
	}
	


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public User getLoginUser() {
		return loginUser;
	}


	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}


	
	
	

	
}
