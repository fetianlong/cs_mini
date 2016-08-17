<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>注册成功</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>
<link rel=stylesheet href="<%=path %>/mobile/common/css/register.css">
</head>

<body class="Registered">
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<p class="SuccessTip"><span>恭喜您</span></p>
		<h3>注册成功！</h3>
		<div class="BottomButton">
        	<p class="text-center Tips">实名认证后方可租车，请在个人中心中，进行认证</p>
			<div class="col-xs-8 col-xs-offset-2">
			<a  href="<%=path %>/mobile/subscriber/showBaseInfo.action" >
				<button type="button"class="btn btn-embossed btn-primary btn-block">完成</button>
			</a>
			</div>
		</div>
	</div>
  </div>
</div>

</body>
</html>
