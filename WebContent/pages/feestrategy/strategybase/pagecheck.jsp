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
<title>基础计费策略</title>
<%@ include file="/pages/common/common_head.jsp"%>
<script type="text/javascript">
var api = frameElement.api, W = api.opener;
var relationEditDoc = W;
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
		var checkInputs = $("input[name='checkdel']:checked");
		if(checkInputs == null || checkInputs.length == 0){
			alert("请选择策略");
			return false;
		}
		var ids = '';
		var names = '';
		$.each(checkInputs,function(index,checkInput){
			var str = $(checkInput).val();
			var strategyBaseDoc=str.split(",");
			ids += strategyBaseDoc[0]+";";
			names += strategyBaseDoc[1]+"；";
		});
		relationEditDoc.$("#strategyBaseIds").val(ids);
		relationEditDoc.$("#strategyBaseNames").val(names);
// 		var str=$("input[name='checkdel']:checked").val();
// 		if(typeof(str)=="undefined" || str == null || str == ''){
			
// 		}
// 		if(str!=""){
// 			var strategyBaseDoc=str.split(",");
// 		    relationEditDoc.$("#strategyBaseId").val(strategyBaseDoc[0]);
// 			relationEditDoc.$("#strategyBaseName").val(strategyBaseDoc[1]);
// 		}
};


function searchEntity(){
	$("#sform").submit();
}
	
</script>
</head>
<body class="SubPage">
<div class="container-fluid">
<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/feestrategy/strategyBase/searchStrategyBase.action">
	<input type="hidden" name="page.orderFlag" id="page.orderFlag" value="<ww:property value="page.orderFlag"/>">
	<input type="hidden" name="page.orderString" id="page.orderString" value="<ww:property value="page.orderString"/>">
	<input type="hidden" name="state" id="state" value="<ww:property value="state"/>">
	<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-sm-4 col-xs-6">
				<div class="form-group">
					<label for="strategyBase.name" class="col-xs-4 control-label">策略名称</label>
					<div class="col-xs-8">
						<input class="form-control" name="strategyBase.name" id="strategyBase.name" type="text" value="<ww:property value="strategyBase.name"/>">
					</div>
				</div>
			</div>
			
			<div class="col-sm-4 col-xs-6">
				<div class="form-group">
					<label for="strategyBase.type" class="col-xs-4 control-label">策略类型</label>
					<div class="col-xs-8">
						<select class="form-control" name="strategyBase.type" id="strategyBase.type">
							<ww:iterator value="#dictUtil.getDictSelectsByGroupCode('strategyBaseType',1)" id="data" status="rl">
										<option value="<ww:property value="id" />"  <ww:if test="strategyBase.type==id">selected=true</ww:if> ><ww:property value="cnName" /></option>	
							</ww:iterator>
						</select>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row SubmitButtonBlock">
			<div class="col-sm-2 col-sm-offset-3 col-xs-4">
				<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
			</div>
		</div>
	</div>
	
	<div class="row TableBlock">
	<table class="table table-striped table-bordered table-condensed">
		<tr class="ths" id="tab_bg_cl">
			<td><input type="checkbox" name="checkdelcheckall" onclick="funCheck('','checkdel')"></td>
			<td><a href="javascript:SetOrder('name')">策略名称<img src="<%=path%>/admin/common/images/main/paixu.png"/></a></td>
			<td><a href="javascript:SetOrder('type')">策略类型<img src="<%=path%>/admin/common/images/main/paixu.png"/></a></td>
			<td>基础价格</td>
			<td>里程价格</td>
		</tr>
		<ww:iterator value="page.results" id="data" status="rl">
				<tr <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
					<td align="center">
						<input type="checkbox" name="checkdel" value="<ww:property value="id"/>,<ww:property value="name"/> "> 
					</td>
					<td align="center"><ww:property value="name" /></td>
					<td align="center"><ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('strategyBaseType',type)" /></td>
					<td align="center"><ww:property value="basePrice" /></td>
					<td align="center"><ww:property value="kmPrice" /></td>
				</tr>
			
		</ww:iterator>
		<tr>
			<td align="right" colspan="10"><ww:property value="page.pageSplit" /></td>
		</tr>
	</table>
	</div>
</form>
</div>
</body>
</html>