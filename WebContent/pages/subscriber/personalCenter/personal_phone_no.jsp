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
<link rel="stylesheet" type="text/css" href="common/portal/css/style.css"/>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/popup_layer.js"></script>
<script type="text/javascript" src="common/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="common/js/popup_layer.js"></script>
<style>
 *{ margin:0; padding:0px;}
 body{ margin:0; padding:0px; background-color:#fff;}
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
		window.parent.parent.alertMsg("提示","请输入手机号!");
		return;
	}
	if(!tel.test($("#phoneNo").val())){
		$("#phoneNo").focus();
		window.parent.parent.alertMsg("提示","手机号格式不正确!");
		return;
	}

	if(!isEnablePhone){
		window.parent.parent.alertMsg("提示",isEnablePhoneMsg);
		return ;
	}

	
	 curCount = count;
     $("#btnSendCode").html( curCount + "秒后重新获取");
     InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
     canSendSMS=false;
	
	     $.ajax({
	         url :'subscriber/sendChangePhoneVerificationCode.action',
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
    $.getJSON("subscriber/isPhoneEngaged.action?subscriber.phoneNo=" + mobile + "&r=" + Math.random(),
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
		 errorClass : 'text-danger',
	      rules: {
		 		
			   "subscriber.phoneNo": {
		                      required: true,
		                      rangelength:[11,11],
		                      isPhone:true
			   },
			   "code": {
                  required: true,
                  rangelength:[4,4]
	   			}
	   			
			   
	  	  },
	      messages: {
	  		   "subscriber.phoneNo": {
	  					required: "请输入新手机号",
	  					rangelength:"请输入11位手机号码"		
	  	         },
	  	       "code": {
	  	        	   required: "请输入短信验证码",
	  	        	  rangelength:"请输入4位验证码"
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





function formSubmit(){

	if($("#subscriberform").valid()){
			$.ajax({
				type: "POST",
			    url: "subscriber/changePhoneNO.action",
			    dataType:'json',
			    data:$('#subscriberform').serialize(),
			    success: function(data) {
					if(0==data.result){
						$("#phoneNo").val("");
						$("#verifyCode").val("");
						//window.parent.parent.alertMsg("修改成功","success");
						window.parent.parent.alertMsg("提示","修改成功");
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
<table class="table2" width="800" border="0" cellpadding="0" cellspacing="0" style="margin-top:50px;">
              <tr>
                <th width="220"><span class="red">*&nbsp;</span>旧手机号：</th>
                <td width="400">
                	 <input class="input_size"  id="oldPhoneNo" type="search" disabled="disabled" value='<ww:property value="#session.subscriber.phoneNo"/>'   maxlength="11"  style="width:320px; height:25px;">
                </td>
                <td></td>
              </tr>
              <tr>
                <th><span class="red">*&nbsp;</span>新手机号：</th>
                <td><input class="input_size" type="search" name="subscriber.phoneNo" id="phoneNo" onkeyup="phoneKeyup();"   maxlength="11" placeholder="请输入新手机号" style="width:320px; height:25px;"></td>
                <td></td>
              </tr>             
               <tr>
                <th><span class="red">*&nbsp;</span>验&nbsp;证&nbsp;码&nbsp;：</th>
                 <td><input  type="text" id="verifyCode" name="code"   placeholder="请输入短信验证码"  maxlength="4" class="input_size" style=" width:160px; position:relative;margin-right: 0px;">
                <a  onclick="javascript:sendPhoneCode()" style="margin-left: 0px;"><span id="btnSendCode" class="yzm" style="cursor:pointer;">获取短信验证码</span></a></td>
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
