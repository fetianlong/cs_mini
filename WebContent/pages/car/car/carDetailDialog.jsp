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

<title>车辆管理</title>

<%@ include file="/pages/common/common_head.jsp"%>

</head>
<body style="overflow-y:auto;">
      <div class="tc">
		<form action="" method="post" id="eform" name="eform">
        	 <input type="hidden" name="car.id" value="<ww:property value="car.id" />" />
        	 <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
                 <tr>
                    <td class="zuo"><span>车牌号</span>:</td>
                    <td class="you">
                      <input  disabled="disabled" class="input_size fl" type="text" style="top: 0;left: 0px" name="car.vehiclePlateId" maxlength="7" id="car.vehiclePlateId"  value="<ww:property value="car.vehiclePlateId" />" />
                    </td>  
                    <td class="zuo1"><span>车辆型号 </span>:</td>
                    <td class="you1">
                      <div class="btt1 fl">
                      	 <select  disabled="disabled" id="car.modelId" name="modelId" onchange="queryModel(this.options[this.options.selectedIndex].value);" >
							<option value="">请选择</option>
							<ww:iterator value="carVehicleModels">
								<option  value="<ww:property value="toString4CarAdd()"/>" <ww:if test="car.modelId.equals(id)">selected=true</ww:if>>
									<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('10',brand)"/>-<ww:property value="name"/>
								</option>
							</ww:iterator>
						 </select>
						 <input  type="hidden" name="car.modelId" id="modelId" value="<ww:property value="car.modelId" />" />
                      </div>
                    </td>   
                 </tr>
                 <tr>
                      <td class="zuo"><span>车架号</span>:</td>
                      <td class="you">
                        <input  disabled="disabled" class="input_size fl" maxlength="17"  style="top: 0;left: 0px"  type="text" name="car.vin" id="car.vin"  value="<ww:property value="car.vin" />" />
                      </td>  
                      <td class="zuo1"><span>车辆等级</span>:</td>
                      <td class="you1">
                        <input class="input_size fl" type="text" id="level" disabled="disabled" 
                        value="<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('1',car.carVehicleModel.level)" />" />
                      </td>   
                   </tr>
                  <tr>
                      <td class="zuo"><span>昵称</span>:</td>
                      <td class="you">
                        <input  disabled="disabled" class="input_size fl" maxlength="30" style="top: 0;left: 0px"  type="text" name="car.nickName" value="<ww:property value="car.nickName"/>" />
                      </td>  
                      <td class="zuo1"><span>动力类型</span>:</td>
                      <td class="you1">
                        <input class="input_size fl" type="text" id="engineType"  disabled="disabled" 
                        value="<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('2',car.carVehicleModel.engineType)" />" />
                      </td>   
                   </tr>
                   <tr>
                      <td class="zuo"><span>车辆颜色 </span>:</td>
                      <td class="you">
                        <input  disabled="disabled" class="input_size fl" maxlength="15" style="top: 0;left: 0px"  type="text" name="car.color" id="car.color" value="<ww:property value="car.color" />"/>
                      </td>  
                      <td class="zuo1"><span>座位数</span>:</td>
                      <td class="you1">
                        <input  disabled="disabled" class="input_size fl" type="text" id="seatingNum" disabled="disabled" value="<ww:property value="car.carVehicleModel.seatingNum" />" />
                      </td>   
                   </tr>
                   <tr>
                      <td class="zuo"><span>车辆状态 </span>:</td>
                      <td class="you">
                         <div class="btt1 fl">
	                         <select class="kd"  style="top:17px"id="car.bizState" name="car.bizState"  disabled="disabled">
								<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carBizState',2)" id="data" status="rl">
									<option value="<ww:property value="id" />"  <ww:if test="car.bizState==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
								</ww:iterator>
							</select>
	                      </div>
                      </td>  
                      <td class="zuo1"><span>行李数</span>:</td>
                      <td class="you1">
                        <input  disabled="disabled" class="input_size fl" type="text" id="luggageNum" disabled="disabled" value="<ww:property value="car.carVehicleModel.luggageNum"/>" />
                      </td>   
                   </tr>
                   <tr>
                      <td class="zuo"><span>是否是广告车 </span>:</td>
                      <td class="you">
                        <div class="btt1 fl">
                        	<select name="car.isAd"  disabled="disabled">
								<option value="1" <ww:if test="car.isAd==1">selected="selected"</ww:if>>是 </option>
								<option value="0" <ww:if test="car.isAd==0">selected="selected"</ww:if>>否</option>
							</select>
                        </div>
                      </td>  
                      <td class="zuo1"><span>档类别</span>:</td>
                      <td class="you1">
                        <input class="input_size fl" type="text" id="gearboxType" disabled="disabled" 
                        value="<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('3',car.carVehicleModel.gearboxType)" />" />
                      </td>   
                   </tr>
                   <tr></tr>
                   
             </table>
          
     			</form>
     	</div>
     
     	
<div class="ta">
  <!-- Nav tabs -->
  <ul class="nav nav-tabs" id="myTab">
   <li><a href="#orders"   		  data-toggle="tab" refHref="orders/ordersSearchForDialog.action" 	refId="orders">订单记录</a></li>
   <li><a href="#vehicleConverse" data-toggle="tab"  refHref="carservice/carViolationSearchForDialog.action" refId="vehicleConverse">违章记录</a></li>
   <li><a href="#accident"        data-toggle="tab" refHref="carservice/carAccidentSearchForDialog.action" refId="accident">事故记录</a></li>
    <li><a href="#maintenance"        data-toggle="tab" refHref="carservice/carMaintenanceSearchForDialog.action" refId="maintenance">保养记录</a></li>
    <li><a href="#operation"        data-toggle="tab" refHref="dialog/queryOperationLog.action" refId="operation">操作日志</a></li>
    <li><a href="#carcommon"        data-toggle="tab" refHref="carservice/searchCarCommon.action?carId=<ww:property value="car.id" />" refId="carcommon">车辆评价</a></li>
   <!--  <li><a href="#onoff"        data-toggle="tab" refHref="order.action" refId="onoff">上下线记录</a></li> -->
</ul>
  <!-- Tab panes -->
  <div class="tab-content">
    <div role="tabpanel" class="tab-pane fade" isload="N" id="orders"></div>
    <div role="tabpanel" class="tab-pane fade" isload="N" id="vehicleConverse"></div>
    <div role="tabpanel" class="tab-pane fade" isload="N" id="accident"></div>
    <div role="tabpanel" class="tab-pane fade" isload="N" id="maintenance"></div>
    <div role="tabpanel" class="tab-pane fade" isload="N" id="operation"></div>
    <div role="tabpanel" class="tab-pane fade" isload="N" id="carcommon"></div>
  <!--   <div role="tabpanel" class="tab-pane fade" isload="N" id="onoff"></div> -->
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
				  data:{"carViolation.carId":'<ww:property value="car.id" />',"carAccident.carId":'<ww:property value="car.id" />',"orders.carId":'<ww:property value="car.id" />',"carMaintenance.carId":'<ww:property value="car.id" />',"operateLog.carId":'<ww:property value="car.id" />'},
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


var p;
function skipToPage(page, func){
	p=page;
	if (typeof func == 'function'){
		func();
	}else{
		searchTrading();
	};
}

function searchTrading(){

	 var refId=$("ul li.active a").attr("refId");
	 var refHref=$("ul li.active a").attr("refHref");
	
	$.ajax({
	 url: '<%=path%>/'+refHref+"?page.currentPage="+p,
	 data:$(".tab-content div.active form").serialize(),
     type: 'POST',
     dataType: 'html',
	  success: function(data){
	 		$("#"+refId).html(data);
	  },
	  error: function(a,b,c){
		  alert(b);
	  }
	});
}
</script>
</html>