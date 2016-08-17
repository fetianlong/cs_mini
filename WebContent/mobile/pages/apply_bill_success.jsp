<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>索取成功</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>

<link rel="stylesheet" href="../mobile/common/css/fapiaolist.css">
<script>
function toAccount(){
	window.location.href="<%=path%>/mobile/account/index.action";
}
</script>
</head>

<body>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<div class="row SuccessTipBlock">
			<img class="img-responsive center-block" src="../mobile/common/images/fapiao/ok.png">
			<h4>索取成功</h4>
			<p class="pull-right">快递寄送<img class="CarImg" src="../mobile/common/images/fapiao/car.png"></p>
		</div>
		
		<div class="row SuccessLabelBlock text-center">
			<h4>收货人：<ww:property value="subscriber.name"/></h4>
			<p><i class="fa fa-phone"></i>联系电话：<ww:property value="subscriber.phoneNo"/></p>
			<p><i class="fa fa-map-marker"></i>收件地址：<ww:property value="subscriber.postAddress"/></p>
		</div>
	</div>
  </div>
</div>

<div class="BottomBlock">
	<div class="col-xs-8">
		<div class="pull-right Heiji">合计金额：<i class="fa fa-jpy"></i><span><ww:property value="totalFee"/></span></div>
	</div>
	<div class="col-xs-4">
		<button onclick="toAccount()" class="SubmitButton" type="submit">确定</button>
	</div>
</div>
</body>
</html>
