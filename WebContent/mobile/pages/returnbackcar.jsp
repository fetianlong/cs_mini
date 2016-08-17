<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>还车成功</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>

<link rel=stylesheet href="<%=path%>/mobile/common/css/backcar.css">
</head>

<body>
<div class="CarImgBlock">
	<img class="img-responsive center-block CarImg" src="<%=path%>/vehiclemodelimgs/microweb/<ww:property value="orders.car.carVehicleModel.microWebModelPhoto" />">
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<table class="table table-condensed table-responsive">
				<tbody>
					<tr>
						<td>车辆型号</td>
						<td><ww:property value="orders.car.nickName" />(<ww:property value="orders.vehicleModelName" />)</td>
					</tr>
					<tr>
						<td>还车地点</td>
						<td><ww:property value="getDotNameById(orders.endSiteId)" /></td>
					</tr>
					<tr>
						<td>用车时间</td>
						<td><span><ww:property value="transDate12String(orders.beginTime)" /></span>&nbsp;~<br><span><ww:property value="transDate12String(orders.endTime)" /></span></td>
					</tr>
					<tr>
						<td>共计</td>
						<td><ww:property value="useCarTimeStr" /></td>
					</tr>
					<tr>
						<td>收费</td>
						<td><i class="fa fa-jpy"></i><span><ww:property value="formatAmount(orders.totalFee)" /></span></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<div class="BottomButton">
<p class="text-center Tips">谢谢您使用华泰租车！</p>
	<div class="col-xs-8 col-xs-offset-2">
		<button type="button" onclick="javascript:window.location.href='<%=path %>/mobile/toBookCar.action'" 
		class="btn btn-embossed btn-primary btn-block">返回<span id="timer"></span></button>
	</div>
</div>

</body>
</html>
