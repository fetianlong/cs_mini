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
<%@ include file="/pages/common/common_head.jsp"%>
<script type="text/javascript">
$().ready(function (){
	
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"reason": {
				required: true
			}
			
		},
		messages: {
			"reason": {
				required: "请输入解绑原因！"
			}
		}
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

<body style="height:100%">
	
	<div class="tc">
	<form name="eform" id="eform" method="post" action="<%=path%>/subscriber/doCardLoss.action">
		<input id="type" type="hidden" name="subscriberCardBinding.id" value="<ww:property value="subscriberCardBinding.id"/>">
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		 <tr>
		    <td class="zuo"><span class="xx red">*</span><span>RFID</span>:</td>
		    <td class="you"><input type="text" disabled="disabled" value="<ww:property value="subscriberCardBinding.cardId" />"  maxlength="30"  class="input_size fl" style="top: 0;left: 0px;"    />
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red">*</span><span>绑定会员手机</span>:</td>
		    <td class="you"><input type="text" disabled="disabled"  value="<ww:property value="subscriberCardBinding.subscriber.phoneNo" />"  maxlength="30"  class="input_size fl" style="top: 0;left: 0px;"/>
		    </td>   
		  </tr> 
		   <tr>
		    <td class="zuo"><span class="xx red">*</span><span>绑定会员姓名</span>:</td>
		    <td class="you"><input type="text" disabled="disabled"  value="<ww:property value="subscriberCardBinding.subscriber.name" />"  maxlength="30"  class="input_size fl" style="top: 0;left: 0px;" />
		    </td>   
		  </tr> 
		   <tr>
		    <td class="zuo"><span class="xx red">*</span><span>解&nbsp;&nbsp;绑&nbsp;&nbsp;原&nbsp;&nbsp;因&nbsp;</span>:
		    </td>
		     <td class="you">
		      <textarea name="reason" id="reason" style="height:60px;overflow-y:hidden" class="textarea_size"  ></textarea>
		    </td>   
		   </tr>
 
		</table>
	</form>
	</div> 
	
</body>


</html>