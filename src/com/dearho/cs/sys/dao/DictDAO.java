package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Dict;
/**
 * @author GaoYunpeng
 * @Description 数据字典组
 * @version 1.0 2015年5月19日 下午3:22:01
 */
public interface DictDAO {
	Dict searchDictById(String id);
	void addDict(Dict dict);
	void updateDict(Dict dict);
	void deleteDict(final String[] checkdel);
	Page<Dict> searchDict(Page<Dict> page,String hql);
	List<Dict> getDictsByGroupId(String groupId);
	Dict getDictByGroupIdAndDictCode(String id, String dictCode);
}
