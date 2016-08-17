<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%String path = request.getContextPath(); %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>500 - 华泰租车</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>

<link rel="stylesheet" href="<%=path%>/mobile/common/css/404.css">
</head>

<body>
<%-- <%@include file="/portal/pages/common/page_header.jsp" %> --%>

<div class="MainContent">
	<div class="container">
		<div class="col-sm-6">
			<div class="col-sm-6 col-sm-offset-6">
				<img class="img-responsive center-block" src="<%=path%>/portal/common/images/404/title.png">
			</div>
		</div>
		<div class="col-sm-6">
			<p><b>sorry 服务器内部错误</b></p>
			<p>您可以通过以下方式继续访问……</p>
			<a class="btn btn-lg ReturnButton" href="<%=path %>" role="button">返回首页</a>
		</div>
	</div>
</div>

<%-- <%@include file="/portal/pages/common/page_bottom.jsp" %> --%>

</body>
</html>
