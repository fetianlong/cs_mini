<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>预定成功</title>


<%@ include file="/mobile/pages/common/common_head.jsp"%>



<link rel=stylesheet href="<%=path %>/mobile/common/css/bookingcar.css">

<script>
var TimeDistance = 0;
$(function(){
	ShowLoading2(true);
	$.ajax({
		async: true,
		data: {'ordersId':$('#ordersId').val()},
		type: "POST",
		url: "<%=path %>/mobile/getGetCarRemainTime.action",
		dataType: "json",
		success: function(data){
			if(data.result == 0){
				TimeDistance = data.info;
			}
		},
		error: function(){
			Alert("无法获取剩余时间，请稍后再试");
		},
		complete: function(){
			ShowLoading2(false);
		}
	});
});

var StartDate = new Date();
Date.prototype.DateAdd = function(strInterval, Number) {  
	var dtTmp = this; 
	switch (strInterval) {  
		case 's' :return new Date(Date.parse(dtTmp) + (1000 * Number)); 
		case 'n' :return new Date(Date.parse(dtTmp) + (60000 * Number)); 
		case 'h' :return new Date(Date.parse(dtTmp) + (3600000 * Number)); 
		case 'd' :return new Date(Date.parse(dtTmp) + (86400000 * Number)); 
		case 'w' :return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number)); 
		case 'q' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number*3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds()); 
		case 'm' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds()); 
		case 'y' :return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds()); 
	}
};
var EndDate = StartDate.DateAdd("s", 1200);

function ShowTime(){
	if (TimeDistance <= 0){
		if($("#isPrePay").val()=="1"){
			$(".LastTime").html("<p class='CancelTip'>订单已经开启，请尽快用车！</h4>");
			$(".KaimenButton").hide();
			$(".Tips").hide();
			$(".YudingButton").css("display", "block");
			clearInterval(TimeShow);
		}else{
			$(".LastTime").html("<p class='CancelTip'>订单已超时，请重新预定！</h4>");
			$(".KaimenButton").hide();
			$(".Tips").hide();
			$(".YudingButton").css("display", "block");
			clearInterval(TimeShow);
		}
	} else {
		var nH = Math.floor(TimeDistance/(1000*60*60) % 24);
		var nM = Math.floor(TimeDistance/(1000*60)) % 60;
		var nS = Math.floor(TimeDistance/1000) % 60;
		if (nH < 10){
			nH = "0" + nH.toString();
		};
		if (nM < 10){
			nM = "0" + nM.toString();
		};
		if (nS < 10){
			nS = "0" + nS.toString();
		};
		
		if(nH == "00"){
			$(".LastTime .Time").html(nM + "<span>分</span>" + nS+"<span>秒</span>");
		}else{
			$(".LastTime .Time").html(nH +"<span>时</span>"+nM + "<span>分</span>" + nS+"<span>秒</span>");
		}
		
	};
	TimeDistance -= 1000;
	
};

var TimeShow = setInterval("ShowTime()",1000);
function openDoor(){
	ShowLoading(true);
	$.ajax({
		async: true,
		data: {'ordersId':$('#ordersId').val()},
		type: "POST",
		url: "<%=path %>/mobile/startUseCar.action",
		dataType: "json",
		success: function(data){
			if(data.result == 0){
				$.ajax({
					async: false,
					data: {'ordersId':$('#ordersId').val()},
					type: "POST",
					url: "<%=path %>/mobile/openDoor.action",
					dataType: "json",
					success: function(data){
						if(data.result == 0){
							Alert("车已开门，请尽快取车");
							window.location.href='<%=path%>/mobile/toCurrentOrder.action';
						}
						else{
							Alert(data.msg+"，请稍后再试");
						}
					},
					error: function(){
						Alert("打开车门失败，请稍后再试。");
					},
					complete: function(){
						ShowLoading(false);
					}
				});
			}
			else {
				Alert(data.msg);
			}
		},
		error: function(){
			Alert("打开车门失败，请稍后再试。");
		},
		complete: function(){
			ShowLoading(false);
		}
		
	});
// 	var lf = checkLocation();
// 	if(!lf){
// 		Alert("请您在车边50范围内进行操作");
// 		return;
// 	}
	
	
}


function cancelOrder(){
	if(confirm("确认取消订单?")){
		ShowLoading(true);
		$.ajax({
			async: false,
			data: {'id':$('#ordersId').val()},
			type: "POST",
			url: "<%=path %>/mobile/cancelOrder.action",
			dataType: "json",
			success: function(data){
				if(data.result == 0){
					Alert(data.msg);
					window.location.href="<%=path %>/mobile/toOrderList.action"; 
				}else {
					Alert(data.msg);
				}
			},
			error: function(){
				Alert("取消订单失败，请稍后再试");
			},
			complete: function(){
				ShowLoading(false);
			}
		});
	}
	
}
</script>
</head>

<body class="Yuding3">
<div class="container-fluid">
	<div class="row CarImgBlock">
		<div class="col-xs-8 col-xs-offset-2">
			<img class="img-responsive center-block CarImg" src="<%=path%>/vehiclemodelimgs/microweb/<ww:property value="carImg" />">
		</div>
	</div>

  <div class="row">
    <div class="col-md-4 col-md-offset-4">
    	<input id="ordersId" type="hidden" value="<ww:property value="orders.id" />"/>
    	<input id="isPrePay" type="hidden" value="<ww:property value="ordersDetail.isPrePay" />"/>
		<h3 class="text-center"><ww:property value="orders.car.vehiclePlateId" /></h3>
		<p class="Didian"><ww:property value="choseCarBranchDot.address" /></p>
        <ww:if test='ordersDetail.isPrePay=="1"'><p class="ShengyuTip" id="restTimeDesc">距离订单开始时间</p></ww:if>
		<ww:else><p class="ShengyuTip" id="restTimeDesc">取车剩余时间</p></ww:else>
		<div class="row LastTime">
			<div class="col-xs-12">
				<h1 class="Time">--<span>分</span>--<span>秒</span></h1>
			</div>
		</div>
		<a class="btn btn-link btn-sm CancelButton" href="javascript:cancelOrder()" role="button">取消订单<i class="fa fa-angle-double-right"></i></a>
		<div class="BottomButton">
			<p class="Tips">到达取车地点后，开锁取车</p>
			<div class="col-xs-8 col-xs-offset-2">
				<button type="button" onclick="openDoor()" class="btn btn-embossed btn-primary btn-block KaimenButton">开车门</button>
				<a href="<%=path %>/mobile/toBookCar.action" class="btn btn-embossed btn-primary btn-block YudingButton">重新预定</a>
			</div>
		</div>
	</div>
  </div>
</div>

</body>
</html>
