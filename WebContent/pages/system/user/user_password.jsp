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

	$('#eform').validate({
		errorClass : 'text-danger',
		errorElement : 'p', 
		rules: {
			"oldPassword": {
				required: true,
				rangelength:[6,20]
			},
			"newPassword":{
				required: true,
				rangelength:[6,20],
				isNotEqualOldPassword:true,
			},
			"passwordConfirm":{
				required: true,
				rangelength:[6,20],
				equalTo: "#newPassword"
			}
		},
		messages: {
			"oldPassword": {
				required: "请输入旧密码！",
				rangelength: "密码长度只能在6-20位字符之间"
			},
			"newPassword":{
				required: "请输入新密码！",
				rangelength: "密码长度只能在6-20位字符之间",
				
			},
			"passwordConfirm":{
				required: "请输入确认密码！",
				rangelength: "密码长度只能在6-20位字符之间",
				equalTo:"新密码不一致"
				
			}
		}
	});
});
jQuery.validator.addMethod("isNotEqualOldPassword", function(value, element) {   
	   return $("#oldPassword").val()!=$("#newPassword").val();
	}, "新旧密码不能相同!");
	
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
	<div class="table_con tc  tanchuang" >
		<form name="eform" id="eform" method="post" action="<%=path %>/user/changePassword.action">
			
			<table class="t1" >
				 <tr class="trr">
		   		     <td  class="zuo"><span class="rr red">*</span><span >旧密码：</span>
					</td>
					<td class="you">
						<input type="password" name="oldPassword" id="oldPassword"  class="input_size"  value="" />
		    		</td> 
				</tr>
				 <tr class="trr">
		   		     <td  class="zuo"><span class="rr red">*</span> <span >新密码：</span>
					</td>
					<td class="you">
						<input type="password" name="newPassword" id="newPassword"  class="input_size"  value=""/>
		    		</td> 
				</tr>
				 <tr class="trr">
		   		     <td class="zuo"><span class="rr red">*</span><span >确认密码：</span>
					</td>
					<td class="you">
						<input type="password" name="passwordConfirm" id="passwordConfirm"  class="input_size"  value=""/>
		    		</td> 
				</tr>
			</table>
		</form>
	</div>
</body>
</html>