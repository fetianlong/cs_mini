<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html >
<head>
<meta charset="utf-8">
<title>个人中心</title>
<%@ include file="/mobile/pages/common/common_head.jsp"%>


<link rel=stylesheet href="<%=path %>/mobile/common/css/center.css">

</head>

<body>
<a class="PositionButton" href="<%=path%>/mobile/toBookCar.action"><i class="fa fa-map-marker fa-lg"></i></a>
<a class="OptionButton" href="<%=path%>/mobile/pages/subscriber/setting.jsp"><i class="fa fa-cog fa-lg"></i></a>
<div class="UserShowBlock">
<div class="container-fluid">
<div class="row">
	<div class="col-xs-6 col-xs-offset-3">
		<ww:if test="subscriber.avatar!=null && subscriber.avatar!=''">
			<img class="img-responsive img-circle center-block UserImg" src="<%=path%>/<ww:property value="subscriber.avatar"/>">
		</ww:if>
		<ww:elseif test="wechatUserInfo!=null && wechatUserInfo.headImgUrl!=null">
			<img class="img-responsive img-circle center-block UserImg" src="<ww:property value="wechatUserInfo.headImgUrl"/>">
		</ww:elseif>
		<ww:else>
			<img class="img-responsive img-circle center-block UserImg" src="<%=path%>/mobile/common/images/center/DefaultUser.jpg">
		</ww:else>
		
	</div>
</div>
<div class="row">
	<div class="col-xs-12 text-center">
		<h4 class="UserName"><ww:property value="subscriber.name"/></h4>
		<p class="WelcomeTip"><ww:if test="#request.isPm==1">下午好</ww:if><ww:else>上午好</ww:else></p>
	</div>
</div>

<div class="row YueBlock">
	<div class="col-xs-4 col-xs-offset-2 text-center">
		<p class="Yue"><ww:property value="formatAmount(account.frozenAmount)"/></p>
		<p class="YueTip">押金</p>
	</div>
	<div class="col-xs-4 text-center">
		<p class="Yue"><ww:property value="formatAmount(account.usableAmount)"/></p>
		<p class="YueTip">可用余额</p>
	</div>
</div>

<div class="row YueBlock">
	
</div>


<div class="row ChongzhiBlock">
	<div class="col-xs-6">
		<a class="btn btn-embossed btn-primary btn-block ChongzhiButton" href="javascript:gotoRecharge()" role="button"><i class="fa fa-database"></i>充值</a>
	</div>
	<div class="col-xs-6">
		<a class="btn btn-embossed btn-primary btn-block XinyongkaButton"href="javascript:gotoApplyRefund()" role="button"><i class="fa fa-credit-card"></i>退款</a>
	</div>
</div>
</div>
</div>

<div class="container-fluid ControlBlock">
	<div class="row">
		<div class="col-xs-12">
			<a class="btn btn-default btn-block" href="<%=path %>/mobile/subscriber/showBaseInfo.action" role="button">个人信息编辑与修改<i class="fa fa-angle-right pull-right"></i></a>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<a class="btn btn-default btn-block" href="<%=path %>/mobile/toOrderList.action" role="button">历史订单<i class="fa fa-angle-right pull-right"></i></a>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<a class="btn btn-default btn-block" href="<%=path %>/mobile/account/showTradingList.action" role="button">消费记录<i class="fa fa-angle-right pull-right"></i></a>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12">
			<a class="btn btn-default btn-block" href="javascript:gotoOrdersBill()" role="button">索取发票<i class="fa fa-angle-right pull-right"></i></a>
		</div>
	</div>
	
	<div class="row">
		<div class="col-xs-12">
			<a class="btn btn-default btn-block" href="<%=path %>/mobile/logout.action" role="button">退出<i class="fa fa-angle-right pull-right"></i></a>
		</div>
	</div>
</div>



<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">提示 </h4>
         </div>
         <div class="modal-body" id="modelMsg"></div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          
         </div>
      </div>
</div>
</div>
<!-- 模态框（Modal）end -->


</body>

<script type="text/javascript">

function gotoApplyRefund(){
	
	  $.ajax({
		type: "POST",
	    url: '<%=path%>/mobile/account/gotoApplyRefundCheck.action',
	    dataType:'json',	  
	    success: function(data) {
			if(0==data.result){
				window.location.href="<%=path%>/mobile/account/gotoApplyRefund.action";
		    }else{
		    	Alert(data.msg);
		    }
	    },
		error: function(){
			Alert("暂时无法提交退款请求，请稍后再试");
		}
	});
		
  
}
function  gotoRecharge(){
		$.ajax({
			type: "POST",
		    url: "<%=path%>/mobile/account/gotoRechargeCheck.action",
		    dataType:'json',
		    success: function(data) {
				if(0==data.result){
					window.location.href="<%=path%>/mobile/account/gotoRecharge.action";
			    }else{
			    	Alert(data.msg);
			    }
		    },
			error: function(){
				Alert("暂时无法提交充值请求，请稍后再试");
			}
		});
}

function alertMsg(msg){
	$('#myModal').modal('show');
	$("#modelMsg").html(msg);
};

function gotoOrdersBill(){
	$.ajax({
		type: "POST",
	    url: "<%=path%>/mobile/needInvoice.action",
	    dataType:'json',
	    success: function(data) {
			if(1==data.result){
				window.location.href="<%=path%>/mobile/chooseNeedBillOrders.action";
		    }if(2==data.result){
				window.location.href="<%=path%>/mobile/subscriber/showBillInfo.action";
		    }
	    }
	});
}
</script>

</html>
