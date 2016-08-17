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
<title>车辆巡检管理</title>
<%@ include file="/pages/common/common_head.jsp"%>

</head>
<body class="SubPage">
	<div class="container-fluid">
		<form name="sform" class="form-horizontal" id="sform" method="post" action="<%=path%>/carservice/carPatrolSearch.action">
			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						<td>编号</td>
						<td>车牌号</td>
						<td>外漆是否完好</td>
						<td>玻璃是否完好</td>
						<td>胎压</td>
						<td>网点</td>
						<td>网点巡检单号</td>
						<td>备注</td>
					</tr>
			
					<ww:iterator value="page.results" id="data" status="rl">
						<tr  <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							<td align="left">
								<ww:property value="code" />
							</td>
							<td align="left">
								<ww:property value="plateNumber" />
							</td>
							<td align="center">
								<ww:if test="paint==1">
									是
								</ww:if>
								<ww:else>否</ww:else>
							</td>
							<td align="center">
								<ww:if test="glass==1">
									是
								</ww:if>
								<ww:else>否</ww:else>
							</td>
							<td align="left">
								<ww:property value="tirePressure" />
							</td>
							<td align="left">
								<ww:property value="dotName" />
							</td>
							<td align="left">
								<ww:property value="parkingPatrolCode" />
							</td>
							<td align="left">
								<ww:property value="remark" />
							</td>
							
						</tr>
					</ww:iterator>
					<tr style="background-color: #fff;height: 30px;">
						<td align="center" colspan="8">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>