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
	var api = frameElement.api, w = api.opener;
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
		if(valid){
			var str=$("input[name='carDoc']:checked").val();
			if(typeof(str)=="undefined"){
				alert("请选择车辆");
			}
			if(str!=""){
				var carDoc=str.split(",");
			    w.$("#carId").val(carDoc[0]);
				w.$("#vehiclePlateId").val(carDoc[1]);
				w.$("#vin").val(carDoc[2]);
				w.$("#brand").val(carDoc[3]);
				w.$("#modelId").val(carDoc[4]);
				w.$("#color").val(carDoc[5]);
				w.$("#level").val(carDoc[6]);
				w.$("#seatingNum").val(carDoc[7]);
			}
		}else{
			return false;
		}
	};
	
	function confirmTr(tr){
		var str = $(tr).find("input[name='carDoc']").val();
		if(str!=""){
			var carDoc=str.split(",");
		    w.$("#carId").val(carDoc[0]);
			w.$("#vehiclePlateId").val(carDoc[1]);
			w.$("#vin").val(carDoc[2]);
			w.$("#brand").val(carDoc[3]);
			w.$("#modelId").val(carDoc[4]);
			w.$("#color").val(carDoc[5]);
			w.$("#level").val(carDoc[6]);
			w.$("#seatingNum").val(carDoc[7]);
			
		}else{
			return false;
		}
		api.close();
	}
	
</script>
</head>
<body>
	<div>
		<div class="col-sm-12">
			<div class="panel">
				<div class="panel-heading">
					<h3 class="panel-title">车辆管理</h3>							
		        </div>
		    </div>
			<form name="sform" id="sform" method="post" action="<%=path%>/car/carSearch.action">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
				<input type="hidden" name="state" 
						value="<ww:property value="state"/>">
				<div class="chazhao">
					<div class="nr fl">
						<span>车牌号：</span>
						<input class="kd" type="text" name="car.vehiclePlateId" value='<ww:property value="car.vehiclePlateId" />' />
					</div>
					<div class="nra"><a class="find_btn"  onclick="searchEntity();" target="_blank">查&nbsp;&nbsp;询</a></div>
				</div>
				<div class="panel_content">
					<table width="100%" class="biaoge" border="0"  cellspacing="0">
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
								<a href="javascript:void(0);">车辆品牌</a>
							</td>
							<td   align="center" nowrap width="100">
								<a href="javascript:void(0);">车辆型号</a>
							</td>
							<td   align="center" nowrap width="100">
								<a href="javascript:void(0);">动力类型</a>
							</td>
							<td  align="center" nowrap width="100">
								<a href="javascript:SetOrder('operationCity');">运营城市</a>
							</td>
						</tr>
						<ww:iterator value="page.results" id="data" status="rl">
							<tr ondblclick="confirmTr(this);"
								
								 <ww:if test="#rl.even"> class="trs"</ww:if> >
								<td align="center">
									<input type="radio" name="carDoc" <ww:if test="#rl.count==1">checked=true</ww:if>
									value="<ww:property value="id" />,<ww:property value="vehiclePlateId" />,<ww:property value="vin" />,<ww:property value="carVehicleModel.brand" />,<ww:property value="carVehicleModel.name" />,<ww:property value="color" />,<ww:property value="carVehicleModel.level" />,<ww:property value="carVehicleModel.seatingNum" />" />
								</td>
								<td align="left">
									<ww:property value="vehiclePlateId" />
								</td>
								<td align="left">
									<ww:property value="vin" />
								</td>
								<td align="left">
									<ww:property value="carVehicleModel.brand" />
								</td>
								<td align="left">
									<ww:property value="carVehicleModel.name" />
								</td>
								<td align="left">
									<ww:property value="carVehicleModel.engineType" />
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
	</div>
</body>
</html>