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
$().ready(function (){
	
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"subscriberCard.cardId": {
				required: true,
				rangelength:[8,30]
			},
			"subscriberCard.type":{
				required: true
			}
		},
		messages: {
			"subscriberCard.cardId": {
				required: "请输入会员卡号！",
				rangelength: "请输入正确的会员卡号"
			},
			"subscriberCard.type":{
				required: "请选择会员卡类型！"
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
	<form name="eform" id="eform" method="post" action="<%=path %>/subscriber/cardUpdate.action">
		<input id="type" type="hidden" name="type" value="<ww:property value="type"/>">
		
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		   <tr>
		    <td class="zuo"><span class="xx red">*</span><span>RFID</span>:</td>
		    <td class="you"> 
		    <ww:if test="'update'.equals(type)">
		    	<input type="text" disabled="disabled" class="input_size fl" style="top: 0;left: 0px"   value="<ww:property value="subscriberCard.cardId" />"  maxlength="30"  class="input_size fl" style="top: 0;left: 0px;position:relative;" />
		    	<input type="text" style="visibility:hidden;width:10px;"  name="subscriberCard.cardId" value="<ww:property value="subscriberCard.cardId" />"/>
		    </ww:if>
		    <ww:else>
		    	<input type="text" name="subscriberCard.cardId"  class="input_size fl" style="top: 0;left: 0px"  id="subscriberCard.cardId" value="<ww:property value="subscriberCard.cardId" />"  maxlength="30"  class="input_size fl" style="top: 0;left: 0px;position:relative;" 	/>
		    </ww:else>
		    
		    	
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red">*</span><span>厂商</span>:
		    </td>
		    <td class="you">
		    <select class="input_size f1" style="top: 0;left: 0px" id="subscriberCard.type" name="subscriberCard.type" style="width:150px;">
		    <option value="">请选择</option>
		    	<ww:iterator value="@com.dearho.cs.sys.util.DictUtil@getDictsByGroupCode('15')" id="data" status="rl">
						<option value="<ww:property value="code" />"   <ww:if test="code.toString().equals(subscriberCard.type.toString())">selected</ww:if> ><ww:property value="cnName" /></option>	
				</ww:iterator>
			</select>
		    </td>   
		  </tr> 
		    <tr>
		    <td class="zuo"><span>识别码</span>:
		    </td>
		     <td class="you">
		      <input class="input_size f1" id="subscriberCard.code" name="subscriberCard.code" value="<ww:property value="subscriberCard.code" />" >
		    </td>   
		   </tr>
		   
		   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCard@STATE_UNBIND.toString().equals(subscriberCard.state.toString())">
			   <tr>
			    <td class="zuo"><span>状态</span>:
			    </td>
			     <td class="you">
				     <select class="input_size" id="subscriberCard.state" name="subscriberCard.state" style="width:150px;">
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCard@STATE_NORMAL"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCard@STATE_NORMAL.toString().equals(subscriberCard.state.toString())">selected</ww:if> >待绑定</option>
							<option value='<ww:property value="@com.dearho.cs.subscriber.pojo.SubscriberCard@STATE_UNBIND"/>'   <ww:if test="@com.dearho.cs.subscriber.pojo.SubscriberCard@STATE_UNBIND.toString().equals(subscriberCard.state.toString())">selected</ww:if> >已解绑</option>	
					</select>
			    </td>   
			   </tr>
			</ww:if>
			<ww:else>
				<input  type="hidden" name="subscriberCard.state" value="<ww:property value="subscriberCard.state"/>">
			</ww:else>
 
		</table>
	</form>
	</div> 
	
</body>


</html>