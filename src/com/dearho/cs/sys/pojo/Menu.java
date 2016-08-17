package com.dearho.cs.sys.pojo;

import java.io.Serializable;
import java.util.Date;
/**
 * @Author liusong
 * @Description 菜单
 * @Version 1.0, 2015-7-17
 */
public class Menu  implements Serializable{


	private static final long serialVersionUID = 1L;
	
	public static final Integer root =1;// 菜单根节点
	
	private Integer menuId;
	private String menuName;
	private String menuUrl;
	private String menuType;
	private Integer menuPid;
	private Integer menuOrder;
	private Date ts;
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public Integer getMenuPid() {
		return menuPid;
	}
	public void setMenuPid(Integer menuPid) {
		this.menuPid = menuPid;
	}
	public Integer getMenuOrder() {
		return menuOrder;
	}
	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	
	
	
	
}
