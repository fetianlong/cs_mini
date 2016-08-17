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
<title>实名认证</title>




<link rel="shortcut icon" href="<%=path%>/common/css/images/favicon.ico" type="image/x-icon" /> 
<script type="text/javascript" src="common/js/cropper/assets/js/jquery.min.js"></script>



<link href="common/js/cropper/dist/cropper.css" rel="stylesheet">
<script type="text/javascript" src="common/js/cropper/dist/cropper.js"></script>

<link href="common/js/cropper/assets/css/bootstrap.min.css" rel="stylesheet">
<link href="common/js/cropper/examples/crop-avatar/css/main.css" rel="stylesheet">


<script type="text/javascript" src="common/js/cropper/assets/js/bootstrap.min.js"></script>


<script type="text/javascript"   src="common/js/cropper/examples/crop-avatar/js/main.js" ></script>
<script type="text/javascript"   src="common/js/cropper/examples/crop-avatar/js/main_driving.js" ></script>

 <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
 <!--[if lt IE 9]>
 <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
   <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
 <![endif]-->

<link rel="stylesheet" type="text/css" href="common/portal/css/style.css"/>

<script type="text/javascript">




function formSubmit(){


	
	/*
	var isValidatePass=true;
	
	if($("#realName").val()=="" ||$("#realName").val()==null ){
		$("#realName").focus();
		$("#realNameSpan").html("请填写真实姓名");
		isValidatePass=false;
	}else{
		$("#realNameSpan").html("");
	}

	if($("#address").val()=="" ||$("#address").val()==null){
		$("#address").focus();
		$("#addressSpan").html("请填写邮寄地址");
		isValidatePass=false;
	}else{
		$("#addressSpan").html("");
	}

	if($("#idCardImage").val()=="" ||$("#idCardImage").val()==null){
		$("#idCardSpan").html("请上传身份证图片");
		isValidatePass=false;
	}else{
		$("#idCardSpan").html("");
	}

	if($("#drivingImage").val()=="" ||$("#drivingImage").val()==null){
		$("#drivingSpan").html("请上传驾驶证图片");
		isValidatePass=false;
	}else{
		$("#drivingSpan").html("");
	}

	if(!isValidatePass){
		alertMsg("提示","请先输入必填项。");
		return false;
	}
	*/
	$("#subscriberName").val($("#realName").val());
/*	$("#subscriberPostAddress").val($("#address").val());*/
	

	if($("#subscriberform").valid()){
		$("#subscriberform").submit();
	}else{
		alertMsg("提示","请先输入必填项。");
	}
	
	
	
	
}

</script>
</head>
<body>
<ww:include page="/pages/subscriber/header.jsp"></ww:include>

<div class="center" style="">
      <div class="step">
         <img src="common/portal/images/zhuce2.png" width="1002" />
         <ul>
           <li class="yh">用户注册</li>
           <li class="sm">实名认证</li>
           <li class="zc">注册成功</li>          
         </ul>
      </div>
      <div class="tab_box">
   <div style="visibility:hidden;">
      <form name="subscriberform" id="subscriberform" method="post" action="subscriber/registerFinish.action">
      
		<input type="text" id="idCardImage" name="subscriber.idCardImg" />
		<input type="text"  id="drivingImage" name="subscriber.drivingLicenseImg"/>
		<input type="text" name="subscriber.name"  id="subscriberName">
	<!--  
		<input type="text"  name="subscriber.postAddress"  id="subscriberPostAddress">
	-->
	</form> 
		</div>
	<br/><font color="red"><ww:property value="retMsg" /></font>
      <table class="table1" width="1000" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <th width="130"><span class="red">*&nbsp;</span>真实姓名：</th>
                <td width="434">  <input class="input_size" type="text"  id="realName" value='<ww:property value="subscriber.name"/>' style="width:414px;margin-left: 0px;"></td>
                <td id="realNameTd"><span class="cw" id="realNameSpan" style="top:-2px;"></span></td>
              </tr>
           
           <!-- 
              <tr>
                <th valign="top"><span style=" position:relative; top:12px;"><font color="red">*&nbsp;</font>邮寄地址：</span></th>
                <td>
                   <table>
                      <tr><td>
                      	<textarea  id="address"  class="input_size"  style=" height:45px;;width:414px;float: left;margin-left: 0px;resize: none;" rows="2" cols=""><ww:property value="subscriber.postAddress"/></textarea>
                      </td></tr>
                      <tr><td><span class="wzts"  style="float:left;width:415px;margin-left: 0px;">此地址用于邮寄会员卡</span></td></tr>
                   </table>
                   
                          
                </td>
                <td id="addressTd"><span class="cw" id="addressSpan" style="top:-14px;"></span></td>
              </tr>
       -->
               <tr>
               	<td height="20"></td>
               	<td style="margin: 0px;"></td>
               	<td style="margin: 0px;"></td>
               </tr>
              <tr>
                <th valign="top"><span class="red">*&nbsp;</span>选择上传文件：</th>
                <td>
                
                  <table>
                      <tr><td>
		                	<!-- 身份证上传 start -->
									 <div class="container" id="crop-avatar" style="width:415px;height:240px;float: left;padding:0px;margin: 0px;">
									    <!-- Current avatar -->
									    <div class="avatar-view" id="avatar-view" style="width:414px;height:240px;" title="上传身份证件">
									   <!--     <img src="common/js/cropper/images/idcard-license-pre_proc.jpg" alt="身份证照片">-->
									      
									      	<img src="common/portal/images/sfz.png" alt="身份证照片">
									      
									    </div>
									    <!-- Cropping modal -->
									    <div class="modal fade" id="avatar-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
									      <div class="modal-dialog modal-lg">
									        <div class="modal-content">
									          <form class="avatar-form" id="avatar-form" action="subscriber/upload.action" enctype="multipart/form-data" method="post">
									          	<input type="hidden" name="imageData" id="imageData">
									        
									          	
									            <div class="modal-header">
									              <button class="close" data-dismiss="modal" type="button">&times;</button>
									              <h4 class="modal-title" id="avatar-modal-label">身份证上传</h4>
									            </div>
									            <div class="modal-body">
									              <div class="avatar-body">
									
									                <!-- Upload image and data -->
									                <div class="avatar-upload" id="avatar-upload">
									                  <input class="avatar-src" id="avatar-src" name="pic_src" type="hidden">
									                  <input class="avatar-data" id="avatar-data" name="pic_data" type="hidden">
									                <label for="avatarInput">选择图片:</label>
									                  <input class="avatar-input" id="avatarInput" name="upload" type="file">
									                </div>
									
									                <!-- Crop and preview -->
									                <div class="row">
									                  <div class="col-md-9" style="width: 95%;">
									                    <div class="avatar-wrapper"  id="avatar-wrapper"></div>
									                  </div>
									                  <div class="col-md-3">
									                    <div style="display:none;"class="avatar-preview preview-lg"></div>
									                     <div style="display:none;" class="avatar-preview preview-md"></div>
									                    <div style="display:none;" class="avatar-preview preview-sm"></div> 
									                  </div>
									                </div>
									
									                <div class="row avatar-btns" id="avatar-btns">
									                  <div class="col-md-9" style="width:100%%;">
									                    <div class="btn-group">
									                      <button class="btn btn-primary" data-method="rotate" data-option="-90" type="button" title="左转">左转</button>
									                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-15" type="button">左转15度</button>
									                     &nbsp; <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-30" type="button">左转30度</button>
									                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-45" type="button">左转45度</button>
									                    </div>
									                    <div class="btn-group">
									                      <button class="btn btn-primary" data-method="rotate" data-option="90" type="button" title="右转">右转</button>
									                      <button style="display:none;"  class="btn btn-primary" data-method="rotate" data-option="15" type="button">15deg</button>
									                      &nbsp;<button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="30" type="button">右转30度</button>
									                      <button style="display:none;"  class="btn btn-primary" data-method="rotate" data-option="45" type="button">45deg</button>
									                    </div>
									                    <div class="col-md-3">
									                    <button class="btn btn-primary btn-block avatar-save" id="avatar-save" type="submit">完成</button>
									                  </div>
									                  </div>
									                  
									                </div>
									              </div>
									            </div>
									            <!-- <div class="modal-footer">
									              <button class="btn btn-default" data-dismiss="modal" type="button">Close</button>
									            </div> -->
									          </form>
									        </div>
									      </div>
									    </div><!-- /.modal -->
									
									    <!-- Loading state -->
									    <div class="loading" id="loading" aria-label="Loading" role="img" tabindex="-1"></div>
									  </div>
									
							<!-- 身份证上传 end -->
						</td>
						</tr>
						<tr>
							<td>
								<span class="wzts" style="margin-left: 0px;">请上传身份证正面图片，用于审核身份信息。</span> 
							</td>
						</tr>
					</table>
				
                    
                                   
                </td>
                <td  id="idCardTd">
                	  <span class="cw"  id="idCardSpan"></span>
                </td>
              </tr>
               <tr>
               		<th  height="50"></th>
               		<td style="margin: 0px;"></td>
               		<td ></td>
               	 </tr>

              <tr>
                <th valign="top"><span class="red">*&nbsp;</span>选择上传文件：</th>
                <td colspan="2">
                
                <table>
                	<tr><td>
                
                
                			<!-- 驾照上传 start -->
							 <div class="container" id="crop-driving" style="width:730px;height:240px;padding-left: 0px;">
							    <!-- Current avatar -->
							    <div class="avatar-view" style="width:730px;height:240px;" id="driving-view" title="上传驾驶证">
							     <!--  <img src="common/js/cropper/images/idcard-license-pre_proc.jpg" alt="驾照"> -->
							      <img src=" common/portal/images/jsz.png" alt="驾照">
							     
							    </div>
							    <!-- Cropping modal -->
							    <div class="modal fade" id="driving-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
							      <div class="modal-dialog modal-lg">
							        <div class="modal-content">
							          <form id="driving-form" class="avatar-form" action="subscriber/upload.action" enctype="multipart/form-data" method="post">
							          	<input type="hidden" name="imageData" id="imageData_driving">
							            <div class="modal-header">
							              <button class="close" data-dismiss="modal" type="button">&times;</button>
							              <h4 class="modal-title" id="avatar-modal-label">驾驶证上传</h4>
							            </div>
							            <div class="modal-body">
							              <div class="avatar-body">
							
							                <!-- Upload image and data -->
							                <div class="avatar-upload" id="driving-upload">
							                  <input class="avatar-src" id="driving-src" name="pic_src" type="hidden">
							                  <input class="avatar-data" id="driving-data" name="pic_data" type="hidden">
							                <label for="avatarInput">选择图片:</label>
							                  <input class="avatar-input" id="drivingInput" name="upload" type="file">
							                </div>
							
							                <!-- Crop and preview -->
							                <div class="row">
							                  <div class="col-md-9" style="width: 95%;">
							                    <div class="avatar-wrapper"  id="driving-wrapper"></div>
							                  </div>
							                  <div class="col-md-3">
							                    <div style="display:none;"class="avatar-preview preview-lg driving-preview"></div>
							                     <div style="display:none;" class="avatar-preview preview-md driving-preview"></div>
							                    <div style="display:none;" class="avatar-preview preview-sm driving-preview"></div> 
							                  </div>
							                </div>
							
							                <div class="row avatar-btns" id="driving-btns">
							                  <div class="col-md-9" style="width:100%%;">
							                    <div class="btn-group">
							                      <button class="btn btn-primary" data-method="rotate" data-option="-90" type="button" title="左转">左转</button>
							                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-15" type="button">左转15度</button>
							                     &nbsp; <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-30" type="button">左转30度</button>
							                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-45" type="button">左转45度</button>
							                    </div>
							                    <div class="btn-group">
							                      <button class="btn btn-primary" data-method="rotate" data-option="90" type="button" title="右转">右转</button>
							                      <button style="display:none;"  class="btn btn-primary" data-method="rotate" data-option="15" type="button">15deg</button>
							                      &nbsp;<button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="30" type="button">右转30度</button>
							                      <button style="display:none;"  class="btn btn-primary" data-method="rotate" data-option="45" type="button">45deg</button>
							                    </div>
							                    <div class="col-md-3">
							                    <button class="btn btn-primary btn-block avatar-save" id="driving-save" type="submit">完成</button>
							                  </div>
							                  </div>
							                  
							                </div>
							              </div>
							            </div>
							            <!-- <div class="modal-footer">
							              <button class="btn btn-default" data-dismiss="modal" type="button">Close</button>
							            </div> -->
							          </form>
							        </div>
							      </div>
							    </div><!-- /.modal -->
							
							    <!-- Loading state -->
							    <div class="loading" id="driving_loading" aria-label="Loading" role="img" tabindex="-1"></div>
							  </div>

					<!-- 驾照上传上传 end -->
                </td></tr>
               	<tr>
               	<td><span class="wzts" style="margin-left: 0px;">请上传驾驶证图片，用于审核驾驶证信息。</span>	</td></tr>
                </table>
                
                </td>
                
               
              </tr>
              <tr>
              	<td height="12" ></td>
              	<td style="margin: 0px;"></td>
              	<td  id="drivingTd"><span class="cw" style="top:0px;" id="drivingSpan"></span></td>
              </tr>
              
              <tr>
              	<th height="38" ></th>
              	<td style="margin: 0px;"></td>
              	<td></td>
               </tr>
              <tr>
                <td></td>
                <td style="margin: 0px;"><input class="input_sub" onclick="formSubmit()" type="button" value="下一步" style=" width:282px; height:39px; color:#fff; font-size:16px; font-weight:bold; line-height:39px;background:url(common/portal/images/text_btn.png) no-repeat; border:none; margin-left:20px; cursor:pointer;" /></td>
                <td ></td>
                 
              </tr>
        </table>
       
        </div>    
</div>
<div class="footer"></div>



<script type="text/javascript" src="common/js/popup_layer.js"></script>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/jquery.validate.min.js"></script>
<script type="text/javascript">

$().ready(function() {
	 $("#subscriberform").validate({
	      rules: {
	   		   	"subscriber.name": {
	   			    required: true
	   			   },
	   			
	   	        "subscriber.idCardImg": {
	   	   		    required: true
	   	   		},
	   	   	    "subscriber.drivingLicenseImg": {
	   	            required: true
	   	   	   	}
	  	  },
	      messages: {
	  	       "subscriber.name": {
	  	           required: "请填写真实姓名"
	  	          },
	  	       
	  	        "subscriber.idCardImg": {
		  		    required: "请上传身份证图片"
		  	     },
		  	     "subscriber.drivingLicenseImg": {
		  	         required: "请上传驾驶证图片"
		  	     }
			 },
			 errorPlacement: function(error, element) {   
				
					var name=$(element).attr("name");
					if(name=="subscriber.name"){
						$("#realNameTd").html("");
						error.appendTo($("#realNameTd"));
					}else if(name=="subscriber.idCardImg"){
						$("#idCardTd").html("");
						error.appendTo($("#idCardTd"));
					}else if(name=="subscriber.drivingLicenseImg"){
						$("#drivingTd").html("");
						error.appendTo($("#drivingTd"));
					}
				 //error.appendTo( element.parent().next() );                          
			 },
			 success: function (label) {
				// var name=$(label).attr("name");
				// alert(label.attr("id"));
	                label.addClass("checked");
	         },highlight: function(element, errorClass) {

		         	var name=$(element).attr("name");
					if(name=="subscriber.name"){
						$("#realNameTd  > lable").find("." + errorClass).removeClass("checked");
					}else if(name=="subscriber.idCardImg"){
						$("#idCardTd").find("." + errorClass).removeClass("checked");
					}else if(name=="subscriber.drivingLicenseImg"){
						$("#drivingTd").find("." + errorClass).removeClass("checked");
					}
					
					//$(element).parent().next().find("." + errorClass).removeClass("checked");
			 }
	    });
	});

</script>

</body>
</html>