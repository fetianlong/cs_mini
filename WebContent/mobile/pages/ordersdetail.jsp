<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>订单详情</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>

<link rel="stylesheet" href="<%=path%>/mobile/common/css/orderinfo.css">
<script type="text/javascript">
$(function(){
	if($("#type").val()=="jishizu"){
		setFee();
	}else{
		$("#feiyong").html($("#totalFee").val());
	}
});

var currentFee;
function setFee(){
	if($("#type").val()=="jishizu"){
		if(currentFee==null){
			$.ajax({
				async: false,
				type: "POST",
				url: "<%=path %>/mobile/countCurrentFee.action",
				dataType: "json",
				success: function(data){
					if(data.result == 0){
						currentFee=data.info;
					}
				}
			});
		}
		if(currentFee!=null){
			var totalFee = (parseFloat($("#totalFee").val())+parseFloat(currentFee)).toFixed(2);
			$("#feiyong").html(totalFee);
			$("#shizuFee").html(parseFloat(currentFee).toFixed(2));
		
		//var nowDate=new Date();
		//var TimeDistance=nowDate-startDate;
		//var nM = Math.floor(TimeDistance/(1000*60)) % 60;
		//var nH = Math.floor(TimeDistance/(1000*60*60));
		//var basePrice = $("#basePrice").val();
		//var pricePerMin = (basePrice/60).toFixed(2);
		//var totalFee = (parseFloat($("#totalFee").val())+parseFloat((nM+nH*60)*pricePerMin)).toFixed(2);
		//var realtimeFee = parseFloat((nM+nH*60)*pricePerMin).toFixed(2);
		//var minConsumption = $("#minConsumption").val();
		//if(parseFloat(totalFee) > parseFloat(minConsumption)){//最低消费
		//	$("#feiyong").html(totalFee);
		//	$("#shizuFee").html(realtimeFee);
		//}else{
		//	$("#feiyong").html(minConsumption);
		//	$("#shizuFee").html(minConsumption);
		//}
		}
	}
}
var TimeShow = setInterval("setFee()",1000 * 60);
</script>
</head>

<body>
<input type="hidden" id="type" value="<ww:property value="currentType.code" />" />
<input type="hidden" id="totalFee" value="<ww:property value="order.totalFee" />" />
<div class="container">
	<ww:if test='order.state=="0"'>
		<div class="row OrderStateBlock Background2">
			<p><ww:property value="order.stateName" /></p>
		</div>
	</ww:if>
	<ww:if test='order.state=="3"'>
		<div class="row OrderStateBlock Background1">
			<p><ww:property value="order.stateName" /></p>
		</div>
	</ww:if>
	<ww:if test='order.state=="4"'>
		<div class="row OrderStateBlock Background3">
			<p><ww:property value="order.stateName" /></p>
		</div>
	</ww:if>
</div>

<div class="OneBlock">
<p class="Caption">订单编号：<ww:property value="order.ordersNo" /></p>
	<p>车牌号：<ww:property value="order.plateNumber" /></p>
	<p>车型/品牌/型号：<span><ww:property value="order.vehicleModelName" />  <ww:property value="order.car.carVehicleModel.casesNum" />厢</span></p>
	<ww:if test='order.beginTime!=null'><p>取车时间：<span><ww:property value="transDate12String(order.beginTime)" /></span></p></ww:if>
	<ww:if test='order.endTime!=null'><p>还车时间：<span><ww:property value="transDate12String(order.endTime)" /></span></p></ww:if>
    <div class="PriceBlock">
    	<p class="CarType"><span><ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('1',order.car.carVehicleModel.level)" /></span></p>
        <p class="CarPrice">当前费用：<i class="fa fa-jpy"></i><span id="feiyong"><ww:property value="order.totalFee" /></span></p>
    </div>
</div>

<ww:iterator value="order.ordersDetail" id="data" status="rl">
<div class="OneBlock XiangqingBlock">
    <p class="Caption">租赁类型：<ww:property value="typeName" />
    	<ww:if test='isRunning=="1"'><span class="label label-warning pull-right">进行中</span></ww:if>
    	<ww:if test='isTimeout!="1" && isOver=="1"'><span class="label label-default pull-right">已结束</span></ww:if>
        <ww:if test='isTimeout=="1" && isOver=="1"'><span class="label label-danger pull-right">已超时</span></ww:if>
        <!--<ww:if test='timeoutRemark=="1"'><span class="label label-primary pull-right">超时罚单</span></ww:if>-->
    </p>
    <ww:if test='beginTime!=null'><p>开始时间：<ww:property value="transDate12String(beginTime)" /></p></ww:if>
    <ww:if test='endTime!=null'><p>结束时间：<ww:property value="transDate12String(endTime)" /></p></ww:if>
    
    <ww:if test='isPrePay=="0"'>
    	<p>已用时长：
        	<ww:property value="useTimeStr" />
    	</p>
   		<ww:if test='mileage!=null && mileage!=0'>
    		<p>已开里程：
        		<ww:property value="mileage" />公里
    		</p>
    	</ww:if> 
    	<ww:if test='timeFee!=null && timeFee!=0'>
    		<p>计时费用：
        		<ww:property value="timeFee" />元
   	 		</p>
   	 	</ww:if> 
    	<ww:if test='mileFee!=null && mileFee!=0'>
    		<p>里程费用：
        		<ww:property value="mileFee" />元
   	 		</p>
   	 	</ww:if> 
    </ww:if>
    
     <ww:if test='ticketsFee!=null && ticketsFee!=0'><p>超时罚金：<ww:property value="ticketsFee" /> 元</p></ww:if>
     
    <p>总费用：
        <ww:if test='isPrePay=="0"'><i id="shizuFee"><ww:property value="totalFee" /></i> 元<ww:if test='isRunning=="1"'>(计费中...)</ww:if></ww:if>
        <ww:if test='isPrePay=="1" && isPaid=="1"'><i><ww:property value="totalFee" /></i> 元</ww:if>
    </p>
    
</div>
</ww:iterator>
</body>
</html>
