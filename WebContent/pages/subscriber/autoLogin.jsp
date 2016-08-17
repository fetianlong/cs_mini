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
<link rel="shortcut icon" href="<%=path%>/common/css/images/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" type="text/css" href="common/portal/css/style.css"/>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/popup_layer.js"></script>


<script type="text/javascript">
function login(){
	var tel = /^0?(13|15|18|14|17)[0-9]{9}$/;
	if($("#phoneNo").val()==null||$("#phoneNo").val()==""){
		$("#phoneNo").focus();
		$("#msg").html("请输入手机号");
		return;
	}else{
		$("#msg").html("");
	}
	if(!tel.test($("#phoneNo").val())){
		$("#phoneNo").focus();
		$("#msg").html("请输入正确手机号");
		return;
	}else{
		$("#msg").html("");
	}

	if($("#password").val()==null||$("#password").val()==""){
		$("#password").focus();
		$("#msg").html("请输入密码");
		return;
	}else{
		$("#msg").html("");
	}

	/*if($("#code").val()==null||$("#code").val()==""){
		$("#code").focus();
		alert("请输入验证码");
		return;
	}
*/
	
	$.ajax({
		type: "POST",
	    url: "subscriber/doLogin.action",
	    dataType:'json',
	    data:{"subscriber.phoneNo":$("#phoneNo").val(),"subscriber.password":$("#password").val(),"code":$("#code").val()},
	    success: function(data) {
	     
			if(0==data.result){
				$("#msg").html("登录成功");
				window.location.href="<%=path %>/subscriber/centerIndex.action";
		    }else{
	    		$("#msg").html(data.msg);
		    }
	    }
	});
}

$().ready(function (){
	$("#password").keyup(function(e){
		 var e = e || event,
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
		  $("#loginBtn").trigger("click");
		 }
		});

	$("#phoneNo").keyup(function(e){
		 var e = e || event,
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			$("#password").focus();
		 }
		});
});


	</script>
</head>
<body style="height:100%; position:relative;">
<ww:include page="/pages/subscriber/header.jsp"></ww:include>
<div style="position:absolute; top:100px; width:100%; height:auto;   margin-bottom:0px; z-index:-1">
<img  src="common/portal/images/dl_bg.jpg" height="100%" width="100%" />
</div>
<div class="dlk">
   <img class="bg_img_small_1" src="common/portal/images/che1.png" width="60"/>
   <img class="bg_img_small_2" src="common/portal/images/che2.png" width="60"/>
   <h1>会员登录</h1>  
        <div class="con fl">
          <i class="user_img fl"></i>
          <input  class="user fl" type="text" name="subscriber.phoneNo" id="phoneNo" value="18612119860"  maxlength=11  placeholder="请输入用户名" />
        
       </div>
       <div class="con fl">
          <i class="psd_img fl"></i>
          <input class="user fl" type="password" name="subscriber.password" id="password" value="qwert123"  maxlength=20   placeholder="请输入密码">
         
       </div>
       <div class="con2 fl">
          <input class="yzm1 fl" id="code" name="code" maxlength=5  type="text" placeholder="请输入验证码">
          <img class="yzm1_img fl" src="common/portal/images/yzm.jpg" height="28">
          
       </div>
       <div class="cwts fl" id="msg"></div>
      <div class="submit fl" id="loginBtn" onclick="login()"  ><span>登</span>录</div>
      <div class="wenz" style="display:none;" >正在登录...</div>
</div>

</body>
</html>