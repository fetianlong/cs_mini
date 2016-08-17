<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%
String path = request.getContextPath();
%>

<form class="form-horizontal" name="sform" id="sform" method="post"	>
<input type="hidden" name="carAccident.memberId" value="<ww:property value="carAccident.memberId" />">
<input type="hidden" name="carAccident.carId" value="<ww:property value="carAccident.carId" />">
<input type="hidden" name="carAccident.orderId" value="<ww:property value="carAccident.orderId" />">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						
						<td>编号</td>
						<td>事故订单</td>
						<td>事故会员</td>
						<td>事故车辆</td>
						<td>事故类型</td>
						<td>事故经过</td>
						<td>发生时间</td>
						<td>通知客服</td>
						<td>受理状态</td>
						<td>处理状态</td>
						<td>备注</td>
						
					</tr>
			
					<ww:iterator value="page.results" id="data" status="rl">
						<tr
							
							 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							
							<td align="left">
								<ww:property value="code" />
							</td>
							<td align="left">
								<ww:property value="orderCode" />
							</td>
							<td align="left">			
								<ww:property value="memberName" />
							</td>
							<td align="left">
								<ww:property value="plateNumber" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('accidentType',accidentType)" />
							</td>
							<td align="left">
								<ww:property value="carLose" />
							</td>
							<td align="left">
								<ww:property value="happenTime" />
							</td>
							
							<td align="center">
								<ww:if test="noticeCs==1">
									是
								</ww:if>
								<ww:else>否</ww:else>
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('acceptFlag',acceptFlag)" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('handleStatus',handleStatus)" />
							</td>
							<td align="left">
								<ww:property value="remark" />
							</td>

						</tr>
					</ww:iterator>
					<tr>
						<td align="center" colspan="12">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
				
</form>
		