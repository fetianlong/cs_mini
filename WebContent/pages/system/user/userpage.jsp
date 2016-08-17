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
			"userDoc": {
				required: true
			}
		},
		messages: {
			"userDoc": {
				required: "请选择用户！"
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
var userEditDoc = W;
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
	if(isValid()){
		var str=$("input[name='userDoc']:checked").val();
		if(typeof(str)=="undefined"){
			alert("请选择用户");
		}
		if(str!=""){
			var userDoc=str.split(",");
			userEditDoc.$("#userId").val(userDoc[0]);
			userEditDoc.$("#userName").val(userDoc[1]);
		}
	}else{
		return false;
	}
};

function confirmTr(tr){
	var str = $(tr).find("input[name='userDoc']").val();
	if(str!=""){
		var userDoc=str.split(",");
		userEditDoc.$("#userId").val(userDoc[0]);
		userEditDoc.$("#userName").val(userDoc[1]);
	}else{
		return false;
	}
	api.close();
}
</script>
</head>
<body class="SubPage">
<div class="container-fluid">
		<form class="form-horizontal" name="sform" id="sform" method="post" action="<%=path%>/user/userSearch.action">
			<input type="hidden" name="page.orderFlag" id="page.orderFlag"
					value="<ww:property value="page.orderFlag"/>">
			<input type="hidden" name="page.orderString" id="page.orderString"
					value="<ww:property value="page.orderString"/>">
			<input type="hidden" name="state" value="<ww:property value="state"/>">
			
			<div class="ControlBlock">
		<div class="row SelectBlock">
			<div class="col-xs-6">
				<div class="form-group">
					<label for="sEntity.name" class="col-xs-4 control-label">用户名</label>
					<div class="col-xs-8">
						<input class="form-control"name="sEntity.name" id="sEntity.name" type="text" value="<ww:property value="sEntity.name"/>" />
					</div>
				</div>
			</div>
		</div>
		<div class="row SubmitButtonBlock">
			<div class="col-xs-4 col-xs-offset-4">
				<a class="btn btn-block Button1"  onclick="searchEntity();" target="_blank"><i class="fa fa-search"></i>查询</a>
			</div>
		</div>
	</div>

<div class="row TableBlock" id="userListShow">
					<table class="table table-striped table-bordered table-condensed">
						<tr class="ths">
							<td height="28" width="40" align="center" nowrap></td>
							
							<td>
								<a href="javascript:SetOrder('name')">用户名</a>
							</td>
							<td>
								<a href="javascript:SetOrder('password')">密码</a>
							</td>
						</tr>
				
						<ww:iterator value="page.results" id="data" status="rl">
							<tr  ondblclick="confirmTr(this);"
								 <ww:if test="#rl.even"> class="trs"</ww:if> >
							<td align="center">
								<input type="radio" name="userDoc" value="<ww:property value="id" />,<ww:property value="name" />"/>
							</td>
								
								<td align="left">
									<ww:property value="name" />
								</td>
								<td align="left">
									<ww:property value="password" />
								</td>
							</tr>
						</ww:iterator>
						<tr style="background-color: #fff;height: 30px;">
							<td align="center" colspan="4">
								<ww:property value="page.pageSplit" />	
							</td>
						</tr>
					</table>
 			</div>
		</form>
</div>
</body>
</html>