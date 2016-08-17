package com.dearho.cs.sys.action.dict;

import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.DictGroup;
import com.dearho.cs.sys.service.DictGroupService;
import com.dearho.cs.sys.service.DictService;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:31
 */
public class DictGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private DictService dictService;
	
	private Dict rEntity;
	
	private List<DictGroup> dictGroups;
	
	private DictGroupService dictGroupService;
	
	private String dictGroupId;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			rEntity = new Dict();
		}else{
			Dict eEntity = dictService.searchDictById(id);
			if (eEntity == null){
				rEntity = new Dict();
			}
			else {
				rEntity = eEntity;
				dictGroupId = eEntity.getGroupId();
			}
			
		}
		dictGroups = dictGroupService.searchDictGroupByIdCode(null,null);
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DictService getDictService() {
		return dictService;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

	public Dict getrEntity() {
		return rEntity;
	}

	public void setrEntity(Dict rEntity) {
		this.rEntity = rEntity;
	}

	public void setDictGroups(List<DictGroup> dictGroups) {
		this.dictGroups = dictGroups;
	}
	public List<DictGroup> getDictGroups() {
		return dictGroups;
	}
	public void setDictGroupService(DictGroupService dictGroupService) {
		this.dictGroupService = dictGroupService;
	}
	public DictGroupService getDictGroupService() {
		return dictGroupService;
	}
	public String getDictGroupId() {
		return dictGroupId;
	}
	public void setDictGroupId(String dictGroupId) {
		this.dictGroupId = dictGroupId;
	}
	
}
