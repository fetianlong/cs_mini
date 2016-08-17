<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>选择租赁类型</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>
<script src="<%=path %>/portal/common/js/jquery-validation-1.14.0/dist/jquery.validate.js" type="text/javascript" charset="utf-8"></script>

<link rel=stylesheet href="<%=path %>/mobile/common/css/bookingcar.css">


<script>
$(function(){
	$(".switch input[type='checkbox']").bootstrapSwitch();
	$(".FukuanButton button").click(function(e) {
		$("#fukuan").val($(this).attr("value"));
		$(".FukuanButton button").removeClass("active");
		$(".FukuanButton button").removeClass("btn-info");
		$(this).addClass("active");
		$(this).addClass("btn-info");
	});
	
	var soc = '<ww:property value="carInfo.SOC" />';
	var sochtml = '';
	if(Number(soc) >= 70){
		sochtml += '<div class="progress Progress3">';
	}
	else if(Number(soc) >= 30){
		sochtml += '<div class="progress Progress2">';
	}
	else{
		sochtml += '<div class="progress Progress1">';
	}
	for(var i=10;i<=Number(soc);i+=10){
		sochtml += '<div class="progress-bar"></div>';
	}
	
	$(".SelectTypeBlock .TimeSelect").hide();
	$(".SelectTypeBlock .SelectType .btn").click(function(e) {
		$(".SelectTypeBlock .SelectType .btn").removeClass("Selected");
		$(this).addClass("Selected");
		$("#rentTypeId").val($(this).val());
		$("#strategyBaseId").val($(this).attr("strategyBaseId"));
		
		document.getElementById('unitStr').innerHTML = $(this).attr("remark");
		document.getElementById('unitPrice').innerHTML = $(this).attr("price")+$(this).attr("unit");
		document.getElementById('fixedTimeSlotDesc').innerHTML = "时间段："+$(this).attr("fixedTimeStart")+"-"+$(this).attr("fixedTimeEnd");
		
		if ($(this).attr("fixedTimeSlot")==1){
			document.getElementById('fixedTimeSlotDesc').style.display="block";
			document.getElementById('selectTimeDiv').style.display="none";
		}else{
			document.getElementById('fixedTimeSlotDesc').style.display="none";
			document.getElementById('selectTimeDiv').style.display="block";
		}
		
		if ($(this).hasClass("Shizu")){
			$(".SelectTypeBlock .TimeSelect").slideUp();
		} else {
			$(".SelectTypeBlock .TimeSelect").slideDown();
		};
	});
	
	$(".TimeSelect .SelectTime .LeftButton").click(function(e) {
		if (parseInt($(".TimeSelect .SelectTime input").val()) > 1){
			$(".TimeSelect .SelectTime input").val((parseInt($(".TimeSelect .SelectTime input").val()) - 1).toString());
		};
	});
	$(".TimeSelect .SelectTime .RightButton").click(function(e) {
		$(".TimeSelect .SelectTime input").val((parseInt($(".TimeSelect .SelectTime input").val()) + 1).toString());
	});
	$("#strategyType1").click();
});

function sub(){
	$.post('<%=path %>/mobile/confirmChangeRentType.action',$('#rentTypeForm').serialize(),function(data){
		if(data.result == 0){
			window.location.href='<%=path%>/mobile/toPayFee.action?ordersDetailNo='+data.info;
		}else if(data.result == 1){
			Alert(data.msg);
			window.location.href='<%=path%>/mobile/toCurrentOrder.action';
		}else{
			Alert(data.msg);
		}
	},'json');
};

function BorderRun() {
	$(".Tips .BorderTop").css("left", $(".Tips .BorderTop").position().left + 1);
	$(".Tips .BorderBottom").css("left", $(".Tips .BorderBottom").position().left - 1);
	$(".Tips .BorderLeft").css("top", $(".Tips .BorderLeft").position().top - 1);
	$(".Tips .BorderRight").css("top", $(".Tips .BorderRight").position().top + 1);
	if ($(".Tips .BorderTop").position().left >= 0){
		$(".Tips .BorderTop").css("left", "-1000px");
	};
	if ($(".Tips .BorderBottom").position().left <= -1000){
		$(".Tips .BorderBottom").css("left", "0");
	};
	if ($(".Tips .BorderLeft").position().top <= -1000){
		$(".Tips .BorderLeft").css("top", "0");
	};
	if ($(".Tips .BorderRight").position().top >= 0){
		$(".Tips .BorderRight").css("top", "-1000px");
	};
};
setInterval("BorderRun()",50);//1000为1秒钟
</script>

</head>
<body class="Yuding1">
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<form class="form-horizontal" id="rentTypeForm">
			<input type="hidden" name="carId" value="<ww:property value="carInfo.car.id" />">
			<input type="hidden" name="orders.carId" value="<ww:property value="carInfo.car.id" />">
			<input type="hidden" name="rentTypeId" id="rentTypeId" value="">
			<input type="hidden" name="strategyBaseId" id="strategyBaseId" value="">
			
			<div class="Tips">
			</div>
			
			<div class="SelectTypeBlock" >
				<div class="SelectType">
					<div class="btn-group btn-group-sm" role="group" aria-label="...">
					<ww:iterator value="strategyCarShowInfos" id="data" status="rl">
						<button id="strategyType<ww:property value="#rl.index+1" />" type="button" value="<ww:property value="strategyType.id" />" strategyBaseId="<ww:property value="strategyBase.id" />" timeBeforeGet="<ww:property value="strategyBase.timeBeforeGet" />" fixedTimeSlot="<ww:property value="strategyBase.isFixedTimeSlot" />" fixedTimeStart="<ww:property value="strategyBase.fixedTimeStart" />" fixedTimeEnd="<ww:property value="strategyBase.fixedTimeEnd" />" unit="<ww:property value="unitStr" />" price="<ww:property value="price" />" remark="<ww:property value="strategyType.remark" />" class="btn btn-default <ww:if test='strategyType.code=="jishizu"'>Shizu</ww:if>"><ww:property value="strategyType.remark" /></button>
					</ww:iterator>
					</div>
				</div>
				<div class="TimeSelect">
					<div class="row" id="selectTimeDiv">
						<div class="col-xs-3 Label LabelLeft">预租</div>
						<div class="col-xs-6">
							<div class="form-group SelectTime">
								<div class="input-group">
									<div class="input-group-addon LeftButton"><i class="fa fa-angle-left"></i></div>
										<input type="text" class="form-control" name="unitCount" id="unitCount" value="1">
									<div class="input-group-addon RightButton"><i class="fa fa-angle-right"></i></div>
								</div>
							</div>
						</div>
						<div class="col-xs-3 Label" id="unitStr"></div>
					</div>
					
					<div class="row">
						<div class="col-xs-3 Label LabelLeft"></div>
						<div class="col-xs-6" id="fixedTimeSlotDesc"></div>
						<div class="col-xs-3 Label" id="unitStr"></div>
					</div>
				</div>
			</div>
			
			<div class="FeiyongBlock">
				<div class="FeiyongShow">
					<div class="FeiyongCaption">计费规则</div>
					<span class="Feiyong"><span id="unitPrice"></span></span>
				</div>
			</div>
			
			<div class="Tips">
				<div class="BorderTop"></div>
				<div class="BorderLeft"></div>
				<div class="BorderBottom"></div>
				<div class="BorderRight"></div>
				<p2>续租的订单将在当前订单结束时立即执行</p2>
			</div>
			
			<div class="BottomButton">
				<div class="col-xs-8 col-xs-offset-2">
					<button onclick="sub();" type="button" class="btn btn-embossed btn-primary btn-block SubmitButton">确认续租</button>
				</div>
			</div>
		</form>
	</div>
  </div>
</div>

</body>
</html>
