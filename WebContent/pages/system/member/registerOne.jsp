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
<title>用户注册</title>
<link href="common/css/common.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/jquery.validate.min.js"></script>
<script type="text/javascript">
var InterValObj; //timer变量，控制时间
var count = 10; //间隔函数，1秒执行
var curCount;//当前剩余秒数


function sendPhoneCode(){
	if($("#phoneNo").val()=="" ||$("#phoneNo").val()==null){
		alert("请输入手机号!");
		return;
	}

 		curCount = count;
	//设置button效果，开始计时
	     $("#btnSendCode").attr("disabled", "true");
	     $("#btnSendCode").val("请在" + curCount + "秒内输入验证码");
	     InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
	
	

	     $.ajax({
	         url :'member/sendPhoneVerificationCode.action',
	         type:'post',
	         cache:false,
	         async:false,
	         data:{'member.phoneNo':$("#phoneNo").val()},
	         dataType:'json',
	         success:function(data) {
	 	       alert(data.result);
	 			
	 	    }
	     });
	
	
}

function SetRemainTime() {
    if (curCount == 0) {                
        window.clearInterval(InterValObj);//停止计时器
        $("#btnSendCode").removeAttr("disabled");//启用按钮
        $("#btnSendCode").val("重新发送验证码");
    }
    else {
        curCount--;
        $("#btnSendCode").val("请在" + curCount + "秒内输入验证码");
    }
}



$().ready(function() {
	 $("#memberform").validate({
	      rules: {
			   "member.phoneNo": {
		                      required: true,
		                      rangelength:[11,11],
		                      isPhone:true
			   },
			   "code": {
                   required: true,
                   rangelength:[4,4]
	   			},
	   			"member.password": {
	   			    required: true,
	   			    minlength: 5
	   			 },
	   			"confirm_password": {
	   			    required: true,
	   			    minlength: 5,
	   			    equalTo: "#password"
	   			   },
	   			"agreementCheckbox":{
	   				required: true
	   	   		}
			   
	  	  },
	      messages: {
	  		   "member.phoneNo": {
	  					required: "请输入手机号",
	  					rangelength:"请输入11位手机号码"		
	  	         },
	  	       "code": {
	  	        	   required: "请输入验证码",
	  	        	  rangelength:"请输入4位验证码"
	  	         },
	  	       "member.password": {
	  	           required: "请输入密码",
	  	           minlength: jQuery.format("密码不能小于{0}个字 符")
	  	          },
	  	        "confirm_password": {
	  	           required: "请输入确认密码",
	  	           minlength: "确认密码不能小于5个字符",
	  	           equalTo: "两次输入密码不一致不一致"
	  	          },
	  	        "agreementCheckbox":{
	  	        	required: "请同意租车协议"
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
			    url: "member/registerOne.action",
			    dataType:'json',
			    data:$('#memberform').serialize(),
			    success: function(data) {
					if(0==data.result){
						//$("#retMsg").html(data.msg);
			    		  window.location.href="pages/system/member/registerTwo.jsp";
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
用户注册---第一步
<font color="red"><ww:property value="retMsg" /></font>
<form name="memberform" id="memberform" method="post" action="">
<table>
	<tr>
		<td>账户名:</td>
		<td><input  type="text" name="member.phoneNo" id="phoneNo" maxlength="11" placeholder="请输入手机号"/></td>
		<td></td>
	</tr>
	<tr>
		<td>验证码:</td>
		<td>
			<input  type="text" id="verifyCode" name="code" size="5" maxlength="4" value=""/>
			<input type="button" id="btnSendCode" onclick="sendPhoneCode()" value="发送验证码"/>
		</td>
		<td></td>
	</tr>
	
	<tr>
		<td>密码:</td>
		<td><input  type="password" name="member.password" autocomplete="off" id="password" maxlength="20" placeholder="请输入密码" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>确认密码:</td>
		<td><input  type="password" id="passwordConfirm" name="confirm_password" autocomplete="off" maxlength="20"  placeholder="请输入确认密码" value=""/></td>
		<td></td>
	</tr>
	<tr>
		<td colspan="3">
			<input name="agreementCheckbox" name="agreementCheckbox" type="checkbox" >同意<a href="#">租车协议</a>
		</td>
	</tr>
	<tr>
	
		<td colspan="3" align="center">
		<input type="button" onclick="formSubmit()" value="注册"> 
		<font color="red" id="retMsg"></font>	
		
		</td>
	 
	</tr>
</table>
</form>
</body>
</html>