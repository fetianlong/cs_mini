<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="<%=path%>/common/css/common.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=path%>/common/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path%>/common/js/lhgdialog4/lhgdialog/lhgdialog.min.js?skin=idialog"></script>
<script type="text/javascript">
function saveEntity(id){
	
}

function r_savedata(data){

}
$().ready(function (){
	var id = '<ww:property value="id" />';
	if (id == ""){
		$("#eform").attr('action','<%=path%>/user/userAdd.action');
	}else{
		$("#eform").attr('action','<%=path%>/user/userUpdate.action');	
	}
	$('#eform').validate({
		rules: {
			"eEntity.name": {
				required: true,
				maxlength : 60
			},
			"eEntity.password":{
				required: true,
				maxlength : 60
			}
		},
		messages: {
			"eEntity.name": {
				required: "请输入用户名称！",
				maxlength: "用户名称过长，最大为60个字符！"
			},
			"eEntity.password":{
				required: "请输入密码！",
				maxlength : "密码过长，最大为60个字符！"
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
		<input type="hidden" name="eEntity.id" id="eEntity.id"
			value="<ww:property value="eEntity.id" />">
		<table >
			<tr >
				<th width=120>
					<span class="nNull">*</span>用户名称：
				</th>
				<td width=280>
					<input name="eEntity.name" id="eEntity.name" size=50 maxlength=300 value="<ww:property value="eEntity.name" />" />
				</td>
			</tr>
			<tr>
				<th width=120>
					密码：
				</th>
				<td width=280>
					<input name="eEntity.password" id="eEntity.password" size=50 maxlength=300 value="<ww:property value="eEntity.password" />" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>