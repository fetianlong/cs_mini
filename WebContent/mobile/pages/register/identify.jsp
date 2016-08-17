<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>实名认证</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>
<link rel=stylesheet href="<%=path %>/mobile/common/css/register.css">

<script type="text/javascript" src="<%=path %>/mobile/common/js/uploadPreview/uploadPreview.js" ></script>
<script src="<%=path %>/mobile/common/js/jquery-validation-1.14.0/dist/jquery.validate.js" type="text/javascript" charset="utf-8"></script>


<script>
$(function(){
	
	  $(".UploadID").click(function(e) {
		$("#IDFile").click();
	})
	$(".UploadJiazhao").click(function(e) {
		$("#drivingFile").click();
	});  
	
});

window.onload = function () { 
    new uploadPreview({ UpBtn: "IDFile", DivShow: "idDiv", ImgShow: "IDImg" });
    new uploadPreview({ UpBtn: "drivingFile", DivShow: "drivingDiv", ImgShow: "drivingImg"});
}
</script>
</head>

<body>

<form id="subscriberform" action="<%=path %>/mobile/subscriber/registerFinish.action" enctype="multipart/form-data" method="post">

<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		
		 	<ww:if test='retMsg != null && retMsg != ""'> 
		 		<div class="alert alert-danger" role="alert" id="warning"><ww:property value="retMsg"/></div>
		 	</ww:if>
           
		
		<div class="form-group">
			<label class="sr-only" for="name">真实姓名</label>
			<input type="text" class="form-control" id="name" name="subscriber.name" placeholder="真实姓名" value="<ww:property value="subscriber.name"/>">
			
		</div>
		<div class="form-group">
			<label class="sr-only" for="drivingLicenseNo">驾驶证号</label>
			<input type="text" class="form-control" maxlength="18" id="drivingLicenseNo" name="subscriber.drivingLicenseNo" value="<ww:property value="subscriber.drivingLicenseNo"/>" placeholder="驾驶证号">
			
		</div>
		<div class="form-group">
			<label class="sr-only" ></label>
			<select class="form-control" id="s_province" name="subscriber.province"></select>
		</div>
        <div class="form-group">
			<label class="sr-only" ></label>
			<select class="form-control" id="s_city" name="subscriber.city" ></select>
		</div>
        <div class="form-group">
			<label class="sr-only" ></label>
			<select class="form-control" id="s_county" name="subscriber.county"></select>
		</div>
		<div class="form-group">
			<label class="sr-only" for="drivingLicenseNo">家庭住址</label>
			
			<input type="text" class="form-control"  id="address" name="subscriber.address" value="<ww:property value="subscriber.address"/>" placeholder="家庭住址">
			
		</div>
		<div class="form-group" >
			<label class="sr-only" for="IDFile">上传身份证件</label>
			<p class="help-block">上传手持身份证照片</p>
			<div id="idDiv">
				<ww:if test="@org.apache.commons.lang.StringUtils@isEmpty(subscriber.idCardImg)">
					<img alt="身份证图片" class="img-responsive center-block UploadID" id="IDImg" src="<%=path %>/mobile/common/images/register/shenfenzheng.jpg">
		  		</ww:if>
		  		<ww:else>
		  			<img alt="身份证图片" class="img-responsive center-block UploadID" id="IDImg" src="<%=path %>/<ww:property value="subscriber.idCardImg"/>">
		  		</ww:else>	  		
			</div>
			<input style="visibility:hidden;width:0px;heigh:0px;" type="file" id="IDFile" name="upload" accept="image/*">
			
		</div>
		<div class="form-group" >
			<label class="sr-only" for="drivingFile">上传驾驶执照</label>
			<p class="help-block">上传驾驶证照片</p>
			
			<div id="drivingDiv">
				
					 <ww:if test="@org.apache.commons.lang.StringUtils@isEmpty(subscriber.drivingLicenseImg)">
			  			 <img  alt="驾照" class="img-responsive center-block UploadJiazhao" id="drivingImg" src="<%=path %>/mobile/common/images/register/jiazhao.jpg">
			  		</ww:if>
			  		<ww:else>
			  			<img alt="驾照" class="img-responsive center-block UploadJiazhao" id="drivingImg" src="<%=path %>/<ww:property value="subscriber.drivingLicenseImg"/>">
			  			
			  		</ww:else>
			  		
			</div>
			<input style="visibility:hidden;width:0px;heigh:0px;"  type="file" name="drivingUpload" id="drivingFile"  accept="image/*">
			
		</div>
		<div class="form-group">
			<div class="col-xs-8 col-xs-offset-2">
				<button type="button" onclick="formSubmit()"  class="btn btn-embossed btn-primary btn-block SubmitButton">下一步</button>
			</div>
		</div>
		
	</div>
  </div>
</div>
</form>
</body>

<script type="text/javascript">
$().ready(function() {
    
    $('#subscriberform').validate({  
        errorElement : 'p',  
        errorClass : 'help-block',  
        focusInvalid : false,  
        rules: {
			   "subscriber.name": {
	                required:true  
			   },
			   "subscriber.drivingLicenseNo": {
               		required: true,
               		isIdCardNo:true
	   			},
				 "subscriber.province": {
					 province: true
		   		},
			   	 "subscriber.city": {
			   		city: true
		   		},
			   	 "subscriber.county": {
			   		county: true
				},
				"subscriber.address":{
					required:true
				},
	   			"upload": {
	   			    required: true
	   			 },
	   			"drivingUpload": {
	   			    required: true
	   	   		}
			   
	  	  },
	      messages: {
	  		   "subscriber.name": {
	  				 required: "请填写真实姓名"
	  	         },
	  	       "subscriber.drivingLicenseNo": {
	  	    	 	required: "请填写驾驶证编号",
	  	           	isIdCardNo:"请填写正确的驾驶证编号"
	  	         },
	  	       "subscriber.address": {
	  	    	 	required: "请填写家庭住址"
	  	           	
	  	         },
	  	       "upload": {
	  	           required: "请上传身份证"
	  	         },
	  	       
	  	        "drivingUpload": {
	  	           required: "请上传驾驶证"
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
        	element.closest('div').append(error);  
        } 

        
    });
    
    
    if('<ww:property value="@org.apache.commons.lang.StringUtils@isEmpty(subscriber.idCardImg)"/>'=='false'){
   	 $("#IDFile").rules("remove","required");
   }
  
   if('<ww:property value="@org.apache.commons.lang.StringUtils@isEmpty(subscriber.drivingLicenseImg)"/>'=='false'){
   	  $("#drivingFile").rules("remove","required");
  }

});
    
jQuery.validator.addMethod("isIdCardNo", function(value, element) {   
	  var tel = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/;
	   return this.optional(element) || (tel.test(value));
	}, "请填写正确的身份证号");

jQuery.validator.addMethod("province", function(value, element) {   
	   
	if("省份"==value){
		   return false;
	   }else{
		   return true;
	   }
	}, "请选择省份");

jQuery.validator.addMethod("city", function(value, element) {   
	 
	   if("地级市"==value){
		   return false;
	   }else{
		   return true;
	   }
	}, "请选择地级市");

jQuery.validator.addMethod("county", function(value, element) {   
	   if("市、县级市"==value){
		   return false;
	   }else{
		   return true;   
	   }
	   
	}, "请选择县级市");

function formSubmit(){
	if($("#subscriberform").valid()){
		var maxsize=5;

		if(($("#IDFile").val()==null || $("#IDFile").val()=='')&&($("#drivingFile").val()==null || $("#drivingFile").val()=='')){
			$("#subscriberform").attr("action",'<%=path %>/mobile/subscriber/registerFinishNoUpload.action');
			$("#subscriberform").submit();
		}else if($("#IDFile").val()==null || $("#IDFile").val()==''){
			var drivingSize=findSize("drivingFile");
			if(drivingSize>maxsize){
				Alert("图片大小不能超过5M!");
				return;
			}
			$("#IDFile").remove();
			$("#subscriberform").attr("action",'<%=path %>/mobile/subscriber/registerFinish.action');
			$("#subscriberform").submit();
		}else if($("#drivingFile").val()==null || $("#drivingFile").val()==''){
			var idSize=findSize("IDFile");
			if(idSize>maxsize){
				Alert("图片大小不能超过5M!");
				return;
			}
			$("#drivingFile").remove();
			$("#subscriberform").attr("action",'<%=path %>/mobile/subscriber/registerFinish.action');
			$("#subscriberform").submit();
		}else{
			var drivingSize=findSize("drivingFile");
			if(drivingSize>maxsize){
				Alert("图片大小不能超过5M!");
				return;
			}
			var idSize=findSize("IDFile");
			if(idSize>maxsize){
				Alert("图片大小不能超过5M!");
				return;
			}
			$("#subscriberform").attr("action",'<%=path %>/mobile/subscriber/registerFinish.action');
			$("#subscriberform").submit();
		}
	}
}


function findSize(field_id){
        var fileInput = $("#"+field_id)[0];
       var byteSize  = fileInput.files[0].size;
        return ( Math.ceil(byteSize / 1024) /1024 ); 
}



</script>
<script class="resources library" src="<%=path %>/mobile/common/js/area.js" type="text/javascript"></script>
 <script type="text/javascript">
 _init_area();
$("#s_province option[value='<ww:property value="subscriber.province"/>']").attr("selected",true);
$("#s_province").trigger("change");  
$("#s_city option[value='<ww:property value="subscriber.city"/>']").attr("selected",true);
$("#s_city").trigger("change");  
$("#s_county option[value='<ww:property value="subscriber.county"/>']").attr("selected",true);
$("#s_county").trigger("change");  
</script>
</html>
