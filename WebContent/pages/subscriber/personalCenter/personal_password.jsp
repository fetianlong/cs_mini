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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本资料</title>
<link rel="stylesheet" type="text/css" href="common/portal/css/style.css"/>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="common/js/popup_layer.js"></script>
<script type="text/javascript" src="common/js/popup_layer.js"></script>
<style>
 *{ margin:0; padding:0px;}
 body{ margin:0; padding:0px; background-color:#fff;}
</style>

<script type="text/javascript">

$().ready(function() {
	 $("#subscriberform").validate({
		 errorClass : 'text-danger',
	      rules: {
			   "oldPassword": {
		              required: true,
		              rangelength:[8,20]
			   },
			   
	   			"subscriber.password": {
	   			    required: true,
	   			 	rangelength:[8,20],
	   			    isNotEqualOldPassword:true
	   			 
	   			 },
	   			"passwordConfirm": {
	   			    required: true,
	   			 	rangelength:[8,20],
	   			    equalTo: "#password"
	   			   }
	  	  },
	      messages: {
	  		   "oldPassword": {
	  				required: "请输入旧密码",
	  				rangelength: "密码长度只能在8-20位字符之间"
	  	         },
	  	       
	  	       "subscriber.password": {
	  	            required: "请输入新密码",
	  	         	rangelength: "密码长度只能在8-20位字符之间"
	  	          },
	  	        "passwordConfirm": {
	  	           required: "请输入确认新密码",
	  	         	rangelength: "密码长度只能在8-20位字符之间",
	  	           equalTo: "两次输入密码不一致"
	  	          }
			 },
			 errorPlacement: function(error, element) {                     
				 error.appendTo( element.parent().next() );                          
			 },
			 success: function (label) {
	                label.addClass("checked");
	         },highlight: function(element, errorClass) {
					$(element).parent().next().find("." + errorClass).removeClass("checked");
			 }
	    });
	});


jQuery.validator.addMethod("isNotEqualOldPassword", function(value, element) {   
	  
	   return $("#oldPassword").val()!=$("#password").val();
	}, "新旧密码不能相同!");




function formSubmit(){

	if($("#subscriberform").valid()){
		$.ajax({
			type: "POST",
		    url: "subscriber/changePassword.action",
		    dataType:'json',
		    data:$('#subscriberform').serialize(),
		    success: function(data) {
				if(0==data.result){
					$("#oldPassword").val("");
					$("#password").val("");
					$("#passwordConfirm").val("");
					window.parent.parent.alertMsg("提示","修改成功!");
			    }else{
			    	window.parent.parent.alertMsg("提示",data.msg);
			    }
		    }
		});
	}else{
		window.parent.parent.alertMsg("","请先输入必填项。");
	}
	
}
</script>

</head>

<body>
<form name="subscriberform" id="subscriberform" method="post" action="">
<table class="table2" width="800" border="0" cellpadding="0" cellspacing="0" style="margin-top:50px; ">
              <tr>
                <th width="220"><span class="red">*&nbsp;</span>旧密码：</th>
                <td width="400"><input class="input_size" type="password" id="oldPassword" name="oldPassword" maxlength="20" autocomplete="off"  placeholder="请输入旧密码" style="width:320px; height:25px;"></td>
                <td></td>
              </tr>
              <tr>
                <th ><span class="red">*&nbsp;</span>新密码：</th>
                <td>
                	<input class="input_size" type="password" id="password" name="subscriber.password"  autocomplete="off" id="password" maxlength="20" placeholder="请输入新密码"  style="width:320px; height:25px;"><span class="cw"></span>
                </td>
                 <td></td>
              </tr>
               <tr>
               	<th><span class="red">*&nbsp;</span>确认新密码：</th>
                <td>
                	<input  type="password" id="passwordConfirm" name="passwordConfirm" autocomplete="off" maxlength="20"  placeholder="请再次输入新密码" class="input_size" style="width:320px; height:25px;"><span class="cw"></span>
                </td>
                 <td></td>
              </tr>
              
              <tr><td colspan="3" height="30"></td></tr>
              <tr>
                <th></th>
                <td><input class="input_sub" type="button" onclick="formSubmit()" value="保  存" style=" width:282px; height:39px; color:#fff; font-size:16px; font-weight:bold; line-height:39px;background:url(common/portal/images/text_btn.png) no-repeat; border:none; margin-left:20px;cursor:pointer;" /></td>
                <td></td>
              </tr>
         </table>
</form>
</body>
</html>
