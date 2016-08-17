<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>索取发票</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>

<link rel="stylesheet" href="<%=path%>/mobile/common/css/fapiaolist.css">

<script>
$(function(){
	function SumPrice(){
		var SumPrice = 0;
		$(".ListBlock .CheckBoxInput").each(function(index, element) {
			if ($(element).val() == 1){
				SumPrice = SumPrice + parseFloat($(element).closest(".checkbox").find(".CarPrice span").html());
			};
		});
		$(".BottomBlock .Heiji span").html(SumPrice.toFixed(2));
	};
	
	$(".ListBlock .CheckBoxInput").change(function(e) {
		SumPrice();
		$(".BottomBlock .SelectAll").radiocheck('uncheck');
	});
	
	$(".BottomBlock .SelectAll").change(function(e) {
		if ($(this).val() == 1){
			$(".ListBlock .CheckBoxInput").radiocheck('check');
		} else if ($(this).val() == 0){
			$(".ListBlock .CheckBoxInput").radiocheck('uncheck');
		};
		SumPrice();
	});
	
	$("#sForm").submit(function(e) {
		CanSubmit = false;
		orderNos = new Array();
		$(".ListBlock .CheckBoxInput").each(function(index, element) {
			if ($(element).val() == 1){
				CanSubmit = true;
				OrderNo = $(element).closest(".checkbox").find(".OrderInfo span").html();
				orderNos.push(OrderNo);
			};
		});
		if (CanSubmit){
			$("#orderNos").val(orderNos.join(","));
			$("#totalFee").val($(".BottomBlock .Heiji span").html());
			return true;
		} else {
			Alert("请选择待开发票的订单");
			return false;
		};
	});
	
	if ($(".ListUl li").length == 0){
		$(".ListUl").html('<li class="NoOrder"><i class="fa fa-exclamation-circle"></i>您没有待开发票的订单</li>');
		$(".BottomBlock .SubmitButton").attr("disabled", "disabled");
		$(".BottomBlock .SubmitButton").addClass("disabled");
		$(".BottomBlock .checkbox").hide();
	};

});

</script>
</head>

<body>
<form class="form-horizontal" id="sForm" method="post" action="<%=path %>/mobile/confirmNeedInvoice.action">
<input type="hidden" value="" name="orderNos" id="orderNos">
<input type="hidden" value="" name="totalFee" id="totalFee">
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4"> 
		<div class="row ListBlock">
			<ul class="ListUl">
			<ww:iterator value="ordersList" id="data" status="rl">
				<li>
					<label class="checkbox">
					  <input class="CheckBoxInput" type="checkbox" value="" data-toggle="checkbox">
					  <p class="OrderInfo">订单号：<span><ww:property value="ordersNo" /></span></p>
						<div class="OrderLink">
							<div class="row">
								<div class="col-xs-4">
									<img class="img-responsive center-block" src="<%=path%>/vehiclemodelimgs/microweb/<ww:property value="car.carVehicleModel.microWebModelPhoto"/>">
								</div>
								<div class="col-xs-8">
									<h4 class="CarName"><ww:property value="vehicleModelName" /><span class="CarPrice pull-right"><i class="fa fa-jpy"></i><span><ww:property value="totalFee"/></span></span></h4>
									<div class="CarTime">
										<p class="CarQuche">开始时间：<span><ww:property value="transDate12String(beginTime)" /></span></p>
										<p class="CarHuanche">结束时间：<span><ww:property value="transDate12String(endTime)" /></span></p>
									</div>
								</div>
							</div>
						</div>
					</label>
				</li>
			</ww:iterator>
			
			</ul>
		</div>
	</div>
  </div>
</div>

<div class="BottomBlock">
	<div class="col-xs-8">
		<label class="checkbox">
			<input class="SelectAll" type="checkbox" value="0" data-toggle="checkbox">全选
		</label>
		<div class="pull-right Heiji">发票金额：<i class="fa fa-jpy"></i><span>0.00</span></div>
	</div>
	<div class="col-xs-4">
		<button class="SubmitButton" type="submit">索取发票</button>
	</div>
</div>
</form>
</body>
</html>

