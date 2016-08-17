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
$().ready(function (){
	var id = '<ww:property value="id" />';
	if (id == ""){
		$("#eform").attr('action','<%=path%>/functions/functionAdd.action');
	}else{
		$("#eform").attr('action','<%=path%>/functions/functionUpdate.action');	
	}
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"function.name": {
				required: true,
				maxlength : 60
			},
			"function.desc":{
				required: true,
				maxlength : 60
			}
		},
		messages: {
			"function.name": {
				required: "请输入功能名称！",
			},
			"function.desc":{
				required: "请输入功能描述！",
			}
		}
	});
});

function isValid(){
	if ($("#eform").valid()){
		return true;
	}else{
		return false;
	}
}
function getForm(){
	return $("#eform");
}
</script>
</head>
<body>
	<form name="eform" id="eform" method="post" action="">
		<input type="hidden" name="function.id" id="function.id"
			value="<ww:property value="function.id" />">
		<table >
			<tr >
				<th width=120>
					<span class="nNull">*</span>功能名称：
				</th>
				<td width=280>
					<input name="function.name" id="function.name" size=50 maxlength=300 value="<ww:property value="function.name" />" />
				</td>
			</tr>
			<tr>
				<th width=120>
					功能描述：
				</th>
				<td width=280>
					<input name="function.funDesc" id="function.funDesc" size=50 maxlength=300 value="<ww:property value="function.funDesc" />" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>