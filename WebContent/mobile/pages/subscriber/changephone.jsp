<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<%@ page import="com.dearho.cs.subscriber.pojo.Subscriber" language="java" %>
<%@page import="com.dearho.cs.util.Constants"%>
<!doctype html>
<html >
<head>
<title>修改绑定手机号</title>
<%@ include file="/mobile/pages/common/common_head.jsp"%>
<link rel=stylesheet href="<%=path %>/mobile/common/css/changeinfo.css">

<script src="<%=path %>/mobile/common/js/jquery-validation-1.14.0/dist/jquery.validate.js" type="text/javascript" charset="utf-8"></script>

</head>

<body>

<div class="container">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<form id="subscriberform" action="<%=path%>/mobile/subscriber/changePhoneNo.action">
		<div class="form-group">
		<% HttpSession session1 = request.getSession();
		Subscriber accsub =(Subscriber)request.getSession().getAttribute(Constants.SESSION_SUBSCRIBER);
		String phoneNo = accsub.getPhoneNo(); %>
			<input type="text" class="form-control" disabled="disabled" id="oldphone" value="<%=phoneNo%>" placeholder="旧手机号">
		</div>
		<div class="form-group">
			<div class="input-group">
				<input type="text" class="form-control" id="oldyanzheng" maxlength="4" name="oldyanzheng" placeholder="原手机号验证码">
				<div class="input-group-addon PlusButton"   id="oldBtnSendCode" onclick="sendOldPhoneCode()">发送验证码</div>
			</div>
		</div>
		<div class="form-group">
			<input type="text" class="form-control"  id="newphone" maxlength="11" name="newphone" placeholder="新手机号">
		</div>
		<div class="form-group">
			<div class="input-group">
				<input type="text" class="form-control" id="newyanzheng" maxlength="4" name="newyanzheng" placeholder="新手机号验证码">
				<div class="input-group-addon PlusButton"   id="newBtnSendCode" onclick="sendNewPhoneCode()">发送验证码</div>
			</div>
		</div>
		<div class="BottomButton">
			<div class="col-xs-8 col-xs-offset-2">
				<button type="button" onclick="formSubmit();" class="btn btn-embossed btn-primary btn-block">确认</button>
			</div>
		</div>
		</form>
	</div>
  </div>
</div>


<!-- 模态框（Modal）alert -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" 
   aria-labelledby="myModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="myModalLabel">提示 </h4>
         </div>
         <div class="modal-body" id="modelMsg"></div>
         <div class="modal-footer">
             <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
         </div>
      </div>
</div>
</div>
<!-- 模态框（Modal）end -->


<!-- 模态框（Modal） confirm-->
<div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" 
   aria-labelledby="confirmModalLabel" aria-hidden="true">
   <div class="modal-dialog">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" 
               data-dismiss="modal" aria-hidden="true">
                  &times;
            </button>
            <h4 class="modal-title" id="confirmModalLabel">提示 </h4>
         </div>
         <div class="modal-body" id="confirmMsg"></div>
         <div class="modal-footer">
             <button type="button" onclick="gotoAccountIndex()" class="btn btn-primary">确认</button>
         </div>
      </div>
</div>
</div>
<!-- 模态框（Modal）end -->
</body>

<script type="text/javascript">
$().ready(function() {
	
    $('#subscriberform').validate({  
        errorElement : 'span',  
        errorClass : 'help-block',  
        focusInvalid : false,  
        rules: {
        	"oldyanzheng": {
           		required: true,
           		rangelength:[4,4]
   			},
			   "newphone": {
		                     required: true,
		                     rangelength:[11,11],
		                     isPhone:true,
		                     remote: {
      		            	    url: "<%=path %>/mobile/subscriber/isPhoneEngagedNew.action",
      		            	    type: "post",               
      		            	    dataType: "json",              
      		            	    data: {                     
      		            	        "subscriber.phoneNo": function() {
      		            	            return $("#phoneNo").val();
      		            	        }
      		            	    }
      		            	}
			   },
			   "newyanzheng": {
               		required: true,
               		rangelength:[4,4]
	   			}
			   
	  	  },
	      messages: {
	    	  "oldyanzheng": {
 	        	   required: "请输入原手机短信验证码",
 	        	  rangelength:"请输入4位短信验证码"
 	         },
	  		   "newphone": {
	  				 required: "请输入新手机号",
	  				 rangelength:"请输入11位手机号码",
	  				 remote: "手机号码已经注册"
	  	         },
	  	       "newyanzheng": {
	  	        	   required: "请输入新手机短信验证码",
	  	        	  rangelength:"请输入4位短信验证码"
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
            if($(element).attr("id")=="newyanzheng" ||$(element).attr("id")=="oldyanzheng"){
       		 element.parent('div').parent().append(error);  
	       	}else{
	       		 element.parent('div').append(error);  
	       	}
        },  

    });
});

jQuery.validator.addMethod("isPhone", function(value, element) {   
	   var tel = /^1\d{10}$/;
	   return this.optional(element) || (tel.test(value));
	}, "请输入正确的手机号");
	
	
	
	
	
	var newInterValObj; //timer变量，控制时间
  	var newCount = 120; //间隔函数，1秒执行
  	var newCurCount;//当前剩余秒数

  	var newCanSendSMS=true;
  

  	function newSetRemainTime() {
  	    if (newCurCount == 0) {                
  	        window.clearInterval(newInterValObj);//停止计时器
  	        $("#newBtnSendCode").html("重新获取");
  	        $("#newBtnSendCode").removeClass("disabled");
  	        newCanSendSMS=true;
  	    }
  	    else {
  	    	newCurCount--;
  	        $("#newBtnSendCode").html("重新获取("+newCurCount+")");
  	        newCanSendSMS=false;
  	    }
  	}


  	function sendNewPhoneCode(){
  		
  		if(!$("#newphone").valid()){return;}
  		if(!newCanSendSMS){return;}
  		

  		newCurCount = newCount;
  	    $("#newBtnSendCode").html( "重新获取("+newCurCount+")");
  	    $("#newBtnSendCode").addClass("disabled");
  	    newInterValObj = window.setInterval(newSetRemainTime, 1000); //启动计时器，1秒执行一次
  	    newCanSendSMS=false;
  		
  		     $.ajax({
  		         url :'<%=path %>/mobile/subscriber/sendChangePhoneCode.action',
  		         type:'post',
  		         cache:false,
  		         async:false,
  		         data:{'type':'new','subscriber.phoneNo':$("#newphone").val()},
  		         dataType:'json',
  		         success:function(data) {
  		        		if(1==data.result){
  		        			Alert(data.msg)
					    }
  		         }
				
  		     });
  	}

  	
  	
  	var oldInterValObj; //timer变量，控制时间
  	var oldCount = 120; //间隔函数，1秒执行
  	var oldCurCount;//当前剩余秒数

  	var oldCanSendSMS=true;
  

  	function oldSetRemainTime() {
  	    if (oldCurCount == 0) {                
  	        window.clearInterval(oldInterValObj);//停止计时器
  	        $("#oldBtnSendCode").html("重新获取");
  	        $("#oldBtnSendCode").removeClass("disabled");
  	        oldCanSendSMS=true;
  	    }
  	    else {
  	    	oldCurCount--;
  	        $("#oldBtnSendCode").html("重新获取("+oldCurCount+")");
  	        oldCanSendSMS=false;
  	    }
  	}
  	
	function sendOldPhoneCode(){
  		if(!$("#oldphone").valid()){return;}
  		if(!oldCanSendSMS){return;}

  		oldCurCount = oldCount;
  	    $("#btnSendCode").html( "重新获取("+oldCurCount+")");
  	    $("#btnSendCode").addClass("disabled");
  	    oldInterValObj = window.setInterval(oldSetRemainTime, 1000); //启动计时器，1秒执行一次
  	    oldCanSendSMS=false;
  		
  		     $.ajax({
  		         url :'<%=path %>/mobile/subscriber/sendChangePhoneCode.action',
  		         type:'post',
  		         cache:false,
  		         async:false,
  		         data:{'type':'old','subscriber.phoneNo':$("#oldphone").val()},
  		         dataType:'json',
  		         success:function(data) {
  		        	if(1==data.result){
		        			Alert(data.msg)
				    }
  		         }
				
  		     });
  	}

  	
  	function formSubmit(){
  		if($("#subscriberform").valid()){
  			
  				$.ajax({
  					type: "POST",
  				    url: "<%=path%>/mobile/subscriber/changePhoneNo.action",
  				    dataType:'json',
  				    data:$('#subscriberform').serialize(),
  				    success: function(data) {
  						if(0==data.result){
  							Alert("修改成功");
  						    
  					    }else{
  					    	Alert(data.msg)
  					    }
  				    },
					error: function(){
						Alert("修改手机号失败，请稍后再试");	
					}
  				});
  		}
  	}

  	function alertMsg(msg){
  		$('#myModal').modal('show');
  		$("#modelMsg").html(msg);
  	};

  	function confirmMsg(msg){
  		$('#confirmModal').modal('show');
  		$("#confirmMsg").html(msg);
  	};
  	function gotoAccountIndex(){
  	  window.location.href="<%=path %>/mobile/account/index.action";
  	}
</script>
</html>
