package com.dearho.cs.sys.action.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.CookieTool;
import com.dearho.cs.util.Md5Util;
import com.dearho.cs.util.PropertiesHelper;
import com.dearho.cs.util.Sha1Util;
import com.dearho.cs.util.ToolDateTime;
import com.opensymphony.webwork.ServletActionContext;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class LoginAction extends AbstractAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(LoginAction.class);

	private UserService userService;
	
	private String name;
	private String password;
	private String validateCode;
	private String isCookie;
	
	
	
	public String process() {
	/*	HttpSession session = ServletActionContext.getRequest().getSession();
		if (validateCode == null
				|| session.getAttribute("rand") == null
				|| !validateCode.toLowerCase().equals(
						String.valueOf(session.getAttribute("rand"))
								.toLowerCase())) {
			result = Ajax.JSONResult(1, "验证码错误！");
			return Action.ERROR;
		}
		User user = userService.login(new User(name, password));
		if (user == null){
			result = Ajax.JSONResult(1, "用户名或密码错误！"); 
			return Action.ERROR;
		}else{
			session.setAttribute(Constants.SESSION_USER, user);
			result = Ajax.JSONResult(0, "登录成功！");
			
			user.setLastLoginTime(user.getLastLoginTime()==null ?new Date():user.getLastLoginTime());
			user.setThisLoginTime(new Date());
			userService.updateUser(user);
			
			CookieTool.addCookie(getResponse(), "", "/", true, "ADMINUSER", Md5Util.MD5Encode("adminUser"), CookieTool.maxAge);
			return Action.SUCCESS;
		}*/
		
		//==========================
		
		//验证码校验
		HttpSession session = ServletActionContext.getRequest().getSession();
//2016年7月12日 11:18:46  赵振明 注释掉验证码 验证功能		
//		if(validateCode == null || session.getAttribute("rand") == null || !validateCode.toLowerCase().equals(String.valueOf(session.getAttribute("rand")).toLowerCase())){
//			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "验证码错误！"); 
//			return SUCCESS;
//		}

		//自动登录
		if("true".equals(isCookie)){
				
				String userId = CookieTool.getCurrentAdmin(getRequest());
				if (!StringUtils.isEmpty(userId)) {
					User user = userService.searchUserByID(userId);
					if (user != null) {
						getSession().setAttribute(Constants.SESSION_USER, user);
						result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "登录成功！");
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
						String dateStr = simpleDateFormat.format(new Date());
						try {
							user.setLastLoginTime(user.getThisLoginTime()==null ?simpleDateFormat.parse(dateStr):user.getThisLoginTime());
							user.setThisLoginTime(simpleDateFormat.parse(dateStr));
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						userService.updateUser(user);
						
						String autoLoginStr =getRequest().getParameter("autoLogin");
						boolean autoLogin = false;
						if(null != autoLoginStr && autoLoginStr.equals("1")){
							autoLogin = true;
						}
						try {
							CookieTool.setCurrentAdmin(getRequest(), getResponse(), user, autoLogin);
						} catch (Exception e) {
							logger.error(e);
						}
						
						return SUCCESS;
					}
				}
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "记住密码过期，请重新登录！");
				
				return SUCCESS;
		//手动登录
		}else{
				
					User user = userService.searchUserByName(name);
					
					if (user == null){
						result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "用户名或密码错误！"); 
						return SUCCESS;
					}else{
						// 密码错误次数超限，几分钟内不能登录
						int errorCount = user.getErrorCount()==null ? 0:user.getErrorCount();;
						int passErrorCount = Integer.parseInt(PropertiesHelper.getValue("password_error_account"));
						if(errorCount >= passErrorCount){
							Date stopDate = user.getStopDate();
							int minuteSpace = ToolDateTime.getDateMinuteSpace( stopDate,ToolDateTime.getDate());
							int passErrorMinute = Integer.parseInt(PropertiesHelper.getValue("password_error_minute"));
							if(minuteSpace < passErrorMinute){ 
								result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "密码错误次数超限，"+(passErrorMinute-minuteSpace )+"分钟内不能登录！"); 
								return SUCCESS;
							}
						}	
						
						
						if(!user.getPassword().equals(Sha1Util.SHA1Encode(password))){
						//if(!user.getPassword().equals(password)){
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							String dateStr = simpleDateFormat.format(new Date());
							try {
								user.setStopDate(simpleDateFormat.parse(dateStr));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							user.setErrorCount(errorCount+1);
							userService.updateUser(user);
							result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "用户名或密码错误！"); 
							
							return SUCCESS;
						}
						
						if (User.STATUS_INVALID.equals(user.getStatus())){
							result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "账号已失效！"); 
							return SUCCESS;
						}else if (StringUtils.isEmpty(user.getGroupId())){
							result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "没有权限，请联系管理员！"); 
							return SUCCESS;
						}else{
							
							/*MenuVO menuVO = new MenuVO(menuService.getRootMenu(),menuService,user.getUserId());
							getRequest().setAttribute("root", menuVO);*/
							
							getSession().setAttribute(Constants.SESSION_USER, user);
							result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "登录成功！");
							
							String autoLoginStr =getRequest().getParameter("autoLogin");
							boolean autoLogin = false;
							if(null != autoLoginStr && autoLoginStr.equals("1")){
								autoLogin = true;
							}
							try {
								CookieTool.setCurrentAdmin(getRequest(), getResponse(), user, autoLogin);
							} catch (Exception e) {
								logger.error(e);
							}
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							String dateStr = simpleDateFormat.format(new Date());
							try {
								user.setLastLoginTime(user.getThisLoginTime()==null ?simpleDateFormat.parse(dateStr):user.getThisLoginTime());
								user.setThisLoginTime(simpleDateFormat.parse(dateStr));
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							user.setErrorCount(0);
							userService.updateUser(user);
							
							//session失效后或者访问非portal开头链接，判断用户是否登录过后台
							CookieTool.addCookie(getResponse(), "", "/", true, "CS_ADMINUSER", Md5Util.MD5Encode("adminUser"), CookieTool.maxAge);
							
							
							return SUCCESS;
						}
					}
			
		}
		
	}

	public String gotoLogin(){
		return SUCCESS;
	}
	public static void main(String[] args) {
		
			System.out.println(Sha1Util.SHA1Encode("csmini"));
		
	}
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getIsCookie() {
		return isCookie;
	}

	public void setIsCookie(String isCookie) {
		this.isCookie = isCookie;
	}
	
	
}
