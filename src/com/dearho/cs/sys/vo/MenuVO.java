package com.dearho.cs.sys.vo;

import com.dearho.cs.sys.pojo.Menu;
import com.dearho.cs.sys.service.MenuService;

/**
 * @Author liusong
 * @Description 菜单VO
 * @Version 1.0, 2015-7-17
 */

public class MenuVO {

	private Integer id;
	private Integer pId;
	private String name;
	private String url;
	private Boolean checked;
	private Boolean open;
	
	
	public MenuVO(){
		super();
	}
	public MenuVO(Integer id,Integer pId,String name){
		super();
		this.id=id;
		this.name=name;
		this.pId=pId;
		this.open=true;
		this.checked=false;
	}
	
	public MenuVO(Integer id,Integer pId,String name,Boolean checked){
		super();
		this.id=id;
		this.name=name;
		this.pId=pId;
		this.open=true;
		this.checked=checked==null?false:checked;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public Integer getpId() {
		return pId;
	}
	public void setpId(Integer pId) {
		this.pId = pId;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}

	

	
	
}
