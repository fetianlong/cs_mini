<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>呼叫客服</title>
<%@ include file="/mobile/pages/common/common_head.jsp"%>

<link rel="stylesheet" href="<%=path%>/mobile/common/css/service.css">
<script>
$(function(){
	$(".BottomButton").css("bottom", ($(window).height() - $(".IconBlock").height() - $(".BottomButton").height())/2);
	$(window).resize(function(){
	   $(".BottomButton").css("bottom", ($(window).height() - $(".IconBlock").height() - $(".BottomButton").height())/2);
	});
});
</script>
</head>

<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-md-4 col-md-offset-4">
			<div class="row IconBlock">
                <div class="CircleBlock"><div class="Circle Circle1"></div></div>
                <div class="CircleBlock"><div class="Circle Circle2"></div></div>
                <div class="CircleBlock"><div class="Circle Circle3"></div></div>
                <div class="IconImgBlock"><img class="img-responsive center-block IconImg" src="<%=path%>/mobile/common/images/service/Icon.png"></div>
			</div>
            <div class="BottomButton">
				<div class="col-xs-8 col-xs-offset-2">
					<a class="btn btn-embossed btn-primary btn-block" href="tel:010-57862214">呼叫客服</a>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
