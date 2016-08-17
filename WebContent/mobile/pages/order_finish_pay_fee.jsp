<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>支付</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>




<link rel=stylesheet href="<%=path%>/mobile/common/css/ordernow.css">
<link rel=stylesheet href="<%=path%>/mobile/common/css/selectzhifu.css">

<script type="text/javascript">

$(function(){
	$(".SelectZhifuBlock .btn").click(function(e) {
		if (!$(this).hasClass("disabled")){
			$(".SelectZhifuBlock .btn").removeClass("Selected");
			$(this).addClass("Selected");
		};
		return false;
	});
	
	$(".SelectZhifuBlock .btn.Selected").click();
	
});



function confirmPay(){
	$("#feeForm").submit();
}
function payTypeChange(type){
	$("#payType").val(type);
}
</script>
</head>

<body>

<form class="form-horizontal" id="feeForm" method="post" action="<%=path %>/mobile/online/confirmPayOrdersFinish.action">
	

<div class="container">
	<div class="row">
		<div class="col-xs-12 text-center">
			<p class="Tips">支付金额</p>
			<h2 class="Money"><i class="fa fa-jpy"></i><ww:property value="formatAmount(#request.totalFee)"/></h2>
			<div class="row SelectZhifuBlock">
				<div class="col-xs-8 col-xs-offset-2">
					<ww:if test="#request.isEnoughMoney">
						<button onclick="payTypeChange(1)" class="btn btn-default btn-block   ">账户余额(<ww:property value="formatAmount(#request.VirtualAmount)"/>)</button>
						
						
							<button onclick="payTypeChange(5)" class="btn btn-default btn-block " >微信支付</button>
					
						
					</ww:if>
					<ww:else>
						
						<button onclick="payTypeChange(1)" class="btn btn-default btn-block   disabled">账户余额 (<ww:property value="formatAmount(#request.VirtualAmount)"/>)</button>
						
						<button onclick="payTypeChange(5)" class="btn btn-default btn-block " >微信支付</button>
							
						
					</ww:else>
					
					<input type="hidden" name="payType" id="payType">
					<input type="hidden" id="ordersDetailNo" name="ordersDetailNo" value="<ww:property value="ordersDetail.ordersDetailNo" />" />
				</div>
				
			</div>
			<ww:if test='retMsg!=null && retMsg!="" '>
				<div class="alert alert-danger" role="alert"><ww:property value="retMsg"/></div>
			</ww:if>
		</div>
	</div>
</div>

<div class="BottomButton">
	<div class="col-xs-8 col-xs-offset-2">
		<button onclick="confirmPay()" class="btn btn-embossed btn-primary btn-block">立即支付</button>
	</div>
</div>
</form>


</body>
</html>