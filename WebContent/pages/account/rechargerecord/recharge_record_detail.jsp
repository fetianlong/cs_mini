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
	<form name="eform" id="eform" method="post" <ww:if test="rechargeCard.id!=null">action="<%=path %>/account/rechargeCardUpdate.action"</ww:if><ww:else>action="<%=path %>/account/rechargeCardAdd.action"</ww:else> >
		
		<input  type="hidden" name="rechargeCard.id" value="<ww:property value="rechargeCard.id"/>">
		
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		   <tr>
		    <td class="zuo"><span class="xx red"></span><span>会员名称</span>:</td>
		    <td class="you"> 
		    	<input type="text"   class="input_size fl"  value="<ww:property value="rechargeRecord.subscriberName" />"   disabled="disabled" 	/>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red"></span><span>手机号</span>:</td>
		    <td class="you"> 
		    	<input type="text"   class="input_size fl" value="<ww:property value="rechargeRecord.subscriberPhoneNo" />"  disabled="disabled" 	/>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red"></span><span>充值金额</span>:
		    </td>
		    <td class="you">
		   		   <input type="text"  class="input_size fl" value="<ww:property value="rechargeRecord.rechargeAmount" />"     disabled="disabled" 	/>
		    </td>   
		  </tr> 
		  
		    <tr>
		    <td class="zuo"><span class="xx red"></span><span>支付方式</span>:
		    </td>
		    <td class="you">
		   		    <select class="form-control input_size" name="payType" id="payType" disabled="disabled">
		   		    		<option></option>
							<option value="pos"  <ww:if test='rechargeRecord.payType=="pos"'>selected="selected"</ww:if> >pos机</option>
							<option value="cash" <ww:if test='rechargeRecord.payType=="cash"'>selected="selected"</ww:if> >现金</option>
							<option value="other" <ww:if test='rechargeRecord.payType=="other"'>selected="selected"</ww:if> >其它</option>
					  </select>
		    </td>   
		  </tr> 
		  
		    <tr>
		    <td class="zuo"><span class="xx red"></span><span>备注</span>:
		    </td>
		    <td class="you">
		   		   <textarea rows=""  disabled="disabled"  cols=""><ww:property value="rechargeRecord.remark" /></textarea>
		    </td>   
		  </tr> 
		   
		 
		
		   
		
		   
		
			   <tr>
			    <td class="zuo"><span class="xx red">&nbsp;</span><span>操作人</span>:
			    </td>
			     <td class="you">
			      <input class="input_size f1" disabled="disabled"  value="<ww:property value="rechargeRecord.operatorName" />" >
			    </td>   
			   </tr>
			   <tr>
			    <td class="zuo"><span class="xx red">&nbsp;</span><span>操作时间</span>:
			    </td>
			     <td class="you">
			      <input class="input_size f1" disabled="disabled" value="<ww:property value="transDateString(rechargeRecord.createTime)" />" >
			    </td>   
			   </tr>
 		
		</table>
	</form>
	</div> 
	
</body>


</html>