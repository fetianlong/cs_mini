package com.dearho.cs.orders.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.orders.pojo.OrdersBill;
import com.dearho.cs.orders.service.OrdersBillService;
import com.dearho.cs.orders.service.OrdersService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.sys.pojo.User;
import com.dearho.cs.sys.util.SystemOperateLogUtil;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.ExcelUtil;
import com.opensymphony.webwork.ServletActionContext;

public class OrdersBillSearchAction  extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private OrdersBillService ordersBillService;
	
	private OrdersService ordersService;
	
	private Page<OrdersBill> page=new Page<OrdersBill>();
	
	private OrdersBill ordersBill;
	
	private String startTime;
	
	private String endTime;
	
	private String state;
	
	private String id;
	
	public OrdersBillSearchAction(){
		super();
		ordersBill=new OrdersBill();
		page.setCountField("a.id");
		page.setCurrentPage(1);
	}
	
	@Override
	public String process() {
		page = ordersBillService.queryOrdersBillPage(page, state, startTime, endTime);
		return SUCCESS;
	}
	
	/**
	 * 更新发票状态
	 * @return
	 */
	public String updateOrderBillState(){
		if(StringUtils.isEmpty(id)){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "找不到发票");
			return ERROR;
		}
		OrdersBill ordersBill = ordersBillService.queryOrdersBill(id);
		ordersBill.setState("1");//标记该发票已经处理过
		ordersBillService.updateBill(ordersBill);
		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "发票处理成功");
		return SUCCESS;
	}
	
	/**
	 * 查询发票详情
	 * @return
	 */
	public String queryOrdersBillDetail(){
		String id = getRequest().getParameter("ordersBillId");
		if(StringUtils.isEmpty(id)){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "找不到发票");
			return ERROR;
		}
		ordersBill = ordersBillService.queryOrdersBill(id);
		return SUCCESS;
	}
	
	/**
	 * 删除发票
	 * @return
	 */
	public String deleteOrdersBill(){
		String id = getRequest().getParameter("ordersBillId");
		if(StringUtils.isEmpty(id)){
			result=Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "找不到发票");
			return ERROR;
		}
//		String[] ids = {id};
//		ordersBillService.deleteBill(ids);
		return SUCCESS;
	}

	/**
	 * 更新发票信息
	 * @return
	 */
	public String updateOrdersBill(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		User s=(User) session.getAttribute("user");
		
		Object beforeObject = ordersBillService.queryOrdersBill(id);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		OrdersBill bill = ordersBillService.queryOrdersBill(id);
		bill.setExpresName(ordersBill.getExpresName());
		bill.setExpresNo(ordersBill.getExpresNo());
		bill.setState(ordersBill.getState());
		try {
			bill.setSendDate(format.parse(getRequest().getParameter("ordersBill.sendDate")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		bill.setRefuseReason(ordersBill.getRefuseReason());
		bill.setOperatorId(s.getId());
		bill.setOperatorName(s.getName());
		ordersBillService.updateBill(bill);
		result=Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS, "发票信息更新成功");
		
		Object afterObject = ordersBillService.queryOrdersBill(id);
		try {
			SystemOperateLogUtil.sysUpdateOperateLog(beforeObject, afterObject, s, "发票管理", bill.getBillNo());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 发票列表导出功能
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String exportOrdersBill(){
		page.setPageSize(1000);
		process();
		String downLoadDateStr=startTime+"_"+endTime;
		ExcelUtil.exportExcel(page.getResults(), "发票记录_"+downLoadDateStr+"_"+new Date().getTime());
		return SUCCESS;
	}
	
	public OrdersBillService getOrdersBillService() {
		return ordersBillService;
	}
	public void setOrdersBillService(OrdersBillService ordersBillService) {
		this.ordersBillService = ordersBillService;
	}
	public Page<OrdersBill> getPage() {
		return page;
	}
	public void setPage(Page<OrdersBill> page) {
		this.page = page;
	}
	public OrdersBill getOrdersBill() {
		return ordersBill;
	}
	public void setOrdersBill(OrdersBill ordersBill) {
		this.ordersBill = ordersBill;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public OrdersService getOrdersService() {
		return ordersService;
	}

	public void setOrdersService(OrdersService ordersService) {
		this.ordersService = ordersService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


}
