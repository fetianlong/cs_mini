package com.dearho.cs.place.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.BookCarInfoEntity;
import com.dearho.cs.place.pojo.BranchDot;

public interface BranchDotService {
	void addBranchDot(BranchDot branchDot);
	void updateBranchDot(BranchDot branchDot);
	void deleteBranchDot(final String[] checkdel);
	Page<BranchDot> searchBranchDot(Page<BranchDot> page,BranchDot branchDot);
	List<BranchDot> searchBranchDot(Integer isActive,String... params );
	//List<BranchDot> searchBranchDot(String code,String name,String address,String areaId,Integer isActive);
	BranchDot getBranchDotById(String id);
	BranchDot getBranchDotByCode(String code);
	/**
	* 查询还车网点
	* @param page
	* @param branchDot
	* @return
	* @return Page<BranchDot>
	*/
	Page<BranchDot> searchReturnbackBranchDot(Page<BranchDot> page, BranchDot branchDot);
	/**
	 * 查询网点下未租借的车辆
	 * @param id
	 * @return
	 */
	List<BookCarInfoEntity> getUnbookCars(String id);
	Integer getCarCount(String id);
	BookCarInfoEntity getCarInfo(String carId);
	
	BranchDot getBranchDotByCarId(String carId);
	/**
	 * @param dotHql
	 * @return
	 */
	List<BranchDot> find(String dotHql);
	/**
	 * 查询包含在ids里面的网点
	 * @param ids
	 * @return
	 */
	List<BranchDot> searchDotsIn(String ids);
	/**
	 * 查询不在ids里面的网点
	 * @param ids
	 * @return
	 */
	List<BranchDot> searchDotsNotIn(String ids);
	/**
	 * 获取当前用户一定距离以内的网点
	 * @param string
	 * @param string2
	 * @param string3
	 * @param string4
	 * @return
	 */
	List<BranchDot> branchDots(String minLng, String maxLng, String minLat,
			String maxLat);
	
	 
}
