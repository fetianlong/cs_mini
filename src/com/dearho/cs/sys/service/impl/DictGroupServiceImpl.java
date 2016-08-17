package com.dearho.cs.sys.service.impl;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.DictGroupDAO;
import com.dearho.cs.sys.pojo.DictGroup;
import com.dearho.cs.sys.service.DictGroupService;
import com.dearho.cs.util.StringHelper;

/**
 * @author GaoYunpeng
 * @Description 角色业务实现类
 * @version 1.0 2015年4月22日 上午9:57:01
 */
public class DictGroupServiceImpl implements DictGroupService {

	private DictGroupDAO dictGroupDAO;
	
	public void setDictGroupDAO(DictGroupDAO dictGroupDAO) {
		this.dictGroupDAO = dictGroupDAO;
	}
	
	@Override
	public List<DictGroup> searchDictGroupByIdCode(String id, String code) {
		return dictGroupDAO.searchDictGroupByIdCode(id, code);
	}

	@Override
	public void addDictGroup(DictGroup dictGroup) {
		dictGroupDAO.addDictGroup(dictGroup);
	}

	@Override
	public void updateDictGroup(DictGroup dictGroup) {
		dictGroupDAO.updateDictGroup(dictGroup);
	}

	@Override
	public void deleteDictGroup(String[] checkdel) {
		dictGroupDAO.deleteDictGroup(checkdel);
	}

	@Override
	public Page<DictGroup> searchDictGroup(Page<DictGroup> page, DictGroup dictGroupEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.id from DictGroup a where 1 = 1 ");
		if (dictGroupEntity != null){
			if (StringHelper.isNotEmpty(dictGroupEntity.getCode())){
				sb.append("and a.code = '"+dictGroupEntity.getCode()+"'");
			}
			if (StringHelper.isNotEmpty(dictGroupEntity.getRemark())){
				sb.append("and a.remark like '%"+dictGroupEntity.getRemark()+"%'");
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createTime  desc" : page.getOrderByString());
		page = dictGroupDAO.searchDictGroup(page, sb.toString());
		return page;
	}

	@Override
	public DictGroup getDictGroupByCode(String code) {
		return dictGroupDAO.getDictGroupByCode(code);
	}

	@Override
	public void deleteDictGroupById(String id) {
		String[] ids = new String[]{id};
		dictGroupDAO.deleteDictGroup(ids);
	}

	@Override
	public DictGroup getDictGroupById(String id) {
		return dictGroupDAO.getDictGroupById(id);
	}

}
