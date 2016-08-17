<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%@ page import="com.dearho.cs.sys.util.DictUtil" language="java" %>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Insert title here</title>

<%@ include file="/pages/common/common_head.jsp"%>


<script type="text/javascript">
	//添加车辆跳转页面
	function editEntity(id,state){
		window.location.href="<%=path%>/car/carGet.action?id="+id+"&state="+state;
	}
	
	function searchEntity(){
		$("#sform").submit();
	}
	var models = [];
	function changeModel(thisval){
		$('#car\\.modelId').empty();
// 		$('#car\\.carVehicleModel\\.engineType').empty();
		$('#car\\.modelId').append($('<option value="">全部</option>'));
// 		$('#car\\.carVehicleModel\\.engineType').append($('<option value="">全部</option>'));
		if(models.length == 0){
			var modelsStr = $('#allModelInput').val();
			if(modelsStr != null && modelsStr != ""){
				var modelStrs = modelsStr.split(',');
				$.each(modelStrs,function(index,modelStr){
					if(modelStr == null || modelStr == ""){
						return true;
					}
					var model = {};
					model.id=modelStr.split(":")[0];
					model.name=modelStr.split(":")[1];
// 					model.engineType=modelStr.split(":")[2];
// 					model.engineTypeName=modelStr.split(":")[3];
					model.brand=modelStr.split(":")[2];
					models.push(model);
				});
			}
		}
		
		
		$.each(models,function(index,model){
			if(thisval != null && thisval != ''){
				if(model.brand == thisval){
					var opt = "<option value='"+model.id+"'>"+model.name+"</option>";
					$('#car\\.modelId').append($(opt));
				}
// 				if(index == 0){
// 					changeEngine(model.id);
// 				}
			}
			else{
				var opt = "<option value='"+model.id+"'>"+model.name+"</option>";
				$('#car\\.modelId').append($(opt));
// 				if(index == 0){
// 					changeEngine(model.id);
// 				}
			}
			
			
		});
<%-- 		$.post('<%=path %>/carVehicleModel/carVehicleModelAjax.action',"carVehicleModel.brand="+thisval,r_changeModel,'json').error(requestError); --%>
	}
// 	function r_changeModel(data){
// 		if(data != null && data.result != null && data.result == 0){
// 			models = data.info;
// 			$.each(data.info,function(index,model){
// 				var opt = "<option value='"+model.id+"'>"+model.name+"</option>";
// 				$('#car\\.modelId').append($(opt));
// 				if(index == 0){
// 					changeEngine(model.id);
// 				}
// 			});
// 		}
// 	}
// 	function changeEngine(modelId){
// 		$('#car\\.carVehicleModel\\.engineType').empty();
// 		$('#car\\.carVehicleModel\\.engineType').append($('<option value="">全部</option>'));
// 		$.each(models,function(index,model){
// 			if(model.id == modelId){
// 				var opt = "<option value='"+model.engineType+"'>"+model.engineTypeName+"</option>";
// 				$('#car\\.carVehicleModel\\.engineType').append($(opt));
// 			}
			
// 		});
// 	}
</script>
</head>
<body class="SubPage">
	<div class="container-fluid">
			<form name="sform" class="form-horizontal" id="sform" method="post" action="<%=path%>/car/carSearch.action">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">
				<input type="hidden" id="allModelInput" value="
					<ww:iterator value="getAllModel('null')" id="data" status="rl"><ww:property value="id" />:<ww:property value="name" />:<ww:property value="brand" />,</ww:iterator>"/>
				<div class="ControlBlock">
					<div class="row SelectBlock">
						<div class="col-sm-4 col-xs-6">
							<div class="form-group">
								<label for="cph" class="col-xs-4 control-label">车牌号：</label>
								<div class="col-xs-8">
									<input type="text" id="cph"  class="form-control" name="car.vehiclePlateId" value='<ww:property value="car.vehiclePlateId" />' />
								</div>
							</div>
							<div class="form-group">
							 	<label for="clzt" class="col-xs-4 control-label">车辆状态：</label>
							 	<div class="col-xs-8">
								 	<select class="form-control" id="clzt" name="car.bizState">
										<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('carBizState',1)" id="data" status="rl">
											<option value="<ww:property value="id" />"  <ww:if test="car.bizState==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
										</ww:iterator>
									</select>
								</div>
							 </div>
						</div>
						<div class="col-sm-4 col-xs-6">
							<div class="form-group">
							 	<label for="car.carVehicleModel.brand" class="col-xs-4 control-label">车辆品牌：</label>
							 	<div class="col-xs-8">
								 	<select  class="form-control" onchange="changeModel(this.value)"  name="car.carVehicleModel.brand" id="car.carVehicleModel.brand">
										<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('10',1)" id="data" status="rl">
											<option value="<ww:property value="id" />"  <ww:if test="car.carVehicleModel.brand==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
										</ww:iterator>
									</select>
								</div>
							 </div>
							<div class="form-group">
							 	<label for="clzt" class="col-xs-4 control-label">是否广告车：</label>
							 	<div class="col-xs-7">
						    		<select class="form-control" id="car.isAd" name="car.isAd" >
										<option value="">全部</option>
										<option value="1" <ww:if test="car.isAd==1">selected=true</ww:if> >是</option>	
										<option value="0" <ww:if test="car.isAd==0">selected=true</ww:if> >否</option>	
									</select>
						    	</div>
							 </div>
		<!-- 					 <div class="nr fl"><span>动力类型：</span> -->
		<!-- 					   <select class="kd"  name="car.carVehicleModel.engineType" id="car.carVehicleModel.engineType"> -->
		<%-- 							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('2',1)" id="data" status="rl"> --%>
		<%-- 								<option value="<ww:property value="id" />"  <ww:if test="car.carVehicleModel.engineType==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	 --%>
		<%-- 							</ww:iterator> --%>
		<!-- 						</select> -->
								
		<!-- 				   </div> -->
							
						</div>
						<div class="col-sm-4 col-xs-6">
							<div class="form-group">
								<label for="car.modelId" class="col-xs-4 control-label">车辆型号：</label>
								<div class="col-xs-8">
								 	<select  class="form-control" name="car.modelId" id="car.modelId">
								 		<option value="">全部</option>
										<ww:iterator value="getAllModel('brand')" id="data" status="rl">
											<option value="<ww:property value="id" />"  <ww:if test="car.modelId==id">selected=true</ww:if> ><ww:property value="name" /></option>	
										</ww:iterator>
									</select>
								</div>
							</div>
							
						</div>
					 </div>
					 <div class="row SubmitButtonBlock">
					 	<ww:if test="hasPrivilegeUrl('/carVehicleModel/carVehicleModelSearch.action')">
							<div class="col-sm-2 col-sm-offset-3 col-xs-4"><a class="btn btn-block Button1"   onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a></div>
						</ww:if>
						<ww:if test="hasPrivilegeUrl('/carVehicleModel/carVehicleModelSearch.action')">
							<div class="col-sm-2 col-xs-4"><a class="btn btn-block Button2" onclick="editEntity('','add');" target="_blank"><i class="fa fa-floppy-o"></i>添加</a></div>
						</ww:if>
  				     </div>
  				</div>
				<div class="row TableBlock">
					<table class="table table-striped table-bordered table-condensed">
						<tr class="ths" id="tab_bg_cl">
							<td  width="68" height="50">
								<input type="checkbox" name="checkdelcheckall" onclick="funCheck('','checkdel')" />
							</td>
							<td width="100">
								<a href="javascript:SetOrder('vehiclePlateId')">车牌号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td width="80">
								车辆品牌
							</td>
							<td width="100">
								车辆型号
							</td>
							<td>
								所属网点
							</td>
							<td>
								车架号
							</td>
							<td>
								车辆状态
							</td>
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
									<ww:property value="vehiclePlateId" />
								</td>
								
								<td align="left">
									<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('10',carVehicleModel.brand)"/>
								</td>
								<td align="left">
									<ww:property value="carVehicleModel.name" />
								</td>
								<td align="left">
									<ww:property value="getBelongDotName(id)" />
								</td>
								<td align="center">
									<ww:property value="vin" />
								</td>
								<td align="center">
									<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('carBizState',bizState)" />
								</td>
								<td>
									<div class="pan_btn3"  onclick="javascript:editEntity('<ww:property value="id"/>','update');">编辑</div>
									<div class="pan_btn2"  onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path %>','车辆管理');">记录</div>
									<!--  <a href="javascript:void(0);" onclick="editEntity('<ww:property value="id"/>','update');">编辑</a>-->
									<!--  <a href="javascript:void(0);" onclick="editEntity('<ww:property value="id"/>','get');">详细信息</a>-->
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