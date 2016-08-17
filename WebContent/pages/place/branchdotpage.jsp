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

function searchEntity(){
	$("#sform").submit();
}
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
		var str=$("input[name='branchDotDoc']:checked").val();
		if(typeof(str)=="undefined" || str == null || str == ''){
			alert("请选择网点");
			return false;
		}
		if(str!=""){
			var branchDotDoc=str.split(",");
		    relationEditDoc.$("#branchDotId").val(branchDotDoc[0]);
			relationEditDoc.$("#branchDotName").val(branchDotDoc[1]);
		}
};

function confirmTr(tr){
	var str = $(tr).find("input[name='branchDotDoc']").val();
	if(str!=""){
		var branchDotDoc=str.split(",");
	    relationEditDoc.$("#branchDotId").val(branchDotDoc[0]);
		relationEditDoc.$("#branchDotName").val(branchDotDoc[1]);
		
	}else{
		return false;
	}
	api.close();
}
</script>
<style type="text/css">
.SubPage .SubmitButtonBlock .Button4 {
	background: rgb(145, 137, 53);
}
</style>
</head>
<body class="SubPage">
<div class="container-fluid">
			<form class="form-horizontal" name="sform" id="sform" method="post" action="">
				<input type="hidden" name="page.orderFlag" id="page.orderFlag"
						value="<ww:property value="page.orderFlag"/>">
				<input type="hidden" name="page.orderString" id="page.orderString"
						value="<ww:property value="page.orderString"/>">

<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-sm-4 col-xs-6">
				<div class="form-group">
					<label for="branchDot.name" class="col-xs-4 control-label">网点名称</label>
					<div class="col-xs-8">
						<input class="form-control" name="branchDot.name" id="branchDot.name" type="text" value="<ww:property value="branchDot.name"/>">
					</div>
				</div>
				<div class="form-group">
					<label for="sfqy" class="col-xs-4 control-label">是否启用</label>
					<div class="col-xs-8">
						<select class="form-control"   name="branchDot.isActive" id="sfqy">
							<option value="">全部</option>
							<option value="1" <ww:if test="branchDot.isActive==1">selected=true</ww:if> >启用</option>	
							<option value="0" <ww:if test="branchDot.isActive==0">selected=true</ww:if>>未启用</option>	
						</select>				
					</div>
				</div>
			</div>
			
			<div class="col-sm-4 col-xs-6">
				<div class="form-group">
					<label for="xzqh" class="col-xs-4 control-label">所属行政区划</label>
					<div class="col-xs-8">
						<select class="form-control"   name="branchDot.areaId" id="xzqh">
							<option value="">全部</option>
							<ww:iterator value="getAreas()" id="data" status="rl">
								<option value="<ww:property value="id" />"  <ww:if test="branchDot.areaId==id">selected=true</ww:if> ><ww:property value="name" /></option>	
							</ww:iterator>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="branchDot.address" class="col-xs-4 control-label">地址</label>
					<div class="col-xs-8">
						<input class="form-control" name="branchDot.address" id="branchDot.address" type="text" value="<ww:property value="branchDot.address"/>">
					</div>
				</div>
			</div>
		</div>
		<div class="row SubmitButtonBlock">
			<div class="col-sm-2 col-xs-3 col-sm-offset-2">
				<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
			</div>
		</div>
	</div>

<div id="branchDotListShowDiv" class="row TableBlock">
					<table class="table table-striped table-bordered table-condensed">
						<tr class="ths" id="tab_bg_cl">
							<td>
							</td>
							
							<td >
								<a href="javascript:SetOrder('code')">编码<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td >
								<a href="javascript:SetOrder('name')">名称<img src="<%=path%>/admin/common/images/main/paixu.png"/></a>
							</td>
							<td >
								地址
							</td>
							<td >
								是否启用
							</td>
							<td >
								行政区划
							</td>
							<td>还车网点</td>
							<td>车位数量</td>
						</tr>
				
						<ww:iterator value="page.results" id="data" status="rl">
							<tr <ww:if test="#rl.even"> class="trs"</ww:if> ondblclick="confirmTr(this)" style="font-size:12px;" >
								<td align="center">
									<input type="radio" name="branchDotDoc" 
											value="<ww:property value="id" />,<ww:property value="name" />" />
								</td>
								<td align="left">
									<ww:property value="code" />
								</td>
								<td align="left">
									<ww:property value="name" />
								</td>
								<td align="left">
									<ww:property value="address" />
								</td>
								<td align="center">
									<ww:if test="isActive==1">是</ww:if>
									<ww:if test="isActive==0">否</ww:if>
								</td>
								<td align="left">
									<ww:property value="getAreaName(areaId)" />
								</td>
								<td align="left">
									<ww:if test="returnbackDotName == null || returnbackDotName == ''">本地</ww:if>
									<ww:else><ww:property value="returnbackDotName"/></ww:else>
								</td>
								<td align="right">
									<ww:property value="totalParkingPlace" />
								</td>
							</tr>
						</ww:iterator>
						<tr>
							<td align="right" colspan="10">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
				</div>
			</form>
</div>
</body>

</html>