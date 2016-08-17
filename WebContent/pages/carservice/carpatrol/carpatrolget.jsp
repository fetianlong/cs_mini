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

<title>车辆巡检</title>
<style type="text/css">
</style>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	var deletePhotoArr = [];
	$(function(){
		for(var imgIndex = 0;imgIndex <= 5;imgIndex++){
			$("#up"+imgIndex).uploadPreview({ Img: "ImgPr"+imgIndex, Width: 120, Height: 120 });
			deletePhotoArr.push('1');
		}
		var id = '<ww:property value="id" />';
		var url="";
		if (id == "" || id == "undefined"){
			url="<%=path%>/carservice/carPatrolAdd.action";
		}else{
			url="<%=path%>/carservice/carPatrolUpdate.action";	
		}
		$("#eform").attr('action',url);
		$('#eform').validate({
			errorClass : 'text-danger',
			rules: {
				"carPatrol.code":{
					required: true,
					carPatrolCodeSc:true
				},
				"parkingPatrolCode":{
					required: true
				},
				"vehiclePlateId":{
					required: true
				},
				"carPatrol.paint":{
					required: true
				},
				"carPatrol.glass":{
					required: true
				},
				"carPatrol.tirePressure":{
					required: true
				},
				"carPatrol.SOC":{
					number: true
				}
			},
			messages: {
				"carPatrol.code":{
					required: "请输入编码！"
				},
				"parkingPatrolCode":{
					required: "请选择网点巡检单号！"
				},
				"vehiclePlateId":{
					required: "请选择巡检车辆！"
				},
				"carPatrol.paint":{
					required: "请选择四周漆面是否完好！"
				},
				"carPatrol.glass":{
					required: "请选择玻璃是否完好！"
				},
				"carPatrol.tirePressure":{
					required: "请选择胎压！",
				},
				"carPatrol.SOC":{
					number: "请输入数字！"
				}
			}
			
		});
		
		val_check_SpecialChar("carPatrolCodeSc");
		
		
	});
	function isValid(){
		if ($("#eform").valid()){
			return true;
		}else{
			return false;
		}
	}
	//取消按钮
	function cancel(){
		window.location.href="<%=path%>/carservice/carPatrolSearch.action";
	}
	function getForm(){
		return $("#eform");
	}
	
	function sub(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == "" || id == "undefined"){
			url="<%=path%>/carservice/carPatrolAdd.action";
		}else{
			url="<%=path%>/carservice/carPatrolUpdate.action";	
		}
		
		var re=isValid();
		if(re){
			var formData = $("#eform").serialize();
			var data = new FormData();
			data.append('frontPhoto', $(':file')[0].files[0]);
			data.append('backPhoto', $(':file')[1].files[0]);
			data.append('leftPhoto', $(':file')[2].files[0]);
			data.append('rightPhoto', $(':file')[3].files[0]);
			data.append('sparePhoto1', $(':file')[4].files[0]);
			data.append('sparePhoto2', $(':file')[5].files[0]);
			 data.append('deletePhotoArr',deletePhotoArr);
			var formDatas = formData.split('&');
			$.each(formDatas,function(index,fd){
				data.append(fd.split('=')[0],fd.split('=')[1]);
			});
			 $.ajax({
			        url: url,
			        type: 'POST',
			        data: data,
			        processData: false,
			        contentType: false,
			        dataType: 'json',
			        success:function(data){
			        	r_saveCar(data);
			        }

			    });
		}
	}
	function r_saveCar(data){
		switch(data.result){
			case 0:
				alertok(data.msg, function(){
					window.location.href="<%=path%>/carservice/carPatrolSearch.action?";
			    });
				break;
			case 1:
				alerterror(data.msg);
				break;
			case 9:
				document.location = "doError.action";
				break;
		}
	}
function selectCar(){
	var parkingPatrolId = $('#parkingPatrolId').val();
	if(parkingPatrolId == null || parkingPatrolId == ""){
		$.dialog.tips("请先选择网点巡检单！");
		return;
	}
	$.dialog({
		id:'searchCarDia',
	    title:'车辆查询',
		content : 'url:<%=path%>/car/carSearch.action?state=page&parkingPatrolId='+parkingPatrolId,
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

function selectParkingPatrol(){
	$.dialog({
		id:'selectParkingPatrolDia',
	    title:'网点巡检单查询',
		content : 'url:<%=path%>/carservice/parkingPatrolSearch.action?state=page',
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
function deletePhoto(imgIndex){
    $('#ImgPr'+imgIndex).val('');
    $('#ImgPr'+imgIndex).attr('src','');
    $('#up'+imgIndex).show();
    $('#imgShow'+imgIndex).val('');
    $('#imgShow'+imgIndex).attr('src','');
    $('#imgShow'+imgIndex).attr('width','0');
    $('#imgShow'+imgIndex).attr('height','0');
    deletePhotoArr[imgIndex]='0';
}
function subAndReturn(){
	var id = '<ww:property value="id" />';
	var url="";
	if (id == "" || id == "undefined"){
		url="<%=path%>/carservice/carPatrolAdd.action";
	}else{
		url="<%=path%>/carservice/carPatrolUpdate.action";	
	}
	
	var re=isValid();
	if(re){
		var formData = $("#eform").serialize();
		var data = new FormData();
		data.append('frontPhoto', $(':file')[0].files[0]);
		data.append('backPhoto', $(':file')[1].files[0]);
		data.append('leftPhoto', $(':file')[2].files[0]);
		data.append('rightPhoto', $(':file')[3].files[0]);
		data.append('sparePhoto1', $(':file')[4].files[0]);
		data.append('sparePhoto2', $(':file')[5].files[0]);
		 data.append('deletePhotoArr',deletePhotoArr);
		var formDatas = formData.split('&');
		$.each(formDatas,function(index,fd){
			data.append(fd.split('=')[0],fd.split('=')[1]);
		});
		 $.ajax({
		        url: url,
		        type: 'POST',
		        data: data,
		        processData: false,
		        contentType: false,
		        dataType: 'json',
		        success:function(data){
		        	window.location.href="<%=path%>/carservice/parkingPatrolGet.action?id=<ww:property value="parkingPatrolId" />";
		        }

		    });
	}
}
function returnParkingPatrol(){
	window.location.href="<%=path%>/carservice/parkingPatrolGet.action?id=<ww:property value="parkingPatrolId" />";
}
</script>

</head>
<body style="overflow-y: auto;">
      <div class="tc">
		<form name="eform" id="eform" method="post" action="" enctype="multipart/form-data" >
			<input type="hidden" name="carPatrol.id" id="carPatrol.id"
						value="<ww:property value="carPatrol.id" />">
			<input type="hidden" name="carPatrol.isDiscard" 
						value="<ww:property value="carPatrol.isDiscard" />">
			<input type="hidden" name="parkingPatrolId" 
						value="<ww:property value="parkingPatrolId" />">
		  <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		  	<tr>
                <td class="zuo"><span class="xx red">*</span><span>编号</span>:</td>
                <td class="you">
                  <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" name="carPatrol.code" id="carPatrol.code"  
                  value="<ww:property value="carPatrol.code" />" />
                </td>  
                 <td class="zuo1"><span class="xx red">*</span><span>网点巡检单号</span>:</td>
                  <td class="you1">
                  	<input type="hidden" name="carPatrol.parkingPatrolId" id="parkingPatrolId" value="<ww:property value="carPatrol.parkingPatrolId" />"/>
                 	
                  <input name="parkingPatrolCode"  id="parkingPatrolCode" type="text" readonly 
                  	class="input_size fl" style="top: 0;left: 0px;position:relative;"
                  		value="<ww:property value="carPatrol.parkingPatrolCode" />" />
					<ww:if test="parkingPatrolId == null || parkingPatrolId == ''">
                 		<input  onclick="selectParkingPatrol();" type="button" value="选择" class="searchinputbut" />
                   	</ww:if>
                  </td>  
             </tr>
             <tr>
             	<td class="zuo1"><span class="xx red">*</span><span>巡检车辆</span>:</td>
                  <td class="you1">
                  	<input type="hidden" name="carPatrol.carId" id="carId" value="<ww:property value="carPatrol.carId" />"/>
                  <input name="vehiclePlateId"  id="vehiclePlateId" type="text" readonly 
                  	class="input_size fl" style="top: 0;left: 0px;position:relative;"
                  		value="<ww:property value="carPatrol.plateNumber" />" />
						<input id="carIdSearchInp" onclick="selectCar();" type="button" value="选择" class="searchinputbut" />
                  </td>  
             </tr>
		  	 <tr>
		  	 	<td class="zuo"><span class="xx red">*</span><span>四周漆面是否完好</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.paint"  style="top:12px;height:26px;">
                      	<option value="1" <ww:if test="carPatrol.paint==1">selected="selected"</ww:if>>是</option>
                        <option value="0" <ww:if test="carPatrol.paint==0">selected="selected"</ww:if>>否</option>
					  </select>
				  </div>
                </td> 
                <td class="zuo1"><span class="xx red">*</span><span>玻璃是否完好</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.glass"  style="top:12px;height:26px;">
                      	<option value="1" <ww:if test="carPatrol.glass==1">selected="selected"</ww:if>>是</option>
                        <option value="0" <ww:if test="carPatrol.glass==0">selected="selected"</ww:if>>否</option>
					  </select>
				  </div>
                </td>  
             </tr>
             <tr>
		  	 	<td class="zuo"><span class="xx red">*</span><span>胎压是否正常</span>:</td>
                <td class="you">
                    <div class="btt1 fl">
                      <select name="carPatrol.tirePressure"  style="top:12px;height:26px;">
                      	<option value="1" <ww:if test="carPatrol.tirePressure==1">selected="selected"</ww:if>>是</option>
                        <option value="0" <ww:if test="carPatrol.tirePressure==0">selected="selected"</ww:if>>否</option>
					  </select>
					</div>
                </td> 
                <td class="zuo1"><span>电量(%)</span>:</td>
                <td class="you1">
                	<input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="10"
                    id="carPatrol.SOC" name="carPatrol.SOC" 
                    value="<ww:property value="carPatrol.SOC" />" />
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>前车轮</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.frontWheel" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.frontWheel==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>后车轮</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.rearWheel" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.rearWheel==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>安全带</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.safetyBelt"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.safetyBelt==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>挡风玻璃</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.windshield" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.windshield ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>遮阳板（左）</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.sunVisorL"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.sunVisorL ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>遮阳板（右）</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.sunVisorR" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.sunVisorR ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>喇叭</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.horn"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.horn ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>天窗开关</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.skylightSwitch" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.skylightSwitch ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>雨刷器</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.wiper"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.wiper ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>车灯开关</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.lampSwitch" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.lampSwitch ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>座椅（外观）</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.chairFace"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.chairFace ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>座椅（调节）</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.chairAdjust" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.chairAdjust ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>反光镜</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.reflector"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.reflector ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>雨刷器开关</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.wiperSwitch" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.wiperSwitch ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>内饰</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.interior"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.interior ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>门外拉手</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.doorHandle" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.doorHandle ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>手刹</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.handbrake"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.handbrake ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>脚垫</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.doorMat" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.doorMat ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>车窗玻璃</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.windowGlass"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.windowGlass ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>刹车</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.brake" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.brake ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>随车税标</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.taxStandard"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.taxStandard ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>天窗</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.skylight" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.skylight ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>空调</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.airConditioning"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.airConditioning ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>车机GPS</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.vehicleGPS" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.vehicleGPS ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>车灯</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.light"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.light ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>音响</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.sound" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.sound ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>儿童座椅</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.childrenSeat"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.childrenSeat ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>千斤顶</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.jack" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.jack ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>点烟器</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.cigaretteLighter"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.cigaretteLighter ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>车钥匙</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.carKey" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.carKey ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>工具包</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.toolKit"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.toolKit ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>烟灰缸</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.ashtray" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.ashtray ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>行驶证</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.drivingLicense"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.drivingLicense ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>故障警示牌</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.failureWarningPlate" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.failureWarningPlate ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>储物盒</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.storageBox"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.storageBox ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>车标</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.autoLogo" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.autoLogo ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>备胎</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.spareTire"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.spareTire ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>车窗升降</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.windowLift" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.windowLift ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>随车手册</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.book"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.book ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>灭火器</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.fireExtinguisher" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.fireExtinguisher ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>室内灯</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.indoorLight"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.indoorLight ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>仪表盘</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.dashboard" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.dashboard ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>转向灯</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.steeringLamp"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.steeringLamp ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>方向盘</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carPatrol.steeringWheel" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.steeringWheel ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
             </tr>
             <tr>
		  	 	<td class="zuo"><span>车内后视灯</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
                      <select name="carPatrol.inRearLamp"  style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carPatrolType',3)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carPatrol.inRearLamp ==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td> 
                <td class="zuo1"><span>总体是否完整</span>:</td>
                <td class="you1">
                	<div class="btt1 fl">
                      <select name="carAccident.isOk"  style="top:12px;height:26px;">
                      	<option value="1" <ww:if test="carAccident.isOk==1">selected="selected"</ww:if>>是</option>
                        <option value="0" <ww:if test="carAccident.isOk==0">selected="selected"</ww:if>>否</option>
					  </select>
				  </div>
                </td> 
             </tr>
             <tr style="height:10px"><td colspan="4" style=" border-top:2px solid #A0AFAE;"></td></tr>
             <tr>
		  	 	<td class="zuo1"><span>车前照片</span>:</td>
                <td class="you1">
                	<div> 
          				<div>
          					<ww:if test="carPatrol.frontPhoto != null && carPatrol.frontPhoto != ''">
          						<img id="imgShow0" class="inputfileshow" src="<%=path%>/carpatrolimgs/<ww:property value="carPatrol.frontPhoto" />"/>
          					</ww:if>
          					<img id="ImgPr0" class="inputfileshow"/>
          				</div> 
          				<input type="file" class="inputfile" id="up0" <ww:if test="carPatrol.frontPhoto != null && carPatrol.frontPhoto != ''">style="display:none"</ww:if> />
          				<input type="button" id="deleteInp0" value="删除图片" onclick="deletePhoto(0)"/> 
              		</div>
                </td> 
                <td class="zuo1"><span>车后照片</span>:</td>
                <td class="you1">
                	<div> 
          				<div>
          					<ww:if test="carPatrol.backPhoto != null && carPatrol.backPhoto != ''">
          						<img id="imgShow1" class="inputfileshow"src="<%=path%>/carpatrolimgs/<ww:property value="carPatrol.backPhoto" />"/>
          					</ww:if>
          					<img id="ImgPr1" class="inputfileshow" />
          				</div> 
          				<input type="file" class="inputfile" id="up1" <ww:if test="carPatrol.backPhoto != null && carPatrol.backPhoto != ''">style="display:none"</ww:if> />
          				<input type="button" id="deleteInp1" value="删除图片" onclick="deletePhoto(1)"/> 
              		</div>
                </td> 
             </tr>
             <tr style="height:10px"><td colspan="4"></td></tr>
             <tr style="height:10px"><td colspan="4" style=" border-top:1px solid #A0AFAE;"></td></tr>
             <tr>
		  	 	<td class="zuo1"><span>车左照片</span>:</td>
                <td class="you1">
                	<div> 
          				<div>
          					<ww:if test="carPatrol.leftPhoto != null && carPatrol.leftPhoto != ''">
          						<img id="imgShow2" class="inputfileshow" src="<%=path%>/carpatrolimgs/<ww:property value="carPatrol.leftPhoto" />"/>
          					</ww:if>
          					<img id="ImgPr2" class="inputfileshow" />
          				</div> 
          				<input type="file" class="inputfile" id="up2" <ww:if test="carPatrol.leftPhoto != null && carPatrol.leftPhoto != ''">style="display:none"</ww:if> />
          				<input type="button" id="deleteInp2" value="删除图片" onclick="deletePhoto(2)"/> 
              		</div>
                </td> 
                <td class="zuo1"><span>车右照片</span>:</td>
                <td class="you1">
                	<div> 
          				<div>
          					<ww:if test="carPatrol.rightPhoto != null && carPatrol.rightPhoto != ''">
          						<img id="imgShow3" class="inputfileshow" src="<%=path%>/carpatrolimgs/<ww:property value="carPatrol.rightPhoto" />"/>
          					</ww:if>
          					<img id="ImgPr3" class="inputfileshow" />
          				</div> 
          				<input type="file" class="inputfile" id="up3" <ww:if test="carPatrol.rightPhoto != null && carPatrol.rightPhoto != ''">style="display:none"</ww:if> />
          				<input type="button" id="deleteInp3" value="删除图片" onclick="deletePhoto(3)"/> 
              		</div>
                </td> 
             </tr>
              <tr style="height:10px"><td colspan="4"></td></tr>
             <tr style="height:10px"><td colspan="4" style=" border-top:1px solid #A0AFAE;"></td></tr>
             <tr>
		  	 	<td class="zuo1"><span>备用照片1</span>:</td>
                <td class="you1">
                	<div> 
          				<div>
          					<ww:if test="carPatrol.sparePhoto1 != null && carPatrol.sparePhoto1 != ''">
          						<img id="imgShow4" class="inputfileshow" src="<%=path%>/carpatrolimgs/<ww:property value="carPatrol.sparePhoto1" />"/>
          					</ww:if>
          					<img id="ImgPr4" class="inputfileshow" />
          				</div> 
          				<input type="file" class="inputfile" id="up4" <ww:if test="carPatrol.sparePhoto1 != null && carPatrol.sparePhoto1 != ''">style="display:none"</ww:if> />
          				<input type="button" id="deleteInp4" value="删除图片" onclick="deletePhoto(4)"/> 
              		</div>
                </td> 
                <td class="zuo1"><span>备用照片2</span>:</td>
                <td class="you1">
                	<div> 
          				<div>
          					<ww:if test="carPatrol.sparePhoto2 != null && carPatrol.sparePhoto2 != ''">
          						<img id="imgShow5" class="inputfileshow"  src="<%=path%>/carpatrolimgs/<ww:property value="carPatrol.sparePhoto2" />"/>
          					</ww:if>
          					<img id="ImgPr5" class="inputfileshow" />
          				</div> 
          				<input type="file" class="inputfile" id="up5" <ww:if test="carPatrol.sparePhoto2 != null && carPatrol.sparePhoto2 != ''">style="display:none"</ww:if> />
          				<input type="button" id="deleteInp5" value="删除图片" onclick="deletePhoto(5)"/> 
              		</div>
                </td> 
             </tr>
             <tr style="height:10px"><td colspan="4"></td></tr>
             <tr style="height:10px"><td colspan="4" style=" border-top:2px solid #A0AFAE;"></td></tr>
              <tr>
              	<td class="zuo1"><span>备注</span>:</td>
                  <td class="you1" colspan="3">
                  	<textarea name="carPatrol.remark" style="width: 91%" class="textarea_size"><ww:property value="carPatrol.remark"/></textarea>
                  </td>  
                 
              </tr>
              <tr></tr>
		  	<tr>
                  <td colspan="4">
                      <div class="btt">
                      	<ww:if test="parkingPatrolId != null && parkingPatrolId != ''">
                      		<div class="sbtn fl" onclick="subAndReturn();">保&nbsp;&nbsp;存</div>
                      		<div class="qzbtn fl" onclick="returnParkingPatrol();">返&nbsp;&nbsp;回</div>
                      	</ww:if>
                      	<ww:else>
                      		<div class="sbtn fl" onclick="sub();">保&nbsp;&nbsp;存</div>
                         	<div class="qzbtn fl" onclick="cancel();">取&nbsp;&nbsp;消</div>
                      	</ww:else>
                      </div>
                  </td>
             </tr>
		  
			</table>
		</form>
	</div>
</body>
</html>