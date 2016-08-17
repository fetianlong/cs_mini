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
<title>车机绑定管理</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">

	function editEntity(id){
		window.location.href="<%=path%>/deviceBinding/deviceBindingGet.action?id="+id;
	}
	
	//解绑
	function unbind(id){
		alertconfirm("确定要解除绑定吗？",function (){
			showLoading();
			var data={"id":id}
			$.post('deviceBindingUnbind.action',data,r_unbind,'json').error(requestError);
		});
	}
	function r_unbind(data){
		hideLoading();
		switch(data.result){
			case 0:
				alertok("解绑成功！", function(){
			    	$('#sform').submit();		
			    });
				break;
			case 1:
				restoreInfo();
				alerterror(data.msg);
				break;
			case 9:
				document.location = "doError.action";
				break;
		}
	}
	function searchEntity(){
		$("#sform").submit();
	}
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
	    <form name="sform" class="form-horizontal" id="sform" method="post" action="<%=path%>/deviceBinding/deviceBindingSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-sm-4 col-xs-6">
						<div class="form-group">
							<label for="cph" class="col-xs-4 control-label">车牌号：</label>
							<div class="col-xs-8">
								<input class="form-control" type="text" id="cph" name="deviceBinding.car.vehiclePlateId" value='<ww:property value="deviceBinding.car.vehiclePlateId"/>' />
							</div>
						</div>
						<div class="form-group">
							<label for="bdzd" class="col-xs-4 control-label">绑定状态：</label>
							<div class="col-xs-8">
								<select class="form-control" id="bdzd" name="deviceBinding.bindType" >
									<option value="">全部</option>
									<option value="1" <ww:if test="deviceBinding.bindType==1">selected=true</ww:if> >绑定</option>	
									<option value="0" <ww:if test="deviceBinding.bindType==0">selected=true</ww:if>>解绑</option>	
								</select>
							</div>
						</div>
					</div>
					<div class="col-sm-4 col-xs-6">
						<div class="form-group">
							<label for="cjbm" class="col-xs-4 control-label">车机编码：</label>
							<div class="col-xs-8">
								<input class="form-control" type="text" id="cjbm" name="deviceBinding.deviceNo" value='<ww:property value="deviceBinding.deviceNo"/>' />
							</div>
						</div>
					</div>
					<div class="col-sm-4 col-xs-6">
						<div class="form-group">
							<label for="simkh" class="col-xs-4 control-label">SIM卡号：</label>
							<div class="col-xs-8">
								<input class="form-control" type="text" id="simkh" name="deviceBinding.device.simId"  value='<ww:property value="deviceBinding.device.simId"/>' />
							</div>
						</div>
					</div>
					
				</div>
				<div class="row SubmitButtonBlock">
					<ww:if test="hasPrivilegeUrl('/deviceBinding/deviceBindingSearch.action')">
						<div class="col-sm-2 col-sm-offset-4 col-xs-6"><a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
					</ww:if>
					<ww:if test="hasPrivilegeUrl('/deviceBinding/deviceBindingAdd.action')">
						<div class="col-sm-2 col-xs-6"><a class="btn btn-block Button2" onclick="editEntity('');" target="_blank"><i class="fa fa-floppy-o"></i>添加</a></div>
					</ww:if>
				</div>
		    </div>
			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths" id="tab_bg_cl">
						<td  width="100">
							<a href="javascript:SetOrder('carPlateNo')">车牌号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td  width="100">
							<a href="javascript:SetOrder('deviceNo')">车机编码<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td  width="100">
							车辆品牌
						</td>
						<td  width="180">
							车机厂家名称
						</td>
						<td  width="80">
							车机型号
						</td>
						<td  width="80">
							SIM卡号
						</td>
						<td  width="100">
							<a href="javascript:SetOrder('bindType')">绑定状态<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
						</td>
						<td>备注</td>
						<td  width=157>
							操作
						</td>
					</tr>
					<ww:iterator value="page.results" id="data" status="rl">
						<tr <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;" >
							<td align="left">
								<ww:property value="car.vehiclePlateId" />
							</td>
							<td align="left">
								<ww:property value="deviceNo" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('10',car.carVehicleModel.brand)"/>
							</td>
							<td align="left">
								<ww:property value="getCompNameById(device.setName)" />
							</td>
							
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('deviceSetType',device.setType)" />
							</td>
							<td align="left">
								<ww:property value="device.simId" />
							</td>
							<td align="center">
								<ww:if test="bindType==0">
									解绑
								</ww:if>
								<ww:elseif test="bindType==1">
									绑定
								</ww:elseif>
							</td>
							<td></td>
							<td align="center">
								<ww:if test="hasPrivilegeUrl('/deviceBinding/deviceBindingGet.action')">
									<div class="<ww:if test="bindType==1">pan_btn3 fl</ww:if><ww:if test="bindType!=1">btn_center</ww:if>"  onclick="editEntity('<ww:property value="id"/>');">编辑</div>
								</ww:if>
								<ww:if test="bindType==1 && hasPrivilegeUrl('/deviceBinding/deviceBindingUnbind.action')">
									<div class="pan_btn4 fl"  onclick="unbind('<ww:property value="id"/>');">解绑</div>
								</ww:if>
								<ww:if test="hasPrivilegeUrl('/systemutil/getSysOperateLogByDataId.action')">
									<div class="pan_btn2"  onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path %>');">记录</div>
								</ww:if>
							</td>
						</tr>
					</ww:iterator>
					<tr style="background-color: #fff;height: 30px;">
						<td align="center" colspan="9">
							<ww:property value="page.pageSplit" />	
						</td>
					</tr>
				</table>
			</div>
			
		</form>
	</div>
</body>
</html>