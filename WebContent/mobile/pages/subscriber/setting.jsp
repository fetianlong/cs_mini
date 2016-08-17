<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html >
<head>
<title>设置</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>


<link rel=stylesheet href="<%=path %>/mobile/common/css/changeinfo.css">

</head>

<body class="MenuPage">
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<ul class="ChangeUl">
			<%-- <li><a class="btn btn-block" href="<%=path%>/mobile/pages/subscriber/changepassword.jsp">修改手机登录密码<i class="fa fa-angle-right pull-right"></i></a></li> --%>
			<li><a class="btn btn-block" href="<%=path%>/mobile/pages/subscriber/changephone.jsp">修改绑定手机号<i class="fa fa-angle-right pull-right"></i></a></li>
		</ul>
		
	</div>
  </div>
</div>

</body>
</html>
