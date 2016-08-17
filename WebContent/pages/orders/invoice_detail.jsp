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
	if($('#state').val()=="2"){
		$('#refuseReasonTr').show();
	}else{
		$('#refuseReasonTr').hide();
	}
	
	/*时间选择*/
	$("#eform .startTime").datetimepicker({
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

</script>
</head>

<body  class="xtczkPage">
	<div class="tc">
	 <form name="eform" id="eform" method="post" action="<%=path %>/orders/updateOrdersBill.action">
	 	<input type="hidden" id="id" name="id" value="<ww:property value="ordersBill.id"/>" />
	 	<input type="hidden" id="state" value="<ww:property value="ordersBill.state"/>" />
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		   <tr>
		    <td class="zuo"><span>快递公司名称</span>:</td>
		    <td class="you"> 
		    	<input type="text" name="ordersBill.expresName"  class="input_size fl" id="ordersBill.expresName" value="<ww:property value="ordersBill.expresName" />" />
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span>快递单号</span>:</td>
		    <td class="you"> 
		    	<input type="text" name="ordersBill.expresNo"  class="input_size fl" id="ordersBill.expresNo" value="<ww:property value="ordersBill.expresNo" />" />
		    </td>   
		  </tr> 
		   <tr>
			<td class="zuo"><span>发票编号</span>:</td>
			    <td class="you">
			       <input class="input_size f1"  value="<ww:property value="ordersBill.billNo" />" >
			    </td>   
		   </tr>
		  <tr>
		    <td class="zuo"><span>寄件时间</span>:
		    </td>
		     <td class="you">
		      <input class="input_size f1 startTime" id="ordersBill.sendDate" name="ordersBill.sendDate" value="<ww:property value="transDate12String(ordersBill.sendDate)" />" >
		    </td>   
		  </tr>
		  
		   <tr>
		    <td class="zuo"><span>处理状态</span>:
		    </td>
		     <td class="you">
			     <select class="input_size" id="state" name="ordersBill.state" >
			     		<option value='0'  <ww:if test='ordersBill.state=="0"'>selected</ww:if> >发票未开</option>	
						<option value='1'  <ww:if test='ordersBill.state=="1"'>selected</ww:if> >审核未通过</option>
						<option value='2'  <ww:if test='ordersBill.state=="2"'>selected</ww:if> >审核通过</option>
						<option value='3'  <ww:if test='ordersBill.state=="3"'>selected</ww:if> >发票已开</option>
				</select>
		    </td>   
		   </tr>
		   
		   <tr>
			    <td class="zuo"><span>收件人</span>:
			    </td>
			     <td class="you">
			      <input class="input_size f1" disabled="disabled"  value="<ww:property value="ordersBill.recipients" />" >
			    </td>   
		   </tr>
		    <tr>
			    <td class="zuo"><span>收件人手机号</span>:
			    </td>
			     <td class="you">
			      <input class="input_size f1" disabled="disabled"  value="<ww:property value="ordersBill.telphone" />" >
			    </td>   
		   </tr>
		   <tr>
			    <td class="zuo"><span>邮编</span>:
			    </td>
			     <td class="you">
			      <input class="input_size f1" disabled="disabled"  value="<ww:property value="ordersBill.postcode" />" >
			    </td>   
		   </tr>
		   
		    <tr>
			    <td class="zuo"><span>发票抬头</span>:
			    </td>
			     <td class="you">
			      <textarea disabled="disabled"><ww:property value="ordersBill.title" /></textarea>
			    </td>   
		   </tr>
		   <tr>
			    <td class="zuo"><span>发票邮寄地址</span>:
			    </td>
			     <td class="you">
			       <textarea disabled="disabled"><ww:property value="ordersBill.address" /></textarea>
			    </td>   
		   </tr>
		  
		   <tr>
			    <td class="zuo"><span>备注</span>:
			    </td>
			     <td class="you">
			       <textarea name="ordersBill.refuseReason"><ww:property value="ordersBill.refuseReason" /></textarea>
			    </td>   
		   </tr>
		</table>
	 </form>
	</div> 
</body>
</html>