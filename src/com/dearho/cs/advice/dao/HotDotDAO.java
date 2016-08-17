package com.dearho.cs.advice.dao;

import java.util.List;


import com.dearho.cs.advice.pojo.HotDot;
/**
 * 催我建点DAO 
 * @fileName:HotDotDAO.java
 * @author:赵振明
 * @createTime:2016年7月15日上午10:37:02
 */
public interface HotDotDAO {
	/**
	 * TODO:addHotDot(新增)
	 * @author:赵振明
	 * @createTime:2016年7月15日上午10:37:38
	 * @return:void
	 * @param hotDot
	 */
	public void addHotDot(HotDot hotDot);
	/**
	 * TODO:updateHotDot(更新)
	 * @author:赵振明
	 * @createTime:2016年7月15日上午10:37:49
	 * @return:void
	 * @param hotDot
	 */
	public void updateHotDot(HotDot hotDot);
	/**
	 * TODO:deleteHotDot(删除)
	 * @author:赵振明
	 * @createTime:2016年7月15日上午10:38:01
	 * @return:void
	 * @param id
	 */
	public void deleteHotDot(final String[] id);
	/**
	 * TODO:searchHotDot(根据hql查询 )
	 * @author:赵振明
	 * @createTime:2016年7月15日上午10:38:08
	 * @return:List<HotDot>
	 * @param hql
	 * @return
	 */
	public List<HotDot> searchHotDot(String hql);
}
