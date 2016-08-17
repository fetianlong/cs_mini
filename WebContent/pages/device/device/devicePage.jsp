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
<title>查找车机</title>
<%@ include file="/pages/common/common_head.jsp"%>

<script type="text/javascript">
	
	$(function(){
		$('#sform').validate({
			errorClass : 'text-danger',
			rules: {
				"deviceDoc": {
					required: true
				}
			},
			messages: {
				"deviceDoc": {
					required: "请选择车机！"
				}
			}
		});
	})
	function isValid(){
		if ($("#sform").valid()){
			return true;
		}else{
			return false;
		}
	}
	function searchEntity(){
		$("#sform").submit();
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
	function ok(){
		var valid=isValid();
		if(valid){
			var str=$("input[name='deviceDoc']:checked").val();
			if(str!=""){
				var deviceDoc=str.split(",");
				carBindEditDoc.$("#setId").val(deviceDoc[0]);
				carBindEditDoc.$("#setNum").val(deviceDoc[1]);
				carBindEditDoc.$("#setName").val(deviceDoc[2]);
				carBindEditDoc.$("#setType").val(deviceDoc[3]);
				carBindEditDoc.$("#simId").val(deviceDoc[4]);
				carBindEditDoc.$("#firmwareVersion").val(deviceDoc[5]);
				if(deviceDoc[6]=="1"){
					carBindEditDoc.$("#updateFirmware").val("是");
				}else if(deviceDoc[6]=="0"){
					carBindEditDoc.$("#updateFirmware").val("否");
				}
			}
		}else{
			return false;
		}
	};
	function confirmTr(tr){
		var str = $(tr).find("input[name='deviceDoc']").val();
		if(str!=""){
			var deviceDoc=str.split(",");
		    carBindEditDoc.$("#setId").val(deviceDoc[0]);
			carBindEditDoc.$("#setNum").val(deviceDoc[1]);
			carBindEditDoc.$("#setName").val(deviceDoc[2]);
			carBindEditDoc.$("#setType").val(deviceDoc[3]);
			carBindEditDoc.$("#simId").val(deviceDoc[4]);
			carBindEditDoc.$("#firmwareVersion").val(deviceDoc[5]);
			if(deviceDoc[6]=="1"){
				carBindEditDoc.$("#updateFirmware").val("是");
			}else if(deviceDoc[6]=="0"){
				carBindEditDoc.$("#updateFirmware").val("否");
			}
			
		}else{
			return false;
		}
		api.close();
	}
	function selectTr(tr){
		var rd = $(tr).find("input[type='radio']");
		rd.attr('selected','selected');
	}
</script>
</head>
<body class="SubPage">
<div class="container-fluid">
	<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/device/deviceSearch.action">
		<input type="hidden" name="page.orderFlag" id="page.orderFlag"
				value="<ww:property value="page.orderFlag"/>">
		<input type="hidden" name="page.orderString" id="page.orderString"
				value="<ww:property value="page.orderString"/>">
		<input type="hidden" name="state" value="<ww:property value="state"/>" />
		
		<div class="ControlBlock">
				<div class="row SelectBlock">
					<div class="col-xs-6">
						<div class="form-group">
							<label for="cph" class="col-xs-4 control-label">车机编号：</label>
							<div class="col-xs-8">
								<input class="form-control" name="device.setNo" id="device.setNo" type="text" value="<ww:property value="device.setNo"/>">
							</div>
						</div>
						<div class="form-group">
							<label for="bdzd" class="col-xs-4 control-label">车机型号：</label>
							<div class="col-xs-8">
								<input class="form-control" name="device.setType" id="device.setType" type="text" value="<ww:property value="device.setType"/>">
							</div>
						</div>
					</div>
					<div class="col-xs-6">
						<div class="form-group">
							<label for="cjbm" class="col-xs-4 control-label">车机厂家名称：</label>
							<div class="col-xs-8">
								<select class="form-control"   name="device.setName" >
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
					<div class="col-xs-2 col-xs-offset-5">
						<a class="btn btn-block Button1" onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
					</div>
				</div>
		    </div>
		

		<div class="row TableBlock">
				<table class="table table-striped table-bordered table-condensed">
				<tr class="ths" id="tab_bg_cl">
					<td width=90>
						选择
					</td>
					<td width="100">
						<a href="javascript:SetOrder('setNo')">车机编码<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
					</td>
					<td  width="100">
						车机厂家名称
					</td>
					<td width="80">
						车机型号
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
				</tr>
		
				<ww:iterator value="page.results" id="data" status="rl">
					<tr  ondblclick="confirmTr(this);" onclick="selectTr(this);"
						
						 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
						<td align="center">
							<input type="radio" name="deviceDoc" value="<ww:property value="id" />,<ww:property value="setNo" />,<ww:property value="getCompNameById(setName)" />,<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('deviceSetType',setType)" />,<ww:property value="simId" />,<ww:property value="firmwareVersion" />,<ww:property value="updateFirmware" />" />
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
					</tr>
				</ww:iterator>
				<tr>
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