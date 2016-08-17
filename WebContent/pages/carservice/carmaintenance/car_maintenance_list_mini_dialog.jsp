<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%
String path = request.getContextPath();
%>
<form class="form-horizontal" name="sform" id="sform" method="post"	>
			<input type="hidden" name="carMaintenance.carId" value="<ww:property value="carMaintenance.carId" />">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						<td>
							编号
						</td>
						<td>保养车辆</td>
						<td>车型</td>
						<td>保期</td>
						<td>预计保养里程</td>
						<td>历史里程</td>
						<td>当前里程</td>
						<td>费别</td>
						<td>保养费用</td>
						<td>状态</td>
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
							<td align="left">
								<ww:property value="vehicleModel" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('maintenanceType',type)" />
							</td>
							<td align="right">
								<ww:property value="expectKm" />
							</td>
							<td align="right">
								<ww:property value="actualKm" />
							</td>
							<td align="right">
								<ww:property value="currentKm" />
							</td>
							<td align="center">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('maintenanceFeeType',feeType)" />
							</td>
							<td align="right">
								<ww:property value="fee" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('maintenanceStatus',status)" />
							</td>
							<td align="left">
								<ww:property value="remark" />
							</td>
							
							
						</tr>
					</ww:iterator>
					<tr >
						<td align="center" colspan="11">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
</form>