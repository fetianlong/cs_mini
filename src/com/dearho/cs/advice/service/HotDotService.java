package com.dearho.cs.advice.service;

import java.util.List;



import com.dearho.cs.advice.pojo.HotDot;
/**
 * 催我建点 业务层接口定义
 * @fileName:HotDotService.java
 * @author:赵振明
 * @createTime:2016年7月15日上午10:49:25
 *
 */
public interface HotDotService {
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
	 */
	public List<HotDot> searchHotDot(String hql);
	
	/**
	 * TODO:searchHotDotByHotDot(根据会员ID， 数据添加时间 查询)
	 * @author:赵振明
	 * @createTime:2016年7月15日上午11:16:06
	 * @return:List<HotDot>
	 * @param hotDot
	 * @param beginDate 开始时间
	 * @param endDate   结束时间
	 */
	public List<HotDot> searchHotDotByHotDot(HotDot hotDot ,String beginDate , String endDate);
}
