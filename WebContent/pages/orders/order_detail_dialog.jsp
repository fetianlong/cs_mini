<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>订单详情</title>
<%@ include file="/pages/common/common_head.jsp"%>


</head>
<body  class="hyglbjPage" style="overflow-y:auto;">
      <div class="tc" >
				<input type="hidden" name="subscriber.id" id="subscriber.id"value="<ww:property value="subscriber.id" />">
        	 <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
                <tr>
	                <td class="zuo"><span>订单编号</span>:</td>
	                <td class="you">
	                  <input class="input_size fl" type="text"  maxlength="30" name="orders.ordersNo" id="orders.ordersNo"  
	                  value="<ww:property value="orders.ordersNo" />" />
	                </td>  
	                <td class="zuo1"><span>订单状态</span>:</td>
	                  <td class="you1">
	                  	<div class="btt1 fl">
	                  	 <input class="input_size fl" type="text"   name="orders.state" id="orders.state"  
	                 	 value="<ww:property value="@com.dearho.cs.sys.util.DictUtil@getCnNameByCode('14',orders.state)" />" />  
							</div>
	                  </td>   
             </tr>
             
             
              <tr>
	                <td class="zuo"><span>租用车辆</span>:</td>
	                <td class="you">
	                  <input class="input_size fl" type="text"   value="<ww:property value="orders.vehicleModelName" />" />
	                </td>  
	                <td class="zuo1"><span>租用车辆</span>:</td>
	                  <td class="you1">
	                  	<div class="btt1 fl">
	                  	 <input class="input_size fl" type="text"   value="<ww:property value="orders.plateNumber" />" />  
						</div>
	                 </td>   
             </tr>
              <tr>
	                <td class="zuo"><span>支付方式</span>:</td>
	                <td class="you">
	                  <input class="input_size fl" type="text"   value="<ww:property value="@com.dearho.cs.sys.util.DictUtil@getCnNameByCode('12',orders.payStyle)" />" />
	                </td>  
	                <td class="zuo1"><span>承租人</span>:</td>
	                    <td class="you1">
	                  	<div class="btt1 fl">
	                  	 <input class="input_size fl" type="text"   value="<ww:property value="orders.memberName" />" />  
						</div>
	                 </td>   
             </tr>
             
              <tr>
	                <td class="zuo"><span>预定取车时间</span>:</td>
	                <td class="you">
	                  <input class="input_size fl" type="text"   value="<ww:property value="transDateString(orders.ordersTime)" />" />
	                </td>  
	                <td class="zuo1"><span>预计还车时间</span>:</td>
	                    <td class="you1">
	                  	<div class="btt1 fl">
	                  	 <input class="input_size fl" type="text"   value="<ww:property value="transDateString(orders.ordersBackTime)" />" />  
						</div>
	                 </td>   
             </tr>
             
             <tr>
	                <td class="zuo"><span>预定取车地点</span>:</td>
	                <td class="you">
	                  <input class="input_size fl" type="text"   value="<ww:property value="orders.beginSiteId" />" />
	                </td>  
	                <td class="zuo1"><span>预计还车地点</span>:</td>
	                    <td class="you1">
	                  	<div class="btt1 fl">
	                  	 <input class="input_size fl" type="text"   value="<ww:property value="orders.ordersBackSiteId" />" />  
						</div>
	                 </td>   
             </tr>
             
             
             <tr>
	                <td class="zuo"><span>实际取车时间</span>:</td>
	                <td class="you">
	                  <input class="input_size fl" type="text"   value="<ww:property value="transDateString(orders.beginTime)" />" />
	                </td>  
	                <td class="zuo1"><span>实际还车时间</span>:</td>
	                    <td class="you1">
	                  	<div class="btt1 fl">
	                  	 <input class="input_size fl" type="text"   value="<ww:property value="transDateString(orders.endTime)" />" />  
						</div>
	                 </td>   
             </tr>
               <tr>
	                <td class="zuo"><span>实际还车地点</span>:</td>
	                <td class="you">
	                  <input class="input_size fl" type="text"   value="<ww:property value="orders.endSiteId" />" />
	                </td>  
	                <td class="zuo1"><span>是否需要发票</span>:</td>
	                    <td class="you1">
	                  	<div class="btt1 fl">
	                  		
	                  	 <input class="input_size fl" type="text"   value="<ww:if test="orders.isBill==1">需要</ww:if><ww:else>不需要</ww:else>" />  
						</div>
	                 </td>   
             </tr>
             
             <tr>
	                <td class="zuo"><span>用车时长</span>:</td>
	                <td class="you">
	                  <input class="input_size fl" type="text"   value="<ww:property value="orders.ordersDuration" />" />
	                </td>  
	                <td class="zuo1"><span>总费用</span>:</td>
	                    <td class="you1">
	                  	<div class="btt1 fl">
	                  	 <input class="input_size fl" type="text"   value="<ww:property value="formatAmount(orders.totalFee)" />" />  
						</div>
	                 </td>   
             </tr>
             
            
             

       </table>
            
     		
     	</div>
     	
     	
	
  <div class="ta">

  <!-- Nav tabs -->
<ul class="nav nav-tabs" id="myTab">
   <li><a href="#vehicleConverse" data-toggle="tab" refHref="carservice/carViolationSearchForDialog.action" refId="vehicleConverse">相关违章</a></li>
   <li><a href="#accident"        data-toggle="tab" refHref="carservice/carAccidentSearchForDialog.action" refId="accident">相关事故</a></li>
</ul>

  <!-- Tab panes -->
  <div class="tab-content">
    <div role="tabpanel" class="tab-pane fade" isload="N" id="vehicleConverse"></div>
    <div role="tabpanel" class="tab-pane fade" isload="N" id="accident"></div>
  </div>

</div>
</body>
<script type="text/javascript">
 
$(function () {
	$('#myTab a').click(function (e) {
		  e.preventDefault()

		  var refId=$(this).attr("refId");
		  var refHref=$(this).attr("refHref");
		  var isLoad=$("#"+refId).attr("isload");
		  
		  
		  if(isLoad=="Y"){
			  $(this).tab('show')
		  }else{
			  $.ajax({
				  url: '<%=path%>/'+refHref,
				  data:{"carViolation.orderId":'<ww:property value="orders.id" />',"carAccident.orderId":'<ww:property value="orders.id" />'},
			      type: 'POST',
				  dataType: 'html',
				  success: function(data){
				 		$("#"+refId).html(data);
				 		$(this).tab('show')
				 		$("#"+refId).attr("isload","Y");	
				  }
				});
		  }
		  
		  
		 
		});
	
	$('#myTab a:first').click();
});
</script>
</html>