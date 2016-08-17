<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>选择充电</title>
<%@ include file="/mobile/pages/common/common_head.jsp"%>

<link rel=stylesheet href="<%=path %>/mobile/common/css/qrcode.css">

<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

<script>
$(function(){
wx.config({
debug: true,    
appId: '<ww:property value="appId"/>',    
timestamp: '<ww:property value="timestamp"/>',    
nonceStr: '<ww:property value="nonceStr"/>',    
signature: '<ww:property value="signature"/>',    
jsApiList: ['scanQRCode']    
});  

wx.ready(function () {
	  document.querySelector('#scanQRCode').onclick = function () {
		    wx.scanQRCode({
		      needResult: 1,
		      desc: 'scanQRCode desc',
		      success: function (res) {
		    	 var result = res.resultStr;
		    	 Alert(result);
		      }
		    });
		  };
	});
});
</script>
</head>

<body>
<div class="container">
<div class="Icon">
	<i class="fa fa-battery-full fa-rotate-270 fa-2x"></i>
</div>

<div class="ButtonBlock">
	<div class="row">
		<div class="col-xs-8 col-xs-offset-2">
			<button class="btn btn-primary btn-embossed btn-block" id="scanQRCode">扫描二维码</button>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-8 col-xs-offset-2">
			<a class="btn btn-primary btn-embossed btn-block" href="">附近充电站</a>
		</div>
	</div>
</div>

<div class="row ShuomingBlock">
	<div class="col-xs-3">
		<img class="img-respond center-block" src="<%=path %>/mobile/common/images/qrcode/01.png">
		<p>1.寻找充电站</p>
		<i class="fa fa-angle-double-right"></i>
	</div>
	<div class="col-xs-3">
		<img class="img-respond center-block" src="<%=path %>/mobile/common/images/qrcode/02.png">
		<p>2.选择充电桩</p>
		<i class="fa fa-angle-double-right"></i>
	</div>
	<div class="col-xs-3">
		<img class="img-respond center-block" src="<%=path %>/mobile/common/images/qrcode/03.png">
		<p>3.插上充电枪</p>
		<i class="fa fa-angle-double-right"></i>
	</div>
	<div class="col-xs-3">
		<img class="img-respond center-block" src="<%=path %>/mobile/common/images/qrcode/04.png">
		<p>4.完成</p>
	</div>
</div>
</div>
</body>
</html>
