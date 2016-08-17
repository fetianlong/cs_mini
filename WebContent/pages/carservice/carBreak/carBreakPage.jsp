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

<title>车辆损坏上报</title>

<%@ include file="/pages/common/common_head.jsp"%>


<script type="text/javascript">
	$(function(){
 
	});
	
	
	function searchEntity(){
		$("#sform").submit();
	}

	function showDetailForDialog(id,path){
		
		var dialoguser = $.dialog({
		    id:'subscriberdailogid', 
		    title:'车辆损坏上报详情',
		    content : "url:"+path+"/carservice/carBreakDetail.action?carBreak.id="+id,
			resize:false,
			fixed:true,
			width:800,
			height:500,
		    lock: true,
	 		max: false,
		    min: false,

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
	}
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
	
	
		<form name="sform" id="sform" class="form-horizontal" method="post" action="<%=path%>/carservice/carbreak.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
			<input type="hidden" name="state" 
					value="<ww:property value="state"/>">
			<input type="hidden" name="query" 
					value="<ww:property value="query"/>">
					
					
					
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-xs-4">
					
						<div class="form-group">
							<label for="orders.ordersNo" class="col-xs-4 control-label">订单编号</label>
							<div class="col-xs-8">
								<input class="form-control" name="carBreak.orderNumber"
									id="carBreak.orderNumber" type="text"
									value="<ww:property value="carBreak.orderNumber"/>">
							</div>
						</div>
						
					</div>
					<div class="col-xs-4">
						<div class="form-group">
							<label for="endTime" class="col-xs-4 control-label">用户名称</label>
							<div class="col-xs-8">
							<input class="form-control" name="carBreak.subscriberName"
									id="carBreak.subscriberName" type="text"
									value="<ww:property value="carBreak.subscriberName"/>">	
							</div>
						</div>
					</div>
					
					<div class="col-xs-4">
						<div class="form-group">
							<label for="endTime" class="col-xs-4 control-label">车牌号</label>
							<div class="col-xs-8">
								<input class="form-control" name="carBreak.carNumber"
									id="carBreak.carNumber" type="text"
									value="<ww:property value="carBreak.carNumber"/>">	
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
						<td  align="center" nowrap width="100">
							<a href="javascript:SetOrder('orderNumber')">订单号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td  align="center" nowrap width="100">
							<a href="javascript:SetOrder('subscriberName')">提交人<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td  align="center" nowrap width="80">
							车牌号
						</td>
						<td   align="center" nowrap width="100">
							外表损坏
						</td>
						<td   align="center" nowrap width="100">
							轮胎损坏
						</td>
						<td  align="center" nowrap width="100">
							内饰损坏
						</td>
						<td  align="center" nowrap width="100">
							玻璃损坏
						</td>
						
						<td  align="center" nowrap width="100">
							内部异味
						</td>
						<td  align="center" nowrap width="100">
							其他损坏
						</td>
						<td  align="center" nowrap width="100">
							其他信息
						</td>
						
					</tr>
					<ww:iterator value="page.results" id="data" status="rl">
						<tr ondblclick="confirmTr(this);"
							
							 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							 
							<td align="center">
								<a href="javascript:showOrderDetailForDialog('<ww:property value="orderId" />','<%=path%>')"><ww:property value="orderNumber" /></a>
							</td>
							
							<td align="left">
								<a href="javascript:showSubscriberDetailForDialog('<ww:property value="subscriberId" />','<%=path%>')"><ww:property value="subscriberName" /></a>
							</td>
							<td align="left">
								
								<a href="javascript:showCarDetailForDialog('<ww:property value="carId" />','<%=path%>')"><ww:property value="carNumber" /></a>
								
							</td>
							<td align="left">
								<ww:if test="breakTypeFacade==1">报损</ww:if>
							</td>
							<td align="left">
								
								<ww:if test="breakTypeTyre==1">报损</ww:if>
								
								
							</td>
							<td align="center">
								<ww:if test="breakTypeDecoration==1">报损</ww:if>
							</td>
							<td align="left">
								<ww:if test="breakTypeGlass==1">报损</ww:if>
							</td>
							<td align="left">
								<ww:if test="breakTypeOdor==1">报损</ww:if>
							</td>
							
							<td align="left">
								<ww:if test="breakTypeOther==1">报损</ww:if>
							</td>
							<td align="left">
								<a href="javascript:showDetailForDialog('<ww:property value="id" />','<%=path%>')">详细</a>
							</td>
						</tr>
					</ww:iterator>
					<tr style="background-color: #fff;height: 30px;">
						<td align="center" colspan="10">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>