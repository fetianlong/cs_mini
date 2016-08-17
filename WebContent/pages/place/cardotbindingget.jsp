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
var edittype = '<ww:property value="type"/>';

function getForm(){
	return $("#sform");
}
	function searchEntity(){
		$("#sform").submit();
	}
	var api = frameElement.api, W = api.opener;
	function isValid(){
		var ob = document.getElementsByName("checkdel");
		var check = false;
		for (var i = 0; i < ob.length; i++) {
			if (ob[i].checked) {
				check = true;
				break;
			}
		}
		if (!check) {
		    return false;
		}
		return true;
	}
	function checkTr(tr){
		var chk = $(tr).find("input[name='checkdel']");
		if(chk.attr('checked') == 'checked'){
			chk.attr("checked", false);
		}
		else{
			chk.attr("checked", true);
		}
	}
</script>
</head>
<body class="SubPage">
<div class="container-fluid">
	<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/place/carDotBinding/searchCars.action?dotId=<ww:property value="dotId"/>&type=<ww:property value="type"/>">
		<input type="hidden" name="carPage.orderFlag" id="carPage.orderFlag"
				value="<ww:property value="carPage.orderFlag"/>">
		<input type="hidden" name="carPage.orderString" id="carPage.orderString"
				value="<ww:property value="carPage.orderString"/>">
		<input type="hidden" name=dotId 
				value="<ww:property value="dotId"/>">
				
<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-md-4">
				<div class="form-group">
					<label class="col-sm-4 control-label">车牌号</label>
					<div class="col-sm-8">
						<input class="form-control" type="text" name="vehiclePlateId" value='<ww:property value="vehiclePlateId" />' />
					</div>
				</div>
			</div>
		</div>
		<div class="row SubmitButtonBlock">
			<div class="col-sm-2 col-sm-offset-5">
				<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
			</div>
			
		</div>
	</div>
	
<div class="row TableBlock">
			<table class="table table-striped table-bordered table-condensed">
				<tr class="ths" id="tab_bg_cl">
					<td width="50">
						<input type="checkbox" name="checkdelcheckall" onclick="funCheck('','checkdel')">
					</td>
					<td  align="center" nowrap width="100">
						<a href="javascript:SetOrder('vehiclePlateId')">车牌号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
					</td>
					<td  align="center" nowrap width="100">
						<a href="javascript:SetOrder('vin')">车架号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
					</td>
					<td  align="center" nowrap width="80">
						<a href="javascript:void(0);">车辆品牌<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
					</td>
					<td   align="center" nowrap width="100">
						<a href="javascript:void(0);">车辆型号<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
					</td>
					<td   align="center" nowrap width="100">
						<a href="javascript:void(0);">动力类型<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
					</td>
					<td  align="center" nowrap width="100">
						<a href="javascript:SetOrder('operationCity');">运营城市<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
					</td>
				</tr>
				<ww:iterator value="carPage.results" id="data" status="rl">
					<tr  onclick="checkTr(this);"
					<%--ondblclick="confirmTr(this);" --%>
						
						 <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
						<td align="center">
							<input type="checkbox" name="checkdel" 
								value="<ww:property value="id" />"/>
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
						<td align="left">
							<ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('2',carVehicleModel.engineType)" />
						</td>
						<td align="left">
							<ww:property value="operationCity" />
						</td>
					</tr>
				</ww:iterator>
				<tr>
					<td align="center" colspan="8">
						<ww:property value="carPage.pageSplit" />	
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
</body>
</html>