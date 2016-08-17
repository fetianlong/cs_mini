<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>车辆信息</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>

<link rel=stylesheet href="<%=path %>/mobile/common/css/carinfo.css">
<script type="text/javascript">
function backToOrder(){
	window.location.href='<%=path%>/mobile/toCurrentOrder.action';
}
</script>
</head>

<body>
<div class="CarImgBlock">
	<img class="img-responsive center-block CarImg" src="<%=path%>/vehiclemodelimgs/microweb/<ww:property value="orders.car.carVehicleModel.microWebModelPhoto" />">
</div>

<div class="container-fluid">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<h3 class="text-center CarNum"><ww:property value="orders.car.vehiclePlateId" /></h3>
			<table class="table table-condensed table-responsive">
				<tbody>
					<tr>
						<td>车辆型号</td>
						<td><ww:property value="orders.car.nickName" />(<ww:property value="orders.vehicleModelName" />)</td>
					</tr>
					<tr>
						<td>取车时间</td>
						<td><ww:property value="orders.beginTimeStr" /></td>
					</tr>
					<tr>
						<td>取车网点名称</td>
						<td><ww:property value="carBranchDot.name" /></td>
					</tr>
					<tr>
						<td>取车地点</td>
						<td><ww:property value="carBranchDot.address" /></td>
					</tr>
					<tr>
						<td>还车地点</td>
						<td><ww:property value="getDotNameById(orders.ordersBackSiteId)" /></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<div class="BottomButton">
	<div class="col-xs-8 col-xs-offset-2">
		<button type="submit" onclick="backToOrder();" class="btn btn-embossed btn-primary btn-block">确定</button>
	</div>
</div>

</body>
</html>
