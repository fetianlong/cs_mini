<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html >
<head>
<meta charset="utf-8">
<title>个人信息</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>



<link rel=stylesheet href="<%=path %>/mobile/common/css/userinfo.css">
<script src="<%=path %>/mobile/common/js/jquery-validation-1.14.0/dist/jquery.validate.js" type="text/javascript" charset="utf-8"></script>

</head>

<body>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<form  name="subscriberform" id="subscriberform" method="post" >
			<p class="Caption">基本资料</p>
		  <div class="form-group">
			<input type="text" disabled="disabled" value="<ww:property value="subscriber.name"/>" class="form-control" id="name" placeholder="用户姓名">	
		  </div>
		   <div class="form-group">
			<input type="text" disabled="disabled" value="<ww:property value="subscriber.phoneNo"/>" class="form-control" id="name" placeholder="用户姓名">	
		  </div>
		  <div class="form-group">
			
				<ww:if test="subscriber.state==1">
					<div class="input-group">
						 <input type="text" disabled="disabled" class="form-control"  id="shenhe" placeholder="资料未提交">
						 <div class="input-group-addon PlusButton"><a  href="<%=path %>/mobile/subscriber/registerNextStep.action">实名认证</a></div>
					</div>
				</ww:if>
        		<ww:elseif test="subscriber.state==2">
        			<input type="text" disabled="disabled" class="form-control"  id="shenhe" placeholder="资料待审核">
        		</ww:elseif>
        		<ww:elseif test="subscriber.state==4">
        			<div class="input-group">
	        			 <input type="text" disabled="disabled" class="form-control"  id="shenhe" placeholder="审核未通过">
						 <div class="input-group-addon PlusButton"><a href="<%=path %>/mobile/subscriber/registerNextStep.action">修改资料</a></div>
					 </div>
        		</ww:elseif>
        		<ww:elseif test="subscriber.state==3">
        		
	        		<input type="text" disabled="disabled" class="form-control"  id="shenhe" placeholder="资料已审核">
						
					
        		</ww:elseif>
        		<ww:else>
        			 <input type="text" disabled="disabled" class="form-control"  id="shenhe" placeholder="其他">
        		</ww:else>
		        		
		  </div>
		  <div class="form-group">
			<input type="text" disabled="disabled" value='<ww:property value="subscriber.drivingLicenseNo"/>' class="form-control" id="jiazhao" placeholder="驾驶证号">
		  </div>
		   <div class="form-group">
			  		<div class="input-group">
			  				<ww:if test="wechatUserInfo!=null && wechatUserInfo.nickname!=null">
					        		<input type="text" disabled="disabled" class="form-control" id="wechatUserInfoNickname"   placeholder="<ww:property value="wechatUserInfo.nickname"/>">
									<div class="input-group-addon PlusButton" onclick="wechatUnbinding()">微信解绑</div>
					  		</ww:if>
					   		<ww:else>
					        		<input type="text" disabled="disabled" class="form-control" id="wechatUserInfoNickname"   placeholder="未绑定">
									<div class="input-group-addon PlusButton" onclick="wechatBinding()">微信绑定</div>
					   		</ww:else>
			  		
			  		</div>
			</div>
		  		

		  <p class="Caption">个人用车信息</p>
		 
		  
		  
		  <div class="form-group">
			<select class="form-control" id="s_province" name="subscriber.province"></select>
		  </div>
		  
		  <div class="form-group">
			<select  class="form-control" id="s_city" name="subscriber.city" ></select>
		  </div>
		   <div class="form-group">
			 <select  class="form-control" id="s_county" name="subscriber.county"></select>
		  </div>
		  
		  <div class="form-group">
			<input type="address" name="subscriber.address" id="address" maxlength="40"  value='<ww:property value="subscriber.address"/>' class="form-control"  placeholder="地址">
		  </div>
		  
		  <div class="form-group">
			<input type="email" name="subscriber.email" id="email" maxlength="40"  value='<ww:property value="subscriber.email"/>' class="form-control"  placeholder="邮箱">
		  </div>
		  
		  <div class="form-group">
			<input type="text" class="form-control" name="subscriber.emergencyContact" maxlength="20" id="emergencyContact" value='<ww:property value="subscriber.emergencyContact"/>' placeholder="紧急联系人姓名">
		  </div>
		   <div class="form-group">
			<input type="text" class="form-control" name="subscriber.emergencyContactPhone" maxlength="20" id="emergencyContactPhone" value='<ww:property value="subscriber.emergencyContactPhone"/>'  placeholder="紧急联系人电话">
		  </div>
		   <div class="form-group">
			<input type="text" class="form-control"  name="subscriber.emergencyContactAddress" id="emergencyContactAddress"  value='<ww:property value="subscriber.emergencyContactAddress"/>' maxlength="400" placeholder="紧急联系人地址">
		  </div>
		  
			<div class="col-xs-8 col-xs-offset-2">
				<button type="button" onclick="formSubmit()"  class="btn btn-embossed btn-primary btn-block SubmitButton">确定</button>
			</div>
			<div id="myAlert" class="form-group col-xs-8 col-xs-offset-2 alert alert-warning hidden">
				   	<span id="msg"></span>
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
function formSubmit(){
	
	if(formData==$('#subscriberform').serialize()){
		Alert("基本资料未修改，无需保存!");
		return;
	}
	
	if($("#subscriberform").valid()){
		$.ajax({
			type: "POST",
		    url: "<%=path %>/mobile/subscriber/updateBaseInfo.action",
		    dataType:'json',
		    data:$('#subscriberform').serialize(),
		    success: function(data) {
				if(0==data.result){
					Alert("保存成功!")
					return;
			    }else{
			    	Alert(data.msg);
			    }
		    },
			error: function(){
				Alert("保存失败，请稍后再试");	
			}
		});
	};
}



var formData;
$(document).ready(function () {
	formData=$('#subscriberform').serialize();

	 $('#subscriberform').validate({  
	        errorElement : 'span',  
	        errorClass : 'help-block',  
	        focusInvalid : false,  
	        rules: {
				   "subscriber.email": {
					  email: true,
		   			}
		  	  },
		      messages: {
		  	       "subscriber.email": {
		  	    	 email: "请输入正确格式的邮箱"
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


function wechatUnbinding(){
	$.ajax({
		type: "POST",
	    url: "<%=path %>/mobile/subscriber/wechatUnbinding.action",
	    dataType:'json',
	    data:$('#subscriberform').serialize(),
	    success: function(data) {
			if(0==data.result){
				
					window.location.href="<%=path %>/mobile/subscriber/showBaseInfo.action";
				
		    }else{
		    	Alert(data.msg);		
		    }
	    },
		error: function(){
			Alert("微信解绑失败，请稍后再试");	
		}
	});
}


function wechatBinding(){
	window.location.href="<%=path %>/mobile/subscriber/wechatBinding.action";
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
	  window.location.href="<%=path %>/mobile/subscriber/showBaseInfo.action";
	};

</script>

<script class="resources library" src="<%=path %>/mobile/common/js/area.js" type="text/javascript"></script>

    
 <script type="text/javascript">

 _init_area();

document.getElementById("s_province").value="<ww:property value="subscriber.province"/>";
$("#s_province").trigger("change");  

document.getElementById("s_city").value="<ww:property value="subscriber.city"/>";
$("#s_city").trigger("change");  

document.getElementById("s_county").value="<ww:property value="subscriber.county"/>";
$("#s_county").trigger("change");

</script>
</html>
