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
	$('#sform').validate({
		rules: {
			"orderDoc": {
				required: true
			}
		},
		messages: {
			"orderDoc": {
				required: "请选择订单！"
			}
		}
	});
})
function searchEntity(){
	$("#sform").submit();
}
function isValid(){
	if ($("#sform").valid()){
		return true;
	}else{
		return false;
	}
}
var api = frameElement.api, W = api.opener;
var orderAccidentEditDoc = W;
api.button({
    id:'valueOk',
	name:'确定',
	callback:ok,
	focus: true
});
api.button({
    id:'valueCancel',
	name:'取消'
});

function ok()
{
	if(isValid()){
		var str=$("input[name='orderDoc']:checked").val();
		if(typeof(str)=="undefined"){
			alert("请选择订单");
		}
		if(str!=""){
			var orderDoc=str.split(",");
			orderAccidentEditDoc.$("#orderId").val(orderDoc[0]);
			orderAccidentEditDoc.$("#orderCode").val(orderDoc[1]);
			orderAccidentEditDoc.$("#carId").val(orderDoc[2]);
			orderAccidentEditDoc.$("#vehiclePlateId").val(orderDoc[3]);
			orderAccidentEditDoc.$("#memberId").val(orderDoc[4]);
			orderAccidentEditDoc.$("#memberName").val(orderDoc[5]);
		}
	}else{
		return false;
	}
};

function confirmTr(tr){
	var str = $(tr).find("input[name='orderDoc']").val();
	if(str!=""){
		var orderDoc=str.split(",");
		orderAccidentEditDoc.$("#orderId").val(orderDoc[0]);
		orderAccidentEditDoc.$("#orderCode").val(orderDoc[1]);
		orderAccidentEditDoc.$("#carId").val(orderDoc[2]);
		orderAccidentEditDoc.$("#vehiclePlateId").val(orderDoc[3]);
		orderAccidentEditDoc.$("#memberId").val(orderDoc[4]);
		orderAccidentEditDoc.$("#memberName").val(orderDoc[5]);
// 		orderAccidentEditDoc.$('#carIdSearchInp').attr('readonly','readonly');
// 		orderAccidentEditDoc.$('#memberIdSearchInp').attr('readonly','readonly');
// 		orderAccidentEditDoc.$('#vehiclePlateId').attr('readonly','readonly');
// 		orderAccidentEditDoc.$('#memberName').attr('readonly','readonly');
	}else{
		return false;
	}
	api.close();
}
</script>
</head>
<body class="SubPage">
<div class="container-fluid">
		<form name="sform" class="form-horizontal" id="sform" method="post" action="<%=path%>/orders/ordersSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
			<input type="hidden" name="state" value="<ww:property value="state"/>">
			<input type="hidden" name="query" value="<ww:property value="query"/>">
			
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-xs-6">
						<div class="form-group">
							<label for="carViolation.code" class="col-xs-5 control-label">订单编号：</label>
							<div class="col-xs-7">
					    		 <input class="form-control" type="text" name="orders.ordersNo"  value='<ww:property value="orders.ordersNo"/>'/>
					    	</div>
					    </div>
					</div>
				</div>
				<div class="row SubmitButtonBlock">
					<div class="col-xs-4 col-xs-offset-4"><a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
	  			</div>
	  		</div>
			
			
			
			
			
			
<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed" >
					<tr class="ths" id="tab_bg_cl">
						<td height="28" width="40" align="center" nowrap>
						</td>
						<td  align="center" nowrap>
							<a href="javascript:SetOrder('ordersNo')">订单编号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td   align="center" nowrap >
							<a href="javascript:SetOrder('state')">订单状态<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td  align="center" nowrap >
							<a href="javascript:SetOrder('ordersTime')">取车时间<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td  align="center" nowrap >
							支付方式
						</td>
						<td  align="center" nowrap >
							承租人
						</td>
						<td  align="center" nowrap width="100">
							操作
						</td>
					</tr>
					<ww:iterator value="page.results" id="data" status="rl">
						<tr  ondblclick="confirmTr(this);"
							<ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							<td align="center">
								<input type="radio" name="orderDoc" value="<ww:property value="id" />,<ww:property value="ordersNo" />,<ww:property value="carId" />,<ww:property value="plateNumber" />,<ww:property value="memberId" />,<ww:property value="memberName" />" />
							</td>
							<td align="left">
								<ww:property value="ordersNo" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByCode('14',state)" />
							</td>
							<td align="left">
								<ww:property value="transDate12String(ordersTime)" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByCode('12',payStyle)" />
							</td>
							<td align="left">
								<ww:property value="getMemberName(memberId)" />
							</td>
							<td align="center">
								<!-- <div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>','update');">编辑</div> -->
							</td>
						</tr>
					</ww:iterator>
					<tr style="background-color: #fff;height: 30px;">
						<td align="center" colspan="9">
							<ww:property value="page.pageSplit" />
						</td>
					</tr>
				</table>
			</div>
		</form>
</div>
</body>
</html>