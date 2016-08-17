package com.dearho.cs.advice.service;

import com.dearho.cs.advice.pojo.Inform;
import com.dearho.cs.core.db.page.Page;

/**
 * 通知服务层接口
 * @author jyt
 * @since 2016年7月20日 下午1:58:17
 */
public interface InformService {

	/**
	 * 查询通知信息列表 后台列表查询
	 * @param page
	 * @param inform
	 * @return
	 */
	Page<Inform> queryInformPage(Page<Inform> page, Inform inform);

	/**
	 * 添加一条通知记录
	 * @param inform
	 */
	void addInform(Inform inform);

	/**
	 * 通过ID删除
	 * @param id
	 * @return
	 */
	boolean  delInformById( String id);

	/**
	 * 更新inform
	 * @param inform
	 * @return
	 */
	boolean  updateInform(Inform inform);

	/**
	 * 通过ids删除inform
	 * @param ids
	 */
	boolean  delInformByIds(String[] ids);

	/**
	 * 通过ID查询inform
	 * @param id
	 * @return
	 */
	Inform getInformById(String id);

}
