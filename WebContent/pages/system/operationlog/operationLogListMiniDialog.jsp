<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%
String path = request.getContextPath();
%>
<form class="form-horizontal" name="sform" id="sform" method="post"	>
			<input type="hidden" name="operateLog.carId" value="<ww:property value="operateLog.carId" />">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						<td>操作名称</td>
						<td>操作模块</td>
						<td>操作类型</td>
						<td>操作人</td>
						<td>数据状态</td>
						<td>操作时间</td>
					</tr>
			
					<ww:iterator value="page.results" id="data" status="rl">
						<tr  <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							
							<td align="left">
								<ww:property value="name" />
							</td>
							<td align="left">
								<ww:property value="modular" />
							</td>
							<td align="left">
								<ww:property value="type" />
							</td>
							<td align="left">
								<ww:property value="user" />
							</td>
							<td align="right">
								<ww:property value="dataState" />
							</td>
							<td align="right">
								<ww:property value="transDateString(ts)" />
							</td>
						</tr>
					</ww:iterator>
					<tr >
						<td align="center" colspan="6">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
</form>