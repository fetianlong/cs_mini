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
		$("#eform").attr('action','<%=path%>/user/userAdd.action');
	}else{
		$("#eform").attr('action','<%=path%>/user/userUpdate.action');	
	}
	$('#eform').validate({
		errorClass : 'text-danger',
		rules: {
			"eEntity.name": {
				required: true,
				maxlength : 60
			},
			"eEntity.password":{
				required: true,
				maxlength : 60
			},
			"eEntity.loginName":{
				required: true,
				maxlength : 60
			}
		},
		messages: {
			"eEntity.name": {
				required: "请输入真实名称！",
				maxlength: "真实名称过长，最大为60个字符！"
			},
			"eEntity.password":{
				required: "请输入密码！",
				maxlength : "密码过长，最大为60个字符！"
			},"eEntity.loginName":{
				required: "请输入登录名！",
				maxlength: "登录名称过长，最大为60个字符！"
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
<body >
	<div class="table_con tanchuang" >
		<form name="eform" id="eform" method="post" action="">
			<input type="hidden" name="eEntity.id" id="eEntity.id"
				value="<ww:property value="eEntity.id" />">
			<table class="t1" >
				<tr class="trr" >
						<th >
							<span >登录名：</span>
						</th>
						<td><input type="text" name="eEntity.loginName" id="eEntity.loginName"  class="input_size"  value="<ww:property value="eEntity.loginName" />"/><span class="rr red">*</span>
			    		</td> 
					</tr>
				<tr class="trr" >
					<th >
						<span >真实姓名：</span>
					</th>
					<td><input type="text" name="eEntity.name" id="eEntity.name"  class="input_size"  value="<ww:property value="eEntity.name" />"/><span class="rr red">*</span>
		    		</td> 
				</tr>
				<tr class="trr" >
					<th >
						<span >手机号：</span>
					</th>
					<td><input type="text" name="eEntity.phoneNo" id="eEntity.phoneNo"  class="input_size"  value="<ww:property value="eEntity.phoneNo" />"/>
		    		</td> 
				</tr>
				
				
				<tr class="trr" >
					<th >
						<span >角色：</span>
					</th>
					<td>
						<select name="eEntity.groupId" style="width:60%">
							<option value=""></option>
							<ww:iterator value="#request.groupList">
								
								<option value='<ww:property value="groupId"/>'  <ww:if test="eEntity.groupId==groupId">selected="selected"</ww:if> ><ww:property value="groupName"/></option>
							</ww:iterator>  
						</select>
					</td>
				</tr>
				
			</table>
		</form>
	</div>
</body>
</html>