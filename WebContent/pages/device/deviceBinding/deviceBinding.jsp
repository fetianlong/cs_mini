<%@page import="java.util.Date"%>
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

<title>车机绑定管理</title>
<%@ include file="/pages/common/common_head.jsp"%>
<script type="text/javascript">
$(function(){
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"vehiclePlateId": {
				required: true,
				minlength:7
			},
			"setNum":{
				required: true
			}
		},
		messages: {
			"vehiclePlateId": {
				required: "请选择车辆！",
				minlength:"请选择车辆"
			},
			"setNum":{
				required: "请选择车机"
			}
		}
		
	});
})
function getForm(){
	return $("#eform");
}
function isValid(){
	if ($("#eform").valid()){
		return true;
	}else{
		return false;
	}
}

//提交表单
function sub(){
	var id = '<ww:property value="deviceBinding.id" />';
	var url="";
	if (id == ""){
		url="<%=path%>/deviceBinding/deviceBindingAdd.action";
	}else{
		url="<%=path%>/deviceBinding/deviceBindingUpdate.action";	
	}
	var valid=isValid();
	if(valid){
		$.post(url,$("#eform").serialize(),r_savedata,'json').error(requestError);
	}
	
}

//保存的回调函数
function r_savedata(data){
	hideLoading();
	switch(data.result){
		case 0:
			alertok(data.msg, function(){
				window.location.href="<%=path%>/deviceBinding/deviceBindingSearch.action";
		    });
			break;
		case 1:
			alerterror(data.msg);
			break;
		case 9:
			document.location = "doError.action";
			break;
	}
	return false;
}
var api = frameElement.api, W = api.opener;
//车辆查询弹出框
function selectCar(){
	$.dialog({
		id:'searchCarDial',
	    title:'车辆查询',
		content : 'url:<%=path%>/car/carSearch.action?state=page',
		fixed:true,
		width:740,
		height:450,
		resize:false,
 		max: false,
	    min: false,
	    lock: true,
	    parent:api,
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}
var carBindEditDoc = api.get('carBindedit');
//选择车机
function selectDevice(){
	$.dialog({
		id:'searchDeviceDia',
	    title:'车机查询',
		content : 'url:<%=path%>/device/deviceSearch.action?state=page',
		fixed:true,
		width:740,
		height:450,
		resize:false,
 		max: false,
	    min: false,
	    lock: true,
	    init: function(){
	    	if (typeof this.content.isError != 'undefined'){
	    		$(":button").slice(0,1).hide();
	    	}
	    }
	});
}
//取消按钮
function cancel(){
	window.location.href="<%=path%>/deviceBinding/deviceBindingSearch.action";	
}
</script>
</head>
<body>
      <div class="tc">
              	<form action="<%=path%>/deviceBinding/deviceBindingAdd.action" method="post" id="eform" name="eform">
		           	<input type="hidden" name="deviceBinding.id" value="<ww:property value="deviceBinding.id" />" />
				<ww:if test="deviceBinding.bindType!=null">
					<input type="hidden" name="deviceBinding.bindType" value="<ww:property value="deviceBinding.bindType" />" />
				</ww:if>
				<ww:else>
					<input type="hidden" name="deviceBinding.bindType" value="1" />
				</ww:else>
				<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
					<tr>
		                <td class="zuo"><span class="xx red">*</span><span>车牌号</span>:</td>
		                <td class="you">
		                  <input type="hidden" name="deviceBinding.carId" id="carId" value="<ww:property value="deviceBinding.carId" />"/>
		                  <input name="vehiclePlateId" id="vehiclePlateId" type="text" readonly 
		                  	class="input_size fl" style="top: 0;left: 0px;position:relative;"
		                  		value="<ww:property value="deviceBinding.car.vehiclePlateId" />" />
							<input onclick="selectCar();" type="button" value="选择" class="searchinputbut" />
		                </td>  
		                <td class="zuo1"><span class="xx red">*</span><span>车机编号</span>:</td>
		                  <td class="you1">
		                    <input type="hidden" name="deviceBinding.deviceId" id="setId" value="<ww:property value="deviceBinding.deviceId" />"/>
		                    <input name="setNum" id="setNum" type="text" readonly 
		                  		class="input_size fl" style="top: 0;left: 0px;position:relative;"
		                  		value="<ww:property value="deviceBinding.device.setNo" />" />
								<input onclick="selectDevice();" type="button" value="选择" class="searchinputbut" />
		                  </td>   
		             </tr>
		             <tr>
		                <td class="zuo"><span>车架号</span>:</td>
		                <td class="you">
		                  <input class="input_size fl" type="text" disabled="disabled" id="vin"  
		                  value="<ww:property value="deviceBinding.car.vin" />" />
		                </td>  
		                <td class="zuo1"><span>厂家名称</span>:</td>
		                  <td class="you1"><input class="input_size fl" id="setName" type="text" disabled="disabled"
		                  	<ww:iterator value="getCompanys('7')" id="data" status="rl">
								<ww:if test="deviceBinding.device.setName==id">
		                  					value="<ww:property value="name" />"
								</ww:if> 
							</ww:iterator>
		                    />
		                  </td>   
		             </tr>
		             <tr>
		                <td class="zuo"><span>车辆品牌</span>:</td>
		                <td class="you">
		                  <input class="input_size fl" type="text" disabled="disabled" id="brand"  
		                  value="<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('10',deviceBinding.car.carVehicleModel.brand)"/>" />
		                </td>  
		                <td class="zuo1"><span>车机型号</span>:</td>
		                  <td class="you1">
		                    <input class="input_size fl" type="text" disabled="disabled" id="setType"  
		                  value="<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('deviceSetType',deviceBinding.device.setType)" />" />
		                  </td>   
		             </tr>
		             <tr>
		                <td class="zuo"><span>车辆型号</span>:</td>
		                <td class="you">
                             <input id="modelId" disabled="disabled" class="input_size fl" type="text"
                             value="<ww:property value="deviceBinding.car.carVehicleModel.name" />"   />
		                </td>  
		                <td class="zuo1"><span>SIM卡号</span>:</td>
		                  <td class="you1">
		                    <input class="input_size fl" type="text" disabled="disabled" id="simId"  
		                  value="<ww:property value="deviceBinding.device.simId" />" />
		                  </td>   
		             </tr>
		             <tr>
		                <td class="zuo"><span>车辆颜色</span>:</td>
		                <td class="you">
		                  <input class="input_size fl" type="text" disabled="disabled" id="color"  
		                  value="<ww:property value="deviceBinding.car.color" />" />
		                </td>  
		                <td class="zuo1"><span>是否刷过固件</span>:</td>
		                  <td class="you1">
		                    <input class="input_size fl" type="text" disabled="disabled" id="updateFirmware"  
		                  <ww:if test="deviceBinding.device.updateFirmware==1">value='是'</ww:if>
                             	<ww:elseif test="deviceBinding.device.updateFirmware==0">
                             		value='否'
                             	</ww:elseif> />
		                  </td>   
		             </tr>
		             <tr>
		                <td class="zuo"><span>车辆等级</span>:</td>
		                <td class="you">
		                  <input class="input_size fl" type="text" disabled="disabled" id="level"  
		                  value="<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('1',deviceBinding.car.carVehicleModel.level)" />" />
		                </td>  
		                <td class="zuo1"><span>固件版本</span>:</td>
		                  <td class="you1">
		                    <input class="input_size fl" type="text" disabled="disabled" id="firmwareVersion"  
		                  value="<ww:property value="deviceBinding.device.firmwareVersion" />" />
		                  </td>   
		             </tr>
		             <tr>
		                <td class="zuo"><span>座位数</span>:</td>
		                <td class="you">
		                  <input class="input_size fl" type="text" disabled="disabled" id="seatingNum"  
		                  value="<ww:property value="deviceBinding.car.carVehicleModel.seatingNum" />" />
		                </td>  
		             </tr>
		             <tr>
                  <td colspan="4">
                      <div class="btt">
                         <div class="sbtn fl" onclick="sub();">提&nbsp;&nbsp;交</div>
                         <div class="qzbtn fl" onclick="cancel();">取&nbsp;&nbsp;消</div>
                      </div>
                  </td>
             </tr>
				</table>
         </form>
	</div>
</body>
</html>