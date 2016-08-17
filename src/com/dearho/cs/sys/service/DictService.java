package com.dearho.cs.sys.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Dict;

/**
 * @author GaoYunpeng
 * @Description 
 * @version 1.0 2015年5月19日 下午3:19:44
 */
public interface DictService {
	Dict searchDictById(String id);
	
	void addDict(Dict dict);
	
	void updateDict(Dict dict);
	void deleteDict(final String[] checkdel);
	
	Page<Dict> searchDict(String dictGroupId, Page<Dict> page,Dict dict);

	Dict getDictByCodes(String groupCode,String dictCode);
	
	Dict getDictByGroupIdAndDictCode(String groupId,String dictCode);
	
	List<Dict> getDictsByGroupId(String groupId);
	void reloadDictEntity();
}
