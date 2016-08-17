<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>退款</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>



<link rel=stylesheet href="<%=path %>/mobile/common/css/payback.css">




<script type="text/javascript">

	
	
	function alertMsg(msg){
		$('#myModal').modal('show');
		$("#modelMsg").html(msg);
	};


	function refundContinue() {
		var type=$("input[name='paymentAccount.type']:checked").val();
		var cardNo=$("#CardNo").val();
		var name=$("#Name").val();
		if(type==3){
			if(cardNo==null ||cardNo==""){
				Alert("请输入支付宝账号");
				return;
			}
		}else if(type==4){
			if(cardNo==null ||cardNo==""){
				Alert("请输入银联卡号");
				return;
			}
			if(name==null ||name==""){
				Alert("请输入持卡人姓名");
				return;
			}
		}
		
		$('#applyRefundModal').modal('show')
	}
	
	
	function refundConfirm(){
		
		$.ajax({
			type : "POST",
			url : "<%=path%>/mobile/account/applyRefund.action",
			dataType : 'json',
			data : $('#refundForm').serialize(),
			success : function(data) {
				if (0 == data.result) {
					$('#applyRefundModal').modal('hide');
					Alert("申请提交成功!");
					window.location.href="<%=path%>/mobile/account/index.action";
				} else {
					$('#applyRefundModal').modal('hide')
					Alert(data.msg);
				}
			},
			error: function(){
				Alert("暂时无法提交退款请求，请稍后再试");	
			}
		});
	}
	
	
$(function(){
	
	$("input[name='paymentAccount.type']").click(function(e) {

		if ($(this).val() == 3){
			$(".CardNoBlock").show();
			$(".NameBlock").hide();
			$("#CardNo").attr("placeholder", "支付宝账号");
		}else if ($(this).val() == 4){
			$(".CardNoBlock").show();
			$(".NameBlock").show();
			$("#CardNo").attr("placeholder", "银联卡账号");
		
		}else if ($(this).val() == 5){
			$(".CardNoBlock").hide();
			$(".NameBlock").hide();
			
		};
	});
});
</script>
</head>

<body>

<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<img class="img-responsive center-block LogoImg" src="<%=path %>/mobile/common/images/pay/tui.png">
		<form id="refundForm">
		  <div class="form-group">
			<label>退款金额</label>
			<p class="form-control-static text-center PayBackNum"><i class="fa fa-jpy"></i><span><ww:property value="formatAmount(account.amount)" /></span>元</p>
		  </div>
		  
		
		
		

		  <ww:if test="#request.isNeedAccount">

					<div class="radio">
					   <label> 
					      <input type="radio"  name="paymentAccount.type" id="refund_type_wxpay" 
					         value="5" checked="checked"   >微信(<ww:property value="#session.refund_wechatUserInfo.nickname"/>)
					   </label>
					</div>
					
					
					
		  
		  </ww:if>
		
		  <div class="form-group">
		  	<div class="col-xs-12 Tips">
				<ul>
					<li>退款金额为实际的充值金额</li>
					<li>提出退款申请后，退款金额将在10个工作日内退还到您原充值账户；</li>
					<li>账户内余额退至其相应充值来源，可能会将金额拆分，出现多笔退款记录</li>
					<li>原充值款项临近退款有效期，则需填写转账的账号，将不能退款至原账号的，转账至用户指定的账号</li>
					<li>长假订单(如春节订单等)后，会员提出退款申请的，“华泰”在35天内向会员退回账户余额。</li>
				</ul>
			</div>
		  </div>
		<div class="form-group">
			<div class="col-xs-8 col-xs-offset-2">
				<button type="button" onclick="refundContinue()" class="btn btn-embossed btn-primary btn-block SubmitButton">确定</button>
			</div>
		</div>
		</form>
	</div>
  </div>
</div>



<!-- 模态框（Modal） -->
<div class="modal fade" id="applyRefundModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">提示</h4>
         </div>
         <div class="modal-body">提交退款申请以后，在退款期间内将不能再租用车辆了，确定要提交退款申请吗？</div>
         <div class="modal-footer">
            <button type="button" class="btn btn-default"  data-dismiss="modal">关闭</button>
            <button type="button" class="btn btn-primary" onclick="refundConfirm()"> 提交申请</button>
         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
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
</html>
