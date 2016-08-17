package com.dearho.cs.carservice.dao;



import java.util.List;

import com.dearho.cs.carservice.pojo.CarBreak;
import com.dearho.cs.core.db.page.Page;

/**
 * 车损上报
 * @fileName:CarBreakDao.java
 * @author:赵振明
 * @createTime:2016年7月13日上午10:52:30
 *
 */
public interface CarBreakDao {

	/**
	 * TODO:addCarBreak(新增 )
	 * @author:赵振明
	 * @createTime:2016年7月13日上午10:52:55
	 * @return:void
	 * @param carBreak
	 */
	public void addCarBreak(CarBreak carBreak);

	/**
	 * TODO:updateCarBreak(修改 )
	 * @author:赵振明
	 * @createTime:2016年7月13日上午10:53:04
	 * @return:void
	 * @param carBreak
	 */
	public void updateCarBreak(CarBreak carBreak);

	/**
	 * TODO:queryCarBreakByPage(分页 )
	 * @author:赵振明
	 * @createTime:2016年7月13日上午10:53:13
	 * @return:Page<CarBreak>
	 * @param page
	 * @param hql
	 * @return
	 */
	public Page<CarBreak> queryCarBreakByPage(Page<CarBreak> page,String hql);
	

	/**
	 * TODO:queryCarBreakById( 根据ID查询)
	 * @author:赵振明
	 * @createTime:2016年7月13日上午10:53:21
	 * @return:CarBreak
	 * @param id
	 * @return
	 */
	public CarBreak queryCarBreakById(String id);

	/**
	 * TODO:deleteCarBreakArrays(根据ID删除 )
	 * @author:赵振明
	 * @createTime:2016年7月13日上午10:53:31
	 * @return:void
	 * @param ids
	 */
	public void deleteCarBreakArrays(String[] ids);

	/**
	 * TODO:queryCarBreakByHql(根据 HQL 语句查询)
	 * @author:赵振明
	 * @createTime:2016年7月13日下午3:47:51
	 * @return:List<CarBreak>
	 * @param hql
	 */
	public List<CarBreak> queryCarBreakByHql(String hql);
	

}
