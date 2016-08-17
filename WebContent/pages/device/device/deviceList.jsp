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

<title>车机管理</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	function editEntity(id,state){
		window.location.href="<%=path%>/device/deviceGet.action?id="+id+'&state='+state;
	}
	
	function searchEntity(){
		$("#sform").submit();
	}
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
		<form name="sform" class="form-horizontal" id="sform" method="post" action="<%=path%>/device/deviceSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-sm-4 col-xs-6">
						<div class="form-group">
						    <label for="device.setNo" class="col-xs-4 control-label">车机编号：</label>
						    <div class="col-xs-8">
						    	<input class="form-control" name="device.setNo" id="device.setNo" type="text" value="<ww:property value="device.setNo"/>">
						    </div>
					    </div>
					</div>
					<div class="col-sm-4 col-xs-6">
					    <div class="form-group"><label for="device.setType" class="col-xs-4 control-label">车机型号：</label>
						    <div class="col-xs-8">
						    	<input class="form-control" name="device.setType" id="device.setType" type="text" value="<ww:property value="device.setType"/>">
						    </div>
						</div>
					</div>
					<div class="col-sm-4 col-xs-6">
						<div class="form-group">
							<label for="cjcjmc" class="col-xs-5 control-label">车机厂家名称：</label>
							<div class="col-xs-7">
								<select class="form-control" id="cjcjmc" name="device.setName" >
									<option value="">全部</option>
									<ww:iterator value="getCompanys('7')" id="data" status="rl">
										<option value="<ww:property value="id" />"  <ww:if test="device.setName==id">selected=true</ww:if> ><ww:property value="name" /></option>	
									</ww:iterator>
								</select>
							</div>
						</div>
					</div>
			   	</div>
				<div class="row SubmitButtonBlock">
					<ww:if test="hasPrivilegeUrl('/device/deviceSearch.action')">
						<div class="col-sm-2 col-sm-offset-3 col-xs-4"><a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
					</ww:if>
					<ww:if test="hasPrivilegeUrl('/device/deviceAdd.action')">
						<div class="col-sm-2 col-xs-4"><a class="btn btn-block Button2" onclick="editEntity('','add');" target="_blank"><i class="fa fa-floppy-o"></i>添加</a></div>
					</ww:if>
	  			</div>
	  		</div>
			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						<td  height="28" width="40"  >
							<input type="checkbox" name="checkdelcheckall" onclick="funCheck('','checkdel')" />
						</td>
						<td width="100">
							<a href="javascript:SetOrder('setNo')">车机编码<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td width="180">
							<a href="javascript:SetOrder('setName')">车机厂家名称<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td width="100">
							<a href="javascript:SetOrder('setType')">车机型号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td width="100">
							SIM卡号
						</td>
						<td width="100">
							是否刷过固件
						</td>
						<td width="100">
							当前固件版本
						</td>
						<td width="70">
							车机状态
						</td>
						<td>备注</td>
						<td width="157">
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
								<ww:property value="setNo" />
							</td>
							<td align="left">
								<ww:property value="getCompNameById(setName)" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('deviceSetType',setType)" />
							</td>
							<td align="left">
								<ww:property value="simId" />
							</td>
							<td align="center">
								<ww:if test="updateFirmware==1">
									是
								</ww:if>
								<ww:else>否</ww:else>
							</td>
							<td align="left">
								<ww:property value="firmwareVersion" />
							</td>
							<td align="center">
								<ww:property value="getState(id)" />
							</td>
							<td>
								<ww:property value="remark" />
							</td>
							<td align="center">
								<!-- <a href="javascript:void(0);" onclick="editEntity('<ww:property value="id"/>','update');">编辑</a> -->
								<ww:if test="hasPrivilegeUrl('/device/deviceGet.action')">
									<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>','update');">编辑</div>
								</ww:if>
								<ww:if test="hasPrivilegeUrl('/systemutil/getSysOperateLogByDataId.action')">
									<div class="pan_btn2"  onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path %>');">记录</div>
								</ww:if>
							</td>
						</tr>
					</ww:iterator>
					<tr style="background-color: #fff;height: 30px;">
						<td align="center" colspan="10">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>