<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html>
<head>
<title>修改手机登录密码</title>
<%@ include file="/mobile/pages/common/common_head.jsp"%>




<link rel=stylesheet href="<%=path %>/mobile/common/css/changeinfo.css">
<script src="<%=path %>/mobile/common/js/jquery-validation-1.14.0/dist/jquery.validate.js" type="text/javascript" charset="utf-8"></script>


</head>

<body>
	<form name="subscriberform" id="subscriberform" method="post">
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<div class="form-group">
			<input type="password" class="form-control" id="oldPassword" name="oldPassword"  maxlength="20" placeholder="旧密码">
		
		</div>
		<div class="form-group">
			<input type="password" class="form-control" id="newPassword" name="subscriber.password"  maxlength="20" placeholder="新密码">
			
		</div>
		<div class="form-group">
			<input type="password" class="form-control" id="passwordConfirm" name="passwordConfirm" placeholder="确认密码">
		</div>
		<div class="BottomButton">
			<div class="col-xs-8 col-xs-offset-2">
				<button type="button" onclick="formSubmit()" class="btn btn-embossed btn-primary btn-block">确认</button>
			</div>
		</div>
	</div>
  </div>
</div>
</form>




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
	 $("#subscriberform").validate({
		 errorElement : 'span',  
         errorClass : 'help-block',  
         focusInvalid : false,  
	      rules: {
			   "oldPassword": {
		              required: true,
		              rangelength:[8,20]
			   },
			   
	   			"subscriber.password": {
	   			    required: true,
	   			 	rangelength:[8,20],
	   			    isNotEqualOldPassword:true,
	   			 	isPhoneStrength:true
	   			 },
	   			"passwordConfirm": {
	   			    required: true,
	   			 	rangelength:[8,20],
	   			    equalTo: "#newPassword"
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
			 highlight : function(element) {  
                 $(element).closest('.form-group').addClass('has-error');  
             },  
   
             success : function(label) {  
                 label.closest('.form-group').removeClass('has-error');  
                 label.remove();  
             },  
   
             errorPlacement : function(error, element) {  
                 element.parent('div').append(error);  
             } 
   
	    });
	});


jQuery.validator.addMethod("isNotEqualOldPassword", function(value, element) {   
	   return $("#oldPassword").val()!=$("#newPassword").val();
	}, "新旧密码不能相同!");
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
		    url: "<%=path%>/mobile/subscriber/changePassword.action",
		    dataType:'json',
		    data:$('#subscriberform').serialize(),
		    success: function(data) {
				if(0==data.result){
					$("#oldPassword").val("");
					$("#newPassword").val("");
					$("#passwordConfirm").val("");
					Alert("修改成功！");
			    }else{
			    	Alert(data.msg);
			    }
		    },
			error: function(){
				Alert("修改密码失败，请稍后再试");	
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
