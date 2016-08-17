<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>代充管理</title>
<base href="<%=basePath%>">
<%@ include file="/pages/common/common_head.jsp"%><script type="text/javascript">
$(function(){
	
	$('#cardList').change(function(){ 
			
			var id=$(this).children('option:selected').attr("id");
			if(id=="zdy"){
				$("#isMetaData").val("N");
				$("#customAmountDiv").removeClass("hidden");
				var amount=$("#customAmount").val();
				if(amount!=null && amount !=""){
					$("#amount").html(amount+"元");
					$("#rechargeCardId").val("");
				}else{
					$("#amount").html("--元");
					$("#rechargeCardId").val("");
				}
				
			}else{
				$("#isMetaData").val("Y");
				$("#customAmountDiv").addClass("hidden");
				
				var amount=$(this).children('option:selected').attr("amount");
				
				if(amount!=null && amount !=""){
					$("#amount").html(amount+"元");
					$("#rechargeCardId").val(id);
				}
			
			
			}
			
			
			
		}) 
		
	$('#cardList').change();
		
	$('#phoneNo').bind('input propertychange', function() {
		
		var phoneNo=$("#phoneNo").val();
		var re = /^1\d{10}$/
		 
		if(phoneNo!=null && phoneNo!="" && re.test(phoneNo)){
			$.ajax({
				url: '<%=path%>/account/commissionCardForSearchSubscriberInfo.action',
			    data: {"subscriber.phoneNo":$("#phoneNo").val()},
			    type: 'POST',
				dataType: 'html',
				success: function(data){
				 		$("#subscriberInfo").html(data);
				}
			});
		}else{
			
			$("#subscriberId").val("");
			$("#usableAmountSpan").html("--");
			$("#frozenAmountSpan").html("--");
			$("#amountSpan").html("--");
			$("#subscriberName").html("--");
			$("#submitBtn").attr("disabled","disabled");
			$("#nameSpan").html("--");
		}
		
	}); 
	
	
	
	$('#customAmount').bind('input propertychange', function() {
		var amount=$("#customAmount").val();
		if(amount!=null && amount !=""){
			$("#amount").html(amount+"元");
			$("#rechargeCardId").val("");
		}else{
			$("#amount").html("--元");
			$("#rechargeCardId").val("");
		}
	});
	
	
});


function submitForm(){
	
	
	 if($("#cardList").val()==null || $("#cardList").val()==""){
			alert("请选择充值卡");
			return;
	 } 

	 if($("#cardList").val()=="zdy" &&($("#customAmount").val()==null ||$("#customAmount").val()=="")){
			alert("请选择填写充值金额");
			return;
	 } 
	
	 if($("#remark").val()==null || $("#remark").val()==""){
			alert("请在备注中填写代充原因");
			return;
	}

	if(confirm("确认充值?")){
		$("#rechargeForm").submit();	
	}
	
		
	
	
}


</script>
</head>

<body  class="dcPage" style="overflow-y: auto;">
<form class="form-horizontal" id="rechargeForm" action="<%=path%>/account/saveCommissionRechargeCard.action" method="post">
<ww:token></ww:token>

<ww:if test="msg!=null">
<div class="form-group">
    <label class="col-sm-2 control-label">&nbsp;</label>
	<div class="alert alert-warning col-sm-3">
	  <ww:property value="msg"/>
	</div>
	</div>
</ww:if>
  

 
  <div class="form-group">
    <label for="cardList" class="col-sm-2 control-label"><span class="xx red"></span>充值卡</label>
    <div class="col-sm-3">
      <select class="form-control input_size" id="cardList"  >
			
			<ww:iterator value="rechargeCardList" >
				<option amount="<ww:property value="amount"/>" id="<ww:property value="id"/>"><ww:property value="showName"/></option>
			</ww:iterator>
			<option value="zdy" id="zdy">自定义</option>
	  </select>
	  <input  name="isMetaData" id="isMetaData" type="hidden">
	  <input type="hidden" id="rechargeCardId" name="rechargeCard.id">
    </div>
  </div>
   <div class="form-group hidden" id="customAmountDiv">
    <label class="col-sm-2 control-label"></label>
    <div class="col-sm-3">
     <input type="text" class="form-control input_size"  name="customAmount" id="customAmount" placeholder="请输入10-10000整数">
    </div>
  </div>
  <!-- <div class="form-group">
    <label class="col-sm-2 control-label">金额</label>
    <div class="col-sm-10">
      <p class="form-control-static" id="amount"></p>
    </div>
  </div> -->
  
  <div class="form-group">
    <label for="cardList" class="col-sm-2 control-label"><span class="xx red"></span>支付方式</label>
    <div class="col-sm-3">
      <select class="form-control input_size" name="payType" id="payType">
			<option value="pos">POS机</option>
			<option value="cash">现金</option>
			<option value="other">其它</option>
	  </select>
    </div>
  </div>
  
   <div class="form-group">
    <label class="col-sm-2 control-label"><span class="xx red"></span>备注</label>
    <div class="col-sm-10">
      <p class="form-control-static" >
      	<textarea class="form-control" name="remark" id="remark" rows="3" cols="30"></textarea>
      </p>
    </div>
  </div>
 
 
  <div class="form-group">
    <label for="phoneNo" class="col-sm-2 control-label"><span class="xx red"></span>手机号</label>
    <div class="col-sm-3">
	  <input type="text"  class="form-control input_size fl" id="phoneNo"   maxlength="11" />
    </div>
  </div>
  
  
   <div id="subscriberInfo"  >
   
  <div class="form-group">
    <label for="subscriberId" class="col-sm-2 control-label">姓名</label>
    <div class="col-sm-10">
      <p class="form-control-static" id="subscriberName">--</p>
	  <input type="hidden" name="subscriber.id" id="subscriberId" value="">
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">余额</label>
    <div class="col-sm-10">
      <p class="form-control-static" id="amountSpan">--</p>
    </div>
  </div>
 
  <div class="form-group">
    <label class="col-sm-2 control-label">押金</label>
    <div class="col-sm-10">
      <p class="form-control-static" id="frozenAmountSpan">--</p>
    </div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">可用余额</label>
    <div class="col-sm-10">
      <p class="form-control-static" id="usableAmountSpan">--</p>
    </div>
  </div>
  
  <div class="form-group">
    <div class="col-sm-offset-5 col-sm-2">
      <button type="button" disabled="disabled" id="submitBtn" class="btn btn-danger btn-block disabled">充值</button>
    </div>
  </div>
  
  </div>
  
  

	
	 
	
</form>
		

</body>


</html>