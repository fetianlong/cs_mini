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

<title>车辆保养预警</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
function searchEntity(){
	$("#sform").submit();
}
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
		<form name="sform" class="form-horizontal" id="sform" method="post" action="<%=path%>/carservice/carMaintenanceWarning.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
			<div class="ControlBlock">
				<div class="row SelectBlock">
				    <div class="col-xs-4">
				    	 <div class="form-group">
							<label for="carMaintenance.plateNumber" class="col-xs-4 control-label">车辆牌号：</label>
							<div class="col-xs-8">
					    		  <input class="form-control" name="carMaintenance.plateNumber" id="carMaintenance.plateNumber" type="text" value="<ww:property value="carMaintenance.plateNumber"/>">
					    	</div>
					    </div>
						
				    </div>
			   </div>
			   <div class="row SubmitButtonBlock">
					<div class="col-sm-2 col-sm-offset-3 col-xs-4"><a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
	  			</div>
	  		</div>
			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						<td>保养车辆</td>
						<td>车型</td>
						<td>保期</td>
						<td>保养时里程</td>
						<td>保养日期</td>
						<td>备注</td>
					</tr>
			
					<ww:iterator value="page.results" id="data" status="rl">
						<tr  <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							<td align="left">
								<a href="javascript:showCarDetailForDialog('<ww:property value="carId" />','<%=path%>')"><ww:property value="plateNumber" /></a>
							</td>
							<td align="left">
								<ww:property value="vehicleModel" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('maintenanceType',type)" />
							</td>
							<td align="right">
								<ww:property value="actualKm" />
							</td>
							<td align="left">
								<ww:property value="transDate10String(maintenanceTime)" />
							</td>
							<td align="left">
								<ww:property value="remark" />
							</td>
							
						</tr>
					</ww:iterator>
					<tr style="background-color: #fff;height: 30px;">
						<td align="center" colspan="13">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>