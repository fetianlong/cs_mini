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

<title>Insert title here</title>

<%@ include file="/pages/common/common_head.jsp"%>


<script type="text/javascript">
	$(function(){
		$('#sform').validate({
			errorClass : 'text-danger',
			rules: {
				"carDoc": {
					required: true
				}
			},
			messages: {
				"carDoc": {
					required: "请选择车辆！"
				}
			}
		});
	})
	function searchEntity(){
		$("#sform").submit();
	}
	function isValid(){
		if ($("#sform").valid()){
			return true;
		}else{
			return false;
		}
	}
	var api = frameElement.api, W = api.opener;
	var carBindEditDoc = W;
	api.button({
	    id:'valueOk',
		name:'确定',
		callback:ok,
		focus: true
	});
	api.button({
	    id:'valueCancel',
		name:'取消'
	});
	
	function ok()
	{
		var valid=isValid();
		if(isValid()){
			var str=$("input[name='carDoc']:checked").val();
			if(typeof(str)=="undefined"){
				alert("请选择车辆");
			}
			if(str!=""){
				var carDoc=str.split(",");
			    carBindEditDoc.$("#carId").val(carDoc[0]);
				carBindEditDoc.$("#vehiclePlateId").val(carDoc[1]);
				carBindEditDoc.$("#vin").val(carDoc[2]);
				carBindEditDoc.$("#brand").val(carDoc[3]);
				carBindEditDoc.$("#modelId").val(carDoc[4]);
				carBindEditDoc.$("#color").val(carDoc[5]);
				carBindEditDoc.$("#level").val(carDoc[6]);
				carBindEditDoc.$("#seatingNum").val(carDoc[7]);
				carBindEditDoc.$("#engineNumber").val(carDoc[8]);
			}
		}else{
			return false;
		}
	};
	
	function confirmTr(tr){
		var str = $(tr).find("input[name='carDoc']").val();
		if(str!=""){
			var carDoc=str.split(",");
		    carBindEditDoc.$("#carId").val(carDoc[0]);
			carBindEditDoc.$("#vehiclePlateId").val(carDoc[1]);
			carBindEditDoc.$("#vin").val(carDoc[2]);
			carBindEditDoc.$("#brand").val(carDoc[3]);
			carBindEditDoc.$("#modelId").val(carDoc[4]);
			carBindEditDoc.$("#color").val(carDoc[5]);
			carBindEditDoc.$("#level").val(carDoc[6]);
			carBindEditDoc.$("#seatingNum").val(carDoc[7]);
			carBindEditDoc.$("#engineNumber").val(carDoc[8]);
			
		}else{
			return false;
		}
		api.close();
	}
	
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
		<form name="sform" id="sform" class="form-horizontal" method="post" action="<%=path%>/car/carSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
			<input type="hidden" name="state" 
					value="<ww:property value="state"/>">
			<input type="hidden" name="query" 
					value="<ww:property value="query"/>">
			<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-xs-6">
						<div class="form-group">
							<label for="cph" class="col-xs-4 control-label">车牌号：</label>
							<div class="col-xs-8">
								<input type="text" id="cph"  class="form-control" name="car.vehiclePlateId" value='<ww:property value="car.vehiclePlateId" />' />
							</div>
						</div>
					</div>
				</div>
				<div class="row SubmitButtonBlock">
					<div class="col-xs-2 col-xs-offset-3"><a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
				</div>
			</div>
			<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
					<tr class="ths">
						<td height="28" width="40" align="center" nowrap>
						</td>
						<td  align="center" nowrap width="100">
							<a href="javascript:SetOrder('vehiclePlateId')">车牌号</a>
						</td>
						<td  align="center" nowrap width="100">
							<a href="javascript:SetOrder('vin')">车架号</a>
						</td>
						<td  align="center" nowrap width="80">
							车辆品牌
						</td>
						<td   align="center" nowrap width="100">
							车辆型号
						</td>
						<td   align="center" nowrap width="100">
							动力类型
						</td>
						<td  align="center" nowrap width="100">
							运营城市
						</td>
					</tr>
					<ww:iterator value="page.results" id="data" status="rl">
						<tr ondblclick="confirmTr(this);"
							
							 <ww:if test="#rl.even"> class="trs"</ww:if> >
							<td align="center">
								<input type="radio" name="carDoc" 
								value="<ww:property value="id" />,<ww:property value="vehiclePlateId" />,<ww:property value="vin" />,<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('10',carVehicleModel.brand)"/>,<ww:property value="carVehicleModel.name" />,<ww:property value="color" />,<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('1',carVehicleModel.level)" />,<ww:property value="carVehicleModel.seatingNum" />,<ww:property value="engineNumber" />" />
							</td>
							<td align="left">
								<ww:property value="vehiclePlateId" />
							</td>
							<td align="left">
								<ww:property value="vin" />
							</td>
							<td align="left">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('10',carVehicleModel.brand)"/>
							</td>
							<td align="left">
								<ww:property value="carVehicleModel.name" />
							</td>
							<td align="center">
								<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('2',carVehicleModel.engineType)" />
							</td>
							<td align="left">
								<ww:property value="operationCity" />
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