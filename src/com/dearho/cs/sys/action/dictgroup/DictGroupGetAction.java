package com.dearho.cs.sys.action.dictgroup;

import java.util.List;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.DictGroup;
import com.dearho.cs.sys.service.DictGroupService;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年4月22日 上午10:29:31
 */
public class DictGroupGetAction extends AbstractAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	
	private DictGroupService dictGroupService;
	
	private DictGroup rEntity;
	
	@Override
	public String process() {
		if (id == null || id.trim().equals("")){
			rEntity = new DictGroup();
		}else{
			List<DictGroup> eEntitys = dictGroupService.searchDictGroupByIdCode(id, null);
			if (eEntitys == null || eEntitys.size() <= 0 ){
				rEntity = new DictGroup();
			}
			else {
				rEntity = eEntitys.get(0);
			}
		}
		return SUCCESS;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DictGroupService getDictGroupService() {
		return dictGroupService;
	}

	public void setDictGroupService(DictGroupService dictGroupService) {
		this.dictGroupService = dictGroupService;
	}

	public DictGroup getrEntity() {
		return rEntity;
	}

	public void setrEntity(DictGroup rEntity) {
		this.rEntity = rEntity;
	}


	
}
