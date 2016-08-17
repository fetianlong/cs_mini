<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>登录</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>


<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script> 
<link rel=stylesheet href="<%=path %>/mobile/common/css/login.css">



<script>
function login(){
	var tel = /^0?(13|15|18|14|17)[0-9]{9}$/;
	if($("#cellphone").val()==null||$("#cellphone").val()==""){
		$("#cellphone").focus();
		$("#phoneMsg").html("请输入手机号");
		$("#phoneMsg").show();
		return;
	}else{
		$("#phoneMsg").html("");
		$("#phoneMsg").hide();
	}
	if(!tel.test($("#cellphone").val())){
		$("#cellphone").focus();
		$("#phoneMsg").html("请输入正确手机号");
		$("#phoneMsg").show();
		return;
	}else{
		$("#phoneMsg").html("");
		$("#phoneMsg").hide();
	}

	if($("#smsCode").val()==null||$("#smsCode").val()==""){
		$("#smsCode").focus();
		$("#smsCodeMsg").html("请输入验证码");
		$("#smsCodeMsg").show();
		return;
	}else if($("#smsCode").val().length!=4){
		$("#smsCode").focus();
		$("#smsCodeMsg").html("请输入正确的验证码");
		$("#smsCodeMsg").show();
	}else{
		$("#smsCodeMsg").html("");
		$("#smsCodeMsg").hide();
	}

	$.ajax({
		type: "POST",
	    url: "<%=path %>/mobile/doLogin.action",
	    dataType:'json',
	    data:$('#loginForm').serialize(),
	    success: function(data) {
	    	if(0==data.result){
				
				if(data.login_referrer !=""){
					window.location.href=data.login_referrer;
				}else{
					window.location.href="<%=path %>/mobile/toBookCar.action";
				}
				
		    }else{
	    		$("#smsCodeMsg").html(data.msg);
	    		$("#smsCodeMsg").show()
		    }
	    },
		error: function(){
			Alert("暂时无法登录，请稍后再试");	
		}
	});
};

$(function(){
	$("#smsCode").keyup(function(e){
		 var e = e || event,
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			 login();
		 }
	});
	

	$("#cellphone").keyup(function(e){
		 var e = e || event,
		 keycode = e.which || e.keyCode;
		 if (keycode==13) {
			$("#smsCode").focus();
		 }
		 
		  if($('#cellphone').val().length==11){
			 
			  $('#newBtnSendCode').removeAttr("disabled"); 
		  }else{
			  $("#newBtnSendCode").attr("disabled","disabled")
		  }
	});
	
	 
});
</script>

</head>

<body>
<form  name="loginForm" id="loginForm">
<div class="container-fluid">
  <div class="row">
    <img class="img-responsive center-block LogoImg" src="<%=path %>/mobile/common/images/login/Logo.png">
	<div class="col-md-4 col-md-offset-4">
		<div class="Border">
			<input type="text" name="subscriber.phoneNo" id="cellphone" maxlength="11" placeholder="手机号">
            <div class="row CodeBlock">
            	<div class="col-xs-7">
                	<input class="CodeInput" type="text" name="subscriber.smsCode" id="smsCode" maxlength="4" placeholder="验证码">
                </div>
                <div class="col-xs-5">
					<button class="btn-block CodeButton" type="button" id="newBtnSendCode" onclick="sendNewPhoneCode()" disabled="disabled">获取验证码</button>
                </div>
            </div>
			<div class="btn-group ButtonGroup" role="group">
				<!--<a class="btn RegButton" href="<%=path %>/mobile/subscriber/register.action">注&nbsp;册</a>-->
				<button type="button" onclick="login()" class="btn SubmitButton">登&nbsp;录</button>
			</div>
			
		</div>
		
		

		   <div class="TipBlock">
			<ww:if test='getSession().getAttribute("wechatUserInfo")!=null'>
			<div class="LoginWeChat">
				 <label class="checkbox WeChat" for="WeChat">
				  <input type="checkbox" value="1" id="WeChat" name="WeChat" checked="checked">
				  关联您的微信账号
				</label> 
			</div>
			</ww:if>
			<p id="phoneMsg" style="display: none;" class="help-block ErrorTip"></p>
			<p id="smsCodeMsg" style="display: none;" class="help-block ErrorTip"></p>
		</div>
	</div>
	
  </div>
</div>
</form>
</body>
<script type="text/javascript">
	var newInterValObj; //timer变量，控制时间
	var newCount = 120; //间隔函数，1秒执行
	var newCurCount;//当前剩余秒数

	var newCanSendSMS=true;


	function newSetRemainTime() {
	    if (newCurCount == 0) {                
	        window.clearInterval(newInterValObj);//停止计时器
	        $("#newBtnSendCode").html("重新获取");
	        $('#newBtnSendCode').removeAttr("disabled"); 
	        newCanSendSMS=true;
	    }
	    else {
	    	newCurCount--;
	        $("#newBtnSendCode").html("重新获取("+newCurCount+")");
	        $("#newBtnSendCode").attr("disabled","disabled")
	        newCanSendSMS=false;
	    }
	}


	function sendNewPhoneCode(){
		
		var tel = /^0?(13|15|18|14|17)[0-9]{9}$/;
		if($("#cellphone").val()==null||$("#cellphone").val()==""){
			$("#cellphone").focus();
			$("#phoneMsg").html("请输入手机号");
			$("#phoneMsg").show();
			return;
		}else{
			$("#phoneMsg").html("");
			$("#phoneMsg").hide();
		}
		if(!tel.test($("#cellphone").val())){
			$("#cellphone").focus();
			$("#phoneMsg").html("请输入正确手机号");
			$("#phoneMsg").show();
			return;
		}else{
			$("#phoneMsg").html("");
			$("#phoneMsg").hide();
		}
		if(!newCanSendSMS){return;}
		

		newCurCount = newCount;
	    $("#newBtnSendCode").html( "重新获取("+newCurCount+")");
	    $("#newBtnSendCode").attr("disabled","disabled")
	    newInterValObj = window.setInterval(newSetRemainTime, 1000); //启动计时器，1秒执行一次
	    newCanSendSMS=false;
		
		     $.ajax({
		         url :'<%=path %>/mobile/sendLoginCode.action',
		         type:'post',

		         data:{'subscriber.phoneNo':$("#cellphone").val()},
		         dataType:'json',
		         success:function(data) {
		        		if(1==data.result){
		        			Alert(data.msg)
				    }
		         }
			
		     });
	}
	
</script>
</html>
