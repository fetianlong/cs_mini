<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>订单统计</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	
function showEntity(unitTime){
	var dialoguser = $.dialog({
	    id:'statistics_order_showedit', 
	    title:'详情',
		content : 'url:<%=path%>/statistics/orderStatisticsDetail.action?unitTime='+unitTime,
		fixed:true,
		width:800,
		height:550,
		resize:true,
 		max: false,
	    min: false,
	    lock: true,
	    ok: false,
	    cancelVal: '关闭',
	    cancel: true,
	    close: function () {
	        this.hide();
	        restoreInfo('hospitalinfo');
	        return true;
	    },
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
};

function r_savedata(data){
	hideLoading();
	switch(data.result){
		case 0:
			alertok(data.msg, function(){
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
};

function searchEntity(){
	if($("#fromDateTmp").val()=="" || $("#toDateTmp").val()==""){
		alert("请选择查询时间");
		return;
	}
	$("#sform").attr("action","<%=path%>/statistics/orderStatisticsSearch.action");
	$("#sform").submit();
};

function downloadEntity(){
	if($("#fromDateTmp").val()=="" || $("#toDateTmp").val()==""){
		alert("请选择查询时间");
		return;
	}
	$("#sform").attr("action","<%=path %>/statistics/orderStatisticsDownload.action");
	$("#sform").submit();
};
	
	
$(function(){
	function ChangeDateSelect(Type){
		$('#fromDateTmp').datetimepicker('remove');
		$('#toDateTmp').datetimepicker('remove');
		if (Type == "day"){
			$("#fromDateTmp").datetimepicker({
				language: 'zh-CN',
				todayHighlight: 'true',
				todayBtn: 'linked',
				minView: 4,
				startView: 2,
				autoclose: true,
				minuteStep: 5,
				format: "yyyy-mm-dd"
			});
			
			$("#toDateTmp").datetimepicker({
				language: 'zh-CN',
				todayHighlight: 'true',
				todayBtn: 'linked',
				minView: 4,
				startView: 2,
				autoclose: true,
				minuteStep: 5,
				format: "yyyy-mm-dd"
			});
		} else if (Type == "month") {
			$("#fromDateTmp").datetimepicker({
				language: 'zh-CN',
				todayHighlight: 'true',
				todayBtn: 'linked',
				minView: 3,
				startView: 3,
				autoclose: true,
				format: "yyyy-mm"
			});
			
			$("#toDateTmp").datetimepicker({
				language: 'zh-CN',
				todayHighlight: 'true',
				todayBtn: 'linked',
				minView: 3,
				startView: 3,
				autoclose: true,
				format: "yyyy-mm"
			});
		};
	};
	
	ChangeDateSelect($("#type option:selected").val());
	$("#type").change(function(e) {
		ChangeDateSelect($("#type option:selected").val());
		$("#fromDateTmp").val("");
		$("#toDateTmp").val("");
	});
});


</script>
</head>
<body  class="SubPage">
<div class="container-fluid">
			<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/statistics/orderStatisticsSearch.action">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
				
				<div class="ControlBlock">
				<div class="row SelectBlock">
			
					 <div class="col-sm-4 col-xs-6">
						<div class="form-group">
						 	<label for="rechargeCard.channel" class="col-xs-4 control-label">类型：</label>
						 	<div class="col-xs-8">
						 		<select class="form-control" name="type" id="type">
									<option value='day' <ww:if test='"day"==type'>selected</ww:if>>日统计</option>
									<option value='month' <ww:if test='"month"==type'>selected</ww:if>>月统计</option>
								</select>
							</div>
						 </div>
						 
						
					 </div>

						<div class="col-xs-4">
							<div class="form-group">
								<label for="fromDateTmp" class="col-xs-5 control-label">起始日期</label>
								<div class="col-xs-7">
									<input class="form-control TimeSelect" name="fromDate" id="fromDateTmp"   type="text" value="<ww:if test='type=="day"'><ww:property value="transDate10String(fromDate)"/></ww:if><ww:elseif test='type=="month"'><ww:property value="transDate6String(fromDate)"/></ww:elseif>" >
								</div>
							</div>
						</div>
						
						<div class="col-xs-4">
							<div class="form-group">
								<label for="fromDateTmp" class="col-xs-5 control-label">结束日期</label>
								<div class="col-xs-7">
									<input class="form-control TimeSelect" name="toDate" id="toDateTmp"   type="text" value="<ww:if test='type=="day"'><ww:property value="transDate10String(toDate)"/></ww:if><ww:elseif test='type=="month"'><ww:property value="transDate6String(toDate)"/></ww:elseif>" >
								</div>
							</div>
						</div>

				
					 
				 </div>
				 	
				 <div class="row SubmitButtonBlock">
					<div class="col-sm-2  col-sm-offset-4 col-xs-6"><a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
					<!--  <div class="col-sm-2 col-xs-6"><a class="btn btn-block Button2" onclick="downloadEntity();" target="_blank"><i class="fa fa-floppy-o"></i>导出表格</a></div>-->
				</div>
			</div>
				
			
			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						
						<td  >
							日期
						</td>
						<td  >
							总订单数
						</td>
						<td  >
							总金额
						</td>
						<td  >
							时租订单数
						</td>
						<td  >
							时租总金额
						</td>
						<td  >
							日租订单数
						</td>
						<td  >
							日租总金额
						</td>
						
						
						<td  nowrap width=157>
							操作
						</td>
					</tr>
			
					<ww:iterator value="orderStatisticsMap" id="data" status="rl">
						<tr
							
							 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							
							<td >
								<ww:property value="key" />
							</td>
							<td ><ww:property value="value.orderTotalNum" /></td>
							<td ><ww:property value="formatAmount(value.orderTotalFee)" /></td>
							
							<td ><ww:property value="value.hourOrdersTotalNum" /></td>
							<td ><ww:property value="formatAmount(value.hourOrdersTotalFee)" /></td>
							
							<td ><ww:property value="value.dayOrdersTotalNum" /></td>
							<td ><ww:property value="formatAmount(value.dayOrdersTotalFee)" /></td>
							
					
							<td><ww:if test="hasPrivilegeUrl('/statistics/orderStatisticsDetail.action')">
									<div onclick="javascript:showEntity('<ww:property value="key"/>');" class="pan_btn1" >详情</div>
								</ww:if>	
							</td>
							
						</tr>
					</ww:iterator>
					
				</table>
			</div>
			</form>
			</div>
</body>
</html>