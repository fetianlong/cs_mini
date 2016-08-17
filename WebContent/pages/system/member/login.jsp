<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>登录</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="common/css/common.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/jquery.validate.min.js"></script>


<script type="text/javascript">


$().ready(function() {
	 $("#memberform").validate({
	      rules: {
			   "member.phoneNo": {
		                      required: true,
		                      rangelength:[11,11] 
			   },
			   "code": {
                   required: true,
                   rangelength:[4,4]
	   			},
	   			"member.password": {
	   			    required: true
	   			 }
	  	  },
	      messages: {
	  		   "member.phoneNo": {
	  					required: "请输入手机号",
	  					rangelength:"请输入11位手机号码"
	  	         },"code": {
	        	   required: "请输入验证码",
	  	        	  rangelength:"请输入4位验证码"
	  	         },
	  	       	"member.password": {
	  	           required: "请输入密码"
	  	          }
			 }
	    });
	});

jQuery.validator.addMethod("isPhone", function(value, element) {   
   var tel = /^1\d{10}$/;
   return this.optional(element) || (tel.test(value));
}, "请输入正确的手机号");




function formSubmit(){

	if($("#memberform").valid()){
			$.ajax({
				type: "POST",
			    url: "member/loginAjax.action",
			    data:$('#memberform').serialize(),
			    dataType:'json',
			    cache:false,
		        async:false,
			    success: function(data) {
			    	if(0==data.result){
			    		  window.location.href="member/login.action";
				    }else{
			    		$("#retMsg").html(data.msg);
				    }
			    }
			});
	}
}




	
</script>
</head>
<body>


	<form name="memberform" id="memberform" method="post" action="">
		
		<table >
			<tr >
				<th width=120>
					手机号：
				</th>
				<td width=280>
					<input name="member.phoneNo" autocomplete="off" id="phoneNo" size=20 maxlength=11 />
				</td>
			</tr>
			<tr>
				<th width=120>
					密&nbsp;&nbsp;码：
				</th>
				<td width=280>
					<input type="password" name="member.password" autocomplete="off" id="password" size=20 maxlength=20   />
				</td>
			</tr>
			<tr>
				
				<th >
					验证码:
				</th>
				<td>
					<input type="text" id="code" name="code" size=7 maxlength=4  class="yzm fl">
					
		          <img style="width:80px;height:25px;"  src="/carsharing/common/css/images/login/yanzm.png">
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">
					<input id="btn_login"  type="button"  onclick="formSubmit()"  value="登录">
					 <font color="red" id="retMsg"></font>
				</td>
				
			</tr>
		</table>
	</form>
</body>
</html>