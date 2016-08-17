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

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户注册</title>

<link rel="shortcut icon" href="<%=path%>/common/css/images/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" type="text/css" href="<%=path %>/common/portal/css/style.css"/>
<script type="text/javascript" src="<%=path %>/common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=path %>/common/js/popup_layer.js"></script>


<style type="text/css">
.strengthA b  {
    background: url("<%=path %>/common/portal/images/pwdstrength.gif") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
}
.strengthB b {       
    background: url("<%=path %>/common/portal/images/pwdstrength.gif") no-repeat scroll 0 -13px rgba(0, 0, 0, 0);
}
.strengthC b {
    background: url("<%=path %>/common/portal/images/pwdstrength.gif") no-repeat scroll 0 -26px rgba(0, 0, 0, 0);
}
<!-- border: 1px solid red;-->
input.error {  }
label.error {
  padding-left: 0px;
  padding-bottom: 2px;
  color: #f53b3b;
  font-size:12px;
}
label.checked {
  background:url("<%=path %>/common/portal/images/sucess.png") no-repeat 0px 0px;
  padding-left: 16px;
  padding-bottom: 2px;
}

</style>
<script type="text/javascript">



var InterValObj; //timer变量，控制时间
var count = 120; //间隔函数，1秒执行
var curCount;//当前剩余秒数

var canSendSMS=true;
var isEnablePhone=false;
var isEnablePhoneMsg="";

function SetRemainTime() {
    if (curCount == 0) {                
        window.clearInterval(InterValObj);//停止计时器
        $("#btnSendCode").html("重新获取短信验证码");
        canSendSMS=true;
    }
    else {
        curCount--;
        $("#btnSendCode").html(curCount + "秒后重新获取");
        canSendSMS=false;
    }
}
var tel = /^0?(13|15|18|14|17)[0-9]{9}$/;

function sendPhoneCode(){
	if(!canSendSMS){return;}
	if($("#phoneNo").val()=="" ||$("#phoneNo").val()==null){
		$("#phoneNo").focus();
		alertMsg("提示","请输入手机号!");
		return;
	}
	if(!tel.test($("#phoneNo").val())){
		$("#phoneNo").focus();
		alertMsg("提示","手机号格式不正确!");
		return;
	}

	if(!isEnablePhone){
		alertMsg("提示",isEnablePhoneMsg);
		return ;
	}
	 curCount = count;
     $("#btnSendCode").html( curCount + "秒后重新获取");
     InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
     canSendSMS=false;
	
	     $.ajax({
	         url :'<%=path %>/subscriber/sendPhoneVerificationCode.action',
	         type:'post',
	         cache:false,
	         async:false,
	         data:{'subscriber.phoneNo':$("#phoneNo").val()},
	         dataType:'json',
	         success:function(data) {	
	 	    }
	     });
}


function phoneKeyup() {
    var mobile = $("#phoneNo").val();
    var mobileLength=mobile.length;
    if(mobileLength != 11){
    	return;
    }
    if (!tel.test(mobile)) {
        return;
    }
    // 检测手机号码是否存在
    $.getJSON("<%=path %>/subscriber/isPhoneEngaged.action?subscriber.phoneNo=" + mobile + "&r=" + Math.random(),
    function(result) {
        mobileResult = result.result;
        if (mobileResult == 0){
        	isEnablePhone=true;
        }else{
        	isEnablePhoneMsg=result.msg;
        } 
    });
}

$().ready(function() {
	 $("#subscriberform").validate({
	      rules: {
			   "subscriber.phoneNo": {
		                      required: true,
		                      rangelength:[11,11],
		                      isPhone:true
			   },
			   "code": {
                  required: true,
                  rangelength:[4,4]
	   			},
	   			"subscriber.password": {
	   			    required: true,
	   			 	rangelength:[8,20],
	   			 	isPhoneStrength:true
	   			 	
	   			 },
	   			"passwordConfirm": {
	   			    required: true,
	   			 	rangelength:[8,20],
	   			    equalTo: "#password"
	   			   },
	   			"agreementCheckbox":{
	   				required: true
	   	   		}
			   
	  	  },
	      messages: {
	  		   "subscriber.phoneNo": {
	  					required: "请输入手机号",
	  					rangelength:"请输入11位手机号码"		
	  	         },
	  	       "code": {
	  	        	   required: "请输入验证码",
	  	        	  rangelength:"请输入4位验证码"
	  	         },
	  	       "subscriber.password": {
	  	           required: "请输入密码",
	  	         	rangelength: "密码长度只能在8-20位字符之间"
	  	          },
	  	        "passwordConfirm": {
	  	           required: "请输入确认密码",
	  	         	rangelength: "密码长度只能在8-20位字符之间",
	  	           equalTo: "两次输入密码不一致"
	  	          },
	  	        "agreementCheckbox":{
	  	        	required: "请同意租车协议"
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

jQuery.validator.addMethod("isPhone", function(value, element) {   
   var tel = /^1\d{10}$/;
   return this.optional(element) || (tel.test(value));
}, "请输入正确的手机号");

jQuery.validator.addMethod("isPhoneStrength", function(value, element) { 
		var isStrength=false;
		if(pwdLevel(value)>=2){
			isStrength=true;
		}
	   return  isStrength ;
	}, "8-20位字符，由字母，数字和符号两种以上组合!");

function pwdLevel(value) {
    var pattern_1 = /^.*([\W_])+.*$/i;
    var pattern_2 = /^.*([a-zA-Z])+.*$/i;
    var pattern_3 = /^.*([0-9])+.*$/i;
    var level = 0;
  
    if (pattern_1.test(value)) {
        level++;
    }
    if (pattern_2.test(value)) {
        level++;
    }
    if (pattern_3.test(value)) {
        level++;
    }
    if (level > 3) {
        level = 3;
    }
    return level;
}

function formSubmit(){

	if($("#subscriberform").valid()){
			$.ajax({
				type: "POST",
			    url: "<%=path %>/subscriber/registerStepOne.action",
			    dataType:'json',
			    data:$('#subscriberform').serialize(),
			    success: function(data) {
					if(0==data.result){
						//$("#retMsg").html(data.msg);
			    		  window.location.href="<%=path %>/subscriber/registerNextStep.action";
				    }else{
			    		alertMsg(data.msg);
				    }
			    }
			});
	}else{
		alertMsg("提示","请先输入必填项。");
	}
}



</script>
</head>
<body>
<ww:include page="../header.jsp"></ww:include>
<div class="center">
      <div class="step">
         <img src="<%=path %>/common/portal/images/zhuce1.png" width="1002" />
         <ul>
           <li class="yh">用户注册</li>
           <li class="sm">实名认证</li>
           <li class="zc">注册成功</li>          
         </ul>
      </div>
       <div class="tab_box">
       <form name="subscriberform" id="subscriberform" method="post" action="">
      <table class="table1" width="750" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <th width="135"><span class="red">*&nbsp;</span>手&nbsp;机&nbsp;号&nbsp;：</th>
                <td width="330"><input  name="subscriber.phoneNo" id="phoneNo"  onkeyup="phoneKeyup();"  autocomplete="off" maxlength="11" placeholder="请输入手机号" class="input_size" type="text" style="width:315px;"></td>
                <td ></td>
              </tr>
              <tr>
                <th><span class="red">*&nbsp;</span>验&nbsp;证&nbsp;码&nbsp;：</th>
                <td><input  type="text" id="verifyCode" name="code"   placeholder="请输入短信验证码"  maxlength="4"  autocomplete="off" class="input_size" style=" width:160px; position:relative;margin-right: 0px;">
                <a  onclick="javascript:sendPhoneCode()" style="margin-left: 0px;"><span id="btnSendCode" class="yzm" style="cursor:pointer;">获取短信验证码</span></a></td>
              	<td style="width: 150px;"></td>
              </tr>
              <tr>
                <th ><span class="red">*&nbsp;</span>密&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;&nbsp;：</th>
                <td>
                	<input class="input_size" type="password"   name="subscriber.password"  autocomplete="off" id="password" maxlength="20" placeholder="请输入密码"  style="width:320px;"><span class="cw"></span>
                </td>
             	<td style="width: 150px;"></td>
              </tr>
             
              
               <tr>
               	<th><span class="red">*&nbsp;</span>确认密码：</th>
                <td>
                	<input  type="password" id="passwordConfirm"  name="passwordConfirm"   autocomplete="off" maxlength="20"  placeholder="请输入确认密码" class="input_size" style=" width:320px;"><span class="cw"></span>
                </td>
              	<td style="width: 150px;"></td>
              </tr>
              <tr>
                <th></th>
                <td><input class="input_checkbox" type="checkbox"  id="agreementCheckbox" name="agreementCheckbox"/><span class="yd">我已阅读并接受</span><a href="pages/subscriber/register/register_agreement.jsp" target="_blank"><span class="font-green">租车协议</span></a></td>
                <td></td>
              </tr>
              <tr>
                <th></th>
                <td><input class="input_sub" type="button" onclick="formSubmit()" value="下一步" style="text-align:center ;width:282px; height:39px; color:#fff; font-size:16px; font-weight:bold; line-height:39px;background:url(<%=path %>/common/portal/images/text_btn.png) no-repeat; border:none; margin-left:20px;cursor:pointer;" />
                </td>
                <td></td>
				
              </tr>
        </table>
        </form>
       </div>

</div>
<div class="footer">
</div>
</body>
</html>

