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

function searchEntity(){
	$("#sform").submit();
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
			

			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						
						<td><a href="javascript:SetOrder('ordersNo')">订单编号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a></td>
						<td>开始时间</td>
						<td>结束时间</td>
						<td>订单状态</td>

						<td>承租人</td>
						<td>费用</td>
					</tr>
					<ww:iterator value="page.results" id="data" status="rl">
						<tr <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							<td align="center">
								<ww:property value="ordersNo" />
							</td>
							<td align="center"><ww:property value="transDate12String(endTime)" /></td>
							<td align="center"><ww:property value="transDate12String(endTime)" /></td>
							<td align="center"><ww:property value="#dictUtil.getCnNameByCode('14',state)" /></td>
						
							<td align="center"><ww:property value="memberName" /></td>
							<td align="center"><ww:property value="formatAmount(totalFee)" /></td>
						</tr>
					</ww:iterator>
						<tr <ww:if test="#rl.even"> class="trs"</ww:if>>
							<td align="right" colspan="6">
								合计<ww:property value="formatAmount(#request.totelFee)"/>
								
							</td>
							
						</tr>
					<tr>
						<td align="right" colspan="8"><ww:property
								value="page.pageSplit" /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>