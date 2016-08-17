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
function saveEntity(id){
	
}

function r_savedata(data){

}
$().ready(function (){
	var id = '<ww:property value="id" />';
	if (id == ""){
		$("#eform").attr('action','<%=path%>/role/roleAdd.action');
	}else{
		$("#eform").attr('action','<%=path%>/role/roleUpdate.action');	
	}
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"rEntity.name": {
				required: true,
				maxlength : 60
			},
			"rEntity.desc":{
				required: false,
				maxlength : 60
			}
		},
		messages: {
			"rEntity.name": {
				required: "请输入角色名称！",
				maxlength: "名称过长，最大为30个字符！"
			},
			"rEntity.desc":{
				required: "请输入角色描述！",
				maxlength : "描述过长，最大为60个字符！"
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
<body style="height:80%">
	<div class="table_con" style="margin-right:50px;height: 200px;width:500px; ">
	<form name="eform" id="eform" method="post" action="">
				<input type="hidden" name="rEntity.id" id="rEntity.id"value="<ww:property value="rEntity.id" />">
		<table class="t1" width="450" border="0" cellpadding="0" cellspacing="0" >
		   <tr class="trr">
		    <th width="80"><span style="position:relative; top:-10px;">角色名称：</span>
		    </th>
		    <td><input type="text" name="rEntity.name" id="rEntity.name"  class="input_size" style=" position:relative;" value="<ww:property value="rEntity.name" />"/><span class="rr red">*</span>
		    </td>   
		  </tr> 
		  <tr class="trr">
		    <th width="80"><span>角色描述：</span>
		    </th>
		    <td><input type="text" name="rEntity.desc" id="rEntity.desc"  class="input_size" style=" position:relative;" value="<ww:property value="rEntity.desc" />"/>
		    </td>   
		  </tr> 
		</table>
	</form>
	</div> 
</body>
</html>