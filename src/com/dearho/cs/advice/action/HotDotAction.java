package com.dearho.cs.advice.action;


import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.advice.pojo.HotDot;
import com.dearho.cs.advice.service.HotDotService;
import com.dearho.cs.sys.action.AbstractAction;
import com.foxinmy.weixin4j.util.DateUtil;



/**
 * 催我建点 ACTION 
 * @fileName:HotDotAction.java
 * @author:赵振明
 * @createTime:2016年7月15日上午10:31:54
 *
 */
public class HotDotAction extends AbstractAction{
	
	private static final long serialVersionUID = 1L;
	
	private Page<HotDot> page = new Page<HotDot>();
	
	private HotDot hotDot;
	
	private String beginDate;
	
	private String endDate;
	
	private HotDotService hotDotService;
	
	private List<HotDot> htlist;
	
	private String jsonhtlist;
	
	
	
	public String getJsonhtlist() {
		return jsonhtlist;
	}

	public void setJsonhtlist(String jsonhtlist) {
		this.jsonhtlist = jsonhtlist;
	}

	public List<HotDot> getHtlist() {
		return htlist;
	}

	public void setHtlist(List<HotDot> htlist) {
		this.htlist = htlist;
	}

	public Page<HotDot> getPage() {
		return page;
	}

	public void setPage(Page<HotDot> page) {
		this.page = page;
	}

	public HotDot getHotDot() {
		return hotDot;
	}

	public void setHotDot(HotDot hotDot) {
		this.hotDot = hotDot;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public HotDotService getHotDotService() {
		return hotDotService;
	}

	public void setHotDotService(HotDotService hotDotService) {
		this.hotDotService = hotDotService;
	}

	
	
	
	public HotDotAction(){
		super();
		hotDot = new HotDot();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	
	
	
	public String process(){
		
		
		htlist = hotDotService.searchHotDotByHotDot(hotDot, beginDate, endDate);
		
		
		//jsonhtlist = JsonUtil.toJson(htlist);
		
		
		JSONArray array = new JSONArray();
		JSONObject obj = null;
		for (HotDot h : htlist) {
			obj = new JSONObject();
			obj.put("id", h.getId());
			obj.put("subid", h.getSubscriberId());
			obj.put("subname", h.getSubscriberName());
			obj.put("lat", h.getLat());
			obj.put("lng", h.getLng());
			obj.put("ts", DateUtil.fortmat2yyyyMMddHHmmss(h.getTs()));
			array.add(obj);
		}
		jsonhtlist =  array.toString();
		System.out.println(jsonhtlist);
		
		return SUCCESS;
		
	}
	

}
