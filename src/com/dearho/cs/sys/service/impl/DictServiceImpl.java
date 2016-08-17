package com.dearho.cs.sys.service.impl;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.DictDAO;
import com.dearho.cs.sys.dao.DictGroupDAO;
import com.dearho.cs.sys.entity.DictSingleClz;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.DictGroup;
import com.dearho.cs.sys.service.DictService;
import com.dearho.cs.util.StringHelper;

/**
 * @author GaoYunpeng
 * @Description 角色业务实现类
 * @version 1.0 2015年4月22日 上午9:57:01
 */
public class DictServiceImpl implements DictService {
	

	private DictDAO dictDAO;
	
	private DictGroupDAO dictGroupDAO;
	
	
	
	public DictDAO getDictDAO() {
		return dictDAO;
	}

	public void setDictDAO(DictDAO dictDAO) {
		this.dictDAO = dictDAO;
	}

	public void setDictGroupDAO(DictGroupDAO dictGroupDAO) {
		this.dictGroupDAO = dictGroupDAO;
	}
	public DictGroupDAO getDictGroupDAO() {
		return dictGroupDAO;
	}
	
	@Override
	public Dict searchDictById(String id) {
		return dictDAO.searchDictById(id);
	}

	@Override
	public void addDict(Dict dict) {
		dictDAO.addDict(dict);
		reloadDictEntity();
	}

	@Override
	public void updateDict(Dict dict) {
		dictDAO.updateDict(dict);
		reloadDictEntity();
	}

	@Override
	public void deleteDict(String[] checkdel) {
		dictDAO.deleteDict(checkdel);
		reloadDictEntity();
	}

	@Override
	public Page<Dict> searchDict(String dictGroupId,Page<Dict> page, Dict roleEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.id from Dict a where 1 = 1 ");
		if (roleEntity != null){
			if (StringHelper.isNotEmpty(roleEntity.getCode())){
				sb.append("and a.code = '"+roleEntity.getCode()+"' ");
			}
			if (StringHelper.isNotEmpty(roleEntity.getCnName())){
				sb.append("and a.cnName = '"+roleEntity.getCnName()+"' ");
			}
			if (roleEntity.getIsUsed() != null){
				sb.append("and a.isUsed = "+roleEntity.getIsUsed());
			}
			if (roleEntity.getIsSystem() != null){
				sb.append("and a.isSystem = "+roleEntity.getIsSystem());
			}
			if (StringHelper.isNotEmpty(roleEntity.getRemark())){
				sb.append("and a.remark = "+roleEntity.getRemark());
			}
			
		}
		if(StringHelper.isNotEmpty(dictGroupId)){
			sb.append("and a.groupId = '"+dictGroupId+"' ");
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.createTime  desc" : page.getOrderByString());
		page = dictDAO.searchDict(page, sb.toString());
		return page;
	}


	@Override
	public void reloadDictEntity(){
		DictSingleClz dictSingleClz = DictSingleClz.getDictSingleClz();
		List<DictGroup> groups = dictGroupDAO.searchDictGroupByIdCode(null, null);
		List<Dict> dicts = dictDAO.getDictsByGroupId(null);
		DictGroup strategyGroup = new DictGroup();
		strategyGroup.setCode("strategyBaseType");
		strategyGroup.setId("402881e4513843430151384b98c00000");
		strategyGroup.setRemark("计费策略类型");
		groups.add(strategyGroup);
		
		Dict szStrategyDict = new Dict();
		szStrategyDict.setCode("jishizu");
		szStrategyDict.setId("402881ed5118bdc4015118c1c8e20001");
		szStrategyDict.setRemark("时");
		szStrategyDict.setCnName("时租");
		szStrategyDict.setColor("038f5d");
		szStrategyDict.setGroupId("402881e4513843430151384b98c00000");
		szStrategyDict.setIsSystem(1);
		dicts.add(szStrategyDict);
		
		Dict rzStrategyDict = new Dict();
		rzStrategyDict.setCode("rizu");
		rzStrategyDict.setId("402881ed5118bdc4015118c24bb00002");
		rzStrategyDict.setRemark("日");
		rzStrategyDict.setCnName("日租");
		rzStrategyDict.setColor("f99338");
		rzStrategyDict.setGroupId("402881e4513843430151384b98c00000");
		rzStrategyDict.setIsSystem(1);
		dicts.add(rzStrategyDict);
		
		dictSingleClz.setDictGroups(groups);
		dictSingleClz.setDicts(dicts);
	}

	@Override
	public List<Dict> getDictsByGroupId(String groupId) {
		return dictDAO.getDictsByGroupId(groupId);
	}

	@Override
	public Dict getDictByCodes(String groupCode, String dictCode) {
		DictGroup dictGroup = dictGroupDAO.getDictGroupByCode(groupCode);
		if(dictGroup == null){
			return null;
		}
		return dictDAO.getDictByGroupIdAndDictCode(dictGroup.getId(),dictCode);
	}

	@Override
	public Dict getDictByGroupIdAndDictCode(String groupId, String dictCode) {
		return dictDAO.getDictByGroupIdAndDictCode(groupId,dictCode);
	}
}
