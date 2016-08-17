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
function detailEntity(id){
	window.location.href="<%=path%>/feestrategy/strategyBase/editStrategyBase.action?id="+id;
}
function searchEntity(){
	$("#sform").submit();
}

function deleteOneEntity(id){
	var pars={
			"id":id
		};
	alertconfirm("确认删除选中的数据吗？",function (){
		showLoading();
		$.post('<%=path%>/feestrategy/strategyBase/abandonedStrategyBase.action',pars,r_delete,'json').error(requestError);
	});	
}
function r_delete(data){
	hideLoading();
	switch(data.result){
		case 0:
			alertok("删除成功！", function(){
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
</script>
</head>
<body class="SubPage">
<div class="container-fluid">
<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/feestrategy/strategyBase/searchStrategyBase.action">
	<input type="hidden" name="page.orderFlag" id="page.orderFlag" value="<ww:property value="page.orderFlag"/>">
	<input type="hidden" name="page.orderString" id="page.orderString" value="<ww:property value="page.orderString"/>">
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
			<div class="col-sm-2 col-xs-4">
				<a class="btn btn-block Button2"  onclick="detailEntity('');" target="_blank"><i class="fa fa-floppy-o"></i>添加</a>
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
			<td>最低消费</td>
			<td>超时罚金</td>
			<td> 操作 </td>
		</tr>
		<ww:iterator value="page.results" id="data" status="rl">
				<tr <ww:if test="#rl.even"> class="trs"</ww:if> style="font-size:12px;">
					<td align="center"><input type="checkbox" name="checkdel" value="<ww:property value="id"/> "> </td>
					<td align="center"><ww:property value="name" /></td>
					<td align="center"><ww:property value="#dictUtil.getCnNameByGroupCodeAndDictId('strategyBaseType',type)" /></td>
					<td align="center"><ww:property value="basePrice" /></td>
					<td align="center"><ww:property value="kmPrice" /></td>
					<td align="center"><ww:property value="minConsumption" /></td>
					<td align="center"><ww:property value="overtimePenalty" /></td>
					<td align="center">
						<div class="pan_btn3"  onclick="javascript:detailEntity('<ww:property value="id"/>');">详情</div>
						<div class="pan_btn4"  onclick="javascript:deleteOneEntity('<ww:property value="id"/>');">删除</div>
						<div class="pan_btn2"  onclick="showLogRecordForDialog('<ww:property value="id" />','<%=path %>');">记录</div>
					</td>
				</tr>
			
		</ww:iterator>
		<tr>
			<td align="right" colspan="8"><ww:property value="page.pageSplit" /></td>
		</tr>
	</table>
	</div>
</form>
</div>
</body>
</html>