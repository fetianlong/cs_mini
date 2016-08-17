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

<title>Insert title here</title>
<base href="<%=basePath%>">
<%@ include file="/pages/common/common_head.jsp"%><script type="text/javascript">
$(function(){
	
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"rechargeCard.name": {
				required: true
			},
			"rechargeCard.showName": {
				required: true
			},
			"rechargeCard.amount":{
				required: true,
				range:[0,10000]
			},
			
			"rechargeCard.startValidTime":{
				required: true
			},
			"rechargeCard.endValidTime":{
				required: true
			},
			"rechargeCard.isValid":{
				required: true
			},
			"rechargeCard.channel":{
				required: true
			},
			"rechargeCard.sortField":{
				required: true
			}
			
		},
		messages: {
			"rechargeCard.name": {
				required: "请输入充值卡名称"
			},
			"rechargeCard.showName": {
				required: "请输入充值卡展示名称"
			},
			
			"rechargeCard.amount":{
				required: "请输入充值卡金额",
				range:"请输入0-10000之间的数字"
			},
			
			"rechargeCard.startValidTime":{
				required: "请输入有效起始时间"
			},
			"rechargeCard.endValidTime":{
				required: "请输入有效终止时间"
			},
			"rechargeCard.isValid":{
				required: "请选择状态"
			},
			"rechargeCard.channel":{
				required: "请选择渠道"
			},
			"rechargeCard.sortField":{
				required: "请输入排序字段"
			}
		}
	});
	
	
	/*时间选择*/
	$("#eform .startTime").datetimepicker({
		language: 'zh-CN',
		autoclose: true,
		format: "yyyy-mm-dd hh:ii"
	});
	
	/*时间选择*/
	$("#eform .endTime").datetimepicker({
		language: 'zh-CN',
		autoclose: true,
		format: "yyyy-mm-dd hh:ii"
	});

	
});
function getForm(){
	return $("#eform");
}
function isValid(){
	return $("#eform").valid();
}




function checkPrice(price){
	return (/^(([1-9]\d*)|\d)(\.\d{1,2})$/).test(price.toString()) || (/^(([1-9]\d*)|\d)$/).test(price.toString()); 
}

</script>
</head>

<body  class="xtczkPage">
	
	<div class="tc">
	<form name="eform" id="eform" method="post" <ww:if test="rechargeCard.id!=null">action="<%=path %>/account/rechargeCardUpdate.action"</ww:if><ww:else>action="<%=path %>/account/rechargeCardAdd.action"</ww:else> >
		
		<input  type="hidden" name="rechargeCard.id" value="<ww:property value="rechargeCard.id"/>">
		
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		   <tr>
		    <td class="zuo"><span class="xx red">*</span><span>名称</span>:</td>
		    <td class="you"> 
		    	<input type="text" name="rechargeCard.name"  class="input_size fl" id="rechargeCard.name" value="<ww:property value="rechargeCard.name" />"  maxlength="30"  	/>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red">*</span><span>展示名称</span>:</td>
		    <td class="you"> 
		    	<input type="text" name="rechargeCard.showName"  class="input_size fl" id="rechargeCard.showName" value="<ww:property value="rechargeCard.showName" />"  maxlength="30"  	/>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red">*</span><span>金额</span>:
		    </td>
		    <td class="you">
		   		   <input type="text" name="rechargeCard.amount" onchange="discountRateCompute()" class="input_size fl" id="amount" value="<ww:property value="formatAmount(rechargeCard.amount)" />"    	/>
		    </td>   
		  </tr> 
		  
		    <tr>
		    <td class="zuo"><span class="xx red">*</span><span>有效开始时间</span>:
		    </td>
		     <td class="you">
		      <input class="input_size f1 startTime" id="rechargeCard.startValidTime" name="rechargeCard.startValidTime" value="<ww:property value="transDate12String(rechargeCard.startValidTime)" />" >
		    </td>   
		   </tr>
		   <tr>
		    <td class="zuo"><span class="xx red">*</span><span>有效结束时间</span>:
		    </td>
		     <td class="you">
		      <input class="input_size f1 endTime" id="rechargeCard.endValidTime" name="rechargeCard.endValidTime" value="<ww:property value="transDate12String(rechargeCard.endValidTime)" />" >
		    </td>   
		   </tr>
		   
	  	   <tr>
		    <td class="zuo"><span class="xx red">*</span><span>状态</span>:
		    </td>
		     <td class="you">
			     <select class="input_size" id="rechargeCard.isValid" name="rechargeCard.isValid" >
			     		<option></option>
						<option value='1'   <ww:if test="1==rechargeCard.isValid">selected</ww:if> >有效</option>
						<option value='0'   <ww:if test="0==rechargeCard.isValid">selected</ww:if> >无效</option>	
				</select>
		    </td>   
		   </tr>
		  <tr>
			    <td class="zuo"><span class="xx red">*</span><span>使用渠道</span>:
			    </td>
			     <td class="you CheckBoxGroup">
				   
						<label class="checkbox-inline">
						  <input type="checkbox" name="rechargeCard.channel" value="W" <ww:if test='rechargeCard.channel !=null && rechargeCard.channel.contains("W")'>checked="checked"</ww:if>>&nbsp;微信
						</label>
						<label class="checkbox-inline">
						  <input type="checkbox" name="rechargeCard.channel" value="C" <ww:if test='rechargeCard.channel !=null && rechargeCard.channel.contains("C")'>checked="checked"</ww:if>>&nbsp;后台
						</label>
			    </td>   
		   </tr>
		   
		   <tr>
		    <td class="zuo"><span class="xx red">*</span><span>排序</span>:
		    </td>
		     <td class="you">
		      <input class="input_size f1" id="rechargeCard.sortField" name="rechargeCard.sortField" value="<ww:property value="rechargeCard.sortField" />" >
		    </td>   
		   </tr>
		   
		   <ww:if test="rechargeCard.creatorId!=null">
			   <tr>
			    <td class="zuo"><span class="xx red">&nbsp;</span><span>创建人</span>:
			    </td>
			     <td class="you">
			      <input class="input_size f1" disabled="disabled"  value="<ww:property value="rechargeCard.creatorName" />" >
			    </td>   
			   </tr>
			   <tr>
			    <td class="zuo"><span class="xx red">&nbsp;</span><span>创建时间</span>:
			    </td>
			     <td class="you">
			      <input class="input_size f1" disabled="disabled" value="<ww:property value="transDateString(rechargeCard.createTime)" />" >
			    </td>   
			   </tr>
 		 </ww:if>
		</table>
	</form>
	</div> 
	
</body>


</html>