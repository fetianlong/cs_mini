package com.dearho.cs.advice.dao;

import com.dearho.cs.advice.pojo.Inform;
import com.dearho.cs.core.db.page.Page;

/**
 * 通知DAO接口
 * @author jyt
 * @since 2016年7月20日 下午2:35:39
 */
public interface InformDao {

	/**
	 * 按页查询系统消息
	 * @param page
	 * @param string
	 * @return
	 */
	Page<Inform> queryInformByPage(Page<Inform> page, String string);

	/**
	 * 通过id查询系统通知
	 * @param id
	 * @return
	 */
	Inform queryInformById(String id);

	/**
	 * 添加
	 * @param inform
	 */
	void addInform(Inform inform);

	/**
	 * 
	 * @param id
	 * @return
	 */
	void delInfromById(String id);

	/**
	 * 更新
	 * @param inform
	 */
	void updateInform(Inform inform);

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	void delInformByIds(String[] ids);

}
