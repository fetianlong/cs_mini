<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>历史订单</title>


<%@ include file="/mobile/pages/common/common_head.jsp"%>


<script src="<%=path%>/mobile/common/js/datetimepicker/datetimepicker.min.js"></script>
<script src="<%=path%>/mobile/common/js/datetimepicker/datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" href="<%=path%>/mobile/common/js/datetimepicker/datetimepicker.min.css">
<link rel="stylesheet" href="<%=path%>/mobile/common/css/orderlist.css">

<script>
$(function(){
	$(".TopMenu ul li").width($(window).width() * 0.25);
	$(window).resize(function(){
	   $(".TopMenu ul li").width($(window).width() * 0.25);
	});
	$(".TopMenu ul li a").click(function(e) {
		var ll = $(this).closest("li").attr("class");
		if(ll != null && ll.indexOf("active") != -1){
			return;
		}
		$(".TopMenu ul li").removeClass("active");
		$(".TopMenu ul li").css('cursor','pointer');
		$(this).closest("li").addClass("active");
		$(this).closest("li").css('cursor','default');
		$('#orderState').val($(this).closest("li").attr('orderState'));

		searchOrdersList($('#searchTypeInp').val());
		
	});
	
	$(".SelectTime").datetimepicker({
		language: 'zh-CN',
		format: "yyyy-mm-dd",
		todayHighlight: true,
		minView: 2,
		startView: 4,
		autoclose: true,
		todayBtn: true
	});
	
	$(".FilterBlock .FilterButton").click(function(e) {
		$(".FilterBlock .FilterSetBlock").slideToggle(200);
	});
	searchOrdersList("lasttime");
	$(document).scroll(function(e) {
        if ($(document).scrollTop() >= ($(document).height()-$(window).height())) {
        	if(continueFlag){
        		$('#currentNum').val(Number($('#currentNum').val()) + 5);
    			searchOrdersList($('#searchTypeInp').val(),'add');
        	}
		};
    });
	
	$("#orderslistul").on("click", ".OrderLink", function(){
		window.location.href='<%=path%>/mobile/toOrdersDetail.action?orderId='+$(this).attr("id");
	});
});

function searchOrdersList(searchType,type){
	if ('rangetime' == searchType && $('#endtime').val() != null && $('#endtime').val() != '' && 
    		$('#begintime').val() != null && $('#begintime').val() != ''){
		if($('#endtime').val() < $('#begintime').val()){
			Alert("起始时间不能大于终止时间");
	    	return;
		}
    	$('#sxtj').text($('#begintime').val()+"至"+$('#endtime').val());
    }
	else{
		$('#sxtj').text( $("#xiangxi").find("option:selected").text());
	}
	
	$('#searchTypeInp').val(searchType);
	var param = {
			'searchType':$('#searchTypeInp').val(),
			'orderState':$('#orderState').val(),
			'begintime':$('#begintime').val(),
			'endtime':$('#endtime').val(),
			'lasttimetype':$('#xiangxi').val(),
			'currentNum':$('#currentNum').val(),
			'lastOrderTime':$('#lastOrderTime').val(),
			'type':type
	};
	if(type != null && type == 'add'){
		$.post('<%=path %>/mobile/searchOrders.action',param,addShowOrders,'json');
	}
	else{
		$('#currentNum').val(5);
		$('#lastOrderTime').val(null);
		continueFlag = true;
		$.post('<%=path %>/mobile/searchOrders.action',param,showOrders,'json');
	}
	
}
function showOrders(data){
	if(data.result == 0){
		$('#orderslistul').empty();
		if(data.info != null && data.info.length> 0){
			addOrder(data.info);
		}
		else{
			$('#orderslistul').append($('<li class="NoList text-center"><i class="fa fa-filter"></i>没有符合条件的订单</li>'));
		}
	}
	else{
		Alert(data.msg);
	}
}
var continueFlag = true;
function addShowOrders(data){
	if(data.result == 0){
		if(data.info != null && data.info.length> 0){
			addOrder(data.info);
		}
		else{
			continueFlag = false;
		}
	}
	else{
		Alert(data.msg);
	}
}

function addOrder(info){
	
	$.each(info,function(index,orders){
		if(index == 0){
			ShowLoading(true);
		}
		if(index == info.length - 1){
			ShowLoading(false);
		}
		var htmlstr = '<li>'+
			'<p class="OrderInfo">订单号：<span>'+orders.ordersNo+'</span><span class="OrderState pull-right">'+orders.stateName+'</span></p>'+
			'<a class="OrderLink" id="'+orders.id+'" href="#">'+
				'<div class="row">'+
					'<div class="col-xs-4">'+
						'<img class="img-responsive center-block" src="<%=path%>/vehiclemodelimgs/microweb/'+orders.car.carVehicleModel.microWebModelPhoto+'">'+
					'</div>'+
					'<div class="col-xs-8">'+
						'<h4 class="CarName">'+orders.car.nickName+'('+orders.vehicleModelName+')<!--只有等待中才显示下面的价格，否则用hidden隐藏掉-->';
			if(orders.totalFee != null ){
				htmlstr += '<span class="CarPrice pull-right"><i class="fa fa-jpy"></i><span>'+orders.totalFee+'</span></span>';
			}
			htmlstr += '</h4>'+
						'<div class="CarTime">'+
							'<p class="CarQuche">取车时间：<span>'+orders.beginTimeStr+'</span></p>'+
							'<p class="CarHuanche">还车时间：<span>'+orders.endTimeStr+'</span></p>'+
						'</div>'+
					'</div>'+
				'</div>'+
			'</a>'+
			'<div class="PinglunButtonBlock">';
				htmlstr += '<div class="clearfix"></div>'+
			'</div>'+
		'</li>';
		
		$('#orderslistul').append($(htmlstr));
		if(index == info.length - 1){
			$('#lastOrderTime').val(orders.id);
		}
	});
	
}

</script>
</head>

<body>
<div class="TopMenu">
	<ul>
		<li <ww:if test="orderState.equals(\"0\")">class="active" </ww:if> orderState="0"><a>全部订单</a></li>
		<li <ww:if test="orderState.equals(\"3\")">class="active" </ww:if> orderState="3"><a>订单结束</a></li>
		<li <ww:if test="orderState.equals(\"5\")">class="active" </ww:if> orderState="5"><a>订单超时</a></li>
		<li <ww:if test="orderState.equals(\"4\")">class="active" </ww:if> orderState="4"><a>订单取消</a></li>
		<div class="clearfix"></div>
	</ul>
</div>

<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<div class="FilterBlock">
			<div class="row FilterSetButton">
				<div class="col-xs-9 FilterShow">
					<div class="Border">
						筛选条件：<span id="sxtj">最近一个月</span>
					</div>
				</div>
				<div class="col-xs-3">
					<button class="btn FilterButton btn-block" type="submit"><i class="fa fa-filter"></i>时间筛选</button>
				</div>
			</div>
			<div class="row FilterSetBlock">
				<div class="col-xs-12">
					<div class="Border">
						<i class="fa fa-caret-up Arrow"></i>
						<div class="row SelectTimeBlock">
							<div class="col-xs-12">
							<form id="sform">
								<input type="hidden" id="searchTypeInp"  value="<ww:property value="searchType"/>" />
								<input type="hidden" id="orderState" value="<ww:property value="orderState"/>" />
								<input type="hidden" id="currentNum" value=5 />
								<input type="hidden" id="lastOrderTime" />
								
								精确:
								<input type="text" class="SelectTime" id="begintime" value='<ww:property value="begintime"/>'  readonly placeholder="起始"
								    <ww:if test="begintime == null || begintime.equals('')">placeholder="起始" </ww:if><ww:else>placeholder="<ww:property value="begintime"/>" </ww:else>> -
								<input type="text" class="SelectTime" id="endtime" value='<ww:property value="endtime"/>' readonly placeholder="终止"
								    <ww:if test="endtime == null || endtime.equals('')">placeholder="终止时间" </ww:if><ww:else>placeholder="<ww:property value="endtime"/>" </ww:else>>
								<input class="btn btn-embossed btn-primary pull-right SubmitButton" type="button" onclick="searchOrdersList('rangetime')" value="确定">
							</form>
							</div>
							<div class="col-xs-12">
							<form>
								范围:
								<select id="xiangxi">
								    <option <ww:if test="lasttimetype.equals(\"0\")">selected=selected</ww:if> value="0"></option>
									<option <ww:if test="lasttimetype.equals(\"1\")">selected=selected</ww:if> value="1">最近一个月</option>
									<option <ww:if test="lasttimetype.equals(\"2\")">selected=selected</ww:if> value="2">最近两个月</option>
									<option <ww:if test="lasttimetype.equals(\"3\")">selected=selected</ww:if> value="3">最近三个月</option>
									<option <ww:if test="lasttimetype.equals(\"4\")">selected=selected</ww:if> value="4">最近半年</option>
									<option <ww:if test="lasttimetype.equals(\"5\")">selected=selected</ww:if> value="5">最近一年</option>
								</select>
								<input class="btn btn-embossed btn-primary pull-right SubmitButton" type="button" onclick="searchOrdersList('lasttime')" value="确定">
							</form>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		
		<div class="row ListBlock">
			<ul id="orderslistul" class="ListUl">
			</ul>
		</div>
	</div>
  </div>
</div>

</body>
</html>
