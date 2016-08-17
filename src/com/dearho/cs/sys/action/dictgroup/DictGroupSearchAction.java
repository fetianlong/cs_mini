package com.dearho.cs.sys.action.dictgroup;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.DictGroup;
import com.dearho.cs.sys.service.DictGroupService;
import com.opensymphony.xwork.Action;


/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:42
 */
public class DictGroupSearchAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DictGroupService dictGroupService;
	
	private Page<DictGroup> page = new Page<DictGroup>();
	
	private DictGroup rEntity;
	
	private List<DictGroup> recordList;
	
	public DictGroupSearchAction(){
		super();
		rEntity = new DictGroup();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}
	
	public String process(){
		try {
			page = dictGroupService.searchDictGroup(page, rEntity);
			return Action.SUCCESS;
		} catch (Exception e) {
			return Action.ERROR;
		}
	}


	public DictGroupService getDictGroupService() {
		return dictGroupService;
	}

	public void setDictGroupService(DictGroupService dictGroupService) {
		this.dictGroupService = dictGroupService;
	}

	public Page<DictGroup> getPage() {
		return page;
	}

	public void setPage(Page<DictGroup> page) {
		this.page = page;
	}

	public DictGroup getrEntity() {
		return rEntity;
	}

	public void setrEntity(DictGroup rEntity) {
		this.rEntity = rEntity;
	}

	public List<DictGroup> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<DictGroup> recordList) {
		this.recordList = recordList;
	}
	
	
	
}
