<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/portal/pages/common/include.jsp" %>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />


<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>订单评价 - <%=siteName %></title>

<%@ include file="/portal/pages/common/common_head.jsp"%>


<link rel="stylesheet" href="<%=path%>/portal/common/css/center.css">
<link rel="stylesheet" href="<%=path%>/portal/common/css/ratewrite.css">

<script src="<%=path%>/portal/common/js/star/js/jquery.raty.min.js"></script>


<script>
$(function(){
	$('#star').raty({
		path: "<%=path%>/portal/common/js/star/doc/img/",
		cancelOff: 'cancel-off-big.png',
		cancelOn: 'cancel-on-big.png',
		size: 36,
		starHalf: 'star-half-big.png',
		starOff: 'star-off-big.png',
		starOn: 'star-on-big.png',
		target : '#target',
		targetKeep   : true,
		hints: ["很差","失望","一般","满意","惊喜"],
		click: function(score, event){
			$("#score").val(score);
		}
	});
});

function updateComment(){
	var score = $('#score').val();
	var commentContent = $('#commentContent').val();
	if(score == ""){
		alert("请您对订单进行评分!");
	}else if(commentContent == ""){
		alert("请您填写评论!");
	}else{
		$.ajax({
			type: "POST",
		    url: "<%=path%>/portal/orders/portalUpdateOrdersComment.action",
		    dataType:'json',
		    data:$('#commentForm').serialize(),
		    success: function(data) {
		    	if(0==data.result){
		    		alert("您的评论已经提交，等待管理员审核!");
					window.setTimeout("window.location='<%=path%>/portal/orders/portalOrdersSearch.action'",500); 
			    }
		    }
		});
	}
}

</script>
</head>

<body>
<%@include file="/portal/pages/common/page_header.jsp" %>

<div class="MainContent">
	<div class="container">
    	<img class="img-responsive center-block TitleImg" src="<%=path%>/portal/common/images/ratewrite/title.png">
        <!--个人中心公共控制-->
        <%String pageindexpage = "myorder"; %>
         <%@include file="/portal/pages/common/center_left.jsp" %>
		
        <!--独立内容-->
        <div class="col-sm-9 ContentBlock">
			<form name="commentForm" id="commentForm" method="post" action="" class="form-horizontal">
			<h3 class="SinglePageCaption">评分
				<span id="star"></span>
				<span id="target"></span>
			</h3>
			<input type="hidden" id="score" name="ordersComment.score" value="">
			<input type="hidden" id="ordersNo" name="ordersComment.ordersNo" value="<ww:property value="ordersno"/>">
			<textarea id="commentContent" name="ordersComment.commentContent" class="form-control" rows="10" placeholder="谈谈您这次的驾驶体验吧~！"></textarea>
			<div class="col-md-2 col-md-offset-10 col-sm-4 col-sm-offset-8">
				<input class="btn btn-block SubmitButton" type="button" onclick="updateComment()" value="提交">
			</div>
			</form>
		</div>
    </div>
</div>

 <%@include file="/portal/pages/common/page_bottom.jsp" %>

</body>
</html>
