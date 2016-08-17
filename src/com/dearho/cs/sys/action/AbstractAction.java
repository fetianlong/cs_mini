package com.dearho.cs.sys.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import com.dearho.cs.core.configuration.Configuration;
import com.dearho.cs.sys.pojo.Menu;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.GroupService;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.DateUtil;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionSupport;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public abstract class AbstractAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Log log = LogFactory.getLog(AbstractAction.class);
	
	private static final SimpleDateFormat sdf10=new SimpleDateFormat("yyyy-MM-dd"); 
	
	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static final SimpleDateFormat sdf4=new SimpleDateFormat("HH:mm");
	
	private static final SimpleDateFormat sdf6=new SimpleDateFormat("yyyy-MM");
	
	private static final SimpleDateFormat sdf12=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	private static final SimpleDateFormat sdf_xyk=new SimpleDateFormat("MM / yy");// 信用卡有效期
	protected String result;
	
	public String actionName;
	

	private static GroupService groupService= null;
	
	private static List<String> menuUrlList=new ArrayList<String>();
	
	
	
	public String execute() throws Exception {
		
		if(groupService==null){
			groupService=(GroupService)ContextLoader.getCurrentWebApplicationContext().getBean("groupService"); 
			List<Menu> menuList=groupService.getAllMenu();
	        if(menuList!=null && menuList.size()>0){
		       	 for(Menu menu:menuList){
		       		 menuUrlList.add(menu.getMenuUrl());
		       	 }
	       	
	        }
		}
		
		try {
			if (hasErrors()) {
				log.debug("action not executed, field or action errors");
				log.debug("Field errors: " + getFieldErrors());
				log.debug("Action errors: " + getActionErrors());
				return INPUT;
			}
			if (log.isDebugEnabled()) {
				log.debug("executing action");
			}
			actionName = ActionContext.getContext().getName() + ".action";
			log.info(DateUtil.getChar19DateString()+"\t"+actionName);
			String sRet = process();
			return sRet;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, getErrorInfo(e));
			return ERROR;
		}
	}

	public abstract String process();
	
	private String getErrorInfo(Exception e){
		StringBuffer sb = new StringBuffer();
		if (Configuration.getInstance().getErrorinfoShowType().equals("detail")){
			sb.append(e.getMessage());
			if (e.getStackTrace() != null){
				StackTraceElement[] trace = e.getStackTrace();
				for (StackTraceElement ste : trace){
					sb.append("<br/>at "+ ste);
				}
			}
		}else{
			sb.append("系统错误，请联系管理员！");
		}
		return sb.toString();
	}
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	/**
	 * 转换成yyyy-MM-dd HH:mm:ss格式
	 * @param date
	 * @return
	 */
	public String transDateString(Date date){
		return sdf.format(date);
	}
	/**
	 * 转换成yyyy-MM-dd格式
	 * @param date
	 * @return
	 */
	public String transDate10String(Date date){
		return sdf10.format(date);
	}
	/**
	 * 转换成HH:mm的格式
	 * @Title transDate4String
	 * @Description:(方法描述)
	 * @param date
	 * @return
	 * @throws
	 */
	public String transDate4String(Date date){
		return sdf4.format(date);
	}
	/**
	 * 转换成年月日小时分钟的格式 
	 * @Title transDate12String
	 * @Description:(方法描述)
	 * @param date
	 * @return
	 * @throws
	 */
	public String transDate12String(Date date){
		return sdf12.format(date);
	}
	
	/**
	 * 转换成信用卡有效期格式 
	 * @Title transDate12String
	 * @Description:(方法描述)
	 * @param date
	 * @return
	 * @throws
	 */
	public String transDateXykString(Date date){
		if(date==null){
			return "";
		}else{
			return sdf_xyk.format(date);
		}
		
	}
	
	public String transDate6String(Date date){
		return sdf6.format(date);
	}
	/**
	 * 
	 * @Title: formatAmount
	 * @Description:账号金额格式化
	 * @param amount
	 * @return
	 * @throws
	 */
	public String formatAmount(Double amount){
		String amountStr="";
		if(amount==null){
			return amountStr;
		}
		amountStr=String.format("%.2f", amount);
		return amountStr;
	}
	
	/**
	 * 时间差格式化
	 * @param interval 相差时间
	 * @return
	 */
	public String formatInterval(Long interval){
		interval=interval/1000;
		  long day=interval/(24*3600);//天
		  long hour=interval%(24*3600)/3600;//小时
		  long minute=interval%3600/60;//分钟
		  long second=interval%60;//秒
		  return ((hour<10)?"0"+hour:hour)+":"+((minute<10)?"0"+minute:minute)+":"+(second<10?"0"+second:second);

		
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	public Boolean hasPrivilegeUrl(String url){
		Boolean result=false;
		
		Object obj=getSession().getAttribute(Constants.SESSION_USER);
		if(obj==null){
			return true;
		}
		User user=(User)obj;
		
		if(needPermissionValidation(url)){

			Object objPermission =getSession().getAttribute(Constants.SESSION_USER_PERMISSION);
			List<String> permissionList=null;
			if(objPermission!=null){
				permissionList=(List<String>)objPermission;
			}else{
				permissionList=groupService.getUserPermissionByGroupId(user.getGroupId());
				getSession().setAttribute(Constants.SESSION_USER_PERMISSION,permissionList);
			}
			if(permissionList.contains(url)){
				result=true;
			}
		
		}else{
			result=true;
		}
		
		return result;
	}
	
	
	private Boolean needPermissionValidation(String url){
		return menuUrlList.contains(url);
	}
	
}