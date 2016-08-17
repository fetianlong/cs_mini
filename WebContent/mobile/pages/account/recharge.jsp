<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html >
<head>
<meta charset="utf-8">
<title>充值</title>
<%@ include file="/mobile/pages/common/common_head.jsp"%>

<link rel=stylesheet href="<%=path %>/mobile/common/css/pay.css">

<script type="text/javascript">
	function rechargeSubmit(){
		
		if($("#isMetaData").val()=="Y"){
			
			if($("#rechargeCardId").val()==null || $("#rechargeCardId").val()==""){
				Alert("请选择充值金额");
				return;	
			}
		}else{
			if($("#customAmount").val() ==null || $("#customAmount").val()==""){
				Alert("请输入充值金额");
				return;	
			}
		}
		
		$("#rechargeForm").submit();
	};
	
	function closeMes(){
		$('#alipayModal').modal('hide');
	};
	
$(function(){
	$(".ZidingyiBlock").hide();
	$(".PayButton .btn").click(function(e) {
		$(".PayButton .btn").removeClass("Selected");
		$(this).addClass("Selected");
		if ($(this).hasClass("Zidingyi")){
			$(".ZidingyiBlock").slideDown(300);
		} else {
			$(".ZidingyiBlock").slideUp(300);
		};
		return false;
	});
	
	$(".SelectZhifu .btn").click(function(e) {
		$(".SelectZhifu .btn").removeClass("Selected");
		$(this).addClass("Selected");
		return false;
	});
	
	
	$('.first').click();
	$('.Weixin').click();
	

});

function czkChange(id){
	$("#isMetaData").val("Y")
	$("#rechargeCardId").val(id);

}
function czkZdyChange(){
	$("#isMetaData").val("N")
	$("#rechargeCardId").val("");
	
	var customAmount=$("#customAmount").val()
	
	
}
function zdyChange(){
	$("#isMetaData").val("N");
	$("#rechargeCardId").val("");
	
}

function payTypeChange(type){
	$("#payType").val(type);
}

function alertMsg(msg){
	$('#myModal').modal('show');
	$("#modelMsg").html(msg);
};


</script>
    
</head>

<body>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<form id="rechargeForm" action="<%=path %>/mobile/online/payRecharge.action" method="post">
			<div class="form-group">
				<label>选择金额</label>
				<div class="SelectMoney">
				<!--  Selected -->
					<ul class="SelectButtonUl PayButton">
						<ww:iterator value="rechargeCardList" id="data" status="rl">
								<li><button   onclick="czkChange('<ww:property  value="id"/>')" class="btn btn-default <ww:if test="#rl.first">first</ww:if>"><ww:property  value="showName"/></button></li>
						</ww:iterator>
						<li><button class="btn btn-default Zidingyi" onclick="czkZdyChange()">自定义</button></li>
						<div class="clearfix"></div>
					</ul>
				</div>
			</div>
			<input type="hidden" name="rechargeCardId" id="rechargeCardId">
			<div class="form-group ZidingyiBlock">
                <input type="hidden"  name="isMetaData" id="isMetaData"  value="">
                <input type="number" class="form-control ZidingyiInput" name="customAmount" id="customAmount" onchange="czkZdyChange()" placeholder="请输入10-5000整数">
			</div>
			
			
			<div class="form-group">
				<label for="#">支付方式</label>
				<div class="SelectType">
					<ul class="SelectButtonUl SelectZhifu">
						<li><button class="btn btn-default btn-sm Weixin  " onclick="payTypeChange(<ww:property value="@com.dearho.cs.account.pojo.Account@PAY_TYPE_WECHAT"/>)"></button></li>
						<div class="clearfix"></div>
					</ul>
				</div>
			</div>
			<input type="hidden" name="payType" id="payType">

			
		<p class="Tips text-center"><ww:property value="retMsg"/></p>
		<div class="BottomButton">
			<div class="col-xs-8 col-xs-offset-2">
				<button  type="button" onclick="rechargeSubmit()" class="btn btn-embossed btn-primary btn-block SubmitButton">确定</button>
			</div>
		</div>
		</form>
	</div>
  </div>
</div>


<!-- 模态框（Modal）alert -->
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


<!-- 模态框（Modal） -->
<div class="modal fade" id="alipayModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">充值遇到问题？
            </h4>
         </div>
         <div class="modal-body">
            		<div class=""><i ></i>请在新开的付款页面完成付款后再选择</div>
            		<div class="">充值成功：<a href="<%=path %>/mobile/account/index.action" >查看余额</a></div>
            		<div class="">充值失败： <a href="javascript:closeMes()" >重新选择充值金额</a></div>
         </div>
         <div class="modal-footer">

         </div>
      </div><!-- /.modal-content -->
</div><!-- /.modal -->
</div>

</body>
</html>
