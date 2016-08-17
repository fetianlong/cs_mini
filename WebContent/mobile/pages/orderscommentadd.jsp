<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>订单评论</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>

<script src="<%=path%>/mobile/common/js/star/js/jquery.raty.min.js"></script>
<link rel="stylesheet" href="<%=path%>/mobile/common/css/ratewrite.css">
<script>
$(function(){
	$('#star').raty({
		path: "<%=path%>/mobile/common/js/star/doc/img/",
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
			$('input[name="ordersComment.score"]').val(Number(score));
		}
	});
	
});

function updateComment(){
	var score = $('input[name="ordersComment.score"]').val();
	var commentContent = $('input[name="ordersComment.commentContent"]').val();
	if(score == ""){
		Alert("请您对订单进行评分!");
	}else if(commentContent == ""){
		Alert("请您填写评论!");
	}else{
		$.ajax({
			type: "POST",
		    url: "<%=path%>/mobile/addComment.action",
		    dataType:'json',
		    data:$('#commentForm').serialize(),
		    success: function(data) {
		    	if(0==data.result){
		    		alert("您的评论已经提交，等待管理员审核!");
					window.setTimeout("window.location='<%=path%>/mobile/toOrderList.action'",500); 
			    }
		    },
			error: function(){
				Alert("暂时无法提交评论，请稍后再试");	
			}
		});
	}
}
</script>
</head>

<body>
<form id="commentForm">
<input type="hidden" name="ordersComment.ordersId" value="<ww:property value="ordersId" />"/>
<input type="hidden" name="ordersComment.score" />
<div class="OneBlock PingfenBlock">
	评分：
	<span id="star"></span>
	<span id="target" class="pull-right"></span>
</div>

<div class="OneBlock">
	<textarea name="ordersComment.commentContent" class="form-control" rows="10" placeholder="谈谈您这次的驾驶体验吧~！"></textarea>
</div>
<div class="col-xs-8 col-xs-offset-2">
	<a  onclick="updateComment();" class="btn btn-embossed btn-primary btn-block SubmitButton">提交</a>
</div>
</form>
</body>
</html>
