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

<title>车辆保养</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	$(function(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == "" || id == "undefined"){
			url="<%=path%>/carservice/carMaintenanceAdd.action";
		}else{
			url="<%=path%>/carservice/carMaintenanceUpdate.action";	
		}
		$("#eform").attr('action',url);
		$('#eform').validate({
			errorClass : 'text-danger',
			rules: {
				"vehiclePlateId":{
					required: true
				},
				"carMaintenance.type":{
					required: true
				},
				"carMaintenance.actualKm":{
					number:true
				},
				"carMaintenance.fee":{
					number:true
				}
			},
			messages: {
				"vehiclePlateId":{
					required: "请选择保养车辆！"
				},
				"carMaintenance.type":{
					required: "请选择保期！"
				},
				"carMaintenance.actualKm":{
					number:"请输入数字！"
				},
				"carMaintenance.fee":{
					number:"请输入数字！"
				}
			}
			
		});
		
		
		$('.timeselect').datetimepicker({
			language: 'zh-CN',
			todayHighlight: 'true',
			todayBtn: 'linked',
			minView: 2,
			autoclose: true,
			format:'yyyy-mm-dd'
		});
		$('.timeselect').attr('readonly','readonly');
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
		window.location.href="<%=path%>/carservice/carMaintenanceSearch.action";
	}
	function getForm(){
		return $("#eform");
	}
	
	function sub(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == "" || id == "undefined"){
			url="<%=path%>/carservice/carMaintenanceAdd.action";
		}else{
			url="<%=path%>/carservice/carMaintenanceUpdate.action";	
		}
		var re=isValid();
		if(re){
			$.post(url,$("#eform").serialize(),r_saveCar,'json').error(requestError);
		}
	}
	function r_saveCar(data){
		switch(data.result){
			case 0:
				alertok(data.msg, function(){
					window.location.href="<%=path%>/carservice/carMaintenanceSearch.action";
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
	$.dialog({
		id:'searchCarDia',
	    title:'车辆查询',
		content : 'url:<%=path%>/car/carSearch.action?state=page&query=accident',
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
function showNoticeTime(noticeFlag){
	if(noticeFlag == '0'){
		$('#noticeTimeTrId').hide();
	}
	else if(noticeFlag == '1'){
		$('#noticeTimeTrId').show();
	}
}
</script>

</head>
<body>
      <div class="tc">
		<form name="eform" id="eform" method="post" action="">
			<input type="hidden" name="carMaintenance.id" id="carMaintenance.id"
						value="<ww:property value="carMaintenance.id" />">
			<input type="hidden" name="carMaintenance.isDiscard" 
						value="<ww:property value="carMaintenance.isDiscard" />">
			<input type="hidden" name="carMaintenance.code" 
						value="<ww:property value="carMaintenance.code" />">
		  <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		  	 <tr>
                <td class="zuo"><span class="xx red">*</span><span>保养车辆</span>:</td>
                <td class="you">
                  <input type="hidden" name="carMaintenance.carId" id="carId" value="<ww:property value="carMaintenance.carId" />"/>
                  
                  <input name="carMaintenance.plateNumber"  id="vehiclePlateId" type="text" readonly 
                  	class="input_size fl" 
                  		value="<ww:property value="carMaintenance.plateNumber" />" />
						<input onclick="selectCar();" type="button" value="选择" class="searchinputbut" />
                </td>  
                <td class="zuo1"><span>车型</span>:</td>
                <td class="you1">
                  <input class="input_size fl" type="text" readonly name="carMaintenance.vehicleModel" id="modelId"  
                  value="<ww:property value="carMaintenance.vehicleModel" />" />
                </td>  
             </tr>
             <tr>
                  <td class="zuo1"><span class="xx red">*</span><span>保期</span>:</td>
                  <td class="you1">
                    <div class="btt1 fl">
                      <select name="carMaintenance.type" id="carMaintenance.type" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('maintenanceType',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carMaintenance.type==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                  </td>   

                   <td class="zuo1"><span>保养日期</span>:</td>
	               <td class="you1">
	                 <input name="carMaintenance.maintenanceTime" value="<ww:property value="transDate10String(carMaintenance.maintenanceTime)"/>"
			    		type="text" class="input_size fl timeselect" id="onlineDate"  />
	               </td>   
              </tr>
              <tr>
              	<td class="zuo"><span>保养地点</span>:</td>
                <td class="you">
                    <input class="input_size fl" type="text"  maxlength="100" name="carMaintenance.maintenancePlace" id="carMaintenance.maintenancePlace"  
                    	value="<ww:property value="carMaintenance.maintenancePlace" />" />
                </td>  
                <td class="zuo1"><span>保养时里程(KM)</span>:</td>
                <td class="you1">
                    <input class="input_size fl" type="text"  maxlength="10" name="carMaintenance.actualKm" id="carMaintenance.actualKm"  
                    	value="<ww:property value="carMaintenance.actualKm" />" />
                </td>   
             </tr>
             <tr>
                <td class="zuo"><span>保养费用</span>:</td>
                <td class="you">
                	<input class="input_size fl" type="text"  maxlength="10" name="carMaintenance.fee" id="carMaintenance.fee"  
                		value="<ww:property value="carMaintenance.fee" />" />元
                </td>  
                <td class="zuo1"><span>办理人</span>:</td>
                <td class="you1">
                	<input class="input_size fl" type="text"  maxlength="30" name="carMaintenance.operator" id="carMaintenance.operator"  
                		value="<ww:property value="carMaintenance.operator" />" />
                </td>  
             </tr>
             <tr>
             	<td class="zuo1"><span>状态</span>:</td>
                  <td class="you1">
                    <div class="btt1 fl">
                      <select name="carMaintenance.status" id="carMaintenance.status">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('maintenanceStatus',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="carMaintenance.status==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                  </td>  
             </tr>
              <tr>
              	<td class="zuo1"><span>备注</span>:</td>
                  <td class="you1" colspan="3">
                  	<textarea name="carMaintenance.remark" style="width: 91%" class="textarea_size"><ww:property value="carMaintenance.remark"/></textarea>
                  </td>  
                 
              </tr>
              <tr></tr>
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