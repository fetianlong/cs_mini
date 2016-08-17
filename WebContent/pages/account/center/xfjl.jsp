<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消费记录</title>

<script type="text/javascript" src="<%=path%>/common/js/jquery-1.7.min.js"></script>

<script type="text/javascript" src="<%=path%>/common/js/Calendar/WdatePicker.js"></script>

<script type="text/javascript">
$(function(){ 
   
}); 

function dateChange(){

	searchRecord($("#fromDateTmp").val(),$("#toDateTmp").val());
}

function searchRecord(start,end){
	$("#fromDate").val(start);
	$("#toDate").val(end);
	$("#search").submit();
}
</script>
</head>
<body>
<form id="search" action="<%=path%>/account/queryTradingRecord.action"  method="post">
<input id="fromDate"  name="fromDate"  type="hidden">
<input id="toDate"  name="toDate"  type="hidden">

从：
<input id="fromDateTmp" value='<ww:property value="transDate10String(fromDate)"/>' onchange="dateChange()" class="Wdate" type="text" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/>
 
			    			
至：
<input id="toDateTmp"  value='<ww:property value="transDate10String(toDate)"/>' onchange="dateChange()" class="Wdate" type="text" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/>
 
			    			
			    			
</form>			    			


<a id="J-today" href="javascript:searchRecord('<ww:property  value="transDate10String(#request.today)"/>','<ww:property  value="transDate10String(#request.today)"/>')" >今天</a>
<a id="J-one-month" href="javascript:searchRecord('<ww:property  value="transDate10String(#request.lateMonth)"/>','<ww:property  value="transDate10String(#request.today)"/>')" >最近1个月</a>
<a id="J-three-month" href="javascript:searchRecord('<ww:property  value="transDate10String(#request.lastThreeMonth)"/>','<ww:property  value="transDate10String(#request.today)"/>')" >最近3个月</a>
<a id="J-six-year" href="javascript:searchRecord('<ww:property  value="transDate10String(#request.lastSixMonth)"/>','<ww:property  value="transDate10String(#request.today)"/>')">最近6个月</a>

<table>
<tr>
<td>序号</td>
<td>交易类型</td>
<td>交易金额</td>
<td>交易时间</td>
<td>操作</td>
</tr>

	<ww:iterator value="page.mResults"   status="st">
		<tr>
			<td><ww:property  value="#st.count"/>  </td>
			<td>
				<ww:if test="type=="></ww:if>
				<ww:elseif test=""></ww:elseif>
				<ww:elseif test=""></ww:elseif>
			</td>
			
			<td><ww:property  value="amount"/>  </td>
			<td><ww:property value="transDateString(tradeTime)"/></td>
			
			
			<td><a >详情</a></td>
		</tr>
	</ww:iterator>
</table>
<ww:property value="page.pageSplit" />	

</body>
</html>