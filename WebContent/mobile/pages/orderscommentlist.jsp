<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>历史评论</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>

<link rel=stylesheet href="<%=path%>/mobile/common/css/comment.css">
<script type="text/javascript">
$(function(){
	searchCommentList();
	$(document).scroll(function(e) {
        if ($(document).scrollTop() >= ($(document).height()-$(window).height())) {
        	if(continueFlag){
    			searchCommentList('add');
        	}
		};
    });
});
var continueFlag = true;
function searchCommentList(type){
	var param = {
			'lastCommentId':$('#lastCommentId').val(),
			'carId':$('#carId').val(),
			'type':type
	};
	if(type != null && type == 'add'){
		$.post('<%=path %>/mobile/searchCommentList.action',param,addShowComment,'json');
	}
	else{
		continueFlag = true;
		$.post('<%=path %>/mobile/searchCommentList.action',param,showComment,'json');
	}
	
}
function showComment(data){
	if(data.result == 0){
		$('#ListUl').empty();
		if(data.info != null && data.info.length > 0){
			addComment(data.info);
		}
		else{
			$('#ListUl').append($('<li><div class="OneBlock text-muted">暂无评论</div></li>'));
		}
	}
	else{
		Alert(data.msg);
	}
}
var continueFlag = true;
function addShowComment(data){
	if(data.result == 0){
		if(data.info != null && data.info.length> 0){
			addComment(data.info);
		}
		else{
			continueFlag = false;
		}
	}
	else{
		Alert(data.msg);
	}
}
function addComment(info){
	$.each(info,function(index,comment){
		var htmlstr = '<li>'+
							'<div class="OneBlock">'+
						'<p class="UserInfo">'+
							'<span class="Score">'+
								'<span>';
			if(Number(comment.score) >= 1){
				htmlstr += '<i class="fa fa-star get"></i>';
			}
			else{
				htmlstr += '<i class="fa fa-star"></i>';
			}
			if(Number(comment.score) >= 2){
				htmlstr += '<i class="fa fa-star get"></i>';
			}
			else{
				htmlstr += '<i class="fa fa-star"></i>';
			}
			if(Number(comment.score) >= 3){
				htmlstr += '<i class="fa fa-star get"></i>';
			}
			else{
				htmlstr += '<i class="fa fa-star"></i>';
			}
			if(Number(comment.score) >= 4){
				htmlstr += '<i class="fa fa-star get"></i>';
			}
			else{
				htmlstr += '<i class="fa fa-star"></i>';
			}
			if(Number(comment.score) == 5){
				htmlstr += '<i class="fa fa-star get"></i>';
			}
			else{
				htmlstr += '<i class="fa fa-star"></i>';
			}
									
				    htmlstr += '</span>'+
							'</span>'+
							'<span class="Time">'+comment.commentTimeStr+'</span>'+
							'<span class="pull-right PhoneNum">'+comment.commentPersonPhone+'</span>'+
						'</p>'+
						'<p>'+comment.commentContent+'</p>'+
					'</div>'+
				'</li>';
		
		$('#ListUl').append($(htmlstr));
		if(index == info.length - 1){
			$('#lastCommentId').val(comment.id);
		}
	});
	
}

</script>
</head>

<body>
<div class="CarImgBlock">
	<img class="img-responsive center-block CarImg" src="<%=path%>/vehiclemodelimgs/microweb/<ww:property value="orders.car.carVehicleModel.microWebModelPhoto" />">
</div>

<div class="OneBlock CarNameBlock">
	<h4><ww:property value="orders.car.nickName" />(<ww:property value="orders.vehicleModelName" />)</h4>
	<input type="hidden" id="carId" value="<ww:property value="orders.carId" />"/>
	<input type="hidden" id="lastCommentId" />
	<p class="CarScore">
		<span class="Score">
			<span>
				<!--打分的星星，get类表明已得的，是黄颜色的星星，不加get就是灰色的星星未得的-->
				<ww:if test="avgScore >= 1"><i class="fa fa-star get"></i></ww:if>
				<ww:else><i class="fa fa-star"></i></ww:else>
				<ww:if test="avgScore >= 2"><i class="fa fa-star get"></i></ww:if>
				<ww:else><i class="fa fa-star"></i></ww:else>
				<ww:if test="avgScore >= 3"><i class="fa fa-star get"></i></ww:if>
				<ww:else><i class="fa fa-star"></i></ww:else>
				<ww:if test="avgScore >= 4"><i class="fa fa-star get"></i></ww:if>
				<ww:else><i class="fa fa-star"></i></ww:else>
				<ww:if test="avgScore == 5"><i class="fa fa-star get"></i></ww:if>
				<ww:else><i class="fa fa-star"></i></ww:else>
			</span>
			<span><ww:property value="avgScore" /></span>分
		</span>
		<span class="CommentTime">
			<span><ww:property value="commentCount" /></span>评价
		</span>
	</p>
</div>

<div class="OneBlock JianjieBlock">
	<h4>车辆简介</h4>
	<p class="text-muted">暂无简介</p>
</div>

<h4 class="Caption">驾车体验</h4>
<ul class="ListUl" id="ListUl">
	
</ul>
</body>
</html>
