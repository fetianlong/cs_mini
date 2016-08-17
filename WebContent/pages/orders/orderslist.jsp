<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单管理</title>
<%@ include file="/pages/common/common_head.jsp"%>
<script type="text/javascript">
$(function(){
	/*时间选择*/
	$("#sform .TimeSelect").datetimepicker({
		language: 'zh-CN',
		todayHighlight: 'true',
		todayBtn: 'linked',
		minView: 4,
		autoclose: true,
		minuteStep: 5,
		format: "yyyy-mm-dd"
	});
});


function searchEntity(){
	$("#sform").submit();
}

function cancelOrderForm(id,name){
	var pars={
			"id":id
		};
	alertconfirm("确认取消【"+name+"】订单吗？",function (){
		showLoading();
		$.post('<%=path%>/orders/ordersCancel.action',pars,r_cancel,'json').error(requestError);
	});	
}

function r_cancel(data){
	hideLoading();
	switch(data.result){
		case 0:
			alertok("取消成功！", function(){
		    	$('#sform').submit();		
		    });
			break;
		case 1:
			restoreInfo();
			alerterror(data.msg);
			break;
		case 9:
			document.location = "doError.action";
			break;
	}
}
function showDetail(id){
	window.location.href="<%=path%>/orders/ordersDetail.action?id="+id;
}
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
		<form class="form-horizontal" name="sform" id="sform" method="post"
			action="<%=path%>/orders/ordersSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
				value="<ww:property value="page.orderFlag"/>"> <input
				type="hidden" name="page.orderString" id="page.orderString"
				value="<ww:property value="page.orderString"/>">
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-xs-4">
						<div class="form-group">
							<label for="startTime" class="col-xs-4 control-label">开始时间</label>
							<div class="col-xs-8">
		    					<input type="text" class="form-control TimeSelect" name="startTime" id="startTime" value="<ww:property value="startTime"/>">
							</div>
						</div>
						<div class="form-group">
							<label for="orders.ordersNo" class="col-xs-4 control-label">订单编号</label>
							<div class="col-xs-8">
								<input class="form-control" name="orders.ordersNo"
									id="orders.ordersNo" type="text"
									value="<ww:property value="orders.ordersNo"/>">
							</div>
						</div>
					</div>

					<div class="col-xs-4">
						<div class="form-group">
							<label for="endTime" class="col-xs-4 control-label">结束时间</label>
							<div class="col-xs-8">
								<input type="text" class="form-control TimeSelect" name="endTime" id="endTime" value="<ww:property value="endTime"/>">
							</div>
						</div>
					</div>

					<div class="col-xs-4">
						<div class="form-group">
							<label for="orders.state" class="col-xs-4 control-label">订单状态</label>
							<div class="col-xs-8">
								<select class="form-control"   name="orders.state" id="orders.state">
									<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('14',1)" id="data" status="rl">
										<option value="<ww:property value="code" />"  <ww:if test="orders.state==code">selected=true</ww:if> ><ww:property value="cnName" /></option>	
									</ww:iterator>
								</select>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row SubmitButtonBlock">
					<div class="col-sm-2 col-sm-offset-5 col-xs-6 col-xs-offset-3">
						<a class="btn btn-block Button1" onclick="searchEntity();"
							target="_blank"><i class="fa fa-search"></i>查询</a>
					</div>
				</div>
			</div>

			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						
						<td><a href="javascript:SetOrder('ordersNo')">订单编号<img src="<%=path_common_head %>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td><a href="javascript:SetOrder('state')">订单状态<img src="<%=path_common_head %>/admin/common/images/main/paixu.png"/></a></td>
						<td>租用车型</td>
						<td>租用车辆</td>
						<td><a href="javascript:SetOrder('ordersTime')">下单时间<img src="<%=path_common_head %>/admin/common/images/main/paixu.png"/></a></td>
						<td><a href="javascript:SetOrder('beginTime')">取车时间<img src="<%=path_common_head %>/admin/common/images/main/paixu.png"/></a></td>
						<td><a href="javascript:SetOrder('endTime')">还车时间<img src="<%=path_common_head %>/admin/common/images/main/paixu.png"/></a></td>
					
						<td>承租人</td>
						<td>操作</td>
					</tr>
					<ww:iterator value="page.results" id="data" status="rl">
						<tr style="font-size:12px;" <ww:if test="#rl.even"> class="trs"</ww:if>>
							<td align="center">
								<a href="javascript:showOrderDetailForDialog('<ww:property value="id" />','<%=path%>')"><ww:property value="ordersNo" /></a>
							</td>
							<td align="center"><ww:property value="#dictUtil.getCnNameByCode('14',state)" /></td>
							<td align="center"><ww:property value="vehicleModelName" /></td>
							<td align="center"><a href="javascript:showCarDetailForDialog('<ww:property value="carId" />','<%=path%>')"><ww:property value="plateNumber" /></a></td>
							<td align="center"><ww:property value="transDate12String(ordersTime)" /></td>
							<td align="center"><ww:property value="transDate12String(beginTime)" /></td>
							<td align="center"><ww:property value="transDate12String(endTime)" /></td>
							
							<td align="center"><a href="javascript:showSubscriberDetailForDialog('<ww:property value="memberId" />','<%=path%>')"><ww:property value="memberName" /></a></td>
							<td align="center">
								<ww:if test="hasPrivilegeUrl('/orders/ordersCancel.action')">
									<ww:if test="state.equals(\"1\")||state.equals(\"3\")">
										<div class="pan_btn3"  onclick="javascript:cancelOrderForm('<ww:property value="id"/>','<ww:property value="ordersNo"/>');">取消订单 </div>	
									</ww:if>	
								</ww:if>	
								
								<ww:if test="hasPrivilegeUrl('/orders/ordersDetail.action')">
									<div class="pan_btn4"  onclick="javascript:showDetail('<ww:property value="id"/>');">详情</div> 
								</ww:if>
							</td>
						</tr>
					</ww:iterator>
					<tr>
						<td align="right" colspan="10"><ww:property
								value="page.pageSplit" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>