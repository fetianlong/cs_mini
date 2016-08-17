package com.dearho.cs.sys.action.user;

import java.util.List;

import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.Orders;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.service.UserService;
import com.dearho.cs.util.StringHelper;
import com.opensymphony.xwork.Action;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class UserSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	
	private Page<User> page = new Page<User>();
	
	private User sEntity;
	
	private List<User> recordList;
	
	private String state;
	
	public UserSearchAction(){
		super();
		sEntity = new User();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			if(state != null && "page".equals(state)){
				page = userService.searchUser(page, sEntity);
				return "search";
			}
			else{
				page = userService.searchUser(page, sEntity);
				return Action.SUCCESS;
			}
			
		} catch (Exception e) {
			return Action.ERROR;
		}
	}
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<User> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<User> recordList) {
		this.recordList = recordList;
	}

	public User getsEntity() {
		return sEntity;
	}

	public void setsEntity(User sEntity) {
		this.sEntity = sEntity;
	}

	public Page<User> getPage() {
		return page;
	}

	public void setPage(Page<User> page) {
		this.page = page;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getState() {
		return state;
	}
	
	
}
