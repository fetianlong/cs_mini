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
<body >
	<div class="table_con tanchuang" >
	
			<!-- 添加-->
		<form   name="eform" id="eform" method="post" action="" >	
		
			<table class="t1" >
				<tr class="trr" >
					<th >
						<span >手机号：</span>
					</th>
					<td>
						<input type="text"    value='<ww:property value="smsRecord.phoneNo"/>'  class="input_size"    />
		    		</td> 
				</tr>
				 <tr class="trr">
		   		     <th ><span >类型：</span></th>
					<td><input type="text"   value='<ww:property value="@com.dearho.cs.sys.pojo.SMSRecord@getType(smsRecord.type)"/>'  class="input_size"  /></td> 
				</tr>
				<tr class="trr">
		   		     <th ><span >发送人：</span></th>
					<td><input type="text"  value='<ww:property value="smsRecord.userName"/>'  class="input_size"  /></td> 
				</tr>
				<tr class="trr">
		   		     <th ><span >发送日期：</span></th>
					<td><input type="text"  value='<ww:property value="transDateString(smsRecord.ts)"/>'  class="input_size"  /></td> 
				</tr>
				<tr class="trr">
		   		     <th ><span >发送内容：</span></th>
					<td><textarea rows="5" cols="50"><ww:property value="smsRecord.content"/></textarea></td> 
				</tr>
				<tr class="trr">
		   		     <th ><span >返回：</span></th>
					<td><input type="text"  value='<ww:property value="smsRecord.result"/>'  class="input_size"  /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>