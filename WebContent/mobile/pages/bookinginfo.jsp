<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>车辆预定</title>
<%@ include file="/mobile/pages/common/common_head.jsp"%>

<link rel=stylesheet href="<%=path %>/mobile/common/css/bookingcar2.css">

<script>
$(function(){
	$(".CarImgLink").click(function(e) {
		$("#ShowCarDetail").modal('show');
	});
	
	$(".CheckBoxInput").change(function(e) {
		if ($(this).hasClass("Shizu")){
			$(".CarOrderBlock .checkbox .Shizu").radiocheck('check');
			$(".CarOrderBlock .checkbox .Rizu").radiocheck('uncheck');
			$(".CarOrderBlock .YuguBlock").slideDown(300);
			$(".CarOrderBlock .SelectTimeBlock").slideUp(300);
			$(".CarOrderBlock .checkbox .ShizuIcon").css("transform", "rotate(90deg)");
			$(".CarOrderBlock .checkbox .RizuIcon").css("transform", "rotate(0)");
			$(".CarOrderBlock ul li.ShizuLi").css("border-bottom", "none");
			$("#strategyBaseId").val($("#shizuId").val());
			$("#alertTitle").html("请在提交订单后"+<ww:property value="shizuStrategy.timeBeforeGet" />+"分钟内取车，逾期订单将自动取消");
			$("#shizuDesc").html("最低消费"+$("#minConsumption").val()+"元，每24小时最高消费"+$("#maxConsumption").val()+"元");
		} else if ($(this).hasClass("Rizu")){
			$(".CarOrderBlock .checkbox .Rizu").radiocheck('check');
			$(".CarOrderBlock .checkbox .Shizu").radiocheck('uncheck');
			$(".CarOrderBlock .YuguBlock").slideUp(300);
			$(".CarOrderBlock .SelectTimeBlock").slideDown(300);
			$(".CarOrderBlock .checkbox .ShizuIcon").css("transform", "rotate(0)");
			$(".CarOrderBlock .checkbox .RizuIcon").css("transform", "rotate(90deg)");
			$(".CarOrderBlock ul li.ShizuLi").css("border-bottom", "solid 1px #e7e7e7");
			$("#strategyBaseId").val($("#rizuId").val());
			$("#alertTitle").html("订单提交"+<ww:property value="rizuStrategy.timeBeforeGet" />+"分钟后开始计费");
		};
	});
	
	<!--$(".CarOrderBlock .checkbox .Shizu").click();-->
	
	$(".CarOrderBlock .SelectTimeBlock .LeftButton").click(function(e) {
		if (parseInt($(".CarOrderBlock .SelectTimeBlock .TimeInput").val()) > 1){
			$(".CarOrderBlock .SelectTimeBlock .TimeInput").val((parseInt($(".CarOrderBlock .SelectTimeBlock .TimeInput").val()) - 1).toString());
			CalcRizuPrice();
		};
		return false;
	});
	$(".CarOrderBlock .SelectTimeBlock .RightButton").click(function(e) {
		$(".CarOrderBlock .SelectTimeBlock .TimeInput").val((parseInt($(".CarOrderBlock .SelectTimeBlock .TimeInput").val()) + 1).toString());
		CalcRizuPrice();
		return false;
	});
	
	$("#unitCount").on("input", function(e) {
		if (parseInt($(".CarOrderBlock .SelectTimeBlock .TimeInput").val()) < 0){
			$(".CarOrderBlock .SelectTimeBlock .TimeInput").val(0);
		};
		CalcRizuPrice();
	});
	
	function CalcRizuPrice(){
		$(".CarOrderBlock .RizuLi .SelectTimeBlock .RizuPrice span").html(parseFloat($("#rizuPrice span").html()) * parseInt($(".CarOrderBlock .RizuLi .SelectTimeBlock #unitCount").val()));
	};
	
	$(".CarImgBlock .CarPrice").css("top", $(".CarImgBlock .CarListUl .Xuhang").offset().top);
	
	if (isAndroid()){
		$(".BottomBlock").removeClass("BottomButton");
		$(".BottomBlock").addClass("row");
	};
});

function sub(){
	if($("#strategyBaseId").val()==null||$("#strategyBaseId").val()==""){
		Alert("请选择租赁类型");
	}else{
		$.post('<%=path %>/mobile/bookCar.action',$('#bookTimeForm').serialize()+'&payStyle='+$('#fukuan').val(),function(data){
			if(data.result == 0){
				window.location.href='<%=path%>/mobile/toBookingFinish.action?ordersId='+data.info.id;
			}else if(data.result == 3){
				window.location.href='<%=path%>/mobile/toPayFee.action?ordersDetailNo='+data.info;
			}else{
				Alert(data.msg);
			}
		},'json');
	}
	
};
</script>

</head>

<body>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
    		<form class="form-horizontal" id="bookTimeForm">
			<input type="hidden" name="carId" value="<ww:property value="carInfo.car.id" />">
			<input type="hidden" name="orders.carId" value="<ww:property value="carInfo.car.id" />">
			<input type="hidden" name="shizuId" id="shizuId" value="<ww:property value="shizuStrategy.id" />">
			<input type="hidden" name="minConsumption" id="minConsumption" value="<ww:property value="shizuStrategy.minConsumption" />">
			<input type="hidden" name="maxConsumption" id="maxConsumption" value="<ww:property value="shizuStrategy.maxConsumption" />">
			<input type="hidden" name="rizuId" id="rizuId" value="<ww:property value="rizuStrategy.id" />">
			<input type="hidden" name="strategyBaseId" id="strategyBaseId" value="">
			
		<div class="row CarImgBlock">
			<div class="col-xs-4">
				<a class="CarImgLink">
                	<img class="img-responsive center-block" src="<%=path%>/vehiclemodelimgs/microweb/<ww:property value="carInfo.carImg" />">
                    <p><ww:property value="carInfo.car.vehiclePlateId" /></p>
                </a>
				<div class="modal fade" id="ShowCarDetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
				  <div class="modal-dialog" role="document">
					<div class="modal-content">
					  <div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="myModalLabel">车辆详图</h4>
					  </div>
					  <div class="modal-body">
						<img class="img-responsive center-block" src="<%=path%>/vehiclemodelimgs/microweb/<ww:property value="carInfo.carImg" />">
					  </div>
					  <div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					  </div>
					</div>
				  </div>
				</div>
			</div>
			<div class="col-xs-8 CarNameBlock">
				<h4><ww:property value="carInfo.brandModel" /><span class="CarType pull-right"><ww:if test="carInfo.carType.equals(\"经济型\")">
					经济型
				</ww:if>
				<ww:if test="carInfo.carType.equals(\"商务型\")">
					商务型
				</ww:if>
				<ww:if test="carInfo.carType.equals(\"豪华型\")">
					豪华型
				</ww:if></span></h4>
                <ul class="CarListUl">
                	<li>网点：<ww:property value="carInfo.dotName" /></li>
                    <li class="Xuhang">续航：<ww:property value="carInfo.km" />km</li>
                    <li>电量：
                        <div class="progress">
                          <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="<ww:property value="carInfo.SOC" />" aria-valuemin="0" aria-valuemax="100" style="width: <ww:property value="carInfo.SOC" />%">
                            <span class="sr-only"><ww:property value="carInfo.SOC" />% Complete</span>
                          </div>
                        </div>
                        <span><ww:property value="carInfo.SOC" />%</span>
                    </li>
                </ul>
			</div>
			<div class="CarPrice">
				<ww:iterator value="strategyCarShowInfos" id="data" status="rl">
						<p id="<ww:property value="strategyType.code" />Price"><span><ww:property value="price" /></span><ww:property value="unitStr" /></p>
				</ww:iterator>
			</div>
		</div>
		
		<div class="row OneBlock CarOrderBlock">
			<ul>
				<li class="ShizuLi">
					<label class="checkbox">
					  <input class="CheckBoxInput Shizu" type="checkbox" value="" data-toggle="checkbox">
					  时租<span class="pull-right ArrowIcon ShizuIcon"><i class="fa fa-chevron-right"></i></span>
					</label>
					<div class="SubBlock YuguBlock" id="shizuDesc">最低消费10元，每24小时最高消费180元</div>
				</li>
				<li  class="RizuLi">
					<label class="checkbox">
					  <input class="CheckBoxInput Rizu" type="checkbox" value="" data-toggle="checkbox">
					  日租<span class="pull-right ArrowIcon RizuIcon"><i class="fa fa-chevron-right"></i></span>
					</label>
					<div class="SubBlock SelectTimeBlock">
						预租天数<span class="TimeSelectBlock"><button class="btn LeftButton"><span class="glyphicon glyphicon-minus" aria-hidden="true"></span></button><input class="form-control TimeInput" type="number" name="unitCount" id="unitCount" value="1"><button class="btn RightButton"><span class="glyphicon glyphicon-plus" aria-hidden="true"></span></button></span><span class="RizuPrice pull-right"><span><ww:property value="rizuStrategy.basePrice" /></span>元</span>
					</div>
				</li>
			</ul>
		</div>
		
		<div class="BottomBlock BottomButton">
			<div class="HuancheLinkBlock text-right">
				<a href="<%=path%>/mobile/toShowReturnDot.action?dotId=<ww:property value="carInfo.dotId" />" class="HuancheLink"><i class="fa fa-map-marker"></i>查看还车网点</a>
			</div>
			<p class="Tips" id="alertTitle"></p>
			<div class="col-xs-8 col-xs-offset-2">
				<button onclick="sub();" type="button" class="btn btn-embossed btn-primary btn-block SubmitButton">确认用车</button>
			</div>
		</div>
		</form>
	</div>
  </div>
</div>

</body>
</html>
