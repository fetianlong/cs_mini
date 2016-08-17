<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Insert title here</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	


	
	

</script>
</head>
<body  class="xtczkPage">
	
	<div class="tc">
	<form name="eform" id="eform" method="post"  >
		
	
		<table class="xxgl" border="0"  cellpadding="0" cellspacing="0">
		   <tr>
		    <td class="zuo"><span class="xx red"></span><span>手机号</span>:</td>
		    <td class="you"> 
		    	<input type="text"   class="input_size fl"  value="<ww:property value="subscriber.phoneNo" />"  maxlength="30"  	/>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red"></span><span>姓名</span>:</td>
		    <td class="you"> 
		    	<input type="text"  class="input_size fl"  value="<ww:property value="subscriber.name" />"   	/>
		    </td>   
		  </tr> 
		  <tr>
		    <td class="zuo"><span class="xx red"></span><span>交易类型</span>:
		    </td>
		    <td class="you">
		   		   <input type="text"   class="input_size fl"  value="<ww:property value="@com.dearho.cs.account.pojo.Account@getTradeType(accountTradeRecord.type)"/>"    	/>
		    </td>   
		  </tr> 
		  
		    <tr>
		    <td class="zuo"><span class="xx red"></span><span>交易金额</span>:
		    </td>
		     <td class="you">
		      <input class="input_size f1 startTime"  value="<ww:property  value="formatAmount(accountTradeRecord.amount)"/>" >
		    </td>   
		   </tr>
		   <tr>
		    <td class="zuo"><span class="xx red"></span><span>交易时间</span>:
		    </td>
		     <td class="you">
		      <input class="input_size f1 endTime" value="<ww:property value="transDateString(accountTradeRecord.tradeTime)"/>" >
		    </td>   
		   </tr>
		   
	  	   <tr>
		    <td class="zuo"><span class="xx red"></span><span>支付渠道</span>:
		    </td>
		     <td class="you">
			      <input class="input_size f1 endTime" value="<ww:property value="@com.dearho.cs.account.pojo.Account@getPayChannel(accountTradeRecord.payChannel)"/>" >
		
		    </td>   
		   </tr>
		  <tr>
			    <td class="zuo"><span class="xx red"></span><span>支付方式</span>:
			    </td>
			     <td class="you ">
				   <input class="input_size f1" value="<ww:property value="@com.dearho.cs.account.pojo.Account@getPayType(accountTradeRecord.payType)"/>" >
		
					
			    </td>   
		   </tr>
		   
		   <tr>
		    <td class="zuo"><span class="xx red"></span><span>订单号</span>:
		    </td>
		     <td class="you">
		    		 <input class="input_size f1" value="<ww:if test="@com.dearho.cs.account.pojo.Account@TYPE_ORDER==accountTradeRecord.type"><ww:property value="accountTradeRecord.bizId"/></ww:if><ww:else><ww:property value="accountTradeRecord.tradeOrderNo"/></ww:else>"/>
		    </td>   
		   </tr>
		    <tr>
		    <td class="zuo"><span class="xx red"></span><span>流水号</span>:
		    </td>
		     <td class="you">
		      <input class="input_size f1" id="rechargeCard.sortField" name="rechargeCard.sortField" value="<ww:property value="accountTradeRecordDetail.tradeNo" />" >
		    </td>   
		   </tr>
		   <tr>
		    <td class="zuo"><span class="xx red"></span><span>描述</span>:
		    </td>
		     <td class="you">
		      <input class="input_size f1"  value="<ww:property value="accountTradeRecord.description" />" >
		    </td>   
		   </tr>
		   
		  
		</table>
	</form>
	</div> 
	
</body>
</html>