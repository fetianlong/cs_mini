<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>欢迎使用</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>
<link rel=stylesheet href="<%=path %>/mobile/common/css/welcome.css">
<script>
	setTimeout(function(){
		location.href="<%=path %>/mobile/toBookCar.action";
	},1000);
</script>
</head>

<body>
<div class="container-fluid">
	<div class="row LabelBlock">
    	<div class="col-xs-12">
        	<h2>Welcome!</h2>
            <p>华泰电动车分时租赁</p>
        </div>
    </div>
    <div class="row ImgBlock">
    	<div class="col-xs-12">
        	<img class="img-responsive center-block" src="<%=path%>/mobile/common/images/welcome/car.png">
        </div>
    </div>
</div>

</body>
</html>
