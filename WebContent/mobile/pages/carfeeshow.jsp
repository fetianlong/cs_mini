<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>费用预估</title>
<%@ include file="/mobile/pages/common/common_head.jsp"%>

<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">


<script src="<%=path %>/mobile/common/js/datetimepicker/datetimepicker.min.js"></script>
<script src="<%=path %>/mobile/common/js/datetimepicker/datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" href="<%=path %>/mobile/common/js/datetimepicker/datetimepicker.min.css">
<link rel=stylesheet href="<%=path %>/mobile/common/css/costexpected.css">

<script>
$(function(){
	var myDate = new Date();
	$(".SelectTime").datetimepicker({
		language: 'zh-CN',
		todayHighlight: 'true',
		minView: 0,
		autoclose: true,
		minuteStep: 15,
		startDate:  myDate
	});
});
function showFee(){
	$.ajax({
		type: "POST",
	    url: "<%=path%>/mobile/showFee.action",
	    dataType:'json',
	    data:{'carId':$("input[name='carId']").val(),'strategyRelationId':$("input[name='strategyRelationId']:checked").val()
	    		'returnBackTime':$("input[name='returnBackTime']").val()},
	    success: function(data) {
	    	if(data.result == 0){
	    		$('#fee').text(data.info);
			}
			else{
				Alert(data.msg);
			}
	    },
		error: function(){
			Alert("暂时无法预估费用，请稍后再试");
		}
	});
	
}
function bookCar(carid){
	$.ajax({
		type: "POST",
	    url: "<%=path%>/mobile/toBookingInfoCheck.action",
	    dataType:'json',
	    data:{'carId':carid},
	    success: function(data) {
	    	if(data.result == 0){
				window.location.href="<%=path%>/mobile/toBookingInfo.action?carId="+carid;
			}
			else{
				Alert(data.msg);
			}
	    },
		error: function(){
			Alert("暂时无法租车，请稍后再试");
		}
	});
}
</script>

</head>

<body>
<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12">
			<a class="pull-right ShowRule" href="<%=path%>/mobile/pages/jifeiguize.jsp">计费规则</a>
		</div>
	</div>
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<form class="form-horizontal">
		  <div class="form-group Location">
			<label for="address1" class="col-xs-2 control-label"><i class="fa fa-map-marker fa-lg"></i></label>
			<div class="col-xs-10">
			  <input type="text" class="form-control" id="address1"  placeholder="<ww:property value="choseCarBranchDot.name" />" name="dotName" value="<ww:property value="choseCarBranchDot.name" />" readonly>
			  <input type="hidden"  name="dotId" value="<ww:property value="choseCarBranchDot.id" />">
			  <input type="hidden"  name="carId" value="<ww:property value="carId" />">
			</div>
		  </div>
		  <!--<div class="form-group Destination">
			<label for="address2" class="col-xs-2 control-label"><i class="fa fa-share-square fa-lg"></i></label>
			<div class="col-xs-10"><p class="form-control-static">请选择策略：</p></div>
			<div class="col-xs-12">
			   	<table class="table table-striped table-bordered table-condensed CelveTable">
					<thead>
						<tr>
							<th>&nbsp;</th>
							<th>策略名称</th>
							<th>策略类型</th>
							<th>价格</th>
							<th>注册折扣名称</th>
							<th>折扣</th>
						</tr>
					</thead>
					<tbody>
			   		<ww:iterator value="strategyCarShowInfos" id="data" status="rl">
			   			<tr>
							<td><input type="radio" name="strategyRelationId" value="<ww:property value="strategyRelationId.id" />" 
			   			    <ww:if test="strategyCarShowInfos.length == 1">checked</ww:if> /></td>
			   				<td><ww:property value="strategyBase.name" /></td>
							<td><ww:property value="strategyType.cnName" /></td>
							<td><ww:property value="price" />/<ww:property value="unitStr" /></td>
							<td><ww:property value="strategyRegDiscount.name" /></td>
							<td><ww:property value="strategyRegDiscount.discount" /></td>
			   			</tr>
			   		</ww:iterator>
					</tbody>
			   	</table>
			</div>
		  </div>-->

		  <div class="form-group TimeSelect">
			<label for="time" class="col-xs-2 control-label"><i class="fa fa-clock-o fa-lg"></i></label>
			<div class="col-xs-10">
			  <input type="text" class="form-control SelectTime" onchange="showFee();" name="returnBackTime" id="time" placeholder="选择还车时间" readonly>
			</div>
		  </div>
		  <div class="ShowPriceBlock text-center">
		  	<p>费用预估</p>
			<h4><i class="fa fa-jpy"></i><span id="fee"></span>元</h4>
		  </div>
		  <div class="BottomButton">
			<div class="col-xs-8 col-xs-offset-2">
				<button  onclick="bookCar('<ww:property value="carId" />'); " type="button" class="btn btn-embossed btn-primary btn-block">确认用车</button>
			</div>
		</div>
		</form>
	</div>
  </div>
</div>

</body>
</html>
