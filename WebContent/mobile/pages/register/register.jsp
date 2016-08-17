<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>用户注册</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>


<link rel=stylesheet href="<%=path %>/mobile/common/css/register.css">
<script src="<%=path %>/mobile/common/js/jquery-validation-1.14.0/dist/jquery.validate.js" type="text/javascript" charset="utf-8"></script>

</head>

<body>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<form  name="subscriberform" id="subscriberform">
		<input type="hidden" name="subscriber.source" value="wechat">
		<input type="hidden" name="unionId" value="<ww:property value="#request.unionId"/>">
		
		<div class="form-group">
			<label class="sr-only" for="phoneNo">手机号</label>
			<input type="text" class="form-control" name="subscriber.phoneNo" id="phoneNo"   maxlength="11" placeholder="手机号">
			
		</div>
		<div class="form-group">
			<label class="sr-only" for="verifyCode">验证码</label>
			<div class="input-group">
				<input type="text" class="form-control" id="verifyCode" name="code"   maxlength="4"  placeholder="请填写验证码">
				<div class="input-group-addon GetYanzheng" id="btnSendCode" onclick="javascript:sendPhoneCode()">获取验证码</div><!--加disabled类变为灰显-->
			</div>
			
		</div>
<!-- 		<div class="form-group">
			<label class="sr-only" for="password">密码</label>
			<input type="password" class="form-control" id="password"  name="subscriber.password" maxlength="20" placeholder="密码">
			
		</div>
		<div class="form-group">
			<label class="sr-only" for="password2">确认密码</label>
			<input type="password" class="form-control" id="password2"  name="passwordConfirm" maxlength="20" placeholder="确认密码">
			
		</div> -->
		<ww:if test="#request.unionId !=null">
			<div class="form-group">
				<label class="checkbox">
					<input data-toggle="checkbox" type="checkbox" id="WeChat" name="WeChat" value="1" checked>关联我的微信号
				</label>
			</div>
			
		</ww:if>
		<div class="form-group">
			<label class="checkbox">
				<input data-toggle="checkbox" type="checkbox" id="agreementCheckbox" name="agreementCheckbox" value="1" checked>我已阅读并接受<a href="<%=path %>/mobile/pages/license.jsp" class="zcxy">《租车协议》</a>
			</label>
		</div>
		<div class="BottomButton">
			<div class="col-xs-8 col-xs-offset-2">
				<button type="button" onclick="formSubmit()" class="btn btn-embossed btn-primary btn-block">下一步</button>
			</div>
		</div>
		</form>
	</div>
  </div>
</div>

</body>

<script type="text/javascript">
         
		  	var InterValObj; //timer变量，控制时间
		  	var count = 120; //间隔函数，1秒执行
		  	var curCount;//当前剩余秒数
		
		  	var canSendSMS=true;
		  
		
		  	function SetRemainTime() {
		  	    if (curCount == 0) {                
		  	        window.clearInterval(InterValObj);//停止计时器
		  	        $("#btnSendCode").html("重新获取");
		  	        $("#btnSendCode").removeClass("disabled");
		  	        canSendSMS=true;
		  	    }
		  	    else {
		  	        curCount--;
		  	        $("#btnSendCode").html( "重新获取("+curCount+")");
		  	        canSendSMS=false;
		  	    }
		  	}
		
		
		  	function sendPhoneCode(){
		  		
		  		if(!$("#phoneNo").valid()){return;}
		  		if(!canSendSMS){return;}
		  		

		  		curCount = count;
		  	   $("#btnSendCode").html( "重新获取("+curCount+")");
		  	    $("#btnSendCode").addClass("disabled");
		  	    InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
		  	    canSendSMS=false;
		  		
		  		     $.ajax({
		  		         url :'<%=path %>/mobile/subscriber/sendPhoneVerificationCode.action',
		  		         type:'post',
		  		         cache:false,
		  		         async:false,
		  		         data:{'subscriber.phoneNo':$("#phoneNo").val()},
		  		         dataType:'json',
		  		         success:function(data) {
		  		        	/* if(0!=data.result){
	  					    	Alert(data.msg)
	  					    } */
		  		         }
		  		     });
		  	}
		
		
		  	
		  	function formSubmit(){
		  		if($("#subscriberform").valid()){
		  			
		  				$.ajax({
		  					type: "POST",
		  				    url: "<%=path %>/mobile/subscriber/registerStepOne.action",
		  				    dataType:'json',
		  				    data:$('#subscriberform').serialize(),
		  				    success: function(data) {
		  						if(0==data.result){
		  							// window.location.href="<%=path %>/mobile/subscriber/registerNextStep.action";
		  				    		 
		  							 window.location.href="<%=path %>/mobile/pages/register/simple_register_success.jsp";
		  					    }else{
		  					    	Alert(data.msg);
		  					    	//$("#warning").html(data.msg);
		  					    	//$("#warning").removeClass("hidden");
		  					    }
		  				    },
							error: function(){
								Alert("注册失败，请稍后再试");	
							}
		  				});
		  		}
		  	}

		  	
         	
		  	
		  	$().ready(function() {
          
                    $('#subscriberform').validate({  
                        errorElement : 'p',  
                        errorClass : 'help-block',  
                        focusInvalid : false,  
                        rules: {
             			   "subscriber.phoneNo": {
         		                required:true,
         		                rangelength:[11,11],
         		                isPhone:true,
         		                remote: {
         		            	    url: "<%=path %>/mobile/subscriber/isPhoneEngagedNew.action",
         		            	    type: "post",               //数据发送方式
         		            	    dataType: "json",           //接受数据格式   
         		            	    data: {                     //要传递的数据
         		            	        "subscriber.phoneNo": function() {
         		            	            return $("#phoneNo").val();
         		            	        }
         		            	    }
         		            	}
             			   },
             			   "code": {
                               required: true,
                               rangelength:[4,4]
             	   			},
             	   			/* "subscriber.password": {
             	   			    required: true,
             	   				rangelength: [8, 20],
             	   			 	isPhoneStrength:true
             	   			 	
             	   			 },
             	   			"passwordConfirm": {
             	   			    required: true,
             	   			 	rangelength:[8,20],
             	   				equalTo: "#password"
             	   			   }, */
             	   			"agreementCheckbox":{
             	   				required: true
             	   	   		}
             			   
             	  	  },
             	      messages: {
             	  		   "subscriber.phoneNo": {
             	  				 required: "请输入手机号",
             	  				 minlength: "请输入正确的手机号码",
             	  				 rangelength:"请输入11位手机号码",
             	  				remote: "手机号码已经注册",
             	  	         },
             	  	       "code": {
             	  	        	   required: "请输入验证码",
             	  	        	  rangelength:"请输入4位验证码"
             	  	         },
             	  	       /* "subscriber.password": {
             	  	            required: "请输入密码",
             	  	         	rangelength: "密码长度只能在8-20位字符之间"
             	  	          },
             	  	        "passwordConfirm": {
             	  	           required: "请输入确认密码",
             	  	         	rangelength: "密码长度只能在8-20位字符之间",
             	  	           equalTo: "两次输入密码不一致"
             	  	          }, */
             	  	        "agreementCheckbox":{
             	  	        	required: "请同意租车协议"
             	  	  	      }
             			 },
              
                        highlight : function(element) { 
                        
                        		 $(element).closest('.form-group').addClass('has-error');  
                        	
                        },  
              
                        success : function(label) {  
                            label.closest('.form-group').removeClass('has-error');  
                            label.remove();  
                        },  
              
                        errorPlacement : function(error, element) { 
                        	if($(element).attr("id")=="verifyCode"){
                        		element.closest('div').parent().append(error);  
                        	}else{
                        		element.closest('div').append(error);  
                        	}
                            
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

         	</script>



</html>
