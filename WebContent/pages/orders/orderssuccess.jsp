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
<title>订单成功</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	function ok(){
		window.location.href="<%=path%>/subscriber/centerIndex.action";
	}
</script>
</head>
<body>
	<ww:include page="/pages/subscriber/header.jsp"></ww:include>
	<div class="center">
      <div class="qrdd">订单成功</div>
      <div class="ydcg"><li class="yd_fontsize">预定成功！</li><li>请您在<ww:property value="transDate12String(orders.ordersTime)" />前去指定网点取车，否则本次订单将自动取消。</li></div>
      <div class="ddh">订单号：<ww:property value="orders.ordersNo" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;承租人： <ww:property value="#session.subscriber.name"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
       <img class="right_qiche" src="<%=path %>/common/css/images/qic.png" />
      </div>
      <div class="yclct">
      	<img  src="<%=path %>/common/css/images/yclechengtu.jpg" />
      </div>
      <div style="text-align: right;margin-bottom: 30px;margin-right:170px; ">
      		<input type="button" onclick="ok();" value="确定" style="width: 100px;height: 30px; background-color: #1dd2af" />
      </div>
</div>
</body>
</html>