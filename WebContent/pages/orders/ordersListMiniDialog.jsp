<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%
	String path = request.getContextPath();
%>

<form class="form-horizontal" name="sform" id="sform" method="post"	>
			<input type="hidden" name="orders.memberId" value="<ww:property value="orders.memberId" />">
			<input type="hidden" name="orders.carId" value="<ww:property value="orders.carId" />">
			
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						
						<td><a href="javascript:SetOrder('ordersNo')">订单编号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a></td>
						<td><a href="javascript:SetOrder('state')">订单状态<img src="<%=path%>/admin/common/images/main/paixu.png"/></a></td>
						<td>租用车型</td>
						<td>租用车辆</td>
						<td><a href="javascript:SetOrder('ordersTime')">取车时间<img src="<%=path%>/admin/common/images/main/paixu.png"/></a></td>
						<td>支付方式</td>
						<td>承租人</td>
						
					</tr>
					<ww:iterator value="page.results" id="data" status="rl">
						<tr <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							<td align="center"><ww:property value="ordersNo" /></td>
							<td align="center"><ww:property value="#dictUtil.getCnNameByCode('14',state)" /></td>
							<td align="center"><ww:property value="vehicleModelName" /></td>
							<td align="center"><ww:property value="plateNumber" /></td>
							<td align="center"><ww:property value="transDate12String(ordersTime)" /></td>
							<td align="center"><ww:property value="@com.dearho.cs.account.pojo.Account@getPayType(payType)" /></td>
							<td align="center"><ww:property value="memberName" /></td>
						</tr>
					</ww:iterator>
					<tr>
						<td align="right" colspan="7"><ww:property
								value="page.pageSplit" /></td>
					</tr>
				</table>
</form>