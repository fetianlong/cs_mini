package com.dearho.cs.appinterface.bookcar.action;


import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.appinterface.bookcar.entity.BranchDotVo;
import com.dearho.cs.place.pojo.BranchDot;
import com.dearho.cs.place.service.BranchDotService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.StringHelper;

public class ReturnBackCarAction extends AbstractAction{

	private static final long serialVersionUID = 2792582095293000806L;
	
	private BranchDotService branchDotService;

	@Override
	public String process() {
		return null;
	}
	
	public String getReturnDotInfos(){
		String dotId = getRequest().getParameter("dotId");
		if(StringHelper.isEmpty(dotId)){
			result = Ajax.AppJsonResult(1, "参数缺失！");
			return SUCCESS;
		}
		BranchDot dot = branchDotService.getBranchDotById(dotId);
		if(dot == null){
			result = Ajax.AppJsonResult(2, "参数有误，查询不到网点！");
			return SUCCESS;
		}
		List<BranchDotVo> vos = new ArrayList<BranchDotVo>();
		String returnDotIdStr = dot.getReturnbackDot();
		if(StringHelper.isNotEmpty(returnDotIdStr)){
			returnDotIdStr = returnDotIdStr.substring(0, returnDotIdStr.length() - 1);
			StringBuffer str = new StringBuffer();
			for (String rdid : returnDotIdStr.split(",")) {
				str.append("'").append(rdid).append("',");
			}
			List<BranchDot> returnBranchDots = branchDotService.searchDotsIn(str.deleteCharAt(str.length() - 1).toString());
			if(returnBranchDots != null && returnBranchDots.size() > 0){
				for (BranchDot branchDot : returnBranchDots) {
					BranchDotVo vo = new BranchDotVo();
					vo.setAddress(branchDot.getAddress());
					vo.setId(branchDot.getId());
					vo.setLat(branchDot.getLat());
					vo.setLng(branchDot.getLng());
					vo.setName(branchDot.getName());
					vo.setCarCount(branchDotService.getCarCount(branchDot.getId()));
					vo.setTotalParkingCount(branchDot.getTotalParkingPlace());
					vos.add(vo);
				}
			}
			BranchDotVo vo = new BranchDotVo();
			vo.setAddress(dot.getAddress());
			vo.setId(dot.getId());
			vo.setLat(dot.getLat());
			vo.setLng(dot.getLng());
			vo.setName(dot.getName());
			vo.setCarCount(branchDotService.getCarCount(dot.getId()));
			vo.setTotalParkingCount(dot.getTotalParkingPlace());
			vos.add(vo);
		}
		result = Ajax.AppJsonResult(0, vos);
		return SUCCESS;
	}
	
	public BranchDotService getBranchDotService() {
		return branchDotService;
	}
	public void setBranchDotService(BranchDotService branchDotService) {
		this.branchDotService = branchDotService;
	}

}
