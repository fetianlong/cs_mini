package com.dearho.cs.carservice.service;


import com.dearho.cs.carservice.pojo.CarBreak;
import com.dearho.cs.core.db.page.Page;

/**
 * 车损上报
 * @fileName:CarBreakService.java
 * @author:赵振明
 * @createTime:2016年7月13日上午10:15:23
 */
public interface CarBreakService {

	/**
	 * TODO:addCarBreak( 新增 )
	 * @author:赵振明
	 * @createTime:2016年7月13日上午10:25:04
	 * @return:void
	 * @param carBreak
	 */
	public void addCarBreak(CarBreak carBreak);
	/**
	 * 
	 * TODO:updateCarBreak( 更新 )
	 * @author:赵振明
	 * @createTime:2016年7月13日上午10:25:13
	 * @return:void
	 * @param carBreak
	 */
	public void updateCarBreak(CarBreak carBreak);
	/**
	 * 
	 * TODO:queryCarBreak( 分页 )
	 * @author:赵振明
	 * @createTime:2016年7月13日上午10:25:27
	 * @return:Page<CarBreak>
	 * @param page
	 * @param carBreak
	 */
	public Page<CarBreak> queryCarBreak(Page<CarBreak> page,CarBreak carBreak);
	/**
	 * TODO:queryCarBreakByOrderId( 根据订单ID查询)
	 * @author:赵振明
	 * @createTime:2016年7月13日上午10:25:40
	 * @return:CarBreak
	 * @param id
	 * @return
	 */
	public CarBreak queryCarBreakByOrderId(String id);
	/**
	 * TODO:deleteCarByOrderIds( 根据订单ID删除 )
	 * @author:赵振明
	 * @createTime:2016年7月13日上午10:26:00
	 * @return:void
	 * @param ids
	 */
	public void deleteCarByOrderIds(String[] ids);
	
	/***
	 * TODO:queryCarBreakById( 根据记录ID查询 )
	 * @author:赵振明
	 * @createTime:2016年7月14日下午4:11:01
	 * @return:CarBreak
	 * @param id
	 * @return
	 */
	public CarBreak queryCarBreakById(String id);
	
	
	 
}
