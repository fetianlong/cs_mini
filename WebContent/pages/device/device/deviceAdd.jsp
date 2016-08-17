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
<title>车机管理</title>
<%@ include file="/pages/common/common_head.jsp"%>


<script type="text/javascript">
	$(function(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == ""){
			url="<%=path%>/device/deviceAdd.action";
		}else{
			url="<%=path%>/device/deviceUpdate.action";	
		}
		$("#eform").attr('action',url);
		$('#eform').validate({
			errorClass : 'text-danger',
			rules: {
				"device.setNo":{
					required: true,
					maxlength:30,
					deviceSetNoSc:true
				},
				"device.setName": {
					required: true,
					maxlength:30,
					deviceSetNameSc:true
				},
				"device.setType":{
					required: true
				},
				"device.simId":{
					required: true,
					deviceSimIdSc:true,
					maxlength:30
				}
			},
			messages: {
				"device.setNo":{
					required: "请输入车机编号",
					maxlength:"最长为30个字符"
				},
				"device.setName": {
					required: "请输入车机厂家名称",
					maxlength:"最长为30个字符"
				},
				"device.setType":{
					required: "请选择车机型号"
				},
				"device.simId":{
					required: "请输入SIM卡号",
					maxlength:"最长为30个字符"
				},
				"device.firmwareVersion":{
					maxlength:"最长为30个字符"
				}
			}
			
		});
		
		val_check_SpecialChar("deviceSetNoSc,deviceSetNameSc,deviceSimIdSc");
		
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
		window.location.href="<%=path%>/device/deviceSearch.action";
	}
	function getForm(){
		return $("#eform");
	}
	
	function sub(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == ""){
			url="<%=path%>/device/deviceAdd.action";
		}else{
			url="<%=path%>/device/deviceUpdate.action";	
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
					window.location.href="<%=path%>/device/deviceSearch.action";
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
</script>

</head>
<body>
      <div class="tc">
		<form name="eform" id="eform" method="post" action="">
			<input type="hidden" name="device.id" id="device.id"
						value="<ww:property value="device.id" />">
		  <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		  	<tr>
                <td class="zuo"><span class="xx red">*</span><span>车机编号</span>:</td>
                <td class="you">
                  <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" name="device.setNo" id="device.setNo"  
                  value="<ww:property value="device.setNo" />" />
                </td>  
                <td class="zuo1"><span class="xx red">*</span><span>厂家名称</span>:</td>
                  <td class="you1">
                  	<div class="btt1 fl">
	                      <select name="device.setName" id="device.setName" style="top:12px;height:26px;">
								<option value="">请选择</option>
								<ww:iterator value="getCompanys('7')" id="data" status="rl">
									<option value="<ww:property value="id" />"  <ww:if test="device.setName==id">selected=true</ww:if> ><ww:property value="name" /></option>	
								</ww:iterator>
							</select>
						</div>
                  </td>   
             </tr>
		  	 <tr>
                <td class="zuo"><span class="xx red">*</span><span>车机型号</span>:</td>
                <td class="you">
                  <div class="btt1 fl">
                      <select name="device.setType" id="device.setType" style="top:12px;height:26px;">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('deviceSetType',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  
								<ww:if test="device.setType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td>  
                <td class="zuo1"><span class="xx red">*</span><span>SIM卡号</span>:</td>
                  <td class="you1">
                    <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" name="device.simId" id="device.simId"
                    value="<ww:property value="device.simId" />" />
                  </td>   
             </tr>
		  	 <tr>
                <td class="zuo"><span>当前版本</span>:</td>
                <td class="you">
                  <input class="input_size fl" type="text" style="top: 0;left: 0px;position:relative;" maxlength="30" name="device.firmwareVersion" id="device.firmwareVersion"  
                  value="<ww:property value="device.firmwareVersion" />" />
                </td>  
                <td class="zuo1"><span>生产日期</span>:</td>
                  <td class="you1">
                    <input name="device.productDate" value="<ww:property value="transDate10String(device.productDate)"/>"
		    			type="text" class="input_size fl timeselect"  id="productDate" />
                  </td>   
             </tr>
             
             <tr>
                 <td class="zuo1"><span>是否刷过固件</span>:</td>
                   <td class="you1">
                   	<div class="btt1 fl">
                      <select name="device.updateFirmware" style="top:12px;height:26px;">
				           <option value="1" <ww:if test="device.updateFirmware==1">selected="selected"</ww:if>>是</option>
				           <option value="0"  <ww:if test="device.updateFirmware==0">selected="selected"</ww:if>>否</option>
				        </select>
					</div>
                   </td>
                    <td class="zuo1"><span>首次启用日期</span>:</td>
	                  <td class="you1">
	                    <input name="device.onlineDate" value="<ww:property value="transDate10String(device.onlineDate)"/>"
			    			type="text" class="input_size fl timeselect"  id="onlineDate"  />
	                  </td>   
                      
              </tr>
              <tr>
              	<td class="zuo1"><span>备注</span>:</td>
                  <td class="you1" colspan="3">
                  	<textarea name="device.remark" style="width: 91%" class="textarea_size"><ww:property value="device.remark"/></textarea>
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