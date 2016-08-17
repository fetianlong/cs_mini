package com.dearho.cs.sys.pojo;

import java.io.Serializable;

/**
 * @author GaoYunpeng
 * 角色实体类
 *
 */
public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5420063146958208608L;
	
	private String name; //角色名称
	private String id;   //角色id
	private String desc; //角色描述
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
