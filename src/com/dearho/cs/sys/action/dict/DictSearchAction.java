package com.dearho.cs.sys.action.dict;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.DictGroup;
import com.dearho.cs.sys.service.DictGroupService;
import com.dearho.cs.sys.service.DictService;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class DictSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DictService dictService;
	
	private DictGroupService dictGroupService;
	
	private Page<Dict> page = new Page<Dict>();
	
	private Dict rEntity;
	
	private List<Dict> recordList;
	
	private String dictGroupId;
	
	private List<DictGroup> dictGroups;
	
	public DictSearchAction(){
		super();
		rEntity = new Dict();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			dictGroups = dictGroupService.searchDictGroupByIdCode(null,null);
			page = dictService.searchDict(dictGroupId,page, rEntity);
			if(page != null && page.getResults() != null){
				List<Dict> dicts = page.getResults();
				for (Dict dict : dicts) {
					DictGroup group = dictGroupService.getDictGroupById(dict.getGroupId());
					if(group != null){
						dict.setGroupRemark(group.getRemark());
					}
					
				}
			}
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}


	public DictService getDictService() {
		return dictService;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

	public Page<Dict> getPage() {
		return page;
	}

	public void setPage(Page<Dict> page) {
		this.page = page;
	}

	public Dict getrEntity() {
		return rEntity;
	}

	public void setrEntity(Dict rEntity) {
		this.rEntity = rEntity;
	}

	public List<Dict> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<Dict> recordList) {
		this.recordList = recordList;
	}
	public DictGroupService getDictGroupService() {
		return dictGroupService;
	}
	public void setDictGroupService(DictGroupService dictGroupService) {
		this.dictGroupService = dictGroupService;
	}

	public String getDictGroupId() {
		return dictGroupId;
	}

	public void setDictGroupId(String dictGroupId) {
		this.dictGroupId = dictGroupId;
	}

	public List<DictGroup> getDictGroups() {
		return dictGroups;
	}

	public void setDictGroups(List<DictGroup> dictGroups) {
		this.dictGroups = dictGroups;
	}
	
	
	
}
