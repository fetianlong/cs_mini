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
<title>基本计价策略详情</title>
<%@ include file="/pages/common/common_head.jsp"%>


<script type="text/javascript">
	$(function(){
		
		$('#eform').validate({
			errorClass : 'text-danger',
			rules: {
				"strategyBase.name":{
					required: true,
					nameRepeat:true
				},
				"strategyBase.type": {
					required: true,
				},
				"strategyBase.basePrice":{
					required: true,
					number:true
				},
				"strategyBase.kmPrice":{
					number:true
				},
				"strategyBase.timelyFeeUnit":{
					required: true
				},
				"strategyBase.timelyFeeLong":{
					required: true,
					number:true
				},
				"strategyBase.timeBeforeGet":{
					required: true,
					number:true
				},
				
				"strategyBase.minConsumption":{
					number:true
				},
				"strategyBase.maxConsumption":{
					number:true
				},
				
				"strategyBase.overtimePenalty":{
					number:true
				}
			},
			messages: {
				"strategyBase.name":{
					required: "请输入策略名称",
					strategyBaseNameSc:"不能有特殊字符"
				},
				"strategyBase.type": {
					required: "请选择策略类型",
				},
				"strategyBase.basePrice":{
					required: "请填写基础价格",
					number:"请输入数字"
				},
				"strategyBase.kmPrice":{
					number:"请输入数字"
				},
				"strategyBase.timelyFeeUnit":{
					required: "请选择基础计价单位"
				},
				"strategyBase.timelyFeeLong":{
					required: "请选择基础计价时长",
					number:"请输入数字"
				},
				"strategyBase.timeBeforeGet":{
					required: "请填写预留取车时间",
					number:"请输入数字"
				},
				
				"strategyBase.minConsumption":{
					number:"请输入数字"
				},
				"strategyBase.maxConsumption":{
					number:"请输入数字"
				},
				"strategyBase.overtimePenalty":{
					number:"请输入数字"
				}
			}
			
		});
		
		jQuery.validator.addMethod("nameRepeat", function(value, element) {
			var flag = true;
			 $.ajax({
		         url :'<%=path %>/feestrategy/strategyBase/checkName.action',
		         type:'post',
		         cache:false,
		         async:true,
		         data:{'strategyBase.name':value},
		         dataType:'json',
		         success:function(data) {
		        	 if(data.result == 0){
		        		 flag = false;
		        	 }
		        	 else {
		        		 flag = true;
		        	 }
		         }
			 });
			 return flag;
     	}, "已有策略名称，请更换");
		
		
		
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
		window.location.href="<%=path%>/feestrategy/strategyBase/searchStrategyBase.action";
	}
	
	function sub(){
		var id = '<ww:property value="id" />';
		var url="";
		if (id == ""){
			url="<%=path%>/feestrategy/strategyBase/addStrategyBase.action";
		}else{
			url="<%=path%>/feestrategy/strategyBase/updateStrategyBase.action";	
		}
		var re=isValid();
		if(re){
			showLoading();
			$.post(url,$("#eform").serialize(),r_saveCar,'json').error(requestError);
		}
	}
	function r_saveCar(data){
		hideLoading();
		switch(data.result){
			case 0:
				alertok(data.msg, function(){
					window.location.href="<%=path%>/feestrategy/strategyBase/searchStrategyBase.action";
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
	
function checkName(){
	$("#strategyBase.name").valid();
}
</script>

</head>
<body style="overflow-y:auto;">
      <div class="tc">
		<form name="eform" id="eform" method="post" action="">
			<input type="hidden" name="strategyBase.id" id="strategyBase.id"
						value="<ww:property value="strategyBase.id" />">
		  <table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		  	<tr>
		  		<td class="zuo"><span class="xx red">*</span><span>类型</span>:</td>
                <td class="you">
                  	<div class="btt1 fl">
	                     <select name="strategyBase.type" id="strategyBase.type" >
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('strategyBaseType',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  <ww:if test="strategyBase.type==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td>   
                <td class="zuo1"><span class="xx red">*</span><span>策略名称</span>:</td>
                <td class="you1">
                  <input class="input_size fl" type="text" maxlength="120" name="strategyBase.name" id="strategyBase.name" onblur="checkName();"
                  value="<ww:property value="strategyBase.name" />" />
                </td>  
                
             </tr>
		  	 <tr>
                <td class="zuo"><span class="xx red">*</span><span>基础价格(元)</span>:</td>
                <td class="you">
                   <input class="input_size fl" type="text" maxlength="10" name="strategyBase.basePrice" id="strategyBase.basePrice"
                    value="<ww:property value="strategyBase.basePrice" />" />
                </td>  
                <td class="zuo1"><span class="xx red"></span><span>里程价格(元/公里)</span>:</td>
                  <td class="you1">
                    <input class="input_size fl" type="text" maxlength="10" name="strategyBase.kmPrice" id="strategyBase.kmPrice"
                    value="<ww:property value="strategyBase.kmPrice" />" />
                  </td>   
             </tr>
		  	 <tr>
                <td class="zuo"><span class="xx red">*</span><span>基础计价单位</span>:</td>
                <td class="you">
                	<div class="btt1 fl">
	                     <select name="strategyBase.timelyFeeUnit" id="strategyBase.timelyFeeUnit" >
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('timeUnit',2)" id="data" status="rl">
								<option value="<ww:property value="id" />"  <ww:if test="strategyBase.timelyFeeUnit==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
                </td>  
                <td class="zuo1"><span class="xx red">*</span><span>基础计价时长</span>:</td>
                  <td class="you1">
                    <input name="strategyBase.timelyFeeLong" maxlength="10" value="<ww:property value="strategyBase.timelyFeeLong"/>"
		    			type="text" class="input_size fl"  id="strategyBase.timelyFeeLong" />
                  </td>
             </tr>
             
             <tr>
                 <td class="zuo"><span class="xx red">*</span><span>预留取车时间(分钟)</span>:</td>
                 <td class="you">
                   	<input name="strategyBase.timeBeforeGet" value="<ww:property value="strategyBase.timeBeforeGet"/>"
		    			type="text" class="input_size fl" maxlength="10"  id="strategyBase.timeBeforeGet" />
                 </td>
              </tr>
              <tr>
                 <td class="zuo"><span>最低消费</span>:</td>
                 <td class="you">
                   	<input name="strategyBase.minConsumption" value="<ww:property value="strategyBase.minConsumption"/>"
		    			type="text" class="input_size fl" maxlength="10"  id="strategyBase.minConsumption" />
                 </td>
                 <td class="zuo1"><span>最高消费</span>:</td>
                 <td class="you1">
                    <input name="strategyBase.maxConsumption" value="<ww:property value="strategyBase.maxConsumption"/>"
		    			type="text" class="input_size fl" maxlength="10" id="strategyBase.maxConsumption"  />
                 </td>   
              </tr>
              <tr>
                 <td class="zuo"><span>超时罚金</span>:</td>
                 <td class="you">
                   	<input name="strategyBase.overtimePenalty" value="<ww:property value="strategyBase.overtimePenalty"/>"
		    			type="text" class="input_size fl" maxlength="10" id="strategyBase.overtimePenalty" />
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