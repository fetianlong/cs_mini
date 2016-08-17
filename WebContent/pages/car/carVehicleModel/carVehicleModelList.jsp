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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>车型管理</title>

<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	//添加车型跳转页面
	function editEntity(id,state){
		window.location.href="<%=path%>/carVehicleModel/carVehicleModelGet.action?id="+id;
	}
	
	function searchEntity(){
		$("#sform").submit();
	}
	
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
		<form name="sform" class="form-horizontal" id="sform" method="post" action="<%=path%>/carVehicleModel/carVehicleModelSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
			
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-xs-4">
						 <div class="form-group">
						 	<label for="carVehicleModel.name" class="col-xs-4 control-label">车辆型号：</label>
						 	<div class="col-xs-8">
						 		<input class="form-control" id="carVehicleModel.name" type="text" name="carVehicleModel.name"  value='<ww:property value="carVehicleModel.name"/>'/>
						 	</div>
						 </div>
						  <div class="form-group"><label for="carVehicleModel.engineType" class="col-xs-4 control-label">动力类型：</label>
						  	<div class="col-xs-8">
							  <select class="form-control" name="carVehicleModel.engineType" id="carVehicleModel.engineType">
									<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('2',1)" id="data" status="rl">
										<option value="<ww:property value="id" />"  <ww:if test="carVehicleModel.engineType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
									</ww:iterator>
							  </select>
							</div>
						  </div>
					 </div>
					 <div class="col-xs-4">
						 <div class="form-group">
						 	<label for="carVehicleModel.brand" class="col-xs-4 control-label">车辆品牌：</label>
						 	<div class="col-xs-8">
						 		<select class="form-control" name="carVehicleModel.brand" id="carVehicleModel.brand">
									<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('10',1)" id="data" status="rl">
										<option value="<ww:property value="id" />"  <ww:if test="carVehicleModel.brand==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
									</ww:iterator>
								</select>
							</div>
						 </div>
					 </div>
					 <div class="col-xs-4">
						 <div class="form-group"><label for="carVehicleModel.level" class="col-xs-4 control-label">车辆等级：</label>
						 	<div class="col-xs-8">
							   <select class="form-control" name="carVehicleModel.level" id="carVehicleModel.level">
									<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('1',1)" id="data" status="rl">
										<option value="<ww:property value="id" />"  <ww:if test="carVehicleModel.level==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
									</ww:iterator>
								</select>
							</div>
						 </div>
					  </div>
				 </div>
				 <div class="row SubmitButtonBlock">
				 	<ww:if test="hasPrivilegeUrl('/car/carSearch.action')">
						<div class="col-sm-2 col-sm-offset-3 col-xs-4"><a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
					</ww:if>
					<ww:if test="hasPrivilegeUrl('/car/carAdd.action')">
						<div class="col-sm-2 col-xs-4"><a class="btn btn-block Button2" onclick="editEntity('','add');" target="_blank"><i class="fa fa-floppy-o"></i>添加</a></div>
					</ww:if>
  				 </div>
			</div>
			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						<td  height="28" nowrap>
							<input type="checkbox" name="checkdelcheckall" onclick="funCheck('','checkdel')" />
						</td>
						<td  >
							<a href="javascript:SetOrder('name')">车辆型号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td  >
							车辆品牌
						</td>
						<td  align="center">
							车辆等级
						</td>
						<td  >
							座位数
						</td>
						<td  >
							车辆厢数
						</td>
						<td>
							是否有娱乐系统
						</td>
						<td >
							标准续航里程
						</td>
						<td >
							即时租策略
						</td>
						<td >
							日租策略
						</td>
						<td  nowrap width=157>
							操作
						</td>
					</tr>
			
					<ww:iterator value="page.results" id="data" status="rl">
						<tr
							
							 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
							<td align="center">
								<input type="checkbox" name="checkdel" value="<ww:property value='id' />" />
							</td>
							<td align="left">
								<ww:property value="name" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('10',brand)" />
							</td>
							<td align="center">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('1',level)" />
							</td>
							
							<td align="left">
								<ww:property value="seatingNum" />座
							</td>
							
							<td align="left">
								<ww:property value="casesNum" />厢
							</td>
							
							<td align="center">
								<ww:if test="entertainmentSystem==1">
									是
								</ww:if>
								<ww:else>否</ww:else>
							</td>
							<td align="left">
								<ww:property value="standardMileage" /><ww:if test="standardMileage != null">Km</ww:if>
							</td>
							<td align="left">
								<ww:property value="shizuStrategyName" />
							</td>
							<td align="left">
								<ww:if test=" #rizuStrategyName != null ">
									<ww:property value="rizuStrategyName" />
								</ww:if>
							</td>
							<td align="center">
								<!-- <a href="javascript:void(0);" onclick="editEntity('<ww:property value="id"/>','update');">编辑</a> -->
								<ww:if test="hasPrivilegeUrl('/car/carGet.action')">
									<div class="pan_btn3"  onclick="editEntity('<ww:property value="id"/>','update');">编辑</div>
								</ww:if>
								<ww:if test="hasPrivilegeUrl('/systemutil/getSysOperateLogByDataId.action')">
									<div class="pan_btn2"  onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path %>');">记录</div>
								</ww:if>
							</td>
						</tr>
					</ww:iterator>
					<tr style="background-color: #fff;height: 30px;">
						<td align="center" colspan="12">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>