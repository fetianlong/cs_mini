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


</head>
<body class="SubPage">
<div class="container-fluid">
	<form name="sform" class="form-horizontal"  id="sform" method="post" action="">
		<input type="hidden" name="page.orderFlag" id="page.orderFlag"
				value="<ww:property value="page.orderFlag"/>">
		<input type="hidden" name="page.orderString" id="page.orderString"
				value="<ww:property value="page.orderString"/>">
		<div class="row TableBlock">
			<table class="table table-striped table-bordered table-condensed">
				<tr class="ths" id="tab_bg_cl">
					<td  width="100">
						<a href="javascript:SetOrder('ts')">时间<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
					</td>
					<td  width="150">
						经度
					</td>
					<td   width="150">
						纬度
					</td>
					<td>
						备注
					</td>
				</tr>
		
				<ww:iterator value="page.results" id="data" status="rl">
					<tr
						
						 <ww:if test="#rl.even">class="trs"</ww:if> style="font-size:12px;">
						<td align="left">
							<ww:property value="ts" />
						</td>
						<td align="right">
							<ww:property value="lng" />
						</td>
						<td align="right">
							<ww:property value="lat" />
						</td>
						<td></td>
					</tr>
				</ww:iterator>
				<tr style="background-color: #fff;height: 30px;">
					<td align="center" colspan="4">
						<ww:property value="page.pageSplit" />	
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
</body>
</html>